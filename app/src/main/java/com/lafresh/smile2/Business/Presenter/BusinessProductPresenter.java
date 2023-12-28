package com.lafresh.smile2.Business.Presenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Business.Contract.BusinessProductContract;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;


public class BusinessProductPresenter extends BasePresenter<BusinessProductContract.View> implements BusinessProductContract.Presenter {
    public static final String TAG = BusinessProductPresenter.class.getSimpleName();

    public BusinessProductPresenter(BusinessProductContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getTypeList(int store_id) {
        repositoryManager.callGetProductTypeListApi(store_id ,  new BaseContract.ValueCallback<List<ProductType>>() {
            @Override
            public void onValueCallback(int task, List<ProductType> type) {
                view.setProductTypeList(type);

            }
        });
    }

    @Override
    public void getProductDetailByID(int product_ID) {
        view.goPageProductDetail(product_ID);
    }

    @Override
    public void getProductList(String sort,String store_id, String product_type_id , String product_name , String business_sale_id) {
        repositoryManager.callGetProductListApi(business_sale_id,sort , store_id , product_type_id ,product_name ,  new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductList(type);

            }
        });
    }
}
