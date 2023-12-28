package com.lafresh.smile2.Main.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.SmileApplication;

import java.util.Objects;

public class ShoppingCartFragment extends BaseFragment
{
    public static final String TAG = ShoppingCartFragment.class.getSimpleName();

    private static ShoppingCartFragment fragment;
    private WebView webView;
//    private Uri uri;

    public static ShoppingCartFragment getInstance()
    {
        if (fragment == null)
        {
            fragment = new ShoppingCartFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.tab_shopping_cart);
        ((MainActivity) getActivity()).refreshBadge();
        setBackButtonVisibility(false);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);

        Log.d("豪豪", "onCreate2: " + MainActivity.payOkStatus);
        if (MainActivity.payOkStatus)
        {
//            String scheme = uri.getScheme();
//            String host = uri.getHost();
//            String path = uri.getPath();
//            String query = uri.getQuery();
            String type = MainActivity.uri.getQueryParameter("redirect_url"); //這是需要帶入購物車頁面的url
            Log.d("shopping_type",type);
            if (type != null)
            {
                webView.loadUrl(type);
                MainActivity.payOkStatus = false;
            }
        } else
        {
            webView.loadUrl(SmileApplication.WEBVIEW_SHOPPING_CART_URL);
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}
