package com.cheyipai.corec.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.cheyipai.corec.R;
import com.cheyipai.corec.log.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gjt on 16/5/31.
 */
public abstract class AbsBasePinnedHeaderListFragment extends AbsBaseListFragment implements AbsListView.OnScrollListener,PinnedHeaderListView.OnScrollListener,PinnedHeaderListView.OnGroupClickListener{
    public static final String TAG = "AbsBasePinnedHeaderListFragment";

    @Override
    protected int getContentLayout() {
        return R.layout.a_list_pinnedheader;
    }
    private View headerView;
    private ListAdapter mListAdapter;
    private PinnedHeaderListView mListView;
    private boolean isExpenedOnInit = true;//是否默认展开

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        mListView = (PinnedHeaderListView) contentView;
        mListView.setGroupIndicator(null);
        int headerLayout = getHeaderLayout();
        if (headerLayout != NO_LAYOUT) {
            headerView = LayoutInflater.from(getContext()).inflate(headerLayout, null, false);
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
        setHeaderView(getAbsGroupAdapterItem().getLayout());
        mListView.setScrollListener(this);
        mListView.setOnListViewScrollListener(this);
        mListView.setOnGroupClickListener(this);
        mListView.setListener();
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        final long flatPos = mListView.getExpandableListPosition(i);
        int groupPosition = ExpandableListView.getPackedPositionGroup(flatPos);
        Log.i(" onScroll "," i = "+i+" flatPos = "+flatPos+" groupPosition = "+groupPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(flatPos);
        mListView.onScrollCallBack(absListView,groupPosition,childPosition);
    }

    protected View getHeaderView(){
        return headerView;
    }

    protected void setTopY(int topY){
        mListView.setTopY(topY);
    }

    protected void setHeaderView(int resourceId){
        mListView.setHeaderView(LayoutInflater.from(getContext()).inflate(resourceId, null, false));
    }


    protected void setExpenedOnInit(boolean expenedOnInit){
        this.isExpenedOnInit = expenedOnInit;
    }

    /**
     * Group Item
     *
     * @return
     */
    protected abstract AbsAdapterItem getAbsGroupAdapterItem();

    protected abstract AbsAdapterItem getAbsGroupAdapterItem(int type);


    protected abstract int getAbsViewGroupTypeCount();

    /**
     * Child Item
     *
     * @return
     */
    protected abstract AbsAdapterItem getAbsChildAdapterItem();
    protected abstract void postEvent(int groupPostion);

    protected  AbsAdapterItem getAbsChildAdapterItem(int type) {
        return null;
    };

    protected abstract int getAbsItemViewType(int groupPostion, int childPosition);

    protected abstract int getAbsGroupViewType(int groupPostion);

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
            if(isExpenedOnInit) {
                for (int i = 0; i < mListView.getCount(); i++) {
                    mListView.expandGroup(i);
                }
            }
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

    protected void setTopHeader(Object o,View v){

    }


    public void selectGroup(int pos){
        mListView.setSelectedGroup(pos);
    }



    private class ListAdapter extends BaseExpandableListAdapter implements PinnedHeaderListView.HeaderAdapter {
        private List<IExpandableChildData> mList;
        private Map<Integer,View> groupViews = new HashMap<Integer, View>();
        public View getCurrentGroup(int groupPostion){
            return groupViews.get(groupPostion);
        }
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
        public int getHeaderState(int groupPosition, int childPosition) {
            //如果添加了表头，对于groupPosition小于0的不显示

            if (groupPosition < 0) {
                return PINNED_HEADER_GONE;
            }
            if(getCurrentGroup(groupPosition+1)!=null&&getHeihtToZero(getCurrentGroup(groupPosition+1))<=140){
               // postEvent(groupPosition+1);
                return PINNED_HEADER_PUSHED_UP;
            }
            final int childCount = getChildrenCount(groupPosition);
            if (childPosition == childCount - 1) {
                return PINNED_HEADER_PUSHED_UP;
            } else if (childPosition == -1
                    && !mListView.isGroupExpanded(groupPosition)) {
                return PINNED_HEADER_GONE;
            } else {
                return PINNED_HEADER_VISIBLE;
            }
        }
        /**
         * 获取控件距屏幕远点的高度
         *
         * @param v
         * @return
         */
        public int getHeihtToZero(View v) {
            if(v == null) return 0;
            int[] loc = new int[2];
            v.getLocationOnScreen(loc);

            return loc[1];
        }
        public boolean isViewCovered(final View view)
        {
            if(view == null)return false;
            View currentView = view;
            Rect currentViewRect = new Rect();
            boolean partVisible =currentView.getGlobalVisibleRect(currentViewRect);
            boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >= currentView.getMeasuredHeight();
            boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= currentView.getMeasuredWidth();
            boolean totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible;
            if (!totalViewVisible)//if any part of the view is clipped by any of its parents,return true
                return true;

            while (currentView.getParent() instanceof ViewGroup)
            {
                ViewGroup currentParent = (ViewGroup) currentView.getParent();
                if (currentParent.getVisibility() != View.VISIBLE)//if the parent of view is not visible,return true
                    return true;

                int start = indexOfViewInParent(currentView, currentParent);
                for (int i = start + 1; i < currentParent.getChildCount(); i++)
                {
                    Rect viewRect = new Rect();
                    view.getGlobalVisibleRect(viewRect);
                    View otherView = currentParent.getChildAt(i);
                    Rect otherViewRect = new Rect();
                    otherView.getGlobalVisibleRect(otherViewRect);
                    if (Rect.intersects(viewRect, otherViewRect))//if view intersects its older brother(covered),return true
                        return true;
                }
                currentView = currentParent;
            }
            return false;
        }


        private int indexOfViewInParent(View view, ViewGroup parent)
        {
            int index;
            for (index = 0; index < parent.getChildCount(); index++)
            {
                if (parent.getChildAt(index) == view)
                    break;
            }
            return index;
        }

        @Override
        public void configureHeader(View header, int groupPosition,
                                    int childPosition, int alpha) {
            setTopHeader(this.getGroup(groupPosition),mListView.getTopPopularView());
        }

        private SparseIntArray groupStatusMap = new SparseIntArray();
        @Override
        public void setGroupClickStatus(int groupPosition, int status) {
            groupStatusMap.put(groupPosition, status);
        }

        @Override
        public int getGroupClickStatus(int groupPosition) {
            if (groupStatusMap.keyAt(groupPosition) >= 0) {
                return groupStatusMap.get(groupPosition);
            } else {
                return 0;
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

        public int getItemViewType(int groupPostion,int childPosition) {
            return getAbsItemViewType(groupPostion,childPosition);
        }

        public int getViewTypeCount() {
            return getAbsViewTypeCount();
        }

        public int getGroupTypeCount() {
            return getAbsViewGroupTypeCount();
        }

        public int getGroupViewType(int groupPostion) {
            return getAbsGroupViewType(groupPostion);
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (mList.get(groupPosition).isOpen()) {
                mListView.expandGroup(groupPosition);
            }
            AbsAdapterItem item = null;
            int count = getGroupTypeCount();
            if(count != 1){
                int type = getGroupViewType(groupPosition);
                item = getAbsGroupAdapterItem(type);
            }else{
                item = getAbsGroupAdapterItem();
            }
            convertView = LayoutInflater.from(getContext()).inflate(item.getItemLayout(), null, false);
            item.init(convertView);
            Object adapterItem = getGroup(groupPosition);
            item.bindData(adapterItem);
            groupViews.put(groupPosition,convertView);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            AbsAdapterItem item = null;
            int count = getViewTypeCount();
            if (count != 1) {
                    int type = getItemViewType(groupPosition, childPosition);
                    item = getAbsChildAdapterItem(type);
            } else {
                    item = getAbsChildAdapterItem();
            }
            convertView = LayoutInflater.from(getContext()).inflate(item.getItemLayout(), null, false);
            item.init(convertView);
            item.bindData(getChild(groupPosition, childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
    public PinnedHeaderListView getListView() {
        return mListView;
    }


}
