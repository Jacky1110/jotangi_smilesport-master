package com.lafresh.smile2.Main.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Store;
import com.lafresh.smile2.Utils.ViewUtils;

public class StoreListHolder extends RecyclerView.ViewHolder {
    public TextView tvName, tvPhone, tvAddress, tvOpenTime;

    public StoreListHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_product_type);
        tvPhone = itemView.findViewById(R.id.tv_phone);
        ViewUtils.addUnderLine(tvPhone);
        tvAddress = itemView.findViewById(R.id.tv_address);
        ViewUtils.addUnderLine(tvAddress);
        tvOpenTime = itemView.findViewById(R.id.tv_time);
    }

    public void setStore(Store store) {
        tvName.setText(store.getName());
        tvPhone.setText(store.getPhone());
        tvAddress.setText(store.getAddress());
        tvOpenTime.setText(store.getOpening());
    }
}
