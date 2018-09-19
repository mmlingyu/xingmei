package com.cheyipai.corec.base.api;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.cheyipai.corec.log.L;
import com.cheyipai.corec.volley.Request;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wungko on 15/1/13.
 * API 请求
 */
public abstract class AbsAPIRequestNew<T extends Fragment, K extends ResponseData> {

    public static final int ERROR_DATA_PARSE = 0XFF10; //数据解析错误
    public static final String ERROR_OTHER_MSG = "好像有问题了";
    public static final int ERROR_ACITYTY_DESTROY = 0XFF11; //界面销毁中错误
    public static final String ERROR_ACITYTY_DESTROY_MSG = "界面销毁中";
    private int mJsonType = 0;//0:json 1:k:v
    private int mStatus;//请求状态
    public static final int JSON_PARAM = 1;//json类型
    public static final int HTTP_PARAM = 0;//k:v类型
    public WeakReference<T> mReference;

    private Object mTag;

    public AbsAPIRequestNew(T reference) {
        mReference = new WeakReference<T>(reference);
    }

    public AbsAPIRequestNew(T reference, int paramType) {
        mReference = new WeakReference<T>(reference);
        mJsonType = paramType;
    }

    protected int getJsonType() {
        return mJsonType;
    }

    public abstract Class<K> getClazz();

    /**
     * 默认需要界面回调
     *
     * @return
     */
    public boolean isNeedReference() {
        return true;
    }

    /**
     * 设置 API
     *
     * @return
     */
    public abstract String getApi();

    /**
     * 设置 发送请求方法
     *
     * @return
     */
    public int getMethod() {
        return Request.Method.POST;
    }

    /**
     * 获取Request tag，用于cancel请求
     * @return 调用请求的fragment
     */
    public Object getTag() {
        if (mTag == null) {
            return mReference.get();
        }
        return mTag;
    }

    /**
     * 设置请求tag
     * @param tag
     */
    public void setTag(Fragment tag) {
        mTag = tag;
    }

    /**
     * 设置请求tag
     * @param tag
     */
    public void setTag(String tag) {
        mTag = tag;
    }

    /**
     * 设置请求tag
     * @param tag
     */
    public void setTag(Object tag) {
        mTag = tag;
    }

    /**
     * 设置 发送请求方法
     *
     * @return
     */
    public int getMethodGet() {
        return Request.Method.GET;
    }

    public String getDomain() {
        return HttpData.BASE_URL;
    }

    /**
     * 设置请求header
     *
     * @return
     */
    public Map<String, String> getHeader() {
        return HttpData.getHeader();
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    private Map<String, String> map = new HashMap<String, String>();

    protected Map putParams(String key, String value) {
        if (TextUtils.isEmpty(value)) return map;
        map.put(key, value);
        return map;
    }

    /**
     * 无判断是否为空逻辑
     *
     * @param key
     * @param value
     * @return
     */
    protected Map putParam(String key, String value) {
        map.put(key, value);
        return map;
    }

    /**
     * 设置请求参数
     *
     * @return
     */
    public Map<String, String> getParams() {
        return map;
    }

    public void onResponse(String data) {
        T t = mReference.get();
        K k = null;
        if (isNeedReference()) { //需要页面回调
            if (t != null ) {
                if((t.getActivity() != null && t.getActivity().isFinishing())){
                    onError(t, ERROR_ACITYTY_DESTROY, ERROR_ACITYTY_DESTROY_MSG,k);
                    return;
                }
            }
        }

        try {
            //防止提供的数据class  和 返回的数据 不匹配导致数据不能解析而出现errorCode 被覆盖 从而导致不能识别服务端的 errorCode
            data = data.replace(",\"content\":[]", "");
            L.i("api==onResponse before paese ==" + data);
            k = new Gson().fromJson(data, getClazz());
            if (k == null || k.resultCode != APIParams.HTTP_RESPONE_STATUS_SUCCESS) {
                onError(t, k.resultCode, k.StateDescription,k);
                return;
            }
            L.i("api==onResponse==" + k.toString());
            onSuccess(t, k);
        } catch (Exception e) {
            e.printStackTrace();
            L.e("api==onResponse==onError==" + t.toString());
            onError(t, ERROR_DATA_PARSE, ERROR_OTHER_MSG,k);
        }
    }

    /**
     * byte转换成int
     * @param data
     * @return
     */
    private int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    public abstract void onSuccess(T t, K k);

    public abstract void onError(T t, int errorCode, String msg,K k);

}
