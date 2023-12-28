package com.lafresh.smile2.Login.VIew;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Login.LoginContract;
import com.lafresh.smile2.Login.Presenter.LoginPresenter;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Main.View.MemberCenterFragment;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.MemberCenter.View.FaqFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.IntentUtils;
import com.lafresh.smile2.Utils.ViewUtils;

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView tvTitleHint, tvTermsOfUse, tvForgetPsd;
    private EditText etPhone, etPassword;
    private ConstraintLayout btnRegister, btnLogin;
    private LoginContract.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, getRepositoryManager(this));
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!etPhone.getText().toString().isEmpty()){
            tvTitleHint.setTextColor(getResources().getColor(R.color.Black));
            tvTitleHint.getPaint().setFlags(0);
            tvTitleHint.setText(R.string.login_title_hint);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                loginPresenter.register();
                break;
            case R.id.btn_login:
                MemberBean.member_id = etPhone.getText().toString();
                MemberBean.member_pwd = etPassword.getText().toString();
                SharedPreferences pref = getSharedPreferences("loginInfo", MODE_PRIVATE);
                pref.edit()
                        .putString("member_id", MemberBean.member_id)
                        .putString("password", MemberBean.member_pwd)
                        .apply();
                loginPresenter.login(etPhone.getText().toString(), etPassword.getText().toString());
                break;
//            case R.id.tv_title_hint:
            case R.id.tv_forget_psd:
                loginPresenter.checkAccount(etPhone.getText().toString());
                break;
            case R.id.tv_terms_of_use:
                intentToTermsOfUse();
                break;
        }
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.login);
        setBackButtonVisibility(false);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);

        tvTitleHint = findViewById(R.id.tv_title_hint);
        tvTitleHint.setOnClickListener(this);
        ViewUtils.addUnderLine(tvTitleHint);

        tvTermsOfUse = findViewById(R.id.tv_terms_of_use);
        tvTermsOfUse.setOnClickListener(this);
        ViewUtils.addUnderLine(tvTermsOfUse);

        tvForgetPsd = findViewById(R.id.tv_forget_psd);
        tvForgetPsd.setOnClickListener(this);
        ViewUtils.addUnderLine(tvForgetPsd);

        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

    }


    @Override
    public void doForgetPassword(String cellphone) {
        loginPresenter.forgetPassword(cellphone);
    }

    @Override
    public void showNoInputCellphoneHint() {
        tvTitleHint.setTextColor(getResources().getColor(R.color.redHint));
        tvTitleHint.getPaint().setFlags(0);
        tvTitleHint.setText(R.string.forget_password_error_hint);
    }

    @Override
    public void intentToRegister() {
        IntentUtils.intentToRegister(this);
    }

    @Override
    public void intentToForgetPassword(String cellphone) {
        IntentUtils.intentToForgetPassword(this, cellphone);
    }

    @Override
    public void intentToTermsOfUse() {
//        IntentUtils.intentToWebView(this, R.string.privacy_policy, SmileApplication.WEBVIEW_TERMS_URL);
        IntentUtils.intentToTermsOfUse(this);
    }

    @Override
    public void showEmptyPhoneAlert() {
        showErrorAlert(getString(R.string.dialog_empty_phone));
    }

    @Override
    public void showEmptyPasswordAlert() {
        showErrorAlert(getString(R.string.dialog_empty_password));
    }

    @Override
    public void showErrorAlert(String message) {
        if(!LoginActivity.this.isFinishing()){
            new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        }
    }

    @Override
    public void showVerifyErrorAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_hint)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IntentUtils.intentToVerifyCode(LoginActivity.this, true);
                    }
                })
                .show();
    }

    @Override
    public void showGoRegistAlert(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loginPresenter.register();
                    }
                })
                .show();
    }

    @Override
    public void setResultOkFinishActivity() {
        setResult(RESULT_OK);
        finish();
    }
}
