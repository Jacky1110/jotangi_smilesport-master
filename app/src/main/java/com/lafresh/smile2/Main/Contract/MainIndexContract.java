package com.lafresh.smile2.Main.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Repository.Model.Address;
import com.lafresh.smile2.Repository.Model.Carousel;

import java.util.List;

public interface MainIndexContract {
    interface View extends BaseContract.View {
        void setCarouselList(List<Carousel> carouselList);

        void setMemberCardImg(String group);
    }

    interface Presenter extends BaseContract.Presenter {
        void getCarouselList();

        String getName();

        String getGroup();

        void checkMemberData();
    }
}
