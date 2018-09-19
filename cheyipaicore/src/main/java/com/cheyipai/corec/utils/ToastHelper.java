package com.cheyipai.corec.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cheyipai.corec.modules.app.BaseApplication;

/**
 * 创建于 2014/4/17
 *
 * @author yangqinghai
 */
public class ToastHelper {

    private static ToastHelper mInstance;
    /**
     * toast实例
     */
    private Toast mToast;
    /**
     * toast 内容TextView
     */
    private TextView mContentTV;
    /**
     * toast 自定义View
     */
    private View mToastCustomView;

    private ToastHelper() {
    }

    /**
     * 获取Toast助手类实例
     *
     * @return
     */
    public static ToastHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ToastHelper();
        }
        return mInstance;
    }

//    /**
//     * 显示toast内容
//     *
//     * @param content：内容
//     * @return
//     */
//    public Toast showToast(String content) {
////        View layout = initToastView(content);
////        if (layout == null){
////            return null;
////        }
//        mToast = initToast();
//        mToast.show();
//        mToastCustomView = null;
//        return mToast;
//    }


    /**
     * 显示toast内容
     *
     * @param content：内容
     * @return
     */
    public Toast showToast(String content) {
//        View layout = initToastView(content);
//        if (layout == null){
//            return null;
//        }
        if(mToast == null){
            mToast = Toast.makeText(BaseApplication.getApplication().getTopActivity(),content, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(content);
        }
        mToast.show();
        return mToast;
    }
    /**
     * 显示toast内容
     *
     * @param contentId：内容id
     * @return
     */
    public Toast showToast(int contentId){
        return showToast(BaseApplication.getApplication().getString(contentId));
    }

    /**
     * 初始化自定义toast界面
     *
     * @param content
     * @return
     */
//    private View initToastView(String content) {
//        if (mContentTV == null || mToastCustomView == null) {
//            if(BaseApplication.getApplication().getTopActivity() == null){
//                return mToastCustomView;
//            }
//            LayoutInflater inflater = BaseApplication.getApplication().getTopActivity().getLayoutInflater();
//            mToastCustomView = inflater.inflate(R.layout.toast_custom, null);
//            mContentTV = (TextView) mToastCustomView.findViewById(R.id.toast_tv_content);
//        }
//        mContentTV.setText(content);
//        return mToastCustomView;
//    }

    /**
     * 初始化自定义toast
     *
     * @param layout
     * @return
     */
    private Toast initToast(View layout) {
        if (mToast == null) {
            mToast = new Toast(BaseApplication.getApplication());
            mToast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
//        mToast.setView(layout);
        return mToast;
    }
    /**
     * 初始化 toast
     * @return
     */
    private Toast initToast() {
        if (mToast == null) {
            mToast = new Toast(BaseApplication.getApplication());
            mToast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
//        mToast.setView(layout);
        return mToast;
    }
}
