package com.lafresh.smile2.DrawLots.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseAdapter;
import com.lafresh.smile2.DrawLots.Contract.DrawLotsContract;
import com.lafresh.smile2.DrawLots.View.DrawLotsHolder;
import com.lafresh.smile2.Product.Contract.ProductContract;
import com.lafresh.smile2.Product.View.ProductHolder;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class DrawLotsListAdapter extends BaseAdapter<DrawLotsHolder> implements View.OnClickListener{
    public static final String TAG = DrawLotsListAdapter.class.getSimpleName();
    private DrawLotsContract.Presenter presenter;
    private DrawLotsHolder drawLotsHolder;

    private List<DrawLots> drawlotslist = new ArrayList<>();

    public DrawLotsListAdapter(DrawLotsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DrawLotsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewDrawLots = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drawlots, viewGroup, false);
        return new DrawLotsHolder(viewDrawLots);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawLotsHolder drawLotsHolder, final int position) {
        drawLotsHolder.setLot(drawlotslist.get(position));

        drawLotsHolder.layout_lot.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return drawlotslist.size();
    }


    public void setDrawlotslist(List<DrawLots> drawlotslist) {
        this.drawlotslist = drawlotslist;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_lot:
                presenter.getLotDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
                break;
        }
    }
}