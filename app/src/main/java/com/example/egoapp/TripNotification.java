//* FILE			: TripNotification.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Feb 8, 2018
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info

package com.example.egoapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;


public class TripNotification extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private EditText customerName;
    private EditText dateOfTrip;
    private EditText timeOfTrip, txtTime;
    Button btnDatePicker, btnTimePicker ;
    private int mYear, mMonth, mDay, mHour, mMinute;


    /* =========================================================================================================================*
     * Name		: onCreate
     * Purpose	: to create notification
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: none
     *===========================================================================================================================*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_trip_notification );
        Button button = findViewById(R.id.buttonmot);
        EditText name = findViewById( R.id.userName );
        EditText date = findViewById( R.id.in_date );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*
                    HERE I'M GONNA USE CUSTOM ALERT DIALOG
                 */
                new AlertDialog.Builder(TripNotification.this).setTitle("NOTIFICATION").setMessage("Are you sure to add a new NOTIFICATION?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText name = findViewById( R.id.userName );
                                EditText date = findViewById( R.id.in_date );
                                int retCode = validateInput(name, date);
                                ShareData.tripNotificationUserName = name.getText().toString();
                                ShareData.tripNotificationDate = date.getText().toString();

                                addNotification();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO NOTHING
                            }
                        })
                        .show();

            }
        });

    }

    /* =========================================================================================================================*
     * Name		: validateInput
     * Purpose	: to validate all input and return a retCode represent for the validity
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: an error string
     *===========================================================================================================================*/
    private int validateInput(EditText name, EditText date) {
        // VALIDATE THE MARK
        if (isEmpty(name) == true)
        {
            Toast.makeText(this, "Please enter your name! Ex: Laura", Toast.LENGTH_SHORT).show();
            return ShareData.StartCityISEmpty;
        }
        else if (isEmpty(date) == true)
        {
            Toast.makeText(this, "Please enter date of your trip! Ex:02/03/2020\"", Toast.LENGTH_SHORT).show();
            return ShareData.EndCityISEmpty;
        }

        return ShareData.SUCCESS;
    }


    /* =========================================================================================================================*
     * Name		: addNotification
     * Purpose	: to send message to chanel 1
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: None
     *===========================================================================================================================*/
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addNotification() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        String message = "You have a trip on " + ShareData.tripNotificationDate.toString();
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText(message)
                .setContentTitle("Hello, " + ShareData.tripNotificationUserName)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Trip Notification",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);
    }




    /* =========================================================================================================================*
     * Name		: onCreateOptionsMenu
     * Purpose	: to send message to chanel 1
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: None
     *===========================================================================================================================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }


    /* =========================================================================================================================*
     * Name		: onOptionsItemSelected
     * Purpose	: to send message to chanel 1
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: None
     *===========================================================================================================================*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.nav_about_app:
                startActivity(new Intent(this, AboutEgoAppActivity.class));
                return true;
            case R.id.nav_make_trip:
                startActivity(new Intent(this, MakeTrip.class));
                return true;
            case R.id.nav_search:
                startActivity(new Intent(this, SearchTripActivity.class));
                return true;
            case R.id.nav_view_Trip:
                startActivity(new Intent(this, ViewTripOptionActivity.class));
                return true;
            case R.id.nav_google_search:
                startActivity(new Intent(this, OpenGoogleSearch.class));
                return true;
            case R.id.nav_app_main:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.nav_open_google_map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.nav_trip_notification:
                startActivity(new Intent(this, TripNotification.class));
                return true;
            case R.id.nav_detect_wifi:
                startActivity(new Intent(this, WifiDetect.class));
                return true;
            case R.id.nav_phone_call:
                startActivity(new Intent(this, PhoneCall.class));
                return true;
            case R.id.nav_add_cus:
                startActivity(new Intent(this, AddCustomer.class));
                return true;
            case R.id.nav_show_payment:
                startActivity(new Intent(this, ShowPayment.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /* =========================================================================================================================*
     * Name		: validateInput
     * Purpose	: to validate all input and return a retCode represent for the validity
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: an error string
     *===========================================================================================================================*/
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


}
