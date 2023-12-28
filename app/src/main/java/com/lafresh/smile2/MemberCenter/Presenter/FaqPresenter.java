package com.lafresh.smile2.MemberCenter.Presenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.MemberCenter.AboutContract;
import com.lafresh.smile2.MemberCenter.FaqContract;
import com.lafresh.smile2.Repository.Model.About;
import com.lafresh.smile2.Repository.Model.Faq;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;

public class FaqPresenter extends BasePresenter<FaqContract.View> implements FaqContract.Presenter {
    public static final String TAG = FaqPresenter.class.getSimpleName();

    public FaqPresenter(FaqContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getFaqData(String faq_id) {
        repositoryManager.callFaqDataApi(faq_id , new BaseContract.ValueCallback<Faq>() {
            @Override
            public void onValueCallback(int task, Faq faq) {
                view.setFaqData(faq);
            }
        });
    }
}
