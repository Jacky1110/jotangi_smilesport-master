package com.lafresh.smile2.Product.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseAdapter;
import com.lafresh.smile2.Product.Contract.ProductContract;
import com.lafresh.smile2.Product.Contract.ProductMerchantContract;
import com.lafresh.smile2.Product.View.ProductHolder;
import com.lafresh.smile2.Product.View.ProductMerchantHolder;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends BaseAdapter<ProductHolder> {
    public static final String TAG = ProductListAdapter.class.getSimpleName();
    private ProductContract.Presenter presenter;

    private List<Product> productleft = new ArrayList<>();
    private List<Product> productright = new ArrayList<>();

    public ProductListAdapter(ProductContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
        return new ProductHolder(viewProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder productHolder, final int position) {
        Log.e(TAG,"onBindViewHolder");
        if(productleft.size() == productright.size()){
            productHolder.setImg_product_two(productleft.get(position) , productright.get(position));
        }
        else{
            if(productright.size() == position){
                productHolder.setImg_product_one(productleft.get(position));
            }
            else{
                productHolder.setImg_product_two(productleft.get(position) , productright.get(position));
            }
        }
        productHolder.img_product_left.setOnClickListener(img_OnClick_Evt);
        productHolder.img_product_right.setOnClickListener(img_OnClick_Evt);
        productHolder.layout_left.setOnClickListener(img_OnClick_Evt);
        productHolder.layout_right.setOnClickListener(img_OnClick_Evt);
    }

    @Override
    public int getItemCount() {
        return productleft.size();
    }


    public void setProduct(List<Product> productleft, List<Product> productright) {
        this.productleft = productleft;
        this.productright = productright;
        notifyDataSetChanged();
    }

    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_product_left :
                case R.id.img_product_right :
                case R.id.constraintLayout_left :
                case R.id.constraintLayout_right :
                    presenter.getProductDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
                    break;
            }
        }
    };
}