package com.lafresh.smile2.Main.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Business.View.BusinessFragment;
import com.lafresh.smile2.Business.View.BusinessProductFragment;
import com.lafresh.smile2.DrawLots.View.DrawLotsFragment;
import com.lafresh.smile2.Main.Contract.MainContract;
import com.lafresh.smile2.Main.Presenter.MainPresenter;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.MemberCenter.View.MailFileFragment;
import com.lafresh.smile2.MemberCenter.View.MessageListFragment;
import com.lafresh.smile2.MemberCenter.View.WebViewFragment;
import com.lafresh.smile2.Product.View.ProductFragment;
import com.lafresh.smile2.Product.View.ProductMerchantFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.IntentUtils;

import org.json.JSONObject;

import static com.lafresh.smile2.Constant.Constant.REQUEST_COUPON;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MAIL;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MEMBER_CARD;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MEMBER_CENTER;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MESSAGE;
import static com.lafresh.smile2.Constant.Constant.REQUEST_PAY;
import static com.lafresh.smile2.Constant.Constant.REQUEST_POINT;
import static com.lafresh.smile2.Constant.Constant.REQUEST_SHOPPING_CART;
import static com.lafresh.smile2.Constant.Constant.REQUEST_BUSINESS;
import static com.lafresh.smile2.Constant.Constant.REQUEST_LOT_LIST;

public class MainActivity extends BaseActivity implements MainContract.View {
    public static final String TAG = MainActivity.class.getSimpleName();
    private final int BACK_STACK_CLEAR = 0;
    private final int FRAGMENT_REMOVE = 1;

    private BaseFragment willChangeFragment;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private MainContract.Presenter mainPresenter;
    private WebView webView;
    private static TextView tv_shopping_cart;
    public static Uri uri;
    public static Boolean payOkStatus = false;
    public static Boolean bindCardStatus = false;
//    public static String type;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BACK_STACK_CLEAR:
                    removeExistFragment();
                    break;
                case FRAGMENT_REMOVE:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, willChangeFragment, "").commit();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        uri = intent.getData();
        if (uri != null) {
            Log.d("shopping_type_intent", uri.toString());
            payOkStatus = true;
            mainPresenter.checkLoginForShoppingCart();
        }
        if (intent.getExtras() != null) {
//            Log.d("shopping_type_extras",uri.toString());
//        if (intent.getExtras() != null && intent.getExtras().containsKey(TAG)) {
//            bottomNavigationViewEx.setCurrentItem(0);

//            addFragment(MailFileFragment.getInstance());
            //changeTabFragment(MainIndexFragment.getInstance());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setMemberId();
    }

    private void setMemberId() {
        SharedPreferences pref = getSharedPreferences("memberId", MODE_PRIVATE);
        pref.edit()
                .putString("member_id", MemberBean.member_id)
                .apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        refreshBadge();
    }

    private void initView() {
        tv_shopping_cart = findViewById(R.id.tv_shopping_cart);
        setAppToolbar(R.id.toolbar);
        appToolbar.getBtnMail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
            }
        });
        appToolbar.getBtnMessage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMessage(fragment.getClass().getSimpleName());
                appToolbar.setMessageBadgeCount(Integer.parseInt("0"));
            }
        });

        appToolbar.getBtnSort().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                // 获取布局文件
                popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());

                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String sort = "NEW";
                        switch (item.getItemId()) {
                            case R.id.item_CHEAP:
                                sort = "CHEAP";
                                break;
                            case R.id.item_EXPENSIVE:
                                sort = "EXPENSIVE";
                                break;
                            case R.id.item_NEW:
                                sort = "NEW";
                                break;
                        }
                        try {
                            if (ProductFragment.getInstance().getChildFragmentManager() != null) {
                                ProductFragment.getInstance().SortMode(sort);
                            }
                        } catch (Exception e) {
                        }

                        try {
                            if (BusinessProductFragment.getInstance().getChildFragmentManager() != null) {
                                BusinessProductFragment.getInstance().SortMode(sort);
                            }
                        } catch (Exception e) {
                        }
                        return true;
                    }
                });
            }
        });
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));
        refreshBadge();
        bottomNavigationViewEx = findViewById(R.id.navigation);
        bottomNavigationViewEx.enableAnimation(false);
