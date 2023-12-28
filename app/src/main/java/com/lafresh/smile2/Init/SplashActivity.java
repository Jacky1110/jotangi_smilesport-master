package com.lafresh.smile2.Init;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.lafresh.smile2.Base.BaseActivity;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Utils.IntentUtils;
import com.lafresh.smile2.Utils.SharedUtils;

public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            startInitApp();
                            return;
                        }

                        String token = task.getResult().getToken();
                        SharedUtils.getInstance().setFirebaseToken(SplashActivity.this, token);
                        startInitApp();
                    }
                });
    }

    private void startInitApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedUtils.getInstance().getUserFirstOpenApp(SplashActivity.this)) {
                    SharedUtils.getInstance().setUserOpenApp(SplashActivity.this);
                    IntentUtils.intentToNotificationControl(SplashActivity.this);
                } else {
                    IntentUtils.intentToMain(SplashActivity.this, true);
                }
                finish();

            }
        }, 1500);
    }
}
