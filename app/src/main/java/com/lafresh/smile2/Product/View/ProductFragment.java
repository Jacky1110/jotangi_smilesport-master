package com.lafresh.smile2.Product.View;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.Pools;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Product.Adapter.ProductListAdapter;
import com.lafresh.smile2.Product.Contract.ProductContract;
import com.lafresh.smile2.Product.Presenter.ProductPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends BaseFragment implements ProductContract.View{
    public static final String TAG = ProductFragment.class.getSimpleName();

    private static ProductFragment fragment;
    private ProductContract.Presenter productPresenter;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ImageView img_go_top;
    private TabLayout tabLayout;

    private static final String STORE_ID = "store_id";
    private int store_id = 0;

    private String sort = "NEW";
    private String type_id ="";

    public static ProductFragment getInstance(int store_id) {
        if (fragment == null) {
            fragment = new ProductFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(STORE_ID, store_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProductFragment getInstance() {
        if (fragment == null) {
            fragment = new ProductFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        store_id = getArguments().getInt(STORE_ID, 0);
        type_id ="";
        initView(view);

        return view;

    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(true);
        tabLayout = view.findViewById(R.id.tab_layout);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        img_go_top = view.findViewById(R.id.img_go_top);
        img_go_top.bringToFront();
        img_go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
        productPresenter = new ProductPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        productListAdapter = new ProductListAdapter(productPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(productListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productPresenter.getTypeList(store_id);
    }

    @Override
    public void setProductTypeList(List<ProductType> productTypeList) {
        tabLayout.addTab(tabLayout.newTab().setText("全部").setTag(""));
        for(int i = 0 ; i < productTypeList.size() ; i++){
            tabLayout.addTab(tabLayout.newTab().setText(productTypeList.get(i).getType_name()).setTag(Integer.toString(productTypeList.get(i).getId())));
        }
        //init 第一個tab(全部) 的 selected font
        if(getActivity()!=null){
            TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
            Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_medium);
            title.setTypeface(typeface);
        }
        //
        productPresenter.getProductList(sort , Integer.toString(store_id) , "" , "");

        //上方分頁功能監聽觸發事件
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type_id = tab.getTag().toString();
                productPresenter.getProductList(sort , Integer.toString(store_id) , type_id , "");
                //改變Unselected tab font
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_medium);
                title.setTypeface(typeface);
                //
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //改變Unselected tab font
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_regular);
                title.setTypeface(typeface);
                //
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void goPageProductDetail(int product_id) {
        ((MainActivity) getActivity()).addFragment(ProductDetailFragment.getInstance(product_id));
    }

    @Override
    public void setProductList(List<Product> productList) {
        for(int i = 0 ; i < productList.size() ; i++){
            Log.e(TAG,productList.get(i).getProduct_name());
        }
        List<Product> productleft = new ArrayList<>();
        List<Product> productright = new ArrayList<>();
        for(int i = 0 ; i < productList.size() ; i++){
            if(i % 2 == 0){
                productleft.add(productList.get(i));
            }
            else{
                productright.add(productList.get(i));
            }
        }
        productListAdapter.setProduct(productleft ,productright);
        recyclerView.scrollToPosition(0);
    }

    //MainActivity呼叫用  排序 傳入後重新排序
    public void SortMode(String sSortMode){
        sort = sSortMode;
        Log.e(TAG,"SortMode:" + sSortMode + ",typeID:" + type_id);
        productPresenter.getProductList(sort , Integer.toString(store_id) , type_id , "");
    }
}



