package com.lafresh.smile2.MemberCenter.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lafresh.smile2.Base.BaseAdapter;
import com.lafresh.smile2.MemberCenter.View.MessageHolder;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends BaseAdapter<MessageHolder> {
    public static final String TAG = MessageListAdapter.class.getSimpleName();

    private List<Message> messages = new ArrayList<>();

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder messageHolder, int position) {
        messageHolder.setMessage(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
}
