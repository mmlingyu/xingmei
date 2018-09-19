package com.cheyipai.corec.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.cheyipai.corec.log.L;

/**
 * Created by 景涛 on 2016/5/30.
 */
public  class PinnedHeaderListView extends ExpandableListView {
    private float mTouchX;
    private float mTouchY;

    public View getTopPopularView() {
        return topPopularView;
    }

    /**
     * 用于在列表头显示的 View,topPopularViewVisible 为 true 才可见
     */
    private View topPopularView;
    private boolean topPopularViewVisible;
    private int mHeaderViewWidth;
    private int mHeaderViewHeight;
    private static final int MAX_ALPHA = 255;
    private int mOldState = -1;
    private HeaderAdapter mAdapter;
    private int topY = 0;
    private int relateHeight = 84;
    private OnScrollListener onScrollListener ;
    private OnGroupClickListener onGroupClickListener;
    private AbsListView.OnScrollListener onListViewScrollListener;
    private float mDownX;
    private float mDownY;
    private int currentGroup = -1;
    public PinnedHeaderListView(Context context) {
        super(context);
        init(context);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public AbsListView.OnScrollListener getOnListViewScrollListener() {
        return onListViewScrollListener;
    }

    public void setOnListViewScrollListener(AbsListView.OnScrollListener onListViewScrollListener) {
        this.onListViewScrollListener = onListViewScrollListener;

    }
    private void init(Context context){
        relateHeight =dip2px(context,32);
    }

    public void setListener(){
        if(onListViewScrollListener!=null){
            setOnScrollListener(onListViewScrollListener);
        }
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        this.onGroupClickListener = onGroupClickListener;
    }

    public int getTopY(){
        return topY;
    }

    public void setTopY(int top){
        topY = top;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final long flatPostion = getExpandableListPosition(getFirstVisiblePosition());
        final int groupPos = ExpandableListView.getPackedPositionGroup(flatPostion);
        final int childPos = ExpandableListView.getPackedPositionChild(flatPostion);
        int state = mAdapter.getHeaderState(groupPos, childPos);
        Log.i("state", "state" + state);
        if (topPopularView != null && mAdapter != null && state != mOldState) {
            mOldState = state;
            setTopY(180);
            topPopularView.layout(0, getTopY(), mHeaderViewWidth, mHeaderViewHeight);
            L.i("PINTEST onLayout topY -" + topY);
            setTopY(0);
        }
        Log.i("onLayout", "groupPos" + groupPos+" getFirstVisiblePosition= "+getFirstVisiblePosition()+" flatPostion="+flatPostion);
        if (groupPos > -1) {
            currentGroup = groupPos;
            setTopPopularView(groupPos, childPos);
        }
    }

    /**
     * 如果 HeaderView 是可见的 , 此函数用于判断是否点击了 HeaderView, 并对做相应的处理 ,
     * 因为 HeaderView 是画上去的 , 所以设置事件监听是无效的 , 只有自行控制 .
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (topPopularViewVisible) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = ev.getX();
                    mDownY = ev.getY();
                    if (mDownX <= mHeaderViewWidth && mDownY <= mHeaderViewHeight) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float x = ev.getX();
                    float y = ev.getY();
                    float offsetX = Math.abs(x - mDownX);
                    float offsetY = Math.abs(y - mDownY);
                    // 如果 HeaderView 是可见的 , 点击在 HeaderView 内 , 那么触发 headerClick()
                    L.i("y = "+y+" offsetY ="+offsetY +" relateHeight="+relateHeight+" mHeaderViewHeight ="+mHeaderViewHeight+" |="+(mHeaderViewHeight+relateHeight));
                    if (x <= mHeaderViewWidth &&y>=mHeaderViewHeight&& y <= mHeaderViewHeight+relateHeight
                            && offsetX <= mHeaderViewWidth && offsetY <= mHeaderViewHeight+relateHeight) {
                        if (topPopularView != null) {
                            headerViewClick(currentGroup);
                        }

                        return true;
                    }
                    break;
                default:
                    break;
            }
        }

        return super.onTouchEvent(ev);

    }


    /**
     * 点击 HeaderView 触发的事件
     */
    private void headerViewClick(int currentGroup) {

        if(onGroupClickListener!=null){
            onGroupClickListener.OnClick(currentGroup);
        }
      /*  long packedPosition = getExpandableListPosition(this.getFirstVisiblePosition());
        int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
        if (mAdapter.getGroupClickStatus(groupPosition) == 1) {
            this.collapseGroup(groupPosition);
            mAdapter.setGroupClickStatus(groupPosition, 0);
        } else {
            this.expandGroup(groupPosition);
            mAdapter.setGroupClickStatus(groupPosition, 1);
        }
        this.setSelectedGroup(groupPosition);*/
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (topPopularView != null) {
            measureChild(topPopularView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = topPopularView.getMeasuredWidth();
            mHeaderViewHeight = topPopularView.getMeasuredHeight();
        }
    }

    @Override
    /**
     * 列表界面更新时调用该方法(如滚动时)
     */
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (topPopularViewVisible) {
            //分组栏是直接绘制到界面中，而不是加入到ViewGroup中
            drawChild(canvas, topPopularView, getDrawingTime());
        }
    }



    public void setHeaderView(View view) {
        topPopularView = view;
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        if (topPopularView != null) {
            setFadingEdgeLength(0);
        }

        requestLayout();
    }

    protected void onScrollCallBack(AbsListView view,int groupPosition,int childPosition){
        L.i("PINTEST onScrollCallBack groupPosition " + groupPosition +" childPosition "+childPosition+" view getFirstVisiblePosition="+view.getFirstVisiblePosition());
        setTopPopularView(groupPosition, childPosition);
        if(onScrollListener !=null){
            view.setTop(getTopY());
            onScrollListener.OnScroll(groupPosition,childPosition);
        }
    }

    public interface OnScrollListener{
        public void OnScroll(int groupPostion, int chindPostion);
    }

    public interface OnGroupClickListener{
        public void OnClick(int groupPostion);
    }
    public interface HeaderAdapter {
        public static final int PINNED_HEADER_GONE = 0;
        public static final int PINNED_HEADER_VISIBLE = 1;
        public static final int PINNED_HEADER_PUSHED_UP = 2;

        /**
         * 获取 Header 的状态
         *
         * @param groupPosition
         * @param childPosition
         * @return PINNED_HEADER_GONE, PINNED_HEADER_VISIBLE, PINNED_HEADER_PUSHED_UP 其中之一
         */
        int getHeaderState(int groupPosition, int childPosition);

        /**
         * 配置 Header, 让 Header 知道显示的内容
         *
         * @param header
         * @param groupPosition
         * @param childPosition
         * @param alpha
         */
        void configureHeader(View header, int groupPosition, int childPosition, int alpha);

        /**
         * 设置组按下的状态
         *
         * @param groupPosition
         * @param status
         */
        void setGroupClickStatus(int groupPosition, int status);

        /**
         * 获取组按下的状态
         *
         * @param groupPosition
         * @return
         */
        int getGroupClickStatus(int groupPosition);

    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (HeaderAdapter) adapter;
    }

    public void setScrollListener(OnScrollListener onScrollListener){
        this.onScrollListener = onScrollListener;
    }
    public int getHeihtToZero(View v) {
        if(v == null) return 0;
        int[] loc = new int[2];
        v.getLocationOnScreen(loc);

        return loc[1];
    }
    public void setTopPopularView(int groupPosition, int childPosition) {
        if (topPopularView == null || mAdapter == null
                || ((ExpandableListAdapter) mAdapter).getGroupCount() == 0) {
            return;
        }
        currentGroup = groupPosition;
        int state = mAdapter.getHeaderState(groupPosition, childPosition);
        switch (state) {
            case HeaderAdapter.PINNED_HEADER_GONE: {
                topPopularViewVisible = false;
                break;
            }
            case HeaderAdapter.PINNED_HEADER_VISIBLE: {

                if (topPopularView.getTop() != 0) {
                    setTopY(relateHeight);
                    topPopularView.layout(0,getTopY(), mHeaderViewWidth, mHeaderViewHeight+relateHeight);
                    setTopY(0);
                    //addView(topPopularView);
                    L.i("PINTEST setTopPopularView PINNED_HEADER_VISIBLE +"+getTopY()+" gettop = "+topPopularView.getTop()+" getHeihtToZero="+getHeihtToZero(topPopularView));
                }
                mAdapter.configureHeader(topPopularView, groupPosition, childPosition, MAX_ALPHA);
                topPopularViewVisible = true;
                break;
            }
            case HeaderAdapter.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                int bottom = firstView.getBottom();
                int headerHeight = topPopularView.getHeight();
                int y;
                int alpha;
                if (bottom < headerHeight) {
                    y = (bottom - headerHeight);
                    alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }

                if (topPopularView.getTop() != y) {
                   // setTopY(relateHeight);
                    L.i("PINTEST setTopPopularView layout top y = "+y +" get top = "+topPopularView.getTop());
                    topPopularView.layout(0, y+relateHeight, mHeaderViewWidth, mHeaderViewHeight+relateHeight);
                    //setTopY(0);
                }
                mAdapter.configureHeader(topPopularView, groupPosition, childPosition, alpha);

                topPopularViewVisible = true;
                break;
            }
        }
    }
}
