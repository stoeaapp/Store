package com.imagine.mohamedtaha.store.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MANASATT on 25/11/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    //the name of the database
    public static final String DATABASE_NAME = "stores.db";
    Context mContext;

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 5;

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
                "CREATE TABLE "                    + TaskEntry.TABLE_CATEGORIES  + " ("                 + TaskEntry._ID    +
                        " INTEGER PRIMARY KEY, "   + TaskEntry.KEY_NAME_CATEGORY + " TEXT NOT NULL, "   +
                        TaskEntry.KEY_NATURAL_CATEGORY + " TEXT, "               + TaskEntry.KEY_NOTES  + " TEXT,"         +
                        TaskEntry.KEY_USER_ID      + " INTEGER,"                 + TaskEntry.KEY_DATE   + " DATE,"         +
                        TaskEntry.KEY_TIME         + " TIME"                     + ")" ;
        //Table Stores
        final String CREATE_TABLE_STORES =
                "CREATE TABLE "         + TaskEntry.TABLE_STORE + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY,"   +
                        TaskEntry.KEY_TYPE_STORE + " TEXT NOT NULL,"     + TaskEntry.KEY_NOTES  + " TEXT NOT NULL,"                  +
                        TaskEntry.KEY_USER_ID    + " INTEGER,"           + TaskEntry.KEY_DATE   + " DATE,"                  +
                        TaskEntry.KEY_TIME    + " TIME"+ ")";
        //Table Convert
        final String CREATE_TABLE_CONVERT_STORE =
                "CREATE TABLE " + TaskEntry.TABLE_CONVERT_STORE + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY," +
                        TaskEntry.KEY_TYPE_STORE_CONVERT        + " TEXT NOT NULL,"    + TaskEntry.KEY_NOTES     +                " TEXT," +
                        TaskEntry.KEY_USER_ID                   + " INTEGER,"          +   TaskEntry.KEY_DATE    +
                        " DATE,"                                +TaskEntry.KEY_TIME    + " TIME"                 + ")";

        //Table Permission
        final String CREATE_TABLE_PERMISSION =
                "CREATE TABLE "                + TaskEntry.TABLE_PERMISSION    + " ("                  + TaskEntry._ID +
                        " INTEGER PRIMARY KEY,"+ TaskEntry.KEY_NAME_PERMISSION + " TEXT NOT NULL,"     +
                        TaskEntry.KEY_NOTES    + " TEXT,"                      +TaskEntry.KEY_USER_ID  + " INTEGER,"
                        + TaskEntry.KEY_DATE   + " DATE, "                     +TaskEntry.KEY_TIME     + " TIME"  + ")";
        //Table Users
        final String CREATE_TABLE_USERS =
                "CREATE TABLE " + TaskEntry.TABLE_USERS + " (" +TaskEntry._ID + " INTEGER PRIMARY KEY,"      +
                        TaskEntry.KEY_NAME_USER         +" TEXT NOT NULL,"    + TaskEntry.KEY_PASSWORD_USERS +
                        " TEXT NOT NULL,"               + TaskEntry.KEY_RETRY_PASSWORD                       +
                        " TEXT NOT NULL,"               + TaskEntry.KEY_MOBILE + " TEXT NOT NULL,"           +
                        TaskEntry.KEY_EMAIL             + " TEXT,"             + TaskEntry.KEY_DATE          +
                        " DATE,"                        +  TaskEntry.KEY_TIME  + " TIME"                     + ")";
        //Table STOCKING_WAREHOUSE
        final String CREATE_TABLE_STOCKING_WAREHOUSE =
                "CREATE TABLE "            + TaskEntry.TABLE_STOCKING_WAREHOUSE   + " (" +TaskEntry._ID        +
                   " INTEGER PRIMARY KEY," + TaskEntry.KEY_CATEGORY_ID            + " INTEGER,"                +
                    TaskEntry.KEY_STORE_ID + " INTEGER,"                          +TaskEntry.KEY_FIRST_BALANCE +
                   " INTEGER,"             + TaskEntry.KEY_NOTES                  + " TEXT,"                   +
                   TaskEntry.KEY_USER_ID   + " INTEGER,"                          + TaskEntry.KEY_DATE         +
                   " DATE,"                +  TaskEntry.KEY_TIME                  + " TIME"                    +")";

        //Table DAILY_MOVEMENTS
        final String CREATE_TABLE_DAILY_MOVEMENTS =
           "CREATE TABLE "             + TaskEntry.TABLE_DAILY_MOVEMENTS + " (" + TaskEntry._ID       + " INTEGER PRIMARY KEY," +
           TaskEntry.KEY_CATEGORY_ID   + " INTEGER,"                     + TaskEntry.KEY_STORE_ID     + " INTEGER,"             +
           TaskEntry.KEY_PERMISSION_ID + " INTEGER,"                     + TaskEntry.KEY_INCOMING     + " INTEGER NOT NULL DEFAULT 0,"             +
           TaskEntry.KEY_ISSUED        + " INTEGER NOT NULL DEFAULT 0,"  + TaskEntry.KEY_CONVERT_FROM + " TEXT,"                +
           TaskEntry.KEY_CONVERT_TO    + " INTEGER,"                     +TaskEntry.KEY_USER_ID       +" INTEGER,"              +
           TaskEntry.KEY_DATE          + " DATE,"                        +TaskEntry.KEY_TIME    + " TIME"+  ")";

        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_STORES);
        db.execSQL(CREATE_TABLE_PERMISSION);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STOCKING_WAREHOUSE);
        db.execSQL(CREATE_TABLE_DAILY_MOVEMENTS);
        db.execSQL(CREATE_TABLE_CONVERT_STORE);


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
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_CONVERT_STORE);

        onCreate(db);

    }
    //get date
    public static String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }
    //get date
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }
    //get time
    public static String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        Date time=new Date();
        return dateFormat.format(time);
    }
    //_____________________________Methods Category____________________________

    //_____________________________Insert Table Category____________________________
    public long addCategory(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_NAME_CATEGORY,itemsStore.getNameGategory());
        values.put(TaskEntry.KEY_NATURAL_CATEGORY,itemsStore.getNauralCategory());
        values.put(TaskEntry.KEY_NOTES,itemsStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        //Insert row
        long store_id = db.insert(TaskEntry.TABLE_CATEGORIES,null,values);

        return store_id;
    }
    //____________________Update Category______________________
    public long updateCategory(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry._ID,itemsStore.getId());
        values.put(TaskEntry.KEY_NAME_CATEGORY,itemsStore.getNameGategory());
        values.put(TaskEntry.KEY_NATURAL_CATEGORY,itemsStore.getNauralCategory());
        values.put(TaskEntry.KEY_NOTES,itemsStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsStore.getId())};
        //updating row
        int updateStore = db.update(TaskEntry.TABLE_CATEGORIES,values, selection,selectionArgs);
        //  Log.d("Updated Result: ","= "+updateStore);
        return updateStore;

    }

    //____________________Delete Category____________________________
    public int deleteCategory(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteRow=  db.delete(TaskEntry.TABLE_CATEGORIES, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsStore.getId())});
        return deleteRow;
    }


    //___________________________get All Categories___________________
    public ArrayList<ItemsStore>getAllItemsCategories(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsStores = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_NAME_CATEGORY,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME
        ,TaskEntry.KEY_NATURAL_CATEGORY,TaskEntry.KEY_NOTES};
        Cursor c = db.query(TaskEntry.TABLE_CATEGORIES, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsStore = new ItemsStore();
            itemsStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsStore.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemsStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemsStore.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
            itemsStore.setNauralCategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NATURAL_CATEGORY)));
            itemsStore.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));

            //adding to tagd list
            itemsStores.add(itemsStore);
        }
        return itemsStores;
    }
    public Cursor getAllItemsCategoriesCusror(){
        SQLiteDatabase db = this.getReadableDatabase();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_NAME_CATEGORY,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME
                ,TaskEntry.KEY_NATURAL_CATEGORY,TaskEntry.KEY_NOTES};
        Cursor c = db.rawQuery("SELECT * FROM "+TaskEntry.TABLE_CATEGORIES +" ",null);

        return c;
    }

    //___________________________get All Categories by Name Category___________________
    public ArrayList<ItemsStore>getAllItemsCategoriesByCategoryName(String nameCategory){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsStores = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_NAME_CATEGORY,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME
                ,TaskEntry.KEY_NATURAL_CATEGORY,TaskEntry.KEY_NOTES};
        Cursor c = db.query(TaskEntry.TABLE_CATEGORIES, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsStore = new ItemsStore();
            itemsStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsStore.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemsStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemsStore.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
            itemsStore.setNauralCategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NATURAL_CATEGORY)));
            itemsStore.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));

            //adding to tagd list
            itemsStores.add(itemsStore);
        }
        return itemsStores;
    }
    public ArrayList<ItemsStore>getAllCategoryByCategoryName(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = " SELECT * FROM " + TaskEntry.TABLE_CATEGORIES +
                " WHERE " + TaskEntry.KEY_NAME_CATEGORY  + " LIKE '%" +
                search + "%'" ;
//        String selectQuery = "SELECT  DISTINCT "         +"tc."   +TaskEntry._ID +
//                 ", tc."  +TaskEntry.KEY_NAME_CATEGORY +", tc. "+TaskEntry.KEY_DATE+
//                ", tc. "+TaskEntry.KEY_NATURAL_CATEGORY         +", tu. "+TaskEntry.KEY_NAME_USER + ", tc. "+
//                TaskEntry.KEY_NOTES         +
//                " FROM " +TaskEntry.TABLE_CATEGORIES+ " tc  INNER JOIN "                   +
//                TaskEntry.TABLE_USERS                    + " tu ON tu."     + TaskEntry._ID + " = " + "tc."
//                + TaskEntry.KEY_USER_ID                   + " WHERE tc. " +TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
//                search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemDailyMovement.setNauralCategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NATURAL_CATEGORY)));
         //   itemDailyMovement.setUserName(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_USER)));
            itemDailyMovement.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
                  //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }
    public ArrayList<ItemsStore>getAllCategoryByDate(String date){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = " SELECT * FROM " + TaskEntry.TABLE_CATEGORIES +
                " WHERE " + TaskEntry.KEY_DATE  + " >='" +
                date + "'" ;
//        String selectQuery = "SELECT  DISTINCT "         +"tc."   +TaskEntry._ID +
//                 ", tc."  +TaskEntry.KEY_NAME_CATEGORY +", tc. "+TaskEntry.KEY_DATE+
//                ", tc. "+TaskEntry.KEY_NATURAL_CATEGORY         +", tu. "+TaskEntry.KEY_NAME_USER + ", tc. "+
//                TaskEntry.KEY_NOTES         +
//                " FROM " +TaskEntry.TABLE_CATEGORIES+ " tc  INNER JOIN "                   +
//                TaskEntry.TABLE_USERS                    + " tu ON tu."     + TaskEntry._ID + " = " + "tc."
//                + TaskEntry.KEY_USER_ID                   + " WHERE tc. " +TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
//                search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemDailyMovement.setNauralCategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NATURAL_CATEGORY)));
            //   itemDailyMovement.setUserName(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_USER)));
            itemDailyMovement.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }

    public ArrayList<ItemsStore>getAllCategoryByCategoryName(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tc."   +TaskEntry._ID +
                ", tc."  +TaskEntry.KEY_NAME_CATEGORY +", tc. "+TaskEntry.KEY_DATE+
                ", tc. "+TaskEntry.KEY_NATURAL_CATEGORY         +", tu. "+TaskEntry.KEY_NAME_USER + ", tc. "+
                TaskEntry.KEY_NOTES         +
                " FROM " +TaskEntry.TABLE_CATEGORIES+ " tc  INNER JOIN "                   +
                TaskEntry.TABLE_USERS                    + " tu ON tu."     + TaskEntry._ID + " = " + "tc."
                + TaskEntry.KEY_USER_ID               ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemDailyMovement.setNauralCategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NATURAL_CATEGORY)));
            itemDailyMovement.setUserName(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_USER)));
            itemDailyMovement.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }

    //____________________Check data exist or not______________________

    public boolean isExistCategoryName(String categoryName){
        String whereClause = TaskEntry.KEY_NAME_CATEGORY +"=?";
        String [] whereArgs = new String[]{categoryName};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_CATEGORIES,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }


    //_____________________________Methods Store____________________________

    //_____________________________Insert Table Store____________________________
    public long addStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_TYPE_STORE,itemsStore.getTypeStore());
       // values.put(TaskEntry.KEY_TYPE_STORE_CONVERT,itemsStore.getConvertTo());
        values.put(TaskEntry.KEY_NOTES,itemsStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());

        //Insert row
        long store_id = db.insert(TaskEntry.TABLE_STORE,null,values);

        return store_id;
    }
    //____________________Check data exist or not______________________
    public boolean isExistTypeStore(String typeStore){
        String whereClause = TaskEntry.KEY_TYPE_STORE +"=?";
        String [] whereArgs = new String[]{typeStore};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_STORE,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }

    //____________________Check data exist or not______________________
    public boolean isExistTypeConvertStore(String typeConvertStore){
        String whereClause = TaskEntry.KEY_TYPE_STORE_CONVERT +"=?";
        String [] whereArgs = new String[]{typeConvertStore};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_CONVERT_STORE,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }


    //____________________Update Store______________________
    public long updateStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(TaskEntry._ID,itemsStore.getId());
        values.put(TaskEntry.KEY_TYPE_STORE,itemsStore.getTypeStore());
        values.put(TaskEntry.KEY_NOTES,itemsStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsStore.getId())};
        //updating row
        int updateStore = db.update(TaskEntry.TABLE_STORE,values, selection,selectionArgs);
      //  Log.d("Updated Result: ","= "+updateStore);
        return updateStore;

    }
    //_____________________________Methods Store____________________________

    //____________________Delete Store____________________________
    public int deleteStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteRow=  db.delete(TaskEntry.TABLE_STORE, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsStore.getId())});
       // Log.d("Deleted  Result: ","= "+ deleteRow);

        return deleteRow;
            }


    //___________________________get All Stores___________________
    public ArrayList<ItemsStore>getAllItemsStore(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsStores = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_TYPE_STORE,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME,TaskEntry.KEY_NOTES};
        Cursor c = db.query(TaskEntry.TABLE_STORE, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsStore = new ItemsStore();
            itemsStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsStore.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemsStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemsStore.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
            itemsStore.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));

            //adding to tagd list
            itemsStores.add(itemsStore);
        }
        return itemsStores;
    }
    //___________________________get All Stores by TypeStore___________________

    public ArrayList<ItemsStore>getAllStoresByTypeStore(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = " SELECT * FROM " + TaskEntry.TABLE_STORE +
                " WHERE " + TaskEntry.KEY_TYPE_STORE  + " LIKE '%" +
                search + "%'" ;
//        String selectQuery = "SELECT  DISTINCT "         +"tc."   +TaskEntry._ID +
//                 ", tc."  +TaskEntry.KEY_NAME_CATEGORY +", tc. "+TaskEntry.KEY_DATE+
//                ", tc. "+TaskEntry.KEY_NATURAL_CATEGORY         +", tu. "+TaskEntry.KEY_NAME_USER + ", tc. "+
//                TaskEntry.KEY_NOTES         +
//                " FROM " +TaskEntry.TABLE_CATEGORIES+ " tc  INNER JOIN "                   +
//                TaskEntry.TABLE_USERS                    + " tu ON tu."     + TaskEntry._ID + " = " + "tc."
//                + TaskEntry.KEY_USER_ID                   + " WHERE tc. " +TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
//                search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemDailyMovement.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));

            //   itemDailyMovement.setUserName(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_USER)));
            itemDailyMovement.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }




    //____________________Update Convert Store______________________
    public long updateConvertStore(ItemsStore itemsConvertStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry._ID,itemsConvertStore.getId());
        values.put(TaskEntry.KEY_TYPE_STORE_CONVERT,itemsConvertStore.getConvertTo());
        values.put(TaskEntry.KEY_NOTES,itemsConvertStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsConvertStore.getId())};
        //updating row
        int updateStore = db.update(TaskEntry.TABLE_CONVERT_STORE,values, selection,selectionArgs);
        //  Log.d("Updated Result: ","= "+updateStore);
        return updateStore;

    }
    //_____________________________Methods Store____________________________

    //_____________________________Insert Table Convert Store____________________________
    public long addConvertStore(ItemsStore itemsConvertStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_TYPE_STORE_CONVERT,itemsConvertStore.getConvertTo());
        values.put(TaskEntry.KEY_NOTES,itemsConvertStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());

        //Insert row
        long store_id = db.insert(TaskEntry.TABLE_CONVERT_STORE,null,values);

        return store_id;
    }
    //____________________Delete Store____________________________
    public int deleteConvertStore(ItemsStore itemsConvertStore){
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteRow=  db.delete(TaskEntry.TABLE_CONVERT_STORE, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsConvertStore.getId())});
        // Log.d("Deleted  Result: ","= "+ deleteRow);

        return deleteRow;
    }


    //___________________________get All Stores___________________
    public ArrayList<ItemsStore>getAllItemsConvertStore(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsConvertStores = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_TYPE_STORE_CONVERT,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME};
        Cursor c = db.query(TaskEntry.TABLE_CONVERT_STORE, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsStore = new ItemsStore();
            itemsStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsStore.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemsStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemsStore.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
            //adding to tagd list
            itemsConvertStores.add(itemsStore);
        }
        return itemsConvertStores;
    }






    //___________________________get All Store cursor___________________
    public Cursor getAllItemsStoreWithCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsStores = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_TYPE_STORE,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME};
        return db.query(TaskEntry.TABLE_STORE, columns,null,null,null,null,null);

      /*  Cursor c = db.query(TaskEntry.TABLE_STORE, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsStore = new ItemsStore();
            itemsStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsStore.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemsStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemsStore.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
            //adding to tagd list
            itemsStores.add(itemsStore);
        }
        return itemsStores;*/
    }
    //_____________________________Methods Permissions____________________________

    //___________________________________Add PErmission___________________________________

    public long addPermission(ItemsStore itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_NAME_PERMISSION,itemsPermission.getNamePermission());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        values.put(TaskEntry.KEY_NOTES ,itemsPermission.getNotes());
