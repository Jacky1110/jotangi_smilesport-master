package com.lafresh.smile2.Login.Presenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Login.RegisterContract;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;
import com.lafresh.smile2.Utils.FormatUtils;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    public static final String TAG = RegisterPresenter.class.getSimpleName();

    public RegisterPresenter(RegisterContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkInputValue(String name, int selectRadioId, String birth,
                                String phone, String password, boolean isAgreeTermsOfUse) {
        if (name.isEmpty()) {
            view.showErrorMessage(R.string.name_empty);
        }
//        性別不檢查
//        else if (selectRadioId == -1) {
//            view.showErrorMessage(R.string.gender_empty);
//        }
        else if (birth.isEmpty()) {
            view.showErrorMessage(R.string.birth_empty);
        } else if (phone.isEmpty() || !FormatUtils.isCellphoneFormat(phone)) {
            view.showErrorMessage(R.string.phone_format_error);
        } else if (password.isEmpty() || !FormatUtils.isPasswordFormat(password)) {
            view.showErrorMessage(R.string.password_format_error);
        } else if (!isAgreeTermsOfUse) {
            view.showErrorMessage(R.string.not_yet_check_terms_of_use);
        } else {
            startRegister(name, selectRadioId, birth, phone, password);
        }
    }

    private void startRegister(String name, int selectRadioId, String birth, final String phone, final String password) {
        User user = new User();
        user.setAccount(phone);
        user.setName(name);
        user.setBirthday(birth);

        // 性別不傳
        // user.setGender(selectRadioId == R.id.radio_male ? "1" : "0");

        repositoryManager.callRegisterApi(user, password, new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User type) {
                repositoryManager.saveAccountInfo(phone, password);
                repositoryManager.saveUserID(Integer.toString(type.getMember()));
                repositoryManager.saveVerifyCode(type.getVerifyCode());
                view.intentToVerifyCode();
            }
        }, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.showErrorMessage(type);
            }
        });
    }
}
