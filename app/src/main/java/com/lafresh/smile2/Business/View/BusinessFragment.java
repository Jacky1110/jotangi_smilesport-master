package com.lafresh.smile2.Business.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Business.Contract.BusinessContract;
import com.lafresh.smile2.Business.Presenter.BusinessPresenter;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.R;

public class BusinessFragment extends BaseFragment implements BusinessContract.View , View.OnClickListener{
    public static final String TAG = BusinessFragment.class.getSimpleName();
    private static BusinessFragment fragment;
    private BusinessContract.Presenter presenter;
    private TextView tv_back , tv_check;
    private EditText et_bussiness_code;
    public static BusinessFragment getInstance() {
        if (fragment == null) {
            fragment = new BusinessFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_business_coupon);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        tv_check = view.findViewById(R.id.tv_check);
        tv_back = view.findViewById(R.id.tv_back);
        et_bussiness_code = view.findViewById(R.id.et_business_code);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new BusinessPresenter(this, getRepositoryManager(getContext()));
        tv_check.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_check:
                if(et_bussiness_code.getText().toString().equals("")){
                    new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("請輸入驗證碼").setPositiveButton(android.R.string.ok, null).show();
                }
                else{
                    presenter.checkBusinessCode(et_bussiness_code.getText().toString());
                    Log.e(TAG,et_bussiness_code.getText().toString());
                    et_bussiness_code.setText("");
                }
                break;
            case R.id.tv_back:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void goBusinessProduct(String business_sale_id) {
        ((MainActivity) getActivity()).addFragment(BusinessProductMerchantFragment.getInstance(business_sale_id));
    }

    @Override
    public void ErrorAlert(String errorMessage) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(errorMessage).setPositiveButton(android.R.string.ok, null).show();
    }


}

