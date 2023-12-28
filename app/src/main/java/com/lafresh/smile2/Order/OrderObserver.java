package com.lafresh.smile2.Order;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Order;

import java.util.List;

public class OrderObserver implements LifecycleObserver {
    public static final String TAG = OrderObserver.class.getSimpleName();

    private boolean callbackFlag = false;

    private List<Order> orders;
    private BaseContract.ValueCallback<List<Order>> valueCallback;

    public OrderObserver(BaseContract.ValueCallback<List<Order>> valueCallback) {
        this.valueCallback = valueCallback;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        callbackFlag = true;
        updateOrders();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        callbackFlag = false;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        updateOrders();
    }

    private void updateOrders() {
        if (callbackFlag && orders != null) {
            callbackFlag = false;
            valueCallback.onValueCallback(0, orders);
        }
    }
}
