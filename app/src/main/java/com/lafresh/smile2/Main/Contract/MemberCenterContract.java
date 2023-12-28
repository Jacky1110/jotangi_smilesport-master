package com.lafresh.smile2.Main.Contract;

import com.lafresh.smile2.Base.BaseContract;

public interface MemberCenterContract {
    interface View extends BaseContract.View {
        void setUserName(String name);

        void redirectToMainPage();
    }

    interface Presenter extends BaseContract.Presenter {
        void getUserInfo();

        void logout();
    }
}
