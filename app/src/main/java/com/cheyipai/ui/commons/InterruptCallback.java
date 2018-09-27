package com.cheyipai.ui.commons;


import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
/**
 * @author: chpchen
 * @Filename:
 * @Description:
 */

public abstract class InterruptCallback implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {

    }

    @Override
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {

    }
}
