//* FILE			: PaymentDB.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting payment info


package com.example.egoapp.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.egoapp.Object.Orders;
import com.example.egoapp.Object.Payment;

public class PaymentDB {
    public static final String  DB_NAME = "Ego.db";
    public static final int     DB_VERSION = 3;

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

    // CREATE and DROP TABLE statements
    public static final String CREATE_PAYMENT_TABLE =
            "CREATE TABLE " + PAYMENT_TABLE + " (" +
                    PAYMENT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    PAYMENT_ORDER_ID + " INTEGER NOT NULL" +
                    PAYMENT_ACCOUNT_ID + " INTEGER NOT NULL " +
                    PAYMENT_PRICE + " REAL NOT NULL " +
                    "FOREIGN KEY (" + PAYMENT_ORDER_ID +
                    ") REFERENCES orders(order_id)" +
                    "FOREIGN KEY (" + PAYMENT_ACCOUNT_ID +
                    ") REFERENCES account(account_id)" +
                    ");";

    public static final String DROP_PAYMENT_TABLE =
            "DROP TABLE IF EXISTS " + PAYMENT_TABLE;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // Constructor --------------------------------------------------------
    public PaymentDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    // Opening Database -------------------------------------------------------------------------------------------------
    private void openReadableDB() { db = dbHelper.getReadableDatabase(); }
    private void openWriteableDB() { db = dbHelper.getWritableDatabase(); }
    private void closeDB() {
        if (db != null) {
            db.close();
        }
    }
    private void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

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
        long numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM account", null);
        this.closeDB();
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
        long numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM orders", null);
        this.closeDB();
        return numRows;
    }

    /* =========================================================================================================================*
     * Name		: getOrderRowCount
     * Purpose	: to get the row count of table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long getPaymentRowCount()
    {
        this.openReadableDB();
        long numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + PAYMENT_TABLE, null);
        this.closeDB();
        return numRows;
    }

    /* =========================================================================================================================*
     * Name		: insertOrder
     * Purpose	: to insert data into order table
     * Inputs	:
     * Outputs	: long
     * Returns	: long
     *===========================================================================================================================*/
    public long insertPayment(Payment payment) {
        ContentValues cv = new ContentValues();
        cv.put(PAYMENT_ID, getPaymentRowCount() + 1);
        cv.put(PAYMENT_ORDER_ID, getOrderRowCount());
        cv.put(PAYMENT_ACCOUNT_ID, getAccountRowCount());
        cv.put(PAYMENT_PRICE, payment.getPriceTotal());

        this.openWriteableDB();
        long rowID = db.insert(PAYMENT_TABLE, null, cv);
        this.closeDB();
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
            db.execSQL(CREATE_PAYMENT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Payment", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(PaymentDB.DROP_PAYMENT_TABLE);
            onCreate(db);
        }
    }  // End of DBHelper class
}
