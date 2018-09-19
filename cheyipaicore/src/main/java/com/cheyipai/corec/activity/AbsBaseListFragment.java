package com.cheyipai.corec.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cheyipai.corec.R;
import com.cheyipai.corec.components.newpullview.PullToRefreshListView;
import com.cheyipai.corec.log.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wungko on 14/11/18.
 */
public abstract class AbsBaseListFragment extends AbsBaseFragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "AbsBaseListFragment";
    public static final int NO_LAYOUT = 0;
    private ListView listView = null;
    private View headerView;
    private Context mContext;
    protected ListView getListView(){
        return listView;
    }

    protected View getHeaderView(){
       return headerView;
    }

    protected int getDividerHeight() {return -1;}
    /**
     * 设置内容布局
     *
     * @return
     */
    @Override
    protected int getContentLayout() {
        return R.layout.a_list;
    }

    /**
     * 设置ListView header 布局
     *
     * @return
     */
    protected abstract int getHeaderLayout();

    /**
     * 设置ListView footer 布局
     *
     * @return
     */
    protected abstract int getFooterLayout();

    /**
     * 返回ListView header 布局
     * 如果没有设置header布局 此方法不会被调用
     *
     * @param view
     */
    protected void onInitHeader(View view) {
        headerView = view;
    }

    /**
     * 返回ListView footer 布局
     * 如果没有设置footer布局 此方法不会被调用
     *
     * @param view
     */
    protected void onInitFooter(View view) {
    }

    /**
     * 设置 ListView type
     */
    protected int getAbsItemViewType(int position) {
        return 0;
    }

    /**
     * 设置ListView type count
     */

    protected int getAbsViewTypeCount() {
        return 1;
    }

    /**
     * 设置 ListView Item
     *
     * @return
     */
    protected AbsAdapterItem getAbsAdapterItem(int type) {
        return null;
    };

    protected abstract AbsAdapterItem getAbsAdapterItem();

    /**
     * ListView adapter
     */
    private ListAdapter mListAdapter;

    /**
     * AbsBaseListFragment 初始化方法  完成对ListView header、footer的初始化
     * 以及ListViewadapter的初始化
     *
     * @param savedInstanceState
     * @param contentView
     */
    @Override
    protected void init(Bundle savedInstanceState, View contentView) {

        if (contentView instanceof ListView) {
            listView = (ListView) contentView;
        } else if (contentView instanceof PullToRefreshListView) {
            listView = ((PullToRefreshListView) contentView).getRefreshableView();
        }

        listView.setOnItemClickListener(this);
        int dividerHeight = getDividerHeight();
        if (dividerHeight != -1) {
            listView.setDividerHeight(dividerHeight);
        }

        if (getHeaderLayout() != NO_LAYOUT) {
            headerView = LayoutInflater.from(getContext()).inflate(getHeaderLayout(), null, false);
            onInitHeader(headerView);
            listView.addHeaderView(headerView);
        }
        if (getFooterLayout() != NO_LAYOUT) {
            View footerView = LayoutInflater.from(getContext()).inflate(getFooterLayout(), null, false);
            onInitFooter(footerView);
            listView.addFooterView(footerView);
        }
        mListAdapter = new ListAdapter();
        listView.setAdapter(mListAdapter);
        init(savedInstanceState);
    }

    /**
     * ListView adapter 非空检查
     *
     * @return
     */
    private boolean isAdapterNotNull() {
        if (mListAdapter == null) {
            L.e("mListAdapter is null!");
            return false;
        }
        return true;
    }

    /**
     * 为 ListView adapter 设置值 初次设置或者更新加载数据
     * ps  ListView 下拉刷新时 需要调用此方法
     *
     * @param list
     */
    protected void setData(List list) {
        if (isAdapterNotNull()) {
            mListAdapter.setList(list);
            updata();
        }
    }

    /**
     * ListView 加载更多数据时 需要条用此方法复制
     *
     * @param list
     */
    protected void addData(List list) {
        if (isAdapterNotNull()) {
            mListAdapter.addList(list);
            updata();
        }
    }

    /**
     * 返回ListView 当前数据
     *
     * @return
     */
    protected List getData() {

        if (isAdapterNotNull()) {
            return mListAdapter.mList;
        } else {
            return null;
        }
    }

    /**
     * 刷新ListView adapter
     */
    protected void invalidated() {
        if (isAdapterNotNull()) {
            mListAdapter.notifyDataSetInvalidated();
        }
    }

    /**
     * 更新ListView adapter
     */
    protected void updata() {
        if (isAdapterNotNull()) {
            int status = getFragmentStatus();
            if (status == FRAGMENT_STATUS_SUCCESS && getData().isEmpty()) {
                setFragmentStatus(FRAGMENT_STATUS_EMPTY);
            } else if (status != FRAGMENT_STATUS_SUCCESS && !getData().isEmpty()) {
                setFragmentStatus(FRAGMENT_STATUS_SUCCESS);
            }
            mListAdapter.notifyDataSetChanged();

        }
    }


    protected abstract void init(Bundle savedInstanceState);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private class ListAdapter extends BaseAdapter {
        private List mList;

        private ListAdapter() {
            mList = new ArrayList();
        }

        private void setList(List list) {
            mList = null;
            mList = list;
        }

        /**
         * 加载更多数据
         *
         * @param list
         */
        private void addList(List list) {
            mList.addAll(list);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            if (position > getCount() - 1) {
                return null;
            }
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 返回类型
         *
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {
            return getAbsItemViewType(position);
        }

        /**
         * 类型数量
         *
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return getAbsViewTypeCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AbsAdapterItem item = null;
            if (convertView == null) {
                int count = getViewTypeCount();
                if (count != 1) {
                    int type = getItemViewType(position);
                    item = getAbsAdapterItem(type);
                } else {
                    item = getAbsAdapterItem();
                }
                convertView = LayoutInflater.from(getContext()).inflate(item.getItemLayout(), null, false);
                item.init(convertView);
                convertView.setTag(item);
            }
            if (item == null) {
                item = (AbsAdapterItem) convertView.getTag();
            }
            item.bindData(mList.get(position));
            return convertView;
        }
    }

}
