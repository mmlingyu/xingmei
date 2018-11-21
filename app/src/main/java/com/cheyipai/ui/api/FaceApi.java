package com.cheyipai.ui.api;

import android.content.Context;

import com.cheyipai.core.base.retrofit.net.CoreRetrofitClient;
import com.cheyipai.corec.log.L;
import com.cheyipai.ui.bean.FaceInfo;
import com.cheyipai.ui.bean.FaceSegment;
import com.cheyipai.ui.bean.FaceSegmentBack;
import com.cheyipai.ui.bean.Oauth;
import com.cheyipai.ui.bean.OauthBack;
import com.cheyipai.ui.utils.Base64Util;
import com.cheyipai.ui.utils.FileUtil;
import com.cheyipai.ui.utils.GsonUtils;
import com.cheyipai.ui.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

public class FaceApi extends BaseApi {
    private static final String FACE_SEGMENT="https://api-cn.faceplusplus.com/humanbodypp/v2/segment";
    private static final String api_key="uIEJ43lzXq8CCChZdvmiuWsqaQFcz9EG";
    private static final String api_secret="HNH84UEnP8b46kPFMVv3LCyIsvCYWNT9";
    private static final String BaseFaceUrl="https://api-cn.faceplusplus.com/";
    private WeakReference<Context> weakReference;
    public FaceApi(Context context){
        weakReference = new WeakReference<>(context);
    }
    public  void detect(String fileByte,FaceSegmentBack faceSegmentBack) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("api_key", api_key);
            map.put("api_secret", api_secret);
            map.put("image_base64", fileByte);
            RetrofitClinetImpl.getInstance(weakReference.get()).setIsUseHttps(true)
                    .setRetrofitBaseURL(BaseFaceUrl)
                    .newRetrofitClient()
                    .postL("humanbodypp/v2/segment", map, new CoreRetrofitClient.ResponseCallBack<ResponseBody>() {
                        @Override
                        public void onSucceess(ResponseBody responseBody) {
                            try {
                                FaceSegment faceInfo = new Gson().fromJson(new String(responseBody.bytes()),FaceSegment.class);
                                faceSegmentBack.onFaceSegmentSucc(faceInfo);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Throwable throwable) {
                            L.e("onFailure",throwable.getLocalizedMessage());
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
