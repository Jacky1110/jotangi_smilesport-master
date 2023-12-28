package com.lafresh.smile2.Main.View;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.SmileApplication;

import java.util.Objects;

public class MessageFragment extends BaseFragment {
    public static final String TAG = MessageFragment.class.getSimpleName();

    private static MessageFragment fragment;
    private WebView webView;

    public static MessageFragment getInstance() {
        if (fragment == null) {
            fragment = new MessageFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.message_list);
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (isAdded()) {
                    ((MainActivity) getActivity()).setBackButtonVisibility(view.canGoBack());
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isAdded()) {
                    ((MainActivity) getActivity()).setBackButtonVisibility(view.canGoBack());
                }
            }
        });
        webView.loadUrl(SmileApplication.WEBVIEW_MESSAGE_URL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}
