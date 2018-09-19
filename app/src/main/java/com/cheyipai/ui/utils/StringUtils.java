package com.cheyipai.ui.utils;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {
	private static Charset charset = Charset.forName("UTF-8");
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static CharsetDecoder decoder = charset.newDecoder();

    /**
     * 换行符
     */
    public static final String NEWLINE = "\n";

	public static String getSecond(int sec) {
		String temp = "00:00";
		if (sec / 60 < 1) {
			temp = "00:00:" + fix(sec);
		} else if (sec / 60 / 60 < 1) {
			temp = "00:" + fix(sec / 60) + ":" + fix(sec % 60);
		} else {
			temp = fix(sec / 60 / 60 % 60) + ":" + fix(sec / 60 % 60) + ":"
					+ fix(sec % 3600);
		}
		return temp;
	}


    public static String getFileNameFromPath(String path) {
        if (TextUtils.isEmpty(path)) return "noname";

        if (path.contains("\\")) {
            return path.substring(path.lastIndexOf('\\') + 1);
        } else {
            return path.substring(path.lastIndexOf('/') + 1);
        }
    }

    public static String getFileNameFromUrl(String urlString) {
        if (TextUtils.isEmpty(urlString)) return "noname";

        return urlString.substring(urlString.lastIndexOf('/') + 1);
    }


    public static String replaceBlank(String str) {
	        String dest = "";
	        if (str!=null) {
	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	            Matcher m = p.matcher(str);
	            dest = m.replaceAll("");
	        }
	        return dest;
	    }
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		 String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字�?		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#�?…�?&*（）—�?+|{}【�?‘；：�?“�?。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	private static String fix(int sec) {
		String s = "0";
		if (sec / 10 < 1)
			s += sec;
		else
			s = sec + "";
		if (s.length() > 2) {
			s = s.substring(0, 2);
		}
		return s;
	}

	public static String firstCharUpper(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1)
				.toUpperCase());
	}

	public static String getCallTime(long time){
		  
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式�?  
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		return formatter.format(time);
	}
	
	public static String getTime(String prefix, String time) {
		if (time == null)
			return "";
		Date date = new Date(Long.valueOf(time) * 1000);
		return prefix + format.format(date);

	}

	public static String getString(ByteBuffer buffer) {

		CharBuffer charBuffer = null;
		try {
			charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
			return charBuffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static String trimSpaces(String ip) {// 去掉IP字符串前后所有的空格
		if (ip == null || ip.equalsIgnoreCase(""))
			return null;
		String str = ip.replaceAll(" ", "");
		return str;
	}

	public static boolean isNull(String str) {
		if (str == null)
			return true;
		if ("".equals(str.trim()))
			return true;
		return false;
	}

	public static boolean isIpAddress(String ip) {// 判断是否是一个IP
		boolean b = false;
		ip = trimSpaces(ip);
		if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String s[] = ip.split("\\.");
			if (Integer.parseInt(s[0]) < 255)
				if (Integer.parseInt(s[1]) < 255)
					if (Integer.parseInt(s[2]) < 255)
						if (Integer.parseInt(s[3]) < 255)
							b = true;
		}
		return b;
	}

	/**
	 * 按照规定长度截取字符�?超出规定字数部分显示...
	 * 
	 * @param oldStr
	 *            要截取的字符�?	 * @param i
	 *            要截取的长度
	 * @return 截取后的字符�?	 */
	public static String subStrAsLong(String oldStr, int i) {
		String newStr = null;
		if (oldStr.length() > i) {
			newStr = oldStr.substring(0, i) + "...";
		} else
			newStr = oldStr;
		return newStr;
	}

	public static int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static String getLazyTime() {
		Date date = new Date();
		int h = date.getHours();
		if (h >= 2 && h < 6) {
			return "lingchen";
		} else if (h >= 6 && h < 9) {
			return "zaoshang";
		} else if (h >= 9 && h < 17) {
			return "baitian";
		} else if (h >= 17 && h < 22) {
			return "bangwan";
		} else {
			return "shenye";
		}
	}

	public static int parse2Int(String str, int defaultv) {
		int i;
		try {
			i = Integer.parseInt(str);
			return i;
		} catch (Exception e) {
			return defaultv;
		}

	}

	

	/**
	 * 拼接 URL
	 * 
	 * @param oldUrl
	 *            原URL
	 * @param lengh
	 *            大小
	 * @return
	 */
	public static String replaceURL(String oldUrl, int lengh) {
		String newUrl = oldUrl.substring(0, oldUrl.lastIndexOf(".")) + lengh
				+ "x" + lengh + oldUrl.substring(oldUrl.lastIndexOf("."));
		return newUrl;
	}


    public static String getDay(){
        Calendar calendar =Calendar.getInstance();
        Date date  =  calendar.getTime();
        return format.format(date);
    }


    ///Date(1435028636913)/
    public static String getTime(String dateString){
        if(dateString == null||!dateString.contains("("))return dateString;
        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(dateString.subSequence(dateString.indexOf("(")+1,dateString.indexOf(")")).toString()));
        Date date  =  calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

    ///Date(1435028636913)/
    public static String getTime2Now(String dateString){
        if(dateString == null||!dateString.contains("("))return dateString;
        Calendar calendar =Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        long t = Long.valueOf(dateString.subSequence(dateString.indexOf("(")+1,dateString.indexOf(")")).toString());
        long diff = now - t;
        long diffMinutes = diff / (60 * 1000);
        long diffSeconds = diff / 1000;
        long diffHours = diff / (60 * 60 * 1000);
        if(diffHours>0){
            return diffHours+"小时";
        }else if(diffMinutes>0){
            return diffMinutes+"分";
        }else {
            return diffSeconds+"秒";
        }
    }

    ///Date(1435028636913)/
    public static String getBetween(String dateString){
        if(dateString == null||!dateString.contains("("))return dateString;
        Calendar calendar =Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        long t = Long.valueOf(dateString.subSequence(dateString.indexOf("(")+1,dateString.indexOf(")")).toString());
        long diff = t - now;
        if(diff <0)diff = 0;
        return getSecond((int)diff/1000);
    }



    static final boolean DEBUG = false;

    public static final String FILE_EXTENSION_APK = "apk";
    public static final String FILE_EXTENSION_DOC = "doc";
    public static final String FILE_EXTENSION_DOCX = "docx";
    public static final String FILE_EXTENSION_PDF = "pdf";
    public static final String FILE_EXTENSION_PPT = "ppt";
    public static final String FILE_EXTENSION_PPTX = "pptx";
    public static final String FILE_EXTENSION_XLS = "xls";
    public static final String FILE_EXTENSION_XLSX = "xlsx";
    public static final String FILE_EXTENSION_TXT = "txt";
    public static final String FILE_EXTENSION_XML = "xml";
    public static final String FILE_EXTENSION_LOG = "log";
    public static final String FILE_EXTENSION_ZIP = "zip";

    public static String formatPlayTime(long milliseconds) {
        if (milliseconds < 0) {
            return "00:00";
        }
        int totalSeconds = (int)(milliseconds / 1000);
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60;
        int hours = minutes / 60;
        return hours == 0 ? String.format(Locale.SIMPLIFIED_CHINESE, "%02d:%02d", minutes, seconds) :
                String.format(Locale.SIMPLIFIED_CHINESE, "%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String getFileExtension(String fileName) {
        if (fileName != null && fileName.length() > 0) {
            int start = fileName.lastIndexOf('.');
            if (start > -1 && start < fileName.length() -1) {
                return fileName.substring(start + 1).toLowerCase(Locale.getDefault());
            }
        }
        return "";
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i=0; i<strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 通过扩展名获取MIME类型
     * @param extension
     * @return
     */
    public static String getMimeTypeByExtension(String extension) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    /**
     * 通过文件名获取MIME类型
     * @param name
     * @return
     */
    public static String getMimeTypeByFileName(String name) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(name));
    }

    /**
     * 通过MIME获取扩展名
     * @param mimeType
     * @return
     */
    public static String getExtensionByMimeType(String mimeType) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
    }

    /**
     * 通过Url获取扩展名
     * @param url
     * @return
     */
    public static String getExtensionFromUrl(String url) {
        return MimeTypeMap.getFileExtensionFromUrl(url);
    }



    /**
     * 通过全路径报名获取简单包名
     * @param className
     * @return
     */
    public static String getSimpleNameFromClassName(String className) {
        int start = className.lastIndexOf(".") + 1;
        int end = className.indexOf("$");
        return className.substring(start, end == -1 ? className.length() : end);
    }

    public static String getSimpleNameFromFullName(String fullName) {
        return getSimpleNameFromClassName(fullName);
    }

	/**
	 * 判断字符串中是否含有数字
	 * @param str 字符串
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile(".*\\d+.*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

    public static String getJsonString(String str) {
        str.replaceAll("'", "&apos;");
        str.replaceAll("\"", "&quot;");
        str.replaceAll(">", "&gt;");
        str.replaceAll("<", "&lt;");
        str.replaceAll("&", "&amp;");
        return str.replaceAll(" ", "\u0020");
    }

    private StringUtils(){/*Do not new me*/};

}
