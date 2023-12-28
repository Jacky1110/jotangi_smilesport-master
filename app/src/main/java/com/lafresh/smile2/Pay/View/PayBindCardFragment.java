package com.lafresh.smile2.Pay.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.R;
import com.lafresh.smile2.SmileApplication;

import java.util.Objects;

public class PayBindCardFragment extends BaseFragment {
    private static PayBindCardFragment fragment;
    private WebView webView;

    public static PayBindCardFragment getInstance(){
        if(fragment == null)
            fragment = new PayBindCardFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.tab_pay);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        ((MainActivity) getActivity()).bindWebView(webView);
        webView.loadUrl(SmileApplication.WEBVIEW_PAY_BIND_CARD);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }

}
