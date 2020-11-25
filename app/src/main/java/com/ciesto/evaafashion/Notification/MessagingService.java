package com.ciesto.evaafashion.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.ciesto.evaafashion.Activity.MainActivity;
import com.ciesto.evaafashion.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MessagingService extends FirebaseMessagingService {
    public static int nId = 10;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    PendingIntent pendingIntent;
    String NOTIFICATION_CHANNEL_ID = "101";
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.d("NOTIFICATION", "remote message" + remoteMessage.getData());
        String Type = remoteMessage.getData().get("Type");
        String Message = remoteMessage.getData().get("message");

        intent = new Intent(this, MainActivity.class);
        intent.putExtra("Type", Type);
        Log.d("NOTIFICATION", "my type " + Type + " message " + Message);

        sendNotification(Message, Type);
    }

    private void sendNotification(String messageBody, String Type)
    {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);

            //Configure Notification Channel
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        //intent.putExtra("Post_Id",id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentIntent(pendingIntent)
                .setLargeIcon(bitmap)
                .setPriority(Notification.PRIORITY_MAX);

        int num = (int) System.currentTimeMillis();
        notificationManager.notify(num, notificationBuilder.build());
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
    }

}