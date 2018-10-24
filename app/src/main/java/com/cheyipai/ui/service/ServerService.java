package com.cheyipai.ui.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cheyipai.ui.CheyipaiApplication;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;
import com.yanzhenjie.andserver.website.WebSite;

import java.io.IOException;

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
              @Override
              public void run() {
                  AssetManager mAssetManager = CheyipaiApplication.getInstance().getAssets();
                  WebSite wesite = new AssetsWebsite(mAssetManager, "web");
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

