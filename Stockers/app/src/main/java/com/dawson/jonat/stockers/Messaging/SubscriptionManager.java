package com.dawson.jonat.stockers.Messaging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.dawson.jonat.stockers.MainActivity;
import com.dawson.jonat.stockers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SubscriptionManager {

    /**
     * Unsubscribes the current user from the given topic, stoping them from getting any notifications
     * regarding that topic
     *
     * @param topic
     * @param context
     */
    public static void unsub(String topic, final Context context, final boolean displayToastToUser) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = context.getString(R.string.unSubSuccess);
                        if (!task.isSuccessful()) {
                            msg = context.getString(R.string.subFailed);
                        }
                        Log.d("SUB", msg);
                        if (displayToastToUser)
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * Subscribes to the given topic for the current user, allowing them to get notification for that
     * particular topic
     *
     * @param topic
     * @param context
     */
    public static void sub(String topic, final Context context, final boolean displayToastToUser) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = context.getString(R.string.subSuccess);
                        if (!task.isSuccessful()) {
                            msg = context.getString(R.string.subFailed);
                        }
                        Log.d("SUB", msg);

                        if(displayToastToUser)
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
