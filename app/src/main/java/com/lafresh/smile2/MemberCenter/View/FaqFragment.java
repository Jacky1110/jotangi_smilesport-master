package com.lafresh.smile2.MemberCenter.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.MemberCenter.AboutContract;
import com.lafresh.smile2.MemberCenter.FaqContract;
import com.lafresh.smile2.MemberCenter.Presenter.AboutPresenter;
import com.lafresh.smile2.MemberCenter.Presenter.FaqPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.About;
import com.lafresh.smile2.Repository.Model.Faq;

import java.util.List;

public class FaqFragment extends BaseFragment implements FaqContract.View{
    public static final String TAG = FaqFragment.class.getSimpleName();

    private static FaqFragment fragment;
    private FaqContract.Presenter presenter;
    private String faq_id;
    private static final String FAQ_ID = "faq_id";

    private TextView tv_faq_data;

    public static FaqFragment getInstance(String faq_id) {
        if (fragment == null) {
            fragment = new FaqFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(FAQ_ID, faq_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        if (getArguments() != null) {
            faq_id = getArguments().getString(FAQ_ID, "");
            initView(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FaqPresenter(this, getRepositoryManager(getContext()));
        presenter.getFaqData(faq_id);
    }

    private void initView(View view) {
        switch (faq_id){
            case "1":
                ((MainActivity) getActivity()).setAppTitle(R.string.privacy_policy);
                break;
            case "2":
                ((MainActivity) getActivity()).setAppTitle(R.string.terms_of_use);
                break;
        }

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);

        tv_faq_data = view.findViewById(R.id.tv_faq_data);
    }

    @Override
    public void setFaqData(Faq faq) {
        tv_faq_data.setText(Html.fromHtml(faq.getAnswer()));
    }
}
