package com.example.egoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchTripActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    String[] cityNameList;
    ArrayList<CityNames> arraylist = new ArrayList<CityNames>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        // Generate sample data

        cityNameList = new String[]{"Toronto", "Ottawa", "Kingston",
                "Mississauga", "Waterloo", "Cambridge", "Kitchener", "Windsor"};

        new LoadCityList().execute();
        new GetConnectionStatus().execute();


    }

    private void loadBackgroundSearch()
    {
        // Locate the ListView in list_view_main.xml
        list = (ListView) findViewById(R.id.listView);

        for (int i = 0; i < cityNameList.length; i++) {
            CityNames animalNames = new CityNames(cityNameList[i]);
            // Binds all strings into an array
            arraylist.add(animalNames);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in list_view_main.xml
        editsearch = (SearchView) findViewById(R.id.searchCity);
        editsearch.setOnQueryTextListener(this);


        SearchView searchBar= findViewById(R.id.searchCity);
        searchBar.setVisibility(View.VISIBLE);
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }





    class GetConnectionStatus extends AsyncTask<Boolean, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean values) {
            super.onPostExecute(values);

            String result = "Cannot connected to network!";
            int ip = 0;
            String ipString = "";
            if (values)
            {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo;

                wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                    ip = wifiInfo.getIpAddress();
//                  String networkName = wifiInfo.getSSID();
                    ipString = String.format("%d.%d.%d.%d”", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
                }
                result = "Network connected to " + ipString;
            }
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected Boolean doInBackground(Boolean... voids) {


            ConnectivityManager conManager = (ConnectivityManager)
                    getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conManager.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    double  percentageScale;
    ProgressBar pb;
    double howManyTaskList;

    class LoadCityList extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... values) {


            int defaultSize = cityNameList.length;
            percentageScale = (double)(100/defaultSize);
            pb = findViewById(R.id.progressBar);
            for(int i = 0 ; i < defaultSize; i++)
            {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                howManyTaskList = percentageScale*(i+1);
                publishProgress((int)howManyTaskList);
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // After load all cities
            loadBackgroundSearch();
            pb.setVisibility(View.GONE);
        }
    }
}
