package com.cheyipai.corec.components.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheyipai.corec.R;


/**
 * Created by GHY on 2015/11/25.
 * 加载中Dialog
 */
public class XMLoadingDialogs extends Dialog {

    private Context context;

    /*
    * 加载状态描述
    * */
    private TextView tvLoadingStatus;

    /**
     * 显示自定义dialog
     * status:1 加载中的状态
     * status:2 加载成功的状态
     * status:3 加载失败的状态
     */
    public static final int STATUS_LAODING = 1;
    public static final int STATUS_LAODING_SUCCESS = 2;
    public static final int STATUS_LAODING_ERROR = 3;

    public XMLoadingDialogs(Context context) {
        super(context, R.style.LoadingDialog);
        this.context = context;
        createView();
    }

    private void createView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.xingmei_loading_dialog, null);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        initView(contentView);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    private void initView(View contentView) {
        tvLoadingStatus = (TextView) contentView.findViewById(R.id.tv_loading_status);
    }

    /*
    * 自定义加载中
    * */
    public void loadingCustom(String content) {
        tvLoadingStatus.setText(content);
    }

    public void setDialogStatus(int status, String content) {
        switch (status) {
            case 1:
                loadingCustom(content);
                break;
            case 2:
                loadingSuccess(content);
                break;
            case 3:
                loadingError(content);
                break;
        }
    }

    /*
    * 加载失败
    * */
    public void loadingError(String status) {

        tvLoadingStatus.setText(status);
    }

    /*
   * 加载成功
   * */
    public void loadingSuccess(String status) {
        tvLoadingStatus.setText(status);
    }

    public void dialogDismiss() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);
    }
}
