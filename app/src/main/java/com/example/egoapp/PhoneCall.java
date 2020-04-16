//* FILE			: PhoneCall.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the class to make phone call
package com.example.egoapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PhoneCall extends AppCompatActivity {

    TextView contactList;
    EditText inputPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        Button call = (Button) findViewById( R.id.fab );
        contactList = (TextView) findViewById(R.id.textView_phoneList);
        inputPhoneNumber = (EditText) findViewById(R.id.editText_phoneNumber);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Constant.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            fetchContacts();
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    HERE I'M GONNA USE CUSTOM ALERT DIALOG TO PROCEED A CALL
                 */
                new AlertDialog.Builder(PhoneCall.this).setMessage("Proceed to Call?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                makeACall();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // clear the input phone number
                                inputPhoneNumber.getText().clear();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO NOTHING
                            }
                        })
            }
        });
    }

    public void makeACall() {
        //----------------------when the call button is pressed-----------------------------
        final int REQUEST_PHONE_CALL = 1;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+ inputPhoneNumber.getText()));
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

    public void fetchContacts() {
        String phoneNumber = null;
        String email = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        StringBuffer output = new StringBuffer();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                if (hasPhoneNumber > 0) {
                    output.append("\n First Name:" + name);
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n Phone number:" + phoneNumber);
                    }
                    phoneCursor.close();
                }
                output.append("\n");
            }
            contactList.setText(output);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

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

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchContacts();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("My App", "permission denied");
                }
                return;
            }
        }
    }*/

    private class Constant {
        public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    }
}
