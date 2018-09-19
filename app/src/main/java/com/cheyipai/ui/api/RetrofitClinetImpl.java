package com.cheyipai.ui.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cheyipai.core.base.api.HttpData;
import com.cheyipai.core.base.common.CypGlobalBaseInfo;
import com.cheyipai.core.base.retrofit.call.CoreBaseRetrofitCallBackResponse;
import com.cheyipai.core.base.retrofit.call.ICallBackResultCode;
import com.cheyipai.core.base.retrofit.call.ICallBackStatusCode;
import com.cheyipai.core.base.retrofit.net.CoreRetrofitClient;
import com.cheyipai.core.base.utils.CypAppUtils;
import com.cheyipai.ui.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaoshuai.
 * @PackageName com.cyp.p.retrofit.net
 * @date 2016-10-27 16:35
 * @description 网络请求实现类
 */
public class RetrofitClinetImpl {
    private static final String TAG = "RetrofitClinetImpl";
    private volatile static RetrofitClinetImpl instance;
    private static String url = BuildConfig.MOBILE_API;
    private static boolean isParseStateCode = false;
    private static boolean isCommonParams = true;
    private static boolean isLoading = true;
    private static boolean isUseHttps = true;
    private static Context mContext;

    private RetrofitClinetImpl() {
    }

    public static RetrofitClinetImpl getInstance(Context context) {
        initRetrofitSet(context);
        if (instance == null) {
            synchronized (RetrofitClinetImpl.class) {
                if (instance == null) {
                    instance = new RetrofitClinetImpl();
                }
                return instance;
            }
        }
        return instance;
    }

    private static void initRetrofitSet(Context context) {
        mContext = context;
        url = BuildConfig.MOBILE_API;
        isLoading = true;
        isParseStateCode = false;
        isCommonParams = true;
        if (url.startsWith("https://")) {
            isUseHttps = true;
        } else {
            isUseHttps = false;
        }

    }

    /**
     * set header
     *
     * @return Map
     */
    private Map<String, String> setHeader() {
        Map<String, String> header = new HashMap<>();
       // header.putAll(HttpData.getHeader());
       // header.putAll(new HttpParams().initHttpHeader());
        return header;
    }

    /**
     * common parameter
     *
     * @return Map
     */
    private Map<String, String> setCommonParameter() {
        Map<String, String> map = new HashMap<String, String>();

        return map;
    }

    /**
     * Whether to display the progress bar
     *
     * @param isloading
     */
    public RetrofitClinetImpl setRetrofitLoading(boolean isloading) {
        this.isLoading = isloading;
        return this;
    }

    /**
     * Whether to add commonParams
     *
     * @param bCommonParams
     */
    public RetrofitClinetImpl setRetrofitIsAddCommonParams(boolean bCommonParams) {
        this.isCommonParams = bCommonParams;
        return this;
    }

    /**
     * Whether to display the state code
     *
     * @param isParseStateCode
     */
    public RetrofitClinetImpl setRetrofitParseStateCode(boolean isParseStateCode) {
        this.isParseStateCode = isParseStateCode;
        return this;
    }

    /**
     * Whether to display the state code
     *
     * @param isUseHttps
     */
    public RetrofitClinetImpl setIsUseHttps(boolean isUseHttps) {
        this.isUseHttps = isUseHttps;
        return this;
    }

    /**
     * @param url
     */
    public RetrofitClinetImpl setRetrofitBaseURL(String url) {
        this.url = url;
        return this;
    }

    public CoreRetrofitClient newRetrofitClient() {
        Map<String, String> headerMap = setHeader();
        Map<String, String> commonMap = setCommonParameter();
        CoreRetrofitClient mRetrofitClient = new CoreRetrofitClient.Builder(mContext)
                .baseUrl(url)
                .addHeader(headerMap)
                .addCommonParameters(commonMap)
                .addLoading(isLoading)
                .addIsAddCommonParams(isCommonParams)
                .addParseStateCode(isParseStateCode)
                .addIsUseHttps(isUseHttps)
                .build();
        mRetrofitClient.setRetrofitCallBackStatusCode(new ICallBackStatusCode() {
            @Override
            public void getCallBackStatusCode(CoreBaseRetrofitCallBackResponse coreBaseRetrofitCallBackResponse) {
                new BaseRetrofitCallBackVM<CoreBaseRetrofitCallBackResponse>(mContext).onResponse(coreBaseRetrofitCallBackResponse);
            }
        });
        mRetrofitClient.setRetrofitCallBackResultCode(new ICallBackResultCode() {
            @Override
            public void getCallBackResultCode(String s, String s1) {
                Log.i(TAG, "getCallBackResultCode: " + s + "--" + s1);
                //判断是否掉线的状态
//                if (ReLoginManager.isReLogin(s)) {
//                    ReLoginManager.reLogin(mContext);
//                }
            }
        });
        return mRetrofitClient;
    }
}
