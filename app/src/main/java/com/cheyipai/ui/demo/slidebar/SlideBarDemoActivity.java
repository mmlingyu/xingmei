package com.cheyipai.ui.demo.slidebar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.components.SlideBar;
import com.cheyipai.ui.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SlideBar Demo
 * Create by JunyiZhou on 2016/6/12.
 */
public class SlideBarDemoActivity extends AbsBaseActivity {
    private final static String TITLE = "SlideBarDemo";
    private final static String TAG = SlideBarDemoActivity.class.getSimpleName();

    public String[] data = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    @BindView(R.id.demo_sb)
    SlideBar slideBar;

    @BindView(R.id.demo_slide_result_tv)
    TextView tvSlideResult;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_slide_bar_demo;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        Paint normalTextPaint = new Paint();
        normalTextPaint.setColor(getResources().getColor(com.cheyipai.corec.R.color.oilPriceBlue));
        normalTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        normalTextPaint.setAntiAlias(true);
        normalTextPaint.setTextSize(30f);

        Paint chosenTextPaint = new Paint();
        chosenTextPaint.setColor(getResources().getColor(com.cheyipai.corec.R.color.holoRed));
        chosenTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        chosenTextPaint.setAntiAlias(true);
        chosenTextPaint.setTextSize(40f);

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(com.cheyipai.corec.R.color.darkGray));
        backgroundPaint.setAntiAlias(true);

        slideBar.setData(data);
        slideBar.setNormalTextPaint(normalTextPaint);
        slideBar.setChosenTextPaint(chosenTextPaint);
        slideBar.setBackgroundPaint(backgroundPaint);
        slideBar.setShowBackground(true);
        slideBar.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(MotionEvent event, String s, int position) {
                Log.i(TAG, "position=" + position);
                tvSlideResult.setText(s);
            }
        });
    }

    public static void startSlideBarDemoActivity(Context context) {
        Log.i(TAG, "startSlideBarDemoActivity===");
        Intent intent = new Intent(context, SlideBarDemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isOpenActionBar() {
        return false;
    }

    @Override
    protected void setActionBarTitle(String value) {

    }
}
