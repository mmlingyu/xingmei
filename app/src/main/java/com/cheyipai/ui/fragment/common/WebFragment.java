package com.cheyipai.ui.fragment.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cheyipai.corec.activity.AbsBaseFragment;
import com.cheyipai.ui.R;
import com.cheyipai.ui.ui.car.commons.WebViewShowActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 景涛 on 2015/9/18.
 */
public class WebFragment extends AbsBaseFragment {

    @InjectView(R.id.common_webView)
    WebView webView;

    @Override
    public void setFragmentType(int fragmentType) {

    }

    @Override
    protected void onNoNetworkClick(View view) {
        super.onNoNetworkClick(view);
        ((WebViewShowActivity) this.getActivity()).loadUrl();
    }


    public void loadWebview(String url) {
        webView.loadUrl(url);
    }


    public void setSettings() {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(final WebView view, String url, Bitmap favicon) {
                setFragmentStatus(FRAGMENT_STATUS_LOADING);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setFragmentStatus(FRAGMENT_STATUS_SUCCESS);
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.webview;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        ButterKnife.inject(this, contentView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setSettings();
        ((WebViewShowActivity) this.getActivity()).loadUrl();
    }
}
