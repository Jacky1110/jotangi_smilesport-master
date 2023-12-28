package com.lafresh.smile2.Main.View;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.Contract.MainIndexContract;
import com.lafresh.smile2.Main.Presenter.MainIndexPresenter;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.MemberCenter.View.MailFileFragment;
import com.lafresh.smile2.MemberCenter.View.MemberPointFragment;
import com.lafresh.smile2.MemberCenter.View.PasswordChangeFragment;
import com.lafresh.smile2.MemberCenter.View.WebViewFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.SmileApplication;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import static com.lafresh.smile2.SmileApplication.DOMAIN;

public class MainIndexFragment extends BaseFragment implements MainIndexContract.View, View.OnClickListener {
    public static final String TAG = MainIndexFragment.class.getSimpleName();

    private static MainIndexFragment fragment;
    private XBanner mXBanner;
    private ImageView imv_membercard;
    //    private ImageView img_point , img_coupon;
    private ConstraintLayout layout_pay, layout_coupon, layout_gift, layout_enterprise;
    private TextView txt_name;

    private SharedPreferences pref;
    private MainIndexContract.Presenter mainindexPresenter;

    public static MainIndexFragment getInstance() {
        if (fragment == null) {
            fragment = new MainIndexFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_index);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        mXBanner = view.findViewById(R.id.xbanner);
        imv_membercard = view.findViewById(R.id.img_member_card);
        imv_membercard.setOnClickListener(this);

        layout_pay = view.findViewById(R.id.layout_pay);
        layout_pay.setOnClickListener(this);
        layout_coupon = view.findViewById(R.id.layout_coupon);
        layout_coupon.setOnClickListener(this);
        layout_gift = view.findViewById(R.id.layout_gift);
        layout_gift.setOnClickListener(this);
        layout_enterprise = view.findViewById(R.id.layout_enterprise);
        layout_enterprise.setOnClickListener(this);

//        img_point = view.findViewById(R.id.img_point);
//        img_point.setOnClickListener(this);
//        img_coupon = view.findViewById(R.id.img_coupon);
//        img_coupon.setOnClickListener(this);


        txt_name = view.findViewById(R.id.txt_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_member_card:
                ((MainActivity) getActivity()).goMemberCard();
                break;
//            case R.id.img_coupon:
//                ((MainActivity) getActivity()).checkLoginForCoupon();
//                break;
//            case R.id.img_point:
//                ((MainActivity) getActivity()).checkLoginForBusiness();
//                break;
//            case R.id.layout_pay:
//                ((MainActivity)getActivity()).checkLoginForPay();
//                break;
            case R.id.layout_pay:
                if (MemberBean.member_id.length() != 10) {

                    showAlertDialog();

                } else {

                    ((MainActivity) getActivity()).addFragment(MemberPointFragment.getInstance());
                }
                break;
            case R.id.layout_coupon:
                if (MemberBean.member_id.length() != 10) {

                    showAlertDialog();

                } else {

                    ((MainActivity) getActivity()).checkLoginForCoupon();

                }
                break;
            case R.id.layout_gift:
                if (MemberBean.member_id.length() != 10) {

                    showAlertDialog();

                } else {

                    ((MainActivity) getActivity()).checkLoginForLot();
                }
                break;
            case R.id.layout_enterprise:
                if (MemberBean.member_id.length() != 10) {

                    showAlertDialog();

                } else {

                    ((MainActivity) getActivity()).checkLoginForBusiness();

                }

                break;
            default:
                break;
        }
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("警告");
        builder.setMessage("員購身分不開放此功能!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 在点击"确定"按钮时的处理逻辑
                // 可以在这里添加相应的操作
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainindexPresenter = new MainIndexPresenter(this, getRepositoryManager(getContext()));
        mainindexPresenter.getCarouselList();
        txt_name.setText(mainindexPresenter.getName());

        getData();
        mainindexPresenter.checkMemberData();
    }

    private void getData() {

        pref = requireActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        MemberBean.member_id = pref.getString("member_id", "");
        MemberBean.staff_quota = pref.getString("staff_quota", "");
        MemberBean.staff_mobile = pref.getString("staff_mobile", "");

        if (mainindexPresenter.getGroup() != null) {
            if (mainindexPresenter.getGroup().equals("V")) {
                imv_membercard.setBackgroundResource(R.drawable.membercard_vip);
            } else if (MemberBean.member_id.length() != 10) {

                imv_membercard.setBackgroundResource(R.drawable.staff_card);

            } else {

                imv_membercard.setBackgroundResource(R.drawable.membercard_normal);
            }
        }
    }

    @Override
    public void setMemberCardImg(String group) {
        if (group != null) {
            if (group.equals("V")) {
                imv_membercard.setBackgroundResource(R.drawable.membercard_vip);
            }
        }
    }

    @Override
    public void setCarouselList(final List<Carousel> carouselList) {
        List<CustomViewsInfo> data = new ArrayList<>();
        for (int i = 0; i < carouselList.size(); i++) {
            data.add(new CustomViewsInfo(DOMAIN + carouselList.get(i).getPicture_url(), carouselList.get(i).getId()));
        }
        mXBanner.setBannerData(R.layout.layout_main_activity, data);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageView img_carousel = (ImageView) view.findViewById(R.id.img_carousel);
                Glide
                        .with(getActivity())
                        .load(((CustomViewsInfo) model).getXBannerUrl())
                        .into(img_carousel);
            }
        });
        mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Log.e(TAG, carouselList.get(position).getTitle());
                ((MainActivity) getActivity()).addFragment(NewsFragment.getInstance(carouselList.get(position)));
//                ((MainActivity) getActivity()).addFragment(NewsFragment.getInstance(((CustomViewsInfo)model).getXBannerTitle()));
//                ((MainActivity) getActivity()).goNewsDetail(((CustomViewsInfo)model).getXBannerTitle());
            }
        });
    }
}

