package com.lafresh.smile2.Base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import com.lafresh.smile2.Repository.RepositoryManager;

public interface BaseContract {
    interface View {
        RepositoryManager getRepositoryManager(Context context);

        void setAppToolbar(@IdRes int appToolbarId);

        void setAppTitle(@StringRes int resString);

        void setAppBadge();

        void setBackButtonVisibility(boolean isVisible);

        void setMailButtonVisibility(boolean isVisible);

        void setMessageButtonVisibility(boolean isVisible);

        void setSortButtonVisibility(boolean isVisible);

        void showToast(String message);

        void checkPermission(ValueCallback<Boolean> callback, String... permission);

        void showLoadingDialog();

        void hideLoadingDialog();
    }

    interface Presenter {
        boolean isLogin();

        void forceLogout();
    }

    interface ValueCallback<T> {
        void onValueCallback(int task, T type);
    }
}
