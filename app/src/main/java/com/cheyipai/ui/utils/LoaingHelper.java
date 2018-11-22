package com.cheyipai.ui.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyipai.ui.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LoaingHelper {

    private View view;
    private Dialog mLoadingDialog;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private TextView loadingText;
    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     */
    public void showDialogForLoading(Context context, String msg) {
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.dailog_loading_layout, null);
            mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
            avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.AVLoadingIndicatorView);
        }

        loadingText.setText(msg);

        if(!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
            avLoadingIndicatorView.smoothToShow();
            mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mLoadingDialog.hide();
                        return true;
                    }
                    return false;
                }
            });
        }

    }
    public void hideDailog() {
        if (mLoadingDialog == null) return;
        mLoadingDialog.hide();
    }
}
