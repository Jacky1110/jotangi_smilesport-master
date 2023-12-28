package com.lafresh.smile2.Pay.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Pay.Adapter.PayCardAdapter;
import com.lafresh.smile2.Pay.Contract.PayContract;
import com.lafresh.smile2.Pay.Presenter.PayCodePresenter;
import com.lafresh.smile2.Pay.Presenter.PayPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PayCard;
import com.lafresh.smile2.Repository.Model.PayCardBarcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PayFragment extends BaseFragment implements View.OnClickListener, PayContract.View {
    private static PayFragment fragment;
    private LinearLayout linear_barcode,linear_add_card,linear_business_detail,linear_change_pwd;
    private RecyclerView rv_pay_card;
    private PayCardAdapter payCardAdapter;
    public static ArrayList<PayCard> cardData = new ArrayList<>();
    public static Boolean setDefault = false;
    private PayPresenter payPresenter;
    public static PayFragment getInstance(){
        if(fragment == null){
            fragment = new PayFragment();
        }
        return fragment;
    }

    public static ArrayList<PayCard> getCardData(){
        return cardData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ((MainActivity)getActivity()).setAppTitle(R.string.tab_pay);
        ((MainActivity)getActivity()).setBackButtonVisibility(true);
        ((MainActivity)getActivity()).setMessageButtonVisibility(true);
        ((MainActivity)getActivity()).setMailButtonVisibility(true);
        ((MainActivity)getActivity()).setSortButtonVisibility(false);
        linear_barcode = view.findViewById(R.id.linear_barcode);
        linear_add_card = view.findViewById(R.id.linear_add_card);
        linear_add_card.setOnClickListener(this);
        linear_business_detail = view.findViewById(R.id.linear_business_detail);
        linear_change_pwd = view.findViewById(R.id.linear_change_pwd);
        linear_change_pwd.setOnClickListener(this);
        linear_business_detail.setOnClickListener(this);
        rv_pay_card = view.findViewById(R.id.rv_pay_card);
        rv_pay_card.setLayoutManager(new LinearLayoutManager(getContext()));
        payCardAdapter = new PayCardAdapter(getContext(),cardData);
        rv_pay_card.setAdapter(payCardAdapter);
        linear_barcode.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(rv_pay_card!=null && setDefault) {
            rv_pay_card.scrollToPosition(0);
            setDefault = false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        payPresenter = new PayPresenter(this,getRepositoryManager(getContext()));
        payPresenter.getCardPageList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linear_business_detail:
                ((MainActivity)getActivity()).addFragment(PayBusinessDetailFragment.getInstance());
                break;
            case R.id.linear_change_pwd:
                ((MainActivity)getActivity()).addFragment(PayCodeChangeFragment.getInstance());
                break;
            case R.id.linear_add_card:
                ((MainActivity)getActivity()).addFragment(PayBindCardFragment.getInstance());
                break;
            case R.id.linear_barcode:
                if(cardData.size()==0){
                    new AlertDialog.Builder(fragment.getActivity())
                            .setTitle(R.string.dialog_hint)
                            .setMessage("請先新增一張信用卡")
                            .setPositiveButton(android.R.string.ok, null).show();
                }else
                    ((MainActivity)getActivity()).addFragment(PayCodeBarcodeFragment.getInstance()); //data position
                break;
        }
    }

    @Override
    public void setData(Object list) {
        if(list!=null) {
            cardData.clear();
            cardData.addAll((List<PayCard>) list);
            Boolean status = false;
            for(int i=0;i<cardData.size();i++){
                PayCard model = cardData.get(i);
                if(model.number.equals(payPresenter.getDefaultCardNumber())){
                    model.defaultCard = true;
                    status = true;
                }
            }
            if(!status && cardData.size()>0)
                cardData.get(0).defaultCard = true;
            Collections.sort(cardData, new Comparator<PayCard>() {
                @Override
                public int compare(PayCard payCard, PayCard t1) {
                    return  (payCard.defaultCard ? -1 : 1);
                }
            });
            payCardAdapter.notifyDataSetChanged();
        }else{
            Log.d("ddd","list null");
            try {
                Thread.sleep(500); //api不能打太快 刪除完睡一下再重撈
                payPresenter.getCardPageList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
