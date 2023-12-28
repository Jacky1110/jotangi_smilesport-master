package com.lafresh.smile2.MemberCenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.MemberPoint;
import com.lafresh.smile2.Repository.Model.PointHistory;
import com.lafresh.smile2.Repository.Model.PointLog;
import com.lafresh.smile2.Repository.Model.User;

import java.util.List;

public interface MemberPointContract {
    interface View extends BaseContract.View {
        void setPoint(List<PointHistory> point);

        void setPointData(User user);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberPoint(String date);

        void getPointData();
    }
}
