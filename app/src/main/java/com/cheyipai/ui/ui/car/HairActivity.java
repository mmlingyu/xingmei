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
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
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

    public static WebFragment[] fragments;
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
        fragments[0]= new WebFragment(EventCode.WEB_100010);
        fragments[1]= new WebFragment(EventCode.WEB_100011);
        fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[0]).commit();
        fragmentManager.executePendingTransactions();


    }

    @Override
    protected void onStart() {
        super.onStart();
        setfragment();
    }

    private void initDialog() {
        if (faceDialog == null) {
            faceDialog = new Dialog(this, R.style.time_dialog);
            faceDialog.setCancelable(false);
            faceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            faceDialog.setContentView(R.layout.index_face_window);
            Window window = faceDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            faceDialog.setCanceledOnTouchOutside(true);
            WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dm.widthPixels;
            window.setAttributes(lp);
            faceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                }
            });
        }
    }

    @Override
    protected void init() {
        fragmentManager = this.getSupportFragmentManager();
       // startLocation();
        openEventBus();
        initDialog();
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


    public void onEvent(Integer code){
        if(code == EventCode.WEB_100010){
            fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[0]).commit();
            fragments[0].loadWebview("http://www.baidu.com");
        }else if(code == EventCode.WEB_100011){
            fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[1]).commit();
            fragments[1].loadWebview("http://www.sogou.com");
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
        fragments[0].loadWebview("http://www.baidu.com");

    }
    @OnClick(R.id.rb_tab_hair_service)
    public void onHairService(View view) {

        fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[1]).commit();
        fragmentManager.executePendingTransactions();
        fragments[1].loadWebview("http://www.sogou.com");
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


    public static boolean locationStatus = true;
    @Override
    protected void onResume() {
       /* if(window!=null){
            window.requestGetCityApi();
        }*/


        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}
