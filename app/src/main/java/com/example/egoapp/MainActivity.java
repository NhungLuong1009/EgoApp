//* FILE			: MainActivity.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Feb 8, 2018
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info


package com.example.egoapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.egoapp.Object.BoardcastReceiver;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //define Logger Class
    private static final String LOGTAG = "MainActivity.class";

    Button searchBtn, makeNewTripBtn, viewTripBtn, aboutAppBtn, makeTripNotificationBtn, wifiDetectBtn;
    private DrawerLayout drawer;

    public static final String CHANNEL_ID = "egoAppServiceChannel";
    private Object Built;


    // Create BroadcastReciever
    BoardcastReceiver exampleBoardcastReceiver = new BoardcastReceiver();

    

    /*
     * Function: onCreate
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        Log.i(LOGTAG, "EgoApp Started... ");

        createNotificationChannel();

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        searchBtn = (Button)findViewById(R.id.searchButton);
        Log.d(LOGTAG, "Before search button for OnClickListener");
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent = new Intent(MainActivity.this, SearchTripActivity.class);
                    startActivity(myIntent);
                }
                catch (Exception e)
                {
                    Log.e(LOGTAG, "An error occurred when the search button was clicked.");
                    Log.e(LOGTAG, "Detailed Log Info", e);
                }
            }
        });
        Log.d(LOGTAG, "after search button for OnClickListener");

        makeNewTripBtn = (Button)findViewById(R.id.makeNewTripButton);
        Log.d(LOGTAG, "Before make trip button for OnClickListener");
        makeNewTripBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent = new Intent(MainActivity.this, MakeTrip.class);
                    startActivity(myIntent);
                }
                catch(Exception e)
                {
                    Log.e(LOGTAG, "An error occurred when the make trip button was clicked.");
                    Log.e(LOGTAG, "Detailed Log Info", e);
                }
            }
        });
        Log.d(LOGTAG, "after make trip button for OnClickListener");

        viewTripBtn = (Button)findViewById(R.id.viewTripButton);
        Log.d(LOGTAG, "Before view trip button for OnClickListener");
        viewTripBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent = new Intent(MainActivity.this, ViewTripOptionActivity.class);
                    startActivity(myIntent);
                }
                catch(Exception e)
                {
                    Log.e(LOGTAG, "An error occurred when the view trip button was clicked.");
                    Log.e(LOGTAG, "Detailed Log Info", e);
                }
            }
        });
        Log.d(LOGTAG, "after view trip button for OnClickListener");

        aboutAppBtn = (Button)findViewById(R.id.aboutAppButton);
        Log.d(LOGTAG, "Before about button for OnClickListener");
        aboutAppBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent;
                    myIntent = new Intent(MainActivity.this, AboutEgoAppActivity.class);
                    startActivity(myIntent);
                }
                catch(Exception e)
                {
                    Log.e(LOGTAG, "An error occurred when the about button was clicked.");
                    Log.e(LOGTAG, "Detailed Log Info", e);
                }
            }
        });
        Log.d(LOGTAG, "after about button for OnClickListener");

        makeTripNotificationBtn= (Button)findViewById(R.id.makeNotificationBtn);
        Log.d(LOGTAG, "Before about makeTripNotification for OnClickListener");
        makeTripNotificationBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent;
                    myIntent = new Intent(MainActivity.this, TripNotification.class);
                    startActivity(myIntent);
                }
                catch(Exception e)
                {
                    Log.e(LOGTAG, "An error occurred when the makeTripNotification button was clicked.");
                    Log.e(LOGTAG, "Detailed Log Info", e);
                }
            }
        });
        Log.d(LOGTAG, "after about makeTripNotification for OnClickListener");

        wifiDetectBtn= (Button)findViewById(R.id.detectWifiBtn);
        Log.d(LOGTAG, "Before about wifiDetectBtn for OnClickListener");
        wifiDetectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent;
                    myIntent = new Intent(MainActivity.this, WifiDetect.class);
                    startActivity(myIntent);
                }
                catch(Exception e)
                {
                    Log.e(LOGTAG, "An error occurred when the wifiDetectBtn button was clicked.");
                    Log.e(LOGTAG, "Detailed Log Info", e);
                }
            }
        });
        Log.d(LOGTAG, "after about wifiDetectBtn for OnClickListener");

        IntentFilter filter = new IntentFilter( "com.example.EXAMPLE.ACTION");
        registerReceiver( exampleBoardcastReceiver, filter );
    }

    /*
     * Function: onDestroy
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerReceiver( exampleBoardcastReceiver );
    }


    /*
     * Function: onDestroy
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    private void registerReceiver(BoardcastReceiver exampleBoardcastReceiver) {
    }


    private void sendBroadcast (View v){
        Intent intent = new Intent( "com.example.EXAMPLE_ACTION" );
        intent.putExtra( "com.example.EXTRA_TEXT", "Broadcast Received" );
        sendBroadcast( intent );
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChanel = new NotificationChannel(
                    CHANNEL_ID,
                    "Ego App Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class );
            manager.createNotificationChannel(serviceChanel );
        }
    }

    public void setSupportActionBar(Toolbar toolBar) {
    }

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


    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
