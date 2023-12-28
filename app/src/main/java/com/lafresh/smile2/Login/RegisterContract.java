package com.lafresh.smile2.Login;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import com.lafresh.smile2.Base.BaseContract;

public interface RegisterContract {
    interface View extends BaseContract.View {
        void showErrorMessage(@StringRes int stringRes);

        void showErrorMessage(String message);

        void intentToTermsOfUse();

        void intentToVerifyCode();
    }

    interface Presenter extends BaseContract.Presenter {
        void checkInputValue(String name, @IdRes int selectRadioId, String birth, String phone, String password, boolean isAgreeTermsOfUse);
    }
}
