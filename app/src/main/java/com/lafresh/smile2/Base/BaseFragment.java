package com.lafresh.smile2.Base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.Objects;

public class BaseFragment extends Fragment implements BaseContract.View {
    @Override
    public RepositoryManager getRepositoryManager(Context context) {
        return new RepositoryManager(context);
    }

    @Override
    public void setAppToolbar(@IdRes int appToolbarId) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppToolbar(appToolbarId);
    }

    @Override
    public void setAppTitle(int resString) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppTitle(resString);
    }

    @Override
    public void setAppBadge() {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppBadge();
    }

    @Override
    public void setBackButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setBackButtonVisibility(isVisible);
    }

    @Override
    public void setMailButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setMailButtonVisibility(isVisible);
    }

    @Override
    public void setMessageButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setMessageButtonVisibility(isVisible);
    }

    @Override
    public void setSortButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setSortButtonVisibility(isVisible);
    }

    @Override
    public void showToast(String message) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).showToast(message);
    }

    @Override
    public void checkPermission(BaseContract.ValueCallback<Boolean> callback, String... permission) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).checkPermission(callback, permission);
    }

    @Override
    public void showLoadingDialog() {
        if (isAdded()) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).showLoadingDialog();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (isAdded()) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).hideLoadingDialog();
        }
    }
}
