package com.cheyipai.corec.base.api;

import com.cheyipai.corec.log.L;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by wungko on 14/11/12.
 */
public abstract class AbsAPIResponse<T extends ResponseData> {
    public abstract void onSuccess(T t); //todo wangke onResponse
    public abstract void onError(int errorCode,String msg); //todo wangke del
    public abstract Class<T> getClazz();

    public void setResponse(String data){
        try {
            T t  = new Gson().fromJson(data, getClazz());
            if (t.resultCode !=  APIParams.HTTP_RESPONE_STATUS_SUCCESS ) {
                onError(t.resultCode, t.StateDescription);
                return;
            }
            onSuccess(t);
        } catch (JsonSyntaxException e) {
            L.e("--------------------------------------------------------");
            e.printStackTrace();
            onError(-1,"数据解析异常"+ data);
            L.e("--------------------------------------------------------");
        }
    }

    /**
     * do something before response data
     * @param t
     * @return
     */
    protected  T beforeOnSuccess(T t){
        return t;
    }

}
