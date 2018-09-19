package com.cheyipai.ui.utils;
;
import android.content.Context;
import android.widget.Toast;


/**
 * @ClassName DialogUtils
 * @Description 构建常用对话框工具包
 */
public class DialogUtils {
    /**
     * @param context
     * @param message
     * @Method showToast
     * @Description 构建小提示对话框
     */
    public final static void showToast(final Context context,
                                       final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @param message
     * @Title: showToastLong
     * @Description: 构建长时间提示Toast
     * @return: void
     */
    public final static void showLongToast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
