//* FILE			: MakeTrip.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info

package com.example.egoapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.EditText;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;

public class MakeTrip  extends AppCompatActivity
    implements View.OnClickListener
{

    Button btnDatePicker, btnTimePicker;
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

        // Get ID from 2 Spinner (Start City & End City)
        spinner1 = (Spinner) findViewById(R.id.listStartCity);
        spinner2 = (Spinner) findViewById(R.id.listEndCity);

        // Load Array into 2 seperate spinner
        String[] allAvailableCities = getResources().getStringArray(R.array.Cities);
        // ******************************** SPINNER **************************************** //
        // Start city spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Cities, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        // End city spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Cities, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        // Check if user click on default trip on screen
        if(ShareData.makeOwnTrip == false)
        {
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
