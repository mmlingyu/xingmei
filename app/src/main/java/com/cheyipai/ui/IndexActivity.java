package com.cheyipai.ui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

@SuppressWarnings("deprecation")
public class IndexActivity extends TabActivity implements
        OnCheckedChangeListener {
    private static RadioGroup mBottomTab;
    private static TabHost mTabhost;
    private Intent mHomeIntent, mIndexIntent;
    private Intent mLikeCarIntent;//爱车
    private Intent mStrategyIntent;//攻略
    private Intent mUserIntent;//我
    private CheyipaiApplication mApplication;
    private LocationClient mLocationClient;//定位
    private LocationClientOption.LocationMode mLocationMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String mTempCoor = "gcj02";//默认
    private static final int SCAN = 60 * 1000 * 60;//定位间隔
    public final static String HOME = "home_intent", LOVE_CAR = "like_car_intent", DISCUZ = "strategy_intent", USER = "user_intent";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        mBottomTab = (RadioGroup) findViewById(R.id.main_tab);
        mBottomTab.setOnCheckedChangeListener(this);
        mTabhost = getTabHost();
        mIndexIntent = this.getIntent();
        initTabIntent();
        initTabs();
        openEventBus();
    }







    /**
     * 开始定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(mLocationMode);//设置定位模式
        option.setCoorType(mTempCoor);//返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(SCAN);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    /**
     * 注册eventbus
     */
    protected void openEventBus() {
        // EventBus.getDefault().register(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        dispatchIntent(this.getIntent());
    }

    /**
     * 初始化主页布局
     */
    private void initTabIntent() {
        mHomeIntent = new Intent(this, HomeActivity.class);
        mLikeCarIntent = new Intent(this, HairListActivity.class);
        mUserIntent = new Intent(this, HairListActivity.class);

    }

    /**
     * 分发intent
     *
     * @param intent
     */
    public void dispatchIntent(Intent intent) {
        if (intent == null || intent.getStringExtra("source") == null)
            return;
        String source = intent.getStringExtra("source");
        mTabhost.setCurrentTabByTag(source);
        mBottomTab.check(getResources().getIdentifier(source, "id", getPackageName()));
    }

    /**
     * 跳转到主页
     */
    public void startHomeActivity() {
        Intent intent = new Intent();
        intent.putExtra("source", "home_intent");
        dispatchIntent(intent);
    }

    /**
     * 跳转到主页
     */
    public void startUserActivity() {
        Intent intent = new Intent();
        intent.putExtra("source", "user_intent");
        dispatchIntent(intent);
    }


    /**
     * change index tab
     *
     * @param source  HOME|LOVE_CAR|DISCUZ
     * @param context
     */

    public static void changeTab(String source, Context context) {
        if (mBottomTab == null || mTabhost == null) return;
        mTabhost.setCurrentTabByTag(source);
        mBottomTab.check(context.getResources().getIdentifier(source, "id", context.getPackageName()));
    }

    /**
     * 跳转车易说
     */
    public void startDiscuzActivity() {
        Intent intent = new Intent();
        intent.putExtra("source", "strategy_intent");
        dispatchIntent(intent);
    }

    /**
     * 跳转车易说
     */
    public void startLoveCarActivity() {
        Intent intent = new Intent();
        intent.putExtra("source", "like_car_intent");
        dispatchIntent(intent);
    }

    /**
     * 初始化底部标签页
     */
    private void initTabs() {
        dispatchIntent(mIndexIntent);
        mTabhost.addTab(mTabhost
                .newTabSpec("home_intent")
                .setIndicator(
                        getResources().getString(R.string.index),
                        getResources().getDrawable(
                                R.drawable.tab_bg))
                .setContent(mHomeIntent));

        mTabhost.addTab(mTabhost
                .newTabSpec("like_car_intent")
                .setIndicator(
                        getResources().getString(R.string.index),
                        getResources().getDrawable(
                                R.drawable.tab_bg))
                .setContent(mLikeCarIntent));


        mTabhost.addTab(mTabhost
                .newTabSpec("user_intent")
                .setIndicator(
                        getResources().getString(R.string.index),
                        getResources().getDrawable(
                                R.drawable.tab_bg))
                .setContent(mUserIntent));


    }

    /**
     * 底部标签选中事件
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home_intent:

                mTabhost.setCurrentTabByTag("home_intent");
                break;
            case R.id.like_car_intent:
                mTabhost.setCurrentTabByTag("like_car_intent");
                break;
            case R.id.user_intent:
                mTabhost.setCurrentTabByTag("user_intent");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 开启主页面
     *
     * @param activity
     */
    public static void startIndexActivity(Activity activity) {
        Intent intent = new Intent(activity, IndexActivity.class);
        activity.startActivity(intent);
    }

}

