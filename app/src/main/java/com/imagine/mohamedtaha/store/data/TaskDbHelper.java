package com.imagine.mohamedtaha.store.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;

/**
 * Created by MANASATT on 25/11/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    //the name of the database
    private static final String DATABASE_NAME = "stores.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION =1;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    /**
     * Called when the stores database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create  tables (careful to follow sQL formatting rules)
        //Table Caregory
        final String CREATE_TABLE_CATEGORIES =
                "CREATE TABLE "                + TaskEntry.TABLE_CATEGORIES  + " ("                 + TaskEntry._ID    +
                    " INTEGER PRIMARY KEY, "   + TaskEntry.KEY_NAME_CATEGORY + " TEXT NOT NULL, "   +
                TaskEntry.KEY_NATURAL_CATEGORY + " TEXT, "                   + TaskEntry.KEY_NOTES  + " TEXT,"         +
                        TaskEntry.KEY_USER_ID  + " INTEGER,"                 + TaskEntry.KEY_DATE   + " DATETIME"      + ")" ;
        //Table Stores
        final String CREATE_TABLE_STORES =
                "CREATE TABLE "         + TaskEntry.TABLE_STORE + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY," +
               TaskEntry.KEY_TYPE_STORE + " TEXT NOT NULL,"     + TaskEntry.KEY_NOTES  +                " TEXT," +
                  TaskEntry.KEY_USER_ID + " INTEGER,"           +   TaskEntry.KEY_DATE + " DATETIME"             + ")";
        //Table Permission
        final String CREATE_TABLE_PERMISSION =
                "CREATE TABLE "                + TaskEntry.TABLE_PERMISSION + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY,"+
                 TaskEntry.KEY_NAME_PERMISSION + " TEXT NOT NULL,"          + TaskEntry.KEY_NOTES  + " TEXT,"               +
                        TaskEntry.KEY_USER_ID  + " INTEGER,"                + TaskEntry.KEY_DATE   + " DATETIME "           +  ")";
        //Table Users
        final String CREATE_TABLE_USERS =
                "CREATE TABLE " + TaskEntry.TABLE_USERS + " (" +TaskEntry._ID + " INTEGER PRIMARY KEY," + TaskEntry.KEY_NAME_USER +
                        " TEXT NOT NULL," + TaskEntry.KEY_PASSWORD_USERS + " TEXT NOT NULL," + TaskEntry.KEY_RETRY_PASSWORD       +
                        " TEXT NOT NULL," + TaskEntry.KEY_MOBILE + " TEXT NOT NULL," + TaskEntry.KEY_EMAIL + " TEXT," +
                        TaskEntry.KEY_DATE +        " DATETIME"  + ")";
        //Table STOCKING_WAREHOUSE
        final String CREATE_TABLE_STOCKING_WAREHOUSE =
                "CREATE TABLE "            + TaskEntry.TABLE_STOCKING_WAREHOUSE   + " (" +TaskEntry._ID + " INTEGER PRIMARY KEY," +
                TaskEntry.KEY_CATEGORY_ID  + " INTEGER," + TaskEntry.KEY_STORE_ID + " INTEGER,"         +
               TaskEntry.KEY_FIRST_BALANCE + " INTEGER," + TaskEntry.KEY_NOTES    + " TEXT,"            + TaskEntry.KEY_USER_ID   +
                        " INTEGER,"        + TaskEntry.KEY_DATE                   + " DATETIME"         + ")";

        //Table DAILY_MOVEMENTS
        final String CREATE_TABLE_DAILY_MOVEMENTS =
           "CREATE TABLE "             + TaskEntry.TABLE_DAILY_MOVEMENTS + " (" + TaskEntry._ID       + " INTEGER PRIMARY KEY," +
           TaskEntry.KEY_CATEGORY_ID   + " INTEGER,"                     + TaskEntry.KEY_STORE_ID     + " INTEGER,"             +
           TaskEntry.KEY_PERMISSION_ID + " INTEGER,"                     + TaskEntry.KEY_INCOMING     + " INTEGER,"             +
           TaskEntry.KEY_ISSUED        + " INTEGER,"                     + TaskEntry.KEY_CONVERT_FROM + " TEXT,"                +
           TaskEntry.KEY_CONVERT_TO    + " TEXT,"                        +TaskEntry.KEY_USER_ID       +" INTEGER,"              +
           TaskEntry.KEY_DATE          + " DATETIME"                     + ")";

        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_STORES);
        db.execSQL(CREATE_TABLE_PERMISSION);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STOCKING_WAREHOUSE);
        db.execSQL(CREATE_TABLE_DAILY_MOVEMENTS);


    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_PERMISSION);
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_STOCKING_WAREHOUSE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_DAILY_MOVEMENTS);
        onCreate(db);

    }
}


















