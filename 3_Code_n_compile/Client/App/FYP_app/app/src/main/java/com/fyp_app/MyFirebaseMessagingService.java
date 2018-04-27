package com.fyp_app;


import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static com.google.android.gms.internal.zzs.TAG;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String EXTRA_MESSAGE = "com.fyp.MyFirebaseMessagingService";
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ



        Log.d(TAG, "*************************************************");
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//
//
//        }
//
//        String click_action = remoteMessage.getNotification().getClickAction();
//        Intent intent = new Intent(click_action);
//        String msg = remoteMessage.getNotification().getBody();
//
//        intent.putExtra(EXTRA_MESSAGE, msg);
//        startActivity(intent);

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
