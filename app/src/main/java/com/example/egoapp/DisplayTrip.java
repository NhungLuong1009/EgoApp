//* FILE			: DisplayTrip.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the third screen of the app asking for getting trip info


package com.example.egoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayTrip extends AppCompatActivity implements View.OnClickListener{

    //define Logger Class
    private static final String LOGTAG = "DisplayTrip.class";

    /*
     * Function: onCreate
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_trip);

        Log.i(LOGTAG, "Display Trip screen...");

        // assign parameter for start city
        TextView startCity = findViewById(R.id.startCity);
        startCity.setText(ShareData.tripSelectedStartCity);

        // assign parameter for end city
        TextView endCity = findViewById(R.id.endCity);
        endCity.setText(ShareData.tripSelectedEndCity);

        // assign parameter for Date
        TextView selectedDate = findViewById(R.id.inSelectedDate);
        selectedDate.setText(ShareData.tripSelectedDate);

        // assign parameter for Time
        TextView selectedTime = findViewById(R.id.inSelectedTime);
        selectedTime.setText(ShareData.tripSelectedTime);

        // assign parameter for NumberOfPassenger
        TextView selectedPassenger = findViewById(R.id.inSelectedPassenger);
        int totalPassenger = Integer.parseInt(ShareData.tripNumberOfAdult) + Integer.parseInt(ShareData.tripNumberOfChildren);
        selectedPassenger.setText(String.valueOf(totalPassenger));

        // assign parameter for RoundTrip
        TextView selectedRoundTrip = findViewById(R.id.inSelectedRoundTrip);
        if(ShareData.tripSelectedRoundTripOption == "OneWay"){
            selectedRoundTrip.setText("No");
        }
        else{
            selectedRoundTrip.setText("Yes");
        }

        Button myBtn = findViewById(R.id.displayTripBtn);
        Log.d(LOGTAG, "Before display trip button for OnClickListener");
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent = new Intent(DisplayTrip.this, DisplayTicket.class);
                    startActivity(myIntent);
                }
                catch(Exception e)
                {
                    Log.e(LOGTAG, "An error occurred when the about button was clicked.");
                    Log.e(LOGTAG, "Detailed Log Info", e);
                }

                Intent myIntent = new Intent(DisplayTrip.this, DisplayTicket.class);
                startActivity(myIntent);
            }
        });
        Log.d(LOGTAG, "after display trip button for OnClickListener");
    }

    @Override
    public void onClick(View v) {

    }
}
