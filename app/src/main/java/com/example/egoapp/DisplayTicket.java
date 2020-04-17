//* FILE			: Display Ticket.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the last screen of the app asking for getting trip info

package com.example.egoapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayTicket  extends AppCompatActivity implements View.OnClickListener
{
    //define Logger Class
    private static final String LOGTAG = "DisplayTicket.class";
    /*
     * Function: onCreate
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set all data from Display Trip screen by getting the share data class values
        // Show customer Information

        Log.i(LOGTAG, "Display Ticket screen...");

        setContentView(R.layout.display_ticket);
        TextView passengerName = findViewById(R.id.userName);
        passengerName.setText(ShareData.userName);
        TextView passengerPhone = findViewById(R.id.userPhoneNum);
        passengerPhone.setText(ShareData.userPhoneNumber);

        // Show start City
        TextView startCity = findViewById(R.id.showedStartCity);
        startCity.setText(ShareData.tripSelectedStartCity);

        // Show end City
        TextView endCity = findViewById(R.id.showedEndCity);
        endCity.setText(ShareData.tripSelectedEndCity);

        // Show Date
        TextView dateToGo = findViewById(R.id.showedDate);
        dateToGo.setText(ShareData.tripSelectedDate);

        // Show time
        TextView timeToGo = findViewById(R.id.showedTime);
        timeToGo.setText(ShareData.tripSelectedTime);

        // Show number of passengers
        TextView numOfPassenger = findViewById(R.id.showedNumberOfPassenger);
        int totalPassenger = Integer.parseInt(ShareData.tripNumberOfAdult) + Integer.parseInt(ShareData.tripNumberOfChildren);
        numOfPassenger.setText(String.valueOf(totalPassenger));

        // Get ID from TextView for Tax & Amount
        TextView tax = findViewById(R.id.showedTax);
        TextView amount = findViewById(R.id.showedAmount);

        // get PriceInfo with startCity, endCity
        amount.setText(Integer.toString(ShareData.totalPrice));

        TextView total = findViewById(R.id.showedTotalAmount);

        // calculate amount & tax
        try {
            // 1. covert amount
            String toConvertStr = amount.getText().toString();
            int toConvertAmount = Integer.parseInt(toConvertStr);
            Log.d(LOGTAG, "convert amount");

            // 2. convert passenger
            toConvertStr = numOfPassenger.getText().toString();
            int toConvertPassenger = Integer.parseInt(toConvertStr);
            Log.d(LOGTAG, "convert passenger");

            // 3. assign tax
            float convertTax = 0.13f;
            float totalAmount = 0.0f;

            convertTax = convertTax * toConvertAmount * toConvertPassenger;
            totalAmount = convertTax + toConvertAmount * toConvertPassenger;

            Log.d(LOGTAG, "convertTax=" + convertTax + "," + "totalAmount=" + totalAmount);

            // assign the value for ticket price
            tax.setText(Float.toString(convertTax));
            total.setText(Float.toString(totalAmount));
        }
        catch (Exception e) {
            Log.e(LOGTAG, "An error occurred when converting a variable to an integer.");
            Log.e(LOGTAG, "Detailed Log Info", e);
        }

        Button myBtn = findViewById(R.id.displayTicketBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DisplayTicket.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(DisplayTicket.this, DisplayTicket.class);
        startActivity(myIntent);

    }
}
