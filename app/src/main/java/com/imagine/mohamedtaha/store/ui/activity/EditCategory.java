package com.imagine.mohamedtaha.store.ui.activity;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditCategory extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Identifer category data loader
    private static final int EXISTING_CATEGORY_LOADER = 0;

    //Content URI for the existing category(null if it's a new category)
    private Uri mCurrentCategoryUri;
    private EditText ETNameCategory, ETNaturalCategory, ETNotes;

    //Boolean flag that keeps track of whether the category has been edited (true) or not(false)
    private boolean mCategoryHAsChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mCategoryHAsChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_category);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new Category or editing an existing one.
        Intent intent = getIntent();
        mCurrentCategoryUri = intent.getData();

        // If the intent DOES NOT contain a Category content URI, then we know that we are
        // creating a new Category.
        if (mCurrentCategoryUri == null) {
            //This is anew Category ,so change the app bar to say "Add A Category"
            setTitle(getString(R.string.editor_activity_title_new_category));
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a Category that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            //Otherwise this is an existing Category,so change app bar to say "Edit Category"
            setTitle(getString(R.string.editor_activity_title_edit_category));
            //Initialize a loader to read the Category data from database
            //and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_CATEGORY_LOADER, null, this);

        }
//Find all relevant views that we will need to read user input from
        ETNameCategory = (EditText) findViewById(R.id.ETNameCategory);
        ETNaturalCategory = (EditText) findViewById(R.id.ETNaturalGategory);
        ETNotes = (EditText) findViewById(R.id.EtNotes);
        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        ETNameCategory.setOnTouchListener(mTouchListener);
        ETNaturalCategory.setOnTouchListener(mTouchListener);
        ETNotes.setOnTouchListener(mTouchListener);
    }
    private void saveCategory(){
        //Read from input field,use trim to eliminate leading or trailing wgite spase
        String nameCategoryString = ETNameCategory.getText().toString().trim();
        String naturalCategoryString = ETNaturalCategory.getText().toString().trim();
        String notesString = ETNotes.getText().toString().trim();
        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentCategoryUri == null && TextUtils.isEmpty(nameCategoryString)){
            Toast.makeText(getApplicationContext(), "not should leave field name emputy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and Category attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(TaskEntry.KEY_NAME_CATEGORY,nameCategoryString);
        values.put(TaskEntry.KEY_NATURAL_CATEGORY,naturalCategoryString);
        values.put(TaskEntry.KEY_NOTES,notesString);
        values.put(TaskEntry.KEY_DATE, getDateTime());

//Dtermine if this is a new or existing Category bychecking if mCurrentCategoryURi is null or not
        if (mCurrentCategoryUri == null){
            //This is a new Category , so insert a new Category into the provider,
            //returning the content URI for the new category
            Uri newUri = getContentResolver().insert(TaskEntry.CONTENT_URI,values);
            //Show a Toast message depending on whether or not the inserting was successful.
            if (newUri == null){
                //If the new content URI is null,then there was an error with inserting
                Toast.makeText(this,getString(R.string.editor_insert_category_failed),Toast.LENGTH_LONG).show();
            }else {
                //Otherwise ,the inserting was successful and we can display a toast
                Toast.makeText(this, getString(R.string.editor_insert_category_successful), Toast.LENGTH_SHORT).show();
            }
        }else {
            //Otherwise this is an Existing Category , soupdate the category with content URI: mCurrentCategoryUri
            //and pass in the new ContentValues. pasin null for the selection and selection args
            //becausa mCurrentCategoryUri will already identify the correct row in the database that we want to modify.

           int rowsAffected = getContentResolver().update(mCurrentCategoryUri, values,null,null);
            //Show a toast message depending on whether or not the update was successful.
                if (rowsAffected == 0){
                    //If no rows were affected,then there was an error with the update
                 //   Toast.makeText(this, getString(R.string.editor_update_category_failed), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, getString(R.string.editor_update_category_failed), Toast.LENGTH_SHORT).show();

                }else {
                    //Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_update_category_successful), Toast.LENGTH_SHORT).show();
                }


        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //If the category hasn't changed,continue with handling back button press
        if (!mCategoryHAsChanged){
            super.onBackPressed();
            return;
        }
        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClockListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //USer clicked "discard" button,close the current activity.
                        finish();
                    }
                };
                //show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClockListener);

    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener){
        //Create an AlertDialog.Builder and set the message,and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard,discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked the "Keep editing" button ,so dismiss the dialog and continue editing the category
                if (dialog != null){
                    saveCategory();
                    dialog.dismiss();
                    finish();
                }
            }
        });
        //Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showDeleteConfirmationDialog(){
        //Create an AlertDialog.Builder and set the message,and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.BTDelete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked the "Delete" button,so delete the Category
                deleteCategory();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //USer clciked the "cancel" button ,so dismiss the dialog and continue editing the category
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
        //Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    
    //Performthe deletion of the category in the database
    private void deleteCategory(){
        //Only perform the delete if this is an exisitng category
        if (mCurrentCategoryUri != null){
            //Call the contentResolver to delete the category at the given content URI.
            //Pass in null for the selection and selection args because the mCurrentCategoryUri
            //content URI already identifies the Category that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentCategoryUri, null,null);
            //Show a toast mesage depending on whether ornot teh delete was successful.
                if (rowsDeleted == 0){
                    //If no rows were deleted,then there was an error with the delete.
                    Toast.makeText(this, getString(R.string.editor_delete_Category_failed), Toast.LENGTH_SHORT).show();


                }else {
                    //Otherwise,the delete was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_delete_category_successful), Toast.LENGTH_SHORT).show();
                }

        }
        //Close Activity
        finish();
        
        
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String [] projection = {
                TaskEntry._ID,TaskEntry.KEY_NAME_CATEGORY,TaskEntry.KEY_NATURAL_CATEGORY,TaskEntry.KEY_NOTES};
     //   This loader will ecexute the ContentProvider's  query method on a background thread
        return new CursorLoader(this,//Parent actvivty context
                 mCurrentCategoryUri, //Query the content URI for the current pet
                projection, // columns toinclude in the resulting Cursor
               null,null,null  );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Bail early if the cursor is null or there is less than 1 row in the cursor
        if (data == null || data.getCount() <1){
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (data.moveToFirst()){
            //Find the columns of category attributes that we're interested in
            int nameCategoryIndex =data.getColumnIndex(TaskEntry.KEY_NAME_CATEGORY);
            int nameNaturalIndex = data.getColumnIndex(TaskEntry.KEY_NATURAL_CATEGORY);
            int notesIndes = data.getColumnIndex(TaskEntry.KEY_NOTES);
            //Extract out the value from the Cursor for the given column index
            String nameCategory = data.getString(nameCategoryIndex);
            String nameNutral = data.getString(nameNaturalIndex);
            String notes = data.getString(notesIndes);

            //Update the views on the screen with the values from the database
            ETNameCategory.setText(nameCategory);
            ETNaturalCategory.setText(nameNutral);
            ETNotes.setText(notes);



        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //If the loader is invalidated ,clear out all the data from the input fileds.
        ETNameCategory.setText("");
        ETNaturalCategory.setText("");
        ETNotes.setText("");

    }
    //get datetime
    public static String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }
}



















