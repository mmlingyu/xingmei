package com.cheyipai.ui.bean;

/**
 * Created by Administrator on 2018/8/8.
 */

public interface OauthBack {
    public void onOauthSucc(FaceInfo faceInfo);
    public void onTokenSucc(String token);
}
