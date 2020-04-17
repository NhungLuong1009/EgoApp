//* FILE			: OrderDB.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting order info
package com.example.egoapp.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.egoapp.Object.Orders;

import java.util.ArrayList;

public class OrderDB {
    //define Logger Class
    private static final String LOGTAG = "OrderDB.class";

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
            "CREATE TABLE " + ORDER_TABLE + " (" +
                    ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    ORDER_DATE + " TEXT NOT NULL, " +
                    ORDER_TIME + " TEXT NOT NULL, " +
                    ORDER_START_CITY + " TEXT NOT NULL, " +
                    ORDER_END_CITY + " TEXT NOT NULL, " +
                    ORDER_ADULT_COUNT + " INTEGER NOT NULL, " +
                    ORDER_CHILD_COUNT + " INTEGER NOT NULL, " +
                    ORDER_ROUND_TRIP + " TEXT NOT NULL, " +
                    ORDER_MILES + " REAL NOT NULL" +
                    /*"FOREIGN KEY (" + ORDER_ACCOUNT_ID +
                    ") REFERENCES account(" + ORDER_ACCOUNT_ID + ")" +*/
                    ");";

    public static final String DROP_ORDER_TABLE =
            "DROP TABLE IF EXISTS " + ORDER_TABLE;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // Constructor --------------------------------------------------------
    public OrderDB(Context context) {
        Log.i(LOGTAG, "Running the OrderDB Logic....");
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

    /* =========================================================================================================================*
     * Name		: getOrderRowCount
     * Purpose	: to get the row count of table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long getOrderRowCount()
    {
        this.openReadableDB();
        long numRows = 0;
        try{
            numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + ORDER_TABLE, null);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "getOrderRowCount method Exception: " + e.getMessage());
        }

        return numRows;
    }

    public ArrayList<Orders> getOrders(int orderID) {
        ArrayList<Orders> orders = new ArrayList<Orders>();
        this.openReadableDB();
        Cursor cursor = db.query(ORDER_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Orders order = new Orders();
            order.setOrderID(cursor.getInt(ORDER_ID_COL));
            order.setOrderDate(cursor.getString(ORDER_DATE_COL));
            order.setOrderTime(cursor.getString(ORDER_TIME_COL));
            order.setStartCity(cursor.getString(ORDER_START_CITY_COL));
            order.setEndCity(cursor.getString(ORDER_END_CITY_COL));
            order.setNumOfAdults(cursor.getInt(ORDER_ADULT_COUNT_COL));
            order.setNumOfChild(cursor.getInt(ORDER_CHILD_COUNT_COL));
            order.setRoundTrip(cursor.getString(ORDER_MILES_COL));

            orders.add(order);
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return orders;
    }

    /* =========================================================================================================================*
     * Name		: insertOrder
     * Purpose	: to insert data into order table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long insertOrder(Orders order) {
        ContentValues cv = new ContentValues();
        long rowID = 0;
        try{
            cv.put(ORDER_ID, getOrderRowCount() + 1);
            cv.put(ORDER_DATE, order.getOrderDate());
            cv.put(ORDER_TIME, order.getOrderTime());
            cv.put(ORDER_START_CITY, order.getStartCity());
            cv.put(ORDER_END_CITY, order.getEndCity());
            cv.put(ORDER_ADULT_COUNT, order.getNumOfAdults());
            cv.put(ORDER_CHILD_COUNT, order.getNumOfChild());
            cv.put(ORDER_ROUND_TRIP, order.getRoundTrip());
            cv.put(ORDER_MILES, order.getMiles());

            this.openWriteableDB();
            rowID = db.insert(ORDER_TABLE, null, cv);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "insertOrder method Exception: " + e.getMessage());
        }

        return rowID;
    }

    public int updateOrder(Orders order) {
        ContentValues cv = new ContentValues();
        int rowCount = 0;
        try{
            cv.put(ORDER_ID, order.getOrderID());
            cv.put(ORDER_DATE, order.getOrderDate());
            cv.put(ORDER_TIME, order.getOrderTime());
            cv.put(ORDER_START_CITY, order.getStartCity());
            cv.put(ORDER_END_CITY, order.getEndCity());
            cv.put(ORDER_ADULT_COUNT, order.getNumOfAdults());
            cv.put(ORDER_CHILD_COUNT, order.getNumOfChild());
            cv.put(ORDER_MILES, order.getMiles());

            String where = ORDER_ID + "= ?";
            String[] whereArgs = { String.valueOf(order.getOrderID()) };
            this.openWriteableDB();
            rowCount = db.update(ORDER_TABLE, cv, where, whereArgs);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "insertOrder method Exception: " + e.getMessage());
        }

        return rowCount;
    }

    public int deleteOrder(int ID) {
        int rowCount = 0;
        try{
            String where = ORDER_ID + "= ?";
            String[] whereArgs = { String.valueOf(ID) };
            this.openWriteableDB();
            rowCount = db.delete(ORDER_TABLE, where, whereArgs);
        }
        catch(Exception e){
            Log.e(LOGTAG, "getOrderRowCount method Exception: " + e.getMessage());
        }

        return rowCount;
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
            db.execSQL(CREATE_ORDER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Order", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(DROP_ORDER_TABLE);
            onCreate(db);
        }
    }  // End of DBHelper class
}