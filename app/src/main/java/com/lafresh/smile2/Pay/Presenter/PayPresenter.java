package com.lafresh.smile2.Pay.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Pay.Contract.PayContract;
import com.lafresh.smile2.Repository.Model.PayCard;
import com.lafresh.smile2.Repository.Model.PayCardBarcode;
import com.lafresh.smile2.Repository.Model.PayCardData;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;

public class PayPresenter extends BasePresenter<PayContract.View> implements PayContract.Presenter{
    public PayPresenter(PayContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getCardPageList() {
        repositoryManager.callGetCardPageListApi(repositoryManager.getUserID(), "ANDROID", new BaseContract.ValueCallback<List<PayCard>>() {
            @Override
            public void onValueCallback(int task, List<PayCard> type) {
                //Log.d("ddd",type.toString());
                view.setData(type);
            }

        });
    }

    @Override
    public void getBarcode(String cardToken) {
        repositoryManager.callGetBarcode(repositoryManager.getUserID(), "ANDROID", cardToken, new BaseContract.ValueCallback<PayCardBarcode>() {
            @Override
            public void onValueCallback(int task, PayCardBarcode type) {
                view.setData(type);
            }
        });
    }

    @Override
    public void deleteBindingCard(String cardToken) {
        repositoryManager.callDeleteBindingCard(repositoryManager.getUserID(), "ANDROID", cardToken, new BaseContract.ValueCallback<PayCardBarcode>() {
            @Override
            public void onValueCallback(int task, PayCardBarcode type) {
                view.setData(type);
            }
        });
    }

    public void saveDefaultCardNumber(String cardNum){
        repositoryManager.saveDefaultCardNumber(cardNum);
    }

    public String getDefaultCardNumber(){
        return repositoryManager.getDefaultCardNumber();
    }
}
