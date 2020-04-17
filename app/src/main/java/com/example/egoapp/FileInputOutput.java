//* FILE			: SampleWidgets.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #3
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: April 17, 2020
//* DESCRIPTION		: The file defines widgets for getting trip info

package com.example.egoapp;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileInputOutput {

    //define Logger Class
    private static final String LOGTAG = "FileInputOutput.class";
    public static String TaskArchiveFilename = "taskFileArchive";        // File name of file used to store task information.

    static void WriteToFile (Context context, String filename, JSONObject data) {
        try {
            Log.d(LOGTAG, "Running the WriteToFile method....");
            // Open file, set to append.
            FileOutputStream outputStream = context.openFileOutput(filename, context.MODE_PRIVATE | context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(data.toString() + System.lineSeparator());
            outputStreamWriter.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e(LOGTAG, "WriteToFile", e);
        }

    }


    static JSONArray ReadAll (Context context, String filename) {
        JSONArray retVal = new JSONArray();
        JSONObject buffer;

        try {
            Log.d(LOGTAG, "Running the ReadAll method....");
            InputStream inputStream = context.openFileInput(filename);

            // Make sure file opened successfully.
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                // Continuously reading from file.
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    buffer = new JSONObject(receiveString);
                    // Depending on file name, convert to read String to appropriate json structure.
                    if (filename.equals(TaskArchiveFilename)) {
                        retVal.put(new JSONObject().put("Task", buffer.getJSONArray("Task")));
                    }
                }

                inputStream.close();
            }
        } catch (Exception e) {
            Log.e(LOGTAG, "An error occurred when the ReadAll method was clicked. ", e);
        }


        return retVal;
    }


    static void DeleteFile(Context context, String filename)
    {
        context.deleteFile(filename);
    }
}
