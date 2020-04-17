//* FILE			: SampleWidgets.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #3
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: April 17, 2020
//* DESCRIPTION		: The file defines widgets for getting trip info

package com.example.egoapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class App extends Application{
    //define Logger Class
    private static final String LOGTAG = "App.class";

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        Log.i(LOGTAG, "Display App onCreate()...");
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        Log.i(LOGTAG, "Display createNotificationChannels...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
}
}
