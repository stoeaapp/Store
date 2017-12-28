package com.imagine.mohamedtaha.store.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.EditStokeWarehouse;
import com.imagine.mohamedtaha.store.R;
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
    private static final String DATABASE_NAME = "stores.db";
    Context mContext;

    // If you change the database schema, you must increment the database version
    private static final int VERSION =2;

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
                        TaskEntry.KEY_USER_ID  + " INTEGER,"                 + TaskEntry.KEY_DATE   + " DATE,"         +
                        TaskEntry.KEY_TIME    + " TIME" + ")" ;
        //Table Stores
        final String CREATE_TABLE_STORES =
                "CREATE TABLE "         + TaskEntry.TABLE_STORE + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY," +
               TaskEntry.KEY_TYPE_STORE + " TEXT NOT NULL,"     + TaskEntry.KEY_NOTES  +                " TEXT," +
                  TaskEntry.KEY_USER_ID + " INTEGER,"           +   TaskEntry.KEY_DATE + " DATE,"                +
                  TaskEntry.KEY_TIME    + " TIME"+ ")";
        //Table Permission
        final String CREATE_TABLE_PERMISSION =
                "CREATE TABLE "                + TaskEntry.TABLE_PERMISSION + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY,"+
                 TaskEntry.KEY_NAME_PERMISSION + " TEXT NOT NULL,"          + TaskEntry.KEY_NOTES  + " TEXT,"               +
                        TaskEntry.KEY_USER_ID  + " INTEGER,"                + TaskEntry.KEY_DATE   + " DATE, "              +
                        TaskEntry.KEY_TIME    + " TIME"+ ")";
        //Table Users
        final String CREATE_TABLE_USERS =
                "CREATE TABLE " + TaskEntry.TABLE_USERS + " (" +TaskEntry._ID + " INTEGER PRIMARY KEY," + TaskEntry.KEY_NAME_USER +
                        " TEXT NOT NULL," + TaskEntry.KEY_PASSWORD_USERS + " TEXT NOT NULL," + TaskEntry.KEY_RETRY_PASSWORD       +
                        " TEXT NOT NULL," + TaskEntry.KEY_MOBILE + " TEXT NOT NULL," + TaskEntry.KEY_EMAIL + " TEXT," +
                        TaskEntry.KEY_DATE +        " DATE,"  +  TaskEntry.KEY_TIME    + " TIME" + ")";
        //Table STOCKING_WAREHOUSE
        final String CREATE_TABLE_STOCKING_WAREHOUSE =
                "CREATE TABLE "            + TaskEntry.TABLE_STOCKING_WAREHOUSE   + " (" +TaskEntry._ID + " INTEGER PRIMARY KEY," +
                TaskEntry.KEY_CATEGORY_ID  + " INTEGER," + TaskEntry.KEY_STORE_ID + " INTEGER,"         +
               TaskEntry.KEY_FIRST_BALANCE + " INTEGER," + TaskEntry.KEY_NOTES    + " TEXT,"            + TaskEntry.KEY_USER_ID   +
                        " INTEGER,"        + TaskEntry.KEY_DATE                   + " DATE,"             +  TaskEntry.KEY_TIME     + " TIME"+")";

        //Table DAILY_MOVEMENTS
        final String CREATE_TABLE_DAILY_MOVEMENTS =
           "CREATE TABLE "             + TaskEntry.TABLE_DAILY_MOVEMENTS + " (" + TaskEntry._ID       + " INTEGER PRIMARY KEY," +
           TaskEntry.KEY_CATEGORY_ID   + " INTEGER,"                     + TaskEntry.KEY_STORE_ID     + " INTEGER,"             +
           TaskEntry.KEY_PERMISSION_ID + " INTEGER,"                     + TaskEntry.KEY_INCOMING     + " INTEGER NOT NULL DEFAULT 0,"             +
           TaskEntry.KEY_ISSUED        + " INTEGER NOT NULL DEFAULT 0,"                     + TaskEntry.KEY_CONVERT_FROM + " TEXT,"                +
           TaskEntry.KEY_CONVERT_TO    + " TEXT,"                        +TaskEntry.KEY_USER_ID       +" INTEGER,"              +
           TaskEntry.KEY_DATE          + " DATE,"                        +TaskEntry.KEY_TIME    + " TIME"+  ")";

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
    //_____________________________Methods Store____________________________

    //_____________________________Insert Table Store____________________________
    public long addStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_TYPE_STORE,itemsStore.getTypeStore());
        values.put(TaskEntry.KEY_NOTES,itemsStore.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());

        //Insert row
        long store_id = db.insert(TaskEntry.TABLE_STORE,null,values);

       /* if (store_id !=0 ){
            Toast.makeText(mContext, "تم الحفظ", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, " لم يتم الحفظ", Toast.LENGTH_SHORT).show();


        }*/

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

    public boolean isExistCategoryName(String categoryName){
        String whereClause = TaskEntry.KEY_NAME_CATEGORY +"=?";
        String [] whereArgs = new String[]{categoryName};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.query(TaskEntry.TABLE_CATEGORIES,null,whereClause,whereArgs,null,null,null);
        boolean exist = (search.getCount() > 0);
        search.close();db.close();
        return exist;
    }

    //____________________Update Store______________________
    public long updateStore(ItemsStore itemsStore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry._ID,itemsStore.getId());
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
        String []columns = {TaskEntry._ID,TaskEntry.KEY_TYPE_STORE,TaskEntry.KEY_DATE,TaskEntry.KEY_TIME};
        Cursor c = db.query(TaskEntry.TABLE_STORE, columns,null,null,null,null,null);
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
        return itemsStores;
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
        values.put(TaskEntry.KEY_NOTES ,itemsPermission.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());        //Insert row
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
    //____________________Update Permission______________________
    public int updatePermission(ItemsStore itemsPermission){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_NAME_PERMISSION,itemsPermission.getNamePermission());
        values.put(TaskEntry.KEY_NOTES,itemsPermission.getNotes());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsPermission.getId())};
        //updating row
        return db.update(TaskEntry.TABLE_PERMISSION,values, selection,selectionArgs);

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
        values.put(TaskEntry.KEY_CONVERT_TO,itemsDailyMovements.getId_code_store());
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
        values.put(TaskEntry.KEY_CONVERT_TO,itemsDailyMovements.getId_code_store());
        values.put(TaskEntry.KEY_DATE, getDate());
        values.put(TaskEntry.KEY_TIME, getTime());
        String selection = TaskEntry._ID + "=?";
        String[] selectionArgs =new String[]{String.valueOf(itemsDailyMovements.getId())};
        //updating row
        return db.update(TaskEntry.TABLE_DAILY_MOVEMENTS,values, selection,selectionArgs);

    }
    //____________________Delete DailyMovements____________________________
    public void deleteDailyMovements(ItemsStore itemsDailyMovements){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskEntry.TABLE_DAILY_MOVEMENTS, TaskEntry._ID + " = ?",new String[]{String.valueOf(itemsDailyMovements.getId())});
    }
    //____________________get All SDailyMovements____________________________

    public ArrayList<ItemsStore>getAllDailyMovements(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tdm. "+TaskEntry.KEY_INCOMING      +
                ", tdm. "+TaskEntry.KEY_ISSUED           + ", tdm. " +TaskEntry.KEY_CONVERT_TO   +
                ", tdm. "+TaskEntry.KEY_DATE             + " FROM " + TaskEntry.TABLE_STORE      + " ts, "
                +TaskEntry.TABLE_CATEGORIES              + " tc,"+ TaskEntry.TABLE_PERMISSION    + " tp, "
                +  TaskEntry.TABLE_DAILY_MOVEMENTS       + " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               +" ON tp."       +TaskEntry._ID + " = " + "tdm."
                +TaskEntry.KEY_PERMISSION_ID;

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
            itemDailyMovement.setId_conert_to(c.getInt(c.getColumnIndex(TaskEntry.KEY_CONVERT_TO)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
    }

    //____________________get report in Issued and Incoming by name category and type store____________________________

    public ArrayList<ItemsStore>getIssedAndIncomingbyNameCategoryAndTypeStore(String nameCategory,String typeStore){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  SUM tdm."         +TaskEntry.KEY_INCOMING +
                ", tdm. " + TaskEntry.KEY_ISSUED      + " FROM " + TaskEntry.TABLE_STORE      + " ts, "
                +TaskEntry.TABLE_CATEGORIES              + " tc,"+ TaskEntry.TABLE_DAILY_MOVEMENTS  + " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               +" ON tp."       +TaskEntry._ID + " = " + "tdm."
                +TaskEntry.KEY_PERMISSION_ID;

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
            itemDailyMovement.setId_conert_to(c.getInt(c.getColumnIndex(TaskEntry.KEY_CONVERT_TO)));
            itemDailyMovement.setCreatedDate(c.getString(c.getColumnIndex(TaskEntry.KEY_DATE)));
            //adding to todo list
            itemDailyMovements.add(itemDailyMovement);
        }
        return itemDailyMovements;
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
       //____________________get All DailyMovements by Search____________________________

    public ArrayList<ItemsStore>getAllDailyMovementsBySearch(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ItemsStore> itemDailyMovements = new ArrayList<ItemsStore>();
        String selectQuery = "SELECT  DISTINCT "         +"tdm. "   +TaskEntry._ID +
                ", ts. " + TaskEntry.KEY_TYPE_STORE      + ", tc."  +TaskEntry.KEY_NAME_CATEGORY +
                ", tp."  + TaskEntry.KEY_NAME_PERMISSION + ", tdm. "+TaskEntry.KEY_INCOMING      +
                ", tdm. "+TaskEntry.KEY_ISSUED           + ", tdm. " +TaskEntry.KEY_CONVERT_TO   +
                ", tdm. "+TaskEntry.KEY_DATE             + " FROM " + TaskEntry.TABLE_STORE      + " ts, "
                +TaskEntry.TABLE_CATEGORIES              + " tc,"+ TaskEntry.TABLE_PERMISSION    + " tp, "
                +  TaskEntry.TABLE_DAILY_MOVEMENTS       + " tdm  INNER JOIN "                   +
                TaskEntry.TABLE_STORE                    + " ON ts."     + TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_STORE_ID                 +" INNER JOIN  "                        +
                TaskEntry.TABLE_CATEGORIES               + " ON tc."      +TaskEntry._ID + " = " + "tdm."
                + TaskEntry.KEY_CATEGORY_ID              +" INNER JOIN  "                        +
                TaskEntry.TABLE_PERMISSION               +" ON tp."       +TaskEntry._ID + " = " + "tdm."
                +TaskEntry.KEY_PERMISSION_ID + " WHERE tp. " +TaskEntry.KEY_NAME_PERMISSION + " LIKE '%" +
                search + "%'" ;;

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
            itemDailyMovement.setId_conert_to(c.getInt(c.getColumnIndex(TaskEntry.KEY_CONVERT_TO)));
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

    //____________________getting all StokeWareHouse by Search____________________________

    public ArrayList<ItemsStore>getAllStokeHouseBySearch(String search){
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
}