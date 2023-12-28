package com.lafresh.smile2.Pay.Contract;

import com.lafresh.smile2.Base.BaseContract;

public interface PayContract {
    interface View extends BaseContract.View {
        void setData(Object list);
    }
    interface Presenter extends BaseContract.Presenter {
        void getCardPageList();
        void getBarcode(String cardToken);
        void deleteBindingCard(String cardToken);
    }
}
