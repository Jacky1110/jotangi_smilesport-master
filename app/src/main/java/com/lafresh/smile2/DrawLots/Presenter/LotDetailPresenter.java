package com.lafresh.smile2.DrawLots.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.DrawLots.Contract.DrawLotsContract;
import com.lafresh.smile2.DrawLots.Contract.LotDetailContract;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.BaseModel;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.Arrays;
import java.util.List;

import static com.lafresh.smile2.Constant.ApiConstant.TASK_POST_GET_LOT_LIST;


public class LotDetailPresenter extends BasePresenter<LotDetailContract.View> implements LotDetailContract.Presenter {
    public static final String TAG = LotDetailPresenter.class.getSimpleName();

    public LotDetailPresenter(LotDetailContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getLotDetailData(int lot_id) {
        repositoryManager.callGetLotDetailApi(lot_id ,  new BaseContract.ValueCallback<DrawLots>() {
            @Override
            public void onValueCallback(int task, DrawLots type) {
                view.setLotDetail(type);
            }
        });
    }

    @Override
    public void checkIdentity(String member_group_required, String receipt_required, String is_draw , String cif_code , String prod_id , String spec_name, String receipt_no) {
        if (is_draw.equals("Y")) {
            view.ErrorAlert(R.string.repeat_join);
        }
        else if(member_group_required.equals("Y")){
            if (repositoryManager.getUser().getGroup().equals("V")) {
                checkJoinData(receipt_required, cif_code , prod_id , spec_name,receipt_no);
            }else{
                view.ErrorAlert(R.string.member_group_error);
            }
        }
        else if(member_group_required.equals("N")){
            checkJoinData(receipt_required, cif_code , prod_id , spec_name,receipt_no);
        }
        else{
            view.ErrorAlert(R.string.sys_error);
        }
    }


    private void checkJoinData(String receipt_required, String cif_code , String prod_id , String spec_name, String receipt_no){
        if(cif_code.equals("")){
            view.ErrorAlert(R.string.cif_empty);
        }

        else if(!checkCardId(cif_code)){
            view.ErrorAlert(R.string.cif_error);
        }

        else if(prod_id.equals("") || spec_name.equals("")){
            view.ErrorAlert(R.string.check_prod_spec);
        }

        else if(receipt_required.equals("Y") && receipt_no.equals("")){
            view.ErrorAlert(R.string.receipt_no_empty);
        }

        else if(receipt_required.equals("Y") && !checkReceiptNo(receipt_no)){
            view.ErrorAlert(R.string.receipt_no_error);
        }

        else{
            view.showCheckAlert();
        }
    }

    private boolean checkReceiptNo(String receipt_no){
        if(!receipt_no.matches("[A-Z][A-Z][-][0-9]{8}")){
            return false;
        }
        return true;
    }

    private boolean checkCardId(String id) {
        if (!id.matches("[a-zA-Z][1-2][0-9]{8}")) {
            return false;
        }

        String newId = id.toUpperCase();
        //身分證第一碼代表數值
        int[] headNum = new int[]{
                1, 10, 19, 28, 37,
                46, 55, 64, 39, 73,
                82, 2, 11, 20, 48,
                29, 38, 47, 56, 65,
                74, 83, 21, 3, 12, 30};

        char[] headCharUpper = new char[]{
                'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z'
        };

        int index = Arrays.binarySearch(headCharUpper, newId.charAt(0));
        int base = 8;
        int total = 0;
        for (int i = 1; i < 10; i++) {
            int tmp = Integer.parseInt(Character.toString(newId.charAt(i))) * base;
            total += tmp;
            base--;
        }

        total += headNum[index];
        int remain = total % 10;
        int checkNum = (10 - remain) % 10;
        if (Integer.parseInt(Character.toString(newId.charAt(9))) != checkNum) {
            return false;
        }
        return true;
    }

    @Override
    public void joinLot(int lot_id, String cif_code, String prod_id, String spec_name ,String receipt_no) {
        repositoryManager.callJoinDrawLotsApi(lot_id , cif_code , prod_id , spec_name , receipt_no , new BaseContract.ValueCallback<BaseModel>() {
            @Override
            public void onValueCallback(int task, BaseModel model) {
                if(model.getSuccess()){
                    view.showSuccessMessage(R.string.success_drawlots);
                }
                else{
                    //view.ErrorAlert(R.string.failed_drawlots);
                    String message = model.getMessage();
                    int index = message.indexOf("|");
                    if(index!=-1){
                        view.showErrorAlert(message.substring(index+1));
                    }else
                        view.showErrorAlert(message);
                }
            }
        });
    }
}
