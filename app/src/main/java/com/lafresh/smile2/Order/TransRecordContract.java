package com.lafresh.smile2.Order;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Order;

import java.util.List;

public interface TransRecordContract {
    interface View extends BaseContract.View {
        void addReturnFragment(Order order, int status);

        void setStatusPage(int page);

        void onOrderListReadyCallback();
    }

    interface Presenter extends BaseContract.Presenter {
        void getOrders();

        List<Order> getStatusOrders(int status);
    }
}
