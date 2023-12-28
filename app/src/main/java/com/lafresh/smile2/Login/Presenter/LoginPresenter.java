package com.lafresh.smile2.Login.Presenter;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Constant.ApiConstant;
import com.lafresh.smile2.Login.LoginContract;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public static final String TAG = LoginPresenter.class.getSimpleName();

    public LoginPresenter(LoginContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void register() {
        view.intentToRegister();
    }

    @Override
    public void login(final String cellphone, final String password) {
        if (cellphone.isEmpty()) {
            view.showEmptyPhoneAlert();
        } else if (password.isEmpty()) {
            view.showEmptyPasswordAlert();
        } else {
            repositoryManager.callLoginApi(cellphone, password, new BaseContract.ValueCallback<User>() {
                @Override
                public void onValueCallback(int task, User user) {
                    repositoryManager.saveAccountInfo(cellphone, password);
                    repositoryManager.saveUserID(Integer.toString(user.getMember()));
                    repositoryManager.saveVerifyCode(user.getVerifyCode());
                    if(task == ApiConstant.RESPONSE_CODE_LOGIN_VERIFIED_ERROR){
                        view.showVerifyErrorAlert("尚未完成簡訊驗證");
                    }
                    else{
                        repositoryManager.callGetBadgeNumberApi(new BaseContract.ValueCallback<String>() {
                            @Override
                            public void onValueCallback(int task, String type) {
                                String[] array= type.split("_");
                                if(array.length==3){
                                    repositoryManager.saveShoppingCartCount(array[2]);
                                }else{
                                    repositoryManager.saveShoppingCartCount("0");
                                }
                            }
                        });
                        view.setResultOkFinishActivity();
                    }
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    view.showErrorAlert(message);
                }
            });
        }
    }

    @Override
    public void forgetPassword(final String cellphone) {
        if (cellphone.isEmpty()) {
            view.showNoInputCellphoneHint();
        } else {
            repositoryManager.callForgetPsdSmsApi(cellphone, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.intentToForgetPassword(cellphone);
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    if(message.contains("無此會員資料，請先註冊帳號。")){
                        view.showGoRegistAlert(message);
                    }else{
                        view.showErrorAlert(message);
                    }
                }
            });
        }
    }

    @Override
    public void checkAccount(final String cellphone) {
        if (cellphone.isEmpty()) {
            view.showNoInputCellphoneHint();
        } else {
            view.doForgetPassword(cellphone);
//            repositoryManager.callCheckAccountApi(cellphone, new BaseContract.ValueCallback<String>() {
//                @Override
//                public void onValueCallback(int task, String type) {
//                    view.doForgetPassword(cellphone);
//                    Log.d("豪", "onValueCallback: ");
//                }
//            }, new BaseContract.ValueCallback<String>() {
//                @Override
//                public void onValueCallback(int task, String message) {
//                    if(message.contains("無此會員資料，請先註冊帳號。")){
//                        view.showGoRegistAlert(message);
//                    }else{
//                        view.showErrorAlert(message);
//                    }
//                }
//            });
        }
    }
}
