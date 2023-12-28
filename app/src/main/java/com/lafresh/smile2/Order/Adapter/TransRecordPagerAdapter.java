package com.lafresh.smile2.Order.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lafresh.smile2.Order.View.OrderListFragment;
import com.lafresh.smile2.R;

import java.util.ArrayList;

import static com.lafresh.smile2.Constant.Constant.*;

public class TransRecordPagerAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = TransRecordPagerAdapter.class.getSimpleName();
    private ArrayList<OrderListFragment> fragmentList = new ArrayList<>();
    private Context context;

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ORDER_STATUS_WAITING_PAY:
                return context.getString(R.string.order_waiting_pay);
            case ORDER_STATUS_WAITING_SHIP:
                return context.getString(R.string.order_waiting_ship);
            case ORDER_STATUS_TRANS_FINISH:
                return context.getString(R.string.order_finish);
            case ORDER_STATUS_CANCEL:
                return context.getString(R.string.order_cancel);
            case ORDER_STATUS_RETURNING:
                return context.getString(R.string.order_returning);
            case ORDER_STATUS_EXCHANGING:
                return context.getString(R.string.order_exchanging);
            case ORDER_STATUS_RETURN_FINISH:
                return context.getString(R.string.order_return_finish);
            case ORDER_STATUS_EXCHANGE_FINISH:
                return context.getString(R.string.order_exchange_finish);
        }
        return super.getPageTitle(position);
    }

    public TransRecordPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        for (int i = 0; i < 8; i++) {
            fragmentList.add(OrderListFragment.getInstance(i));
        }
    }

    @Override
    public OrderListFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
