//* FILE			: HttpHandler.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the HttpHandler for JSON Server
package com.example.egoapp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {

    //define Logger Class
    private static final String LOGTAG = "HttpHandler.class";

    public HttpHandler() {
        Log.i(LOGTAG, "Running the HttpHandler method....");
    }

    /*
     * Function: makeServiceCall
     * Description:
     * Input: reqUrl String
     * Return: String
     */
    public String makeServiceCall(String reqUrl) {
        String response = null;
        Log.i(LOGTAG, "makeServiceCall running... ");
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(LOGTAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(LOGTAG, "ProtocolException: " + e.getMessage());

        } catch (IOException e) {
            Log.e(LOGTAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(LOGTAG, "Exception: " + e.getMessage());
        }
        return response;
    }


    /*
     * Function: convertStreamToString
     * Description:
     * Input: InputStream
     * Return: String
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        Log.i(LOGTAG, "convertStreamToString method...");
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(LOGTAG, "IOException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(LOGTAG, "IOException: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
