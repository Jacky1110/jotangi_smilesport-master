package com.lafresh.smile2.MemberCenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.About;
import com.lafresh.smile2.Repository.Model.Faq;

import java.util.List;

public interface FaqContract {
    interface View extends BaseContract.View {
        void setFaqData(Faq faq);
    }

    interface Presenter extends BaseContract.Presenter {
        void getFaqData(String faq_id);
    }
}
