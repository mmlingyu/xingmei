package com.cheyipai.ui.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cheyipai.ui.CheyipaiApplication;
import com.cheyipai.ui.commons.Path;
import com.cheyipai.ui.commons.XStorageWebsite;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;
import com.yanzhenjie.andserver.website.StorageWebsite;
import com.yanzhenjie.andserver.website.WebSite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2018/10/18.
 */

public class ServerService extends Service {

    AndServer andServer;
    Server server;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static String copyAssetsDir2Phone(String filePath) {
        String webPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + filePath;
        try {
            String[] fileList = CheyipaiApplication.getInstance().getAssets().list(filePath);
            if (fileList.length > 0) {//如果是目录
                File file = new File(webPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileList) {
                    filePath = filePath + File.separator + fileName;

                    copyAssetsDir2Phone(filePath);

                    filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
                    //Log.i("oldPath", filePath);
                }
            } else {//如果是文件
                InputStream inputStream = CheyipaiApplication.getInstance().getAssets().open(filePath);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + filePath);
                //Log.i("copyAssets2Phone", "file:" + file);
                if (!file.exists() || file.length() == 0) {
                    FileOutputStream fos = new FileOutputStream(file);
                    int len = -1;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    inputStream.close();
                    fos.close();
                    Log.d("service ---- ", "模型文件复制完毕");
                } else {
                    Log.d("service ---- ", "模型文件已存在，无需复制");
                    return webPath;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webPath;
    }

    private String getPath(String url){
        File file = null;
        try {
            file = new File(new URI(url.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String filePath = file.getAbsolutePath();
        String[] dataStr = filePath.split("/");
        String fileTruePath = "/sdcard";
        for(int i=4;i<dataStr.length;i++) {
            fileTruePath = fileTruePath + "/" + dataStr[i];
        }
        return filePath;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
              @Override
              public void run() {
                  //copyAssetsDir2Phone(Path.WEB_PATH);
                  File file = new File(Environment.getExternalStorageDirectory(), Path.WEB_PATH);
                  String websiteDirectory = file.getAbsolutePath();
                 // String path = getPath(websiteDirectory);
                  WebSite wesite = new XStorageWebsite(websiteDirectory);
                  andServer = new AndServer.Build().website(wesite).listener(mListener).port(8001).timeout(10 * 1000).build();
                  server = andServer.createServer();
                  if(server!=null)
                      if(!server.isRunning()){
                          server.start();
                      }
              }
          }).start();

        return super.onStartCommand(intent, flags, startId);
    }
    private Server.Listener mListener = new Server.Listener() {
        @Override
        public void onStarted() {
            // 服务器启动成功.
            Log.d("service","start------------------");
        }

        @Override
        public void onStopped() {
            // 服务器停止了，一般是开发者调用server.stop()才会停止。
            Log.d("service","stop------------------");
        }

        @Override
        public void onError(Exception e) {
            // 服务器启动发生错误，一般是端口被占用。
            Log.d("service","error------------------");
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(server!=null){
            server.stop();
        }
    }
}

