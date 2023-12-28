package com.lafresh.smile2.Login.VIew;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Login.LoginContract;
import com.lafresh.smile2.Login.Presenter.LoginPresenter;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.MemberCenter.FaqContract;
import com.lafresh.smile2.MemberCenter.Presenter.FaqPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Faq;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.IntentUtils;
import com.lafresh.smile2.Utils.ViewUtils;

public class TermsOfUseActivity extends BaseActivity implements FaqContract.View{
    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView tv_faq_data;
    private FaqContract.Presenter presenter;
    private ConstraintLayout layoutContent;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        presenter = new FaqPresenter(this, getRepositoryManager(this));
        presenter.getFaqData("1");

        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.privacy_policy);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);

        tv_faq_data = findViewById(R.id.tv_faq_data);
        layoutContent = findViewById(R.id.layout_content);
    }

    @Override
    public void setFaqData(Faq faq) {
        tv_faq_data.setText(Html.fromHtml(faq.getAnswer()));

        Resources res = this.getResources();
        drawable = res.getDrawable(R.drawable.bg_shadow_corner);
        layoutContent.setBackground(drawable);
    }
}