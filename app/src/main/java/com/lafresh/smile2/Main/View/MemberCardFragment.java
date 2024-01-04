package com.lafresh.smile2.Main.View;

import static android.content.Context.MODE_PRIVATE;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.Contract.MemberCardContract;
import com.lafresh.smile2.Main.Presenter.MemberCardPresenter;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.User;

public class MemberCardFragment extends BaseFragment implements View.OnClickListener, MemberCardContract.View {
    public static final String TAG = MemberCardFragment.class.getSimpleName();

    private static MemberCardFragment fragment;
    private ConstraintLayout memberCardFront, memberCardBack;
    private ImageView imgBarcode, imgBgCardBack, imgBgCardFront, imgBirth;
    private ImageButton btnToBack, btnToFront;
    private TextView tvBarcodeNumber, tvLevel, tvName, tvBirth, tvPoint, tvCellphone, tvPrepaidAmount, tvBirthConst, tvEmployeeConst, tvPointConst;

    private View viewLineBirth;
    private AnimatorSet rightOutSet, leftInSet;
    private MemberCardContract.Presenter memberCardPresenter;
    private SharedPreferences pref;


    public static MemberCardFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberCardFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_card, container, false);
        initView(view);
//        initAnimation();
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_member_card);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);


        memberCardFront = view.findViewById(R.id.layout_card_front);
        memberCardBack = view.findViewById(R.id.layout_card_back);

        imgBarcode = view.findViewById(R.id.img_barcode);
        imgBgCardBack = view.findViewById(R.id.bg_member_card_back);
        imgBgCardFront = view.findViewById(R.id.bg_member_card_front);

//        btnToBack = view.findViewById(R.id.btn_to_back);
//        btnToBack.setOnClickListener(this);
//
//        btnToFront = view.findViewById(R.id.btn_to_front);
//        btnToFront.setOnClickListener(this);

        tvBarcodeNumber = view.findViewById(R.id.tv_barcode_number);
//        tvLevel = view.findViewById(R.id.tv_member_type);
        //暫時隱藏
//        tvPrepaidAmount = view.findViewById(R.id.tv_prepaid_amount);
        tvName = view.findViewById(R.id.tv_name);
        tvBirth = view.findViewById(R.id.tv_birth);
        tvBirthConst = view.findViewById(R.id.tv_birth_const);
        imgBirth = view.findViewById(R.id.img_birth);
        tvPoint = view.findViewById(R.id.tv_point);
        tvCellphone = view.findViewById(R.id.tv_cellphone);
        viewLineBirth = view.findViewById(R.id.view_line_birth);
        tvPointConst = view.findViewById(R.id.tv_point_const);
        tvEmployeeConst = view.findViewById(R.id.tv_employee_const);

        memberCardPresenter = new MemberCardPresenter(this, getRepositoryManager(getContext()));
        memberCardPresenter.getMemberInfo();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_to_back:
//                flipCard(true);
//                break;
//            case R.id.btn_to_front:
//                flipCard(false);
//                break;
//        }
    }

    private void initAnimation() {
        setAnimators();
        setCameraDistance();
    }

    private void setAnimators() {
        rightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_card_out);
        leftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_card_in);

        rightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                btnToBack.setClickable(false);
                btnToFront.setClickable(false);
            }
        });
        leftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnToBack.setClickable(true);
                btnToFront.setClickable(true);
            }
        });
    }

    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        memberCardFront.setCameraDistance(scale);
        memberCardBack.setCameraDistance(scale);
    }

    private void flipCard(boolean isToBack) {
        rightOutSet.setTarget(isToBack ? memberCardFront : memberCardBack);
        leftInSet.setTarget(isToBack ? memberCardBack : memberCardFront);
        rightOutSet.start();
        leftInSet.start();

        imgBarcode.setOnClickListener(isToBack ? this : null);
    }

    @Override
    public void setUerInfo(User user) {
//        tvLevel.setText(user.isVipUser() ? R.string.type_vip : R.string.type_normal);
//        imgBgCardBack.setImageResource(user.isVipUser() ? R.drawable.bg_vip : R.drawable.bg_normal);
//        imgBgCardFront.setImageResource(user.isVipUser() ? R.drawable.bg_vip : R.drawable.bg_normal);

        pref = requireActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        MemberBean.member_id = pref.getString("member_id", "");
        MemberBean.staff_quota = pref.getString("staff_quota", "");
        MemberBean.staff_mobile = pref.getString("staff_mobile", "");

        Log.d(TAG, "MemberBean.staff_quota: " + MemberBean.staff_quota);
        if (user.getGroup() != null) {
            if (user.getGroup().equals("V")) {
                imgBgCardBack.setImageResource(R.drawable.bg_vip);
            } else if (MemberBean.member_id.length() != 10) {
                imgBgCardBack.setImageResource(R.drawable.bg_blue_background);
            } else {
                imgBgCardBack.setImageResource(R.drawable.bg_normal);
            }
        } else if (MemberBean.member_id.length() != 10) {
            imgBgCardBack.setImageResource(R.drawable.bg_blue_background);
        } else {
            imgBgCardBack.setImageResource(R.drawable.bg_normal);
        }

        tvName.setText(user.getName());

        if (MemberBean.member_id.length() != 10) {
            tvBirth.setVisibility(View.GONE);
            tvBirthConst.setVisibility(View.GONE);
            imgBirth.setVisibility(View.GONE);
            viewLineBirth.setVisibility(View.GONE);

        } else {
            tvBirth.setVisibility(View.VISIBLE);
            tvBirthConst.setVisibility(View.VISIBLE);
            imgBirth.setVisibility(View.VISIBLE);
            viewLineBirth.setVisibility(View.VISIBLE);

            if (user.getBirthday() != null) {
                if (user.getBirthday().equals("")) {
                    tvBirth.setText("尚未填寫");
                    tvBirth.setTextColor(Color.parseColor("#c7c7c7"));
                } else {
                    tvBirth.setText(user.getBirthday());
                    tvBirth.setTextColor(Color.parseColor("#333333"));
                }
            }
        }

        if (MemberBean.member_id.length() != 10) {

            tvEmployeeConst.setVisibility(View.VISIBLE);
            tvPointConst.setVisibility(View.INVISIBLE);
            tvPoint.setText(MemberBean.staff_quota);
            tvCellphone.setText(MemberBean.staff_mobile);

        } else {

            tvPointConst.setVisibility(View.VISIBLE);
            tvEmployeeConst.setVisibility(View.INVISIBLE);
            tvPoint.setText(user.getPoint());
            tvCellphone.setText(user.getMobile());

        }


        //暫時隱藏
//        tvPrepaidAmount.setText(user.getprePaidAmount());
    }

    @Override
    public void setMemberCardInfo(String cardNum) {
        if (cardNum != null) {
            if (cardNum.equals("")) {
                Glide
                        .with(getActivity())
                        .load(R.drawable.barcod)
                        .into(imgBarcode);
                tvBarcodeNumber.setText("無會員卡資訊");
            } else {
                imgBarcode.setBackgroundColor(Color.WHITE);
                tvBarcodeNumber.setText(cardNum);
                Log.d(TAG, "cardNum: " + cardNum);
                if (isAdded()) {
//            Glide.with(this).load(barcodeUrl).into(imgBarcode);
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(cardNum, BarcodeFormat.CODE_39, 400, 56);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imgBarcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
