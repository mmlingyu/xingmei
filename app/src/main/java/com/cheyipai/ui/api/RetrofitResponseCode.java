package com.cheyipai.ui.api;

/**
 * @author shaoshuai.
 * @PackageName com.cyp.p.retrofit.common
 * @date 2016-09-26 15:07
 * @description return response code
 */
public class RetrofitResponseCode {

    public final static String CALL_BACK_FAILED = "00000";//失败
    public final static String CALL_BACK_VALIDCODE_ERROR = "00001";//您的短信验证码输入错误，请重新输入
    public final static String CALL_BACK_VALIDCODE_TIMES_MUCH = "00004";//获取动态验证码次数太多
    public final static String CALL_BACK_SUCCESS = "10000";//成功
    public final static String CALL_BACK_ACCOUNT_UNAVAILABLE = "10001";//您的账号不可用，无法登录！如有问题请联系400-0268-116
    public final static String CALL_BACK_OTHER_DEVIEC_LOGIN = "10004";//您的账号在另一台设备登录，确认强行登录吗？
    public final static String CALL_BACK_UNREGIST = "10006";//账号尚未注册，请先进行注册，或使用手机号快捷登录
    public final static String CALL_BACK_PASSWORD_ERROR = "10007";//密码输入有误，请重新输入
    public final static String CALL_BACK_PASSWORD_ERROR_MORE_THAN_THIRD_TIEMS = "10008";//密码输入错误大于等于3次,并且验证码为空
    public final static String CALL_BACK_VERIFICATION_PHONE = "10010";//该手机号已注册，验证手机号后可直接登录
    public final static String CALL_BACK_PASSWORD_ERROR_THIRD_TIMES = "10013";//密码输入错误3次,弹框提示
    public final static String CALL_BACK_OFFLINE = "10014";//下线
    public final static String CALL_BACK_DISABLE = "10015";//您的账号已被禁用
    public final static String CALL_BACK_NEED_AUTHENTICATION = "10016";//您的账号在新设备上第一次登陆，需要进行身份验证

}
