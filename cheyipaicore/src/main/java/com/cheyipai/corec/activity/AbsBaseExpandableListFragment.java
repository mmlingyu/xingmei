package com.cheyipai.corec.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.cheyipai.corec.R;
import com.cheyipai.corec.log.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wungko on 14/11/18.
 */
public abstract class AbsBaseExpandableListFragment extends AbsBaseListFragment {
    public static final String TAG = "AbsBaseExpandableListFragment";

    @Override
    protected int getContentLayout() {
        return R.layout.a_list_expandable;
    }

    private ListAdapter mListAdapter;
    private ExpandableListView mListView;

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        mListView = (ExpandableListView) contentView;
        mListView.setGroupIndicator(null);
        if (getHeaderLayout() != NO_LAYOUT) {
            View headerView = LayoutInflater.from(getContext()).inflate(getHeaderLayout(), null, false);
            onInitHeader(headerView);
            mListView.addHeaderView(headerView);
        }
        if (getFooterLayout() != NO_LAYOUT) {
            View footerView = LayoutInflater.from(getContext()).inflate(getFooterLayout(), null, false);
            onInitFooter(footerView);
            mListView.addFooterView(footerView);
        }

        mListAdapter = new ListAdapter();
        mListView.setAdapter(mListAdapter);
        mListView.setChildDivider(getResources().getDrawable(R.color.expandable_listview_driver));
        init(savedInstanceState);
    }

    /**
     * Group Item
     *
     * @return
     */
    protected abstract AbsAdapterItem getAbsGroupAdapterItem();

    /**
     * Child Item
     *
     * @return
     */
    protected abstract AbsAdapterItem getAbsChildeAdapterItem();

    private boolean isAdapterNotNull() {
        if (mListAdapter == null) {
            L.e("mListAdapter is null!");
            return false;
        }
        return true;
    }

    protected void setData(List list) {
        if (isAdapterNotNull()) {
            mListAdapter.setList(list);
            //DeviceUtils.setListViewHeightBasedOnChildren(getListView());
            updata();
        }
    }

    protected List getData() {
        if (isAdapterNotNull()) {
            return mListAdapter.mList;
        } else {
            return null;
        }
    }

    protected void invalidated() {
        if (isAdapterNotNull()) {
            mListAdapter.notifyDataSetInvalidated();
        }
    }

    protected void updata() {
        if (isAdapterNotNull()) {
            mListAdapter.notifyDataSetChanged();
            //DeviceUtils.setListViewHeightBasedOnChildren(getListView());
        }
    }

    private class ListAdapter extends BaseExpandableListAdapter {
        private List<IExpandableChildData> mList;

        private ListAdapter() {
            mList = new ArrayList<IExpandableChildData>();
        }

        private void setList(List<IExpandableChildData> list) {
            mList = null;
            mList = list;
        }

        public List<IExpandableChildData> getListData(){
            return mList;
        }

        /**
         * 加载更多数据
         *
         * @param list
         */
        private void addList(List<IExpandableChildData> list) {
            mList.addAll(list);
        }

        @Override
        public int getGroupCount() {
            return mList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {

            if (groupPosition < mList.size()) {

                List list = mList.get(groupPosition).getChild();
                if (list == null) {
                    return 0;
                } else {
                    return list.size();
                }
            } else {
                return 0;
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            if (groupPosition < mList.size()) {
                return mList.get(groupPosition);
            } else {
                return null;
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {

            List list = mList.get(groupPosition).getChild();
            if (list == null) {
                return null;
            }

            if (childPosition < list.size()) {
                return list.get(childPosition);
            }
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (mList.get(groupPosition).isOpen()) {
                mListView.expandGroup(groupPosition);
            }

            AbsAdapterItem item = null;
            if (convertView == null) {
                item = getAbsGroupAdapterItem();
                convertView = LayoutInflater.from(getContext()).inflate(item.getItemLayout(), null, false);
                item.init(convertView);
                convertView.setTag(item);
            }
            if (item == null) {
                item = (AbsAdapterItem) convertView.getTag();
            }
            item.bindData(getGroup(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            AbsAdapterItem item = null;
            if (convertView == null) {
                item = getAbsChildeAdapterItem();
                convertView = LayoutInflater.from(getContext()).inflate(item.getItemLayout(), null, false);
                item.init(convertView);
                convertView.setTag(item);
            }
            if (item == null) {
                item = (AbsAdapterItem) convertView.getTag();
            }
            item.bindData(getChild(groupPosition, childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
    public ExpandableListView getListView() {
        return mListView;
    }


}
