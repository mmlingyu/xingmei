package com.cheyipai.ui.fragment.common;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cheyipai.corec.activity.AbsBaseFragment;
import com.cheyipai.ui.CheyipaiApplication;
import com.cheyipai.ui.R;
import com.cheyipai.ui.commons.EventCode;
import com.cheyipai.ui.ui.car.commons.WebViewShowActivity;
import com.ypy.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 景涛 on 2015/9/18.
 */
public class WebFragment extends AbsBaseFragment {

    private int code;
    private boolean isLoad;
    public WebFragment(){

    }
    public static WebFragment newInstance(int code) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putInt("code", code);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.common_webView)
    WebView webView;

    public boolean isLoad() {
        return isLoad;
    }

    @Override
    public void setFragmentType(int fragmentType) {

    }
    @Override
    protected void onNoNetworkClick(View view) {
        super.onNoNetworkClick(view);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getInt("code");
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().post(code);
    }

    public void loadWebview(final String url) {
        webView.clearHistory();

        webView.postDelayed(new Runnable() {

            @Override
            public void run() {
                webView.loadUrl(url);
            }
        }, 500);

    }




    public void setSettings() {
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setBackgroundColor(getResources().getColor(R.color.white_half_transparent));
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              return  false;
            }

            @Override
            public void onPageStarted(final WebView view, String url, Bitmap favicon) {
                setFragmentStatus(FRAGMENT_STATUS_LOADING);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setFragmentStatus(FRAGMENT_STATUS_SUCCESS);
                isLoad = true;

            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 接受所有网站的证书
                super.onReceivedSslError(view, handler, error);
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.webview;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        ButterKnife.bind(this, contentView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings .setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*68);
        String appCachePath=CheyipaiApplication.getInstance().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);


        webSettings.setBlockNetworkImage(true);//解决图片不显示
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        setSettings();
    }
}
