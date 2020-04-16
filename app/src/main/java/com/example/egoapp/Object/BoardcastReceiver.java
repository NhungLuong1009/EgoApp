//* FILE			: BoardcastRevicer.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Feb 8, 2018
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info



package com.example.egoapp.Object;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BoardcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.EXAMPLE_ACTION".equals( intent.getAction())){
            String receivedText = intent.getStringExtra( "com.example.EXTRA_TEXT" );
            Toast.makeText(context, receivedText, Toast.LENGTH_SHORT).show();
        }
    }
}
