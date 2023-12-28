package com.lafresh.smile2.Order.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseAdapter;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Order.View.OrderListFragment;
import com.lafresh.smile2.Order.View.OrderListHolder;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends BaseAdapter<OrderListHolder> {
    public static final String TAG = OrderListAdapter.class.getSimpleName();

    private int status;
    private List<Order> orders = new ArrayList<>();
    private BaseContract.ValueCallback<Order> buttonCallback;

    public OrderListAdapter(int status, BaseContract.ValueCallback<Order> buttonCallback) {
        this.status = status;
        this.buttonCallback = buttonCallback;
    }

    @NonNull
    @Override
    public OrderListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_list, viewGroup, false);
        return new OrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListHolder orderListHolder, final int position) {
        orderListHolder.setOrder(orders.get(position), status);
        orderListHolder.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback.onValueCallback(OrderListFragment.TASK_RETURN, orders.get(position));
            }
        });

        orderListHolder.btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback.onValueCallback(OrderListFragment.TASK_EXCHANGE, orders.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }
}
