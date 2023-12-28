package com.lafresh.smile2.Main.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.User;

public interface MemberCardContract {
    interface View extends BaseContract.View {
        void setUerInfo(User user);

        void setMemberCardInfo(String cardNum);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberInfo();
    }
}
