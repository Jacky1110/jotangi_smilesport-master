package com.lafresh.smile2.Login.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Login.ForgetPasswordContract;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.Repository.RepositoryManager;
import com.lafresh.smile2.Utils.FormatUtils;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.View> implements ForgetPasswordContract.Presenter {
    public static final String TAG = ForgetPasswordPresenter.class.getSimpleName();

    public ForgetPasswordPresenter(ForgetPasswordContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkInputValue(String cellphone, String verifyCode, String password, String passwordCheck) {
        String passwordFormat = "[a-zA-Z0-9]{6,12}";
        if (verifyCode.equals(MemberBean.Global_Code)) {
            repositoryManager.callVerifyForgetPsdApi(cellphone, password, verifyCode, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.finishActivity();
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    if (message.contains("無此會員資料，請先註冊帳號。")) {
                        view.showGoRegistAlert(message);
                    } else {
                        view.showAlert(message);
                    }
                }
            });
        }

        if (verifyCode.isEmpty()) {
            Log.d(TAG, "verifyCode: " + verifyCode);
            Log.d(TAG, "Global_Code: " + MemberBean.Global_Code);
            view.showEmptyVerifyCodeAlert();
        } else if (password.isEmpty() || !FormatUtils.isPasswordFormat(password)) {
            view.showPasswordFormatAlert();
        } else if (passwordCheck.isEmpty() || !passwordCheck.matches(passwordFormat)) {
            view.showPasswordCheckFormatAlert();
        } else if (!password.equals(passwordCheck)) {
            view.showPasswordDifferentAlert();
        } else {
            repositoryManager.callVerifyForgetPsdApi(cellphone, password, verifyCode, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.finishActivity();
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    if (message.contains("無此會員資料，請先註冊帳號。")) {
                        view.showGoRegistAlert(message);
                    } else {
                        view.showAlert(message);
                    }
                }
            });
        }
    }

    @Override
    public void register() {
        view.intentToRegister();
    }
}
