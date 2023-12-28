package com.lafresh.smile2;

import android.app.Application;

public class SmileApplication extends Application {
    public static final String TAG = SmileApplication.class.getSimpleName();

    private static final boolean isPrd = true;

    //public static final String DOMAIN_TEST = "https://2020smiletest.jotangi.net:10443/";
    public static final String DOMAIN_TEST = "https://2020smiletest.jotangi.net:443/";
    public static final String DOMAIN_PRD = "https://2020smilesports.jotangi.net/";

//    public static final String
    public static final String DOMAIN = isPrd ? DOMAIN_PRD : DOMAIN_TEST;
    public static final String WEBVIEW_DOMAIN = DOMAIN;
    public static final String WEBVIEW_COUPONS_URL = WEBVIEW_DOMAIN + "/coupon.html";
    public static final String WEBVIEW_TERMS_URL = WEBVIEW_DOMAIN + "/faq.html?id=1";
    public static final String WEBVIEW_LOCATION_URL = WEBVIEW_DOMAIN + "/location.html";
    public static final String WEBVIEW_SHOPPING_CART_URL = WEBVIEW_DOMAIN + "/shoppingcart_list.html";
    public static final String WEBVIEW_NEWS_URL = WEBVIEW_DOMAIN + "/news_detail.html";
    public static final String WEBVIEW_SMILEPOINT_URL = WEBVIEW_DOMAIN + "/my_points.html";
    public static final String WEBVIEW_SIZE_SPEC_URL = WEBVIEW_DOMAIN + "/product_spec.html";
    public static final String WEBVIEW_MESSAGE_URL = WEBVIEW_DOMAIN + "/message_board.html";
    public static final String WEBVIEW_PUSHMAIL_URL = WEBVIEW_DOMAIN + "/push_folder.html";
    public static final String WEBVIEW_ORDER_URL = WEBVIEW_DOMAIN + "/order.html";
    public static final String WEBVIEW_PAY_BIND_CARD = WEBVIEW_DOMAIN + "/smilepay_bindcard.html";

    private static SmileApplication application;

    public static SmileApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
