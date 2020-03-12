//* FILE			: MainActivity.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info


package com.example.egoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.egoapp.DBHandler.CityDB;
import com.example.egoapp.Object.Cities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button searchBtn, makeNewTripBtn, viewTripBtn, aboutAppBtn;
    private DrawerLayout drawer;

    private String TAG = MakeTrip.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String url = "http://10.0.2.2:3000/cities/";
    ArrayList<HashMap<String, String>> citiesList;

    CityDB callerCity;

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


        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        searchBtn = (Button)findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SearchTripActivity.class);
                startActivity(myIntent);
            }
        });


        makeNewTripBtn = (Button)findViewById(R.id.makeNewTripButton);
        makeNewTripBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, MakeTrip.class);
                startActivity(myIntent);
            }
        });

        viewTripBtn = (Button)findViewById(R.id.viewTripButton);
        viewTripBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ViewTripOptionActivity.class);
                startActivity(myIntent);
            }
        });

        aboutAppBtn = (Button)findViewById(R.id.aboutAppButton);
        aboutAppBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent;
                myIntent = new Intent(MainActivity.this, AboutEgoAppActivity.class);
                startActivity(myIntent);
            }
        });
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

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray cities = jsonObj.getJSONArray("cities");

                    // looping through All Contacts
                    Log.e(TAG, "Size: " + cities.length());
                    for (int i = 0; i < cities.length(); i++) {
                        JSONObject c = cities.getJSONObject(i);

                        String id = c.getString("id");
                        String startCity = c.getString("startCity");
                        String endCity = c.getString("endCity");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("id", id);
                        contact.put("startCity", startCity);
                        contact.put("endCity", endCity);

                        // adding contact to contact list
                        citiesList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             * */

            List<String> listStartCity = new ArrayList<String>();
            Log.e(TAG, "element size: " + citiesList.size());

            for (int i = 0; i < citiesList.size(); i++) {
                HashMap<String, String> map = citiesList.get(i);
                Cities findCities = new Cities();

                // check cities for existing cities
                findCities = callerCity.findCity(map.get("startCity"));
                if(findCities == null) {
                    // new City ==> cityID assign
                    Cities newCities = new Cities();
                    Log.e(TAG, "### New City ###");
                    // get cityID from table
                    long rows = callerCity.getCityIDCount();
                    newCities.setCityID(rows + 1);
                    Log.e(TAG, "The cityID: " + newCities.getCityID());
                    // get city Name from json-server
                    newCities.setCityName(map.get("startCity"));

                    // insert new city
                    long insertCityId = callerCity.insertCity(newCities);
                    Log.e(TAG, "New Cities: " + insertCityId);
                }
            }
        }
    }
}
