package com.lafresh.smile2.Main.View;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.Contract.NewsContract;
import com.lafresh.smile2.Main.Presenter.MainIndexPresenter;
import com.lafresh.smile2.Main.Presenter.NewsPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.Repository.Model.MemberMessage;
import com.lafresh.smile2.SmileApplication;
import static com.lafresh.smile2.SmileApplication.DOMAIN;
import java.util.Objects;

public class NewsFragment extends BaseFragment implements NewsContract.View {
    public static final String TAG = NewsFragment.class.getSimpleName();

    private static NewsFragment fragment;
    private static final String NEWS_ID = "news_id";
    private TextView  tv_news_title ,tv_news_content;
    private ImageView imageView;
    private Carousel carousel;

    public static NewsFragment getInstance(Carousel carousel) {
        if (fragment == null) {
            fragment = new NewsFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Carousel.TAG, carousel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        if (getArguments() != null && getArguments().containsKey(Carousel.TAG)) {
            carousel = getArguments().getParcelable(Carousel.TAG);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
//        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.tab_news);
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        imageView = view.findViewById(R.id.imageView);
        tv_news_content = view.findViewById(R.id.tv_news_content);
        tv_news_title = view.findViewById(R.id.tv_news_title);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(getActivity()).load(DOMAIN+carousel.getPicture_url()).into(imageView);
        tv_news_content.setText(Html.fromHtml(carousel.getContent()));
        tv_news_title.setText(carousel.getTitle());
    }
}
