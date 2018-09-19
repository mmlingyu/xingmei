package com.cheyipai.ui.demo.draglist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.utils.AppInfoHelper;
import com.cheyipai.ui.R;

/**
 * Created by JunyiZhou on 2016/6/21.
 */
public class DragListDemoActivity extends AbsBaseActivity {
    private final static String TITLE = "BannerDemo";
    private final static String TAG = DragListDemoActivity.class.getSimpleName();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_drag_list_demo;
    }

    @Override
    protected void init() {
        AppInfoHelper.printDeviceInf();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, DragListDemoFragment.instance()).commit();
    }

    public static void startDragListDemoActivity(Context context) {
        Log.i(TAG, "startBannerDemoActivity===");
        Intent intent = new Intent(context, DragListDemoActivity.class);
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

