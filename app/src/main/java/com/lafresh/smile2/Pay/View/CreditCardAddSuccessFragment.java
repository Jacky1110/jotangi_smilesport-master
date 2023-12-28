package com.lafresh.smile2.Pay.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.R;

public class CreditCardAddSuccessFragment extends BaseFragment implements View.OnClickListener{
    private static CreditCardAddSuccessFragment fragment;
    private TextView tv_pay_back;
    public static CreditCardAddSuccessFragment getInstance(){
        if(fragment == null){
            fragment = new CreditCardAddSuccessFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_creditcard_add_success,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ((MainActivity)getActivity()).setAppTitle(R.string.tab_pay);
        ((MainActivity)getActivity()).setBackButtonVisibility(true);
        ((MainActivity)getActivity()).setMessageButtonVisibility(false);
        ((MainActivity)getActivity()).setMailButtonVisibility(false);
        ((MainActivity)getActivity()).setSortButtonVisibility(false);
        tv_pay_back = view.findViewById(R.id.tv_pay_back);
        tv_pay_back.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).hideBottomNavigation();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).showBottomNavigation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_pay_back:
                ((MainActivity)getActivity()).changeTabFragment(PayFragment.getInstance());
                break;
        }
    }
}
