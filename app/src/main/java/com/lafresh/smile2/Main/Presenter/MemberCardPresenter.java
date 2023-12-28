package com.lafresh.smile2.Main.Presenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Main.Contract.MemberCardContract;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;

public class MemberCardPresenter extends BasePresenter<MemberCardContract.View> implements MemberCardContract.Presenter {
    public static final String TAG = MemberCardPresenter.class.getSimpleName();

    public MemberCardPresenter(MemberCardContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMemberInfo() {
        repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User user) {
                view.setUerInfo(user);
//                view.setMemberCardInfo(user.getMembershipCode(), user.getMembershipSrc());
                view.setMemberCardInfo(user.getMembershipCode());
            }
        });
    }
}
