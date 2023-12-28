package com.lafresh.smile2.MemberCenter;

import android.support.annotation.StringRes;

import com.lafresh.smile2.Base.BaseContract;

public interface PasswordChangeContract {
    interface View extends BaseContract.View {
        void showInputErrorAlert(@StringRes int stringRes);

        void showFinishAlert();

        void showErrorMessage(String stringRes);
    }

    interface Presenter extends BaseContract.Presenter {
        void checkInputAndSendApi(String existPsd, String newPsd, String newPsdAgain);
    }
}
