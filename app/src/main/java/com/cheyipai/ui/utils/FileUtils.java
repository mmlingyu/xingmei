package com.cheyipai.ui.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.cheyipai.corec.log.L;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    private final String TAG = "FileUtils";
    private final Context context;
    private final String SDPATH;
    private final String FILESPATH;
    private static String folderName;// 文件夹名
    private static final String PATH = PathManagerBase.SDCARD_FJ_PATH;// 附件存放的路径
    public FileUtils(Context context) {
        this.context = context;
        SDPATH = Environment.getExternalStorageDirectory().getPath() + "//";
        FILESPATH = this.context.getFilesDir().getPath() + "//";
    }

    public static void testUpload(Activity activity,int flag){
        String paramName = "DrivingLicense";
        String url = "* api";;
        String fileDir = PATH + folderName + "/";
        String fileName = folderName + flag + ".jpg";

        upload(activity,url, paramName, fileDir, fileName);
    }

    public static void upload(final Activity activity, String url, String paramName, String fileDir, String fileName){
            File filePath = new File(fileDir);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File file = new File(filePath, fileName);
            Map<String, File> files = new HashMap<String, File>();
            files.put(file.getName(), file);
            new HttpAsyncuploadMoreFile(activity, url, files, paramName, new HttpAsyncuploadMoreFile.CallBackFileInfo() {
                @Override
                public void getCallBackFileInfo(String fileLink) {
                    if (fileLink != null && !fileLink.equals("") && !fileLink.equals("null")) {
                        L.i("fileLink--->", fileLink);
                        //upLoadCarEntryInfo(fileLink);
                    } else {
                        DialogUtils.showToast(activity, "图片上传失败！");
                    }
                }
            }).execute();
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    public boolean delSDFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        file.delete();
        return true;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 删除SD卡上的目录
     *
     * @param dirName
     */
    public boolean delSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        return delDir(dir);
    }

    /**
     * 修改SD卡上的文件或目录名
     */
    public boolean renameSDFile(String oldfileName, String newFileName) {
        File oleFile = new File(SDPATH + oldfileName);
        File newFile = new File(SDPATH + newFileName);
        return oleFile.renameTo(newFile);
    }

    /**
     * 拷贝SD卡上的单个文件
     * @throws IOException
     */
    public boolean copySDFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(SDPATH + srcFileName);
        File destFile = new File(SDPATH + destFileName);
        return copyFileTo(srcFile, destFile);
    }

    /**
     * 拷贝SD卡上指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */
    public boolean copySDFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(SDPATH + srcDirName);
        File destDir = new File(SDPATH + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

    /**
     * 移动SD卡上的单个文件
     *
     * @param srcFileName
     * @param destFileName
     * @return
     * @throws IOException
     */
    public boolean moveSDFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(SDPATH + srcFileName);
        File destFile = new File(SDPATH + destFileName);
        return moveFileTo(srcFile, destFile);
    }

    /**
     * 移动SD卡上的指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */
    public boolean moveSDFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(SDPATH + srcDirName);
        File destDir = new File(SDPATH + destDirName);
        return moveFilesTo(srcDir, destDir);
    }

    /**
     * 建立私有文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public File creatDataFile(String fileName) throws IOException {
        File file = new File(FILESPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 建立私有目录
     *
     * @param dirName
     * @return
     */
    public File creatDataDir(String dirName) {
        File dir = new File(FILESPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 删除私有文件
     *
     * @param fileName
     * @return
     */
    public boolean delDataFile(String fileName) {
        File file = new File(FILESPATH + fileName);
        return delFile(file);
    }

    /**
     * 删除私有目录
     *
     * @param dirName
     * @return
     */
    public boolean delDataDir(String dirName) {
        File file = new File(FILESPATH + dirName);
        return delDir(file);
    }

    /**
     * 更改私有文件名
     *
     * @param oldName
     * @param newName
     * @return
     */
    public boolean renameDataFile(String oldName, String newName) {
        File oldFile = new File(FILESPATH + oldName);
        File newFile = new File(FILESPATH + newName);
        return oldFile.renameTo(newFile);
    }

    /**
     * 在私有目录下进行文件复制
     *
     * @param srcFileName  ： 包含路径及文件名
     * @param destFileName
     * @return
     * @throws IOException
     */
    public boolean copyDataFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(FILESPATH + srcFileName);
        File destFile = new File(FILESPATH + destFileName);
        return copyFileTo(srcFile, destFile);
    }

    /**
     * 复制私有目录里指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */
    public boolean copyDataFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(FILESPATH + srcDirName);
        File destDir = new File(FILESPATH + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

    /**
     * 移动私有目录下的单个文件
     *
     * @param srcFileName
     * @param destFileName
     * @return
     * @throws IOException
     */
    public boolean moveDataFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(FILESPATH + srcFileName);
        File destFile = new File(FILESPATH + destFileName);
        return moveFileTo(srcFile, destFile);
    }

    /**
     * 移动私有目录下的指定目录下的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */
    public boolean moveDataFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(FILESPATH + srcDirName);
        File destDir = new File(FILESPATH + destDirName);
        return moveFilesTo(srcDir, destDir);
    }

    /**
     * 删除一个文件
     *
     * @param file
     * @return
     */
    public boolean delFile(File file) {
        if (file.isDirectory())
            return false;
        return file.delete();
    }

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param dir
     */
    public boolean delDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                delDir(file);// 递归
            }
        }
        dir.delete();
        return true;
    }

    public static void zip(String src, String dest) throws IOException {
        //提供了一个数据项压缩成一个ZIP归档输出流
        ZipOutputStream out = null;
        try {

            File outFile = new File(dest);//源文件或者目录
            File fileOrDirectory = new File(src);//压缩文件路径
            out = new ZipOutputStream(new FileOutputStream(outFile));
            //如果此文件是一个文件，否则为false。
            if (fileOrDirectory.isFile()) {
                zipFileOrDirectory(out, fileOrDirectory, "");
            } else {
                //返回一个文件或空阵列。
                File[] entries = fileOrDirectory.listFiles();
                for (int i = 0; i < entries.length; i++) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, entries[i], "");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //关闭输出流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void zipFileOrDirectory(ZipOutputStream out,
                                           File fileOrDirectory, String curPath) throws IOException {
        //从文件中读取字节的输入流
        FileInputStream in = null;
        try {
            //如果此文件是一个目录，否则返回false。
            if (!fileOrDirectory.isDirectory()) {
                // 压缩文件
                byte[] buffer = new byte[4096];
                int bytes_read;
                in = new FileInputStream(fileOrDirectory);
                //实例代表一个条目内的ZIP归档
                ZipEntry entry = new ZipEntry(curPath
                        + fileOrDirectory.getName());
                //条目的信息写入底层流
                out.putNextEntry(entry);
                while ((bytes_read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                }
                out.closeEntry();
            } else {
                // 压缩目录
                File[] entries = fileOrDirectory.listFiles();
                for (int i = 0; i < entries.length; i++) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, entries[i], curPath
                            + fileOrDirectory.getName() + "/");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // throw ex;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

        /**
         * 递归删除文件和文件夹
         *
         * @param file 要删除的根目录
         */
    public void deleteFile(File file) {
        if (!file.exists()) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @throws IOException
     */
    public boolean copyFileTo(File srcFile, File destFile) throws IOException {
        if (srcFile.isDirectory() || destFile.isDirectory())
            return false;// 判断是否是文件
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = fis.read(buf)) != -1) {
            fos.write(buf, 0, readLen);
        }
        fos.flush();
        fos.close();
        fis.close();
        return true;
    }

    /**
     * 拷贝目录下的所有文件到指定目录
     *
     * @param srcDir
     * @param destDir
     * @return
     * @throws IOException
     */
    public boolean copyFilesTo(File srcDir, File destDir) throws IOException {
        if (!srcDir.isDirectory() || !destDir.isDirectory())
            return false;// 判断是否是目录
        if (!destDir.exists())
            return false;// 判断目标目录是否存在
        File[] srcFiles = srcDir.listFiles();
        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isFile()) {
                // 获得目标文件
                File destFile = new File(destDir.getPath() + "//" + srcFiles[i].getName());
                copyFileTo(srcFiles[i], destFile);
            } else if (srcFiles[i].isDirectory()) {
                File theDestDir = new File(destDir.getPath() + "//" + srcFiles[i].getName());
                copyFilesTo(srcFiles[i], theDestDir);
            }
        }
        return true;
    }

    /**
     * 移动一个文件
     *
     * @param srcFile
     * @param destFile
     * @return
     * @throws IOException
     */
    public boolean moveFileTo(File srcFile, File destFile) throws IOException {
        boolean iscopy = copyFileTo(srcFile, destFile);
        if (!iscopy)
            return false;
        delFile(srcFile);
        return true;
    }

    /**
     * 移动目录下的所有文件到指定目录
     *
     * @param srcDir
     * @param destDir
     * @return
     * @throws IOException
     */
    public boolean moveFilesTo(File srcDir, File destDir) throws IOException {
        if (!srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }
        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            if (srcDirFiles[i].isFile()) {
                File oneDestFile = new File(destDir.getPath() + "//" + srcDirFiles[i].getName());
                moveFileTo(srcDirFiles[i], oneDestFile);
                delFile(srcDirFiles[i]);
            } else if (srcDirFiles[i].isDirectory()) {
                File oneDestFile = new File(destDir.getPath() + "//" + srcDirFiles[i].getName());
                moveFilesTo(srcDirFiles[i], oneDestFile);
                delDir(srcDirFiles[i]);
            }

        }
        return true;
    }

    private static ArrayList<File> allfiles;

    /**
     * Description:获得所有的多媒体文件，存入allfiles
     *
     * @param path
     * @return
     * @author Administrator Create at:
     */
    public static ArrayList<File> getAllMediaFiles(String path) {
        allfiles = new ArrayList<File>();
        File file = new File(path);
        RecursionDir(file);
        return allfiles;
    }

    /**
     * Description:递归遍历返回多媒体文件集合
     *
     * @param file
     * @author Administrator
     * @Create at:
     */
    public static void RecursionDir(File file) {
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && isMedia(files[i].getName())) {
                    allfiles.add(files[i]);
                } else if (files[i].isDirectory()) {
                    RecursionDir(files[i]);
                }
            }
        }
    }

    /**
     * 判断文件名后缀，是否是多媒体文件
     *
     * @param fileName
     * @return
     */
    public static boolean isMedia(String fileName) {// 判断文件名后缀是否为多媒体文件(mp3,amr,mp4)
        String behand = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (behand.equals("jpg") || behand.equals("mp4") || behand.equals("amr") || behand.equals("png")
                || behand.equals("mp3") || behand.equals("gpx"))
            return true;
        else
            return false;
    }

    /**
     * Description: 打开不同格式的附件
     *
     * @param type         文件格式
     * @param param        文件路径
     * @param paramBoolean
     * @param context      上下文
     * @param file         要打开的文件 void
     * @author
     */
    public static void OpenFile(String type, String param, boolean paramBoolean, Context context, File file) {

        FileType filetype = getFileType(type);

        Intent intent = new Intent("android.intent.action.VIEW");

        Uri uri = null;

        switch (filetype) {

            case HTML:
                uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content")
                        .encodedPath(param).build();
                break;

            case IMAGE:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                uri = Uri.fromFile(file);
                break;

            case PDF:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                uri = Uri.fromFile(file);
                break;

            case TXT:
                if (paramBoolean) {
                    Uri uri1 = Uri.parse(param);
                    intent.setDataAndType(uri1, type);
                } else {
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    uri = Uri.fromFile(file);
                }
                break;

            case AUDIO:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("oneshot", 0);
                intent.putExtra("configchange", 0);
                uri = Uri.fromFile(file);
                break;

            case VIDEO:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("oneshot", 0);
                intent.putExtra("configchange", 0);
                uri = Uri.fromFile(file);
                break;

            case CHM:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                uri = Uri.fromFile(file);
                break;

            case WORD:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                uri = Uri.fromFile(file);
                break;

            case EXCEL:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                uri = Uri.fromFile(file);
                break;

            case PPT:
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                uri = Uri.fromFile(file);
                break;
        }

        intent.setDataAndType(uri, type);

        try {

            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {

            e.printStackTrace();

            Toast.makeText(context, "附件无法打开，请下载相关软件", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * FileName: FileHelper.java Description: 文件类型的枚举类
     *
     * @author
     * @Version
     * @Copyright
     */
    public static enum FileType {
        HTML("text/html"), IMAGE("image/*"), PDF("application/pdf"), TXT("text/plain"), AUDIO("audio/*"), VIDEO(
                "video/*"), CHM("application/x-chm"), WORD(
                "application/msword"), EXCEL("application/vnd.ms-excel"), PPT("application/vnd.ms-powerpoint");

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private FileType(String type) {
            this.type = type;
        }
    }

    /**
     * Description: 通过不同的文件类型返回不同的枚举对象
     *
     * @param type 系统的文件类型
     * @return 文件类型枚举对象 FileType
     * @author 王红娟 Create at: 2012-12-7 下午01:18:46
     */
    public static FileType getFileType(String type) {
        if (type.equals("text/html")) {
            return FileType.HTML;
        }
        if (type.equals("image/*")) {
            return FileType.IMAGE;
        }
        if (type.equals("application/pdf")) {
            return FileType.PDF;
        }
        if (type.equals("text/plain")) {
            return FileType.TXT;
        }
        if (type.equals("audio/*")) {
            return FileType.AUDIO;
        }
        if (type.equals("video/*")) {
            return FileType.VIDEO;
        }
        if (type.equals("application/x-chm")) {
            return FileType.CHM;
        }
        if (type.equals("application/msword")) {
            return FileType.WORD;
        }
        if (type.equals("application/vnd.ms-excel")) {
            return FileType.EXCEL;
        }
        if (type.equals("application/vnd.ms-powerpoint")) {
            return FileType.PPT;
        }
        return null;
    }

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    public static String getFileType(File file) {
        String type = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
        return type;
    }

    /**
     * @param filename
     * @return
     */
    public boolean isBitmapExists(String filename) {
        File dir = context.getExternalFilesDir(null);
        File file = new File(dir, filename);
        return file.exists();
    }

    /**
     * 保存图片到指定的路径下
     *
     * @param bitmap
     */
    /**
     * @param bitmap
     * @param filePath 文件路径
     */
    public void saveBitmapPath(Bitmap bitmap, String filePath, String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream(file);
                if (bitmap != null) {
                    // 如果不压缩是100，表示压缩率为0
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                        try {
                            fileOutputStream.flush();
                        } catch (IOException e) {
                            file.delete();
                            e.printStackTrace();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                file.delete();
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 删除 Files 目录下所有的图片
     *
     * @return
     */
    public boolean cleanCache() {
        if (!isExternalStorageWritable()) {
            return false;
        }

        File dir = context.getExternalFilesDir(null);

        if (dir != null) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }

        return true;
    }

    /**
     * 计算 Files 目录下图片的大小
     *
     * @return
     */
    public String getCacheSize() {
        if (!isExternalStorageWritable()) {
            return null;
        }

        long sum = 0;
        File dir = context.getExternalFilesDir(null);

        if (dir != null) {
            for (File file : dir.listFiles()) {
                sum += file.length();
            }
        }

        if (sum < 1024) {
            return sum + "字节";
        } else if (sum < 1024 * 1024) {
            return (sum / 1024) + "K";
        } else {
            return (sum / (1024 * 1024)) + "M";
        }
    }

    /**
     * 返回当前应用 SD 卡的绝对路径 like
     * /storage/sdcard0/Android/data/com.example.test/files
     */
    public String getAbsolutePath() {
        File root = context.getExternalFilesDir(null);

        if (root != null) {
            return root.getAbsolutePath();
        }

        return null;
    }

    /**
     * 返回当前应用的 SD卡缓存文件夹绝对路径 like
     * /storage/sdcard0/Android/data/com.example.test/cache
     */
    public String getCachePath() {
        File root = context.getExternalCacheDir();

        if (root != null) {
            return root.getAbsolutePath();
        }

        return null;
    }

    /**
     * 写入内容到SD卡中的txt文本中 str为内容
     *
     * @param sdPath
     * @param fileName (峪子沟彩蝶线之10公里线路.gpx)
     * @return
     */
    public static void writeSDFile(String sdPath, String content, String fileName) {
        try {
            FileWriter fw = new FileWriter(sdPath + "/" + fileName);
            File f = new File(sdPath + "/" + fileName);
            fw.write(content);
            FileOutputStream os = new FileOutputStream(f);
            DataOutputStream out = new DataOutputStream(os);
            out.writeShort(2);
            out.writeUTF("");
            System.out.println(out);
            fw.flush();
            fw.close();
            System.out.println(fw);
        } catch (Exception e) {
        }
    }

    /**
     * 读取SD卡中文本文件(解决中文乱码)
     *
     * @param sdPath
     * @param fileName (峪子沟彩蝶线之10公里线路.gpx)
     * @return
     */
    public static String chinaReadSDTxtFile(String sdPath, String fileName) {
        StringBuffer sb = new StringBuffer("");
        File file = new File(sdPath + "/" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 需要权限:android.permission.GET_TASKS
     *
     * @param context
     * @return
     */
    public static boolean isApplicationBroughtToBackground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
