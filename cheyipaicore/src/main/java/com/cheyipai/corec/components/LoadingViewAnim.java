package com.cheyipai.corec.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


import com.cheyipai.corec.R;

import java.io.InputStream;

/**
 * Created by GHY on 2015/12/01.
 * loading 动画
 */
public class LoadingViewAnim extends View {

    /**
     * 屏幕中心点坐标
     */
    private float mWidth;
    private float mHeight;
    /**
     * 小易弹跳的实时位置坐标
     */
    private float newBitmapX;
    private float newBitmapY;
    /**
     * 弹跳的初始参考点
     */
    private float highY;
    /**
     * 小易圆圈和小易图标
     */
    private Bitmap mLoadingCircle;
    private Bitmap mLoadingXiaoYi;
    /**
     * 画布旋转弧度
     */
    private int degrees;

    private Handler mHandler = new Handler();

    public LoadingViewAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBitMap();
        mHandler.postDelayed(runnable, 500);
    }

    /**
     * 读取图像
     */
    private void initBitMap() {
        mLoadingCircle = readBitMap(getContext(), R.drawable.loading_circle);
        mLoadingXiaoYi = readBitMap(getContext(), R.drawable.loading_xiaoyi);
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mWidth == 0 || mHeight == 0) {
            initLocation();
        }
        canvas.save();
        canvas.rotate(degrees, mWidth, mHeight);
        canvas.drawBitmap(mLoadingCircle, mWidth - mLoadingCircle.getWidth() / 2, mHeight - mLoadingCircle.getHeight() / 2, null);
        canvas.restore();
        getBitmapNewLocation();
        canvas.drawBitmap(mLoadingXiaoYi, newBitmapX, newBitmapY, null);
    }

    /**
     * 初始位置
     */
    private void initLocation() {
        mWidth = getWidth() / 2;
        mHeight = getHeight() / 2;
        //弹跳的初始参考点
        highY = mHeight - mLoadingXiaoYi.getHeight() / 2;
        //小易初始的位置
        newBitmapX = mWidth - mLoadingXiaoYi.getWidth() / 2;
        newBitmapY = highY;
    }

    /**
     * 画布旋转角度计算
     */
    private void getNewDegrees() {
        if (degrees == 360) {
            degrees = 0;
        } else {
            degrees += 3;
        }
    }

    /**
     * 小易图标位置计算
     */
    boolean isUp = true;//是否为向上弹跳
    float moveRate = 1.5f;//每次刷新移动的速率
    float moveHeight = 20f;//弹跳的高度

    private void getBitmapNewLocation() {

        if (newBitmapY == highY - moveHeight) {
            //在最高点，向下弹跳
            isUp = false;
            newBitmapY = newBitmapY + moveRate;
        } else if (newBitmapY == highY + moveHeight) {
            //在最低点，向上弹跳
            isUp = true;
            newBitmapY = newBitmapY - moveRate;
        } else {
            //弹跳过程中
            newBitmapY = (isUp) ? newBitmapY - moveRate : newBitmapY + moveRate;
        }

        if ((newBitmapY < highY - moveHeight) || (newBitmapY > highY + moveHeight)) {
            //小易越界
            newBitmapY = (isUp) ? highY - moveHeight : highY + moveHeight;
        }

    }

    //刷新运动
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                mHandler.postDelayed(this, 10);
                getNewDegrees();
                getBitmapNewLocation();
                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void clear() {
        if (mHandler != null && runnable != null) {
            mHandler.removeCallbacks(runnable);
        }
    }

}
