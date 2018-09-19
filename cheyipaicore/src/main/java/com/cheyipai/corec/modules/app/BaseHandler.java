/*
 * WeakReferenceHandler.java
 * classes : com.baidu.netdisk.util.WeakReferenceHandler
 * @author chenyuquan
 * V 1.0.0
 * Create at 2013-4-23 下午6:36:17
 */
package com.cheyipai.corec.modules.app;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by daincly on 2014/10/22.
 */
public abstract class BaseHandler<T> extends Handler {

    private static final String TAG = BaseHandler.class.getSimpleName();

    private WeakReference<T> mReference;

    public BaseHandler(T reference) {
        mReference = new WeakReference<T>(reference);
    }

    public BaseHandler(T reference, Looper looper) {
        super(looper);
        mReference = new WeakReference<T>(reference);
    }

    @Override
    public final void handleMessage(Message msg) {
        T t = mReference.get();
        if (t == null || msg == null) {
            return;
        }
        handleMessage(t, msg);
    }

    protected abstract void handleMessage(T reference, Message msg);

    public static Message getMessage(){
       return Message.obtain();
    }

}

