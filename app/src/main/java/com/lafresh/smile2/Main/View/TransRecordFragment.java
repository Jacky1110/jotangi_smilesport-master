package com.lafresh.smile2.Main.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.SmileApplication;

import java.util.List;
import java.util.Objects;

import okhttp3.Cookie;

public class TransRecordFragment extends BaseFragment {
    public static final String TAG = TransRecordFragment.class.getSimpleName();

    private static TransRecordFragment fragment;
    private WebView webView;

    public static TransRecordFragment getInstance() {
        if (fragment == null) {
            fragment = new TransRecordFragment();
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
        ((MainActivity) getActivity()).setAppTitle(R.string.trans_record);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).bindWebView(webView);
//
////        setCookies();
        webView.loadUrl(SmileApplication.WEBVIEW_ORDER_URL);
    }

    private void setCookies() {
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        SharedPrefsCookiePersistor sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(getContext());
        final List<Cookie> cookies = sharedPrefsCookiePersistor.loadAll();

        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                for (Cookie cookie : cookies) {
                    String cookieString = cookie.name() + "=" + cookie.value() + "; Domain=" + cookie.domain();
                    cookieManager.setCookie(cookie.domain(), cookieString);
                    Log.d(TAG, "cookieString: " + cookieString);
                }
            }
        });
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
//    }
}
