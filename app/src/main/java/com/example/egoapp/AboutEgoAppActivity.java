//* FILE			: AboutEgoAppActivity.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the about screen
package com.example.egoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutEgoAppActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //define Logger Class
    private static final String LOGTAG = "AboutEgoAppActivity.class";

    /*
     * Function: onCreate
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Log.i(LOGTAG, "Running AboutAppActivity...");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about_ego_app);
        }
        catch (Exception e){
            Log.e(LOGTAG, "An error occurred when the about button was clicked.");
            Log.e(LOGTAG, "Detailed Log Info", e);
        }

    }

    /*
     * Function: onQueryTextSubmit
     * Description:
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    /*
     * Function: onQueryTextChange
     * Description:
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    /*
     * Function: onCreateOptionsMenu
     * Description:
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }


    /*
     * Function: onOptionsItemSelected
     * Description:
     * Input: Bundle savedInstanceState
     * Return: none
     */
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
