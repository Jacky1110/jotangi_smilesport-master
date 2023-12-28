package com.lafresh.smile2.MemberCenter;

import android.support.annotation.StringRes;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Address;
import com.lafresh.smile2.Repository.Model.User;

import java.util.List;

public interface MemberInfoChangeContract {
    interface View extends BaseContract.View {
        void setUser(User user);

        void setPropertyCode(List<Address> addressList);

        void setBirthDayModifiedOrNot(String message);

        void showAlert(@StringRes int messageId);

        void showAlert(String message);

        void redirectToMainPage();
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberInfo();

        void getPropertyData();

        void updateMember(String city_code , String area_code, String address,String email, String birth);

        void logout();
    }
}
