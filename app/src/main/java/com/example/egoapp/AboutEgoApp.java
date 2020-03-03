package com.example.egoapp;

import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutEgoApp extends AppCompatActivity implements SearchView.OnQueryTextListener{
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
