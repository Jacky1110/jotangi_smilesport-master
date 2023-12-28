package com.lafresh.smile2.Main.Adapter;

import android.Manifest;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseAdapter;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Main.Contract.StoreListContract;
import com.lafresh.smile2.Main.View.StoreListHolder;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreListAdapter extends BaseAdapter<StoreListHolder> {
    public static final String TAG = StoreListAdapter.class.getSimpleName();
    private StoreListContract.View view;
    private List<Store> stores;

    public StoreListAdapter(StoreListContract.View view) {
        this.view = view;
        stores = new ArrayList<>();
    }

    @NonNull
    @Override
    public StoreListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store_list, viewGroup, false);
        return new StoreListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StoreListHolder storeListHolder, final int position) {
        storeListHolder.setStore(stores.get(position));
        storeListHolder.tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.intentToGoogleMap(stores.get(position).getAddress());
            }
        });
        storeListHolder.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.checkPermission(new BaseContract.ValueCallback<Boolean>() {
                    @Override
                    public void onValueCallback(int task, Boolean type) {
                        if (type) {
                            view.intentToPhoneCall(stores.get(position).getPhone().replace("-", ""));
                        }
                    }
                }, Manifest.permission.CALL_PHONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public void setData(List<Store> stores) {
        this.stores = stores;
        notifyDataSetChanged();
    }
}
