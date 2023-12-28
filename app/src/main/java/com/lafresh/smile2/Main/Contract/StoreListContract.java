package com.lafresh.smile2.Main.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Store;

import java.util.List;

public interface StoreListContract {
    interface View extends BaseContract.View {
        void changeToNewAreaType(int area);

        void setStoreList(List<Store> stores);

        void setAreaSelectVisible(boolean isVisible);

        boolean getAreaSelectVisible();

        void intentToGoogleMap(String address);

        void intentToPhoneCall(String phone);
    }

    interface Presenter extends BaseContract.Presenter {
        void setArea(int area);

        void setType(int type, boolean checked);
    }
}
