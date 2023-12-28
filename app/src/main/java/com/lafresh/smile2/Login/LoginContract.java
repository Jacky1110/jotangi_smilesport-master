package com.lafresh.smile2.Login;

import com.lafresh.smile2.Base.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.View {
        void showErrorAlert(String message);

        void showVerifyErrorAlert(String message);

        void showGoRegistAlert(String message);

        void showNoInputCellphoneHint();

        void intentToRegister();

        void intentToForgetPassword(String cellphone);

        void intentToTermsOfUse();

        void showEmptyPhoneAlert();

        void showEmptyPasswordAlert();

        void setResultOkFinishActivity();

        void doForgetPassword(String cellphone);
    }

    interface Presenter extends BaseContract.Presenter {
        void register();

        void login(String cellphone, String password);

        void forgetPassword(String cellphone);

        void checkAccount(String cellphone);
    }
}
