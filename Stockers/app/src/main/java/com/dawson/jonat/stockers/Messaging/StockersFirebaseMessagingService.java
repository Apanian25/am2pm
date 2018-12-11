package com.dawson.jonat.stockers.Messaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Class is used to handle notification that come from Firebase
 */
public class StockersFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "MessagingService";


    /**
     *This method is called everytime the instance id token is updated. For now it is not used, but
     * would be usefull if we needed to send the token to the server
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i(TAG, "Refresehed Token: " + token);
    }

    /**
     * Event listiner that listens for a RemoteMessage (notificaition) to be send to the application,
     * and then extracts the title and text from it, in order to display it to the user on the screen.
     *
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();

        NotificationUtilities notificationUtilities = new NotificationUtilities(getApplicationContext());
        notificationUtilities.displayNotification(title, text);
    }

}
