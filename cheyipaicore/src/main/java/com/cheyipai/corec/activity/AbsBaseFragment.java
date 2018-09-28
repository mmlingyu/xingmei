package com.cheyipai.corec.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.cheyipai.corec.R;
import com.cheyipai.corec.account.AbsAccount;
import com.cheyipai.corec.base.api.ResponseData;
import com.cheyipai.corec.components.dialog.XMLoadingDialogs;
import com.cheyipai.corec.modules.app.BaseApplication;
import com.cheyipai.corec.components.BaseHolder;
import com.cheyipai.corec.components.LoadingViewAnim;
import com.cheyipai.corec.components.LoadingViewError;
import com.cheyipai.corec.components.dialog.LoadingDialogs;
import com.cheyipai.corec.event.IBaseEvent;
import com.cheyipai.corec.log.L;
import com.cheyipai.corec.utils.ToastHelper;
import com.ypy.eventbus.EventBus;

/**
 * Created by daincly on 2014/11/14.
 */
public abstract class   AbsBaseFragment extends Fragment {
    Bundle savedState;
    private static final String TAG = AbsBaseFragment.class.getSimpleName();
    //fragment 展示状态
//    public static final int FRAGMENT_STATUS_LOADING = 0XFF01;//加载
//    public static final int FRAGMENT_STATUS_EMPTY = 0XFF02;//空数据
//    public static final int FRAGMENT_STATUS_SUCCESS = 0XFF03;//加载数据成功
//    public static final int FRAGMENT_STATUS_ERROR = 0XFF04;//加载数据出错
    public static final int FRAGMENT_STATUS_LOADING = 1;//加载
    public static final int FRAGMENT_STATUS_EMPTY = 2;//空数据
    public static final int FRAGMENT_STATUS_SUCCESS = 3;//加载数据成功
    public static final int FRAGMENT_STATUS_ERROR = 4;//加载数据出错
    public static final int FRAGMENT_STATUS_NO_NETWORK = 5;//无网络
    public static final int NO_LAYOUT = 0;

    private View mRootView, mContentView;
    private ViewAnimator mViewAnimator;
    private int mFragmentStatus = FRAGMENT_STATUS_LOADING;

    public void setFragmentType(int fragmentType){

    }

    /**
     * 加载失败动画View
     */
    private LoadingViewError loadingViewError;
    /**
     * 加载中动画View
     */
    private LoadingViewAnim loadingViewAnim;

    /**
     * 获取Fragment 当前展示状态
     *
     * @return
     */
    protected int getFragmentStatus() {
        return mFragmentStatus;
    }

    public XMLoadingDialogs loadingDialogs;

    public void initLoadingDialog() {
        loadingDialogs = new XMLoadingDialogs(getActivity());
    }

    /**
     * 显示默认dialog
     * 数据请求中...
     */
    public void showLoading() {
        if (loadingDialogs != null && !loadingDialogs.isShowing()) {
            loadingDialogs.show();
        }
    }

    /**
     * 显示自定义dialog
     * status:1 加载中的状态
     * status:2 加载成功的状态
     * status:3 加载失败的状态
     */
    public void showDialogStatus(int status, String content) {
        if (loadingDialogs != null) {
            loadingDialogs.setDialogStatus(status, content);
            if (!loadingDialogs.isShowing()) {
                loadingDialogs.show();
            }
        }
    }

    /**
     * dialog消失
     * 立即消失
     */
    public void dialogDismiss() {
        if (getActivity() == null || getActivity().isFinishing()) return;
        if (loadingDialogs != null && loadingDialogs.isShowing()) {
            loadingDialogs.dismiss();
        }
    }

    /**
     * dialog消失
     * 延时消失
     */
    public void dialogDismissDelay() {
        if (getActivity() == null || getActivity().isFinishing()) return;
        if (loadingDialogs != null && loadingDialogs.isShowing()) {
            loadingDialogs.dialogDismiss();
        }
    }


