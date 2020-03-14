//* FILE			: PaymentDB.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting payment info


package com.example.egoapp.DBHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PaymentDB {
    public static final String  DB_NAME = "Ego.db";
    public static final int     DB_VERSION = 1;

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

    // CREATE and DROP TABLE statements
    public static final String CREATE_PAYMENT_TABLE =
            "CREATE TABLE " + PAYMENT_TABLE + " (" +
                    PAYMENT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    PAYMENT_ORDER_ID + " INTEGER NOT NULL" +
                    PAYMENT_ACCOUNT_ID + " INTEGER NOT NULL " +
                    PAYMENT_PRICE + " INTEGER NOT NULL " +
                    PAYMENT_TYPE +  " INTEGER NOT NULL " +
                    "FOREIGN KEY (" + PAYMENT_ORDER_ID +
                    ") REFERENCES orders(order_id)" +
                    "FOREIGN KEY (" + PAYMENT_ACCOUNT_ID +
                    ") REFERENCES account(account_id)" +
                    ");";

    public static final String DROP_PAYMENT_TABLE =
            "DROP TABLE IF EXISTS " + PAYMENT_TABLE;



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
