package com.dawson.jonat.stockers.Messaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class StockersFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "MessagingService";


    /**
     *This method is called everytime the instance id token is updated.
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i(TAG, "Refresehed Token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();

        NotificationUtilities notificationUtilities = new NotificationUtilities(getApplicationContext());
        notificationUtilities.displayNotification(title, text);
    }

}
