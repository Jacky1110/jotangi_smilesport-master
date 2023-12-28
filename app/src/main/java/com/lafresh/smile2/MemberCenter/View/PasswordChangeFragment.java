package com.lafresh.smile2.MemberCenter.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.MemberCenter.PasswordChangeContract;
import com.lafresh.smile2.MemberCenter.Presenter.PasswordChangePresenter;
import com.lafresh.smile2.R;

import java.util.Objects;

public class PasswordChangeFragment extends BaseFragment implements View.OnClickListener, PasswordChangeContract.View {
    public static final String TAG = PasswordChangeFragment.class.getSimpleName();

    private TextView tvErrorMessage, tvSend;
    private EditText etExistPassword, etPassword, etPasswordAgain;

    private static PasswordChangeFragment fragment;
    private PasswordChangeContract.Presenter changePresenter;

    public static PasswordChangeFragment getInstance() {
        if (fragment == null) {
            fragment = new PasswordChangeFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_change, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        changePresenter = new PasswordChangePresenter(this, getRepositoryManager(getContext()));
        ((MainActivity) getActivity()).setAppTitle(R.string.title_password_change);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);

        tvErrorMessage = view.findViewById(R.id.tv_error_message);
        tvErrorMessage.setVisibility(View.GONE);
        tvSend = view.findViewById(R.id.tv_send);
        tvSend.setOnClickListener(this);

        etExistPassword = view.findViewById(R.id.et_exist_password);
        etPassword = view.findViewById(R.id.et_password);
        etPasswordAgain = view.findViewById(R.id.et_password_again);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                changePresenter.checkInputAndSendApi(etExistPassword.getText().toString(), etPassword.getText().toString(), etPasswordAgain.getText().toString());
                break;
        }
    }

    @Override
    public void showInputErrorAlert(int stringRes) {
        new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_hint).setMessage(stringRes).setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public void showFinishAlert() {
        new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_hint).setMessage(R.string.change_password_success).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etExistPassword.setText("");
                etPassword.setText("");
                etPasswordAgain.setText("");
                ((MainActivity) Objects.requireNonNull(getActivity())).resetPassword();
            }
        }).show();
    }

    @Override
    public void showErrorMessage(String stringRes) {
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(stringRes);
    }
}
