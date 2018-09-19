package com.cheyipai.corec.components.dialog;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;


import com.cheyipai.corec.R;

import java.io.InputStream;

/**
 * Created by GHY on 2016/4/18.
 * 加载中loading转圈动画
 */
public class LoadingDrawable extends Drawable {

    private Bitmap bitmapLoading;

    private float degree = 0;

    private int centerX, centerY;

    private int width, height;

    public LoadingDrawable(Context context) {
        bitmapLoading = readBitMap(context, R.drawable.loading);
        //开启动画
        startAnimation();
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
        centerX = bounds.width()/2;
        centerY = bounds.height()/2;
        width = bounds.width()/2 - bitmapLoading.getWidth() / 2;
        height = bounds.height()/2 - bitmapLoading.getHeight() / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.rotate(degree, centerX, centerY);
        canvas.drawBitmap(bitmapLoading, width, height, null);
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

    public class FloatEvaluator implements TypeEvaluator<Float> {
        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue) {
            return (endValue - startValue) * fraction;
        }
    }

    private void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofObject(new FloatEvaluator(), 0f, 360f);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (Float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
    }

}
