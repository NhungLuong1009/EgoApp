//* FILE			: Display Ticket.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the last screen of the app asking for getting trip info




package com.example.egoapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class DisplayTicket  extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_ticket);
        TextView startCity = findViewById(R.id.showedStartCity);
        startCity.setText(ShareData.tripSelectedStartCity);
        TextView endCity = findViewById(R.id.showedEndCity);
        endCity.setText(ShareData.tripSelectedEndCity);
        TextView dateToGo = findViewById(R.id.showedDate);
        dateToGo.setText(ShareData.tripSelectedDate);
        TextView timeToGo = findViewById(R.id.showedTime);
        timeToGo.setText(ShareData.tripSelectedTime);
        TextView numOfPassenger = findViewById(R.id.showedNumberOfPassenger);
        numOfPassenger.setText(ShareData.tripNumberOfPassenger);

        TextView tax = findViewById(R.id.showedTax);
        TextView amount = findViewById(R.id.showedAmount);

        // get PriceInfo with startCity, endCity
        Map<PriceVO, String> hMap = PriceMetaData.setPriceMeta();
        //int price = PriceMetaData.getPriceInfo(hMap,"Waterloo", "Ottawa");
        int price = PriceMetaData.getPriceInfo(hMap, startCity.getText().toString().trim(), endCity.getText().toString().trim());
        //int price = PriceMetaData.getPriceInfo(hMap, ShareData.tripSelectedStartCity, ShareData.tripSelectedEndCity);
        amount.setText(String.valueOf(price));

        TextView total = findViewById(R.id.showedTotalAmount);

        // calculate amount & tax
        // 1. covert amount
        String toConvertStr;
        toConvertStr = amount.getText().toString();
        int toConvertAmount = Integer.parseInt(toConvertStr);

        // 2. convert passenger
        toConvertStr = numOfPassenger.getText().toString();
        int toConvertPassenger = Integer.parseInt(toConvertStr);
        // 3. assign tax
        float convertTax = 0.13f;
        float totalAmount = 0.0f;

        convertTax = convertTax * toConvertAmount * toConvertPassenger;
        totalAmount = convertTax + toConvertAmount * toConvertPassenger;

        // assign the value for ticket price
        tax.setText(Float.toString(convertTax));
        total.setText(Float.toString(totalAmount));

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
