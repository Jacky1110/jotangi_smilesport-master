package com.lafresh.smile2.Product.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Main.Contract.MainIndexContract;
import com.lafresh.smile2.Product.Contract.ProductMerchantContract;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;


public class ProductMerchantPresenter extends BasePresenter<ProductMerchantContract.View> implements ProductMerchantContract.Presenter {
    public static final String TAG = ProductMerchantPresenter.class.getSimpleName();

    public ProductMerchantPresenter(ProductMerchantContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMerchantList(String keyword) {
        repositoryManager.callGetMerchantListApi(keyword , "" ,  new BaseContract.ValueCallback<List<Merchant>>() {
            @Override
            public void onValueCallback(int task, List<Merchant> type) {
                view.setMerchantlList(type);
            }
        });
    }

    @Override
    public void getProductListByID(int store_id) {
        view.goPageProductList(store_id);
    }

    @Override
    public void getProductDetailByID(int product_ID) {
        view.goPageProductDetail(product_ID);
    }

    @Override
    public void getProductList(String sort,String store_id, String product_type_id, String product_name) {
        repositoryManager.callGetProductListApi("",sort ,store_id , product_type_id ,product_name ,   new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductList(type);
            }
        });
    }
}
