package com.cheyipai.ui.demo.webview;

import android.view.View;

import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.activity.WebViewActivity;
import com.cheyipai.ui.R;

public class WebViewDemoActivity extends AbsBaseActivity {


    String url = "http://m.wxb.com.cn/mobile/?channelNo=B10122&channelSite=s01";
    String title = "喂小宝";

    @Override
    protected int getLayoutID() {
        return R.layout.activity_web_view_demo;
    }

    @Override
    protected boolean isOpenActionBar() {
        return true;
    }

    @Override
    protected String getActionBarTitle() {
        return "WebViewDemo";
    }

    @Override
    protected void init() {

        findViewById(R.id.btn_go_web_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启一个webView页面
                WebViewActivity.startWebViewActivity(WebViewDemoActivity.this, url, title);
            }
        });

    }

}
