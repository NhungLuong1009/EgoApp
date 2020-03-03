package com.example.egoapp;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class SearchTrip extends AppCompatActivity implements SearchView.OnQueryTextListener{

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
        setContentView(R.layout.search_trip);

        // Generate sample data

        cityNameList = new String[]{"Toronto", "Ottawa", "Kingston",
                "Mississauga", "Waterloo", "Cambridge", "Kitchener", "Windsor"};

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
}
