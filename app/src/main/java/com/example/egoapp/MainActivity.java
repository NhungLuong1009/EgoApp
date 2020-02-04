package com.example.egoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast myToast = Toast.makeText(getApplicationContext(), "hello there how are you", Toast.LENGTH_SHORT);
        myToast.show();


        Button myBtn = findViewById(R.id.mainBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, MakeTrip.class);
                startActivity(myIntent);
            }
        });

    }
}
