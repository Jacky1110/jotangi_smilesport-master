package com.lafresh.smile2.Pay.Presenter;


import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Pay.Contract.PayCodeContract;
import com.lafresh.smile2.Repository.RepositoryManager;

public class PayCodePresenter extends BasePresenter<PayCodeContract.View> {
    public PayCodePresenter(PayCodeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void savePayCode(String code){
        repositoryManager.savePayCode(code);
    }

    public String getPayCode(){
        return repositoryManager.getPayCode();
    }
}
