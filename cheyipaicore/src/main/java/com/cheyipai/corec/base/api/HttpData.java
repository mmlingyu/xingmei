package com.cheyipai.corec.base.api;

import com.cheyipai.corec.utils.AppInfoHelper;
import com.cheyipai.corec.utils.FilterMap;
import com.cheyipai.corec.utils.NetUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangqinghai on 16/05/11.
 */
public class HttpData {
    /**
     * 数据接口域名
     */
    public static String BASE_URL ;
    //http 基础请求参数
    private static FilterMap<String,String> map = new FilterMap<String,String>();
    //http 请求头
    private static HashMap<String, String> header = new HashMap<String, String>();
    public static HashMap<String, String> getHeader() {
        //app名称
        header.put("app", AppInfoHelper.APP_NAME);
        //设备唯一标示符，获取不到自动生成
        header.put("imei", Eutils.getDeviceId());
        //设备型号
       // header.put("ClientID",AppInfoHelper.DEVICE_MODEL);
        //设备操作系统
        header.put("os", AppInfoHelper.APP_OS);
        //设备操作系统版本
        header.put("sv", AppInfoHelper.DEVICE_VERSION);
//      版本代码 数字 每次递增
        header.put("vc", String.valueOf(AppInfoHelper.getAppVersionCode()));
        //客户端版本号
        header.put("version", AppInfoHelper.getAppVersionName());
        //网络状态，1=蜂窝网，2=WIFI
        header.put("n", AppInfoHelper.getNetType());
        //蜂窝网络提供商；0=无sim卡，1=移动、2=联通、3=电信 4=其他
        header.put("p", AppInfoHelper.getCellularType());
//        接口请求ID
        header.put("tid", Eutils.getDeviceId() + System.currentTimeMillis());
//        网络IP
        header.put("ip", NetUtil.getIP());
//        应用类型：app/浏览器
        header.put("at", "app");
//        安装的应用
       // map.filterPut("al", AppInfoHelper.ALL_APPS);
        //header.put("al", AppInfoHelper.ALL_APPS);
        return header;
    }

    /**
     * 设置请求头信息
     * @param headers
     */
    public static void setHttpHeader( Map<String,String> headers){
        header.putAll(headers);
    }

    //http 请求公共参数
    public static Map<String, String> getParams() {
        return map;
    }

}
