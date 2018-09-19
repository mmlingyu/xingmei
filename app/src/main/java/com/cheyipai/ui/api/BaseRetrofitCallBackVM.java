package com.cheyipai.ui.api;

import android.content.Context;
import android.text.TextUtils;

import com.cheyipai.core.base.retrofit.call.CoreBaseRetrofitCallBackResponse;

/**
 * @author shaoshuai.
 * @PackageName com.cyp.p.retrofit.model
 * @date 2016-09-26 11:33
 * @description retrofit model
 */
public class BaseRetrofitCallBackVM<T extends CoreBaseRetrofitCallBackResponse> {

    private Context mContext;

    public BaseRetrofitCallBackVM(Context context) {
        this.mContext = context;
    }

    public void onResponse(T response) {

        String resultCode = response.getResultCode();

        if (!TextUtils.isEmpty(resultCode)) {

            if (resultCode.equals(RetrofitResponseCode.CALL_BACK_SUCCESS)) {
                //成功
                //DialogUtils.showToast(mContext, "" + response.getResultCode().toString());
            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_FAILED)) {
                //失败
            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_VALIDCODE_ERROR)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_VALIDCODE_TIMES_MUCH)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_ACCOUNT_UNAVAILABLE)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_OTHER_DEVIEC_LOGIN)) {
                //强登
                onReLogin();
            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_UNREGIST)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_PASSWORD_ERROR)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_PASSWORD_ERROR_THIRD_TIMES)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_PASSWORD_ERROR_MORE_THAN_THIRD_TIEMS)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_VERIFICATION_PHONE)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_OFFLINE)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_DISABLE)) {

            } else if (resultCode.equals(RetrofitResponseCode.CALL_BACK_NEED_AUTHENTICATION)) {

            }
        }
    }

    /**
     * 登录
     */
    public void onReLogin() {

    }
}
