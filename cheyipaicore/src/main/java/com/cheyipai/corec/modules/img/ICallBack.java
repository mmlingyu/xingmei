package com.cheyipai.corec.modules.img;

import android.graphics.Bitmap;

/**
 * Created by jincan on 14-11-7.
 */
public interface ICallBack {
    void onSuccess(Bitmap bitmap);

    void onError();
}
