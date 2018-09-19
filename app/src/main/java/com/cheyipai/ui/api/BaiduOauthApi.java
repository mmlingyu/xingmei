package com.cheyipai.ui.api;
import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.cheyipai.core.base.retrofit.net.CoreRetrofitClient;
import com.cheyipai.core.base.utils.SharedPrefersUtils;
import com.cheyipai.ui.bean.DownF3d;
import com.cheyipai.ui.bean.F3d;
import com.cheyipai.ui.bean.F3dCallback;
import com.cheyipai.ui.bean.F3dStatus;
import com.cheyipai.ui.bean.F3dUploadCallback;
import com.cheyipai.ui.bean.FaceInfo;
import com.cheyipai.ui.bean.Oauth;
import com.cheyipai.ui.bean.OauthBack;
import com.cheyipai.ui.bean.OauthCallback;
import com.cheyipai.ui.bean.OauthPlayer;
import com.cheyipai.ui.utils.Base64Util;
import com.cheyipai.ui.utils.FileUtil;
import com.cheyipai.ui.utils.GsonUtils;
import com.cheyipai.ui.utils.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;


/**
 * Created by Administrator on 2016/8/19.
 */
public class BaiduOauthApi {
    public static final MediaType urlencode = MediaType.parse("application/x-www-form-urlencoded;");
    private WeakReference<Context> weakcontext;
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;

    public BaiduOauthApi(Context context){
        this.weakcontext = new WeakReference<Context>(context);
    }
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).build();//设置连接超时时间

    /**
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public  String getAuth(final OauthBack oauthBack) throws IOException {
        // 官网获取的 API Key 更新为你注册的
        final String clientId = "0IVGjE6qyuCNAuczGRhI571H";
        // 官网获取的 Secret Key 更新为你注册的
        final String clientSecret = "vVVfdgSZ9oTFDFlfmUX7G9WC7TmGNMNW ";

            new Thread() {
                @Override
                public void run() {
                    try {
                        getAuth(clientId, clientSecret,oauthBack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }}.start();
        return null;
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public  void getAuth(String ak, String sk,OauthBack oauthBack) throws IOException, JSONException {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        Request request = new Request.Builder()
                .url(getAccessTokenUrl)
                .get()
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            String access_token = jsonObject.getString("access_token");
                oauthBack.onTokenSucc(access_token);
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public  void detect(String filePath,String accessToken, OauthBack oauthBack) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", Base64Util.encode(FileUtil.readFileByBytes(filePath)).toString());
            map.put("face_field", "age,beauty,expression,face_shape,gender,glasses,landmark,race,quality,face_type");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            FaceInfo faceInfo = new Gson().fromJson(result,FaceInfo.class);
            oauthBack.onOauthSucc(faceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void uploadUserFace(final File file, final String accessToken, final OauthBack oauthBack){
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                detect(file.getAbsolutePath(),accessToken,oauthBack);
            }
        }.start();

    }




    }
