package com.lafresh.smile2.Main.Presenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Business.View.BusinessFragment;
import com.lafresh.smile2.DrawLots.View.DrawLotsFragment;
import com.lafresh.smile2.Main.Contract.MainContract;

import com.lafresh.smile2.Main.View.MemberCardFragment;
import com.lafresh.smile2.Main.View.MemberCenterFragment;

import com.lafresh.smile2.Main.View.ShoppingCartFragment;
import com.lafresh.smile2.MemberCenter.View.MailDetailFragment;
import com.lafresh.smile2.MemberCenter.View.MailFileFragment;
import com.lafresh.smile2.MemberCenter.View.MessageListFragment;
import com.lafresh.smile2.MemberCenter.View.WebViewFragment;
import com.lafresh.smile2.Pay.View.PayFragment;
import com.lafresh.smile2.Pay.View.PayInitFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;
import com.lafresh.smile2.SmileApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.lafresh.smile2.Constant.Constant.REQUEST_COUPON;
import static com.lafresh.smile2.Constant.Constant.REQUEST_LOT_LIST;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MAIL;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MEMBER_CARD;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MEMBER_CENTER;
import static com.lafresh.smile2.Constant.Constant.REQUEST_MESSAGE;
import static com.lafresh.smile2.Constant.Constant.REQUEST_BUSINESS;
import static com.lafresh.smile2.Constant.Constant.REQUEST_PAY;
import static com.lafresh.smile2.Constant.Constant.REQUEST_SHOPPING_CART;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public static final String TAG = MainPresenter.class.getSimpleName();

    public MainPresenter(MainContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

//    @Override
//    public void getMailBadgeFromApi() {
//        if (repositoryManager.getUserLogin()) {
//            repositoryManager.callGetMailBadgeApi(new BaseContract.ValueCallback<String>() {
//                @Override
//                public void onValueCallback(int task, String type) {
//                    view.setMailBadge(type);
//                }
//            });
//        } else {
//            view.setMailBadge("0");
//        }
//    }

//    @Override
//    public void getMessageBadgeFromApi() {
//        if (repositoryManager.getUserLogin()) {
//            repositoryManager.callGetMessageBadgeApi(new BaseContract.ValueCallback<String>() {
//                @Override
//                public void onValueCallback(int task, String type) {
//                    view.setMessageBadge(type);
//                }
//            });
//        } else {
//            view.setMessageBadge("0");
//        }
//    }

    @Override
    public void getBadgeNumberFromApi() {
        if (repositoryManager.getUserLogin()) {
            repositoryManager.callGetBadgeNumberApi(new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.setAllBadge(type);

                    String[] array= type.split("_");
                    if(array.length==3){
                        repositoryManager.saveShoppingCartCount(array[2]);
                    }else{
                        repositoryManager.saveShoppingCartCount("0");
                    }
                }
            });
        } else {
            view.setAllBadge("0_0_0");
        }
    }

    @Override
    public void checkLoginForMemberCard() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(MemberCardFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MEMBER_CARD);
        }
    }

    @Override
    public void checkLoginForCoupon() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(WebViewFragment.getInstance(R.string.coupon, SmileApplication.WEBVIEW_COUPONS_URL));
        } else {
            view.intentToLogin(REQUEST_COUPON);
        }
    }

    @Override
    public void checkLoginForPay() {
        if(repositoryManager.getUserLogin()){
            if(repositoryManager.getPayCode().length()>0)
                view.addFragment(PayFragment.getInstance());
            else
                view.addFragment(PayInitFragment.getInstance());
        }else{
            view.intentToLogin(REQUEST_PAY);
        }
    }

    @Override
    public void checkLoginForLot() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(DrawLotsFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_LOT_LIST);
        }
    }

    @Override
    public void checkLoginForBusiness() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(BusinessFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_BUSINESS);
        }
    }

//    @Override
//    public void checkLoginForIndex() {
//        if (repositoryManager.getUserLogin()) {
//            repositoryManager.getShoppingCartCountApi(new BaseContract.ValueCallback<List<Object>>() {
//                @Override
//                public void onValueCallback(int task, List<Object> type) {
//                    String Count = "";
//                    try {
//                        JSONObject jsonObject = new JSONObject(type.get(0).toString());
//                        Count = jsonObject.getString("total_quantity");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    repositoryManager.saveShoppingCartCount(Count);
//                    view.setShoppingCartCount(Count);
//                }
//            });
//        }
//        else{
//            view.setShoppingCartCount("");
//        }
//    }

    @Override
    public void goNewsDetail(String news_id) {
        view.addFragment(WebViewFragment.getInstance(R.string.tab_news, SmileApplication.WEBVIEW_NEWS_URL,news_id));
    }

    @Override
    public User JsCallGetMemberInfo() {
        return repositoryManager.getUser();
    }

    @Override
    public void checkLoginForMemberCenter() {
        if (repositoryManager.getUserLogin()) {
            view.changeTabFragment(MemberCenterFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MEMBER_CENTER);
        }
    }

    @Override
    public void checkLoginForShoppingCart() {
        if (repositoryManager.getUserLogin()) {
            view.changeTabFragment(ShoppingCartFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_SHOPPING_CART);
        }
    }

    @Override
    public void checkLoginForMail(String fragmentName) {
        if (fragmentName.equals(MailFileFragment.TAG) || fragmentName.equals(MailDetailFragment.TAG)) {
            return;
        }
        if (repositoryManager.getUserLogin()) {
            view.addFragment(MailFileFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MAIL);
        }    }

    @Override
    public void checkLoginForMessage(String fragmentName) {
        if (fragmentName.equals(MessageListFragment.TAG)) {
            return;
        }

        if (repositoryManager.getUserLogin()) {
            view.addFragment(MessageListFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MESSAGE);
        }
    }

    @Override
    public void logout() {
        repositoryManager.callLogOutApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                checkLoginForMemberCenter();
                view.setAllBadge("0_0_");
            }
        });
    }
}
