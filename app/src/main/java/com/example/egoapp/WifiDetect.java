//* FILE			: WifiDetect.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Feb 8, 2018
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info

package com.example.egoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WifiDetect extends AppCompatActivity {
    private Switch switchWifi;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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



    protected void onStart(){
        super.onStart();
        IntentFilter intentFilter = new IntentFilter( WifiManager.WIFI_STATE_CHANGED_ACTION );
        registerReceiver( wifiStateReceiver, intentFilter );
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver( wifiStateReceiver );
    }

    BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
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
            case R.id.nav_phone_call:
                startActivity(new Intent(this, PhoneCall.class));
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
