package com.cheyipai.ui;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.ui.commons.Path;
import com.cheyipai.ui.view.ScrollListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
@Route(path = Path.HAIR_SHOP)
public class BarberShopActivity extends AbsBaseActivity {

    private long mExitTime;
    @BindView(R.id.title)
    protected TextView title;
    private ScrollListView faceListView;


    /**
     * 设置布局文件
     * @return
     */
    @Override
    protected int getLayoutID() {

        return R.layout.barber_activity;
    }



    @OnClick(R.id.back_iv)
    public void back(View view){
        this.finish();
    }

    @Override
    protected void init() {


       // startLocation();
        openEventBus();
        ButterKnife.bind(this);
        initView();

    }

    private void initView(){

        //back_iv.setVisibility(View.GONE);
        title.setText("发型库");

    }







    protected void openEventBus() {
        //EventBus.getDefault().register(this);
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
