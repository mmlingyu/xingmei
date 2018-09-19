package com.cheyipai.corec.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.cheyipai.corec.R;
import com.cheyipai.corec.utils.NetUtil;

/**
 * h5页面
 * Created by 景涛 on 2015/9/17.
 */
public class WebViewActivity extends AbsBaseActivity {

    private WebFragment webFragment;
    private TextView mTitle;

    private Intent mIntent;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void init() {
        mTitle = (TextView) findViewById(R.id.title_tv);
        webFragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.web_fragment);
        mIntent = this.getIntent();
        mTitle.setText(mIntent.getStringExtra("title") == null ? "" : mIntent.getStringExtra("title"));
    }

    /**
     * 返回按钮点击事件
     *
     * @param view
     */
    public void backClick(View view) {
        this.finish();
    }

    /**
     * 开启webView页面
     * @param activity
     * @param link 网页链接
     */
    public static void startWebViewActivity(Activity activity, String link) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", link);
        activity.startActivity(intent);
    }

    /**
     * 开启webView页面
     * @param activity
     * @param link 网页链接
     * @param title 页面标题
     */
    public static void startWebViewActivity(Activity activity, String link, String title) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", link);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    /**
     * 根据url加载h5
     */
    public void loadUrl() {
        String url = mIntent.getStringExtra("url");
        if (!NetUtil.isConnnected()) {
            webFragment.setFragmentStatus(AbsBaseFragment.FRAGMENT_STATUS_NO_NETWORK);
        } else {
            webFragment.loadWebView(url);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // do nothing
            return true;
        }
        if (WebFragment.webView != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK && WebFragment.webView.canGoBack()) {
                WebFragment.webView.goBack();// 返回web的前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
