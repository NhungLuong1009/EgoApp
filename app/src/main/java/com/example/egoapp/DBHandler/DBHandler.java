package com.example.egoapp.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.egoapp.Object.Account;
import com.example.egoapp.Object.Cities;
import com.example.egoapp.Object.Orders;

public class DBHandler {

    //define Logger Class
    private static final String LOGTAG = "DBHandler.class";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // Constructor --------------------------------------------------------
    public DBHandler(Context context) {
        Log.i(LOGTAG, "Running the DBHandler Logic....");
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

    public static final String  DB_NAME = "Ego.db";
    public static final int     DB_VERSION = 1;

    // ACCOUNT table constants
    public static final String  ACCOUNT_TABLE = "account";
    public static final String  ACCOUNT_ID = "account_id";
    public static final int     ACCOUNT_ID_COL = 0;
    public static final String  ACCOUNT_NAME = "account_name";
    public static final int     ACCOUNT_NAME_COL = 1;
    public static final String  ACCOUNT_PHONE = "account_phone_number";
    public static final int     ACCOUNT_PHONE_COL = 2;

    // CITY table constants
    public static final String  CITY_TABLE = "cities";
    public static final String  CITY_ID = "city_id";
    public static final int     CITY_ID_COL = 0;
    public static final String  CITY_NAME = "city_name";
    public static final int     CITY_NAME_COL = 1;

    // PAYMENT table constants
    public static final String  PAYMENT_TABLE = "payment";
    public static final String  PAYMENT_ID = "pay_id";
    public static final int     PAYMENT_ID_COL = 0;
    public static final String  PAYMENT_ORDER_ID = "order_id";
    public static final int     PAYMENT_ORDER_ID_COL = 1;
    public static final String  PAYMENT_ACCOUNT_ID = "account_id";
    public static final int     PAYMENT_ACCOUNT_ID_COL = 2;
    public static final String  PAYMENT_PRICE = "total_price";
    public static final int     PAYMENT_PRICE_COL = 3;
    public static final String  PAYMENT_TYPE = "pay_type";
    public static final int     PAYMENT_TYPE_COL = 4;

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



    // CREATE and DROP TABLE statements
    public static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE " + ACCOUNT_TABLE + " (" +
                    ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    ACCOUNT_NAME + " TEXT NOT NULL, " +
                    ACCOUNT_PHONE + " TEXT NOT NULL);";

    public static final String CREATE_CITY_TABLE =
            "CREATE TABLE " + CITY_TABLE + " (" +
                    CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    CITY_NAME + " INTEGER NOT NULL);";

    public static final String CREATE_PAYMENT_TABLE =
            "CREATE TABLE " + PAYMENT_TABLE + " (" +
                    PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    PAYMENT_ORDER_ID + " INTEGER NOT NULL, " +
                    PAYMENT_ACCOUNT_ID + " INTEGER NOT NULL, " +
                    PAYMENT_PRICE + " INTEGER NOT NULL, " +
                    PAYMENT_TYPE +  " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + PAYMENT_ORDER_ID +
                    ") REFERENCES orders(order_id), " +
                    "FOREIGN KEY (" + PAYMENT_ACCOUNT_ID +
                    ") REFERENCES account(account_id)" +
                    ");";

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


    public static final String DROP_ACCOUNT_TABLE =
            "DROP TABLE IF EXISTS " + ACCOUNT_TABLE;

    public static final String DROP_CITY_TABLE =
            "DROP TABLE IF EXISTS " + CITY_TABLE;

    public static final String DROP_PAYMENT_TABLE =
            "DROP TABLE IF EXISTS " + PAYMENT_TABLE;

    public static final String DROP_ORDER_TABLE =
            "DROP TABLE IF EXISTS " + ORDER_TABLE;

    // Function for DB --------------------------------------------------------------------------------------------------
    /* =========================================================================================================================*
     * Name		: getAccountRowCount
     * Purpose	: to get the row count of table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long getAccountRowCount()
    {
        this.openReadableDB();
        long numRows = 0;
        try{
            numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM account", null);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "getAccountRowCount method Exception: " + e.getMessage());
        }

        return numRows;
    }

    /* =========================================================================================================================*
     * Name		: getAccountRowCount
     * Purpose	: to get the row count of table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long getCityRowCount()
    {
        this.openReadableDB();
        long numRows = 0;
        try{
            numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM cities", null);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "getCityRowCount method Exception: " + e.getMessage());
        }

        return numRows;
    }

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
            numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM orders", null);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "getOrderRowCount method Exception: " + e.getMessage());
        }

        return numRows;
    }

    /* =========================================================================================================================*
     * Name		: insertAccount
     * Purpose	: to insert data into account table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long insertAccount(Account account) {
        ContentValues cv = new ContentValues();
        long rowID = 0;
        try{
            cv.put(ACCOUNT_ID, getAccountRowCount() + 1);
            cv.put(ACCOUNT_NAME, account.getName());
            cv.put(ACCOUNT_PHONE, account.getPhoneNumber());

            this.openWriteableDB();
            rowID = db.insert(ACCOUNT_TABLE, null, cv);
            this.closeDB();
        }
        catch(Exception e){
            Log.e(LOGTAG, "insertAccount method Exception: " + e.getMessage());
        }

        return rowID;
    }

    /* =========================================================================================================================*
     * Name		: insertCity
     * Purpose	: to insert data into city table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long insertCity(Cities city) {
        ContentValues cv = new ContentValues();
        long rowID = 0;
        try{
            cv.put(CITY_ID, getCityRowCount() + 1);
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

    /* =========================================================================================================================*
     * Name		: insertOrder
     * Purpose	: to insert data into order table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long insertOrder(Orders order) {
        ContentValues cv = new ContentValues();
        //cv.put(ORDER_ID, getOrderRowCount() + 1);
        long rowID = 0;
        try{
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
            db.execSQL(CREATE_ACCOUNT_TABLE);
            db.execSQL(CREATE_CITY_TABLE);
            db.execSQL(CREATE_PAYMENT_TABLE);
            db.execSQL(CREATE_ORDER_TABLE);

            // insert default lists
            db.execSQL("INSERT INTO city VALUES (1, 'Toronto')");
            db.execSQL("INSERT INTO city VALUES (2, 'Ottawa')");
            db.execSQL("INSERT INTO orders (order_id, order_date, order_time, start_city, end_city, count_of_adult, count_of_child, round_trip, miles) VALUES " +
                    "(1, '12-03-2020', '12:03', 'Toronto', 'Waterloo', 2, 3, 'OneWay', 50.00)");
            db.execSQL("INSERT INTO orders (order_id, order_date, order_time, start_city, end_city, count_of_adult, count_of_child, round_trip, miles) VALUES " +
                    "(2, '15-04-2020', '12:03', 'Toronto', 'Ottawa', 2, 3, 'RoundTrip', 150.00)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Account", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(DROP_ACCOUNT_TABLE);
            db.execSQL(DROP_CITY_TABLE);
            db.execSQL(DROP_ORDER_TABLE);
            db.execSQL(DROP_PAYMENT_TABLE);

            onCreate(db);
        }
    }  // End of DBHelper class
}