//Insert row
        long store_id = db.insert(TaskEntry.TABLE_PERMISSION,null,values);
        return store_id;
    }

    //get All Permission
    public ArrayList<ItemsStore>getAllItemsPermission(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsPermissions = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_NAME_PERMISSION,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME};
        Cursor c = db.query(TaskEntry.TABLE_PERMISSION, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsPermission = new ItemsStore();
            itemsPermission.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsPermission.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemsPermission.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemsPermission.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
            //adding to tagd list
            itemsPermissions.add(itemsPermission);
        }
        return itemsPermissions;
    }

    //get All Permission by Search
    public ArrayList<ItemsStore>getAllItemsPermissionBySearch(String search){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemsPermissions = new ArrayList<>();
        String selectQuery = "SELECT  DISTINCT "    +TaskEntry._ID + TaskEntry.KEY_NAME_PERMISSION
        +TaskEntry.KEY_DATE+ TaskEntry.KEY_TIME     +" FROM " +TaskEntry.TABLE_PERMISSION +
                 " WHERE tp. " +TaskEntry.KEY_NAME_PERMISSION + " LIKE '%" +
                search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);


        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemsPermission = new ItemsStore();
            itemsPermission.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemsPermission.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemsPermission.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            itemsPermission.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
            //adding to tagd list
            itemsPermissions.add(itemsPermission);
        }
        return itemsPermissions;
    }
    //____________________Update Permission______________________
    public long updatePermission(ItemsStore itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry._ID,itemsPermission.getId());
        values.put(TaskEntry.KEY_NAME_PERMISSION,itemsPermission.getNamePermission());
        values.put(TaskEntry.KEY_NOTES,itemsPermission.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsPermission.getId())};
        //updating row
      //  return db.update(TaskEntry.TABLE_PERMISSION,values, selection,selectionArgs);
        return db.replace(TaskEntry.TABLE_PERMISSION,null,values);

    }
    //____________________Delete Permission____________________________
    public void deletePermission(ItemsStore itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TaskEntry.TABLE_PERMISSION, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsPermission.getId())});

    }
    //____________________Check data exist or not______________________
    public boolean isExistNamePErmission(String namePermission){
        String whereClause = TaskEntry.KEY_NAME_PERMISSION +"=?";
        String [] whereArgs = new String[]{namePermission};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_PERMISSION,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }

    //_____________________________Methods Daily Movements____________________________

    //___________________________________Add Daily Movements____________________________

    public long addDailyMovements(ItemsStore itemsDailyMovements){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_PERMISSION_ID,itemsDailyMovements.getId_permission_id());
        values.put(TaskEntry.KEY_STORE_ID ,itemsDailyMovements.getId_code_store());
        values.put(TaskEntry.KEY_CATEGORY_ID,itemsDailyMovements.getId_code_category());
        values.put(TaskEntry.KEY_ISSUED,itemsDailyMovements.getIssued());
        values.put(TaskEntry.KEY_INCOMING,itemsDailyMovements.getIncoming());
        values.put(TaskEntry.KEY_CONVERT_TO,itemsDailyMovements.getId_convert_to());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());        //Insert row
        long dailyMovement_id = db.insert(TaskEntry.TABLE_DAILY_MOVEMENTS,null,values);

        return dailyMovement_id;
    }

    //____________________Update DailyMovements____________________________
    public int updateDailyMovements(ItemsStore itemsDailyMovements){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry._ID,itemsDailyMovements.getId());
        values.put(TaskEntry.KEY_PERMISSION_ID,itemsDailyMovements.getId_permission_id());
        values.put(TaskEntry.KEY_STORE_ID ,itemsDailyMovements.getId_code_store());
        values.put(TaskEntry.KEY_CATEGORY_ID,itemsDailyMovements.getId_code_category());
        values.put(TaskEntry.KEY_ISSUED,itemsDailyMovements.getIssued());
        values.put(TaskEntry.KEY_INCOMING,itemsDailyMovements.getIncoming());
        values.put(TaskEntry.KEY_CONVERT_TO,itemsDailyMovements.getId_convert_to());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsDailyMovements.getId())};
        //updating row
        return db.update(TaskEntry.TABLE_DAILY_MOVEMENTS,values, selection,selectionArgs);

    }
    public boolean deleteMovementsBackup(String where){
        return delete(TaskEntry.TABLE_DAILY_MOVEMENTS,where);
    }
    public boolean deletePermissionBackup(String where){
        return delete(TaskEntry.TABLE_PERMISSION,where);
    }
    public boolean deleteCategoresBackup(String where){
        return delete(TaskEntry.TABLE_CATEGORIES,where);
    }
    public boolean deleteStoreBackup(String where){
        return delete(TaskEntry.TABLE_STORE,where);
    }
    public boolean deleteConvertStoreBackup(String where){
        return delete(TaskEntry.TABLE_CONVERT_STORE,where);
    }
    public boolean deleteStokeBackup(String where){
        return delete(TaskEntry.TABLE_STOCKING_WAREHOUSE,where);
    }
    public boolean deleteUsersBackup(String where){
        return delete(TaskEntry.TABLE_USERS,where);
    }
    /**
     * delete id row of table
     */
    public boolean delete(String table, String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }
    //____________________Delete DailyMovements____________________________
    public void deleteDailyMovements(ItemsStore itemsDailyMovements){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskEntry.TABLE_DAILY_MOVEMENTS, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsDailyMovements.getId())});
    }
    //____________________get All Dialy Movements____________________________

    public ArrayList<ItemsStore>getAllItemsDialyMovements(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_PERMISSION_ID,TaskEntry.KEY_CATEGORY_ID,
                TaskEntry.KEY_STORE_ID,TaskEntry.KEY_INCOMING,TaskEntry.KEY_ISSUED,
                TaskEntry.KEY_CONVERT_TO,TaskEntry.KEY_DATE};
        Cursor c = db.query(TaskEntry.TABLE_DAILY_MOVEMENTS, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setId_permission_id(c.getLong(c.getColumnIndex(TaskEntry.KEY_PERMISSION_ID)));
            itemStokeHouse.setId_code_category(c.getLong(c.getColumnIndex(TaskEntry.KEY_CATEGORY_ID)));
            itemStokeHouse.setId_code_store(c.getLong(c.getColumnIndex(TaskEntry.KEY_STORE_ID)));
            itemStokeHouse.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemStokeHouse.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemStokeHouse.setId_convert_to(c.getLong(c.getColumnIndex(TaskEntry.KEY_CONVERT_TO)));
            //adding to tagd list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }
    //____________________get All SDailyMovements____________________________

    public ArrayList<ItemsStore>getAllDailyMovements(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " /*+ TaskEntry.TABLE_STORE         +" ts, "+TaskEntry.TABLE_CATEGORIES     +
                " tc,"   + TaskEntry.TABLE_CONVERT_STORE +" tcs, "+ TaskEntry.TABLE_PERMISSION   +
                " tp, "  */+TaskEntry.TABLE_DAILY_MOVEMENTS         + " tdm  INNER JOIN "          +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO            ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }

    //____________________get report in Issued and Incoming by name category and type store____________________________


    public int getFirstBalance(long categoryId,long storeId){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( " +TaskEntry.KEY_FIRST_BALANCE  +
                ") FROM " + TaskEntry.TABLE_STOCKING_WAREHOUSE +
                " WHERE "  + TaskEntry.KEY_CATEGORY_ID + " = " +
                categoryId + " AND "+TaskEntry.KEY_STORE_ID    + " = " + storeId ;
        int sumFirstBalance = 0;
        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

          }
    //____________________get report in Issued and Incoming by name category and type store____________________________


    public int getFirstBalanceByNameCategoryAndTypeStore(String nameCategory,String TypeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( tsw." +TaskEntry.KEY_FIRST_BALANCE  + ") FROM "
                + TaskEntry.TABLE_STOCKING_WAREHOUSE + " tsw " +
                " INNER JOIN " + TaskEntry.TABLE_CATEGORIES + " tc ON " +
                " tsw. "+TaskEntry.KEY_CATEGORY_ID + " = tc. " + TaskEntry._ID +
                " INNER JOIN " + TaskEntry.TABLE_STORE + " ts ON tsw. "+ TaskEntry.KEY_STORE_ID +" = "
                +" ts. " + TaskEntry._ID + " WHERE tc. "+TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +nameCategory +
                 "%' AND ts."+TaskEntry.KEY_TYPE_STORE + " LIKE '%" + TypeStore + "%'" ;

                int sumFirstBalance = 0;

        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }

    //____________________get report in  SUM(Incoming) by name category and type store____________________________
    public int getIncomingByNameCategoryAndTypeStore(String nameCategory,String TypeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( td." +TaskEntry.KEY_INCOMING + ") FROM "
                + TaskEntry.TABLE_DAILY_MOVEMENTS + " td " +
                " INNER JOIN " + TaskEntry.TABLE_CATEGORIES + " tc ON " +
                " td. "+TaskEntry.KEY_CATEGORY_ID + " = tc. " + TaskEntry._ID +
                " INNER JOIN " + TaskEntry.TABLE_STORE + " ts ON td. "+ TaskEntry.KEY_STORE_ID +" = "
                +" ts. " + TaskEntry._ID + " WHERE tc. "+TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +nameCategory +
                "%' AND ts."+TaskEntry.KEY_TYPE_STORE + " LIKE '%" + TypeStore + "%'" ;

        int sumFirstBalance = 0;

        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }
    //____________________get report in  SUM(Issued) by name category and type store____________________________
    public int getIssuedByNameCategoryAndTypeStore(String nameCategory,String TypeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( td." +TaskEntry.KEY_ISSUED + ") FROM "
                + TaskEntry.TABLE_DAILY_MOVEMENTS + " td " +
                " INNER JOIN " + TaskEntry.TABLE_CATEGORIES + " tc ON " +
                " td. "+TaskEntry.KEY_CATEGORY_ID + " = tc. " + TaskEntry._ID +
                " INNER JOIN " + TaskEntry.TABLE_STORE + " ts ON td. "+ TaskEntry.KEY_STORE_ID +" = "
                +" ts. " + TaskEntry._ID + " WHERE tc. "+TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +nameCategory +
                "%' AND ts."+TaskEntry.KEY_TYPE_STORE + " LIKE '%" + TypeStore + "%'" ;

        int sumFirstBalance = 0;

        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }
    //____________________get report in  SUM(ConvertTo) by name category and type store____________________________
    public int getConvertToByNameCategoryAndTypeStore(String nameCategory,String TypeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( td." +TaskEntry.KEY_ISSUED + ") FROM "
                + TaskEntry.TABLE_DAILY_MOVEMENTS + " td " +
                " INNER JOIN " + TaskEntry.TABLE_CATEGORIES + " tc ON " +
                " td. "+TaskEntry.KEY_CATEGORY_ID + " = tc. " + TaskEntry._ID +
                " INNER JOIN " + TaskEntry.TABLE_CONVERT_STORE + " tcs ON td. "+ TaskEntry.KEY_CONVERT_TO +" = "
                +" tcs. " + TaskEntry._ID + " WHERE tc. "+TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +nameCategory +
                "%' AND tcs."+TaskEntry.KEY_TYPE_STORE_CONVERT + " LIKE '%" + TypeStore + "%'" ;

        int sumFirstBalance = 0;

        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }

    //____________________get report in Issued and Incoming by name category and type store____________________________

    public int getIncomingForDailyMovements(long categoryId,long storeId){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( " +TaskEntry.KEY_INCOMING  +
                ") FROM " + TaskEntry.TABLE_DAILY_MOVEMENTS +
                " WHERE "  + TaskEntry.KEY_CATEGORY_ID + " = " +
                categoryId + " AND "+TaskEntry.KEY_STORE_ID    + " = " + storeId ;
        int sumFirstBalance = 0;
        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }
    public int getIssedForDailyMovements(long categoryId,long storeId){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( " +TaskEntry.KEY_ISSUED  +
                ") FROM " + TaskEntry.TABLE_DAILY_MOVEMENTS +
                " WHERE "  + TaskEntry.KEY_CATEGORY_ID + " = " +
                categoryId + " AND "+TaskEntry.KEY_STORE_ID    + " = " + storeId ;
        int sumFirstBalance = 0;
        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }
    public int getConvertToForDailyMovements(long categoryId,long converTo){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( " +TaskEntry.KEY_ISSUED  +
                ") FROM " + TaskEntry.TABLE_DAILY_MOVEMENTS +
                " WHERE "  + TaskEntry.KEY_CATEGORY_ID + " = " +
                categoryId + " AND "+TaskEntry.KEY_CONVERT_TO    + " = " + converTo ;
        int sumFirstBalance = 0;
        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }


    //____________________get report in Incoming by name category in Reportes ____________________________

    public int getIncomingReportesForDailyMovements(long categoryId){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( " +TaskEntry.KEY_INCOMING  +
                ") FROM " + TaskEntry.TABLE_DAILY_MOVEMENTS +
                " WHERE "  + TaskEntry.KEY_CATEGORY_ID + " = " +
                categoryId  ;
        int sumFirstBalance = 0;
        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){
            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }
    public int getConvertToForDailyMovements(long categoryId,String converTo){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT SUM( " +TaskEntry.KEY_ISSUED  +
                ") FROM " + TaskEntry.TABLE_DAILY_MOVEMENTS +
                " WHERE "  + TaskEntry.KEY_CATEGORY_ID + " = " +
                categoryId + " AND "+TaskEntry.KEY_CONVERT_FROM    + " = " + converTo ;
        int sumFirstBalance = 0;
        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();

        if(c != null && c.getCount()>0){

            sumFirstBalance = c.getInt(0);
        }
        return sumFirstBalance;

    }

    //____________________Check type store used or not ______________________
    public boolean isTypeStoreUsedDailyMovements(int typeStore){
        String whereClause = TaskEntry.KEY_STORE_ID +"=?";
        String [] whereArgs = new String[]{String.valueOf(typeStore)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_DAILY_MOVEMENTS,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();
        db.close();
        return exist;
    }
    //____________________Check type store used or not ______________________
    public boolean isConvertTypeStoreUsedDailyMovements(int typeStore){
        String whereClause = TaskEntry.KEY_CONVERT_TO +"=?";
        String [] whereArgs = new String[]{String.valueOf(typeStore)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_DAILY_MOVEMENTS,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();
        db.close();
        return exist;
    }
    //____________________Check type store used or not______________________
    public boolean isTypeStoreUsedStokewearhouse(int typeStore){
        String whereClause = TaskEntry.KEY_STORE_ID +"=?";
        String [] whereArgs = new String[]{String.valueOf(typeStore)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_STOCKING_WAREHOUSE,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }
    //____________________Check type First Balance used or not______________________
    public boolean isFirstBalanceUsedStokewearhouse(int idStoke){
        String whereClause = TaskEntry.KEY_CATEGORY_ID +"=?";
        String [] whereArgs = new String[]{String.valueOf(idStoke)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_DAILY_MOVEMENTS,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }
    //____________________Check name permission used or not______________________
    public boolean isNamePermissioneUsedDailyMovements(int namePermission){
        String whereClause = TaskEntry.KEY_PERMISSION_ID +"=?";
        String [] whereArgs = new String[]{String.valueOf(namePermission)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_DAILY_MOVEMENTS,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }
    //____________________Check name permission used or not______________________
    public boolean isNameCategoryUsedDailyMovements(long nameCategory){
        String whereClause = TaskEntry.KEY_CATEGORY_ID +"=?";
        String [] whereArgs = new String[]{String.valueOf(nameCategory)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_DAILY_MOVEMENTS,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }
    //____________________Check isNameCategoryUsedStokeWearhouse used or not______________________
    public boolean isNameCategoryUsedStokeWearhouse(long nameCategory){
        String whereClause = TaskEntry.KEY_CATEGORY_ID +"=?";
                String [] whereArgs = new String[]{String.valueOf(nameCategory)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_STOCKING_WAREHOUSE,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }
    //____________________Check IsLastRowInTable or not______________________


      public int isLastRow(){

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  "  +TaskEntry._ID +
                " FROM " + TaskEntry.TABLE_DAILY_MOVEMENTS+
                " ORDER BY "+TaskEntry._ID  +" DESC LIMIT 1 ";
     /*
        String selectQuery = "SELECT " + TaskEntry._ID + " FROM " + TaskEntry.TABLE_DAILY_MOVEMENTS +
                " WHERE "+TaskEntry._ID  +" = " + " (SELECT MAX(" +TaskEntry._ID + ")" + " FROM "+
                TaskEntry.TABLE_DAILY_MOVEMENTS + ")" ;*/
        int sumFirstBalance = 0;

        Cursor search = db.rawQuery(selectQuery,null);
        search.moveToFirst();

        if(search != null && search.getCount()>0){
            sumFirstBalance = search.getInt(0);
        }

      //    boolean exist =  search.isLast();
     //   search.close();


    //    db.close();
        return sumFirstBalance;

    }
       //____________________get All DailyMovements by name Permission____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsByNamePermission(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO             + " WHERE tp. " +TaskEntry.KEY_NAME_PERMISSION + " LIKE '%" +
                search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }
    //____________________get All DailyMovements by name Permission and TypeStore____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsByNamePermissionAndTypeStore(String search,String typeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE            + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO               + " WHERE ( ts. "  +TaskEntry.KEY_TYPE_STORE + " LIKE '%"
                + typeStore + "%' OR tcs. "  +TaskEntry.KEY_TYPE_STORE_CONVERT + " LIKE '%" +
                typeStore + "%' ) AND tp. "  + TaskEntry.KEY_NAME_PERMISSION+ " LIKE '%" + search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }
    //____________________get All DailyMovements by Category Name____________________________
    public ArrayList<ItemsStore>getAllDailyMovementsByCategoryName(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO             + " WHERE tc. " +TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
                search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }
    //____________________get All DailyMovements by Category Name and TypeStore____________________________
    public ArrayList<ItemsStore>getAllDailyMovementsByCategoryNameAndTypeStore(String search,String typeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO             + " WHERE ( ts. " +TaskEntry.KEY_TYPE_STORE + " LIKE '%" +
                typeStore + "%' OR tcs."  +TaskEntry.KEY_TYPE_STORE_CONVERT + " LIKE '%" +
                typeStore + "%' ) AND tc."+ TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
                search + "%'";



        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }

    //____________________get All DailyMovements by  Type Store____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsByTypeStore(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO             + " WHERE ts. " +TaskEntry.KEY_TYPE_STORE + " LIKE '%" +
                search + "%' OR tcs. " +TaskEntry.KEY_TYPE_STORE_CONVERT + " LIKE '%" +
                search + "%'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }

    //____________________get All DailyMovements by  Incoming____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsByIncoming(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO             + " WHERE tdm. " +TaskEntry.KEY_INCOMING + " >= " +" ' "
                 + search + "'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }
    //____________________get All DailyMovements by  Incoming and TypeStore____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsByIncomingAndTypeStore(String search,String typeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO             + " WHERE ( ts. " +TaskEntry.KEY_TYPE_STORE + " LIKE '%" +
                typeStore + "%' OR tcs."  +TaskEntry.KEY_TYPE_STORE_CONVERT + " LIKE '%" +
                typeStore + "%' ) AND tdm."+TaskEntry.KEY_INCOMING + " >= " +" ' "
                + search + "'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }
    //____________________get All DailyMovements by  Issued____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsByIssued(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE          + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO             + " WHERE tdm. " +TaskEntry.KEY_ISSUED + " >= " +" ' "
                + search + "'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }
    //____________________get All DailyMovements by  Issued and TypeStore____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsByIssuedAndTypeStore(String search,String typeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tcs. "+TaskEntry.KEY_TYPE_STORE_CONVERT+
                ", tdm. "+TaskEntry.KEY_INCOMING         +", tdm. "+TaskEntry.KEY_ISSUED         +
                ", tdm. "+TaskEntry.KEY_DATE             +
                " FROM " +TaskEntry.TABLE_DAILY_MOVEMENTS+ " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ts ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " tc ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               + " tp ON tp."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_PERMISSION_ID            +" LEFT JOIN  "                        +
                TaskEntry.TABLE_CONVERT_STORE            + " tcs ON tcs."     +TaskEntry._ID+ " = " + "tdm."
                + TaskEntry.KEY_CONVERT_TO               + " WHERE ( ts. " +TaskEntry.KEY_TYPE_STORE + " LIKE '%" +
                typeStore + "%' OR tcs."  +TaskEntry.KEY_TYPE_STORE_CONVERT + " LIKE '%" +
                typeStore + "%' ) AND tdm. " +TaskEntry.KEY_ISSUED + " >= " +" ' "
                + search + "'" ;
        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemDailyMovement = new ItemsStore();
            itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemDailyMovement.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
            itemDailyMovement.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemDailyMovement.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
            itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
            itemDailyMovement.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }



    //_____________________________Methods StokeWareHouse____________________________

    //___________________________________Add StokeWareHouse____________________________

    public long addStokeWarehouse(ItemsStore itemStokeHouse){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_CATEGORY_ID,itemStokeHouse.getId_code_category());
        values.put(TaskEntry.KEY_STORE_ID ,itemStokeHouse.getId_code_store());
        values.put(TaskEntry.KEY_FIRST_BALANCE,itemStokeHouse.getFirst_balanse());
        values.put(TaskEntry.KEY_NOTES,itemStokeHouse.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());        //Insert row
        long stokewarehouse_id = db.insert(TaskEntry.TABLE_STOCKING_WAREHOUSE,null,values);
        return stokewarehouse_id;
    }
    //____________________Update StokeWareHouse____________________________
    public int updateStokeWarehouse(ItemsStore itemStokeHouse){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry._ID,itemStokeHouse.getId());
        values.put(TaskEntry.KEY_CATEGORY_ID,itemStokeHouse.getId_code_category());
        values.put(TaskEntry.KEY_STORE_ID ,itemStokeHouse.getId_code_store());
        values.put(TaskEntry.KEY_FIRST_BALANCE,itemStokeHouse.getFirst_balanse());
        values.put(TaskEntry.KEY_NOTES,itemStokeHouse.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemStokeHouse.getId())};
        //updating row
        return db.update(TaskEntry.TABLE_STOCKING_WAREHOUSE,values, selection,selectionArgs);

    }
    //____________________Delete StokeWareHouse____________________________
    public void deleteStokeWareHouse(ItemsStore itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();

       db.delete(TaskEntry.TABLE_STOCKING_WAREHOUSE, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsPermission.getId())});
    }




    //____________________get All StokeWareHouse____________________________

    public ArrayList<ItemsStore>getAllItemsStokeWarehouse(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<>();
        String []columns = {TaskEntry._ID,TaskEntry.KEY_CATEGORY_ID,TaskEntry.KEY_STORE_ID,TaskEntry.KEY_FIRST_BALANCE,TaskEntry.KEY_DATE};
        Cursor c = db.query(TaskEntry.TABLE_STOCKING_WAREHOUSE, columns,null,null,null,null,null);
        //Looping through all rows and adding to list
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
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

    //____________________getting all StokeWareHouse under Store and Category wit Cursor____________________________

    public Cursor getAllStokeHouseByCategoryAndStoryByCursor(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID ;

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return c;
    }

    //____________________getting all StokeWareHouse under Store and Category____________________________

    public ArrayList<ItemsStore>getAllStokeHouseByCategoryAndStory(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE +
                ", tc."     +TaskEntry.KEY_NAME_CATEGORY +", tsw."      + TaskEntry.KEY_FIRST_BALANCE         +
                " FROM "    + TaskEntry.TABLE_STORE      + " ts, "      +TaskEntry.TABLE_CATEGORIES           +
                " tc,"      + TaskEntry.TABLE_STOCKING_WAREHOUSE        + " tsw  INNER JOIN "                 +
                TaskEntry.TABLE_STORE        + " ON ts."+ TaskEntry._ID + " = "      + "tsw."                 +
                TaskEntry.KEY_STORE_ID       +" INNER JOIN  "           +TaskEntry.TABLE_CATEGORIES           +
                " ON tc."+TaskEntry._ID      + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID ;

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }

    //____________________getting all StokeWareHouse with ID____________________________

    public ArrayList<ItemsStore>getAllStokeHouseByID(long category_id){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID ;

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }

    //____________________getting all StokeWareHouse by Search Category Name____________________________

    public ArrayList<ItemsStore>getAllStokeHouseBySearchCategoryName(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID + " WHERE tc. " +TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
                search + "%'" ;

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }
    //____________________getting all StokeWareHouse by Search Category Name and TypeStore____________________________

    public ArrayList<ItemsStore>getAllStokeHouseBySearchCategoryNameAndTypeStore(String search,String typeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID + " WHERE tc. " +TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
                search + "%' AND ts."+ TaskEntry.KEY_TYPE_STORE + " LIKE '%" + typeStore + "%'";

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }


    //____________________getting all StokeWareHouse by Search Type Store ____________________________

    public ArrayList<ItemsStore>getAllStokeHouseBySearchTypeStore(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID + " WHERE ts. " +TaskEntry.KEY_TYPE_STORE + " LIKE '%" +
                search + "%'" ;

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }

    //____________________getting all StokeWareHouse by Search First Balance ____________________________

    public ArrayList<ItemsStore>getAllStokeHouseBySearchFirstBalance(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID + " WHERE tsw. " +TaskEntry.KEY_FIRST_BALANCE +
                " >= ' " +   search + " ' " ;

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }
    //____________________getting all StokeWareHouse by Search First Balance and TypeStore ____________________________

    public ArrayList<ItemsStore>getAllStokeHouseBySearchFirstBalanceAndTypeStore(String search,String typeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID + " WHERE tsw. " +TaskEntry.KEY_FIRST_BALANCE +
                " >= ' " +   search + " ' AND ts."+ TaskEntry.KEY_TYPE_STORE + " LIKE '%" + typeStore + "%'";

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return itemStokeHouses;
    }

    //____________________getting all StokeWareHouse by Search and Cursor____________________________

    public Cursor getAllStokeHouseBySearchByCursor(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "+"tsw. " +TaskEntry._ID + ", ts. " + TaskEntry.KEY_TYPE_STORE + ", tc."
                +TaskEntry.KEY_NAME_CATEGORY +", tsw."+ TaskEntry.KEY_FIRST_BALANCE + " FROM " + TaskEntry.TABLE_STORE + " ts, "
                +TaskEntry.TABLE_CATEGORIES + " tc,"+  TaskEntry.TABLE_STOCKING_WAREHOUSE
                + " tsw  INNER JOIN "  +TaskEntry.TABLE_STORE + " ON ts."+ TaskEntry._ID + " = " + "tsw." + TaskEntry.KEY_STORE_ID
                +" INNER JOIN  " +TaskEntry.TABLE_CATEGORIES+ " ON tc."+TaskEntry._ID
                + " = " + "tsw." + TaskEntry.KEY_CATEGORY_ID + " WHERE tc. " +TaskEntry.KEY_NAME_CATEGORY + " LIKE '%" +
                search + "%'" ;

        Cursor c=db.rawQuery(selectQuery,null);
        //looping through all rows and adding to list
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            ItemsStore itemStokeHouse = new ItemsStore();
            itemStokeHouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
            itemStokeHouse.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
            itemStokeHouse.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
            itemStokeHouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
            //adding to todo list
            itemStokeHouses.add(itemStokeHouse);
        }
        return c;
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
    public ArrayList<String> getDataForSpinnerConvertStore(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> IDConvertStore = new ArrayList<>();
        String selectQuery = "SELECT * FROM " +TaskEntry.TABLE_CONVERT_STORE;
        Cursor c = db.rawQuery(selectQuery,null);
        for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
            IDConvertStore.add(c.getString(1));
        }
        return IDConvertStore;
    }
    public ArrayList<String> getDataForSpinnerPermission(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> IDPermission = new ArrayList<>();
        String selectQuery = "SELECT * FROM " +TaskEntry.TABLE_PERMISSION;
        Cursor c = db.rawQuery(selectQuery,null);
        for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
            IDPermission.add(c.getString(1));
        }
        return IDPermission;
    }

    /**
     * convert cursor to note
     */
    public ItemsStore cursorToNoteForTableDailyMovements(Cursor c) {

        ItemsStore itemDailyMovement = new ItemsStore();
        itemDailyMovement.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
        itemDailyMovement.setId_permission_id(c.getInt(c.getColumnIndex(TaskEntry.KEY_PERMISSION_ID)));
        itemDailyMovement.setId_code_category(c.getInt(c.getColumnIndex(TaskEntry.KEY_CATEGORY_ID)));
        itemDailyMovement.setId_code_store(c.getInt(c.getColumnIndex(TaskEntry.KEY_STORE_ID)));
        itemDailyMovement.setIncoming(c.getInt(c.getColumnIndex(TaskEntry.KEY_INCOMING)));
        itemDailyMovement.setIssued(c.getInt(c.getColumnIndex(TaskEntry.KEY_ISSUED)));
        itemDailyMovement.setId_convert_to(c.getInt(c.getColumnIndex(TaskEntry.KEY_CONVERT_TO)));
        itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
        return itemDailyMovement;
    }

    public ItemsStore cursorToNoteForTableStokeWarehouse(Cursor c) {

        ItemsStore itemStokeWearhouse = new ItemsStore();
        itemStokeWearhouse.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
        itemStokeWearhouse.setId_code_category(c.getInt(c.getColumnIndex(TaskEntry.KEY_CATEGORY_ID)));
        itemStokeWearhouse.setId_code_store(c.getInt(c.getColumnIndex(TaskEntry.KEY_STORE_ID)));
        itemStokeWearhouse.setFirst_balanse(c.getInt(c.getColumnIndex(TaskEntry.KEY_FIRST_BALANCE)));
        itemStokeWearhouse.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
        itemStokeWearhouse.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
        itemStokeWearhouse.setUserId(c.getInt(c.getColumnIndex(TaskEntry.KEY_USER_ID)));
        itemStokeWearhouse.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
        return itemStokeWearhouse;
    }
    public ItemsStore cursorToNoteForTablePermission(Cursor c) {

        ItemsStore itemPermission = new ItemsStore();
        itemPermission.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
        itemPermission.setNamePermission(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_PERMISSION)));
        itemPermission.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
        itemPermission.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
        itemPermission.setUserId(c.getInt(c.getColumnIndex(TaskEntry.KEY_USER_ID)));
        itemPermission.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
             return itemPermission;
    }
    public ItemsStore cursorToNoteForTableStore(Cursor c) {

        ItemsStore itemStore = new ItemsStore();
        itemStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
        itemStore.setTypeStore(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE)));
        itemStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
        itemStore.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
        itemStore.setUserId(c.getInt(c.getColumnIndex(TaskEntry.KEY_USER_ID)));
        itemStore.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
        return itemStore;
    }
    public ItemsStore cursorToNoteForTableCategores(Cursor c) {

        ItemsStore itemCategores = new ItemsStore();
        itemCategores.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
        itemCategores.setNameGategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY)));
        itemCategores.setNauralCategory(c.getString(c.getColumnIndex(TaskEntry.KEY_NATURAL_CATEGORY)));
        itemCategores.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
        itemCategores.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
        itemCategores.setUserId(c.getInt(c.getColumnIndex(TaskEntry.KEY_USER_ID)));
        itemCategores.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
        return itemCategores;
    }
    public ItemsStore cursorToNoteForTableConvertStore(Cursor c) {
        ItemsStore itemConvertStore = new ItemsStore();
        itemConvertStore.setId(c.getInt(c.getColumnIndex(TaskEntry._ID)));
        itemConvertStore.setConvertTo(c.getString(c.getColumnIndex(TaskEntry.KEY_TYPE_STORE_CONVERT)));
        itemConvertStore.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
        itemConvertStore.setCreatedTime(c.getString(c.getColumnIndex(TaskEntry.KEY_TIME)));
        itemConvertStore.setUserId(c.getInt(c.getColumnIndex(TaskEntry.KEY_USER_ID)));
        itemConvertStore.setNotes(c.getString(c.getColumnIndex(TaskEntry.KEY_NOTES)));
        return itemConvertStore;
    }

//    public ArrayList<ItemsStore> getListNote(String sql) {
//
//        ArrayList<ItemsStore> list = new ArrayList<>();
//        Cursor cursor = getAll(sql);
//
//        while (!cursor.isAfterLast()) {
//            list.add(cursorToNoteForTableDailyMovements(cursor));
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        return list;
//    }
//    public Cursor getAll(String sql) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        close();
//        return cursor;
//    }

   }






















