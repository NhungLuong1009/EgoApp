//* FILE			: DisplayTrip.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the third screen of the app asking for getting trip info


package com.example.egoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayTrip extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_trip);

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
        selectedPassenger.setText(ShareData.tripNumberOfPassenger);

        // assign parameter for RoundTrip
        TextView selectedRoundTrip = findViewById(R.id.inSelectedRoundTrip);
        if(ShareData.tripSelectedRoundTripOption == true){
            selectedRoundTrip.setText("No");
        }
        else{
            selectedRoundTrip.setText("Yes");
        }

        /*
        Toast myToast = Toast.makeText(getApplicationContext(), "we are in layout 3 and the new value is " + ShareData.tripSelectedStartCity +
                " and " + ShareData.tripSelectedRoundTripOption, Toast.LENGTH_SHORT);
        myToast.show();
        */

        Button myBtn = findViewById(R.id.displayTripBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DisplayTrip.this, DisplayTicket.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
