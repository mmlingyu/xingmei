package com.cheyipai.ui.fragment.common;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cheyipai.corec.activity.AbsBaseFragment;
import com.cheyipai.ui.CheyipaiApplication;
import com.cheyipai.ui.R;
import com.cheyipai.ui.ui.car.commons.WebViewShowActivity;

import java.util.ArrayList;

/**
 * Created by 景涛 on 2015/9/14.
 * <p/>
 * UpDated by JunyiZhou on 2016/1/28.
 */
public abstract class AbsBannerFragment extends AbsBaseFragment implements ViewPager.OnPageChangeListener {

    // 定义ViewPager对象
    private ViewPager viewPager;

    //指示器linearLayout
    private LinearLayout pointLinearLayout;

    // 定义ViewPager适配器
    private ViewPagerAdapter mAdapter;

    // 定义一个ArrayList来存放View
    private ArrayList<View> mPagerViewList;

    private ImageView defaultImage;

    private ImageView[] mPointViews;

    // 记录当前选中位置
    private int currentDotPosition;

    @Override
    public void setFragmentType(int fragmentType) {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.index_banner_pager;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        initView(contentView);
    }

    protected abstract ArrayList<View> getPagerViews();

    /**
     * 初始化组件
     */
    private void initView(View parent) {
        viewPager = (ViewPager) parent.findViewById(R.id.banner_viewpager);
        pointLinearLayout = (LinearLayout) parent.findViewById(R.id.banner_linearLayout);
        defaultImage = (ImageView) parent.findViewById(R.id.banner_default_iv);
        defaultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewShowActivity.startUserCarActivity(AbsBannerFragment.this.getActivity(), "http://web3d.com.cn/h5show/h5201809/");
            }
        });
        setDefaultImage();
    }

    public void setDefaultImage(int resourceId){
        if(defaultImage!=null) {
            defaultImage.setBackgroundResource(resourceId);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setDefaultImage() {
        if (mPagerViewList == null || mPagerViewList.size() < 1) {
            if (viewPager != null) {
                viewPager.setVisibility(View.GONE);
                defaultImage.setVisibility(View.VISIBLE);
            }
        } else {
            viewPager.setVisibility(View.VISIBLE);
            defaultImage.setVisibility(View.GONE);
        }
    }

    private boolean isLoop = true;
    private Handler handler = new Handler();

    private int currentViewPosition;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager != null && mPagerViewList != null) {
                handler.postDelayed(this, 3000);
                viewPager.setCurrentItem(currentViewPosition);
                if (currentViewPosition >= viewPager.getAdapter().getCount()) {
                    currentViewPosition = 0;
                } else {
                    currentViewPosition += 1;
                }
            }
        }
    };

    /**
     * 初始化数据
     */
    protected void initData() {
        mPagerViewList = getPagerViews();
        mAdapter = new ViewPagerAdapter(mPagerViewList);
        currentViewPosition = 0;
        initPaper();
        setDefaultImage();
    }

    /**
     * 初始化paper adapter
     */
    private void initPaper() {
        if (viewPager == null) return;
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(this);

        initPoint();
    }

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        if (mPagerViewList == null || mPagerViewList.size() < 1) return;
        mPointViews = new ImageView[mPagerViewList.size()];
        pointLinearLayout.removeAllViews();
        if (mPagerViewList.size() <= 1) return;
        for (int i = 0; i < mPagerViewList.size(); i++) {
            ImageView point = new ImageView(CheyipaiApplication.getInstance());
            point.setImageDrawable(getResources().getDrawable(R.drawable.index_banner_point_selector));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 2, 10, 2);
            point.setLayoutParams(layoutParams);
            pointLinearLayout.addView(point);
            mPointViews[i] = point;
            mPointViews[i].setSelected(false);
        }
        currentDotPosition = 0;
        mPointViews[currentDotPosition].setSelected(true);

        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(runnable, 3000);
    }

    /**
     * 当滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    /**
     * 当当前页面被滑动时调用
     */

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 当新的页面被选中时调用
     */

    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurDot(position);
        currentViewPosition = position;
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position) {
        // 排除异常情况
        if (position < 0 || position >= mPagerViewList.size()) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        // 排除异常情况
        if (positon < 0 || positon > mPagerViewList.size() - 1 || currentDotPosition == positon) {
            return;
        }
        mPointViews[positon].setSelected(true);
        mPointViews[currentDotPosition].setSelected(false);

        currentDotPosition = positon;
    }

    class ViewPagerAdapter extends PagerAdapter {
        private ArrayList<View> views;

        public ViewPagerAdapter(ArrayList<View> views) {
            this.views = views;
        }

        //获得当前界面总数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            viewGroup.addView(views.get(position), 0);
            return views.get(position);
        }

        //判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;

        }

        //销毁position位置的界面
        @Override
        public void destroyItem(ViewGroup viewGroup, int position, Object arg2) {
            viewGroup.removeView(views.get(position));
        }

    }
}
