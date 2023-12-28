package com.lafresh.smile2.MemberCenter.View;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.MemberCenter.AboutContract;
import com.lafresh.smile2.MemberCenter.Adapter.PointRecordAdapter;
import com.lafresh.smile2.MemberCenter.MemberPointContract;
import com.lafresh.smile2.MemberCenter.Presenter.AboutPresenter;
import com.lafresh.smile2.MemberCenter.Presenter.MemberPointPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.About;
import com.lafresh.smile2.Repository.Model.PointHistory;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Utils.IntentUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AboutFragment extends BaseFragment implements AboutContract.View , View.OnClickListener{
    public static final String TAG = AboutFragment.class.getSimpleName();

    private static AboutFragment fragment;
    private AboutContract.Presenter presenter;

    private TextView tv_office_address , tv_office_service , tv_office_tel , tv_office_line , tv_office_mail , tv_office_fb;
    private TextView tv_enterprise_service , tv_enterprise_tel , tv_enterprise_mail ;

    public static AboutFragment getInstance() {
        if (fragment == null) {
            fragment = new AboutFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new AboutPresenter(this, getRepositoryManager(getContext()));
        presenter.getAboutData();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.contact_us);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);

        tv_office_address = view.findViewById(R.id.tv_office_address);
        tv_office_service = view.findViewById(R.id.tv_office_service);
        tv_office_tel = view.findViewById(R.id.tv_office_tel);
        tv_office_line = view.findViewById(R.id.tv_office_line);
        tv_office_mail = view.findViewById(R.id.tv_office_mail);
        tv_office_fb = view.findViewById(R.id.tv_office_fb);
        tv_enterprise_service = view.findViewById(R.id.tv_enterprise_service);
        tv_enterprise_tel = view.findViewById(R.id.tv_enterprise_tel);
        tv_enterprise_mail = view.findViewById(R.id.tv_enterprise_mail);

    }

    @Override
    public void setAboutData(List<About> aboutList) {
        final About office = aboutList.get(0);
        final About enterprise = aboutList.get(1);
        tv_office_address.setText(office.getAddress());
        tv_office_service.setText(office.getService_time());
        tv_office_tel.setText(office.getPhone());
        tv_office_line.setText(office.getLine_at());
        tv_office_mail.setText(office.getEmail());
        tv_office_fb.setText(office.getFb_url());
        tv_enterprise_service.setText(enterprise.getService_time());
        tv_enterprise_tel.setText(enterprise.getPhone());
        tv_enterprise_mail.setText(enterprise.getEmail());

//        tv_office_tel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_enterprise_tel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_office_mail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_enterprise_mail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_office_fb.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        tv_office_tel.setOnClickListener(this);
        tv_enterprise_tel.setOnClickListener(this);
        tv_office_mail.setOnClickListener(this);
        tv_enterprise_mail.setOnClickListener(this);
        tv_office_fb.setOnClickListener(this);
    }

    @Override
    public void intentToPhoneCall(String phone) {
        IntentUtils.intentToCall((BaseActivity) getActivity(), phone);
    }
//    intentToShare

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_office_tel:
                presenter.callPhone(tv_office_tel.getText().toString());
                break;
            case R.id.tv_enterprise_tel:
                presenter.callPhone(tv_enterprise_tel.getText().toString());
                break;
            case R.id.tv_office_mail:
                IntentUtils.intentToEmail((BaseActivity) getActivity(), tv_office_mail.getText().toString());
                break;
            case R.id.tv_enterprise_mail:
                IntentUtils.intentToEmail((BaseActivity) getActivity(), tv_enterprise_mail.getText().toString());
                break;
            case R.id.tv_office_fb:
                IntentUtils.intentToOutWeb((BaseActivity) getActivity(), tv_office_fb.getText().toString());
                break;
        }
    }
}
