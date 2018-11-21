package com.cheyipai.ui;
import com.cheyipai.corec.base.api.AbsHttpParams;
import com.cheyipai.corec.log.L;
import com.cheyipai.corec.utils.AppInfoHelper;
import com.cheyipai.corec.utils.DeviceUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求头信息，业务基础头信息
 * Created by yqh on 2016/5/9.
 */
public class HttpParams extends AbsHttpParams {
    /**
     * 竞品
     */
    public static String ACHIEVE_COMPETITORS = "";

    /**
     * 是否使用gzip压缩
     */
    private boolean mGzip = true;

    public HttpParams() {
    }

    public HttpParams(boolean gzip) {
        mGzip = gzip;
    }

    @Override
    public Map initHttpHeader() {
        HashMap<String, String> header = new HashMap<String, String>();
        //app名称
        header.put("app", AppInfoHelper.APP_NAME);
        if (mGzip) {
            header.put("Accept-Encoding", "gzip");
        }
//        用户在线id onlineid

//        压缩标识，0-未压缩，1-压缩
        int gzip = mGzip ? 1 : 0;
        header.put("z", String.valueOf(mGzip ? 1 : 0));
//        经度,纬度 逗号分隔
//        获取经度
//        应用类型：app/浏览器
        header.put("at", "app");
//        打开类型 0-自身打开 1-H5打开
        header.put("ow", "0");
//        分辨率 1280_800
        /*header.put("sc",
                String.valueOf(DeviceUtils.getScreenHeight()).concat("_").concat(String.valueOf(DeviceUtils.getScreenWidth())));*/

//        加密标识，0-未加密 ，1-加密
        header.put("e", "1");
//        header.put("al", ACHIEVE_COMPETITORS);

        try {
            String al = URLEncoder.encode(ACHIEVE_COMPETITORS, "UTF-8");
            header.put("al", al);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        L.i("initHttpHeader==" + header.toString());
        return header;
    }


    @Override
    public Map initBasicsParams() {
        return null;
    }

    @Override
    public String initDomain() {
        return null;
    }
}