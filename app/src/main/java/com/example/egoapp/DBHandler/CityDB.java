//* FILE			: CityDB.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info

package com.example.egoapp.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.egoapp.Object.Cities;
import java.util.ArrayList;

public class CityDB {
    //define Logger Class
    private static final String LOGTAG = "CityDB.class";

    public static final String  DB_NAME = "Ego.db";
    public static final int     DB_VERSION = 1;

    // ORDER table constants
    public static final String  CITY_TABLE = "cities";
    public static final String  CITY_ID = "city_id";
    public static final int     CITY_ID_COL = 0;
    public static final String  CITY_NAME = "city_name";
    public static final int     CITY_NAME_COL = 1;

    public static final String CREATE_CITY_TABLE =
            "CREATE TABLE " + CITY_TABLE + " (" +
                    CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    CITY_NAME + " INTEGER NOT NULL);";

    public static final String DROP_CITY_TABLE =
            "DROP TABLE IF EXISTS " + CITY_TABLE;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public CityDB(Context context) {
        Log.i(LOGTAG, "Running the CityDB Logic....");
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    // Opening Database -------------------------------------------------------------------------------------------------
    private void openReadableDB() {
        try{
            db = dbHelper.getReadableDatabase();
        }
        catch(Exception e){
            Log.e(LOGTAG, "openReadableDB method Exception: " + e.getMessage());
        }
    }

    private void openWriteableDB() {
        try{
            db = dbHelper.getWritableDatabase();
        }
        catch(Exception e){
            Log.e(LOGTAG, "openWriteableDB method Exception: " + e.getMessage());
        }
    }

    private void closeDB() {
        if (db != null) {
            db.close();
        }
    }
    private void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

    // Function for DB --------------------------------------------------------------------------------------------------
    public ArrayList<Cities> getCityName(int cityID) {
        ArrayList<Cities> cities = new ArrayList<Cities>();
        this.openReadableDB();
        Cursor cursor = db.query(CITY_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Cities city = new Cities();
            city.setCityID(cursor.getInt(CITY_ID_COL));
            city.setCityName(cursor.getString(CITY_NAME_COL));
            cities.add(city);
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return cities;
    }

    public long insertCity(Cities city) {
        ContentValues cv = new ContentValues();
        long rowID = 0;
        try{
            cv.put(CITY_ID, getCityIDCount() + 1);
            cv.put(CITY_NAME, city.getCityName());

            this.openWriteableDB();
            rowID = db.insert(CITY_TABLE, null, cv);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "insertCity method Exception: " + e.getMessage());
        }

        return rowID;
    }

    public Cities findCity(String cityName) {
        String where = CITY_NAME + "= ?";
        String[] whereArgs = { cityName };

        this.openReadableDB();
        Cursor cursor = db.query(CITY_TABLE, null,
                where, whereArgs, null, null, null);
        Cities city = null;

        if(cursor==null) {
            return city;
        }
        else if (!cursor.moveToFirst()) {
            cursor.close();
            return city;
        }
        else{
            cursor.moveToFirst();
            city = new Cities(cursor.getInt(CITY_ID_COL),
                    cursor.getString(CITY_NAME_COL));
        }

        this.closeCursor(cursor);
        this.closeDB();

        return city;
    }

    public long getCityIDCount() {
        this.openReadableDB();
        long numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + CITY_TABLE, null);
        this.closeDB();

        return numRows;
    }

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
            db.execSQL(CREATE_CITY_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Order", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(CityDB.DROP_CITY_TABLE);
            onCreate(db);
        }
    }  // End of DBHelper class
}