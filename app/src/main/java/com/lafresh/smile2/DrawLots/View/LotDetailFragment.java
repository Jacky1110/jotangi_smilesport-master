package com.lafresh.smile2.DrawLots.View;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.DrawLots.Contract.LotDetailContract;
import com.lafresh.smile2.DrawLots.Presenter.LotDetailPresenter;
import com.lafresh.smile2.Main.View.CustomViewsInfo;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.LotProduct;
import com.lafresh.smile2.Repository.Model.ProductPicture;
import com.stx.xhb.xbanner.XBanner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.lafresh.smile2.SmileApplication.DOMAIN;

public class LotDetailFragment extends BaseFragment implements LotDetailContract.View{
    public static final String TAG = LotDetailFragment.class.getSimpleName();
    private static final String LOT_ID = "lot_id";
    private static LotDetailFragment fragment;
    private RadioGroup radioGroupProd , radioGroupSpec;
    private String Spec_name = "" , Prod_name = "" , Prod_id = "" , cif_code = "",receipt_no = "",receipt_required = "",member_group_required = "";
    private Button btn_join_lot;
    private EditText et_cif_code,et_receipt_no;
    private XBanner mXBanner;
    private String is_draw;
    private TextView tv_lot_name ,tv_product_spec, tv_lot_dealer_product , tv_lot_product_price , tv_location_name , tv_lot_date , tv_announce_date , tv_pickup_date , tv_content, tv_receipt_no,tv_receipt_tint;
    private ConstraintLayout constraintLayoutSpec, constraintLayoutDoDraw;
    private LotDetailContract.Presenter presenter;
    private int lot_id = 0;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public static LotDetailFragment getInstance(int lot_id) {
        if (fragment == null) {
            fragment = new LotDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(LOT_ID, lot_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lot_detail, container, false);
        if (getArguments() != null) {
            lot_id = getArguments().getInt(LOT_ID, 0);
        }
        initView(view);
        return view;
    }
    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_lot);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        radioGroupProd = view.findViewById(R.id.radio_group_prod);
        radioGroupSpec = view.findViewById(R.id.radio_group_spec);
        mXBanner = view.findViewById(R.id.xbanner);
        tv_lot_name = view.findViewById(R.id.tv_lot_name);
        tv_lot_dealer_product = view.findViewById(R.id.tv_lot_dealer_product);
        tv_lot_product_price = view.findViewById(R.id.tv_lot_product_price);
        tv_location_name = view.findViewById(R.id.tv_location_name);
        tv_lot_date = view.findViewById(R.id.tv_lot_date);
        tv_announce_date = view.findViewById(R.id.tv_announce_date);
        tv_pickup_date = view.findViewById(R.id.tv_pickup_date);
        tv_content = view.findViewById(R.id.tv_content);
        tv_receipt_no = view.findViewById(R.id.textView26);
        tv_receipt_tint = view.findViewById(R.id.tv_receipt_tint);

        btn_join_lot = view.findViewById(R.id.btn_join_lot);
        et_cif_code = view.findViewById(R.id.et_cif_code);
        et_receipt_no = view.findViewById(R.id.et_receipt_no);
        tv_product_spec = view.findViewById(R.id.tv_product_spec);
        constraintLayoutDoDraw = view.findViewById(R.id.constraintLayout9);
        constraintLayoutSpec = view.findViewById(R.id.constraintLayout10);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new LotDetailPresenter(this, getRepositoryManager(getContext()));
        presenter.getLotDetailData(lot_id);


