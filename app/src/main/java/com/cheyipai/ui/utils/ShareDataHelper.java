package com.cheyipai.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.cheyipai.corec.utils.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class ShareDataHelper {
    // sharePreferences的文件的名称
    public static final String HISTORY = "HISTORY";
    public static final String SESSION = "serrsion";
    public static final String CONTACT = "contact";
    public static final String OAUTH_obj = "oauth_obj";
    private static SharedPreferences user_setting;
    public static final String HAIR = "hair";
    public static final String HAIR_ID = "hair_id";
    private static SharedPreferences getInstance(String key, Context ctx) {
        if (HAIR.equalsIgnoreCase(key)) {
            if (user_setting == null)
                user_setting = ctx.getSharedPreferences(HAIR,
                        Context.MODE_PRIVATE);
            return user_setting;
        }
        return null;
    }

    public static void saveString(String spkey, Context ctx, String key,
                                  String value) {
        SharedPreferences sp = getInstance(spkey, ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String spkey, Context context, String key,
                                   String defValue) {
        SharedPreferences sp = getInstance(spkey, context);
        return sp.getString(key, defValue);
    }

    public static boolean isCache(String spkey, Context context, String key) {
        SharedPreferences sp = getInstance(spkey, context);
        return sp.contains(key);
    }

    public static void saveBoolean(String spkey, Context context, String key,
                                   boolean value) {
        SharedPreferences sp = getInstance(spkey, context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String spkey, Context context, String key,
                                     boolean defValue) {
        SharedPreferences sp = getInstance(spkey, context);
        return sp.getBoolean(key, defValue);
    }

    public static boolean clear(String spkey, Context context, String key) {
        SharedPreferences sp = getInstance(spkey, context);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
            return true;
        }
        return false;
    }

    public static boolean clearAll(String spkey, Context context) {

        SharedPreferences sp = getInstance(spkey, context);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
            return true;
        }
        return false;
    }

    public static boolean saveObject(String spkey, Context context, String key,
                                     Object obj) {
        try {
            // 保存对象
            SharedPreferences sp = getInstance(spkey, context);
            // 先将序列化结果写到byte缓存中，其实就分配一个内存空�?
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            // 将对象序列化写入byte缓存
            os.writeObject(obj);
            byte[] arr = bos.toByteArray();
            String value = new String(Base64.encode(arr));
            // 将序列化的数据转�?6进制保存
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.commit();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("保存Share--", e.toString());
            return false;
        }
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param context
     * @param key
     * @return modified:
     */
    public static Object readObject(String spkey, Context context, String key) {
        try {
            SharedPreferences sp = getInstance(spkey, context);
            if (sp.contains(key)) {
                String string = sp.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    byte[] base64 = Base64.decode(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(base64);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    return is.readObject();
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // �?��异常返回null
        return null;

    }
}
