package com.lafresh.smile2.Order.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Order.Adapter.OrderListAdapter;
import com.lafresh.smile2.Order.OrderListContract;
import com.lafresh.smile2.Order.OrderObserver;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Order;

import java.util.List;
import java.util.Objects;

import static com.lafresh.smile2.Constant.Constant.ORDER_STATUS_WAITING_PAY;
import static com.lafresh.smile2.Order.View.ReturnListFragment.STATUS_EXCHANGE;
import static com.lafresh.smile2.Order.View.ReturnListFragment.STATUS_RETURN;

public class OrderListFragment extends BaseFragment implements OrderListContract.View, View.OnClickListener {
    public static final String TAG = OrderListFragment.class.getSimpleName();
    public static final int TASK_RETURN = 0;
    public static final int TASK_EXCHANGE = 1;

    private int status = ORDER_STATUS_WAITING_PAY;
    private OrderObserver orderObserver;

    private Group noOrderGroup;
    private TextView tvSend;
    private RecyclerView recyclerView;
    private OrderListAdapter orderListAdapter;

    public static OrderListFragment getInstance(int status) {
        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, status);
        orderListFragment.setArguments(bundle);

        return orderListFragment;
    }

    public OrderListFragment() {
        orderObserver = new OrderObserver(new BaseContract.ValueCallback<List<Order>>() {
            @Override
            public void onValueCallback(int task, List<Order> orders) {
                noOrderGroup.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                orderListAdapter.setOrders(orders);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLifecycle().addObserver(orderObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getLifecycle().removeObserver(orderObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvSend = view.findViewById(R.id.tv_send);
        tvSend.setOnClickListener(this);
        noOrderGroup = view.findViewById(R.id.no_order_group);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);

        if (getArguments() != null) {
            status = getArguments().getInt(TAG, 0);
        }

        orderListAdapter = new OrderListAdapter(status, new BaseContract.ValueCallback<Order>() {
            @Override
            public void onValueCallback(int task, Order order) {
                switch (task) {
                    case TASK_RETURN:
                        ((TransRecordFragment) Objects.requireNonNull(getParentFragment())).addReturnFragment(order, STATUS_RETURN);
                        break;
                    case TASK_EXCHANGE:
                        ((TransRecordFragment) Objects.requireNonNull(getParentFragment())).addReturnFragment(order, STATUS_EXCHANGE);
                        break;
                }
            }
        });
        recyclerView.setAdapter(orderListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                BottomNavigationViewEx bottomNavigationViewEx = ((MainActivity) Objects.requireNonNull(getActivity())).getBottomNavigationViewEx();
                if (bottomNavigationViewEx != null) {
                    bottomNavigationViewEx.setCurrentItem(1);
                }
                break;
        }
    }

    @Override
    public void setList(List<Order> orders) {
        orderObserver.setOrders(orders);
    }
}
