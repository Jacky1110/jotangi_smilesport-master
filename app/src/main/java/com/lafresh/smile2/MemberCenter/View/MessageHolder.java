package com.lafresh.smile2.MemberCenter.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageHolder extends RecyclerView.ViewHolder {
    public static final String TAG = MessageHolder.class.getSimpleName();

    public TextView tvUserName, tvMessage, tvMessageTime ,tvStoreName;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        tvStoreName = itemView.findViewById(R.id.tv_store_name);
        tvUserName = itemView.findViewById(R.id.tv_user_name);
        tvMessage = itemView.findViewById(R.id.tv_message);
        tvMessageTime = itemView.findViewById(R.id.tv_message_time);
    }

    public void setMessage(Message message) {
            tvUserName.setText(message.name);

            tvMessage.setText(message.content);
            tvMessageTime.setText(getFormatTime(message.date));
            tvUserName.setVisibility((message.is_reply.equals("N")) ? View.VISIBLE : View.INVISIBLE);
            tvStoreName.setVisibility((message.is_reply.equals("N")) ? View.INVISIBLE : View.VISIBLE);
//        groupResponse.setVisibility((message.response == null || message.response.isEmpty()) ? View.GONE : View.VISIBLE);
    }

    private String getFormatTime(String itemDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = format.parse(itemDate);
            return transFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
