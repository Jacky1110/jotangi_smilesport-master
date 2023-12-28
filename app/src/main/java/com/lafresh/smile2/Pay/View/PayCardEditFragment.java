package com.lafresh.smile2.Pay.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Pay.Contract.PayContract;
import com.lafresh.smile2.Pay.Presenter.PayPresenter;
import com.lafresh.smile2.R;

public class PayCardEditFragment extends BaseFragment implements PayContract.View {
    private static PayCardEditFragment fragment;
    private String name,number,cardType,token;
    private boolean defaultCard;
    private int position;
    private TextView tv_pay_number,tv_default_card,tv_pay_card_default,tv_card_name,tv_pay_card_delete;
    private ImageView img_card;
    private PayPresenter payPresenter;
    public static PayCardEditFragment getInstance(){
        if(fragment == null){
            fragment = new PayCardEditFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_card_edit,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        payPresenter = new PayPresenter(this,getRepositoryManager(getContext()));
        ((MainActivity)getActivity()).setAppTitle(R.string.tab_pay_card_edit);
        ((MainActivity)getActivity()).setBackButtonVisibility(true);
        ((MainActivity)getActivity()).setMessageButtonVisibility(true);
        ((MainActivity)getActivity()).setMailButtonVisibility(true);
        ((MainActivity)getActivity()).setSortButtonVisibility(false);
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        number = bundle.getString("number");
        cardType = bundle.getString("cardType");
        token = bundle.getString("token");
        defaultCard = bundle.getBoolean("defaultCard");
        position = bundle.getInt("position");
        tv_pay_number = view.findViewById(R.id.tv_pay_number);
        tv_default_card = view.findViewById(R.id.tv_default_card);
        tv_pay_card_default = view.findViewById(R.id.tv_pay_card_default);
        tv_card_name = view.findViewById(R.id.tv_card_name);
        tv_card_name.setText(name);
        tv_pay_number.setText(number);
        if(defaultCard) {
            tv_default_card.setText("預設支付卡片");
            tv_pay_card_default.setVisibility(View.INVISIBLE);
        }
        else {
            tv_default_card.setText("非預設支付卡片");
            tv_pay_card_default.setVisibility(View.VISIBLE);
        }
        tv_pay_card_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPresenter.saveDefaultCardNumber(number);
                PayFragment.setDefault = true;
                tv_default_card.setText("預設支付卡片");
                tv_pay_card_default.setVisibility(View.INVISIBLE);
                new AlertDialog.Builder(fragment.getActivity())
                        .setTitle("操作成功")
                        .setMessage("已變更為預設支付卡片")
                        .setPositiveButton(android.R.string.ok,null)
                        .show();
            }
        });
        tv_pay_card_delete = view.findViewById(R.id.tv_pay_card_delete);
        tv_pay_card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(fragment.getActivity())
                        .setTitle("刪除卡片?")
                        .setMessage("請確認是否要刪除卡片")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Log.d("ddd",position+"");
                                if(payPresenter.getDefaultCardNumber().equals(number)){
                                    payPresenter.saveDefaultCardNumber("");
                                }
                                payPresenter.deleteBindingCard(token);
                            }
                        })
                        .setNegativeButton(android.R.string.no,null)
                        .show();
            }
        });
        img_card = view.findViewById(R.id.img_card);
        if(cardType.equals("1"))
            img_card.setImageResource(R.drawable.ic_visa_icon);
        else if(cardType.equals("2"))
            img_card.setImageResource(R.drawable.ic_mastercard_icon);
        else if(cardType.equals("3"))
            img_card.setImageResource(R.drawable.ic_jcb_icon);
        else
            img_card.setImageResource(R.drawable.ic_unionpay_icon);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setData(Object list) {
        fragment.getActivity().onBackPressed();
    }
}
