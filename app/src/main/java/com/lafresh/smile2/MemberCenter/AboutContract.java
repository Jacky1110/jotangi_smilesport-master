package com.lafresh.smile2.MemberCenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.About;
import com.lafresh.smile2.Repository.Model.PointHistory;
import com.lafresh.smile2.Repository.Model.User;

import java.util.List;

public interface AboutContract {
    interface View extends BaseContract.View {
        void setAboutData(List<About> aboutList);

        void intentToPhoneCall(String phone);
    }

    interface Presenter extends BaseContract.Presenter {
        void getAboutData();

        void callPhone(String phone);
    }
}
