package com.example.egoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper  extends SQLiteOpenHelper
{
    private static final String DB_NAME = "city.db";
    private static final int DB_VERSION = 1;

    // Member table constants
    private static final String CITY_TABLE = "city_list";
    private static final String CITY_ID = "city_id";
    private static final int CITY_ID_COL = 0;

    private static final String CITY_NAME = "city_name";
    private static final int CITY_NAME_COL = 1;



    // CREATE and DROP TABLE statements
    private static final String CREATE_CITY_TABLE =
            "CREATE TABLE " + CITY_TABLE + " (" +
                    CITY_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITY_NAME + " TEXT    NOT NULL UNIQUE);";


    private static final String DROP_CITY_TABLE =
            "DROP TABLE IF EXISTS " + CITY_TABLE;

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super( context, name, factory, version );
    }


    private static class DBHelper extends SQLiteOpenHelper {


        // Constructor of the class.
        DBHelper(Context context, String name,
                 SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create tables.
            db.execSQL(CREATE_CITY_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(DataBaseHelper.DROP_CITY_TABLE);
            onCreate(db);
        }
    }

    // Database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;


    /*
     *  Function    : openReadableDB
     *  Description : This method opens a database with read privilege.
     *  Parameter   : N/A
     *  Returns     : N/A
     */
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }


    /*
     *  Function    : openWriteableDB
     *  Description : This method opens a database with write privilege.
     *  Parameter   : N/A
     *  Returns     : N/A
     */
    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }


    /*
     *  Function    : closeDB
     *  Description : This method closes the currently opened database.
     *  Parameter   : N/A
     *  Returns     : N/A
     */
    private void closeDB() {
        if (db != null)
            db.close();
    }


    /*
     *  Function    : closeCursor
     *  Description : This method is used to close cursor.
     *  Parameter   : Cursor        cursor  : cursor that will be closed.
     *  Returns     : N/A
     */
    private void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }


    /*
     *  Function    : insertToTable
     *  Description : This method is used to close cursor.
     *  Parameter   : String cityID  : cursor that will be closed.
     *                String cityID  :
     *  Returns     : N/A
     */
    public boolean insertToTable(String cityID, String cityName){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("1",cityID);
        contentValues.put("Waterloo",cityName);
        contentValues.put("2",cityID);
        contentValues.put("Windsor",cityName);
        contentValues.put("3",cityID);
        contentValues.put("Kitchener",cityName);
        contentValues.put("4",cityID);
        contentValues.put("Cambridge",cityName);
        contentValues.put("5",cityID);
        contentValues.put("Mississauga",cityName);
        contentValues.put("6",cityID);
        contentValues.put("Toronto",cityName);
        contentValues.put("7",cityID);
        contentValues.put("Kingston",cityName);
        contentValues.put("8",cityID);
        contentValues.put("Quebec",cityName);
        db.insert("Your table name",null,contentValues);

        return true;
    }

    /*
     *  Function    : getMemberName
     *  Description : This method is used to get the member name using id.
     *  Parameter   : long      id  : member's id.
     *  Returns     : String: name of member.
     */
    String getMemberName(long id) {
        String where = CITY_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        openReadableDB();
        Cursor cursor = db.query(CITY_TABLE, null,
                where, whereArgs, null, null, null);
        String memberName = "";
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            memberName = cursor.getString(CITY_NAME_COL);
        }
        this.closeCursor(cursor);
        this.closeDB();

        return memberName;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
