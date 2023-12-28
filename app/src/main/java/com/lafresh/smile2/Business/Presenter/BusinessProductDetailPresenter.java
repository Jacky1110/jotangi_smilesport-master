package com.lafresh.smile2.Business.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Business.Contract.BusinessProductDetailContract;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;

import static com.lafresh.smile2.Constant.Constant.REQUEST_PRODUCT_DETAIL;


public class BusinessProductDetailPresenter extends BasePresenter<BusinessProductDetailContract.View> implements BusinessProductDetailContract.Presenter {
    public static final String TAG = BusinessProductDetailPresenter.class.getSimpleName();

    public BusinessProductDetailPresenter(BusinessProductDetailContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getProductDetailByID(String product_id , String business_sale_id) {
        repositoryManager.callGetProductDetailApi(business_sale_id,product_id ,  new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductDetail(type);
            }
        });
    }

    @Override
    public void addShoppingCart(String business_sale_id ,String product_id, String spec_id,String spec_qty,String stock, String quantity) {
        if (repositoryManager.getUserLogin()) {
            if(spec_id.equals("0") || spec_id.isEmpty()){
                view.showErrorAlert("請填寫商品規格");
            }
            else if (quantity.equals("0") || quantity.isEmpty()){
                view.showErrorAlert("請填寫商品數量");
            }
            else if(stock.equals("0")){
                view.showErrorAlert("此尺寸庫存量不足");
            }
            else if (Integer.parseInt(quantity) > Integer.parseInt(stock) - Integer.parseInt(spec_qty)){
                view.showErrorAlert("此尺寸庫存量不足 " + "目前庫存量" + (Integer.parseInt(stock) - Integer.parseInt(spec_qty)));
            }


            else{
                Log.e(TAG,"stock : " + stock + "spec_qty : " + spec_qty );
                repositoryManager.callAddShoppingCattApi(business_sale_id,"B",product_id , spec_id , quantity ,  new BaseContract.ValueCallback<String>() {
                    @Override
                    public void onValueCallback(int task, String type) {
                        view.showErrorAlert("加入購物車成功");
                    }
                });
            }
        } else {
            view.intentToLogin(REQUEST_PRODUCT_DETAIL);
        }
    }
}
