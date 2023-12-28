package com.lafresh.smile2.Order.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseAdapter;
import com.lafresh.smile2.Order.View.ProductDetailHolder;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductOrderDetailAdapter extends BaseAdapter<ProductDetailHolder> {
    public static final String TAG = ProductOrderDetailAdapter.class.getSimpleName();

    private List<OrderProduct> products = new ArrayList<>();

    @NonNull
    @Override
    public ProductDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_product_detail, viewGroup, false);
        return new ProductDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailHolder productDetailHolder, int position) {
        productDetailHolder.setProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
