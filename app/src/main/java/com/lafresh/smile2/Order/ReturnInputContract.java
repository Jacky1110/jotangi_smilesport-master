package com.lafresh.smile2.Order;

import android.support.annotation.StringRes;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.OrderProduct;

import java.util.ArrayList;

public interface ReturnInputContract {
    interface View extends BaseContract.View {
        void showAlert(@StringRes int message);

        void showAlert(String message);

        void showReturnResult(boolean isSuccess, String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void checkInputAndSend(ArrayList<OrderProduct> products, int returnType, String name, String address, String phone, String email);
    }
}