    /**
     * 设置fragment 展示状态
     * 可以传入FRAGMENT_STATUS_XXXX 来设置fragment的不同页面展示
     *
     * @param fragmentStatus
     */
    public void setFragmentStatus(int fragmentStatus) {
        if (mViewAnimator == null) return;
        int childCount = mViewAnimator.getChildCount();
        L.i(childCount + "");
        mFragmentStatus = fragmentStatus;
        switch (fragmentStatus) {
            case FRAGMENT_STATUS_LOADING:
                if (childCount == 0) return;
                mViewAnimator.setDisplayedChild(0);
                setDisplayHidden(0);
                break;
            case FRAGMENT_STATUS_SUCCESS:
                if (childCount < 1) return;
                setDisplayHidden(1);
                mViewAnimator.setDisplayedChild(1);
                break;
            case FRAGMENT_STATUS_EMPTY:
                if (childCount < 2) return;
                setDisplayHidden(2);
                mViewAnimator.setDisplayedChild(2);
                break;
            case FRAGMENT_STATUS_ERROR:
                if (childCount < 3) return;
                setDisplayHidden(3);
                mViewAnimator.setDisplayedChild(3);
                break;
            case FRAGMENT_STATUS_NO_NETWORK:
                if (childCount < 4) return;
                setDisplayHidden(4);
                mViewAnimator.setDisplayedChild(4);
                if (loadingViewError != null) {
                    loadingViewError.stopLoading();
                }
                break;
        }
    }

    /**
     * 支持扩展文字和图片
     *
     * @param fragmentStatus
     */
    public void setFragmentStatus(int fragmentStatus, int resourceId, String text) {
        if (mViewAnimator == null) return;
        BaseHolder holder = new BaseHolder(resourceId, text);
        int childCount = mViewAnimator.getChildCount();
        L.i(childCount + "");
        mFragmentStatus = fragmentStatus;
        switch (fragmentStatus) {
            case FRAGMENT_STATUS_LOADING:
                if (childCount == 0) return;
                mViewAnimator.setDisplayedChild(0);
                setDisplayHidden(0, holder);
                break;
            case FRAGMENT_STATUS_SUCCESS:
                if (childCount < 1) return;
                setDisplayHidden(1, holder);
                mViewAnimator.setDisplayedChild(1);
                break;
            case FRAGMENT_STATUS_EMPTY:
                if (childCount < 2) return;
                setDisplayHidden(2, holder);
                mViewAnimator.setDisplayedChild(2);
                break;
            case FRAGMENT_STATUS_ERROR:
                if (childCount < 3) return;
                setDisplayHidden(3, holder);
                mViewAnimator.setDisplayedChild(3);
            case FRAGMENT_STATUS_NO_NETWORK:
                if (childCount < 4) return;
                setDisplayHidden(4, holder);
                mViewAnimator.setDisplayedChild(4);
                break;
        }
    }

    protected void setEmptyResource(BaseHolder holder, View view) {
        if (holder == null || holder.getInfo() == null) return;
        if (view instanceof TextView) {
            ((TextView) view).setText(holder.getInfo());
            Drawable drawable = getResources().getDrawable(holder.getResourceId());
            drawable.setBounds(0, 0,  drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, drawable, null, null);
        }

    }

    private void setDisplayHidden(int i, BaseHolder holder) {
        int count = mViewAnimator.getChildCount();
        View viewLayout = null;
        View viewTv = null;
        View root = mViewAnimator.getChildAt(i);
        if (root == null) return;
        switch (mFragmentStatus) {
            case FRAGMENT_STATUS_NO_NETWORK:
//                viewLayout = root.findViewById(R.id.no_network_layout);
//                viewTv = root.findViewById(R.id.no_network_tv);
                viewLayout = root.findViewById(R.id.no_network_layout_error);
                break;
            case FRAGMENT_STATUS_EMPTY:
                viewLayout = root.findViewById(R.id.empty_layout);
                viewTv = root.findViewById(R.id.tv_empty);
                break;
            case FRAGMENT_STATUS_ERROR:
                viewLayout = root.findViewById(R.id.error_layout);
                viewTv = root.findViewById(R.id.error);
                break;

        }
        setEmptyResource(holder, viewTv);
        if (viewLayout == null || viewTv == null) {
        } else {
            viewLayout.setVisibility(View.VISIBLE);
            viewTv.setVisibility(View.VISIBLE);
            for (int p = 0; p < count; p++) {
                if (p != i) {
                    mViewAnimator.getChildAt(p).setVisibility(View.GONE);
                } else {
                    mViewAnimator.getChildAt(p).setVisibility(View.VISIBLE);
                }
            }
        }

    }

