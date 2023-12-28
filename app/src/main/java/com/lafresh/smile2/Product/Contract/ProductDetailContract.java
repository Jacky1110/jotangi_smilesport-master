package com.lafresh.smile2.Product.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;

import java.util.List;

public interface ProductDetailContract {
    interface View extends BaseContract.View {
        void setProductDetail(List<Product> productDetail);

        void intentToLogin(int requestCode);

        void showErrorAlert(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void getProductDetailByID(String product_ID);

        void addShoppingCart(String product_id , String spec_id ,String spec_qty,String stock, String quantity);
    }
}
