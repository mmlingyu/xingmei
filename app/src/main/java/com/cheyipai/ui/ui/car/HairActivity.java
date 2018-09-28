package com.cheyipai.ui.ui.car;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.modules.config.GlobalConfigHelper;
import com.cheyipai.ui.HomeActivity;
import com.cheyipai.ui.R;
import com.cheyipai.ui.commons.EventCode;
import com.cheyipai.ui.commons.Path;
import com.cheyipai.ui.fragment.BannerFragment;
import com.cheyipai.ui.fragment.common.WebFragment;
import com.cheyipai.ui.utils.DialogUtils;
import com.cheyipai.ui.view.ScrollListView;
import com.cheyipai.ui.view.SelectPicPopupWindow;
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 首页
 */
@Route(path = Path.HAIR)
public class HairActivity extends AbsBaseActivity {
    private FragmentManager fragmentManager;
    private Dialog faceDialog;
    @InjectView(R.id.hair_ll)
    protected RelativeLayout hair_ll;
    private SelectPicPopupWindow picPopupWindow;
    public static WebFragment[] fragments;
    private static final String intro="https://mp.weixin.qq.com/s?__biz=MzIyNzM2ODIyMQ==&mid=2247483695&idx=1&sn=ace240e37f3d9be9e485364791e2635e&mpshare=1&scene=1&srcid=0928dmYEHuGEZcOa12GvEdgA&pass_ticket=4SbZtPID2TQWhKPSJp3MPlQ3GaENP%2F%2Br2foKqDmc6JVLkaJC%2BZ2qi5GhZuxT8eYi#rd";
    private static final String service="https://mp.weixin.qq.com/s?__biz=MzIxMDkzNjU3Mw==&mid=2247483751&idx=1&sn=d6f72d65e56a8c7e44e8f691c0f3ebcb&chksm=975db6f5a02a3fe3633bcaee03833c79af19e307f3b3191efda75569d2434c616c9bffd63f27&mpshare=1&scene=1&srcid=09280lYNuqyeSVcZSBHvv78D&pass_ticket=4SbZtPID2TQWhKPSJp3MPlQ3GaENP%2F%2Br2foKqDmc6JVLkaJC%2BZ2qi5GhZuxT8eYi#rd";
    /**
     * 设置布局文件
     * @return
     */
    @Override
    protected int getLayoutID() {

        return R.layout.hair_activity;
    }

    @OnClick(R.id.back_iv)
    public void back(View view){
        this.finish();
    }

    /**初始化fragment*/
    public void setfragment()
    {
        fragments = new WebFragment[2];
        fragments[0]=  WebFragment.newInstance(EventCode.WEB_100010);
        fragments[1]=  WebFragment.newInstance(EventCode.WEB_100011);
        fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[0]).commit();
        fragmentManager.executePendingTransactions();


    }

    @Override
    protected void onStart() {
        super.onStart();
        setfragment();
    }



    @Override
    protected void init() {
        fragmentManager = this.getSupportFragmentManager();
       // startLocation();
        openEventBus();
       // initDialog();
        ButterKnife.inject(this);
        initView();

        openAppStatitcs();
       //TextViewh all_tv.setText(getRadiusGradientSpan("全部",0xFFec4ce6,0xfffa4a6f));
    }

    private void initView(){
        DialogUtils.setShapeDrawable(hair_ll);
    }
    private void openAppStatitcs(){

    }



    @OnClick(R.id.use_ll)
    public void onUserClick(View view){
        if(picPopupWindow==null)
        picPopupWindow = new SelectPicPopupWindow(this,null, Arrays.asList("清新自然风","甜美淑女风","职场女神风","清新自然风","甜美淑女风","职场女神风"));
        picPopupWindow.showAtLocation(this.findViewById(R.id.title), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popupView.startAnimation(animation);
    }

    public void onEvent(Integer code){
        if(code == EventCode.WEB_100010){
            fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[0]).commit();
            fragments[0].loadWebview(intro);
        }else if(code == EventCode.WEB_100011){
            fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[1]).commit();
            fragments[1].loadWebview(service);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 点击事件
     * @param
     */
    @OnClick(R.id.rb_tab_hair_intro)
    public void onHairIntro(View view) {

        fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[0]).commit();
        fragmentManager.executePendingTransactions();
        if(!fragments[0].isLoad())
        fragments[0].loadWebview(intro);

    }
    @OnClick(R.id.rb_tab_hair_service)
    public void onHairService(View view) {

        fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[1]).commit();
        fragmentManager.executePendingTransactions();
        if(!fragments[1].isLoad())
        fragments[1].loadWebview(service);
    }

    protected void openEventBus() {
        EventBus.getDefault().register(this);
    }





    /**
     * 两次点击back退出appp
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

                this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}
