//* FILE			: MainActivity.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Feb 8, 2018
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info


package com.example.egoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button searchBtn, makeNewTripBtn, viewTripBtn, aboutAppBtn;
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

        searchBtn = (Button)findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SearchTrip.class);
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
                Intent myIntent = new Intent(MainActivity.this, ViewTripOption.class);
                startActivity(myIntent);
            }
        });

        aboutAppBtn = (Button)findViewById(R.id.aboutAppButton);
        aboutAppBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent;
                myIntent = new Intent(MainActivity.this, AboutEgoApp.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
