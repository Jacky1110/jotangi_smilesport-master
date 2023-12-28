package com.lafresh.smile2.Business.Contract;
import com.lafresh.smile2.Base.BaseContract;

public interface BusinessContract {
    interface View extends BaseContract.View {
        void goBusinessProduct(String business_sale_id);

        void ErrorAlert(String errorMessage);
    }

    interface Presenter extends BaseContract.Presenter {
        void checkBusinessCode(String sale_password);
    }
}
