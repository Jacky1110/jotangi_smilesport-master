package com.lafresh.smile2.Login.VIew;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.HttpPost;
import com.lafresh.smile2.Login.ForgetPasswordContract;
import com.lafresh.smile2.Login.Presenter.ForgetPasswordPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Utils.IntentUtils;

import java.util.Objects;

import static com.lafresh.smile2.Constant.Constant.PHONE;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, ForgetPasswordContract.View {
    public static final String TAG = ForgetPasswordActivity.class.getSimpleName();

    private EditText etVerify, etPassword, etPasswordCheck;
    private TextView tvSend;

    private String cellphone = "";
    private ForgetPasswordContract.Presenter forgetPresenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        cellphone = Objects.requireNonNull(getIntent().getExtras()).getString(PHONE, "");
        initView();
        getVerificationCode();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.forget_password);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);

        etVerify = findViewById(R.id.et_verify_code);
        etPassword = findViewById(R.id.et_password);
        etPasswordCheck = findViewById(R.id.et_check_password);
        tvSend = findViewById(R.id.tv_send);
        tvSend.setOnClickListener(this);

        forgetPresenter = new ForgetPasswordPresenter(this, getRepositoryManager(this));
    }

    private void getVerificationCode() {
        HttpPost httpPost = new HttpPost(context);
        httpPost.setOnPostBack(new HttpPost.Post_Call_Back() {
            @Override
            public void Success() {
                Log.d(TAG, "Success: " + "成功");
            }

            @Override
            public void Fail() {

            }
        });

        httpPost.httprequest(
                "https://2020smilesports.jotangi.net/api/Authority/ObtainGlobalCode",
                "{\"member_id\":\"0912345678\"}"
        );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                forgetPresenter.checkInputValue(cellphone, etVerify.getText().toString(),
                        etPassword.getText().toString(), etPasswordCheck.getText().toString());
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void intentToRegister() {
        IntentUtils.intentToRegister(this);
    }

    @Override
    public void showAlert(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public void showGoRegistAlert(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        forgetPresenter.register();
                    }
                })
                .show();
    }


    @Override
    public void showEmptyVerifyCodeAlert() {
        showAlert(getString(R.string.empty_verify_code));
    }

    @Override
    public void showPasswordFormatAlert() {
        showAlert(getString(R.string.password_format_error));
    }

    @Override
    public void showPasswordCheckFormatAlert() {
        showAlert(getString(R.string.password_check_format_error));
    }

    @Override
    public void showPasswordDifferentAlert() {
        showAlert(getString(R.string.password_different));
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
