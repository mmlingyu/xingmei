package com.cheyipai.corec.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cheyipai.corec.R;
import com.cheyipai.corec.components.newpullview.PullToRefreshBase.Mode;
import com.cheyipai.corec.components.newpullview.PullToRefreshListView;
import com.cheyipai.corec.components.newpullview.PullToRefreshBase;

/**
 * Created by wungko on 14/11/18.
 */
public abstract class AbsBaseDragListFragment extends AbsBaseListFragment {
    private PullToRefreshListView mPullListView;
    /**
     * 下拉刷新模式
     */
    public static final Mode MODE_PULL_DOWN_TO_REFRESH = Mode.PULL_FROM_START;
    /**
     * 上拉刷新模式
     */
    public static final Mode MODE_PULL_UP_TO_REFRESH = Mode.PULL_FROM_END;
    /**
     * 上、下拉刷新模式均支持
     */
    public static final Mode MODE_BOTH = Mode.BOTH;
    /**
     * 无刷新模式
     */
    public static final Mode MODE_NONE = Mode.DISABLED;

    @Override
    protected int getContentLayout() {
        return R.layout.a_drag_list;
    }


    /**
     * 下拉刷新
     */
    abstract protected void onFresh();



    /**
     * 上拉加载
     */
    abstract protected void onNextPage();
    protected void pullDone() {
        if (mPullListView != null) {
            mPullListView.onRefreshComplete();
        }
    }

    protected Mode getMode() {
        return Mode.BOTH;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        super.init(savedInstanceState, contentView);
        if (contentView instanceof PullToRefreshListView) {
            mPullListView = (PullToRefreshListView)contentView;
            mPullListView.setMode(getMode());
            int dividerHeight = getDividerHeight();
            if (dividerHeight != -1) {
                mPullListView.getRefreshableView().setDividerHeight(dividerHeight);
            }
            mPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    onFresh();
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    onNextPage();
                }
            });
        }
    }

    @Override
    protected int getHeaderLayout() {
        return 0;
    }
}
