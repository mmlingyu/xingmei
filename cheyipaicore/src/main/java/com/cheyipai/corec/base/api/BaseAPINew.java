package com.cheyipai.corec.base.api;

import android.text.TextUtils;

import com.cheyipai.corec.log.L;
import com.cheyipai.corec.volley.NetworkError;
import com.cheyipai.corec.volley.Response;
import com.cheyipai.corec.volley.ServerError;
import com.cheyipai.corec.volley.TimeoutError;
import com.cheyipai.corec.volley.VolleyError;
import com.cheyipai.corec.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by daincly on 2014/11/3.
 */

public class BaseAPINew<T> {
    private StringRequest mStringRequest;
    private APIErrorListener mAPIErrorListener;
    private APIListenerFix mAPIListenerFix;
    public static final String TAG = BaseAPINew.class.getSimpleName();
    private AbsAPIRequestNew mRequest;

    /**
     * 设置空构造私有
     */
    private BaseAPINew() {
    }

    /**
     * 强约束,子类必须重写函数API 修订 request，response
     * 修订时间 2015-01-14 by wk
     * @param request
     */
    public BaseAPINew(AbsAPIRequestNew request) {
        mRequest = request;
        mAPIListenerFix = new APIListenerFix();
        mAPIErrorListener = new APIErrorListener();
        mStringRequest = new StringRequest(mRequest.getMethod(), getAPIUrl() , mRequest.getHeader(), getAllParams(), mAPIListenerFix, mAPIErrorListener);
        mStringRequest.setTag(request.getTag());
    }


    /**
     * 强约束,子类必须重写函数API 修订 request，response
     * 修订时间 2015-01-14 by wk
     * @param request
     */
    public BaseAPINew(AbsAPIRequestNew request, int requestMethod) {
        mRequest = request;
        mAPIListenerFix = new APIListenerFix();
        mAPIErrorListener = new APIErrorListener();
        mStringRequest = new StringRequest(mRequest.getMethodGet(), getAPIUrl() , mRequest.getHeader(), getAllParams(), mAPIListenerFix, mAPIErrorListener);
        mStringRequest.setTag(request.getTag());
    }

    /**
     * 返回 AbsAPIRequest
     * @return
     */
    public AbsAPIRequestNew getRequest() {
        return mRequest;
    }

    /**
     * 返回API 请求基础URL
     * @return
     */
    private String getAPIUrl() {
        String domain = HttpData.BASE_URL;
        if(!TextUtils.isEmpty(mRequest.getDomain())){
            domain = mRequest.getDomain();
        }
        return domain.concat(mRequest.getApi());
    }

    /**
     * 设置请求参数
     * @return
     */
    private Map<String, String> getAllParams() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.putAll(HttpData.getParams());
        if(mRequest.getJsonType()== AbsAPIRequestNew.HTTP_PARAM){
            map.putAll(mRequest.getParams());
            return map;
        }
        Map<String, String> tempMap = make(mRequest.getParams());
        if (tempMap != null) {
            map.putAll(tempMap);
        }
        return map;
    }


    /**
     *
     * @param reqMap
     * @return Map key:bparams
     */
    public static Map<String,String> make(Map<String,String> reqMap){
        Map<String,String> reqParamsMap = new HashMap<String,String>(1);
        if(reqMap == null || reqMap.isEmpty()) {
            reqParamsMap.put(APIParams.API_PARAMS,"{}");
            L.d(" api 参数:{}");
            return reqParamsMap;
        };
        Iterator<Map.Entry<String,String>> it=reqMap.entrySet().iterator();
        StringBuffer sb = new StringBuffer();

        sb.append("{");
        while(it.hasNext()){
            convert(reqParamsMap,sb,it.next());
        }
        sb = new StringBuffer(delprefix(sb));
        sb.append("}");
        L.d(" api 参数:"+sb.toString());
        reqParamsMap.put(APIParams.API_PARAMS,sb.toString());
        return reqParamsMap;
    }

    /**
     * 删除多余的逗号
     * @param
     * @return
     */
    private static String delprefix(StringBuffer sb){
        int i = sb.lastIndexOf(",");
        if(i<=0)return sb.toString();
        String str = sb.subSequence(0,i).toString();
        return  str;
    }

    /**
     * 请求格式格式转换
     * @return Map
     */
    private static void convert(Map<String,String> parmasMap, StringBuffer reqString, Map.Entry<String,String> reqEntry){
        reqString.append("\"").append(reqEntry.getKey()).append("\"")
                .append(":").append("\"").append(reqEntry.getValue()).append("\",");
    }


    StringRequest getAPIRequest() {
        return mStringRequest;
    }

    /**
     * 请求错误返回监听
     */
    private class APIErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {

            L.i("BaseAPI error response" + volleyError);
            ResponseData data = new ResponseData();

            if (volleyError instanceof TimeoutError) {
                data.StateDescription = "您的网络不给力啊，请稍后再试" ;
            } else if (volleyError instanceof ServerError) {
                data.StateDescription = "服务器不可用，请稍后再试";
            } else if (volleyError instanceof NetworkError) {
                data.StateDescription = "您的互联网好像没有连接，请连接之后再试";
            }

            data.resultCode = VolleyError.VOLLEY_ERROR;
            mRequest.onResponse(new Gson().toJson(data));
        }
    }

    /**
     * 请求返回监听
     * 网络API请求 数据返回
     * 修订 2014-01-14 by wk
     */
    private class APIListenerFix implements Response.Listener<String> {
        @Override
        public void onResponse(String responseData) {
            L.i("BaseAPI data response==" + responseData );
            mRequest.onResponse(responseData);
        }
    }

}