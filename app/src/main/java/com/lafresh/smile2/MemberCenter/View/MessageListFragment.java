package com.lafresh.smile2.MemberCenter.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.MemberCenter.Adapter.MessageListAdapter;
import com.lafresh.smile2.MemberCenter.MessageListContract;
import com.lafresh.smile2.MemberCenter.Presenter.MessageListPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Message;

import java.util.List;

public class MessageListFragment extends BaseFragment implements View.OnClickListener, MessageListContract.View {
    public static final String TAG = MessageListFragment.class.getSimpleName();

    private static MessageListFragment fragment;
    private RecyclerView recyclerView;
    private EditText etMessage;
    private ImageButton btnSend;

    private MessageListAdapter messageListAdapter;
    private MessageListContract.Presenter messagePresenter;

    public static MessageListFragment getInstance() {
        if (fragment == null) {
            fragment = new MessageListFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_message_list, container, false);
        initView(view);

        messagePresenter = new MessageListPresenter(this, getRepositoryManager(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideBottomNavigation();
        messagePresenter.getMessageList();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).showBottomNavigation();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.message_list);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(false);
        ((MainActivity) getActivity()).setMailButtonVisibility(false);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.product_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        messageListAdapter = new MessageListAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(messageListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String msg = etMessage.getText().toString();
                if (!msg.isEmpty()) {
                    messagePresenter.sendMessage(msg);
                    etMessage.setText("");
                }
                break;
        }
    }

    @Override
    public void setMessageList(List<Message> list) {
        messageListAdapter.setMessages(list);
        recyclerView.scrollToPosition(list.size() - 1);
        messagePresenter.updateReadMessageApi();
    }
}
