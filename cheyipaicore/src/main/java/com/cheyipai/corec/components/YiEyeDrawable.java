package com.cheyipai.corec.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;


import com.cheyipai.corec.R;

import java.io.InputStream;

/**
 * 小易下拉刷新眨眼睛动画
 * Created by GHY on 2016/4/15.
 */
public class YiEyeDrawable extends Drawable {

    private Bitmap bitmapYi;
    private Bitmap bitmapEye;

    private int centerX, centerY;

    private int small = 2;

    private Handler mHandler = new Handler();

    /**
     * 左侧眼睛坐标
     */
    private int eyeLeftX, eyeLeftY;

    /**
     * 存放左侧眼睛四个坐标的数组
     */
    private int[] leftEyeX = new int[4];
    private int[] leftEyeY = new int[4];

    public YiEyeDrawable(Context context) {
        bitmapYi = readBitMap(context, R.drawable.yi_no_eye);
        bitmapEye = readBitMap(context, R.drawable.yi_eye);

        //开启动画
        mHandler.postDelayed(runnable, 500);
    }

    private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        //参考点
        centerX = bounds.centerX() / 2;
        centerY = bounds.centerY() / 2;
        //左眼睛中心位置
        int eyeLocationCenterX = centerX + bitmapYi.getWidth() / 3 - bitmapEye.getWidth() / 2;
        int eyeLocationCenterY = bitmapYi.getHeight() / 2 - bitmapEye.getHeight() / 4;
        //左眼睛初始位置，为中心位置
        eyeLeftX = eyeLocationCenterX;
        eyeLeftY = eyeLocationCenterY;

        //左眼睛上方位置
        leftEyeX[0] = eyeLocationCenterX;
        leftEyeY[0] = eyeLocationCenterY - bitmapEye.getHeight() / 2 + small;

        //左眼睛底部位置
        leftEyeX[1] = eyeLocationCenterX;
        leftEyeY[1] = eyeLocationCenterY + bitmapEye.getHeight() / 2 - small;

        //左眼睛左侧位置
        leftEyeX[2] = eyeLocationCenterX - bitmapEye.getWidth() / 2;
        leftEyeY[2] = eyeLocationCenterY;

        //左眼睛右侧位置
        leftEyeX[3] = eyeLocationCenterX + bitmapEye.getWidth() / 2;
        leftEyeY[3] = eyeLocationCenterY;

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmapYi, centerX, 0, null);
        //绘制左侧眼睛
        canvas.drawBitmap(bitmapEye, eyeLeftX, eyeLeftY, null);
        //绘制右侧眼睛，注：右侧眼睛坐标完全参照左侧眼睛坐标
        canvas.drawBitmap(bitmapEye, eyeLeftX + bitmapYi.getWidth() / 3, eyeLeftY, null);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private int i = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            switch (i) {
                case 0:
                    eyeLeftX = leftEyeX[0];
                    eyeLeftY = leftEyeY[0];
                    i = 1;
                    break;
                case 1:
                    eyeLeftX = leftEyeX[1];
                    eyeLeftY = leftEyeY[1];
                    i = 2;
                    break;
                case 2:
                    eyeLeftX = leftEyeX[2];
                    eyeLeftY = leftEyeY[2];
                    i = 3;
                    break;
                case 3:
                    eyeLeftX = leftEyeX[3];
                    eyeLeftY = leftEyeY[3];
                    i = 0;
                    break;
            }
            mHandler.postDelayed(this, 250);
            invalidateSelf();
        }
    };
}
