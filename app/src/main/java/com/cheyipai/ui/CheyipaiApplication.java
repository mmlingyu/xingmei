package com.cheyipai.ui;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cheyipai.corec.modules.app.BaseApplication;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
;

import java.io.File;

/**
 * application
 */
public class CheyipaiApplication extends BaseApplication {

    private static CheyipaiApplication instance = null;
    protected Activity baseActivity;
    public TextView mLocationResult, logMsg;
    public TextView trigger, exit;
    public static double mLatitude, mLongitude;
    private String mTempCoor = "bd09ll";//默认
    private static final int SCAN = 60 * 1000 * 60;//定位间隔

    public static synchronized CheyipaiApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        initARouter();
        //初始化ImageLoaderConfiguration
        initImageLoader(getApplicationContext());
        MultiDex.install(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=57c50e37");


    }

    public boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
    private void initARouter() {
        if (isApkDebugable(this)) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void initAccount() {

    }




    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        try {
            if (mLocationResult != null)
                mLocationResult.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBaseActivity(Activity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public Activity getBaseActivity() {
        return baseActivity;
    }




    /**
     * 初始化图片缓存配置
     * @param context
     */
    private void initImageLoader(Context context) {

    }
}
