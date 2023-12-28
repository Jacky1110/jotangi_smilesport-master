package com.lafresh.smile2.Base;

import android.util.Log;

import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.RepositoryManager;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.SharedUtils;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter {
    protected V view;
    protected RepositoryManager repositoryManager;

    public BasePresenter(V view, RepositoryManager repositoryManager) {
        this.view = view;
        this.repositoryManager = repositoryManager;
        repositoryManager.setPresenter(this);
    }

    @Override
    public boolean isLogin() {
        return repositoryManager.getUserLogin();
    }

    @Override
    public void forceLogout() {
        SharedUtils.getInstance().removeAllLocalData(SmileApplication.getInstance());
        view.showToast(SmileApplication.getInstance().getString(R.string.force_logout_message));
    }

    public void startCallApi() {
        Log.e("startCallApi", "fail startCallApi: ");
//        view.showLoadingDialog();
    }

    public void finishApi() {
        Log.e("finishApi", "fail finishApi: ");
//        view.hideLoadingDialog();
    }
}
