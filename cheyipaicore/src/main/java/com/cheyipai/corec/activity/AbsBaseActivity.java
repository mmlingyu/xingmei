package com.cheyipai.corec.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheyipai.corec.R;
import com.cheyipai.corec.components.LinearGradientFontSpan;
import com.cheyipai.corec.modules.app.BaseApplication;
import com.cheyipai.corec.event.IBaseEvent;
import com.cheyipai.corec.log.L;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class AbsBaseActivity extends FragmentActivity {
    public static final String TAG = AbsBaseActivity.class.getSimpleName();
    public static final int NO_LAYOUT = 0;
    View.OnClickListener actoinBarOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            onActionBarRight(view);
        }
    };
    private TextView actionBarRightTextView;
    private ImageView actionBarRightImageView;
    private RelativeLayout mActionbarRL;
    public SpannableStringBuilder getRadiusGradientSpan(String string, int startColor, int endColor) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        LinearGradientFontSpan span = new LinearGradientFontSpan(startColor, endColor);
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    protected void setActionBarRightTextView(String actionBarRightText) {
        if (this.actionBarRightTextView != null) {
            actionBarRightTextView.setText(actionBarRightText);
        }
    }

    protected void setActionBarRightView(int resourseID) {
        if (this.actionBarRightImageView != null) {
            actionBarRightImageView.setBackgroundResource(resourseID);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        L.i("onCreate==");
        super.onCreate(savedInstanceState);

       /* if (mApplication == null) {
            mApplication = (BaseApplication) getApplication();
        }
        mApplication.addActivity(this);*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int id = getLayoutID();
        View contentView = null;
        if (isOpenActionBar()) {
           // initActionBar();
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (id != NO_LAYOUT) {
            contentView = getLayoutInflater().inflate(id, null);
            setContentView(contentView);
        } else {
            L.e("contentView is Null!");
        }
        init();
        L.i("onCreate=end=");
    }

    protected abstract int getLayoutID();

    /**
     * ActionBar************************************************************
     */

    protected abstract void init();

    /**
     * 是否打开ActionBar
     *
     * @return
     */
    protected boolean isOpenActionBar() {
        return false;
    }

    /**
     * 设置布局
     */
    protected int getActionBarLayoutID() {
        return R.layout.a_actionbar_title;
    }

    /**
     * 右边点击事件
     */
    protected void onActionBarRight(View view) {
    }

    /**
     * 左边边点击事件
     */
    protected void onActionBarLeft(View view) {
        finish();
    }

    /**
     * 中间点击事件
     */
    protected void onActionBarCenter(View view) {

    }

    /**
     * 是否隐藏ActionBar
     */
    protected boolean isHideActionBar() {
        return false;
    }

    /**
     * 右边样式
     */
    protected Object getActionBarRightValue() {
        return null;
    }

    /**
     * 设置标题
     *
     * @return
     */
    protected String getActionBarTitle() {
        return "";
    }

    /**
     * 修改标题
     *
     * @param value 值
     * @return
     */
    protected void setActionBarTitle(String value) {

    }



    protected View getActionBarLayout() {
        return mActionbarRL;
    }


    protected Context getContext() {
        return this.getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 友盟统计分析工具
         * 自动地从AndroidManifest.xml文件里读取Appkey
         */
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(((Object) this).getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(((Object) this).getClass().getSimpleName());
//        if (PushUtils.isApplicationBroughtToBackground(getApplicationContext())) {
//            L.i("-----msg--开始上传日志");
//            UserActionLog.getInstance().SendLog();
////        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //mApplication.removeActivity(this);

        super.onDestroy();
    }

    public void eventPost(IBaseEvent event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    protected void openEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            return false;  //直接屏蔽
        }
        return super.onKeyLongPress(keyCode, event);

    }

}
