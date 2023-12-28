package com.lafresh.smile2.Login.VIew;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.Login.Presenter.RegisterPresenter;
import com.lafresh.smile2.Login.RegisterContract;
import com.lafresh.smile2.R;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.IntentUtils;
import com.lafresh.smile2.Utils.ViewUtils;
import com.lafresh.smile2.Widget.SpinnerDatePickerDialog;

import java.util.Calendar;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterContract.View {
    public static final String TAG = RegisterActivity.class.getSimpleName();

    private ScrollView scrollView;
    private TextView tvErrorMessage, tvBirth;
    private EditText etName, etPhone, etPassword;
    private RadioGroup radioGender;
    private CheckBox cbTermsOfUse;
    private ConstraintLayout btnRegister;

    private SpannableString spannableString;
    private RegisterContract.Presenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.register);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);

        scrollView = findViewById(R.id.scrollview);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        etName = findViewById(R.id.et_name);
        tvBirth = findViewById(R.id.tv_birthday);
        tvBirth.setOnClickListener(this);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        radioGender = findViewById(R.id.group_gender);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        String cbWording = getString(R.string.register_agree_terms_of_use);
        spannableString = new SpannableString(cbWording);
        spannableString.setSpan(new CheckboxClickableSpan(), cbWording.indexOf(getString(R.string.member_terms_of_use)), cbWording.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        cbTermsOfUse = findViewById(R.id.cb_terms_of_use);
        cbTermsOfUse.setOnCheckedChangeListener(null);
        cbTermsOfUse.setOnClickListener(this);
        cbTermsOfUse.setText(spannableString);
        cbTermsOfUse.setMovementMethod(LinkMovementMethod.getInstance());

        registerPresenter = new RegisterPresenter(this, getRepositoryManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                registerPresenter.checkInputValue(etName.getText().toString(), radioGender.getCheckedRadioButtonId(),
                        tvBirth.getText().toString(), etPhone.getText().toString(), etPassword.getText().toString(), cbTermsOfUse.isChecked());
                break;
            case R.id.cb_terms_of_use:
                cbTermsOfUse.setText(spannableString);
                break;
            case R.id.tv_birthday:
                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        DatePickerDialog datePickerDialog;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            datePickerDialog = new SpinnerDatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    tvBirth.setText(i + "-" + (i1 + 1) + "-" + i2);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            datePickerDialog = new DatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    tvBirth.setText(i + "-" + (i1 + 1) + "-" + i2);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }

        ViewUtils.colorizeDatePicker(datePickerDialog.getDatePicker());
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void showErrorMessage(int stringRes) {
        scrollView.smoothScrollTo(0, 0);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(stringRes);
    }

    @Override
    public void showErrorMessage(String message) {
        scrollView.smoothScrollTo(0, 0);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(message);
    }

    @Override
    public void intentToTermsOfUse() {
//        IntentUtils.intentToWebView(this, R.string.privacy_policy, SmileApplication.WEBVIEW_TERMS_URL);
        IntentUtils.intentToTermsOfUse(this);
    }

    @Override
    public void intentToVerifyCode() {
        tvErrorMessage.setVisibility(View.GONE);
        IntentUtils.intentToVerifyCode(this, true);
    }

    class CheckboxClickableSpan extends ClickableSpan {

        @Override
        public void onClick(@NonNull View widget) {
            widget.cancelPendingInputEvents();
            intentToTermsOfUse();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setFakeBoldText(true);
            ds.setColor(cbTermsOfUse.isChecked() ? getResources().getColor(R.color.orangeText) : Color.BLACK);
            ds.setUnderlineText(true);
        }
    }
}
