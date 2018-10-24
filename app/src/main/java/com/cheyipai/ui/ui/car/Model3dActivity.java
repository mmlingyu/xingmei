package com.cheyipai.ui.ui.car;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.ui.R;
import com.cheyipai.ui.api.D3OauthApi;
import com.cheyipai.ui.bean.DownF3d;
import com.cheyipai.ui.bean.DownObj;
import com.cheyipai.ui.bean.F3d;
import com.cheyipai.ui.bean.F3dCallback;
import com.cheyipai.ui.bean.F3dStatus;
import com.cheyipai.ui.bean.F3dUploadCallback;
import com.cheyipai.ui.bean.Oauth;
import com.cheyipai.ui.bean.OauthCallback;
import com.cheyipai.ui.commons.EventCode;
import com.cheyipai.ui.commons.Path;
import com.cheyipai.ui.fragment.common.WebFragment;
import com.cheyipai.ui.utils.DialogUtils;
import com.cheyipai.ui.utils.IntentUtils;
import com.cheyipai.ui.view.SelectPicPopupWindow;
import com.ypy.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
@Route(path = Path.HAIR_3D)
public class Model3dActivity extends AbsBaseActivity {
    private FragmentManager fragmentManager;
    public static WebFragment fragments;
    private static final String intro="http://192.168.96.254:8080/f3dthree/main.html";
    /**
     * 设置布局文件
     * @return
     */
    @Override
    protected int getLayoutID() {

        return R.layout.hair3d_activity;
    }

    @OnClick(R.id.back_iv)
    public void back(View view){
        this.finish();
    }

    /**初始化fragment*/
    public void setfragment()
    {
        if(fragments==null) {
            fragments = new WebFragment();
            fragmentManager.beginTransaction().replace(R.id.ll_tab, fragments).commit();
            fragmentManager.executePendingTransactions();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        setfragment();
        if(!fragments.isLoad())
            fragments.loadWebview(intro);
    }

    @Override
    protected void init() {
        fragmentManager = this.getSupportFragmentManager();
        ButterKnife.bind(this);

    }


}
