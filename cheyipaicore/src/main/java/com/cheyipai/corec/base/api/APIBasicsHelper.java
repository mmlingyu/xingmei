package com.cheyipai.corec.base.api;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.cheyipai.corec.log.L;
import com.cheyipai.corec.modules.app.BaseApplication;
import com.cheyipai.corec.utils.AppInfoHelper;
import com.cheyipai.corec.volley.RequestQueue;
import com.cheyipai.corec.volley.toolbox.Volley;

/**
 * Created by wungko on 14/11/5.
 */
public class APIBasicsHelper {
    private RequestQueue mRequestQueue;
    private Context mContext;

    private static APIBasicsHelper sInstance;

    private APIBasicsHelper(Context context){
        mContext = context;
        mRequestQueue = new Volley().newRequestQueue(mContext);
        mRequestQueue.start();
    }

    public static APIBasicsHelper getInstance(AbsHttpParams httpParams){
        if (sInstance == null) {
            synchronized (APIBasicsHelper.class) {
                if (sInstance == null) {
                    sInstance = new APIBasicsHelper(BaseApplication.getApplication());
                }
            }
        }
        if(httpParams != null){
            HttpData.setHttpHeader(httpParams.initHttpHeader());
            HttpData.BASE_URL = httpParams.initDomain();
        }
        return sInstance;
    }

    /**
     * request 和 response 整合 api
     * 发送API
     * @param absAPIRequestNew
     */
    public void putNewAPI(AbsAPIRequestNew absAPIRequestNew) {
        L.i("putNewAPI==" + absAPIRequestNew);
        if (AppInfoHelper.isMobileConnected() || AppInfoHelper.isWifiConnected()) {
            BaseAPINew baseAPI = new BaseAPINew(absAPIRequestNew);
            mRequestQueue.add(baseAPI.getAPIRequest());
        } else {
            absAPIRequestNew.onError((Fragment)absAPIRequestNew.mReference.get(),
                    -1, "您的互联网好像没有连接，请连接之后再试",null);
        }
    }

    public void putNewAPIGet(AbsAPIRequestNew absAPIRequestNew) {
        L.i("putNewAPI==" + absAPIRequestNew);
        if (AppInfoHelper.isMobileConnected() || AppInfoHelper.isWifiConnected()) {
            BaseAPINew baseAPI = new BaseAPINew(absAPIRequestNew,absAPIRequestNew.getMethodGet());
            mRequestQueue.add(baseAPI.getAPIRequest());
        } else {
            absAPIRequestNew.onError((Fragment)absAPIRequestNew.mReference.get(),
                    -1, "您的互联网好像没有连接，请连接之后再试",null);
        }
    }

    /**
     * 自定义Object作为请求的tag
     * @param tag
     */
    public void cancelAPI(Object tag) {
        if (tag == null) {
            L.e("tag cannot be null");
            return;
        }
        mRequestQueue.cancelAll(tag);
    }

    /**
     * 清除API助手缓存信息
     */
    public void clean(){
        if(mRequestQueue != null){
            mRequestQueue.stop();
            mRequestQueue= null;
        }
        sInstance = null;
    }
}
