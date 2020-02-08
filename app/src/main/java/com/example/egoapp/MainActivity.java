/*
 * File: MainActivity.java
 * Name: Trung Nguyen - Abdullah - Nhung Luong - Huynchul Choi
 * Date: 08 Feb, 2020
 * Description: contains the back end of the first screen for Trip Planner App
 */
package com.example.egoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ListView listView_tripList;

    /*
     * Function: onCreate
     * Description: initial function that runs the application
     * Input: Bundle savedInstanceState
     * Return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Debug
        Toast myToast = Toast.makeText(getApplicationContext(), "hello there how are you", Toast.LENGTH_SHORT);
        myToast.show();

        // Make a trip by default
        // Get ID from the listtView for Listener
        listView_tripList = findViewById(R.id.listView_tripList);
        // Set new Adapter
        TripAdapter tripAdapter = new TripAdapter(this, ShareData.mTitle, ShareData.mDescription, ShareData.images);
        listView_tripList.setAdapter(tripAdapter);
        // Listener Event
        listView_tripList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Item 1
                if (position == 0)
                {
                    Intent intent = new Intent(getApplicationContext(), MakeTrip.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", ShareData.images[0]);
                    intent.putExtras(bundle);
                    // now put title and description to another activity
                    intent.putExtra("title", ShareData.mTitle[0]);
                    intent.putExtra("description", ShareData.mDescription[0]);
                    // also put your position
                    intent.putExtra("position", ""+0);
                    startActivity(intent);
                }
                // Item 2
                if (position == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), MakeTrip.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", ShareData.images[1]);
                    intent.putExtras(bundle);
                    // now put title and description to another activity
                    intent.putExtra("title", ShareData.mTitle[1]);
                    intent.putExtra("description", ShareData.mDescription[1]);
                    // also put your position
                    intent.putExtra("position", ""+1);
                    startActivity(intent);
                }
                // Item 3
                if (position == 2)
                {
                    Intent intent = new Intent(getApplicationContext(), MakeTrip.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", ShareData.images[2]);
                    intent.putExtras(bundle);
                    // now put title and description to another activity
                    intent.putExtra("title", ShareData.mTitle[2]);
                    intent.putExtra("description", ShareData.mDescription[2]);
                    // also put your position
                    intent.putExtra("position", ""+2);
                    startActivity(intent);
                }
                // Item 4
                if (position == 3)
                {
                    Intent intent = new Intent(getApplicationContext(), MakeTrip.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", ShareData.images[3]);
                    intent.putExtras(bundle);
                    // now put title and description to another activity
                    intent.putExtra("title", ShareData.mTitle[3]);
                    intent.putExtra("description", ShareData.mDescription[3]);
                    // also put your position
                    intent.putExtra("position", ""+3);
                    startActivity(intent);
                }
                // Item 5
                if (position == 4)
                {
                    Intent intent = new Intent(getApplicationContext(), MakeTrip.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", ShareData.images[4]);
                    intent.putExtras(bundle);
                    // now put title and description to another activity
                    intent.putExtra("title", ShareData.mTitle[4]);
                    intent.putExtra("description", ShareData.mDescription[4]);
                    // also put your position
                    intent.putExtra("position", ""+4);
                    startActivity(intent);
                }
            } // Override listener
        });

        /* ----------------------------------------------------------------------------------------------------------------- */
        // Make an own trip
        Button myBtn = findViewById(R.id.mainBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, MakeTrip.class);
                startActivity(myIntent);
            }
        });

    }

    /* --------------------------------NEW CLASS ------------------------------*/
    class TripAdapter extends ArrayAdapter<String>{

        Context context;
        String rTitle[];
        String rDescription[];
        int rImages[];

        TripAdapter(Context c, String title[], String description[], int imgs[])
        {
            super(c, R.layout.trip_selection, R.id.textView_title, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImages = imgs;
        }

        /*
         * Function: getView
         * Description: operation for the listview functionality
         * Input: position, view, viewGroup
         * Output: View
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.trip_selection, parent, false);

            ImageView images = row.findViewById(R.id.first_img);
            TextView myTitle = row.findViewById(R.id.textView_title);
            TextView myDescription = row.findViewById(R.id.textView_description);

            images.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }
}
