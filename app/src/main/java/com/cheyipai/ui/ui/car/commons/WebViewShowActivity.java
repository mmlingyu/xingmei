package com.cheyipai.ui.ui.car.commons;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.activity.AbsBaseFragment;
import com.cheyipai.corec.utils.NetUtil;
import com.cheyipai.ui.R;
import com.cheyipai.ui.fragment.common.WebFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * h5页面
 * Created by 景涛 on 2015/9/17.
 */
public class WebViewShowActivity extends AbsBaseActivity {

    WebFragment webFragment;
    @InjectView(R.id.back_iv)
    ImageView backIv;
    @OnClick(R.id.back_iv)
    public void back(){
        this.finish();
    }


    @Override
    protected int getLayoutID() {
        return R.layout.user_car_activity;
    }

    private Intent mIntent;

    @InjectView(R.id.title_tv)
    TextView title;
    @Override
    protected void init() {
        ButterKnife.inject(this);
        webFragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.web_fragment);
        mIntent = this.getIntent();
        backIv.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        title.setText(mIntent.getStringExtra("title")==null?"":mIntent.getStringExtra("title"));
    }

    public static void startUserCarActivity(Activity activity,String link){
        Intent intent = new Intent(activity,WebViewShowActivity.class);
        intent.putExtra("url",link);
        activity.startActivity(intent);
        //IntentUtils.startIntent(BannerFragment.this.getContext(),UserCarsActivity.class,intent);
    }

    public static void startUserCarActivity(Activity activity,String link,String title){
        Intent intent = new Intent(activity,WebViewShowActivity.class);
        intent.putExtra("url",link);
        intent.putExtra("title",title);
        activity.startActivity(intent);
        //IntentUtils.startIntent(BannerFragment.this.getContext(),UserCarsActivity.class,intent);
    }
    /**
     * 根据url加载h5
     */
    public void loadUrl(){
        String url = mIntent.getStringExtra("url");
        if(!NetUtil.isConnnected()){
            webFragment.setFragmentStatus(AbsBaseFragment.FRAGMENT_STATUS_NO_NETWORK);

        }else {
            webFragment.loadWebview(url);
//            webFragment.setFragmentStatus(AbsBaseFragment.FRAGMENT_STATUS_SUCCESS);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // do nothing
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
