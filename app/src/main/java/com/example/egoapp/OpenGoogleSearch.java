package com.example.egoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OpenGoogleSearch extends AppCompatActivity {

    private WebView mWebView;
    String myUrl = "https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_google_search);

        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl(myUrl); // URL
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
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
            case R.id.nav_google_search:
                startActivity(new Intent(this, OpenGoogleSearch.class));
                return true;
            case R.id.nav_view_Trip:
                startActivity(new Intent(this, ViewTripOptionActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
