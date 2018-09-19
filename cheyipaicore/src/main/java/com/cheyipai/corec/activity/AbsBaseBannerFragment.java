package com.cheyipai.corec.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cheyipai.corec.R;

import java.util.ArrayList;

/**
 * 轮播Banner基类
 * Create by JunyiZhou on 2016/6/7.
 */
public abstract class AbsBaseBannerFragment extends AbsBaseFragment implements ViewPager.OnPageChangeListener {

    // 定义ViewPager对象
    private ViewPager mViewPager;

    //指示器linearLayout
    private LinearLayout mPointContainer;

    // 定义ViewPager适配器
    private ViewPagerAdapter mAdapter;

    //定义一个ArrayList来存放View
    private ArrayList<View> mPagerViewList;

    //默认图片
    private ImageView mDefaultImage;

    //底部小点views
    private ImageView[] mPointViews;

    //当前选中point的位置
    private int mCurrentPointPosition;

    //当前选中view的位置
    private int mCurrentViewPosition;

    //轮播间隔时间（微秒）
    private long mBannerPlayOffset = 3000;

    //底部小点Resource
    private int mPointImage = R.drawable.point_banner;

    //底部小点LayoutParams
    private LinearLayout.LayoutParams mPointLayoutParams;

    //Handler，用于执行循环任务
    private static Handler handler = new Handler();

    /**
     * 获取轮播Views
     *
     * @return 轮播Views
     */
    protected abstract ArrayList<View> getPagerViews();

    /**
     * 获取默认图片点击监听
     *
     * @return 点击监听
     */
    protected abstract View.OnClickListener getDefaultImageOnClickListener();

    /**
     * 设置轮播间隔时间
     *
     * @param bannerPlayOffset
     */
    protected void setBannerPlayOffset(long bannerPlayOffset) {
        mBannerPlayOffset = bannerPlayOffset;
    }

    /**
     * 设置默认图片resource
     *
     * @param resourceId
     */
    protected void setDefaultImage(int resourceId) {
        if (mDefaultImage != null) {
            mDefaultImage.setBackgroundResource(resourceId);
        }
    }

    /**
     * 设置底部小点resource
     *
     * @param resourceId
     */
    protected void setPointImage(int resourceId) {
        if (resourceId == 0) {
            throw new IllegalArgumentException("resourceId cannot be 0");
        }
        mPointImage = resourceId;
    }

    /**
     * 设置底部小点LayoutParams
     *
     * @param layoutParams
     */
    protected void setPointLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (layoutParams == null) {
            throw new IllegalArgumentException("layoutParams cannot be null");
        }
        mPointLayoutParams = layoutParams;
    }

    /**
     * 设置底部小点可见性
     *
     * @param visibility
     */
    protected void setPointVisibility(int visibility) {
        mPointContainer.setVisibility(visibility);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.a_banner_fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        initView(contentView);
    }

    /**
     * 初始化控件
     */
    private void initView(View parent) {
        mViewPager = (ViewPager) parent.findViewById(R.id.banner_viewpager);
        mPointContainer = (LinearLayout) parent.findViewById(R.id.banner_point_container);
        mDefaultImage = (ImageView) parent.findViewById(R.id.banner_default_iv);
        if (mDefaultImage != null) {
            mDefaultImage.setOnClickListener(getDefaultImageOnClickListener());
        }

        mPointLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPointLayoutParams.setMargins(10, 2, 10, 2);

        updateViews();
    }

    /**
     * 更新界面
     * 若没有可轮播的views，则展示default image
     */
    public void updateViews() {
        if (mPagerViewList == null || mPagerViewList.size() < 1) {
            if (mViewPager != null) {
                mViewPager.setVisibility(View.GONE);
            }
            if (mDefaultImage != null) {
                mDefaultImage.setVisibility(View.VISIBLE);
            }
        } else {
            if (mViewPager != null) {
                mViewPager.setVisibility(View.VISIBLE);
            }
            if (mDefaultImage != null) {
                mDefaultImage.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 自动轮播逻辑
     * 可以调用 {@link AbsBaseBannerFragment#setBannerPlayOffset(long)} 设置轮播间隔
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager != null && mPagerViewList != null) {
                handler.postDelayed(this, mBannerPlayOffset);
                mViewPager.setCurrentItem(mCurrentViewPosition);
                if (mCurrentViewPosition >= mViewPager.getAdapter().getCount()) {
                    mCurrentViewPosition = 0;
                } else {
                    mCurrentViewPosition += 1;
                }
            }
        }
    };

    /**
     * 开始轮播
     */
    protected void start() {
        mPagerViewList = getPagerViews();
        mAdapter = new ViewPagerAdapter(mPagerViewList);
        mCurrentViewPosition = 0;
        initPaper();
        updateViews();
    }

    /**
     * 初始化paper adapter
     */
    private void initPaper() {
        if (mViewPager == null) {
            return;
        }
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

        initPoint();
    }

    /**
     * 初始化底部小点
     * 可以调用 {@link AbsBaseBannerFragment#setPointImage(int)} 设置底部小点的素材
     * 可以调用 {@link AbsBaseBannerFragment#setPointLayoutParams(LinearLayout.LayoutParams)} 设置底部小点的布局
     * 可以调用 {@link AbsBaseBannerFragment#setPointVisibility(int)} 设置底部小点可见性
     */
    private void initPoint() {
        if (mPointContainer == null || mPagerViewList == null || mPagerViewList.size() < 1) {
            return;
        }
        mPointViews = new ImageView[mPagerViewList.size()];
        mPointContainer.removeAllViews();
        if (mPagerViewList.size() <= 1) {
            return;
        }
        for (int i = 0; i < mPagerViewList.size(); i++) {
            ImageView point = new ImageView(getContext());
            point.setImageDrawable(getResources().getDrawable(mPointImage));
            point.setLayoutParams(mPointLayoutParams);
            mPointContainer.addView(point);
            mPointViews[i] = point;
            mPointViews[i].setSelected(false);
        }
        mCurrentPointPosition = 0;
        mPointViews[mCurrentPointPosition].setSelected(true);

        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(runnable, mBannerPlayOffset);
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
        mCurrentViewPosition = position;
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        // 排除异常情况
        if (positon < 0 || positon > mPagerViewList.size() - 1 || mCurrentPointPosition == positon) {
            return;
        }
        mPointViews[positon].setSelected(true);
        mPointViews[mCurrentPointPosition].setSelected(false);

        mCurrentPointPosition = positon;
    }

    /**
     * 轮播使用的adapter
     */
    static class ViewPagerAdapter extends PagerAdapter {
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
