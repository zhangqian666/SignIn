package com.iptv.signin.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iptv.signin.R;
import com.iptv.signin.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieWebViewActivity extends AppCompatActivity {

    @BindView(R.id.web_movie)
    WebView webView;
    private String baseUrl;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_web_view);
        ButterKnife.bind(this);
        baseUrl = getIntent().getStringExtra("URL");
        LogUtil.e(baseUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Object() {
            public void clickOnAndroid() {
                mHandler.post(new Runnable() {
                    public void run() {
                        webView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "demo");
        webView.getSettings().getUseWideViewPort();
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(baseUrl);
    }
}