//        bottomNavigationViewEx.setTextSize(10);
//        Typeface typeface = ResourcesCompat.getFont(this, R.font.noto_sans_tc_regular); //字型setting
//        bottomNavigationViewEx.setTypeface(typeface);
        for (int i = 0; i < bottomNavigationViewEx.getItemCount(); i++) {
            bottomNavigationViewEx.getIconAt(i).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_news:
                        changeTabFragment(MainIndexFragment.getInstance());
                        break;
                    case R.id.item_shop:
                        addFragment(ProductMerchantFragment.getInstance());
                        break;
                    case R.id.item_member_card:
                        mainPresenter.checkLoginForMemberCenter();
                        break;
                    case R.id.item_location:
                        changeTabFragment(StoreFragment.getInstance());
                        break;
                    case R.id.item_member_info:
                        if (MemberBean.member_id.length() != 10) {

                            showAlertDialog();

                        } else {
                            mainPresenter.checkLoginForShoppingCart();
                        }
                        break;
                }
                return true;
            }
        });

        Intent intent = getIntent();
        String notify_extra = intent.getStringExtra("NOTIFY_EXTRA"); //前景才會有值
        //Toast.makeText(MainActivity.this, "initView", Toast.LENGTH_SHORT).show();


        if (notify_extra != null && !notify_extra.equals("")) {
            addFragment(MailFileFragment.getInstance());
        }
//        else if (uri !=null){
//            bottomNavigationViewEx.setCurrentItem(4);
//            Log.d("豪豪3", "initView: ");
//        }
        else {
            bottomNavigationViewEx.setCurrentItem(0);
        }
    }

    private void showAlertDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("員購身分不開放此功能!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 在点击"确定"按钮时的处理逻辑
                // 可以在这里添加相应的操作
            }
        });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_MEMBER_CARD:
                    changeTabFragment(MemberCardFragment.getInstance());
                    break;
                case REQUEST_MEMBER_CENTER:
                    changeTabFragment(MemberCenterFragment.getInstance());
                    break;
                case REQUEST_COUPON:
                    addFragment(WebViewFragment.getInstance(R.string.coupon, SmileApplication.WEBVIEW_COUPONS_URL));
                    break;
                case REQUEST_POINT:
                    addFragment(WebViewFragment.getInstance(R.string.my_point, SmileApplication.WEBVIEW_SMILEPOINT_URL));
                    break;
                case REQUEST_MAIL:
                    addFragment(MailFileFragment.getInstance());
                    break;
                case REQUEST_MESSAGE:
                    addFragment(MessageListFragment.getInstance());
                    break;
                case REQUEST_SHOPPING_CART:
                    changeTabFragment(ShoppingCartFragment.getInstance());
                    break;
                case REQUEST_BUSINESS:
                    addFragment(BusinessFragment.getInstance());
                    break;
                case REQUEST_LOT_LIST:
                    addFragment(DrawLotsFragment.getInstance());
                    break;
                case REQUEST_PAY:
                    checkLoginForPay();
                    break;
            }
        }
    }

    @Override
    public void refreshBadge() {
//        mainPresenter.getMailBadgeFromApi();
//        mainPresenter.getMessageBadgeFromApi();
        mainPresenter.getBadgeNumberFromApi();

        setAppBadge();
    }

