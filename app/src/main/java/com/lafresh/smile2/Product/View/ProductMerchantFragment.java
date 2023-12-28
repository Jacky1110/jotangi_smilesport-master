package com.lafresh.smile2.Product.View;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Product.Adapter.MerchantListAdapter;
import com.lafresh.smile2.Product.Contract.ProductMerchantContract;
import com.lafresh.smile2.Product.Presenter.ProductMerchantPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMerchantFragment extends BaseFragment implements ProductMerchantContract.View{
    public static final String TAG = ProductMerchantFragment.class.getSimpleName();

    private static ProductMerchantFragment fragment;
    private ProductMerchantContract.Presenter productmerchantPresenter;
    private RecyclerView recyclerView;
    private EditText edit_keyword;
    private TextView txtProductNoResult;
    Drawable drawable;
    private ConstraintLayout layout_clean;
    private MerchantListAdapter merchantListAdapter;
    public static ProductMerchantFragment getInstance() {
        if (fragment == null) {
            fragment = new ProductMerchantFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_merchant, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        edit_keyword = view.findViewById(R.id.edit_keyword);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        txtProductNoResult= view.findViewById(R.id.product_no_result);
        layout_clean = view.findViewById(R.id.layout_clean);
        layout_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_keyword.setText("");
                productmerchantPresenter.getMerchantList("");
                layout_clean.setVisibility(View.GONE);
            }
        });

        //監聽 EditText Focus事件
        View.OnFocusChangeListener mFocusChangedListener = null;
        mFocusChangedListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    layout_clean.setVisibility(View.VISIBLE);
                    setClearList(false, false);
                }else {
                    layout_clean.setVisibility(View.GONE);
                }
            }
        };
        edit_keyword.setOnFocusChangeListener(mFocusChangedListener);
        //監聽 EditText Focus事件

        edit_keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_clean.setVisibility(View.VISIBLE);
                setClearList(false, false);
            }
        });

        //監聽送出事件
        edit_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!v.getText().toString().equals("")){
                    productmerchantPresenter.getProductList("NEW","" , "" , v.getText().toString());
                }
                return false;
            }
        });

        productmerchantPresenter = new ProductMerchantPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        merchantListAdapter = new MerchantListAdapter(productmerchantPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(merchantListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productmerchantPresenter.getMerchantList("");
    }


    //取得品牌列表
    @Override
    public void setMerchantlList(List<Merchant> merchantlList) {
        hideSoftKeyboard(((MainActivity) getActivity()));
        setClearList(merchantlList.size()>0, true);

        if (merchantlList.size()>0){
            List<Merchant> merchantsleft = new ArrayList<>();
            List<Merchant> merchantsright = new ArrayList<>();
            for(int i = 0 ; i < merchantlList.size() ; i++){
                Log.e(TAG,merchantlList.get(i).getPicture_url());

//                Resources res = this.getResources();
//                drawable = res.getDrawable(R.color.white);
                recyclerView.setBackgroundResource(R.color.white);

                if(i % 2 == 0){
                    merchantsleft.add(merchantlList.get(i));
                }
                else{
                    merchantsright.add(merchantlList.get(i));
                }
            }
            merchantListAdapter.setMerchant(merchantsleft ,merchantsright);
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void goPageProductList(int store_id) {
        edit_keyword.setText("");
        ((MainActivity) getActivity()).addFragment(ProductFragment.getInstance(store_id));
    }

    @Override
    public void goPageProductDetail(int product_id) {
        edit_keyword.setText("");
        ((MainActivity) getActivity()).addFragment(ProductDetailFragment.getInstance(product_id));
    }

    //品牌列表頁面EditText送出後搜尋商品
    @Override
    public void setProductList(List<Product> productList) {
        hideSoftKeyboard(((MainActivity) getActivity()));
        setClearList(productList.size() > 0, true);

        if(productList.size() > 0){
            Resources res = this.getResources();
            drawable = res.getDrawable(R.color.greyBackground);
            recyclerView.setBackground(drawable);

            List<Product> productleft = new ArrayList<>();
            List<Product> productright = new ArrayList<>();

            if (productList.size() < 1){
                Log.e(TAG,"clean");
                merchantListAdapter.clean();
            }
            else{
                for(int i = 0 ; i < productList.size() ; i++){
                    if(i % 2 == 0){
                        productleft.add(productList.get(i));
                    }
                    else{
                        productright.add(productList.get(i));
                    }
                }
            }
            merchantListAdapter.setProduct(productleft ,productright);
            recyclerView.scrollToPosition(0);
        }
    }

    private void hideSoftKeyboard(Activity mActivity) {
        if(mActivity!=null){
            InputMethodManager inputMethodManager =(InputMethodManager) mActivity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE);
            View focusedView = getActivity().getCurrentFocus();
            if(focusedView!=null){
                inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    private void setClearList(boolean atLeastOneItem, boolean isText){
        if(atLeastOneItem){
            recyclerView.setVisibility(View.VISIBLE);
            txtProductNoResult.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            txtProductNoResult.setVisibility(View.VISIBLE);
        }

        if(isText){
            txtProductNoResult.setText(R.string.product_no_result);
        }else{
            txtProductNoResult.setText("");
        }
    }
}

