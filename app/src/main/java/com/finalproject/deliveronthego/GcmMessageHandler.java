package com.finalproject.deliveronthego;

/**
 * Created by sahithi on 5/1/15.
 */
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class GcmMessageHandler extends IntentService {

    String mes;
    private Handler handler;
    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        Intent intentt = new Intent(this, DeliveryActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentt, 0);
        mes = extras.getString("title");
        Notification n  = new Notification.Builder(this)
                .setContentTitle(mes)
                .setContentText(extras.getString("message"))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher, "Accept", pIntent)
                .addAction(R.drawable.ic_launcher, "Reject", pIntent).build();
        showToast();
        Log.i("GCM", "Received : (" +messageType+")  "+extras.getString("title"));
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
        GcmBroadcastReceiver.completeWakefulIntent(intent);


    }

    public void showToast(){
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),mes , Toast.LENGTH_LONG).show();
            }
        });

    }
}