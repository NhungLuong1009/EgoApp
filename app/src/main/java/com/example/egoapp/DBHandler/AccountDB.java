//* FILE			: AccountBD.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting account info


package com.example.egoapp.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.egoapp.Object.Account;

import java.util.ArrayList;

public class AccountDB {
    public static final String  DB_NAME = "Ego.db";
    public static final int     DB_VERSION = 2;

    // ACCOUNT table constants
    public static final String  ACCOUNT_TABLE = "account";
    public static final String  ACCOUNT_ID = "account_id";
    public static final int     ACCOUNT_ID_COL = 0;
    public static final String  ACCOUNT_NAME = "account_name";
    public static final int     ACCOUNT_NAME_COL = 1;
    public static final String  ACCOUNT_PHONE = "account_phone_number";
    public static final int     ACCOUNT_PHONE_COL = 2;


    // ACCOUNT_ROLES table constants
    public static final String  ACCOUNT_ROLES_TABLE = "account_roles";
    public static final String  ACCOUNT_ROLES_ID = "account_id";
    public static final int     ACCOUNT_ROLES_ID_COL = 0;
    public static final String  ACCOUNT_ROLES = "roles";
    public static final int     ACCOUNT_ROLES_COL = 1;

    // CREATE and DROP TABLE statements
    public static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE " + ACCOUNT_TABLE + " (" +
                    ACCOUNT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    ACCOUNT_NAME + " TEXT NOT NULL, " +
                    ACCOUNT_PHONE + " TEXT NOT NULL);";

    public static final String CREATE_ACCOUNT_ROLES_TABLE =
            "CREATE TABLE" + ACCOUNT_ROLES_TABLE + " (" +
                    ACCOUNT_ID + " INTEGER NOT NULL, " +
                    ACCOUNT_ROLES + " TEXT NOT NULL" +
                    "FOREIGN KEY (" + ACCOUNT_ID +
                    ") REFERENCES " + ACCOUNT_TABLE + "(" + ACCOUNT_ID + ")" +
                    ");";

    public static final String DROP_ACCOUNT_TABLE =
            "DROP TABLE IF EXISTS " + ACCOUNT_TABLE;

    public static final String DROP_ACCOUNT_ROLES_TABLE =
            "DROP TABLE IF EXISTS " + ACCOUNT_ROLES_TABLE;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // Constructor --------------------------------------------------------
    public AccountDB(Context context) {
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
        long numRows = (long) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM account" , null);
        this.closeDB();
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
        cv.put(ACCOUNT_ID, getAccountRowCount() + 1);
        cv.put(ACCOUNT_NAME, account.getName());
        cv.put(ACCOUNT_PHONE, account.getPhoneNumber());

        this.openWriteableDB();
        long rowID = db.insert(ACCOUNT_TABLE, null, cv);
        this.closeDB();
        return rowID;
    }

    /* public ArrayList<Account> getAccounts(String accountName) {
        ArrayList<Account> accounts = new ArrayList<Account>();
        this.openReadableDB();
        Cursor cursor = db.query(ACCOUNT_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Account account = new Account();
            account.setId(cursor.getInt(ACCOUNT_ID_COL));
            account.setName(cursor.getString(ACCOUNT_NAME_COL));
            account.setEmail(cursor.getString(ACCOUNT_EMAIL_COL));
            account.setPassword(cursor.getString(ACCOUNT_PASSWORD_COL));

            accounts.add(account);
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return accounts;
    }*/



    /*public int updateAccount(Account account) {
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_ID, account.getId());
        cv.put(ACCOUNT_NAME, account.getName());
        cv.put(ACCOUNT_EMAIL, account.getEmail());
        cv.put(ACCOUNT_PASSWORD, account.getPassword());

        String where = ACCOUNT_ID + "= ?";
        String[] whereArgs = { String.valueOf(account.getId()) };
        this.openWriteableDB();
        int rowCount = db.update(ACCOUNT_TABLE, cv, where, whereArgs);
        this.closeDB();
        return rowCount;
    }

    public int deleteAccount(int ID) {
        String where = ACCOUNT_ID + "= ?";
        String[] whereArgs = { String.valueOf(ID) };
        this.openWriteableDB();
        int rowCount = db.delete(ACCOUNT_TABLE, where, whereArgs);
        return rowCount;
    }

    public Account findAccount(String accountName) {
        String where = ACCOUNT_NAME + "= ?";
        String[] whereArgs = { accountName };

        this.openReadableDB();
        Cursor cursor = db.query(ACCOUNT_TABLE, null,
                where, whereArgs, null, null, null);
        Account account = null;
        cursor.moveToFirst();
        account = new Account(cursor.getInt(ACCOUNT_ID_COL),
                cursor.getString(ACCOUNT_NAME_COL), cursor.getString(ACCOUNT_EMAIL_COL), cursor.getString(ACCOUNT_PASSWORD_COL), cursor.getString(ACCOUNT_ROLES_ID_COL));
        this.closeCursor(cursor);
        this.closeDB();

        return account;
    }*/

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
            //db.execSQL(CREATE_ACCOUNT_ROLES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Account", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(AccountDB.DROP_ACCOUNT_TABLE);
            //db.execSQL(AccountDB.DROP_ACCOUNT_ROLES_TABLE);
            onCreate(db);
        }
    }  // End of DBHelper class
}
