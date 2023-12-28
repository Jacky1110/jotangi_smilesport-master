package com.lafresh.smile2.Business.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Business.Contract.BusinessProductMerchantContract;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;


public class BusinessProductMerchantPresenter extends BasePresenter<BusinessProductMerchantContract.View> implements BusinessProductMerchantContract.Presenter {
    public static final String TAG = BusinessProductMerchantPresenter.class.getSimpleName();

    public BusinessProductMerchantPresenter(BusinessProductMerchantContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMerchantList(String keyword , String business_sale_id) {
        repositoryManager.callGetMerchantListApi(keyword , business_sale_id ,  new BaseContract.ValueCallback<List<Merchant>>() {
            @Override
            public void onValueCallback(int task, List<Merchant> type) {
                for(Merchant merchant : type){
                    Log.e(TAG,merchant.getStore_name());
                }
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
    public void getProductList(String sort,String store_id, String product_type_id, String product_name , String business_sale_id) {
        repositoryManager.callGetProductListApi(business_sale_id , sort ,store_id , product_type_id ,product_name ,   new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductList(type);
            }
        });
    }
}
