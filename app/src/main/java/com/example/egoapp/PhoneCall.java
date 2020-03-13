package com.example.egoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PhoneCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        Button call = (Button) findViewById( R.id.fab );

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------when the call button is pressed-----------------------------
                final int REQUEST_PHONE_CALL = 1;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:1234567890"));
                //-----------------checks for permission before placing the call--------------------
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(PhoneCall.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(PhoneCall.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    }else{
                        //-------------------------------places the call-----------------------------------
                        startActivity(callIntent);
                    }
                }
            }
        });
    }
}
