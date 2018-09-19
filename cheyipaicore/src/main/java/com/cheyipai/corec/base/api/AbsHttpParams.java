package com.cheyipai.corec.base.api;


import java.util.Map;

/**
 * API请求基础数据封装基类，预定义
 * Created by yqh on 2016/5/9.
 */
public abstract class AbsHttpParams {

    public abstract Map initHttpHeader();

    public abstract Map initBasicsParams();

    public abstract String initDomain();

}
