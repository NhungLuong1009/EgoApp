//* FILE			: MakeTrip.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the second screen of the app asking for getting trip info


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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egoapp.DBHandler.AccountDB;
import com.example.egoapp.DBHandler.OrderDB;
import com.example.egoapp.Object.Account;
import com.example.egoapp.Object.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MakeTrip  extends AppCompatActivity implements View.OnClickListener {

    //define Logger Class
    private static final String LOGTAG = "MakeTrip.class";

    private String TAG = MakeTrip.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // JSON-Server URL, at this step, we are working with file by using two instances
    private static String url = "http://10.0.2.2:3000/cities/";
    private static String anotherUrl = "http://10.0.2.2:8888/tourinfo/";

    // Working with List that contains two hashMap that get the content from the JSON Server
    ArrayList<HashMap<String, String>> citiesList;
    ArrayList<HashMap<String, String>> tourList;

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

    OrderDB orderDB;
    AccountDB accountDB;
    double distanceGlobal;

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

        Log.i(LOGTAG, "Running the MakeTrip screen....");

        //Initialize the ArrayList
        try{
            Log.d(LOGTAG, "Initialize the ArrayList....");
            citiesList = new ArrayList<>();
            tourList = new ArrayList<>();
        }
        catch(NullPointerException ne){
            Log.e(LOGTAG, "Initialize the ArrayList Exception: " + ne.getMessage());
        }

        // Initialize the ORDER Database
        try{
            Log.d(LOGTAG, "Initialize the ORDER Database....");
            orderDB = new OrderDB(this);
            accountDB = new AccountDB(this);
        }
        catch(Exception e){
            Log.e(LOGTAG, "Initialize the ArrayList Exception: " + e.getMessage());
        }

        // Run the Cities JSON-SERVER instances using AsyncTask
        new GetCitiesAsyncTask().execute();

        // Get ID from 2 Spinner (Start City & End City)
        spinner1 = (Spinner) findViewById(R.id.listStartCity);
        spinner2 = (Spinner) findViewById(R.id.listEndCity);

        // Load Array into 2 seperate spinner
        String[] allAvailableCities = getResources().getStringArray(R.array.Cities);

        // Check if user click on default trip on screen
        if (ShareData.makeOwnTrip == false) {
            // This step will do some manipulation with string to split the mTitle into startcity & endcity String
            // parse selected trip to 2 end and start city
            String selectedTripCitiy = ShareData.mTitle[ShareData.selectedTrip];
            String[] splitedSelectedTripCity = selectedTripCitiy.split(" to ");
            // Start City Stirng
            selectedStartCities = splitedSelectedTripCity[0];
            // End City String
            selectedEndCities = splitedSelectedTripCity[1];
            int positionStartCities = findIndex(allAvailableCities, selectedStartCities);
            int positionEndCities = findIndex(allAvailableCities, selectedEndCities);
            // Set the position of spinner based on the result from 2 cities
            spinner1.setSelection(positionStartCities);
            spinner2.setSelection(positionEndCities);
        }

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
                if (retCode == ShareData.SUCCESS) {
                    ShareData.tripSelectedStartCity = spinner1.getSelectedItem().toString();
                    ShareData.tripSelectedEndCity = spinner2.getSelectedItem().toString();

                    ShareData.tripSelectedDate = selectedDate.getText().toString();
                    ShareData.tripSelectedTime = selectedTime.getText().toString();

                    ShareData.tripNumberOfAdult = selectedNumOfAdult.getText().toString();
                    txtNumOfAdult = ShareData.tripNumberOfAdult;
                    ShareData.tripNumberOfChildren = selectedNumberOfChildren.getText().toString();
                    txtNumOfChildren = ShareData.tripNumberOfChildren;

                    int selectedButtonId = selectedRoundtripButton.getCheckedRadioButtonId();
                    if (selectedButtonId == 0) {
                        ShareData.tripSelectedRoundTripOption = "RoundTrip";
                        buttonSelected = selectedButtonId;
                    } else {
                        ShareData.tripSelectedRoundTripOption = "OneWay";
                    }

                    // Create the second instances that execute and get the Data from the JSON-Server using AsyncTask
                    new GetTourInfoAsyncTask().execute();

                    // Calculate the bill for Display trip
                    ShareData.totalPrice = calculateBill(Integer.parseInt(ShareData.tripNumberOfAdult), Integer.parseInt(ShareData.tripNumberOfChildren));
                    ShareData.userName = enteredUserName.getText().toString();
                    ShareData.userPhoneNumber = enteredPhoneNum.getText().toString();

                    // INSERT DATA into ORDER TABLE
                    Orders orderObj = new Orders(ShareData.tripSelectedDate, ShareData.tripSelectedTime, ShareData.tripSelectedStartCity,
                            ShareData.tripSelectedEndCity, Integer.parseInt(ShareData.tripNumberOfAdult) , Integer.parseInt(ShareData.tripNumberOfChildren),
                            ShareData.tripSelectedRoundTripOption, getMilesFromJSON(ShareData.tripSelectedStartCity, ShareData.tripSelectedEndCity));

                    long insertOrderId = orderDB.insertOrder(orderObj);
                    if (insertOrderId > 0) {
                        Toast.makeText(MakeTrip.this,"New Order Inserted",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MakeTrip.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }

                    // INSERT DATA into ACCOUNT TABLE
                    Account accountObj = new Account(ShareData.userName, ShareData.userPhoneNumber);
                    long insertAccountId = accountDB.insertAccount(accountObj);
                    if (insertAccountId > 0)
                    {
                        Log.d("ACCOUNT DATA", "New Account Inserted " + enteredUserName + " " + enteredPhoneNum);
                    }
                    else
                    {
                        Log.d("ACCOUNT DATA", "Account data is not inserted");
                        Toast.makeText(MakeTrip.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }

                    Intent myIntent = new Intent(MakeTrip.this, DisplayTrip.class);
                    startActivity(myIntent);
                } else {
                    Toast myToast = Toast.makeText(getApplicationContext(), getErrorString(retCode), Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });


        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
    }


    //=============================================================================
    // Date and time display for allowing user select
    //=============================================================================
    @Override
    public void onClick(View v) {
        Log.i(LOGTAG, "Running the ListViewAdapter onClick....");
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
     * Name		: getMilesFromJSON
     * Purpose	: to get the miles data from JSON Server based on startCity and endCity
     * Inputs	: startCity String - endCity String
     * Outputs	: double
     * Returns	: double
     *===========================================================================================================================*/
    private double getMilesFromJSON(String startCity, String endCity)
    {
        Log.d(LOGTAG, "Running the getMilesFromJSON method....");
        double miles = 0.00;
        for (HashMap<String, String> entry : tourList) {
            // for each hashmap, iterate over it
            Log.d("SC", entry.get("startCity"));
            Log.d("EC", entry.get("endCity"));
            Log.d("ASC", ShareData.tripSelectedStartCity);
            Log.d("AEC", ShareData.tripSelectedEndCity);
            if (entry.get("startCity").equals(startCity) && entry.get("endCity").equals(endCity))
            {
                Log.d("HELLO", entry.get("distance"));
                try{
                    miles = Double.parseDouble(entry.get("distance"));
                    if (miles != 0.00)
                    {
                        break;
                    }
                }
                catch (Exception e) {
                    Log.e(LOGTAG, "getMilesFromJSON method Exception: " + e.getMessage());
                }
            }
        }
        return miles;
    }


    /* =========================================================================================================================*
     * Name		: calculateBill
     * Purpose	: to get the price based on num of adults & children
     * Inputs	: numOfAdult String - numOfAdult String
     * Outputs	: double
     * Returns	: double
     *===========================================================================================================================*/
    public int calculateBill(int numOfAdult, int numOfChild) {
        Log.d(LOGTAG, "Running the calculateBill method....");
        return (numOfAdult*ShareData.priceForAdult) + (numOfChild*ShareData.priceForChild);
    }


    /* =========================================================================================================================*
     * Name		: findIndex
     * Purpose	: to find index of the string
     * Inputs	: int errorCode : the input error code
     * Outputs	: None
     * Returns	: an error string
     *===========================================================================================================================*/
    public static int findIndex(String[] array, String name) {
        Log.d(LOGTAG, "Running the findIndex method....");
        for (int i = 0; i < array.length; i++) {
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
        Log.d(LOGTAG, "Running the validateInput method....");
        String temptStartcity = spinner1.getSelectedItem().toString();
        String temptEndCity = spinner2.getSelectedItem().toString();
        String temptDate = ((EditText) findViewById(R.id.in_date)).getText().toString();
        String temptTime = ((EditText) findViewById(R.id.in_time)).getText().toString();
        EditText numOfAdult = findViewById(R.id.adult_edit_text);
        EditText numOfChildren = findViewById(R.id.child_edit_text);
        int temptNoOfPass = 0;
        if (!numOfAdult.getText().toString().trim().isEmpty()) {
            temptNoOfPass = Integer.valueOf(numOfAdult.getText().toString());
        }

        if (!numOfChildren.getText().toString().trim().isEmpty()) {
            temptNoOfPass = Integer.valueOf(numOfChildren.getText().toString());
        }


        // VALIDATE THE MARK
        if (temptStartcity.isEmpty() == true) {
            return ShareData.StartCityISEmpty;
        } else if (temptNoOfPass <= 0) {
            return ShareData.NoOfPassEmpty;
        } else if (temptEndCity.isEmpty() == true) {
            return ShareData.EndCityISEmpty;
        } else if (temptDate.isEmpty() == true) {
            return ShareData.DateISEmpty;
        } else if (temptTime.isEmpty() == true) {
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
    private String getErrorString(int errorCode) {
        Log.d(LOGTAG, "Running the getErrorString method....");
        String retString = "";
        if (errorCode == ShareData.StartCityISEmpty) {
            retString = "Start city name cannot be empty";
        } else if (errorCode == ShareData.EndCityISEmpty) {
            retString = "End city cannot contain any character";
        } else if (errorCode == ShareData.DateISEmpty) {
            retString = "Date cannot be empty";
        } else if (errorCode == ShareData.TimeIsEmpty) {
            retString = "Time cannot be empty";
        } else if (errorCode == ShareData.RoundTripIsEmpty) {
            retString = "Please select round trip or not";
        } else if (errorCode == ShareData.NoOfPassEmpty) {
            retString = "Please enter number of passenger";
        }
        return retString;
    }


    /* =========================================================================================================================*
     * Name		: onCreateOptionsMenu
     * Purpose	: as function for Menu
     * Inputs	: Menu menu : the menu
     * Outputs	: None
     * Returns	: boolean
     *===========================================================================================================================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }


    /* =========================================================================================================================*
     * Name		: onOptionsItemSelected
     * Purpose	: as function for menuitem
     * Inputs	: MenuItem item : the menu item
     * Outputs	: None
     * Returns	: boolean
     *===========================================================================================================================*/
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
            case R.id.nav_open_google_map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.nav_trip_notification:
                startActivity(new Intent(this, TripNotification.class));
                return true;
            case R.id.nav_detect_wifi:
                startActivity(new Intent(this, WifiDetect.class));
                return true;
            case R.id.nav_phone_call:
                startActivity(new Intent(this, PhoneCall.class));
                return true;
            case R.id.nav_add_cus:
                startActivity(new Intent(this, AddCustomer.class));
                return true;
            case R.id.nav_show_payment:
                startActivity(new Intent(this, ShowPayment.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetCitiesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.d(LOGTAG, "Running the GetCitiesAsyncTask.onPreExecute method....");
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
            Log.d(LOGTAG, "Running the GetCitiesAsyncTask.doInBackground method....");

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray cities = jsonObj.getJSONArray("cities");

                    // looping through All Cities
                    for (int i = 0; i < cities.length(); i++) {
                        JSONObject c = cities.getJSONObject(i);

                        String id = c.getString("id");
                        String startCity = c.getString("startCity");
                        String endCity = c.getString("endCity");
                        //                        String distance = c.getString("distance");

                        // tmp hash map for single cities
                        HashMap<String, String> cityMap = new HashMap<>();

                        cityMap.put("id", id);
                        cityMap.put("startCity", startCity);
                        cityMap.put("endCity", endCity);

                        // adding cityMap to cityMap list
                        citiesList.add(cityMap);
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
            Log.d(LOGTAG, "Running the GetCitiesAsyncTask.onPostExecute method....");
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

            try {
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MakeTrip.this, android.R.layout.simple_spinner_item, listStartCity);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(dataAdapter);

                dataAdapter = new ArrayAdapter<String>(MakeTrip.this, android.R.layout.simple_spinner_item, listEndCity);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(dataAdapter);
            }
            catch(Exception e){
                Log.e(LOGTAG, "getView onPostExecute Exception: " + e.getMessage());
            }
        }
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTourInfoAsyncTask extends AsyncTask<Void, Void, Void> {
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
            String jsonStr = sh.makeServiceCall(anotherUrl);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray tours = jsonObj.getJSONArray("tourinfo");

                    // looping through All Tours
                    for (int i = 0; i < tours.length(); i++) {
                        JSONObject c = tours.getJSONObject(i);

                        String id = c.getString("id");
                        String startCity = c.getString("startCity");
                        String endCity = c.getString("endCity");
                        String distance = c.getString("distance");


                        // tmp hash map for single cityMap
                        HashMap<String, String> tourMap = new HashMap<>();

                        tourMap.put("id", id);
                        tourMap.put("startCity", startCity);
                        tourMap.put("endCity", endCity);
                        tourMap.put("distance", distance);

                        // Add to the list
                        tourList.add(tourMap);
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
        }
    }
}