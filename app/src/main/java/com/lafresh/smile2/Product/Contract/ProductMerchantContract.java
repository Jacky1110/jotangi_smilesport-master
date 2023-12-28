package com.lafresh.smile2.Product.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Product;

import java.util.List;

public interface ProductMerchantContract {
    interface View extends BaseContract.View {
        void setMerchantlList(List<Merchant> merchantlList);

        void goPageProductList(int store_id);

        void goPageProductDetail(int product_id);

        void setProductList(List<Product> productList);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMerchantList(String keyword);

        void getProductListByID(int store_id);

        void getProductDetailByID(int product_ID);

        void getProductList(String sort ,String store_id , String product_type_id , String product_name);
    }
}
