package com.lafresh.smile2.Pay.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PayDetail;

import java.util.ArrayList;

public class PayBusinessDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<PayDetail> data;
    private final int TYPE_NORMAL_ITEM = 10;
    private final int TYPE_EMPTY_ITEM = 11;
    public PayBusinessDetailAdapter(ArrayList<PayDetail> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==TYPE_NORMAL_ITEM){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pay_business_detail,viewGroup,false);
            return new PayBusinessViewHolder(view);
        }else{
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pay_business_detail_empty,viewGroup,false);
            return new PayBusinessEmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof PayBusinessViewHolder)
            ((PayBusinessViewHolder)viewHolder).bind(data.get(i));
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

    public class PayBusinessEmptyViewHolder extends RecyclerView.ViewHolder{

        public PayBusinessEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class PayBusinessViewHolder extends RecyclerView.ViewHolder{
        TextView name,date,price;
        public PayBusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_pay_business_detail_name);
            date = itemView.findViewById(R.id.tv_pay_business_detail_date);
            price = itemView.findViewById(R.id.tv_pay_business_detail_price);
        }

        public void bind(PayDetail model){
            name.setText(model.name);
            date.setText(model.date);
            price.setText(" "+model.price);
        }
    }
}
