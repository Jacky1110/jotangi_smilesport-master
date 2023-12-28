package com.lafresh.smile2.MemberCenter.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lafresh.smile2.MemberCenter.Adapter.PointRecordAdapter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PointHistory;
import com.lafresh.smile2.Repository.Model.PointLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PointRecordHolder extends RecyclerView.ViewHolder {
    public static final String TAG = PointRecordHolder.class.getSimpleName();
    public TextView tvDate, tvPoint;
    private Context context;

    public PointRecordHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        tvDate = itemView.findViewById(R.id.tv_date);
        tvPoint = itemView.findViewById(R.id.tv_point);
    }

    public void setPointLog(PointHistory pointHistory) {
        tvPoint.setText(String.format(context.getString(R.string.point_add), pointHistory.bonus_points));
        tvDate.setText(pointHistory.modified_date+" " + pointHistory.remark);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
//        try {
//            Date date = format.parse(pointHistory.modified_date);
//            tvDate.setText(transFormat.format(date) + " " + pointHistory.modified_date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
}
