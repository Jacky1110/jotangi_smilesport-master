package com.lafresh.smile2.MemberCenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.Message;

import java.util.List;

public interface MessageListContract {
    interface View extends BaseContract.View {
        void setMessageList(List<Message> list);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList();

        void updateReadMessageApi();

        void sendMessage(String message);
    }
}
