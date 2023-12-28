package com.lafresh.smile2.Business.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;

import java.util.List;

public interface BusinessProductContract {
    interface View extends BaseContract.View {
        void setProductTypeList(List<ProductType> productTypeList);

        void goPageProductDetail(int product_id);

        void setProductList(List<Product> productList);


    }

    interface Presenter extends BaseContract.Presenter {
        void getTypeList(int store_id);

        void getProductDetailByID(int product_ID);

        void getProductList(String sort, String store_id, String product_type_id, String product_name , String business_sale_id);
    }
}
