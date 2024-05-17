package com.anass.jplus.Serves;

import android.content.Context;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class NotifacationService implements OneSignal.OSRemoteNotificationReceivedHandler {
    @Override
    public void remoteNotificationReceived(Context context, OSNotificationReceivedEvent osNotificationReceivedEvent) {

        OSNotification notification = osNotificationReceivedEvent.getNotification();
        Log.d("TAG", "remoteNotificationReceived: " + notification.getTitle());
        Log.d("TAG", "remoteNotificationReceived: " + notification.getBigPicture());


        JSONObject jsonObject = notification.getAdditionalData();

        Log.d("TAG", "remoteNotificationReceived: " + jsonObject);



        osNotificationReceivedEvent.complete(notification);


    }
}
