package com.cheyipai.corec.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cheyipai.corec.modules.app.BaseApplication;
import com.cheyipai.corec.log.L;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * app信息助手类
 * Created by daincly on 2014/10/28.
 */
public class AppInfoHelper {
    /**
     * 客户端文件名
     */
    public static final String APP_NAME = "CheYiPaiBusiness";
    /**
     * 客户端适用于系统 android
     */
    public static final String APP_OS = "android";
    /**
     * 客户端 代号
     */
    public static final String APP_CODE = "d";
    /**
     * 设备系统版本号
     */
    public static final String DEVICE_VERSION = android.os.Build.VERSION.RELEASE;
    /**
     * 手机型号
     */
    public static final String DEVICE_MODEL = android.os.Build.MODEL;
    /**
     * WIFI网络
     */
    public static final String NET_TYPE_WIFI = "2";
    /**
     * 蜂窝网络
     */
    public static final String NET_TYPE_CELLULAR = "1";
    /**
     * 无sim 卡
     */
    public static final String CELLULAR_TYPE_NO = "0";
    /**
     * 移动蜂窝
     */
    public static final String CELLULAR_TYPE_CM = "1";
    /**
     * 联通蜂窝
     */
    public static final String CELLULAR_TYPE_CN = "2";
    /**
     * 电信蜂窝
     */
    public static final String CELLULAR_TYPE_CT = "3";
    /**
     * 其他蜂窝
     */
    public static final String CELLULAR_TYPE_OT = "4";

    /**
     * 获取渠道号途径tag
     */
    public static final String CHANNEL_TAG = "channel_tag";

    /**
     * 手机内应用列表
     * 此处获取本地安装应用可能存在性能问题，会和耗时
     */
    public static final String ALL_APPS = achieveAPPList();

    private static final String TAG = "AppInfoHelper";



    /**
     * 查询手机内应用
     *
     * @return
     */
    public static List<PackageInfo> getAllApps() {
        PackageManager pManager = BaseApplication.getApplication().getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> pakList = pManager.getInstalledPackages(0);
        return pakList;
    }

