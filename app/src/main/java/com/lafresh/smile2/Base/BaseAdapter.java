package com.lafresh.smile2.Base;

import android.support.v7.widget.RecyclerView;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
