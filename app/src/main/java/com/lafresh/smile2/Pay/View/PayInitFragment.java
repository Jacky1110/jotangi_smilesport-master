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

public class PayInitFragment extends BaseFragment implements View.OnClickListener {
    private static PayInitFragment fragment;
    private TextView tv_pay_bind;
    public static PayInitFragment getInstance(){
        if(fragment == null){
            fragment = new PayInitFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_init,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ((MainActivity)getActivity()).setAppTitle(R.string.tab_pay);
        ((MainActivity)getActivity()).setBackButtonVisibility(true);
        ((MainActivity)getActivity()).setMessageButtonVisibility(true);
        ((MainActivity)getActivity()).setMailButtonVisibility(true);
        ((MainActivity)getActivity()).setSortButtonVisibility(false);
        tv_pay_bind = view.findViewById(R.id.tv_pay_bind);
        tv_pay_bind.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_pay_bind:
                ((MainActivity)getActivity()).addFragment(PayPrivacyFragment.getInstance());
                break;
        }
    }
}
