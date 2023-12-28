package com.lafresh.smile2.DrawLots.View;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lafresh.smile2.Product.View.ProductFragment;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.SmileApplication;

import java.text.DecimalFormat;

public class DrawLotsHolder extends RecyclerView.ViewHolder {
    public static final String TAG = DrawLotsHolder.class.getSimpleName();

    public TextView tv_vip , tv_title , tv_location_name , tv_end_date;
    public ImageView img_lot;
    public ConstraintLayout layout_lot;


    public DrawLotsHolder(@NonNull View itemView) {
        super(itemView);
        tv_vip = itemView.findViewById(R.id.tv_vip);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_location_name = itemView.findViewById(R.id.tv_location_name);
        tv_end_date = itemView.findViewById(R.id.tv_end_date);

        img_lot = itemView.findViewById(R.id.img_lot);

        layout_lot = itemView.findViewById(R.id.layout_lot);

    }
    public void setLot(DrawLots drawLots){
        Log.e(TAG , drawLots.getTitle());
        tv_title.setText(drawLots.getTitle());
        tv_end_date.setText("報名截止："+drawLots.getEnd_date());
        tv_location_name.setText(drawLots.getLocation_name());
        String isVip = drawLots.getMember_group_required();
        if(isVip!=null && isVip.equals("Y"))
            tv_vip.setVisibility(View.VISIBLE);

        Log.e(TAG, SmileApplication.DOMAIN + drawLots.getLot_picture_url());
        Glide.with(DrawLotsFragment.getInstance()).load(SmileApplication.DOMAIN + drawLots.getLot_picture_url()).into(img_lot);
        Log.e(TAG,drawLots.getLot_picture_url());

        layout_lot.setTag(R.id.layout_lot,drawLots.getId());
    }
}
