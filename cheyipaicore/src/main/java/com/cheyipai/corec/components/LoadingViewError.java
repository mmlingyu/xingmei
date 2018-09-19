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
 * 无网络加载失败页面
 */
public class LoadingViewError extends View {

    private float mWidth;
    private float mHeight;
    private Bitmap mLoadingCircle;
    private Bitmap mLoadingXiaoYi;
    private Bitmap mTextRefresh;
    private Bitmap mTextNetworkError;
    private Handler mHandler1 = new Handler();
    private Handler mHandler2 = new Handler();

    private float textRefreshX;
    private float textRefreshY;
    private float textNoNetX;
    private float textNoNetY;
    private float textRefreshYCenter;
    private float textNoNetYCenter;
    private float textRefreshYUp;
    private float textNoNetYDown;

    /**
     * 刷新状态type
     */
    public static int TYPE = 0;
    public static final int TYPE_LOADING_DEFAULT = 0;
    public static final int TYPE_LOADING_TEXT_MOVE = 1;

    public LoadingViewError(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载图像
        initBitMap();
    }

    /**
     * 开始刷新动画
     */
    public void startLoading() {
        //获取初始值
        getViewDefaultLocation();
        if (textRefreshY == textRefreshYCenter && textNoNetY == textNoNetYCenter) {
            //文字退出动画开启
            TYPE = TYPE_LOADING_TEXT_MOVE;
            mHandler2.postDelayed(runnable2, 0);
        }
    }

    /**
     * 停止刷新动画
     */
    public void stopLoading() {
        //获取初始值
        getViewDefaultLocation();
        TYPE = TYPE_LOADING_TEXT_MOVE;
        //文字进入动画开启
        mHandler1.postDelayed(runnable1, 600);
    }

    /**
     * 读取图像
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 加载图像
     */
    private void initBitMap() {
        mLoadingCircle = readBitMap(getContext(), R.drawable.loading_circle);
        mLoadingXiaoYi = readBitMap(getContext(), R.drawable.loading_xiaoyi);
        mTextRefresh = readBitMap(getContext(), R.drawable.loading_error_click_me_refresh);
        mTextNetworkError = readBitMap(getContext(), R.drawable.loading_error_network_error);
    }

    /**
     * View的初始位置
     */
    private void getViewDefaultLocation() {
        mWidth = getWidth() / 2;
        mHeight = getHeight() / 2;
        //文字在顶部居中
        textRefreshX = mWidth - mTextRefresh.getWidth() / 2;
        textRefreshY = 0 - mTextRefresh.getHeight();
        textRefreshYUp = textRefreshY;
        //文字在底部居中
        textNoNetX = mWidth - mTextNetworkError.getWidth() / 2;
        textNoNetY = mHeight * 2 + mTextNetworkError.getHeight();
        textNoNetYDown = textNoNetY;
        //文字中间位置
        textRefreshYCenter = mHeight - mTextRefresh.getHeight() / 2 - mLoadingCircle.getHeight();
        textNoNetYCenter = mHeight - mTextNetworkError.getHeight() / 2 + mLoadingCircle.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mWidth == 0 || mHeight == 0) {
            getViewDefaultLocation();
        }
        switch (TYPE) {
            case TYPE_LOADING_TEXT_MOVE:
                startDrawTextAnim(canvas);
                break;
            case TYPE_LOADING_DEFAULT:
                onDrawCenterDefault(canvas);
                break;
        }
    }

    /**
     * 文字移动动画
     *
     * @param canvas
     */
    private void startDrawTextAnim(Canvas canvas) {
        onDrawCenterDefault(canvas);
        canvas.drawBitmap(mTextRefresh, textRefreshX, textRefreshY, null);
        canvas.drawBitmap(mTextNetworkError, textNoNetX, textNoNetY, null);
    }

    /**
     * 文字默认位置
     *
     * @param canvas
     */
    private void onDrawCenterDefault(Canvas canvas) {
        canvas.drawBitmap(mLoadingCircle, mWidth - mLoadingCircle.getWidth() / 2, mHeight - mLoadingCircle.getHeight() / 2, null);
        canvas.drawBitmap(mLoadingXiaoYi, mWidth - mLoadingXiaoYi.getWidth() / 2, mHeight - mLoadingXiaoYi.getHeight() / 2, null);
    }

    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            try {
                mHandler1.postDelayed(this, 10);
                getTextDownLocation();
                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            try {
                mHandler2.postDelayed(this, 10);
                getTextUpLocation();
                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 计算文字的距离
     */
    private void getTextDownLocation() {

        if (textRefreshY < textRefreshYCenter) {
            textRefreshY += 60;
        } else {
            textRefreshY = textRefreshYCenter;
        }

        if (textNoNetY > textNoNetYCenter) {
            textNoNetY -= 60;
        } else {
            textNoNetY = textNoNetYCenter;
        }

        if (textRefreshY == textRefreshYCenter && textNoNetY == textNoNetYCenter) {
            //停止文字进入动画
            mHandler1.removeCallbacks(runnable1);
        }
    }

    /**
     * 计算文字的距离
     */
    private void getTextUpLocation() {

        if (textRefreshY > textRefreshYUp) {
            textRefreshY -= 80;
        } else {
            textRefreshY = textRefreshYUp;
        }

        if (textNoNetY < textNoNetYDown) {
            textNoNetY += 80;
        } else {
            textNoNetY = textNoNetYDown;
        }

        if (textRefreshY == textRefreshYUp && textNoNetY == textNoNetYDown) {
            //停止文字退出动画
            mHandler2.removeCallbacks(runnable2);
        }
    }

    public void clear() {
        if (mHandler1 != null && runnable1 != null) {
            mHandler1.removeCallbacks(runnable1);
        }
        if (mHandler2 != null && runnable2 != null) {
            mHandler2.removeCallbacks(runnable2);
        }
    }

}
