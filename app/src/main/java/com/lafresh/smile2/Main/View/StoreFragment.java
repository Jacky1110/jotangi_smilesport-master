package com.lafresh.smile2.Main.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.graphics.Color;

import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.Adapter.StoreListAdapter;
import com.lafresh.smile2.Main.Presenter.StoreListPresenter;
import com.lafresh.smile2.Main.Contract.StoreListContract;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Store;
import com.lafresh.smile2.Utils.IntentUtils;

import java.util.List;

import static com.lafresh.smile2.Main.Presenter.StoreListPresenter.AREA_CHIAYI;
import static com.lafresh.smile2.Main.Presenter.StoreListPresenter.AREA_TAICHUNG;
import static com.lafresh.smile2.Main.Presenter.StoreListPresenter.TYPE_DEPARTMENT;
import static com.lafresh.smile2.Main.Presenter.StoreListPresenter.TYPE_SELL_CENTER;
import static com.lafresh.smile2.Main.Presenter.StoreListPresenter.TYPE_STORE;

public class StoreFragment extends BaseFragment implements View.OnClickListener, StoreListContract.View {
    public static final String TAG = StoreFragment.class.getSimpleName();

    private static StoreFragment fragment;
    private TextView btnChiayi, btnTaichung;
    private RecyclerView recyclerView;
    private Group storeSelectGroup;
    private CheckBox cbStore, cbDepartment, cbSellCenter;

    private StoreListAdapter storeListAdapter;
    private StoreListContract.Presenter storeListPresenter;

    public static StoreFragment getInstance() {
        if (fragment == null) {
            fragment = new StoreFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chiayi:
                storeListPresenter.setArea(AREA_CHIAYI);
                break;
            case R.id.btn_taichung:
                storeListPresenter.setArea(AREA_TAICHUNG);
                break;
            case R.id.cb_store:
                storeListPresenter.setType(TYPE_STORE, cbStore.isChecked());
                break;
            case R.id.cb_department:
                storeListPresenter.setType(TYPE_DEPARTMENT, cbDepartment.isChecked());
                break;
            case R.id.cb_sell_center:
                storeListPresenter.setType(TYPE_SELL_CENTER, cbSellCenter.isChecked());
                break;
        }
    }

    @Override
    public void changeToNewAreaType(int area) {
        Log.e(TAG,"changeToNewAreaType : " + area );
        btnChiayi.setBackgroundResource(area == AREA_CHIAYI ? R.drawable.bg_orange_gradient_bar : R.drawable.bg_grey_bar);
        btnChiayi.setTextColor(area == AREA_CHIAYI ? Color.parseColor("#ffffff") : Color.parseColor("#ff9000"));
        btnTaichung.setBackgroundResource(area == AREA_TAICHUNG ? R.drawable.bg_orange_gradient_bar : R.drawable.bg_grey_bar);
        btnTaichung.setTextColor(area == AREA_TAICHUNG ? Color.parseColor("#ffffff"):Color.parseColor("#ff9000"));

        setAreaSelectVisible(true);
        cbStore.setChecked(true);
        cbDepartment.setChecked(true);
        cbSellCenter.setChecked(true);
    }

    @Override
    public void setStoreList(List<Store> stores) {
        for (Store store : stores){
            Log.e(TAG,store.getName());
        }
        storeListAdapter.setData(stores);
    }

    @Override
    public void setAreaSelectVisible(boolean isVisible) {
        storeSelectGroup.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean getAreaSelectVisible() {
        return storeSelectGroup.getVisibility() == View.VISIBLE;
    }

    @Override
    public void intentToGoogleMap(String address) {
        IntentUtils.intentToGoogleMap((BaseActivity) getActivity(), address);
    }

    @Override
    public void intentToPhoneCall(String phone) {
        IntentUtils.intentToCall((BaseActivity) getActivity(), phone);
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_location);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        btnChiayi = view.findViewById(R.id.btn_chiayi);
        btnChiayi.setOnClickListener(this);

        btnTaichung = view.findViewById(R.id.btn_taichung);
        btnTaichung.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.product_recycler_view);

        storeSelectGroup = view.findViewById(R.id.group_store_select);

        cbStore = view.findViewById(R.id.cb_store);
        cbStore.setOnClickListener(this);
        cbStore.setChecked(true);

        cbDepartment = view.findViewById(R.id.cb_department);
        cbDepartment.setOnClickListener(this);
        cbDepartment.setChecked(true);

        cbSellCenter = view.findViewById(R.id.cb_sell_center);
        cbSellCenter.setOnClickListener(this);
        cbSellCenter.setChecked(true);

        storeListAdapter = new StoreListAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(storeListAdapter);

        storeListPresenter = new StoreListPresenter(this, getRepositoryManager(getContext()));
    }
}
