package com.lafresh.smile2.Widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lafresh.smile2.R;

public class AppToolbar extends Toolbar {
    public static final String TAG = AppToolbar.class.getSimpleName();

    private TextView tvAppTitle, tvMailBadge , tvMessageBadge;
    private ImageButton btnBack, btnMail, btnMessage;
    private ImageView btnSort;
    private int iMailCount = 0;
    private int iMessageCount = 0;

    public AppToolbar(Context context) {
        super(context);
        initView(context, null);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_toolbar, this, true);
        setContentInsetsAbsolute(0, 0);

        tvAppTitle = findViewById(R.id.tv_app_title);
        tvMessageBadge = findViewById(R.id.tv_message_badge);
        tvMailBadge = findViewById(R.id.tv_mail_badge);
        btnBack = findViewById(R.id.btn_app_back);
        btnMail = findViewById(R.id.btn_mail);
        btnMessage = findViewById(R.id.btn_message);
        btnSort = findViewById(R.id.btn_sort);
    }

    public TextView getTvTitle() {
        return tvAppTitle;
    }

    public ImageButton getBtnBack() {
        return btnBack;
    }

    public ImageButton getBtnMail() {
        return btnMail;
    }

    public TextView getMailBadge() {
        return tvMailBadge;
    }

    public ImageButton getBtnMessage() {
        return btnMessage;
    }

    public TextView getMessageBadge() {
        return tvMessageBadge;
    }

    public ImageView getBtnSort() {
        return btnSort;
    }

    public int getiMailCount(){return iMailCount;}

    public int getiMessageCount(){return iMessageCount;}


    public void setMailBadgeCount(int mailBadgeCount) {
        iMailCount = mailBadgeCount;
        if(btnMail.getVisibility() == VISIBLE){
            tvMailBadge.setVisibility(mailBadgeCount > 0 ? VISIBLE : GONE);
            tvMailBadge.setText(Integer.toString(mailBadgeCount));
        }
        else{
            tvMailBadge.setVisibility(GONE);
        }
    }

    public void setMessageBadgeCount(int messageBadgeCount) {
        iMessageCount = messageBadgeCount;
        tvMessageBadge.setVisibility(messageBadgeCount > 0 ? VISIBLE : GONE);
        tvMessageBadge.setText(Integer.toString(messageBadgeCount));
    }
}
