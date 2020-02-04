package com.example.egoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MakeTrip  extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_trip);

        Toast myToast = Toast.makeText(getApplicationContext(), "we are in layout 2 and the new value is " + ShareData.something + " and " + ShareData.something2, Toast.LENGTH_SHORT);
        myToast.show();

        Button myBtn = findViewById(R.id.makeTripBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MakeTrip.this, DisplayTrip.class);
                startActivity(myIntent);
            }
        });
    }
}
