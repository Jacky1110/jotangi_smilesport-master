package com.lafresh.smile2.Pay.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Pay.View.PayBindCardFragment;
import com.lafresh.smile2.Pay.View.PayCardEditFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PayCard;

import java.util.ArrayList;

public class PayCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<PayCard> data;
    private final int TYPE_NORMAL_ITEM = 10;
    private final int TYPE_EMPTY_ITEM = 11;
    private final int TYPE_LOADING_ITEM = 12;
    private Context context;
    public PayCardAdapter(Context context,ArrayList<PayCard> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==TYPE_EMPTY_ITEM){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pay_card_empty,viewGroup,false);
            return new PayCardEmptyViewHolder(view);
        }else{
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pay_card,viewGroup,false);
            return new PayCardViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof PayCardViewHolder)
            ((PayCardViewHolder)viewHolder).bind(data.get(i),i);
    }

    @Override
    public int getItemCount() {
        if(data.size()==0) return 1;
        else return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(data.size()==0) return TYPE_EMPTY_ITEM;
        else return TYPE_NORMAL_ITEM;
    }

    public class PayCardEmptyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_pay_add_card;
        public PayCardEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pay_add_card = itemView.findViewById(R.id.tv_pay_add_card);
            tv_pay_add_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)context).addFragment(PayBindCardFragment.getInstance());
                }
            });
        }
    }

    public class PayCardViewHolder extends RecyclerView.ViewHolder{
        ImageView bg_pay_card_back,img_card_add_success,img_card_edit;
        TextView tv_card_name,tv_card_number;
        public PayCardViewHolder(@NonNull View itemView) {
            super(itemView);
            bg_pay_card_back = itemView.findViewById(R.id.bg_pay_card_back);
            img_card_add_success = itemView.findViewById(R.id.img_card_add_success);
            img_card_edit = itemView.findViewById(R.id.img_card_edit);
            tv_card_name = itemView.findViewById(R.id.tv_card_name);
            tv_card_number = itemView.findViewById(R.id.tv_card_number);
        }

        public void bind(final PayCard model, final int position){
            if(model.defaultCard) {
                bg_pay_card_back.setBackgroundResource(R.drawable.bg_normal);
                img_card_add_success.setVisibility(View.VISIBLE);
                img_card_edit.setImageResource(R.drawable.ic_edit_white_icon);
                tv_card_name.setTextAppearance(R.style.fontBoldTw_White);
                tv_card_number.setTextAppearance(R.style.fontBoldTw_White);
            }
            else {
                bg_pay_card_back.setBackgroundResource(R.drawable.bg_grey_card);
                img_card_add_success.setVisibility(View.INVISIBLE);
                img_card_edit.setImageResource(R.drawable.ic_edit_gray_icon);
                tv_card_name.setTextAppearance(R.style.fontBoldTw_Grey);
                tv_card_number.setTextAppearance(R.style.fontBoldTw_Grey);
            }
            tv_card_name.setText(model.name);
            String numberFormat = "****   ****   ****   "+model.number.substring(model.number.lastIndexOf("*")+1);
            tv_card_number.setText(numberFormat);
            img_card_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PayCardEditFragment payCardEditFragment = PayCardEditFragment.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",model.name);
                    bundle.putString("number",model.number);
                    bundle.putBoolean("defaultCard",model.defaultCard);
                    bundle.putString("cardType",model.cardType);
                    bundle.putString("token",model.token);
                    bundle.putInt("position",position);
                    payCardEditFragment.setArguments(bundle);
                    ((MainActivity)context).addFragment(payCardEditFragment);
                }
            });
        }
    }
}
