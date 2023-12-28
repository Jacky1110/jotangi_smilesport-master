package com.lafresh.smile2.Order;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Order;

import java.util.List;

public interface OrderListContract {
    interface View extends BaseContract.View {
        void setList(List<Order> orders);
    }

    interface Presenter extends BaseContract.Presenter {

    }
}
