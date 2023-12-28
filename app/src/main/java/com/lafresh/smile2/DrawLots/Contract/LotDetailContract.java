package com.lafresh.smile2.DrawLots.Contract;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.Model.DrawLots;

public interface LotDetailContract {
    interface View extends BaseContract.View {
        void setLotDetail(DrawLots lotDetail);

        void ErrorAlert(int errorMessage);

        void showSuccessMessage(int successMessage);

        void showErrorAlert(String message);

        void showCheckAlert();
    }

    interface Presenter extends BaseContract.Presenter {
        void getLotDetailData(int lot_id);

        void checkIdentity(String member_group_required , String receipt_required ,String is_draw , String cif_code , String prod_id , String spec_name,String receipt_no);

        void joinLot(int lot_id ,String  cif_code ,String  prod_id ,String  spec_name,String receipt_no);
    }
}
