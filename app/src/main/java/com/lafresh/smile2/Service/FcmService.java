package com.lafresh.smile2.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.R;

public class FcmService extends FirebaseMessagingService {
    public static final String TAG = FcmService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"),remoteMessage.getData().get("openUrl"));
            Log.d("onMessageReceived",remoteMessage.getData().get("message"));
            //uiRefresh();
        }
    }
    private void showNotification(String title,String message,String url){
        String channeId = "1";
        String channelName="ChannelName";
        Intent intent;
        if(url!=null && url.length()>0){
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
        }else{
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("NOTIFY_EXTRA", "NOTIFY");
        }

        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(message);
        // Since android Oreo notification channel is needed.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            try{
                NotificationChannel mChannel= new NotificationChannel(channeId, channelName, NotificationManager.IMPORTANCE_HIGH);
                mChannel.enableVibration(true); //震??置
                mChannel.setSound(defaultSoundUri, null); //?置提示音，IMPORTANCE_DEFAULT及以上才?有?音
                notificationManager.createNotificationChannel(mChannel);
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.ic_fcm_logo)
                                .setColor(getResources().getColor(R.color.orangeText))
                                .setContentTitle(title)
                                .setContentText(message)
                                .setContentIntent(pi)
                                .setChannelId(channeId)
                                .setStyle(bigTextStyle);
                notificationManager.notify(0, builder.build());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            NotificationCompat.Builder notificationBuilder= new NotificationCompat.Builder(this,channeId)
                    .setSmallIcon(R.drawable.ic_fcm_logo)
                    .setColor(getResources().getColor(R.color.orangeText))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setChannelId(channeId)
                    .setStyle(bigTextStyle);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }
/*
    private void uiRefresh(){
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
        Intent intent = new Intent(INFO_UPDATE_FILTER);
        intent.putExtra("BADGE_EXTRA", "BADGE_REFRESH");
        broadcaster.sendBroadcast(intent);
    }

 */
}
