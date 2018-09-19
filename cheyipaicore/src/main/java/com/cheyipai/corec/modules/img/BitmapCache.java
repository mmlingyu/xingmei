package com.cheyipai.corec.modules.img;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.cheyipai.corec.log.L;
import com.cheyipai.corec.volley.toolbox.ImageLoader.ImageCache;

/**
 * Created by daincly on 2014/11/3.
 */

public class BitmapCache extends LruCache<String, Bitmap> implements ImageCache {


    private final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    private final static int maxSize = maxMemory / 5;

    public BitmapCache() {
        super(maxSize);
        L.i("BitmapCache", "maxMemory==" + maxMemory + "==maxSize==" + maxSize);
    }


    @Override
    public Bitmap getBitmap(String url) {
        return get(url);

    }

    @Override

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);

    }

}
