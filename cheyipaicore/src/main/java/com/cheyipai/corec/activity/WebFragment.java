package com.cheyipai.corec.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cheyipai.corec.R;


/**
 * Created by 景涛 on 2015/9/18.
 */
public class WebFragment extends AbsBaseFragment {

    public static   WebView webView;

    @Override
    public void setFragmentType(int fragmentType) {

    }

    /**
     * 无网络点击事件，重新加载
     * @param view
     */
    @Override
    protected void onNoNetworkClick(View view) {
        super.onNoNetworkClick(view);
        ((WebViewActivity) this.getActivity()).loadUrl();
    }


    /**
     * 加载webView
     * @param url
     */
    public void loadWebView(String url) {
        webView.loadUrl(url);
    }


    /**
     * webView设置
     */
    public void setSettings() {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                // Otherwise allow the OS to handle things like tel, mailto, etc.
                // 可以打开h5页面的电话、邮件等
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
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
        webView = (WebView) contentView.findViewById(R.id.common_webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setSettings();
        ((WebViewActivity) this.getActivity()).loadUrl();
    }

}
