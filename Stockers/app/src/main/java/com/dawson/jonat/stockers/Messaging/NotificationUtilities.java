package com.dawson.jonat.stockers.Messaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.dawson.jonat.stockers.R;

public class NotificationUtilities {

    private Context context;

    public NotificationUtilities(Context context) {
        this.context = context;
    }

    public void displayNotification(String title, String text) {

        //Channel Id can be null since it is only used on Oreo and above (todo later)
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, null)
                .setSmallIcon(R.drawable.temp)
                .setContentTitle(title)
                .setContentText(text)
                //This allows for the notification to pop up
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);

        Intent intent = new Intent(context, Messaging.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }
}
