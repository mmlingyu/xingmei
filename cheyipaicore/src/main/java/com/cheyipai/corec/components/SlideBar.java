package com.cheyipai.corec.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cheyipai.corec.R;

public class SlideBar extends View {
    //滑动选择所需的数据
    public String[] mData = {"全部", "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    //绘制正常text所需的paint
    private Paint mNormalTextPaint;

    //绘制选中text所需的paint
    private Paint mChosenTextPaint;

    //绘制背景所需的paint
    private Paint mBackgroundPaint;

    //选中item监听
    private OnTouchLetterChangeListenner mOnTouchLetterChangeListenner;

    //是否画出背景标识
    private boolean isShowBackground = false;

    //选中的item的index
    private int mChosenItem = -1;

    //控件的高
    private int mHeight;

    //控件的宽
    private int mWidth;

    //item的高
    private int mItemHeight;

    public SlideBar(Context context) {
        super(context);
        initPaint();
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mNormalTextPaint = new Paint();
        mNormalTextPaint.setColor(getResources().getColor(R.color.black));
        mNormalTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mNormalTextPaint.setAntiAlias(true);
        mNormalTextPaint.setTextSize(20f);

        mChosenTextPaint = new Paint();
        mChosenTextPaint.setColor(getResources().getColor(R.color.darkOrange));
        mChosenTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mChosenTextPaint.setAntiAlias(true);
        mChosenTextPaint.setTextSize(20f);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(getResources().getColor(R.color.transparency_white));
        mBackgroundPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mItemHeight = mHeight / mData.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isShowBackground) {
            canvas.drawPaint(mBackgroundPaint);
        }

        for (int i = 0; i < mData.length; i++) {
            float posX = mWidth / 2 - mNormalTextPaint.measureText(mData[i]) / 2;
            float posY = i * mItemHeight + mItemHeight / 2;

            if (i == mChosenItem) {
                canvas.drawText(mData[i], posX, posY, mChosenTextPaint);
            } else {
                canvas.drawText(mData[i], posX, posY, mNormalTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float y = event.getY();
        // 算出点击的字母的索引
        final int index = (int) (y / getHeight() * mData.length);
        // 保存上次点击的字母的索引到oldChoose
        final int oldChoose = mChosenItem;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isShowBackground = true;
                if (oldChoose != index && mOnTouchLetterChangeListenner != null && index < mData.length) {
                    mChosenItem = index;
                    mOnTouchLetterChangeListenner.onTouchLetterChange(event, mData[index], index);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (oldChoose != index && mOnTouchLetterChangeListenner != null && index < mData.length) {
                    mChosenItem = index;
                    mOnTouchLetterChangeListenner.onTouchLetterChange(event, mData[index], index);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
            default:
                isShowBackground = false;
                mChosenItem = -1;
                if (mOnTouchLetterChangeListenner != null && index < mData.length) {
                    mOnTouchLetterChangeListenner.onTouchLetterChange(event, mData[index], index);
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 设置滑动选择所需的数据
     *
     * @param data
     */
    public void setData(String[] data) {
        mData = data;
        invalidate();
    }

    /**
     * 设置绘制正常text所需的paint
     *
     * @param normalTextPaint
     */
    public void setNormalTextPaint(Paint normalTextPaint) {
        mNormalTextPaint = normalTextPaint;
        invalidate();
    }

    /**
     * 设置绘制被选中text所需的paint
     *
     * @param chosenTextPaint
     */
    public void setChosenTextPaint(Paint chosenTextPaint) {
        mChosenTextPaint = chosenTextPaint;
        invalidate();
    }

    /**
     * 设置绘制背景所需的paint
     *
     * @param backgroundPaint
     */
    public void setBackgroundPaint(Paint backgroundPaint) {
        mBackgroundPaint = backgroundPaint;
        invalidate();
    }

    /**
     * 设置是否需要绘制背景
     *
     * @param isShowBackground
     */
    public void setShowBackground(boolean isShowBackground) {
        this.isShowBackground = isShowBackground;
        invalidate();
    }

    /**
     * 回调方法，注册监听器
     *
     * @param listenner
     */
    public void setOnTouchLetterChangeListenner(OnTouchLetterChangeListenner listenner) {
        mOnTouchLetterChangeListenner = listenner;
    }

    /**
     * SlideBar 的监听器接口
     */
    public interface OnTouchLetterChangeListenner {
        void onTouchLetterChange(MotionEvent event, String s, int position);
    }

}