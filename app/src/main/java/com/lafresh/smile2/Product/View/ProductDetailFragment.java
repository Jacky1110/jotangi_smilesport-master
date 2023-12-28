package com.lafresh.smile2.Product.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Login.VIew.LoginActivity;
import com.lafresh.smile2.Main.Contract.MainIndexContract;
import com.lafresh.smile2.Main.Presenter.MainIndexPresenter;
import com.lafresh.smile2.Main.View.CustomViewsInfo;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.MemberCenter.View.WebViewFragment;
import com.lafresh.smile2.Product.Contract.ProductDetailContract;
import com.lafresh.smile2.Product.Presenter.ProductDetailPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductPicture;
import com.lafresh.smile2.Repository.Model.ProductSpec;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.IntentUtils;
import com.stx.xhb.xbanner.XBanner;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lafresh.smile2.SmileApplication.DOMAIN;

public class ProductDetailFragment extends BaseFragment implements ProductDetailContract.View {
    public static final String TAG = ProductDetailFragment.class.getSimpleName();
    private static final String PRODUCT_ID = "product_id";
    private static ProductDetailFragment fragment;
    private XBanner mXBanner;
    private Spinner spinner_spec;
    private ConstraintLayout layout_plus, layout_minus, layout_size;
    private EditText edit_num;
    private Button btn_shoppingcart;
    private Button btn_freight_title;
    private TextView tv_price, tv_sale_price, tv_store_name, tv_product_type, tv_dealer_product_id, tv_desc;
    private ProductDetailContract.Presenter presenter;
    private int product_id = 0;
    private int quantity = 0;
    private final String[] Spec_ID = {""};
    private final String[] SpecQty = {""};
    private final String[] Stock = {""};
    private ArrayAdapter<String> ProductSpinnerArray;

    public static ProductDetailFragment getInstance(int product_id) {
        if (fragment == null) {
            fragment = new ProductDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, product_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        if (getArguments() != null) {
            product_id = getArguments().getInt(PRODUCT_ID, 0);
            Log.e(TAG, product_id + "");
            initView(view);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        mXBanner = view.findViewById(R.id.xbanner);
        tv_dealer_product_id = view.findViewById(R.id.tv_dealer_product_id);
        tv_price = view.findViewById(R.id.tv_price);
        tv_sale_price = view.findViewById(R.id.tv_sale_price);
        tv_store_name = view.findViewById(R.id.tv_store_name);
        tv_product_type = view.findViewById(R.id.tv_product_type);
        tv_desc = view.findViewById(R.id.tv_desc);
        spinner_spec = view.findViewById(R.id.spinner_spec);
        layout_minus = view.findViewById(R.id.layout_minus);
        layout_plus = view.findViewById(R.id.layout_plus);
        edit_num = view.findViewById(R.id.edit_num);
        btn_freight_title = view.findViewById(R.id.btn_freight_title);
        edit_num.setText("1");
        btn_shoppingcart = view.findViewById(R.id.btn_shoppingcart);
        layout_size = view.findViewById(R.id.layout_size);

        layout_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.valueOf(edit_num.getText().toString()).intValue();
                quantity++;
                edit_num.setText(Integer.toString(quantity));
            }
        });

        layout_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.valueOf(edit_num.getText().toString()).intValue();
                if (quantity > 0) {
                    quantity--;
                }
                edit_num.setText(Integer.toString(quantity));
            }
        });

        if (MemberBean.member_id.length() != 10) {

            btn_shoppingcart.setBackgroundResource(R.drawable.rec_stroke_grey1);

        } else {

            btn_shoppingcart.setBackgroundResource(R.drawable.rec_stroke_orange);
        }

        btn_shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MemberBean.member_id.length() == 10) {

                    presenter.addShoppingCart(Integer.toString(product_id), Spec_ID[0], SpecQty[0], Stock[0], edit_num.getText().toString());
                    edit_num.setText("1");

                }
            }
        });

        layout_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.tab_shop, SmileApplication.WEBVIEW_SIZE_SPEC_URL));
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProductDetailPresenter(this, getRepositoryManager(getContext()));
        presenter.getProductDetailByID(Integer.toString(product_id));

    }

    @Override
    public void setProductDetail(List<Product> productDetail) {
        List<ProductPicture> productPictureList = productDetail.get(0).getPicture_url_list();
        List<CustomViewsInfo> data = new ArrayList<>();
        for (int i = 0; i < productPictureList.size(); i++) {
            data.add(new CustomViewsInfo(DOMAIN + productPictureList.get(i).getPictureurl(), productPictureList.get(i).getId()));
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

        DecimalFormat mDecimalFormat = new DecimalFormat("#,###");
        tv_product_type.setText(productDetail.get(0).getProduct_name());
        tv_store_name.setText(productDetail.get(0).getStore_name());
        tv_sale_price.setText("NT$" + mDecimalFormat.format((double) productDetail.get(0).getSale_price()));
        tv_price.setText("NT$" + mDecimalFormat.format((double) productDetail.get(0).getPrice()));
        tv_dealer_product_id.setText(productDetail.get(0).getDealer_product_id());
        btn_freight_title.setText(productDetail.get(0).getFreight_title());
        tv_desc.setText(Html.fromHtml(productDetail.get(0).getDesc()));
        //tv_desc.setMovementMethod(LinkMovementMethod.getInstance());
        tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        edit_num.setText("1");

        List<ProductSpec> specArrayList = productDetail.get(0).getSpec_list();

        final ArrayList<String> IDArrayList = new ArrayList<>();
        IDArrayList.add("0");
        ArrayList<String> SpecNameArrayList = new ArrayList<>();
        SpecNameArrayList.add("商品尺寸選擇");
        final ArrayList<String> StockArrayList = new ArrayList<>();
        StockArrayList.add("0");

        final ArrayList<String> SpecQtyArrayList = new ArrayList<>();
        SpecQtyArrayList.add("0");

        for (int i = 0; i < specArrayList.size(); i++) {
            SpecNameArrayList.add(specArrayList.get(i).getSpecname());
            IDArrayList.add(Integer.toString(specArrayList.get(i).getId()));
            StockArrayList.add(specArrayList.get(i).getStock());
            SpecQtyArrayList.add(Integer.toString(specArrayList.get(i).getSpec_qty()));
        }

        ProductSpinnerArray = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_style_spec,
                SpecNameArrayList);
        spinner_spec.setAdapter(ProductSpinnerArray);
        spinner_spec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spec_ID[0] = IDArrayList.get(position);
                SpecQty[0] = SpecQtyArrayList.get(position);
                Stock[0] = StockArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void showErrorAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        edit_num.setText("1");
        ((MainActivity) getActivity()).refreshBadge();
    }
}