        btn_join_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cif_code = et_cif_code.getText().toString();
                receipt_no = et_receipt_no.getText().toString();
                presenter.checkIdentity(member_group_required ,receipt_required ,is_draw , cif_code , Prod_id , Spec_name,receipt_no);
            }
        });

    }

    @Override
    public void setLotDetail(final DrawLots lotDetail) {
        if(lotDetail.getCan_draw_flag().equals("Y")){
            constraintLayoutDoDraw.setVisibility(View.VISIBLE);
        }else{
            constraintLayoutDoDraw.setVisibility(View.GONE);
        }

        is_draw = lotDetail.getIs_draw();
        receipt_required = lotDetail.getReceipt_required();
        member_group_required = lotDetail.getMember_group_required();
        if(receipt_required==null) receipt_required = "N";
        if(member_group_required==null) member_group_required = "N";

        //Log.d("receipt_required",receipt_required);
        //Log.d("member_group_required",member_group_required);
        if(receipt_required.equals("N")){ //不用發票欄位
            tv_receipt_no.setVisibility(View.GONE);
            et_receipt_no.setVisibility(View.GONE);
            tv_receipt_tint.setVisibility(View.GONE);
        }

        final List<LotProduct> lotProductList = lotDetail.getLot_product_list();
        et_cif_code.setText("");
        et_receipt_no.setText("");
        tv_lot_name.setText(lotDetail.getTitle());
        tv_location_name.setText(lotDetail.getLocation_name());
        tv_lot_date.setText(lotDetail.getStart_date() + " ~ " + lotDetail.getEnd_date());
        tv_announce_date.setText(lotDetail.getAnnounce_date());
        tv_pickup_date.setText(lotDetail.getPickup_date());
        tv_content.setText(Html.fromHtml(lotDetail.getContent()));
        List<ProductPicture> productPictureList = lotDetail.getLot_picture_url_list();
        List<CustomViewsInfo> data = new ArrayList<>();
        for(int i = 0 ; i < productPictureList.size();i++){
            data.add(new CustomViewsInfo(DOMAIN + productPictureList.get(i).getPictureurl(),productPictureList.get(i).getId()));
        }
        mXBanner.setBannerData(R.layout.layout_main_activity, data);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageView img_carousel = view.findViewById(R.id.img_carousel);
                Glide
                        .with(getActivity())
                        .load(((CustomViewsInfo) model).getXBannerUrl())
                        .into(img_carousel);
            }
        });

        for (int i = 0; i < lotProductList.size(); i++) {
            final RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//            //设置RadioButton边距 (int left, int top, int right, int bottom)
            lp.setMargins(0,0,8,8);
            //设置RadioButton背景
            //radioButton.setBackgroundResource(R.drawable.xx);
            //设置RadioButton的样式
            radioButton.setLayoutParams(lp);
            radioButton.setTextSize(13);
            radioButton.setButtonDrawable(null);
//            radioButton.setChecked(true);
            //修改文字的顏色  必須使用 getResources().getColorStateList(R.color.select_lot_text)
            //使用getColor會錯誤  所以使用getColorStateList
            radioButton.setTextColor(getResources().getColorStateList(R.color.select_lot_text));
            radioButton.setBackgroundResource(R.drawable.selec_lot_prod);
            radioButton.setTag(lotProductList.get(i).getId());
//            radioButton.setId(i);
            //設置字型
            Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_regular);
            radioButton.setTypeface(typeface);
            //设置文字距离四周的距离
            radioButton.setPadding(20,0,20,0);
            //设置文字
            radioButton.setText(lotProductList.get(i).getLot_product_name());
            //设置radioButton的点击事件
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLotSpecData(false , lotProductList , v.getTag().toString());
                }
            });
            //将radioButton添加到radioGroup中
            radioGroupProd.addView(radioButton);
