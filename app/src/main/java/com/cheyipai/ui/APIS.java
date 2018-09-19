package com.cheyipai.ui;


/**
 * 服务器端接口定义
 *
 * @author jingtao
 *         init 2015.5.15
 *         update .... by ....
 */
public class APIS {

    /**
     * 用户操作日志
     */
    public static final String USER_ACTION_LOG = "/api/logs/record";

    /**
     * 优惠卷列表
     */
    public static final String USER_COUPON_LIST = "api/voucherListInfo";

    /**
     * 优惠卷列表
     */
    public static final String USER_PAY_DETAIL = "api/pay/detail";

    /**
     * 废弃收款单-取消支付
     */
    public static final String USER_PAY_CANCE="api/pay/cancelReceipt";

    /**
     * 钱包首页
     */
    public static final String USER_WALLER = "api/wallet";

    /**
     * 竞品列表
     */
    public static final String COMPETITOR_LIST = "api/getCompetitorList";

    /**
     * 车辆详情分享链接
     */
    public static final String CAR_DETAIL_SHARE = "PublicMethod/GetShareWeiXinUrl";

    /**
     * 直播车展列表
     * YQH 20160721
     */
    public static final String LIVEVIDEO_CAREXHIBIT = "api/livecarlist";

    /**
     * 云信用户登录
     * CCP 20160727
     */
    public static final String LIVEVIDEO_Login = "live/login";

    /**
     * 直播详情页车辆信息
     * CCP 20160728
     */
    public static final String LIVEVIDEO_Carinfo = "api/liveinfo";

    /**
     * 直播间退出
     * CCP 2016年9月12日
     */
    public static final String LIVEVIDEO_LOGOUT = "api/live/logout";

    /**
     * 发送礼物
     * CCP 2016年9月23日
     */
    public static final String LIVEVIDEO_SENDGIFT = "api/live/sendgift";

    /**
     * 猜你喜欢
     */
    public static final String CAR_SOURCE_SEARCH_LIKE = "Bazaar/GetSearchHotWord";


    /**
     * 根据搜索查询品牌 排量
     */
    public static final String CAR_SOURCE_SEARCH_BRAND = "Bazaar/GetFoxProSearchCondition";

    /**
     * 历史成交价
     */
    public static final String CAR_HISTORY_PRICE_SEARCH_LIST = "Pricesearch/GetPriceSearch";
    /**
     * 历史成交价
     */
    public static final String CAR_HISTORY_PRICE_SEARCH_FILTER = "PriceSearch/GetPriceFilter";
    /**
     * 历史成交价详情
     */
    public static final String CAR_HISTORY_PRICE_SEARCH_DETAIL = "Pricesearch/GetCarHistoryInfo";


    /**
     * 历史订单联想词
     */
    public static final String CAR_SERVICE_SEARCH_HOT_WORD = "user/GetSearchWord";


    /**
     * 语音识别
     */
    public static final String CAR_SOURCE_SEARCH_VOICE = "nlp/csgapi.do";
    /**
     * add sample data to train
     */
    public static final String CAR_SOURCE_TRAIN = "nlp/tainapi.do";

    /**
     * http://wiki.cheyipai.com/pages/viewpage.action?pageId=2163265
     * 验证码发送接口(语音)
     */
    public static final String API_CAPTCHA_SEND = "api/code/sendCode";

    /**
     * http://wiki.cheyipai.com/pages/viewpage.action?pageId=2163454
     * 校验验证码
     */
    public static final String API_CODE_CHECKCODE = "api/code/checkCode";

}
