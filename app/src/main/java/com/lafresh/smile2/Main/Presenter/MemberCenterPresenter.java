package com.lafresh.smile2.Main.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Main.Contract.MemberCenterContract;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;

public class MemberCenterPresenter extends BasePresenter<MemberCenterContract.View> implements MemberCenterContract.Presenter {
    public static final String TAG = MemberCenterPresenter.class.getSimpleName();

    public MemberCenterPresenter(MemberCenterContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getUserInfo() {
        User user = repositoryManager.getUser();
        if (user != null) {
            view.setUserName(user.getName());
        }
    }

    @Override
    public void logout() {
        repositoryManager.callLogOutApi(repositoryManager.getUserID(), new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.redirectToMainPage();
            }
        });
    }
}
