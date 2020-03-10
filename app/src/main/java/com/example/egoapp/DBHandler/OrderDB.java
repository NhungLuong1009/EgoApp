package com.example.egoapp.DBHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OrderDB {
    public static final String  DB_NAME = "Ego.db";
    public static final int     DB_VERSION = 1;

    // ORDER table constants
    public static final String  ORDER_TABLE = "orders";
    public static final String  ORDER_ID = "order_id";
    public static final int     ORDER_ID_COL = 0;
    public static final String  ORDER_ACCOUNT_ID = "account_id";
    public static final int     ORDER_ACCOUNT_ID_COL = 1;
    public static final String  ORDER_DATE = "order_date";
    public static final int     ORDER_DATE_COL = 2;
    public static final String  ORDER_TIME = "order_time";
    public static final int     ORDER_TIME_COL = 3;
    public static final String  ORDER_START_CITY = "start_city";
    public static final int     ORDER_START_CITY_COL = 4;
    public static final String  ORDER_END_CITY = "end_city";
    public static final int     ORDER_END_CITY_COL = 5;
    public static final String  ORDER_ADULT_COUNT = "count_of_adult";
    public static final int     ORDER_ADULT_COUNT_COL = 6;
    public static final String  ORDER_CHILD_COUNT = "count_of_child";
    public static final int     ORDER_CHILD_COUNT_COL = 7;
    public static final String  ORDER_ROUND_TRIP = "round_trip";
    public static final int     ORDER_ROUND_TRIP_COL = 8;
    public static final String  ORDER_MILES = "miles";
    public static final int     ORDER_MILES_COL = 9;

    public static final String CREATE_ORDER_TABLE =
            "CREATE TABLE" + ORDER_TABLE + " (" +
                    ORDER_ID + " INTEGER NOT NULL, " +
                    ORDER_ACCOUNT_ID + " INTEGER NOT NULL" +
                    ORDER_DATE + " TEXT NOT NULL" +
                    ORDER_TIME + " TEXT NOT NULL" +
                    ORDER_START_CITY + " TEXT NOT NULL" +
                    ORDER_END_CITY + " TEXT NOT NULL" +
                    ORDER_ADULT_COUNT + " INTEGER NOT NULL" +
                    ORDER_CHILD_COUNT + " INTEGER NOT NULL" +
                    ORDER_ROUND_TRIP + " NUMERIC NOT NULL" +
                    ORDER_MILES + " NUMERIC NOT NULL" +
                    "FOREIGN KEY (" + ORDER_ACCOUNT_ID +
                    ") REFERENCES account(" + ORDER_ACCOUNT_ID + ")" +
                    ");";

    public static final String DROP_ORDER_TABLE =
            "DROP TABLE IF EXISTS " + ORDER_TABLE;


    /* ------------------------------------------------------------------------------------------------------------------
        CLASS: DBHELPER
        ------------------------------------------------------------------------------------------------------------------
     */
    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // First, create tables
            db.execSQL(CREATE_ORDER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Order", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(OrderDB.DROP_ORDER_TABLE);
            onCreate(db);
        }
    }  // End of DBHelper class
}