//    @Override
//    public void setMailBadge(String count) {
//        appToolbar.setMailBadgeCount(Integer.parseInt(count));
//    }
//
//    @Override
//    public void setMessageBadge(String count) {
//        appToolbar.setMessageBadgeCount(Integer.parseInt(count));
//    }

    @Override
    public void setAllBadge(String count) {
        // messageUnreadNum_pushUnreadNum_cartTotalQuantity
        String[] array = count.split("_");

        // 訊息夾未讀、客服留言未讀
        if (array.length >= 2) {
            appToolbar.setMessageBadgeCount(Integer.parseInt(array[0]));
            appToolbar.setMailBadgeCount(Integer.parseInt(array[1]));
        } else {
            appToolbar.setMessageBadgeCount(Integer.parseInt("0"));
            appToolbar.setMailBadgeCount(Integer.parseInt("0"));
        }

        // 購物車商品數量
        if (array.length == 3) {
            tv_shopping_cart.setVisibility(View.VISIBLE);
            tv_shopping_cart.setText(array[2]);
            //Add by Carolyn
            Log.e(TAG, "setAllBadge");
            if (array[2].equals("0")) {
                tv_shopping_cart.setVisibility(View.GONE);
            }
        }
        // 購物車沒東西時未讀數量會收到空字串
        else {
            tv_shopping_cart.setVisibility(View.GONE);
        }
    }

    public void checkLoginForLot() {
        mainPresenter.checkLoginForLot();
    }


    @Override
    public void intentToLogin(int requestCode) {
        IntentUtils.intentToLogin(this, requestCode);
    }


    public BottomNavigationViewEx getBottomNavigationViewEx() {
        return bottomNavigationViewEx;
    }

    private void removeAllStackFragment() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }

        Message message = new Message();
        message.what = BACK_STACK_CLEAR;
        handler.sendMessage(message);
    }

    private void removeExistFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        Message message = new Message();
        message.what = FRAGMENT_REMOVE;
        handler.sendMessage(message);
    }

    @Override
    public void changeTabFragment(BaseFragment willChangeFragment) {
        this.willChangeFragment = willChangeFragment;
        Log.e(TAG, willChangeFragment + "");
        removeAllStackFragment();
    }

    @Override
    public void addFragment(BaseFragment baseFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame, baseFragment, "");
        transaction.addToBackStack("");
        transaction.commit();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void bindWebView(WebView webView) {
        this.webView = webView;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                result.cancel();
                return true;
            }
        });
        webView.getSettings().setUseWideViewPort(true);
        webView.addJavascriptInterface(new AndroidJsInterface(), "hamels");
        webView.addJavascriptInterface(new JavaScriptInterface(), "android");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                setBackButtonVisibility(view.canGoBack());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //setBackButtonVisibility(view.canGoBack());
                //view.loadUrl("javascript:window.OPEN_OCR = () => window.android.openOCR();");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                try {
                    if (url.startsWith("https://maps.app.goo.gl/") || url.startsWith("https://www.google.com.tw/")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } else if (url.indexOf("O2OgwApi/card/Success") != -1 || url.indexOf("O2OgwApi/card/success") != -1) {
                        Log.d("ddd", "success binding card");
                        bindCardStatus = true;
                        return false;
                    } else if (url.startsWith("http:") || url.startsWith("https:")) {
                        return false;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    return false;
                }
//            webView.loadUrl(url);
                return true;
            }
        });
    }

    public class JavaScriptInterface {
        @JavascriptInterface
        public void BindCardComplete() {
            Log.d("ddd", "onbindcardcomplete");
        }
    }

    public class AndroidJsInterface {

        @JavascriptInterface
        public String jsCall_getVariable(String Info) {
            Log.d("ddd", "jsCall_getVariable");
            String sData = "";
            try {
                JSONObject oMemberData = new JSONObject(getUser().toString());
                sData = oMemberData.getString(Info);
            } catch (Exception e) {
//                e.printStackTrace();
            }
            return sData;
        }

        @JavascriptInterface
        public void jsCall_goLoginPage(String page, String function) {
            goPage();
        }

        @JavascriptInterface
        public void jsCall_goShopPage() {
            goProductPage();
        }

        @JavascriptInterface
        public void jsCall_setShopCartNumToApp() {
            refreshBadge();
        }

    }

    public void detachWebView() {
        webView = null;
    }

    public void setTabPage(int page) {
        bottomNavigationViewEx.setCurrentItem(page);
    }

    public void resetPassword() {
        setTabPage(0);
        mainPresenter.logout();
    }

    public void hideBottomNavigation() {
        bottomNavigationViewEx.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        bottomNavigationViewEx.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack() && !bindCardStatus) {
            webView.goBack();
        } else {
            bindCardStatus = false;
            super.onBackPressed();
        }
    }

    public void goMemberCard() {
        mainPresenter.checkLoginForMemberCard();
    }

    public void checkLoginForCoupon() {
        mainPresenter.checkLoginForCoupon();
    }

    public void checkLoginForPay() {
        mainPresenter.checkLoginForPay();
    }

    public void checkLoginForBusiness() {
        mainPresenter.checkLoginForBusiness();
    }

//    public void checkLoginAndShoppingCartCount(){
//        mainPresenter.checkLoginForIndex();
//    }

    public void setShoppingCartCount(String Count) {

        if (!Count.equals("")) {
            tv_shopping_cart.setVisibility(View.VISIBLE);
            tv_shopping_cart.setText(Count);
        } else {
            tv_shopping_cart.setVisibility(View.GONE);
        }
    }

    public void goNewsDetail(String news_id) {
        mainPresenter.goNewsDetail(news_id);
    }

    public User getUser() {
        return mainPresenter.JsCallGetMemberInfo();
    }

    public void goPage() {
        mainPresenter.checkLoginForMemberCenter();
    }

    public void goProductPage() {
//        changeTabFragment(ProductMerchantFragment.getInstance());
        addFragment(ProductMerchantFragment.getInstance());
    }
}
