package com.imagine.mohamedtaha.store.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;

import java.net.UnknownServiceException;

/**
 * Created by MANASATT on 25/11/17.
 */

public class TaskContentProvider extends ContentProvider {
    private TaskDbHelper mTaskDbHelper;
    // Define final integer constants for the directory of tasks and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int CATEGORY = 100;
    public static final int CATEGORY_WITH_ID = 101;

    public static final int STORE =200;
    public static final int STORE_WITH_ID =201;

    public static final int PERMISSION =300;
    public static final int PERMISSION_WITH_ID =301;

    public static final int USERS = 400;
    public static final int USERS_WITH_ID =401;

    public static final int STOCKING_WAREHOUSE = 500;
    public static final int STOCKING_WAREHOUSE_WITH_ID =501;

    public static final int DAILY_MOVEMENTS = 600;
    public static final int DAILY_MOVEMENTS_WITH_ID =601;
     //  Define a static buildUriMatcher method that associates URI's with their int match
    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //Add matches with addURI(String authrity, String path, int code)
        //directory
        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_TASKS,CATEGORY);
        //single item
        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_TASKS + "/#", CATEGORY_WITH_ID);
        return uriMatcher;
    }

    //  Declare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMAtcher = buildUriMatcher();
 /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
    */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTaskDbHelper = new TaskDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
       final SQLiteDatabase database = mTaskDbHelper.getReadableDatabase();
        int match = sUriMAtcher.match(uri);
        Cursor returnCursor;
        switch (match){
            case CATEGORY:
                returnCursor = database.query(TaskEntry.TABLE_CATEGORIES,projection,selection,selectionArgs
                        ,null,null,sortOrder);
                break;
            case CATEGORY_WITH_ID:
                //using selection and selectionArgs
                //URI: content://<AUTHORITY>/tasks/#
                String id = uri.getPathSegments().get(1);
                //Selection id the _ID column = ? the selection args = the row ID from the URI
                String mSelection = "_id?";
                String [] mSelectionArgs = new String[]{id};
                returnCursor = database.query(TaskEntry.TABLE_CATEGORIES,projection,mSelection,mSelectionArgs,
                        null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase database = mTaskDbHelper.getWritableDatabase();
        int match =sUriMAtcher.match(uri);
        Uri returnUri ;
        switch (match){
            case CATEGORY:
                //Inserting values into tasks table
                long id = database.insert(TaskEntry.TABLE_CATEGORIES,null,values);
                if (id>0){
                    //success
                    returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                }else {
                    throw new android.database.SQLException("Falid toinsert row into " + uri);
                }
                break;
            //defualt case throws an UnsupportrdOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = mTaskDbHelper.getWritableDatabase();
        int match = sUriMAtcher.match(uri);
        //Keep track fo the number of deleted tasks
        int tasksDeleted ; //starts as 0
        // [Hint] Use selections to delete an item by its row ID
        switch (match){
            //Handle the single item case, recognized by Id included in the URI path
            case CATEGORY_WITH_ID:
                //Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                //Get selections/selectionArgs to filter for this ID
                tasksDeleted = database.delete(TaskEntry.TABLE_CATEGORIES,"_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);

        }
        if (tasksDeleted != 0){
            //A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri,null);
        }
        //Return the number of tasks deleted
        return tasksDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}















