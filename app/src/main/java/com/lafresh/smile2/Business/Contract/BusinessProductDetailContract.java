package com.lafresh.smile2.Business.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Product;

import java.util.List;

public interface BusinessProductDetailContract {
    interface View extends BaseContract.View {
        void setProductDetail(List<Product> productDetail);

        void intentToLogin(int requestCode);

        void showErrorAlert(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void getProductDetailByID(String product_ID , String business_sale_id);

        void addShoppingCart(String business_sale_id ,String product_id, String spec_id, String spec_qty, String stock, String quantity);
    }
}