    /**
     * 获取非系统安装app集合字符串
     * @return
     */
    public static String achieveAPPList() {
        StringBuffer sb = new StringBuffer();
        PackageManager packageManager = BaseApplication.getApplication().getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0); //返回已安装的包信息列表
        for(PackageInfo packageInfo : packageInfoList) {
                //判断是否为非系统应用
            if((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM) == 0) {
                sb.append(packageInfo.applicationInfo.loadLabel(packageManager).toString()).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 查询手机内应用
     *
     * @return
     */
    public static String getAllApp() {
        List<PackageInfo> pakList = getAllApps();
        return pakList != null ? pakList.toString(): "" ;
    }

    /**
     * 查询当前程序apk名称
     *
     * @return
     */
    public String getApplicationName() {
        PackageManager pManager = BaseApplication.getApplication().getPackageManager();
        ApplicationInfo appInfo = null;
        String appName = "";
        try {
            appInfo = pManager.getApplicationInfo(BaseApplication.getApplication().getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (appInfo != null) {
            appName = pManager.getApplicationLabel(appInfo).toString();
        }
        String apkName = appInfo.sourceDir;
//        String apkName1 = appInfo.publicSourceDir;
        return appName;
    }

    /**
     * 查询当前程序包名称
     *
     * @return
     */
    public String getApplicationPackageName() {
        PackageManager pManager = BaseApplication.getApplication().getPackageManager();
        ApplicationInfo appInfo = null;
        String appName = "";
        try {
            appInfo = pManager.getApplicationInfo(BaseApplication.getApplication().getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (appInfo != null) {
            appName = pManager.getApplicationLabel(appInfo).toString();
        }
        String apkName = appInfo.packageName;
        return appName;
    }

    /**
     * 返回当前程序版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo("com.cyp", 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            L.e(TAG, "Exception", e);
        }
        return versionName;
    }

    /**
     * Manifest中meta_data的字符串信息
     *
     * @param metaKey
     * @return
     */
    public static String getMetaInfo(String metaKey, String defaultValue) {
        PackageManager pManager = BaseApplication.getApplication().getPackageManager();
        ApplicationInfo appInfo = null;
        String msg = defaultValue;
        try {
            appInfo = pManager.getApplicationInfo(BaseApplication.getApplication().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return msg;
        }
        if (appInfo != null && appInfo.metaData != null) {
            if (appInfo.metaData.get(metaKey) != null) {
                if (!TextUtils.isEmpty(appInfo.metaData.get(metaKey).toString())) {
                    msg = appInfo.metaData.get(metaKey).toString();
                }
            }
        }
        return msg;
    }


    /**
     * 查询当前程序安装路径
     *
     * @return
     */
    public String getApplicationSourceDir() {
        PackageManager pManager = BaseApplication.getApplication().getPackageManager();
        ApplicationInfo appInfo = null;
        String sourceDir = "";
        try {
            appInfo = pManager.getApplicationInfo(BaseApplication.getApplication().getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            L.e(e + "");
        }
        if (appInfo != null) {
            sourceDir = appInfo.sourceDir;
        }
        return sourceDir;
    }

    /**
     * Manifest中meta_data的布尔值信息
     *
     * @param metaKey
     * @return
     */
    public boolean getMetaInfo(String metaKey, boolean defaultValue) {
        PackageManager pManager = BaseApplication.getApplication().getPackageManager();
        ApplicationInfo appInfo = null;
        boolean result = defaultValue;
        try {
            appInfo = pManager.getApplicationInfo(BaseApplication.getApplication().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return result;
        }
        if (appInfo != null && appInfo.metaData != null) {
            result = appInfo.metaData.getBoolean(metaKey);
        }
        return result;
    }

    /**
     * 获取客户端versionName
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager pManager = BaseApplication.getApplication().getPackageManager();
            PackageInfo pi;
            if (pManager != null) {
                pi = pManager.getPackageInfo(BaseApplication.getApplication().getPackageName(), 0);
                versionName = pi.versionName;
            }
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 获取客户端versionCode
     */
    public static int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageManager pManager = BaseApplication.getApplication().getPackageManager();
            PackageInfo pi;
            if (pManager != null) {
                pi = pManager.getPackageInfo(BaseApplication.getApplication().getPackageName(), 0);
                versionCode = pi.versionCode;
            }
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 获取手机串号IMEI
     *
     * @return
     */
    public static String getDeviceIMEI() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId == null || deviceId.length() == 0 ? UUID.randomUUID().toString() : tm.getDeviceId();
    }

    /**
     * 获取网络状态  蜂窝网 or WIFI
     * 1=蜂窝网，2=WIFI
     *
     * @return
     */
    public static String getNetType() {
        if (isMobileConnected()) {
            return NET_TYPE_CELLULAR;
        } else if (isWifiConnected()) {
            return NET_TYPE_WIFI;
        }
        return null;
    }

    /**
     * 判断蜂窝网络是否连接
     *
     * @return
     */
    public static boolean isMobileConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //友盟崩溃修复 by haoxiaoyang 15-1-29
        //mConnectivityManager可能为空
        NetworkInfo mMobileNetworkInfo = null;
        if (null != mConnectivityManager) {
            mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        if (mMobileNetworkInfo != null) {
            return mMobileNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断WIFI网络是否链接
     *
     * @return
     */
    public static boolean isWifiConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)BaseApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 获取蜂窝网络提供商
     *
     * @return
     */
    public static String getCellularType() {
        TelephonyManager telManager = (TelephonyManager) BaseApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {

            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                //中国移动
                return CELLULAR_TYPE_CM;
            } else if (operator.equals("46001")) {
                //中国联通
                return CELLULAR_TYPE_CN;
            } else if (operator.equals("46003")) {
                //中国电信
                return CELLULAR_TYPE_CT;
            } else {
                return CELLULAR_TYPE_OT;
            }
        }
        return null;
    }

    /**
     * 获取渠道号
     * @return 渠道号
     */
    public static String getChannel() {
        ApplicationInfo appinfo = BaseApplication.getApplication().getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains("cypchannel")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] split = ret.split("_");
        if (split != null && split.length >= 2) {
            String channel = ret.substring(split[0].length() + 1);
            return channel;
        } else {
            return "";
        }
    }

    /**
     * 获取渠道号
     * @param tag 是否获取已保存的渠道号标记
     * @return 渠道号
     */
//    public static String getChannel(String tag) {
//        if (TextUtils.isEmpty(tag)) {
//            return getChannel();
//        } else {
//            String channel = GlobalConfigHelper.getInstance().getChannel();
//            if (StringUtils.isNull(channel)) {
//                return getChannel();
//            } else {
//                return channel;
//            }
//        }
//    }

    /**
     * 判断是否是小米手机
     * @return
     */
    public static boolean isMIUI(){
        return  android.os.Build.BRAND.contains("Xiaomi");
    }

    /**
     * 打印手机信息（测试使用）
     */
    public static void printDeviceInf(){
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCT ").append(android.os.Build.PRODUCT).append("\n");
        sb.append("BOARD ").append(android.os.Build.BOARD).append("\n");
        sb.append("BOOTLOADER ").append(android.os.Build.BOOTLOADER).append("\n");
        sb.append("BRAND ").append(android.os.Build.BRAND).append("\n");
        sb.append("CPU_ABI ").append(android.os.Build.CPU_ABI).append("\n");
        sb.append("CPU_ABI2 ").append(android.os.Build.CPU_ABI2).append("\n");
        sb.append("DEVICE ").append(android.os.Build.DEVICE).append("\n");
        sb.append("DISPLAY ").append(android.os.Build.DISPLAY).append("\n");
        sb.append("FINGERPRINT ").append(android.os.Build.FINGERPRINT).append("\n");
        sb.append("HARDWARE ").append(android.os.Build.HARDWARE).append("\n");
        sb.append("HOST ").append(android.os.Build.HOST).append("\n");
        sb.append("ID ").append(android.os.Build.ID).append("\n");
        sb.append("MANUFACTURER ").append(android.os.Build.MANUFACTURER).append("\n");
        sb.append("MODEL ").append(android.os.Build.MODEL).append("\n");
        sb.append("PRODUCT ").append(android.os.Build.PRODUCT).append("\n");
        sb.append("RADIO ").append(android.os.Build.RADIO).append("\n");
        sb.append("SERIAL ").append(android.os.Build.SERIAL).append("\n");
        sb.append("TAGS ").append(android.os.Build.TAGS).append("\n");
        sb.append("TIME ").append(android.os.Build.TIME).append("\n");
        sb.append("TYPE ").append(android.os.Build.TYPE).append("\n");
        sb.append("USER ").append(android.os.Build.USER).append("\n");
        L.i(sb.toString());
    }
}
