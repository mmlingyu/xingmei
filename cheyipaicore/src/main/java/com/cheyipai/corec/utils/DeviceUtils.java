package com.cheyipai.corec.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;

import com.cheyipai.corec.modules.app.BaseApplication;

public class DeviceUtils {

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth() {
        WindowManager wm = (WindowManager) BaseApplication.getApplication()
                .getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight( ) {
        WindowManager wm = (WindowManager)BaseApplication.getApplication()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity( ) {
		return BaseApplication.getApplication().getResources().getDisplayMetrics().density;
	}

    /**
     * 友盟统计设备识别工具函数
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
