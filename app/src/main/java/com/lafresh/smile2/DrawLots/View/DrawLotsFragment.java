package com.lafresh.smile2.DrawLots.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.DrawLots.Adapter.DrawLotsListAdapter;
import com.lafresh.smile2.DrawLots.Contract.DrawLotsContract;
import com.lafresh.smile2.DrawLots.Presenter.DrawLotsPresenter;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Product.Adapter.ProductListAdapter;
import com.lafresh.smile2.Product.Presenter.ProductPresenter;
import com.lafresh.smile2.Product.View.ProductDetailFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class DrawLotsFragment extends BaseFragment implements DrawLotsContract.View{
    public static final String TAG = DrawLotsFragment.class.getSimpleName();

    private static DrawLotsFragment fragment;
    private DrawLotsContract.Presenter drawlotsPresenter;
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayoutEmpty;
    private DrawLotsListAdapter drawlotsListAdapter;


    public static DrawLotsFragment getInstance() {
        if (fragment == null) {
            fragment = new DrawLotsFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawlots, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_lot);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        recyclerView = view.findViewById(R.id.dwawlots_recycler_view);
        constraintLayoutEmpty = view.findViewById(R.id.layout_empty);
        drawlotsPresenter = new DrawLotsPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        drawlotsListAdapter = new DrawLotsListAdapter(drawlotsPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(drawlotsListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drawlotsPresenter.getLotList();
    }


    @Override
    public void setLotList(List<DrawLots> drawLotsList) {
        if (drawLotsList.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            constraintLayoutEmpty.setVisibility(View.GONE);
            drawlotsListAdapter.setDrawlotslist(drawLotsList);
            recyclerView.scrollToPosition(0);
        }else{
            recyclerView.setVisibility(View.GONE);
            constraintLayoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goLotDetail(int lot_id) {
        ((MainActivity) getActivity()).addFragment(LotDetailFragment.getInstance(lot_id));
    }
}



