package com.imagine.mohamedtaha.store.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.ItemStokeHouse;
import com.imagine.mohamedtaha.store.ItemsPermission;
import com.imagine.mohamedtaha.store.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by MANASATT on 25/11/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    //the name of the database
    private static final String DATABASE_NAME = "stores.db";
    Context mContext;

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
    //get datetime
    public static String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }
    //_____________________________Methods Store____________________________

    //_____________________________Insert Table Store____________________________
    public long addStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_TYPE_STORE,itemsStore.getTypeStore());
        values.put(TaskEntry.KEY_NOTES ,itemsStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDateTime());
        //Insert row
        long store_id = db.insert(TaskEntry.TABLE_STORE,null,values);
        return store_id;
    }

    //____________________Update Store______________________
    public int updateStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_TYPE_STORE,itemsStore.getTypeStore());
        values.put(TaskEntry.KEY_NOTES,itemsStore.getNotes());
        values.put(TaskEntry.KEY_DATE,getDateTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsStore.getId())};
        //updating row
        return db.update(TaskEntry.TABLE_STORE,values, selection,selectionArgs);

    }
    //____________________Delete Store____________________________
    public void deleteStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();

            db.delete(TaskEntry.TABLE_STORE, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsStore.getId())});

            }


    //___________________________get All Stores___________________
    public ArrayList<ItemsStore>getAllItemsStore(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsStores = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_TYPE_STORE,TaskEntry.KEY_DATE};
        Cursor c = db.query(TaskEntry.TABLE_STORE, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsStore = new ItemsStore();
            itemsStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsStore.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemsStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to tagd list
            itemsStores.add(itemsStore);
        }
        return itemsStores;
    }
    //_____________________________Methods Permissions____________________________

    //___________________________________Add PErmission___________________________________

    public long addPErmission(ItemsPermission itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_NAME_PERMISSION,itemsPermission.getNamePermission());
        values.put(TaskEntry.KEY_NOTES ,itemsPermission.getNotes());
        values.put(TaskEntry.KEY_DATE, getDateTime());
        //Insert row
        long store_id = db.insert(TaskEntry.TABLE_PERMISSION,null,values);
        return store_id;
    }

    //get All Permission
    public ArrayList<ItemsPermission>getAllItemsPermission(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsPermission> itemsPermissions = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_NAME_PERMISSION,TaskEntry.KEY_DATE};
        Cursor c = db.query(TaskEntry.TABLE_PERMISSION, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsPermission itemsPermission = new ItemsPermission();
            itemsPermission.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsPermission.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemsPermission.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to tagd list
            itemsPermissions.add(itemsPermission);
        }
        return itemsPermissions;
    }
    //____________________Update Permission______________________
    public int updatePermission(ItemsPermission itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_NAME_PERMISSION,itemsPermission.getNamePermission());
        values.put(TaskEntry.KEY_NOTES,itemsPermission.getNotes());
        values.put(TaskEntry.KEY_DATE,getDateTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsPermission.getId())};
        //updating row
        return db.update(TaskEntry.TABLE_PERMISSION,values, selection,selectionArgs);

    }
    //____________________Delete Permission____________________________
    public void deletePermission(ItemsPermission itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TaskEntry.TABLE_PERMISSION, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsPermission.getId())});

    }

    //_____________________________Methods StokeWareHouse____________________________

    //___________________________________Add StokeWareHouse____________________________

    public long addStokeWarehouse(ItemStokeHouse itemStokeHouse){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_CATEGORY_ID,itemStokeHouse.getId_code_category());
        values.put(TaskEntry.KEY_STORE_ID ,itemStokeHouse.getId_code_store());
        values.put(TaskEntry.KEY_FIRST_BALANCE,itemStokeHouse.getFirst_balanse());
        values.put(TaskEntry.KEY_NOTES,itemStokeHouse.getNotes());
        values.put(TaskEntry.KEY_DATE, getDateTime());
        //Insert row
        long stokewarehouse_id = db.insert(TaskEntry.TABLE_STOCKING_WAREHOUSE,null,values);
        return stokewarehouse_id;
    }
    //____________________get All StokeWareHouse____________________________

    public ArrayList<ItemStokeHouse>getAllItemsStokeWarehouse(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemStokeHouse> itemStokeHouses = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_CATEGORY_ID,TaskEntry.KEY_STORE_ID,TaskEntry.KEY_FIRST_BALANCE,TaskEntry.KEY_DATE};
        Cursor c = db.query(TaskEntry.TABLE_STOCKING_WAREHOUSE, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemStokeHouse itemStokeHouse = new ItemStokeHouse();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setId_code_category(c.getLong(c.getColumnIndex(TaskEntry.KEY_CATEGORY_ID)));
            itemStokeHouse.setId_code_store(c.getLong(c.getColumnIndex(TaskEntry.KEY_STORE_ID)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            itemStokeHouse.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to tagd list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }

    //____________________get All Items StokeWarehouse With Category Name and StoreName____________________________

    public ArrayList<ItemStokeHouse>getAllItemsStokeWarehouseWithCategoruNameandStoreName(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<ItemStokeHouse> itemStokeHouses = new ArrayList<>();
            String []columns = {TaskEntry._ID,TaskEntry.KEY_NAME_CATEGORY,TaskEntry.KEY_TYPE_STORE,TaskEntry.KEY_FIRST_BALANCE,TaskEntry.KEY_DATE};

            String selection = TaskEntry._ID + TaskEntry.KEY_NAME_CATEGORY + TaskEntry.KEY_TYPE_STORE + TaskEntry.KEY_FIRST_BALANCE +TaskEntry.KEY_DATE + "=?";
            String[] selectionArgs =new String[]{String.valueOf(itemsPermission.getId())};

        Cursor c = db.query(TaskEntry.TABLE_STOCKING_WAREHOUSE, columns,selection,selectionArgs,null,null,null);
            //Looping through all rows and adding to list
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                ItemStokeHouse itemStokeHouse = new ItemStokeHouse();
                itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
                itemStokeHouse.setId_code_category(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY));
                itemStokeHouse.setId_code_store(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE));
                itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
                itemStokeHouse.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
                //adding to tagd list
                itemStokeHouses.add(itemStokeHouse);
            }
            return itemStokeHouses;
        }


        //____________________getting all StokeWareHouse under Store and Category____________________________

    public ArrayList<ItemStokeHouse>getAllStokeHouseByCategoryAndStory(String type_name){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemStokeHouse> itemStokeHouses = new ArrayList<ItemStokeHouse>();
        String selectQuery = "SELECT * FROM " + TaskEntry.TABLE_CATEGORIES + " tc, "
                + TaskEntry.TABLE_STORE + " ts, " + TaskEntry.TABLE_STOCKING_WAREHOUSE + " tt WHERE ts."
                +TaskEntry.KEY_TYPE_STORE + " = '" + type_name + "'" + " AND ts. " + TaskEntry._ID
                + " = " + "tt." + TaskEntry.KEY_STORE_ID + " AND tc." + TaskEntry._ID + " = "
                + "tt." + TaskEntry.KEY_NAME_CATEGORY;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemStokeHouse itemStokeHouse = new ItemStokeHouse();
            itemStokeHouse.setId(c.getInt((c.getColumnIndex(TaskEntry._ID))));
           // itemStokeHouse.set(c.getString(c.getColumnIndex(KEY_NOTE)));
            itemStokeHouse.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }
    //____________________Update StokeWareHouse____________________________
    public int updateStokeWarehouse(ItemsPermission itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_NAME_PERMISSION,itemsPermission.getNamePermission());
        values.put(TaskEntry.KEY_NOTES,itemsPermission.getNotes());
        values.put(TaskEntry.KEY_DATE,getDateTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsPermission.getId())};
        //updating row
        return db.update(TaskEntry.TABLE_PERMISSION,values, selection,selectionArgs);

    }
    //____________________Delete StokeWareHouse____________________________
    public void deleteStokeWareHouse(ItemsPermission itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TaskEntry.TABLE_PERMISSION, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsPermission.getId())});

    }
public ArrayList<String> getDataForSpinnerCategory(){
    SQLiteDatabase db = this.getReadableDatabase();

    ArrayList<String> IDCategory =new ArrayList<String>();
    String selectQuery = "SELECT * FROM " + TaskEntry.TABLE_CATEGORIES;
    Cursor c = db.rawQuery(selectQuery,null);
    for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
        IDCategory.add(c.getString(1));
    }
    return IDCategory;
}
public ArrayList<String> getDataForSpinnerStore(){
    SQLiteDatabase db = this.getReadableDatabase();

    ArrayList<String> IDStore = new ArrayList<>();
    String selectQuery = "SELECT * FROM " +TaskEntry.TABLE_STORE;
    Cursor c = db.rawQuery(selectQuery,null);
    for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
        IDStore.add(c.getString(1));
    }
    return IDStore;
}
}





























