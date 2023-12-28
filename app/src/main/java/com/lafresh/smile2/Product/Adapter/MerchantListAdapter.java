package com.lafresh.smile2.Product.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseAdapter;
import com.lafresh.smile2.MemberCenter.MailFileContract;
import com.lafresh.smile2.MemberCenter.View.MessageHolder;
import com.lafresh.smile2.Product.Contract.ProductMerchantContract;
import com.lafresh.smile2.Product.View.ProductMerchantHolder;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Message;
import com.lafresh.smile2.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class MerchantListAdapter extends BaseAdapter<ProductMerchantHolder> {
    public static final String TAG = MerchantListAdapter.class.getSimpleName();
    private ProductMerchantContract.Presenter presenter;
    private List<Merchant> merchantsleft = new ArrayList<>();
    private List<Merchant> merchantsright = new ArrayList<>();

    private List<Product> productleft = new ArrayList<>();
    private List<Product> productright = new ArrayList<>();
    private boolean cleanType = false;

    public MerchantListAdapter(ProductMerchantContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ProductMerchantHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);

            return new ProductMerchantHolder(viewProduct);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductMerchantHolder productmerchantHolder, final int position) {
        if(cleanType){
            productmerchantHolder.clean();
            cleanType = false;
        }
        else{
            if(productleft.size() > 0){
                if(productleft.size() == productright.size()){
                    productmerchantHolder.setImg_product_two(productleft.get(position) , productright.get(position));
                }
                else{
                    if(productright.size() == position){
                        productmerchantHolder.setImg_product_one(productleft.get(position));
                    }
                    else{
                        productmerchantHolder.setImg_product_two(productleft.get(position) , productright.get(position));
                    }
                }
            }


            else{
                if(merchantsleft.size() == merchantsright.size()){
                    productmerchantHolder.setImg_merchant_two(merchantsleft.get(position) , merchantsright.get(position));
                }
                else{
                    if(merchantsright.size() == position){
                        productmerchantHolder.setImg_merchant_one(merchantsleft.get(position));
                    }
                    else{
                        productmerchantHolder.setImg_merchant_two(merchantsleft.get(position) , merchantsright.get(position));
                    }
                }
            }
        }
        productmerchantHolder.img_product_left.setOnClickListener(img_OnClick_Evt);
        productmerchantHolder.img_product_right.setOnClickListener(img_OnClick_Evt);
        productmerchantHolder.img_merchant_left.setOnClickListener(img_OnClick_Evt);
        productmerchantHolder.img_merchant_right.setOnClickListener(img_OnClick_Evt);
    }

    @Override
    public int getItemCount() {
        if(productleft.size() > 0){
            return productleft.size();
        }
        else{
            return merchantsleft.size();
        }

    }

    public void setMerchant(List<Merchant> merchantsleft,List<Merchant> merchantsright) {
        this.cleanType = false;
        this.merchantsleft = merchantsleft;
        this.merchantsright = merchantsright;
        this.productright.clear();
        this.productleft.clear();
        notifyDataSetChanged();
    }

    public void setProduct(List<Product> productleft, List<Product> productright) {
        this.cleanType = false;
        this.productleft = productleft;
        this.productright = productright;
        this.merchantsleft.clear();
        this.merchantsright.clear();
        notifyDataSetChanged();
    }

    public void clean(){
        this.cleanType = true;
    }

    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_merchant_left :
                case R.id.img_merchant_right :
                    presenter.getProductListByID(Integer.parseInt(v.getTag(v.getId()).toString()));
                    break;
                case R.id.img_product_left :
                case R.id.img_product_right :
                    presenter.getProductDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
                    break;
            }
        }
    };
}
//switch (v.getTag(R.id.img_product_right).toString()){
//        case "Product":
//        Log.e(TAG,"ProductID : " + v.getId());
//        presenter.getProductDetailByID(v.getId());
//        break;
//        case "Merchant":
//        Log.e(TAG,"MerchantID : " + v.getId());
//        presenter.getProductListByID(v.getId());
//        break;
//        }