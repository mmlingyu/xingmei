package com.cheyipai.ui;

import android.support.v4.app.Fragment;

import com.cheyipai.corec.base.api.APIBasicsHelper;
import com.cheyipai.corec.base.api.AbsAPIRequestNew;


/**
 * API助手类
 * Created by yqh on 2016/5/9.
 * ---------------------------
 * 增加取消请求方法
 * Updated by JunyiZhou on 2016/6/23
 */
public class APIHelper {
    /**
     * http头信息实例
     */
    private static HttpParams mHttpParamsUnGzip = new HttpParams(false);
    /**
     * http头信息实例
     */
    private static HttpParams mHttpParams = new HttpParams();

    /**
     * 默认POST方式发送API请求
     * @param absAPIRequestNew
     */
    public static void putAPI(AbsAPIRequestNew absAPIRequestNew){
        if(mHttpParams == null){
            mHttpParams = new HttpParams();
        }
        APIBasicsHelper.getInstance(mHttpParams).putNewAPI(absAPIRequestNew);
    }
    /**
     * 默认POST方式发送API请求，非Gzip压缩
     * @param absAPIRequestNew
     */
    public static void putAPIUnGzip(AbsAPIRequestNew absAPIRequestNew){
        if(mHttpParamsUnGzip == null){
            mHttpParamsUnGzip = new HttpParams(false);
        }
        APIBasicsHelper.getInstance(mHttpParamsUnGzip).putNewAPI(absAPIRequestNew);
    }
    /**
     * GET方式发送API请求
     * @param absAPIRequestNew
     */
    public static void putGetAPI(AbsAPIRequestNew absAPIRequestNew){
        if(mHttpParams == null){
            mHttpParams = new HttpParams();
        }
        APIBasicsHelper.getInstance(mHttpParams).putNewAPIGet(absAPIRequestNew);
    }
    /**
     * GET方式发送API请求，非Gzip压缩
     * @param absAPIRequestNew
     */
    public static void putGetAPIUnGzip(AbsAPIRequestNew absAPIRequestNew){
        if(mHttpParamsUnGzip == null){
            mHttpParamsUnGzip = new HttpParams();
        }
        APIBasicsHelper.getInstance(mHttpParamsUnGzip).putNewAPIGet(absAPIRequestNew);
    }

    /**
     * 取消请求
     * @param tag
     */
    public static void cancelRequest(Fragment tag){
        APIBasicsHelper.getInstance(mHttpParams).cancelAPI(tag);
    }

    /**
     * 取消请求
     * @param tag
     */
    public static void cancelRequest(String tag){
        APIBasicsHelper.getInstance(mHttpParams).cancelAPI(tag);
    }

    /**
     * 取消请求
     * @param tag
     */
    public static void cancelRequest(Object tag){
        APIBasicsHelper.getInstance(mHttpParams).cancelAPI(tag);
    }

    /**
     * 清空网络助手缓存信息
     */
    public static void cleanHttpParams(){
        APIBasicsHelper.getInstance(mHttpParams).clean();
        mHttpParamsUnGzip = null;
        mHttpParams = null;
    }

}
