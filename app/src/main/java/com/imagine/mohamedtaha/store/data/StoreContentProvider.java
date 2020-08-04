package com.imagine.mohamedtaha.store.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;

/**
 * Created by MANASATT on 29/11/17.
 */

public class StoreContentProvider extends ContentProvider {
    private TaskDbHelper mTaskDbHelper;
    // Define final integer constants for the directory of tasks and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.

    public static final int STORE =200;
    public static final int STORE_WITH_ID =201;
    public static UriMatcher buildUriMatcherStore(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //Add matches with addURI(String authrity, String path, int code)
        //directory
        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_TASKSSTORE,STORE);
        //single item
        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_TASKSSTORE + "/#", STORE_WITH_ID);
        return uriMatcher;
    }
    //  Declare a static variable for the Uri matcher that you construct

    private static final UriMatcher sUriMAtcherStore = buildUriMatcherStore();
    /* onCreate() is where you should initialize anything you’ll need to setup
       your underlying data source.
       In this case, you’re working with a SQLite database, so you’ll need to
       initialize a DbHelper to gain access to it.
       */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTaskDbHelper = new TaskDbHelper(context);
        return true;    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
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
        int match =sUriMAtcherStore.match(uri);
        Uri returnUri ;
        switch (match){
            case STORE:
                //Inserting values into tasks table
                long id = database.insert(TaskEntry.TABLE_STORE,null,values);
                if (id>0){
                    //success
                    returnUri = ContentUris.withAppendedId(TaskEntry.CONTENT_URISTORE, id);
                }else {
                    throw new android.database.SQLException("Falid toinsert row into " + uri);
                }
                break;
            //defualt case throws an UnsupportrdOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
