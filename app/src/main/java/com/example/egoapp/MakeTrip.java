//* FILE			: MakeTrip.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info

package com.example.egoapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MakeTrip  extends AppCompatActivity
    implements View.OnClickListener
{

    Button btnDatePicker, btnTimePicker;
    EditText userFullName;
    EditText userPhoneNum;
    EditText txtDate;
    EditText txtTime;
    String txtNumOfAdult;
    String txtNumOfChildren;
    int buttonSelected;
    private int mYear, mMonth, mDay, mHour, mMinute;

    Boolean isAllFieldFilled = true;

    String selectedStartCities = "";
    String selectedEndCities = "";
    Spinner spinner1 = null;
    Spinner spinner2 = null;

    private String TAG = MakeTrip.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    private static String url = "http://10.0.2.2:3000/cities/";

    ArrayList<HashMap<String, String>> citiesList;

    /*
     * Function: onCreate
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_trip);

        // ******************** Make Trip button ***************************************************** //
        Button myBtn = findViewById(R.id.makeTripBtn);
        // Event trigger fot "Make Trip" Button
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText enteredUserName = findViewById(R.id.userName );
                EditText enteredPhoneNum = findViewById(R.id.userPhoneNumber );
                EditText selectedDate = findViewById(R.id.in_date);
                EditText selectedTime = findViewById(R.id.in_time);
                EditText selectedNumOfAdult = findViewById(R.id.adult_edit_text);
                EditText selectedNumberOfChildren = findViewById(R.id.child_edit_text);
                RadioGroup selectedRoundtripButton = findViewById(R.id.roundtrip_radioGroup);


                //==========================================================================================================//
                //  - WE VALIDATE THE INPUT. THEN WE CHECK IF:
                //      - The return value of validateInput() is different from SUCCESS, display Toast message
                //      - Otherwise, update ex expected mark, course name and open the info screen
                //==========================================================================================================//
                int retCode = validateInput();
                if(retCode == ShareData.SUCCESS)
                {
                    ShareData.tripUserName = enteredUserName.getText().toString();
                    ShareData.tripUserPhoneNum = enteredPhoneNum.getText().toString();

                    ShareData.tripSelectedStartCity = spinner1.getSelectedItem().toString();
                    ShareData.tripSelectedEndCity = spinner2.getSelectedItem().toString();

                    ShareData.tripSelectedDate = selectedDate.getText().toString();
                    ShareData.tripSelectedTime = selectedTime.getText().toString();

                    ShareData.tripNumberOfAdult = selectedNumOfAdult.getText().toString();
                    txtNumOfAdult = ShareData.tripNumberOfAdult;
                    ShareData.tripNumberOfChildren = selectedNumberOfChildren.getText().toString();
                    txtNumOfChildren = ShareData.tripNumberOfChildren;

                    int selectedButtonId = selectedRoundtripButton.getCheckedRadioButtonId();
                    if(selectedButtonId == 0)
                    {
                        ShareData.tripSelectedRoundTripOption = true;
                        buttonSelected = selectedButtonId;
                    }
                    else
                    {
                        ShareData.tripSelectedRoundTripOption = false;
                    }

                    Intent myIntent = new Intent(MakeTrip.this, DisplayTrip.class);
                    startActivity(myIntent);
                }
                else
                {
                    Toast myToast = Toast.makeText(getApplicationContext(), getErrorString(retCode), Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });


        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
    }


    double  percentageScale;
    ProgressBar pb;
    double howManyTaskList;

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MakeTrip.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray cities = jsonObj.getJSONArray("cities");

                    // looping through All Contacts
                    for (int i = 0; i < cities.length(); i++) {
                        JSONObject c = cities.getJSONObject(i);

                        String id = c.getString("id");
                        String startCity = c.getString("startCity");
                        String endCity = c.getString("endCity");
//                        String distance = c.getString("distance");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("id", id);
                        contact.put("startCity", startCity);
                        contact.put("endCity", endCity);

                        // adding contact to contact list
                        citiesList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            final Spinner spinner1 = (Spinner) findViewById(R.id.listStartCity);
            final Spinner spinner2 = (Spinner) findViewById(R.id.listEndCity);

            List<String> listStartCity = new ArrayList<String>();
            List<String> listEndCity = new ArrayList<String>();

            Log.e(TAG, "element size: " + citiesList.size());
            for (int i = 0; i < citiesList.size(); i++) {
                HashMap<String, String> map = citiesList.get(i);

                listStartCity.add(map.get("startCity"));
                listEndCity.add(map.get("endCity"));
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MakeTrip.this, android.R.layout.simple_spinner_item, listStartCity);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(dataAdapter);

            dataAdapter = new ArrayAdapter<String>(MakeTrip.this, android.R.layout.simple_spinner_item, listEndCity);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(dataAdapter);

        }
    }


    //=============================================================================
    // Date and time display for allowing user select
    //=============================================================================
    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Open the DatePicker for user to choose one special date on screen
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    /* =========================================================================================================================*
     * Name		: findIndex
     * Purpose	: to find index of the string
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: an error string
     *===========================================================================================================================*/
    public static int findIndex (String[] array , String name) {
        for (int i=0; i<array.length; i++ ) {
            if (array[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }



    /* =========================================================================================================================*
     * Name		: validateInput
     * Purpose	: to validate all input and return a retCode represent for the validity
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: an error string
     *===========================================================================================================================*/
    private int validateInput() {
        String temptStartcity = spinner1.getSelectedItem().toString();
        String temptEndCity = spinner2.getSelectedItem().toString();
        String temptDate = ((EditText)findViewById(R.id.in_date)).getText().toString();
        String temptTime = ((EditText)findViewById(R.id.in_time)).getText().toString();
        EditText numOfAdult = findViewById(R.id.adult_edit_text);
        EditText numOfChildren = findViewById(R.id.child_edit_text);
        int temptNoOfPass = 0;
        if(!numOfAdult.getText().toString().trim().isEmpty())
        {
            temptNoOfPass = Integer.valueOf(numOfAdult.getText().toString());
        }

        if(!numOfChildren.getText().toString().trim().isEmpty())
        {
            temptNoOfPass = Integer.valueOf(numOfChildren.getText().toString());
        }


        // VALIDATE THE MARK
        if (temptStartcity.isEmpty() == true)
        {
            return ShareData.StartCityISEmpty;
        }
        else if (temptNoOfPass <=0 )
        {
            return ShareData.NoOfPassEmpty;
        }
        else if (temptEndCity.isEmpty() == true)
        {
            return ShareData.EndCityISEmpty;
        }
        else if (temptDate.isEmpty() == true)
        {
            return ShareData.DateISEmpty;
        }
        else if (temptTime.isEmpty() == true)
        {
            return ShareData.TimeIsEmpty;
        }

        return ShareData.SUCCESS;
    }



    /* =========================================================================================================================*
     * Name		: getErrorString
     * Purpose	: to get an appropriate error string base on the input int errorCode
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: an error string
     *===========================================================================================================================*/
    private String getErrorString(int errorCode)
    {
        String retString = "";
        if (errorCode == ShareData.StartCityISEmpty)
        {
            retString = "Start city name cannot be empty";
        }
        else if (errorCode == ShareData.EndCityISEmpty)
        {
            retString = "End city cannot contain any character";
        }
        else if (errorCode == ShareData.DateISEmpty)
        {
            retString = "Date cannot be empty";
        }
        else if (errorCode == ShareData.TimeIsEmpty)
        {
            retString = "Time cannot be empty";
        }
        else if (errorCode == ShareData.RoundTripIsEmpty)
        {
            retString = "Please select round trip or not";
        }
        else if (errorCode == ShareData.NoOfPassEmpty)
        {
            retString = "Please enter number of passenger";
        }
        return retString;
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
}
