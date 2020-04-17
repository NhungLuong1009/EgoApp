//* FILE			: SampleWidgets.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #3
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: April 17, 2020
//* DESCRIPTION		: The file defines widgets for getting trip info

package com.example.egoapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class SampleWidgets extends AppWidgetProvider {
    //define Logger Class
    private static final String LOGTAG = "SampleWidgets.class";

    /*
     * Function: updateAppWidget
     * Description: update Application widgets function that runs the application
     * Input: Bundle Context context, AppWidgetManager appWidgetManager, int appWidgetId
     * Return: none
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.i(LOGTAG, "Running the Widgets screen....");

        try{
            //Widget's Title
            CharSequence widgetText = context.getString(R.string.appwidget_text);

            //assign the data for widget
            String firstCity = ShareData.mTitle[0] + ": " + ShareData.mDescription[0] + ShareData.mDistance[0] + ", " + ShareData.mTime[0];
            String secondCity = ShareData.mTitle[1] + ": " + ShareData.mDescription[1] + ShareData.mDistance[1] + ", " + ShareData.mTime[1];
            String thirdCity = ShareData.mTitle[2] + ": " + ShareData.mDescription[2] + ShareData.mDistance[2] + ", " + ShareData.mTime[2];
            String forthCity = ShareData.mTitle[3] + ": " + ShareData.mDescription[3] + ShareData.mDistance[3] + ", " + ShareData.mTime[3];

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sample_widgets);
            views.setTextViewText(R.id.appwidget_text, widgetText);
            views.setTextViewText(R.id.appwidget_first_city, firstCity);
            views.setTextViewText(R.id.appwidget_second_city,secondCity);
            views.setTextViewText(R.id.appwidget_third_city, thirdCity);
            views.setTextViewText(R.id.appwidget_forth_city, forthCity);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        catch(Exception e)
        {
            Log.e(LOGTAG, "Widgets Exception: " + e.getMessage());
        }
    }

    /*
     * Function: onUpdate
     * Description: update widgets function that runs the application
     * Input: Bundle Context context, AppWidgetManager appWidgetManager, int appWidgetId
     * Return: none
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    /*
     * Function: onEnabled
     * Description: onEnabled function that runs the application
     * Input: Bundle Context context, AppWidgetManager appWidgetManager, int appWidgetId
     * Return: none
     */
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    /*
     * Function: onDisabled
     * Description: onDisabled function that runs the application
     * Input: Bundle Context context, AppWidgetManager appWidgetManager, int appWidgetId
     * Return: none
     */
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

