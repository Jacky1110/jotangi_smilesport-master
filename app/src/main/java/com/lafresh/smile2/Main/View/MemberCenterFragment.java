package com.lafresh.smile2.Main.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.Contract.MemberCenterContract;
import com.lafresh.smile2.Main.Presenter.MemberCenterPresenter;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.MemberCenter.View.AboutFragment;
import com.lafresh.smile2.MemberCenter.View.FaqFragment;
import com.lafresh.smile2.MemberCenter.View.MemberInfoChangeFragment;
import com.lafresh.smile2.MemberCenter.View.MemberPointFragment;
import com.lafresh.smile2.MemberCenter.View.MailFileFragment;
import com.lafresh.smile2.MemberCenter.View.PasswordChangeFragment;
import com.lafresh.smile2.MemberCenter.View.WebViewFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.IntentUtils;

import java.util.Objects;

public class MemberCenterFragment extends BaseFragment implements View.OnClickListener, MemberCenterContract.View {
    public static final String TAG = MemberCenterFragment.class.getSimpleName();

    private TextView tvName, tvMemberInfo, tvChangePassword, tvPoint, tvCoupon, tvContactUs, tvTerms, tvSmilePoint, tvTransRecord, tvPrivacy, tv_smilepoint, tvAccountInfo;
    private ConstraintLayout btnLogout;

    private View view1, view2, view3, view4, view5;

    private static MemberCenterFragment fragment;
    private MemberCenterContract.Presenter memberPresenter;

    public static MemberCenterFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberCenterFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_center, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberPresenter = new MemberCenterPresenter(this, getRepositoryManager(getContext()));
        memberPresenter.getUserInfo();

        init();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_member_info);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);

        tvName = view.findViewById(R.id.tv_name);
        tvMemberInfo = view.findViewById(R.id.tv_member_info);
        tvMemberInfo.setOnClickListener(this);
        tvChangePassword = view.findViewById(R.id.tv_change_password);
        tvChangePassword.setOnClickListener(this);
        tvPoint = view.findViewById(R.id.tv_point);
        tvPoint.setOnClickListener(this);
        tvCoupon = view.findViewById(R.id.tv_coupon);
        tvCoupon.setOnClickListener(this);
//        tvShare = view.findViewById(R.id.tv_share);
//        tvShare.setOnClickListener(this);
        tvContactUs = view.findViewById(R.id.tv_contact_us);
        tvContactUs.setOnClickListener(this);
        tvPrivacy = view.findViewById(R.id.tv_privacy);
        tvPrivacy.setOnClickListener(this);
        tvTerms = view.findViewById(R.id.tv_terms);
        tvTerms.setOnClickListener(this);
        tvTransRecord = view.findViewById(R.id.tv_trans_record);
        tvTransRecord.setOnClickListener(this);

        tvAccountInfo = view.findViewById(R.id.tv_account_info);

        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);
        view5 = view.findViewById(R.id.view5);


        //暫時隱藏
//        tvSmilePoint = view.findViewById(R.id.tv_smilepoint);
//        tvSmilePoint.setOnClickListener(this);
//        tvTransRecord = view.findViewById(R.id.tv_trans_record);
//        tvTransRecord.setOnClickListener(this);

        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);
    }

    private void init() {

        if (MemberBean.member_id.length() != 10) {

            tvMemberInfo.setVisibility(View.GONE);
            tvAccountInfo.setVisibility(View.GONE);
            tvTransRecord.setVisibility(View.GONE);
            tvPoint.setVisibility(View.GONE);
            tvCoupon.setVisibility(View.GONE);
            tvPrivacy.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
            view5.setVisibility(View.GONE);


        } else {

            tvMemberInfo.setVisibility(View.VISIBLE);
            tvAccountInfo.setVisibility(View.VISIBLE);
            tvTransRecord.setVisibility(View.VISIBLE);
            tvPoint.setVisibility(View.VISIBLE);
            tvCoupon.setVisibility(View.VISIBLE);
            tvPrivacy.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            view5.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_member_info:
                ((MainActivity) getActivity()).addFragment(MemberInfoChangeFragment.getInstance());
                break;
            case R.id.tv_change_password:
                ((MainActivity) getActivity()).addFragment(PasswordChangeFragment.getInstance());
                break;
            case R.id.tv_point:
                ((MainActivity) getActivity()).addFragment(MemberPointFragment.getInstance());
//                ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.my_point, SmileApplication.WEBVIEW_SMILEPOINT_URL));

                break;
            //暫時隱藏
//            case R.id.tv_smilepoint:
//                ((MainActivity) getActivity()).addFragment(MemberPointFragment.getInstance());
//                break;
            case R.id.tv_coupon:
                ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.coupon, SmileApplication.WEBVIEW_COUPONS_URL));
                break;
//            case R.id.tv_share:
//                IntentUtils.intentToShare((MainActivity) getActivity(), getString(R.string.share_text));
//                break;
            case R.id.tv_contact_us:
//                ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.contact_us, SmileApplication.WEBVIEW_CONTACT_URL));
                ((MainActivity) getActivity()).addFragment(AboutFragment.getInstance());
                break;
            case R.id.tv_terms:
                ((MainActivity) getActivity()).addFragment(FaqFragment.getInstance("1"));
//                ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.privacy_policy, SmileApplication.WEBVIEW_TERMS_URL));
                break;
            case R.id.tv_privacy:
                ((MainActivity) getActivity()).addFragment(FaqFragment.getInstance("2"));
//                ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.terms_of_use, SmileApplication.WEBVIEW_PRIVACY_URL));
                break;
            //暫時隱藏
            case R.id.tv_trans_record:
                ((MainActivity) getActivity()).addFragment(TransRecordFragment.getInstance());
//                ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.trans_record, SmileApplication.WEBVIEW_ORDER_URL));
                break;
            case R.id.btn_logout:
                memberPresenter.logout();
                break;
        }
    }

    @Override
    public void setUserName(String name) {
        tvName.setText(name);
    }

    @Override
    public void redirectToMainPage() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setTabPage(0);
        ((MainActivity) Objects.requireNonNull(getActivity())).refreshBadge();
    }
}
