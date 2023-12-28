package com.lafresh.smile2.MemberCenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.MemberMessage;

import java.util.List;
import java.util.Map;

public interface MailFileContract {
    interface View extends BaseContract.View {
        void showMailDetail(MemberMessage memberMessage);

        void setMessageList(List<MemberMessage> memberMessages);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList();

        void deleteMessage(MemberMessage memberMessage, BaseContract.ValueCallback<String> valueCallback);

        void readMessage(MemberMessage memberMessage);
    }
}
