package com.lafresh.smile2.Main.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Repository.Model.User;

public interface MainContract {
    interface View extends BaseContract.View {
        void refreshBadge();

//        void setMailBadge(String count);
//
//        void setMessageBadge(String count);

        void setAllBadge(String count);

        void intentToLogin(int requestCode);

        void setShoppingCartCount(String Count);

        void changeTabFragment(BaseFragment willChangeFragment);

        void addFragment(BaseFragment baseFragment);
    }

    interface Presenter extends BaseContract.Presenter {
//        void getMailBadgeFromApi();
//
//        void getMessageBadgeFromApi();

        void getBadgeNumberFromApi();

        void checkLoginForMemberCard();

        void checkLoginForCoupon();

        void checkLoginForPay();

        void checkLoginForLot();

        void checkLoginForBusiness();

//        void checkLoginForIndex();

        void goNewsDetail(String news_id);

        User JsCallGetMemberInfo();

        void checkLoginForMemberCenter();

        void checkLoginForShoppingCart();

        void checkLoginForMail(String fragmentName);

        void checkLoginForMessage(String fragmentName);

        void logout();
    }
}
