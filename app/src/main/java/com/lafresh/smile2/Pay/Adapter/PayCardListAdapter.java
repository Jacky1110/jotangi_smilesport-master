package com.lafresh.smile2.Pay.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PayCard;

import java.util.List;

public class PayCardListAdapter extends ArrayAdapter<PayCard> {
    public PayCardListAdapter(@NonNull Context context, @NonNull List<PayCard> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_card_list,parent,false);
        TextView tv_card_name = convertView.findViewById(R.id.tv_card_name);
        TextView tv_card_number = convertView.findViewById(R.id.tv_card_number);
        ImageView img_card = convertView.findViewById(R.id.img_card);
        PayCard model = getItem(position);
        tv_card_name.setText(model.name);
        tv_card_number.setText(model.number.substring(model.number.lastIndexOf("*")));
        if(model.cardType.equals("1"))
            img_card.setImageResource(R.drawable.ic_visa_icon);
        else if(model.cardType.equals("2"))
            img_card.setImageResource(R.drawable.ic_mastercard_icon);
        else if(model.cardType.equals("3"))
            img_card.setImageResource(R.drawable.ic_jcb_icon);
        else
            img_card.setImageResource(R.drawable.ic_unionpay_icon);
        return convertView;
    }
}
