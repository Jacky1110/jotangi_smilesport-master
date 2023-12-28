package com.lafresh.smile2.DrawLots.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;

import java.util.List;

public interface DrawLotsContract {
    interface View extends BaseContract.View {
        void setLotList(List<DrawLots> drawLotsList);

        void goLotDetail(int lot_id);

    }

    interface Presenter extends BaseContract.Presenter {
        void getLotList();

        void getLotDetailByID(int lot_id);
    }
}