    private void setDisplayHidden(int i) {
        int count = mViewAnimator.getChildCount();
        View viewLayout = null;
        View viewTv = null;
        View root = mViewAnimator.getChildAt(i);
        if (root == null) return;
        switch (mFragmentStatus) {
            case FRAGMENT_STATUS_NO_NETWORK:
//                viewLayout = root.findViewById(R.id.no_network_layout);
                viewTv = root.findViewById(R.id.no_network_tv);
                viewLayout = root.findViewById(R.id.no_network_layout);
                break;
            case FRAGMENT_STATUS_EMPTY:
                viewLayout = root.findViewById(R.id.empty_layout);
                viewTv = root.findViewById(R.id.tv_empty);
                break;
            case FRAGMENT_STATUS_ERROR:
                viewLayout = root.findViewById(R.id.error_layout);
                viewTv = root.findViewById(R.id.error);
                break;

        }
        if (viewLayout == null || viewTv == null) {
        } else {
            viewLayout.setVisibility(View.VISIBLE);
            viewTv.setVisibility(View.VISIBLE);
            for (int p = 0; p < count; p++) {
                if (p != i) {
                    mViewAnimator.getChildAt(p).setVisibility(View.GONE);
                } else {
                    mViewAnimator.getChildAt(p).setVisibility(View.VISIBLE);
                }
            }
        }

    }

    /**
     * 默认错误处理
     *
     * @param response
     * @return
     */
    public boolean defaultErrorHandle(ResponseData response) {
        int error = response.resultCode;
        String msg = response.StateDescription;
        return defaultErrorHandle(error, msg);
    }

    /**
     * 默认无网络处理
     *
     * @param resCode
     * @return
     */
    public void defaultNoNetworkHandle(int resCode, String msg) {
        if (resCode == -1) {
            setFragmentStatus(FRAGMENT_STATUS_NO_NETWORK);
        }
    }

    /**
     * 默认错误处理
     *
     * @return
     */
    public boolean defaultErrorHandle(int error, String msg) {
        switch (error) {
            case AbsAccount.REFUSE_TYPE_FORCED:
            case AbsAccount.REFUSE_TYPE_LOGIN_ALLOPATRY:
            case AbsAccount.REFUSE_TYPE_INVALID:
                BaseApplication.getApplication().logout();
                setFragmentStatus(FRAGMENT_STATUS_ERROR);
                ToastHelper.getInstance().showToast("您的登录已失效，请重新登录");
                return true;
            case AbsAccount.REFUSE_TYPE_PWD_CHANGED:
                L.i("" + "密码修改");
                //处理华为手机们字会换行
                msg = msg.replace("们0", "们 0");
                char[] c = msg.toCharArray();
                for (int i = 0; i < c.length; i++) {
                    if (c[i] == 12288) {
                        c[i] = (char) 32;
                        continue;
                    }
                    if (c[i] > 65280 && c[i] < 65375)
                        c[i] = (char) (c[i] - 65248);
                }
                ToastHelper.getInstance().showToast("您的密码已经修改，请重新登录");
//                ProfileActivity.showRepeatLog(this, getSupportFragmentManager(), new String(c));
                return true;
            case AbsAccount.REFUSE_TYPE_FORBIDDEN:
                ToastHelper.getInstance().showToast("您的账号已禁用");
                return false;
            default:
                setFragmentStatus(FRAGMENT_STATUS_ERROR);
                TextView tv = (TextView) mViewAnimator.findViewById(R.id.error);
                if (tv != null) {
                    tv.setText(msg);
                }
        }
        return false;
    }

    protected int getNoNetworkLayout() {
//        return R.layout.a_no_network;
        return R.layout.a_no_network_error;
    }

    /*
    * 无网络加载失败带动画布局
    * */
    protected void onNoNetWorkLayoutInitAnim(View view) {
        View noNetworkLayout = view.findViewById(R.id.no_network_layout_error);
        loadingViewError = (LoadingViewError) view.findViewById(R.id.common_loading_error);
        if (noNetworkLayout != null) {
            NoNetworkClickListener noNetworkClickListener = new NoNetworkClickListener();
            noNetworkLayout.setOnClickListener(noNetworkClickListener);
        }
    }

    protected int getLoadingLayout() {
        return R.layout.common_loading;
    }

    protected void onLoadingLayoutInit(View view) {
        loadingViewAnim = (LoadingViewAnim) view.findViewById(R.id.common_loading);
    }

