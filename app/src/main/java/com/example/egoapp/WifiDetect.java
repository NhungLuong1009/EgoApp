//* FILE			: SampleWidgets.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #3
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: April 17, 2020
//* DESCRIPTION		: The file defines widgets for getting trip info

package com.example.egoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WifiDetect extends AppCompatActivity {
    //define Logger Class
    private static final String LOGTAG = "WifiDetect.class";

    private Switch switchWifi;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOGTAG, "Display WifiDetect screen...");
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wifi_detect );
        String result = "Cannot connected to network!";

        switchWifi = findViewById(R.id.wifiSwitch );
        wifiManager = (WifiManager) getApplicationContext().getSystemService( Context.WIFI_SERVICE );
        switchWifi.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String result = "Cannot connected to network!";
                if(isChecked){
                  wifiManager.setWifiEnabled( true );
                  switchWifi.setText("WIFI is on" );
                    Context context;
                    result = "Network is connected";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
                else{
                    wifiManager.setWifiEnabled( false );
                    switchWifi.setText("WIFI is off" );
                    result = "Network is disconnected";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
            }
        } );

        if (wifiManager.isWifiEnabled())
        {
            switchWifi.setChecked(true );
            switchWifi.setText("WIFI is on");
            result = "Network is connected";
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
        else{
            switchWifi.setChecked(true );
            switchWifi.setText("WIFI is off");
            result = "Network is disconnected";
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }


    /*
            Function: onStart
            Description: to get the contact from system database and show on textview
            Input:
            Return: void
     */
    protected void onStart(){
        Log.i(LOGTAG, "Display WifiDetect start()...");
        super.onStart();
        IntentFilter intentFilter = new IntentFilter( WifiManager.WIFI_STATE_CHANGED_ACTION );
        registerReceiver( wifiStateReceiver, intentFilter );
    }

    /*
            Function: onStop
            Description: to get the contact from system database and show on textview
            Input:
            Return: void
     */
    @Override
    protected void onStop() {
        Log.i(LOGTAG, "Display WifiDetect stop()...");
        super.onStop();
        unregisterReceiver( wifiStateReceiver );
    }

    BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(LOGTAG, "Display wifiStateReceiver Listener...");
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    switchWifi.setChecked( true );
                    switchWifi.setText( "WIFI is on" );
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    switchWifi.setChecked( false);
                    switchWifi.setText( "WIFI is off" );
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

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
            case R.id.nav_add_cus:
                startActivity(new Intent(this, AddCustomer.class));
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
            case R.id.nav_show_payment:
                startActivity(new Intent(this, ShowPayment.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
