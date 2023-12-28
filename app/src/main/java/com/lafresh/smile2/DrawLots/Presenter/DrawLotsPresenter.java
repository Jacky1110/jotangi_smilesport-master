package com.lafresh.smile2.DrawLots.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.DrawLots.Contract.DrawLotsContract;
import com.lafresh.smile2.Product.Contract.ProductContract;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;

import static com.lafresh.smile2.Constant.ApiConstant.TASK_POST_GET_LOT_LIST;
import static com.lafresh.smile2.Constant.ApiConstant.TOTAL_ERROR_CODE;


public class DrawLotsPresenter extends BasePresenter<DrawLotsContract.View> implements DrawLotsContract.Presenter {
    public static final String TAG = DrawLotsPresenter.class.getSimpleName();

    public DrawLotsPresenter(DrawLotsContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getLotList() {
        repositoryManager.callGetLotListApi(new BaseContract.ValueCallback<List<DrawLots>>() {
            @Override
            public void onValueCallback(int task, List<DrawLots> type) {
                if(task == TASK_POST_GET_LOT_LIST){
                    view.setLotList(type);
                }
                else{
//                    view.setLotList(type); goBack
                }
            }
        });
    }

    @Override
    public void getLotDetailByID(int lot_id) {
        view.goLotDetail(lot_id);
    }

//    @Override
//    public void getTypeList(int store_id) {
//        repositoryManager.callGetProductTypeListApi(store_id ,  new BaseContract.ValueCallback<List<ProductType>>() {
//            @Override
//            public void onValueCallback(int task, List<ProductType> type) {
//                view.setProductTypeList(type);
//
//            }
//        });
//    }
//
//    @Override
//    public void getProductDetailByID(int product_ID) {
//        view.goPageProductDetail(product_ID);
//    }
//
//    @Override
//    public void getProductList(String sort,String store_id, String product_type_id , String product_name) {
//        repositoryManager.callGetProductListApi(sort , store_id , product_type_id ,product_name ,  new BaseContract.ValueCallback<List<Product>>() {
//            @Override
//            public void onValueCallback(int task, List<Product> type) {
//                view.setProductList(type);
//
//            }
//        });
//    }
}
