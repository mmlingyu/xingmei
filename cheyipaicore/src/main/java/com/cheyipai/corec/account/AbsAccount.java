package com.cheyipai.corec.account;

/**
 * 账户基类
 * Created by yqh on 2016/5/10.
 */
public abstract class AbsAccount {

    //    用户禁用
    public static final int REFUSE_TYPE_FORBIDDEN = 10001;
    //    修改密码
    public static final int REFUSE_TYPE_PWD_CHANGED = 10002;
    //    	被踢
    public static final int REFUSE_TYPE_FORCED =  10003 ;
    //    已在别处登录
    public static final int REFUSE_TYPE_LOGIN_ALLOPATRY =  10004 ;
    //    session过期
    public static final int REFUSE_TYPE_INVALID =  10005 ;



    /**
     * 用户登出信息处理
     */
    public abstract void cleanAccount();
    /**
     * 用户登录信息处理
     */
    public void signInAccount(){

    }

}