//            radioGroupProd.check(0);
        }
        setLotSpecData(true , lotProductList , "0");
    }

    @Override
    public void ErrorAlert(int errorMessage) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(errorMessage).setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public void showErrorAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public void showSuccessMessage(int successMessage) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(successMessage).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().onBackPressed();
            }
        }).show();
    }

    @Override
    public void showCheckAlert() {
        new AlertDialog.Builder(fragment.getActivity())
                .setTitle(R.string.dialog_hint)
                .setMessage(String.format(getString(R.string.check_join),Prod_name,Spec_name))
                .setNegativeButton(R.string.re_verify, null)
                .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.joinLot(lot_id , cif_code , Prod_id , Spec_name,receipt_no);
                    }
                })
                .show();
    }

    private void setLotSpecData(Boolean bInit , List<LotProduct> lotProductList , String sTag){
        radioGroupSpec.removeAllViews();
        Spec_name = "";
        if(bInit) {
            tv_product_spec.setVisibility(View.VISIBLE);
            constraintLayoutSpec.setVisibility(View.GONE);
        }else{
            tv_product_spec.setVisibility(View.GONE);
            constraintLayoutSpec.setVisibility(View.VISIBLE);
            for(int j = 0 ; j < lotProductList.size() ; j++){
                if(Integer.toString(lotProductList.get(j).getId()).equals(sTag)){
                    tv_lot_dealer_product.setText(lotProductList.get(j).getLot_dealer_product_id());

                    tv_lot_product_price.setText("$" + mDecimalFormat.format((double)Integer.parseInt(lotProductList.get(j).getLot_product_price())));
                    for (int i = 0 ; i < lotProductList.get(j).getLot_spec_list().size() ; i++){
                        Log.e(TAG,lotProductList.get(j).getLot_spec_list().get(i).getSpecname());
                        RadioButton radioButtonSpec = new RadioButton(getContext());
                        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0,0,8,8);
                        radioButtonSpec.setLayoutParams(lp);
                        radioButtonSpec.setTextSize(13);
                        radioButtonSpec.setButtonDrawable(null);
                        radioButtonSpec.setTextColor(getResources().getColorStateList(R.color.select_lot_text));
                        radioButtonSpec.setTag(R.string.lot_spec_name,lotProductList.get(j).getLot_spec_list().get(i).getSpecname());
                        radioButtonSpec.setTag(R.string.lot_prod_name,lotProductList.get(j).getLot_product_name());
                        radioButtonSpec.setTag(R.string.lot_prod_id,lotProductList.get(j).getId());
                        radioButtonSpec.setBackgroundResource(R.drawable.selec_lot_prod);
                        //設置字型
                        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_regular);
                        radioButtonSpec.setTypeface(typeface);
                        radioButtonSpec.setPadding(20,0,20,0);
                        radioButtonSpec.setText(lotProductList.get(j).getLot_spec_list().get(i).getSpecname());
                        radioButtonSpec.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e(TAG,"Spec_name : " + v.getTag(R.string.lot_spec_name) + "Prod_name : " + v.getTag(R.string.lot_prod_name) + "Prod_id : " + v.getTag(R.string.lot_prod_id));
                                Spec_name = v.getTag(R.string.lot_spec_name).toString();
                                Prod_name = v.getTag(R.string.lot_prod_name).toString();
                                Prod_id = v.getTag(R.string.lot_prod_id).toString();
                                Log.e(TAG,"new Click Spec :" + Spec_name + " Prod_name : " + Prod_name + " Prod_id : " + Prod_id);
                            }
                        });
                        radioGroupSpec.addView(radioButtonSpec);
                    }
                }
            }
        }
//        if(!bInit){

//        }
//        else{
//            tv_lot_dealer_product.setText(lotProductList.get(0).getLot_dealer_product_id());
//            tv_lot_product_price.setText("$ " + mDecimalFormat.format((double)Integer.parseInt(lotProductList.get(0).getLot_product_price())));
//            for (int i = 0 ; i < lotProductList.get(0).getLot_spec_list().size() ; i++){
//                Log.e(TAG,lotProductList.get(0).getLot_spec_list().get(i).getSpecname());
//                RadioButton radioButtonSpec = new RadioButton(getContext());
//                RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//                lp.setMargins(0,0,8,8);
//                radioButtonSpec.setLayoutParams(lp);
//                radioButtonSpec.setTextSize(13);
////                radioButtonSpec.setTag(R.string.lot_spec_name,lotProductList.get(0).getLot_spec_list().get(i).getSpecname());
////                radioButtonSpec.setTag(R.string.lot_prod_name,lotProductList.get(0).getLot_product_name());
////                radioButtonSpec.setTag(R.string.lot_prod_id,lotProductList.get(0).getId());
//                radioButtonSpec.setButtonDrawable(null);
//                radioButtonSpec.setTextColor(getResources().getColorStateList(R.color.select_lot_text));
//                radioButtonSpec.setBackgroundResource(R.drawable.selec_lot_prod);
//                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_regular);
//                radioButtonSpec.setTypeface(typeface);
//                radioButtonSpec.setPadding(20,0,20,0);
//                radioButtonSpec.setText(lotProductList.get(0).getLot_spec_list().get(i).getSpecname());
////                radioButtonSpec.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                    }
////                });
//                radioGroupSpec.addView(radioButtonSpec);
//            }
//        }
    }
}



