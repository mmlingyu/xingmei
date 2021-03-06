package com.cheyipai.ui.utils;
;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheyipai.ui.CheyipaiApplication;
import com.cheyipai.ui.R;
import com.cheyipai.ui.view.ShadowDrawable;
import com.wang.avi.AVLoadingIndicatorView;


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

    public static void setShapeDrawable(View view){
        ShadowDrawable.setShadowDrawable(view, new int[] {
                        Color.parseColor("#ffffff")}, dpToPx(5),
                Color.parseColor("#99999999"), dpToPx(5), dpToPx(2), dpToPx(2));

    }

    public static int dpToPx(int dp) {
        return (int) (CheyipaiApplication.getInstance().getResources().getSystem().getDisplayMetrics().density * dp + 0.5f);
    }
}
