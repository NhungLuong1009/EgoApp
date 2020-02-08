/*
 * File: MakeTrip.java
 * Name: Trung Nguyen - Abdullah - Nhung Luong - Huynchul Choi
 * Date: 08 Feb, 2020
 * Description: contains the back end of the make trip screen for Trip Planner App
 */
package com.example.egoapp;

import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.text.InputType;
import android.widget.EditText;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;



public class MakeTrip  extends AppCompatActivity
    implements View.OnClickListener
{

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_trip);

     Toast myToast = Toast.makeText(getApplicationContext(), "we are in layout 2 and the new value is " + ShareData.tripNumberOfPassenger + " and " + ShareData.something2, Toast.LENGTH_SHORT);
      myToast.show();

        // Start city spinner
        final Spinner spinner1 = (Spinner) findViewById(R.id.listStartCity);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Cities, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);


        // End city spinner
        final Spinner spinner2 = (Spinner) findViewById(R.id.listEndCity);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Cities, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);


        // Make trip button
        Button myBtn = findViewById(R.id.makeTripBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText selectedDate = findViewById(R.id.in_date);
                EditText selectedTime = findViewById(R.id.in_time);
                EditText selectedNumberOfPassenger = findViewById(R.id.passenger_edittext);
                RadioGroup selectedRoundtripButton = findViewById(R.id.roundtrip_radioGroup);


                ShareData.tripSelectedStartCity = spinner1.getSelectedItem().toString();
                ShareData.tripSelectedEndCity = spinner2.getSelectedItem().toString();

                ShareData.tripSelectedDate = selectedDate.getText().toString();
                ShareData.tripSelectedTime = selectedTime.getText().toString();

                ShareData.tripNumberOfPassenger = selectedNumberOfPassenger.getText().toString();
                int selectedButtonId = selectedRoundtripButton.getCheckedRadioButtonId();
                if(selectedButtonId == 0)
                {
                    ShareData.tripSelectedRoundTripOption = true;
                }
                else
                {
                    ShareData.tripSelectedRoundTripOption = false;
                }


                Intent myIntent = new Intent(MakeTrip.this, DisplayTrip.class);
                startActivity(myIntent);
            }
        });


        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


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

}