    protected int getContentLayout() {
        return R.layout.a_content;
    }

    protected void onContentLayoutInit(View view) {
    }

    protected int getEmptyLayout() {
        return R.layout.a_empty;
    }

    protected void onEmptyLayoutInit(View view) {
        View emptyView = view.findViewById(R.id.empty_layout);
        View tvEmpty = view.findViewById(R.id.tv_empty);
        if (emptyView != null) {
            EmptyClickListener emptyClickListener = new EmptyClickListener();
            emptyView.setOnClickListener(emptyClickListener);
            tvEmpty.setOnClickListener(emptyClickListener);
        }
    }

    protected int getErrorLayout() {
        return R.layout.a_error;
    }

    protected void onErrorLayoutInit(View view) {
        View errorView = view.findViewById(R.id.error_layout);
        View error = view.findViewById(R.id.error);
        if (errorView != null) {
            ErrorClickListener errorClickListener = new ErrorClickListener();
            errorView.setOnClickListener(errorClickListener);
            error.setOnClickListener(errorClickListener);
        }
    }


    protected abstract void init(Bundle savedInstanceState, View contentView);

    protected Context getContext() {
        if (getActivity() instanceof AbsBaseActivity) {
            return ((AbsBaseActivity) getActivity()).getContext();
        }
        return getActivity();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.a_view_animator, container, false);
        mViewAnimator = (ViewAnimator) mRootView.findViewById(R.id.va_view_animator);
        View view;
        //loading布局初始化
        view = inflater.inflate(getLoadingLayout(), null, false);
        onLoadingLayoutInit(view);
        mViewAnimator.addView(view);

        //内容布局
        mContentView = inflater.inflate(getContentLayout(), null, false);
        onContentLayoutInit(mContentView);
        mViewAnimator.addView(mContentView);

        //空布局初始化
        view = inflater.inflate(getEmptyLayout(), null, false);
        onEmptyLayoutInit(view);
        mViewAnimator.addView(view);

        //错误布局初始化
        view = inflater.inflate(getErrorLayout(), null, false);
        onErrorLayoutInit(view);
        mViewAnimator.addView(view);


        //无网络布局初始化
        view = inflater.inflate(getNoNetworkLayout(), null, false);
        onNoNetWorkLayoutInitAnim(view);
        mViewAnimator.addView(view);
        mViewAnimator.setOutAnimation(getActivity().getApplicationContext(), android.R.anim.fade_out);
        mViewAnimator.setInAnimation(getActivity().getApplicationContext(), android.R.anim.fade_in);
        return mRootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.i("onActivityCreated==");
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }
        init(savedInstanceState, mContentView);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (loadingViewAnim != null) loadingViewAnim.clear();
        if (loadingViewError != null) loadingViewError.clear();
        super.onDestroy();
    }

    protected void openEventBus() {
        EventBus.getDefault().register(this);
    }

    protected void eventPost(IBaseEvent event) {
        EventBus.getDefault().post(event);
    }

    protected View getRootview() {
        return mRootView;
    }

    protected void onEmptyClick(View emptyView) {
        L.i("onEmptyClick==" + emptyView);
    }

    protected void onErrorClick(View emptyView) {
        L.i("onErrorClick==" + emptyView);
    }

    protected void onNoNetworkClick(View view) {
        L.i("onNoNetworkClick==" + view);
    }

    private class ErrorClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onErrorClick(v);
        }
    }

    private class EmptyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onEmptyClick(v);
        }
    }

    private class NoNetworkClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (loadingViewError != null) {
                loadingViewError.startLoading();
            }
            onNoNetworkClick(v);
        }
    }

    protected void onFirstTimeLaunched() {

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Here
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Save State Here
        saveStateToArguments();
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private void saveStateToArguments() {
        if (getView() != null)
            savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            if(b==null)return;
            b.putBundle("internalSavedViewState8954201239547", savedState);
        }
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        if(b==null)return false;
        savedState = b.getBundle("internalSavedViewState8954201239547");
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }

    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private void restoreState() {
        if (savedState != null) {
            // For Example
            //tv1.setText(savedState.getString("text"));
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private Bundle saveState() {
        Bundle state = new Bundle();
        // For Example
        //state.putString("text", tv1.getText().toString());
        onSaveState(state);
        return state;
    }

    protected void onSaveState(Bundle outState) {

    }
}
