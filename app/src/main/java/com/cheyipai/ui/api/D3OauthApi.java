package com.cheyipai.ui.api;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.cheyipai.core.base.retrofit.net.CoreRetrofitClient;
import com.cheyipai.core.base.utils.SharedPrefersUtils;
import com.cheyipai.corec.activity.AbsBaseFragment;
import com.cheyipai.corec.base.api.APIParams;
import com.cheyipai.corec.base.api.AbsAPIRequestNew;
import com.cheyipai.ui.APIS;
import com.cheyipai.ui.bean.CarBrand;
import com.cheyipai.ui.bean.DownF3d;
import com.cheyipai.ui.bean.DownObj;
import com.cheyipai.ui.bean.F3d;
import com.cheyipai.ui.bean.F3dCallback;
import com.cheyipai.ui.bean.F3dStatus;
import com.cheyipai.ui.bean.F3dUploadCallback;
import com.cheyipai.ui.bean.Oauth;
import com.cheyipai.ui.bean.OauthCallback;
import com.cheyipai.ui.bean.OauthPlayer;
import com.cheyipai.ui.bean.Status;
import com.cheyipai.ui.utils.ZipUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;


/**
 * Created by Administrator on 2016/8/19.
 */
public class D3OauthApi {
    public static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;");
    private WeakReference<Context> weakcontext;
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    private static final String LINE_STATIC_NONE="static";//静态光头与单独的理发
    private static final String LINE_ANIMATED_FACE="animated_face";//静态光头，带有一组用于动画和单独理发的混合形状;
    private static final String LINE_HEAD="head";//预测头部，理发和胸围| 预测头部，理发和胸部与一组混合形状（45面部，17个visems）的动画|预测头部，理发和胸部与一组混合形状（51面部）的动画
    private static final String SUB_LINE_FACE_BASE="base/legacy";//预测头部，理发和胸围
    private static final String SUB_LINE_HEAD_MOBILE="base/mobile";//预测头部，理发和胸围

    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).build();//设置连接超时时间

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    public D3OauthApi(Context context){
        this.weakcontext = new WeakReference<Context>(context);
    }

    public void getObjOfBlendshapes(final Oauth oauth,final String code,  final DownF3d downF3d, final String filepath) {
        try {
            new Thread() {
                @Override
                public void run() {
                    DownloadObj(oauth, "https://api.avatarsdk.com/avatars/" + code + "/blendshapes?fmt=fbx", filepath, downF3d);
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static String get(String url,Oauth oauth) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get().header("Authorization",oauth.getToken()).header("X-PlayerUID",oauth.getPlayer())
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String post(String url, String json,String key,String value) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body).header(key,value)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    public void getAuthor(final String user, final OauthCallback callback) throws IOException {
        Map<String, String> source = new HashMap<>();
        source.put("client_id", "LqimjWVsBi40Wd3K815t0WwqcvyElK9kUJTPNetr");
        source.put("grant_type", "client_credentials");
        source.put("client_secret", "jYmfcHSTQqP6U7haJ4KOu4MlEIMNLYhAgnbyyBMlMZytSZK1RY13uwU7kas08TCexIkqWco8RaGUuRPqPtOQmGIGf99ASqj9dfGSM6abWuF4XwQeglbNXkVtLZL0zSeu");
        RetrofitClinetImpl.getInstance(weakcontext.get()).setIsUseHttps(true)
                .setRetrofitBaseURL("https://api.avatarsdk.com/")
                .newRetrofitClient()
                .postL("o/token/", source, new CoreRetrofitClient.ResponseCallBack<ResponseBody>() {
                    @Override
                    public void onSucceess(ResponseBody responseBody) {
                        try {
                            Oauth oauth = new Gson().fromJson(new String(responseBody.bytes()),Oauth.class);
                            createPlayer(user,oauth,callback);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });

    }

    public void createPlayer(final String playerName,final Oauth oauth,final OauthCallback oauthCallback){
        new Thread() {
            @Override
            public void run() {
                String res = null;
                try {
                    res = post("https://api.avatarsdk.com/players/","comment="+playerName,"Authorization", oauth.getToken_type()+" "+oauth.getAccess_token());
                    final OauthPlayer palyer = new Gson().fromJson(res,OauthPlayer.class);
                    oauth.setPlayer(palyer.getCode());
                    SharedPrefersUtils.put(weakcontext.get(),"player",palyer.getCode());
                    ((Activity)weakcontext.get()).runOnUiThread(new Runnable() {
                        public void run() {
                            oauthCallback.onOauthSucc(oauth);
                            Log.d("api---------", "player code -->" + palyer.getCode());

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    public  boolean DownloadSmallFile(Oauth oauth, final String uri, final String filePath, final DownF3d downF3d) {
        Request request = new Request.Builder().url(uri.toString()).header("Authorization",oauth.getToken()).header("X-PlayerUID",oauth.getPlayer()).build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return false;
            }

            ResponseBody body = response.body();
            long contentLength = body.contentLength();
            BufferedSource source = body.source();
            File file = new File(filePath);
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(source);
            sink.flush();
            ((Activity)weakcontext.get()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    downF3d.onDown(null);
                }});

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public  boolean DownloadObj(Oauth oauth, final String uri, final String filePath, final DownF3d downF3d) {
        Request request = new Request.Builder().url(uri.toString()).header("Authorization",oauth.getToken()).header("X-PlayerUID",oauth.getPlayer()).build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return false;
            }

            ResponseBody body = response.body();
            long contentLength = body.contentLength();
            BufferedSource source = body.source();
            File file = new File(filePath);
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(source);
            sink.flush();
            ZipUtils.UnZipFolder(filePath,Environment.getExternalStorageDirectory().getAbsolutePath(),"model.fbx");
            ((Activity)weakcontext.get()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    downF3d.onDown(null);
                }});

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    public void get(final Oauth oauth, final String url, final DownF3d downF3d, final String filepath){
        try {
            new Thread() {
                @Override
                public void run() {
                    DownloadSmallFile(oauth, url, filepath,downF3d);
                }}.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getFace(final Oauth oauth, final F3dStatus f3dStatus, final F3dCallback f3dCallback){
        try {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        String res = null;
                        try {
                            res = get(f3dStatus.getUrl(), oauth);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final F3d status = new Gson().fromJson(res, F3d.class);
                        if (status.getStatus().equals("Completed")) {
                            ((Activity)weakcontext.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    f3dCallback.onF3dSucc(status);
                                }});
                            break;
                        }
                    }
                }}.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFaceObj(final Oauth oauth, final F3dStatus f3dStatus, final F3dCallback f3dCallback){
        try {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        String res = null;
                        try {
                            res = get(f3dStatus.getUrl(), oauth);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final F3d status = new Gson().fromJson(res, F3d.class);
                        if (status.getStatus().equals("Completed")) {
                            ((Activity)weakcontext.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    f3dCallback.onF3dSucc(status);
                                }});
                            break;
                        }
                    }
                }}.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadUserFace(final String faceName, final Oauth oauth, final File file, final F3dUploadCallback f3dUploadCallback){
        new Thread() {
            @Override
            public void run() {
                final String player = SharedPrefersUtils.getValue(weakcontext.get(),"player","");
              /*  StringBuilder param=new StringBuilder();
                param.append("name="+faceName).append("&pipeline=animated_face");*/
                final Map<String,String> stringMap = new HashMap<String, String>();
                stringMap.put("name",faceName);
                stringMap.put("pipeline","animated_face");//head 带原始头发 animated_face 只有光头
                stringMap.put("pipeline_subtype","base/legacy");
                ((Activity)weakcontext.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uploadUserFace(stringMap, player, file, oauth, f3dUploadCallback);
                    }});

            }
        }.start();

    }

    public void uploadUserFace(final Map<String, String> map, String player, File file, Oauth oauth, final F3dUploadCallback f3dUploadCallback) {
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("photo", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        Request request = new Request.Builder().url("https://api.avatarsdk.com/avatars/").header("X-PlayerUID",player).header("Authorization", oauth.getToken_type()+" "+oauth.getAccess_token()).post(requestBody.build()).tag(weakcontext.get()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lfq" ,"onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    final F3dStatus f3d = new Gson().fromJson(str,F3dStatus.class);
                    ((Activity)weakcontext.get()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            f3dUploadCallback.onUploadSucc(f3d);
                        }
                    });

                } else {
                    Log.i("lfq" ,response.message() + " error : body " + response.body().string());
                }
            }
        });

    }

}
