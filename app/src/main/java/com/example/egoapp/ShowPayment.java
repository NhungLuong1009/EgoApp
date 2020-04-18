//* FILE			: ShowPayment.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #3
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: April 17, 2020
//* DESCRIPTION		: The file defines Show Payment screen
package com.example.egoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.egoapp.DBHandler.PaymentDB;

public class ShowPayment extends AppCompatActivity {

    //define Logger Class
    private static final String LOGTAG = "ShowPayment.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOGTAG, "ShowPayment screen...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payment);

        // get the URL
        Uri paymentsUri = Uri.parse("content://" + PaymentContentProvider.AUTHORITY + "/payments");
        Log.d("MyApp", paymentsUri.toString());
        Cursor cursor = getContentResolver().query(paymentsUri,null,null,null,null);
        String[] columns = {PaymentDB.PAYMENT_ID, PaymentDB.PAYMENT_TRIP_FROM, PaymentDB.PAYMENT_TRIP_TO, PaymentDB.PAYMENT_PRICE};
        int[] to = {R.id.paymentID, R.id.tripFrom,R.id.tripTo, R.id.priceTotal};
        // Call the Cursor to get the data
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.payment_item,
                cursor,
                columns, to, 0
        );
        // Set the data into ListView
        ListView myList = (ListView)findViewById(R.id.listView_paymentList);
        myList.setAdapter(adapter);
    }
}
