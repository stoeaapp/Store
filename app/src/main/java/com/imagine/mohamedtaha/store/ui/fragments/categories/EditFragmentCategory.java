package com.imagine.mohamedtaha.store.ui.fragments.categories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.data.TaskContract;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Categories;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.imagine.mohamedtaha.store.Constant.ID_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.ID_STORE;
import static com.imagine.mohamedtaha.store.Constant.NAME_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.NATURAL_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditFragmentCategory extends DialogFragment implements DialogInterface.OnClickListener {
    private StoreViewModel viewModel;
    private static final String EXTRA_ID = "id";
    EditText ETCategoryName, ETNaturalCategory, EtNotesFF;
    private TextView TVTitleCategory;
    private Button BTAddCategory, BTDeleteCategory;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    TaskDbHelper dbHelper;
    long id;
    public static EditFragmentCategory newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, id);
        EditFragmentCategory editFragmentCategory = new EditFragmentCategory();
        editFragmentCategory.setArguments(bundle);
        return editFragmentCategory;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_content_provider, null);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
        TVTitleCategory = (TextView) view.findViewById(R.id.TVTitleCategory);
        ETCategoryName = (EditText) view.findViewById(R.id.ETNameCategoryFF);
        ETNaturalCategory = (EditText) view.findViewById(R.id.ETNaturalGategoryFF);
        EtNotesFF = (EditText) view.findViewById(R.id.EtNotesFF);
        BTAddCategory = (Button) view.findViewById(R.id.BTAddCategory);
        BTDeleteCategory = (Button) view.findViewById(R.id.BTDeleteCategory);

        boolean nn = true;
        if (getArguments() != null && getArguments().getLong(EXTRA_ID) != 0) {
            id = getArguments().getLong(EXTRA_ID);
            BTAddCategory.setText(getString(R.string.action_edit));
            BTDeleteCategory.setVisibility(View.VISIBLE);
            TVTitleCategory.setText(getString(R.string.edit_category_titile));
//             uri = Uri.withAppendedPath(TaskContract.TaskEntry.CONTENT_URI,String.valueOf(id));
//            Cursor cursor = getActivity().getContentResolver().query(uri,null,null,null,null);
//            if (cursor.moveToNext()){
//                nn =false;
            ETCategoryName.setText(getArguments().getString(NAME_CATEGORY));
            ETNaturalCategory.setText(getArguments().getString(NATURAL_CATEGORY));
            EtNotesFF.setText(getArguments().getString(NOTES));
//            }
//            cursor.close();

        }
        //__________________________________This with onClick Method_____________________
        //   return new AlertDialog.Builder(getActivity()).setTitle(nn ?"AddTitle": "Edit title").setView(view)
        //             .setPositiveButton("Save",this ).setNegativeButton("Delete",null).setIcon(R.drawable.storesmall).create();
        BTAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory();

            }
        });
        BTDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();

            }
        });
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setIcon(R.drawable.logoysyp);
        dialog = builder.create();
        dialog.show();
        return dialog;


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

      /*  ContentValues values =new ContentValues();
        values.put(TaskContract.TaskEntry.KEY_NAME_CATEGORY,ETCategoryName.getText().toString());
        values.put(TaskContract.TaskEntry.KEY_NATURAL_CATEGORY,ETNaturalCategory.getText().toString());
        if (id != 0){
            Uri uri = Uri.withAppendedPath(TaskContract.TaskEntry.CONTENT_URI,String.valueOf(id));
            getContext().getContentResolver().update(uri,values,null,null);
        }else {
            getContext().getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI,values);
        }*/

    }

    private void saveCategory() {
        //Read from input field,use trim to eliminate leading or trailing wgite spase
        String nameCategoryString = ETCategoryName.getText().toString().trim();
        String naturalCategoryString = ETNaturalCategory.getText().toString().trim();
        String notesString = EtNotesFF.getText().toString().trim();
        //  boolean isExist = dbHelper.isExistCategoryName(nameCategoryString);

        //  String notesString = ETNotes.getText().toString().trim();
        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (getArguments() == null && TextUtils.isEmpty(nameCategoryString)) {
            ETCategoryName.setError(getString(R.string.error_empty_text));
            ETCategoryName.requestFocus();
            //  Toast.makeText(getActivity(), getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
            return;
        }

      /*  if (isExist ==true){
            ETCategoryName.requestFocus();
            ETCategoryName.setError(getString(R.string.error_exist_category));
            return;
        }*/

        // Create a ContentValues object where column names are the keys,
        // and Category attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.KEY_NAME_CATEGORY, nameCategoryString);
        values.put(TaskContract.TaskEntry.KEY_NATURAL_CATEGORY, naturalCategoryString);
        values.put(TaskContract.TaskEntry.KEY_NOTES, notesString);
        values.put(TaskContract.TaskEntry.KEY_DATE, getDateTime());

//Dtermine if this is a new or existing Category bychecking if mCurrentCategoryURi is null or not
        if (getArguments() == null) {
            Categories itemCategory = new Categories(nameCategoryString, nameCategoryString, notesString);
            itemCategory.setCreatedAt(getDate());
            itemCategory.setTime(getTime());
            viewModel.insertCategory(itemCategory);

//            //This is a new Category , so insert a new Category into the provider,
//            //returning the content URI for the new category
//            Uri newUri = getContext().getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI,values);
//            //Show a Toast message depending on whether or not the inserting was successful.
//            if (newUri == null){
//                //If the new content URI is null,then there was an error with inserting
//                Toast.makeText(getContext(),getString(R.string.editor_insert_category_failed),Toast.LENGTH_LONG).show();
//            }else {
//                //Otherwise ,the inserting was successful and we can display a toast
//                Toast.makeText(getContext(), getString(R.string.editor_insert_category_successful), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();

            //      }
        } else {
            viewModel.updateCategory(getArguments().getLong(ID_CATEGORY), nameCategoryString, nameCategoryString, notesString, getDate());
            //Otherwise this is an Existing Category , soupdate the category with content URI: mCurrentCategoryUri
            //and pass in the new ContentValues. pasin null for the selection and selection args
            //becausa mCurrentCategoryUri will already identify the correct row in the database that we want to modify.

//            //Show a toast message depending on whether or not the update was successful.
//            if (rowsAffected == 0){
//                //If no rows were affected,then there was an error with the update
//                //   Toast.makeText(this, getString(R.string.editor_update_category_failed), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), getString(R.string.editor_update_category_failed), Toast.LENGTH_SHORT).show();
//
//            }else {
//                //Otherwise, the update was successful and we can display a toast.
//                Toast.makeText(getContext(), getString(R.string.editor_update_category_successful), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }


        }


    }

    private void showDeleteConfirmationDialog() {
        //Create an AlertDialog.Builder and set the message,and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        //Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Performthe deletion of the category in the database
    private void deleteCategory() {
        //Only perform the delete if this is an exisitng category
        if (id != 0) {
            //Call the contentResolver to delete the category at the given content URI.
            //Pass in null for the selection and selection args because the mCurrentCategoryUri
            //content URI already identifies the Category that we want.
//            uri = Uri.withAppendedPath(TaskContract.TaskEntry.CONTENT_URI,String.valueOf(id));
//            boolean isExistDialyMovements = dbHelper.isNameCategoryUsedDailyMovements(id);
//            boolean isExistStokeWearehouse = dbHelper.isNameCategoryUsedStokeWearhouse(id);
//
//            if (isExistDialyMovements == true || isExistStokeWearehouse == true) {
//                Toast.makeText(getContext(), getString(R.string.this_category_used), Toast.LENGTH_SHORT).show();
//                return;
//            }
            viewModel.deleteCategory(requireArguments().getLong(ID_CATEGORY));


//
//            int rowsDeleted = getActivity().getContentResolver().delete(uri, null,null);
//            //Show a toast mesage depending on whether ornot teh delete was successful.
//            if (rowsDeleted == 0){
//                //If no rows were deleted,then there was an error with the delete.
//                Toast.makeText(getContext(), getString(R.string.editor_delete_Category_failed), Toast.LENGTH_SHORT).show();
//
//
//            }else {
//                //Otherwise,the delete was successful and we can display a toast.
//                Toast.makeText(getContext(), getString(R.string.editor_delete_category_successful), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }

        } else {
            Toast.makeText(getContext(), "Error : ", Toast.LENGTH_SHORT).show();

        }
        //Close Activity
        // finish();


    }

    //get datetime
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}





















