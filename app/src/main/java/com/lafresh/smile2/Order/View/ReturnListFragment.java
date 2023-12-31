package com.lafresh.smile2.Order.View;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Constant.Constant;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Order.Adapter.ReturnListAdapter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Order;

import java.util.Objects;

public class ReturnListFragment extends BaseFragment implements View.OnClickListener, BaseContract.ValueCallback<Boolean> {
    public static final String TAG = ReturnListFragment.class.getSimpleName();
    public static final int STATUS_RETURN = 1;
    public static final int STATUS_EXCHANGE = 2;

    public TextView tvApplyExchange, tvOrderNo, tvOrderTime, tvOrderPrice, tvOrderCount, tvOrderExtra;
    private ImageView imgOrder;

    private RecyclerView recyclerView;
    private ReturnListAdapter returnListAdapter;

    private BaseContract.ValueCallback<Integer> transRecordTabCallback;
    private static ReturnListFragment fragment;
    private int status = STATUS_RETURN;

    public static ReturnListFragment getInstance(Order order, int status) {
        if (fragment == null) {
            fragment = new ReturnListFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Order.TAG, order);
        bundle.putInt(TAG, status);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return_list, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        if (getArguments() != null && getArguments().containsKey(ReturnListFragment.TAG)) {
            status = getArguments().getInt(TAG, STATUS_RETURN);
        }

        ((MainActivity) Objects.requireNonNull(getActivity())).setAppTitle(status == STATUS_RETURN ? R.string.return_title : R.string.exchange_title);

        tvOrderNo = view.findViewById(R.id.tv_order_no);
        tvOrderTime = view.findViewById(R.id.tv_order_time);
        tvOrderPrice = view.findViewById(R.id.tv_order_price);
        tvOrderCount = view.findViewById(R.id.tv_order_count);
        tvOrderExtra = view.findViewById(R.id.tv_order_extra);

        tvApplyExchange = view.findViewById(R.id.tv_return_apply);
        tvApplyExchange.setText(status == STATUS_RETURN ? R.string.return_apply : R.string.exchange_apply);
        tvApplyExchange.setOnClickListener(this);

        imgOrder = view.findViewById(R.id.img_order);

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);

        returnListAdapter = new ReturnListAdapter();
        recyclerView.setAdapter(returnListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null && getArguments().containsKey(Order.TAG)) {
            setOrders((Order) Objects.requireNonNull(getArguments().getParcelable(Order.TAG)));
        }
    }

    private void setOrders(Order order) {
        Glide.with(this).load(order.getPreviewImageUrl()).into(imgOrder);

        tvOrderNo.setText(String.format(getContext().getString(R.string.order_no), order.getId()));
        tvOrderTime.setText(String.format(getContext().getString(R.string.order_time), order.getOrderTime()));
        tvOrderPrice.setText(String.format(getContext().getString(R.string.order_price), order.getRealPayTotal()));
        tvOrderCount.setText(String.format(getContext().getString(R.string.order_total), order.getOrderProduct().size()));
        tvOrderExtra.setVisibility(order.isEenterpriseOrder() ? View.VISIBLE : View.GONE);

        returnListAdapter.setProducts(order.getOrderProduct());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return_apply:
                if (returnListAdapter.getSelectProducts().size() > 0) {
                    ReturnInputFragment fragment = ReturnInputFragment.getInstance(returnListAdapter.getSelectProducts(), status);
                    fragment.setFinishCallback(this);
                    ((MainActivity) Objects.requireNonNull(getActivity())).addFragment(fragment);
                } else {
                    new AlertDialog.Builder(Objects.requireNonNull(getContext())).setMessage(R.string.empty_order_hint).setPositiveButton(android.R.string.ok, null).show();
                }
                break;
        }
    }

    public void setTransRecordTabCallback(BaseContract.ValueCallback<Integer> transRecordTabCallback) {
        this.transRecordTabCallback = transRecordTabCallback;
    }

    @Override
    public void onValueCallback(int task, Boolean isSuccess) {
        if (isSuccess) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (transRecordTabCallback != null) {
                        transRecordTabCallback.onValueCallback(0, status == STATUS_RETURN ? Constant.ORDER_STATUS_RETURNING : Constant.ORDER_STATUS_EXCHANGING);
                    }

                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
            });
        }
    }
}
