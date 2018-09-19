package com.cheyipai.corec.base.api;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cheyipai.corec.modules.app.BaseApplication;
import com.cheyipai.corec.utils.EncryptUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Eutils {

    private static String sDeviceId = "";
	
	public static String getNetworkOperatorName() {
		TelephonyManager tm = (TelephonyManager) BaseApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getNetworkOperatorName();
	}

	/**
	 * 获取 本机ip
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 判断系统是否能否响应 intent
	 * 
	 * @param context
	 * @param intent
	 * @return
	 */
	public static boolean checkResponseIntent(Context context, Intent intent) {
		if (context == null || intent == null) return false;
		List<ResolveInfo> activitys = context.getPackageManager().queryIntentActivities(intent, 10);
		return activitys.size() > 0;
	}
	
    /**
     * 返回imei，如果没有imei则返回软件计算的uuid
     */
    public static String getDeviceId() {
        if(!TextUtils.isEmpty(sDeviceId)){
            return sDeviceId;
        }
        Context context = BaseApplication.getApplication();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            String imei = "" + tm.getDeviceId();
            if (TextUtils.isEmpty(imei) || "000000000000000".equals(imei)) {
                String androidId = "";
                String deviceMobileNo = "";
                deviceMobileNo = "" + tm.getLine1Number();
                androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                String deviceUUID = EncryptUtil.encrypt((imei + androidId + deviceMobileNo), EncryptUtil.MD5);
                sDeviceId = deviceUUID;
            } else {
                sDeviceId = imei;
            }
        }
        return sDeviceId;
    }


    public static String str2UnicodeString(String res) {
		String result = "";
		if (res == null || res.length() == 0)
			return result;
		try {
			byte[] uncode;
			uncode = res.getBytes("Unicode");
			int x = 0xff;
			for (int i = 2; i < uncode.length; i++) {
				if (i % 2 == 0)
					result += "\\u";
				result += String.format("%2s", Integer.toHexString(x & uncode[i])).replaceAll(" ", "0");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 把list列表转换成逗号分割的字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list) {
		if (list == null || list.size() == 0) return null;
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			sb.append(list.get(0));
			for (int i = 1; i < list.size(); i++) {
				sb.append(",").append(list.get(i));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 把list列表转换成逗号分割的字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String listObjectToString(List<Object> list) {
		String temp = "";
		if (list == null || list.size() == 0) return temp;
		if (list != null && list.size() > 0) {
			temp += list.get(0);
			for (int i = 1; i < list.size(); i++) {
				temp += "," + list.get(i);
			}
		}
		return temp;
	}
	
	
 	public static final String APP_DIRECTORY_NAME = ".cheyipai";
 	public static final String ATTACHMENT_DIRTORY_NAME  = "attachment";
 	public static final String APK_DIRCTORY_NAME = "apk";
 	
	/**
	 * 将bitmap保存到sdcard上
	 * 
	 * @param bmp
	 * @param diretoryName	保存目录
	 * @param name			文件名
	 */
	public static void bitmap2File(Bitmap bmp, String diretoryName, String name) {
		if (bmp == null || diretoryName == null || name == null) return;
		File dir = createDirectory(Environment.getExternalStorageDirectory(), APP_DIRECTORY_NAME);
		if (dir != null) {
			dir = createDirectory(dir, ATTACHMENT_DIRTORY_NAME);
			if (dir != null && dir.exists()) {
				File bitmapFile = new File(dir, name);
				if (!bitmapFile.exists()) {
					try {
						bitmapFile.createNewFile();
						FileOutputStream fos = new FileOutputStream(bitmapFile);
						bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
						fos.flush();
						fos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 从缓存取图片
	 * 
	 * @param fileName
	 * @return
	 */
	public static BitmapDrawable file2Bitmap(String fileName) {
		if (fileName == null) return null;
		File attachmentDir = getDirctoryByName(ATTACHMENT_DIRTORY_NAME);
		if (attachmentDir != null) {
			File imageFile = new File(attachmentDir, fileName);
			if (imageFile.exists()) return new BitmapDrawable(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
		}
		return null;
	}

	/**
	 * 通过目录名取得目录file
	 * 
	 * @param name
	 * @return
	 */
	public static File getDirctoryByName(String name) {
		if (name == null) return null;
		File dir = new File(Environment.getExternalStorageDirectory(), APP_DIRECTORY_NAME);
		if (dir != null && dir.exists()) {
			dir = new File(dir, name);
			if (dir != null && dir.exists()) return dir;
		}
		return null;
	}
	
	/**
	 * 创建目录
	 * 
	 * @param parent
	 * @param directoryName
	 * @return
	 */
	public static File createDirectory(File parent, String directoryName) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (parent == null || directoryName == null) return null;
			File file = new File(parent, directoryName);
			if (!file.exists()) file.mkdirs();
			try {
				new File(file, ".Nomedia").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return file;
		}
		return null;
	}

	public static boolean exsitPnsService(Context context, String PackageName) {
		if (PackageName == null || PackageName.length() == 0) return false;
		if (context == null) context = BaseApplication.getApplication();
		ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runServices = mActivityManager.getRunningServices(10000);
		for (RunningServiceInfo serviceInfo : runServices) {
			if (serviceInfo.service.getPackageName().equals(PackageName)) return true;
		}
		return false;
	}
	
	public static String getDeviceType() {
		String type = null;
		type = (type = Build.MODEL) == null || Build.MODEL.length() == 0 ? "未知" : type;
		return type;
	}
	
	public static Bundle map2Bundle(Map<String, ?> map) {
		if (map == null) return null;
		Bundle bundle = new Bundle();
		for (Entry<String, ?> entry : map.entrySet()) {
			Object object = entry.getValue();
			bundle.putSerializable(entry.getKey(), object == null ? "" : (Serializable)object);
		}
		return bundle;
	}
	
	public static Map<String, Object> bundle2Map(Bundle bundle) {
		if (bundle == null) return null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (String key : bundle.keySet()) {
			Object object = bundle.get(key);
			map.put(key, object == null ? "" : object);
		}
		return map;
	}
	
//	/**
//	 * 这个验证方法 请不要随意更改，如果要更改请与服务端的规则保持一致 OK ?
//	 * @param phone
//	 * @return
//	 */
//	public static boolean isMobile(String phone) {
//		if (phone == null || phone.length() != 11) return false;
//		Pattern p = Pattern.compile("^1[3458][0-9]{9}$");
//		Matcher m = p.matcher(phone); 
//		return m.matches();
//	}
	
	/**
	 * 注意：验证手机号 客户端只验证位数，合法性交给服务端无特殊要求 请调用此方法来进行手机号的验证
	 * @param phone
	 * @return
	 */
	public static boolean isMobile(String phone) {
		return (phone != null && phone.trim().length() == 11) ? true : false;
	}
	
	/**
	 * 计算两经纬度之间的距离，用于地图功能。
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double DistanceOfTwoPoints(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = lat1 * (Math.PI / 180.0);
		double radLat2 = lat2 * (Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = lng1 * (Math.PI / 180.0) - lng2 * (Math.PI / 180.0);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static void getThrowable() {
		StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
		if(stackElements != null && stackElements.length > 0) {
			for(int i = stackElements.length - 1; i > 0; i--) {
				System.out.println(stackElements[i].getClassName() + " Method:" + stackElements[i].getLineNumber() + "  " + stackElements[i].getMethodName() + "line");
				System.out.println("-----------------------------------");
			
			}
		}
	}
	
	public static String parseException2String(Exception e) {
		if (e == null) return "";
		StringBuilder sb = new StringBuilder();
		sb.append(e.toString() + "\n");
		StackTraceElement[] elements = e.getStackTrace();
		int length = elements.length;
		for (int i = 0; i < length; i++) sb.append(elements[i] + "\n");
		return sb.toString();
	}

	/**
	 * 对手机号 进行合法性验证
	 * @param
	 * @return
	 */
	public static boolean isValideMobile(String phone) {
		if (phone == null || phone.length() != 11) return false;
		Pattern p = Pattern.compile("^1[3-8][0-9]{9}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	
	// 去掉html标记
	public static String stripHtml(String content) {
		// 去掉段落换行
		content = content.replaceAll("<p.*?>", "");
		// <br><br/>替换为换行
		content = content.replaceAll("<brs*/?>", "\n");
		// 去掉其它的<>之间的东西
		content = content.replaceAll("<.*?>", "");
		return content;
	}
	
	/**
	 * 字符转换
	 * 如 -------><a href="www.baidu.com">&#30334;&#24230;</a>
	 * 转换之后 为<a href="www.baidu.com">百度</a>
	 * @param s
	 * @return
	 */
	public static String UnicodeToGBK2(String s){
        String[] k = s.split(";") ;
        String rs = "" ;
        for(int i=0;i<k.length;i++) {
            int strIndex=k[i].indexOf("&#");
            String newstr = k[i];
            if(strIndex>-1) {
                String kstr = "";
                if(strIndex>0) {
                    kstr = newstr.substring(0,strIndex);
                    rs+=kstr;
                    newstr = newstr.substring(strIndex);
                }
                int m = Integer.parseInt(newstr.replace("&#",""));
                char c = (char)m ;
                rs+= c ;
            } else {
                rs+=k[i];
            }
        }
        return rs;
    }
}
