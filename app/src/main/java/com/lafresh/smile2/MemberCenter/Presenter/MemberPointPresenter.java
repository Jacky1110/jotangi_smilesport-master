package com.lafresh.smile2.MemberCenter.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.MemberCenter.MemberPointContract;
import com.lafresh.smile2.Repository.Model.MemberPoint;
import com.lafresh.smile2.Repository.Model.PointHistory;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;

public class MemberPointPresenter extends BasePresenter<MemberPointContract.View> implements MemberPointContract.Presenter {
    public static final String TAG = MemberPointPresenter.class.getSimpleName();

    public MemberPointPresenter(MemberPointContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMemberPoint(String date) {
        repositoryManager.callMemberPointHistoryApi(date ,new BaseContract.ValueCallback<List<PointHistory>>() {
            @Override
            public void onValueCallback(int task, List<PointHistory> pointHistory) {
                view.setPoint(pointHistory);
//                view.setPointLogList(memberPoint.getInTimeLog());
            }
        });
    }

    @Override
    public void getPointData() {
        repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User user) {
                view.setPointData(user);
            }
        });
    }
}
