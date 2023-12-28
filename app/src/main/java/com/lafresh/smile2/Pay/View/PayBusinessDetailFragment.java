package com.lafresh.smile2.Pay.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Pay.Adapter.PayBusinessDetailAdapter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PayDetail;

import java.util.ArrayList;

public class PayBusinessDetailFragment extends BaseFragment {
    private static PayBusinessDetailFragment fragment;
    private RecyclerView rv_pay_business;
    private ArrayList<PayDetail> data = new ArrayList<>();
    public static PayBusinessDetailFragment getInstance(){
        if(fragment == null){
            fragment = new PayBusinessDetailFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_business_detail,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        data.clear();
        ((MainActivity)getActivity()).setAppTitle(R.string.tab_pay_business);
        ((MainActivity)getActivity()).setBackButtonVisibility(true);
        ((MainActivity)getActivity()).setMessageButtonVisibility(true);
        ((MainActivity)getActivity()).setMailButtonVisibility(true);
        ((MainActivity)getActivity()).setSortButtonVisibility(false);
        rv_pay_business = view.findViewById(R.id.rv_pay_business);
        rv_pay_business.setLayoutManager(new LinearLayoutManager(getContext()));
        for(int i=0;i<5;i++){
            PayDetail model = new PayDetail();
            model.name = "全家便利商店123";
            model.date = "2020.08.10 15:20:25";
            model.price = 12;
            data.add(model);
        }
        rv_pay_business.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        rv_pay_business.setAdapter(new PayBusinessDetailAdapter(data));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

