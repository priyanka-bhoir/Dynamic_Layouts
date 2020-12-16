package com.priyanka.dynamiclayouts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import javax.xml.transform.Result;

public class Firebase extends FirebaseMessagingService {
//    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//        @Override
//        public void onComplete(@NonNull Task<String> task) {
//            if (!task.isSuccessful()) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                return;
//            }
//
//            // Get new FCM registration token
//            String token = task.getResult();
//
//            // Log and toast
//            String msg = getString(R.string.msg_token_fmt, token);
//            Log.d(TAG, msg);
//            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//        }
//    });
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }

    public void onTokenRefresh() {
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        //Implement this method if you want to store the token on your server
    }



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        createNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());



    }
    private void createNotification( String messageBody,String title) {
        Intent intent = new Intent( this , Recycler. class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
        //      PendingIntent.FLAG_ONE_SHOT);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Recycler.class), 0);


        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(contentIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
