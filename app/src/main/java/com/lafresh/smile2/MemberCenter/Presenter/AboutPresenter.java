package com.lafresh.smile2.MemberCenter.Presenter;

import android.Manifest;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.MemberCenter.AboutContract;
import com.lafresh.smile2.MemberCenter.MemberPointContract;
import com.lafresh.smile2.Repository.Model.About;
import com.lafresh.smile2.Repository.Model.PointHistory;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;

public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {
    public static final String TAG = AboutPresenter.class.getSimpleName();

    public AboutPresenter(AboutContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getAboutData() {
        repositoryManager.callAboutDataApi(new BaseContract.ValueCallback<List<About>>() {
            @Override
            public void onValueCallback(int task, List<About> type) {
                view.setAboutData(type);
            }
        });
    }

    @Override
    public void callPhone(final String phone) {
        view.checkPermission(new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
                if (type) {
                    view.intentToPhoneCall(phone.replace("-", ""));
                }
            }
        }, Manifest.permission.CALL_PHONE);
    }
}
