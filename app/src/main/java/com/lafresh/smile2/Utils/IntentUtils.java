package com.lafresh.smile2.Utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.StringRes;
import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.Base.WebViewActivity;
import com.lafresh.smile2.Init.NotificationControlActivity;
import com.lafresh.smile2.Login.VIew.ForgetPasswordActivity;
import com.lafresh.smile2.Login.VIew.LoginActivity;
import com.lafresh.smile2.Login.VIew.RegisterActivity;
import com.lafresh.smile2.Login.VIew.TermsOfUseActivity;
import com.lafresh.smile2.Login.VIew.VerifyCodeActivity;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.R;

import static com.lafresh.smile2.Constant.Constant.PHONE;
import static com.lafresh.smile2.Constant.Constant.TITLE;
import static com.lafresh.smile2.Constant.Constant.URL;

public class IntentUtils {
    public static void intentToNotificationControl(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, NotificationControlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        baseActivity.startActivity(intent);
    }

    public static void intentToSystemPermissionSetting(BaseActivity baseActivity) {
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", baseActivity.getPackageName());
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", baseActivity.getPackageName());
            intent.putExtra("app_uid", baseActivity.getApplicationInfo().uid);
        } else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + baseActivity.getPackageName()));
        }

        baseActivity.startActivity(intent);
    }

    public static void intentToLogin(BaseActivity baseActivity, int requestCode) {
        Intent intent = new Intent(baseActivity, LoginActivity.class);
        baseActivity.startActivityForResult(intent, requestCode);
    }

    public static void intentToForgetPassword(BaseActivity baseActivity, String cellphone) {
        Intent intent = new Intent(baseActivity, ForgetPasswordActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(PHONE, cellphone);
        intent.putExtras(bundle);
        baseActivity.startActivity(intent);
    }

    public static void intentToRegister(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, RegisterActivity.class);
        baseActivity.startActivity(intent);
    }

    public static void intentToTermsOfUse(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, TermsOfUseActivity.class);
        baseActivity.startActivity(intent);
    }

    public static void intentToVerifyCode(BaseActivity baseActivity, boolean needResendSms) {
        Intent intent = new Intent(baseActivity, VerifyCodeActivity.class);

        Bundle bundle = new Bundle();
        bundle.putBoolean(VerifyCodeActivity.TAG, needResendSms);
        intent.putExtras(bundle);
        baseActivity.startActivity(intent);
    }

    public static void intentToMain(BaseActivity baseActivity, boolean needClearStack) {
        Intent intent = new Intent(baseActivity, MainActivity.class);
        if (needClearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(MainActivity.TAG, true);
        }
        baseActivity.startActivity(intent);
    }

    public static void intentToWebView(BaseActivity baseActivity, @StringRes int stringRes, String url) {
        Intent intent = new Intent(baseActivity, WebViewActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt(TITLE, stringRes);
        bundle.putString(URL, url);
        intent.putExtras(bundle);

        baseActivity.startActivity(intent);
    }

    public static void intentToGoogleMap(BaseActivity baseActivity, String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        baseActivity.startActivity(mapIntent);
    }

    public static void intentToShare(BaseActivity baseActivity, String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        baseActivity.startActivity(Intent.createChooser(sharingIntent, ""));
    }

    public static void intentToEmail(BaseActivity baseActivity, String text) {
        String[] TO = {text};
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        baseActivity.startActivity(Intent.createChooser(sharingIntent, ""));
    }

    public static void intentToOutWeb(BaseActivity baseActivity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        baseActivity.startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    public static void intentToCall(BaseActivity baseActivity, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        baseActivity.startActivity(intent);
    }
}
