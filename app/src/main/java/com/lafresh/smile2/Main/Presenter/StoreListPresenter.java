package com.lafresh.smile2.Main.Presenter;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.Main.Contract.StoreListContract;
import com.lafresh.smile2.Repository.Model.Store;
import com.lafresh.smile2.Repository.RepositoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreListPresenter extends BasePresenter<StoreListContract.View> implements StoreListContract.Presenter {
    public static final String TAG = StoreListPresenter.class.getSimpleName();

    public static final int AREA_CHIAYI = 1;
    public static final int AREA_TAICHUNG = 2;

    public static final int TYPE_STORE = 1;
    public static final int TYPE_DEPARTMENT = 2;
    public static final int TYPE_SELL_CENTER = 3;

    private int area;
    private Map<Integer, Boolean> typeMap;

    public StoreListPresenter(StoreListContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
        typeMap = new HashMap<>();
        changeArea(AREA_CHIAYI);
    }

    @Override
    public void setArea(int area) {
        if (this.area == area) {
            view.setAreaSelectVisible(!view.getAreaSelectVisible());
        } else {
            changeArea(area);
        }
    }

    @Override
    public void setType(int type, boolean checked) {
        typeMap.put(type, checked);
        getStoreList();
    }

    private void changeArea(int area) {
        this.area = area;

        typeMap.put(TYPE_STORE, true);
        typeMap.put(TYPE_DEPARTMENT, true);
        typeMap.put(TYPE_SELL_CENTER, true);

        view.changeToNewAreaType(area);
        getStoreList();
    }

    private void getStoreList() {
        List<Integer> list = new ArrayList<>();
        for (Integer type : typeMap.keySet()) {
            if (typeMap.get(type)) {
                list.add(type);
            }
        }

        if (list.size() > 0) {
            repositoryManager.callGetStoresApi(area, list, new BaseContract.ValueCallback<List<Store>>() {
                @Override
                public void onValueCallback(int task, List<Store> type) {
                    view.setStoreList(type);
                }
            });
        } else {
            view.setStoreList(new ArrayList<Store>());
        }
    }
}
