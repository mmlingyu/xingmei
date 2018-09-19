package com.cheyipai.ui.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Class Name: HttpURLConAsyncUPMoreImage.java Function:
 * <p/>
 * Modifications:上传图片
 *
 * @author ShaoS DateTime 2014-3-8 下午2:58:16
 * @version 1.0
 */
public class HttpAsyncuploadMoreFile extends AsyncTask<String, Integer, String> {
    private final Activity act;
    private final String url;
    private final Map<String, File> files;
    private ProgressDialog mProgress;
    private final String paramName;
    private final CallBackFileInfo mCallBackFileInfo;
    private boolean isShowProgress = true;
    private boolean isComPress = true;

    public HttpAsyncuploadMoreFile(Activity activity, String url,
                                   Map<String, File> files, String paramName,
                                   CallBackFileInfo mCallBackFileInfo) {
        this.act = activity;
        this.url = url;
        this.files = files;
        this.paramName = paramName;
        this.mCallBackFileInfo = mCallBackFileInfo;
    }

    public HttpAsyncuploadMoreFile(Activity activity, String url,
                                   Map<String, File> files, String paramName,
                                   CallBackFileInfo mCallBackFileInfo, boolean flag, boolean isComPress) {
        this.act = activity;
        this.url = url;
        this.files = files;
        this.paramName = paramName;
        this.mCallBackFileInfo = mCallBackFileInfo;
        this.isShowProgress = flag;
        this.isComPress = isComPress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress = new ProgressDialog(act);
        mProgress.setTitle("提示信息");
        mProgress.setMessage("正在上传...请稍后");
        mProgress.setCancelable(false);
        // 定义进度条的样式
        mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        if (isShowProgress) {
            mProgress.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        // 数据输出流
        DataOutputStream dos = null;
        // 获得文件输入流
        // FileInputStream fis = null;
        // 字节数组输入流
        ByteArrayInputStream bais = null;
        // 字节数组输出流
        ByteArrayOutputStream baos = null;
        // 定义BufferReader输入流来读取URL的响应
        BufferedReader br = null;
        // 获取响应的结果
        String result = "";

        String BOUNDARY = "***";
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        try {
            URL uri = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setConnectTimeout(5 * 1000);// 设置超时时间
            conn.setReadTimeout(5 * 1000); // 缓存的最长时间
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST");// 设置传输方式POST
            conn.setRequestProperty("Connection", "Keep-Alive");// 保持长连接
            conn.setRequestProperty("Charsert", "UTF-8");// 文字编码
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                    + ";boundary=" + BOUNDARY);// 文件类型
            // 数据输出流
            dos = new DataOutputStream(conn.getOutputStream());

            // 发送文件数据
            if (files != null) {
                for (Map.Entry<String, File> file : files.entrySet()) {
                    StringBuilder strb = new StringBuilder();
                    strb.append(PREFIX);
                    strb.append(BOUNDARY);
                    strb.append(LINEND);
                    strb.append("Content-Disposition: form-data; name=\""
                            + paramName + "\"; filename=\"" + file.getKey()
                            + "\"" + LINEND);
                    strb.append("Content-Type: multipart/form-data; charset="
                            + CHARSET + LINEND);
                    strb.append(LINEND);
                    dos.write(strb.toString().getBytes());

                    // 获得文件输入流
                    // fis = new FileInputStream(file.getValue());
                    baos = new ByteArrayOutputStream();
                    Bitmap bitmap = getSmallBitmap(file.getValue().toString());
                    // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    // 把压缩后的数据baos存放到ByteArrayInputStream中
                    int options = 100;
                    // 如果大于80kb则再次压缩,最多压缩三次
                    if (isComPress) {
                        while (baos.toByteArray().length / 1024 > 80
                                && options != 10) {
                            // 清空baos
                            baos.reset();
                            // 这里压缩options%，把压缩后的数据存放到baos中
                            bitmap.compress(Bitmap.CompressFormat.JPEG,
                                    options, baos);
                            options -= 30;
                        }
                    }
                    bais = new ByteArrayInputStream(baos.toByteArray());
                    // 获取文件的总长度
                    int fileLength = bais.available();
                    // 每次上传后累加的长度
                    int upLength = 0;
                    // 设置每次写入1024个字节的缓冲区
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    // 从文件中读取数据到缓冲区
                    while ((len = bais.read(buffer)) != -1) {
                        // 每上传一次将数据累加起来
                        upLength += len;
                        // 将资料写入DataOutputStream中
                        dos.write(buffer, 0, len);
                        // 得到当前图片上传的进度
                        int progress = (int) ((upLength / (float) fileLength) * 100);
                        // 时刻将当前进度更新给onProgressUpdate方法
                        publishProgress(progress);
                    }
                    dos.write(LINEND.getBytes());
                }

                // 请求结束标志
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND)
                        .getBytes();
                dos.write(end_data);
                // 清空缓存
                dos.flush();

                // 获取响应的内容
                if (conn.getResponseCode() == HttpStatus.SC_OK) {
                    InputStream inputStream = conn.getInputStream();
                    byte[] data = readInstream(inputStream);
                    String json = new String(data);
                    JSONObject jsonobj = new JSONObject(json);
                    result = jsonobj.getString("Data");
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
                if (baos != null) {
                    baos.close();
                }
                if (dos != null) {
                    dos.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgress.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String filePath = PathManagerBase.SDCARD_FJ_PATH + "cypphoto/cypphoto.jpg";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        if (mProgress != null) {
            mProgress.cancel();
        }
        if (mCallBackFileInfo != null) {
            mCallBackFileInfo.getCallBackFileInfo(result);
        }
    }

    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        // inSampleSize就是缩放值。 inSampleSize为1表示宽度和高度不缩放，为2表示压缩后的宽度与高度为原来的1/2
        options.inSampleSize = calculateInSampleSize(options, 480, 480);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // heightRatio是图片原始高度与压缩后高度的倍数
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            // widthRatio是图片原始宽度与压缩后宽度的倍数
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static byte[] readInstream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        baos.close();
        inputStream.close();
        return baos.toByteArray();
    }

    public interface CallBackFileInfo {
        void getCallBackFileInfo(String fileLink);
    }
}
