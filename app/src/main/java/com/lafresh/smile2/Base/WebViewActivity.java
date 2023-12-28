package com.lafresh.smile2.Base;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lafresh.smile2.R;

import static com.lafresh.smile2.Constant.Constant.TITLE;
import static com.lafresh.smile2.Constant.Constant.URL;

public class WebViewActivity extends BaseActivity {
    public static final String TAG = WebViewActivity.class.getSimpleName();

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(getIntent().getExtras().getInt(TITLE));
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);

        webView = findViewById(R.id.web_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getExtras().getString(URL));
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
