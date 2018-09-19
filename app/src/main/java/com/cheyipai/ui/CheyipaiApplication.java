package com.cheyipai.ui;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.widget.TextView;
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
    public Vibrator mVibrator;
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

        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        //初始化ImageLoaderConfiguration
        initImageLoader(getApplicationContext());
        MultiDex.install(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=57c50e37");


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
