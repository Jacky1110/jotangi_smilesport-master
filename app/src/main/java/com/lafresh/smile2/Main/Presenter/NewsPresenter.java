package com.lafresh.smile2.Main.Presenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Main.Contract.MainIndexContract;
import com.lafresh.smile2.Main.Contract.NewsContract;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;


public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {
    public static final String TAG = NewsPresenter.class.getSimpleName();

    public NewsPresenter(NewsContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }
}
