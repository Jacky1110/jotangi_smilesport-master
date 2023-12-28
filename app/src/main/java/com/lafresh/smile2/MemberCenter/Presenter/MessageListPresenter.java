package com.lafresh.smile2.MemberCenter.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.MemberCenter.MessageListContract;
import com.lafresh.smile2.Repository.Model.Message;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.List;

public class MessageListPresenter extends BasePresenter<MessageListContract.View> implements MessageListContract.Presenter {
    public static final String TAG = MessageListPresenter.class.getSimpleName();

    public MessageListPresenter(MessageListContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMessageList() {
        repositoryManager.callGetMessageListApi(new BaseContract.ValueCallback<List<Message>>() {
            @Override
            public void onValueCallback(int task, List<Message> type) {
                Log.e(TAG,type.toString());
                view.setMessageList(type);
            }
        });
    }

    @Override
    public void updateReadMessageApi() {
        repositoryManager.callReadMessageApi(new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        repositoryManager.callSendMessageApi(message, new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
                getMessageList();
            }
        });
    }
}
