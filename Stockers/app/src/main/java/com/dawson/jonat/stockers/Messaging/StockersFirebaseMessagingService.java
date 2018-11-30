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
        Log.i(TAG, "Refresehed Token: " + token);
        //Send the token to the server
        //TODO sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


    }

}
