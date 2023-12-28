package com.lafresh.smile2.Main.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Main.Contract.MainContract;
import com.lafresh.smile2.Main.Contract.MainIndexContract;
import com.lafresh.smile2.Main.View.MemberCardFragment;
import com.lafresh.smile2.Main.View.MemberCenterFragment;
import com.lafresh.smile2.MemberCenter.View.MailDetailFragment;
import com.lafresh.smile2.MemberCenter.View.MailFileFragment;
import com.lafresh.smile2.MemberCenter.View.MessageListFragment;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;


public class MainIndexPresenter extends BasePresenter<MainIndexContract.View> implements MainIndexContract.Presenter {
    public static final String TAG = MainIndexPresenter.class.getSimpleName();

    public MainIndexPresenter(MainIndexContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }


    @Override
    public void getCarouselList() {
        repositoryManager.callGetCarouselListApi( new BaseContract.ValueCallback<List<Carousel>>() {
            @Override
            public void onValueCallback(int task, List<Carousel> type) {
                view.setCarouselList(type);

            }
        });
    }

    @Override
    public String getName() {
        String Name = "";
        if (repositoryManager.getUserLogin()) {
            Name = repositoryManager.getUser().getName();
        }
        else{

        }
        return Name;
    }

    @Override
    public String getGroup() {
        String Group = "";
        if (repositoryManager.getUserLogin()) {
            Group = repositoryManager.getUser().getGroup();
        }
        else{

        }
        return Group;
    }

    @Override
    public void checkMemberData() {
        if(!repositoryManager.getUserID().equals("")){
            repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<User>() {
                @Override
                public void onValueCallback(int task, User user) {
                    repositoryManager.saveUser(user);
                    view.setMemberCardImg(user.getGroup());
                }
            });
        }
    }
}
