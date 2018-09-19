package com.cheyipai.ui.demo.pulltorefreshscrollview;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ScrollView;

import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.components.newpullview.PullToRefreshBase;
import com.cheyipai.corec.components.newpullview.PullToRefreshBase.Mode;
import com.cheyipai.corec.components.newpullview.PullToRefreshScrollView;
import com.cheyipai.ui.R;

/**
 * PullToRefreshScrollView Demo
 * Create by JunyiZhou on 2016/6/8.
 *
 * 使用PullToRefreshScrollView注意事项
 * 1.PullToRefreshScrollView需要包含子view，否则不会截获触摸事件
 * 2.PullToRefreshScrollView默认的 {@link PullToRefreshBase.Mode} 是下拉刷新{@link PullToRefreshBase.Mode#PULL_FROM_START}
 *   如果要使用上拉刷新{@link PullToRefreshBase.Mode#PULL_FROM_END}
 *   两种都使用{@link PullToRefreshBase.Mode#BOTH}
 *   都不用{@link PullToRefreshBase.Mode#DISABLED}
 * 3.只使用上拉或下拉刷新，只需要{@link PullToRefreshBase#setOnRefreshListener(PullToRefreshBase.OnRefreshListener)}
 *   例如：
 *       mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
 *           @Override public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
 *               new GetDataTask().execute();
 *           }
 *       });
 *   两种都使用，需要{@link PullToRefreshBase#setOnRefreshListener(PullToRefreshBase.OnRefreshListener2)}
 *   例如：
 *       mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
 *           @Override public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
 *               new GetDataTask().execute();
 *           }
 *           @Override public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
 *               new GetDataTask().execute();
 *           }
 *       });
 */
public class PullToRefreshScrollViewDemoActivity extends AbsBaseActivity {
    private final static String TITLE = "PullToRefreshScrollViewDemo";
    private final static String TAG = PullToRefreshScrollViewDemoActivity.class.getSimpleName();

    private PullToRefreshScrollView mPullRefreshScrollView;
    private ScrollView mScrollView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_pull_to_refresh_scroll_view_demo;
    }

    @Override
    protected void init() {
        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setMode(Mode.PULL_FROM_END);
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
            }
        });
        mScrollView = mPullRefreshScrollView.getRefreshableView();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... params) {
            Log.i(TAG, "Start loading data===");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            Log.i(TAG, "Loading data complete===");
            mPullRefreshScrollView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    public static void startPullToRefreshScrollViewDemoActivity(Context context) {
        Log.i(TAG, "startPullToRefreshScrollViewDemoActivity===");
        Intent intent = new Intent(context, PullToRefreshScrollViewDemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isOpenActionBar() {
        return false;
    }

    @Override
    protected void setActionBarTitle(String value) {
        super.setActionBarTitle(TITLE);
    }
}
