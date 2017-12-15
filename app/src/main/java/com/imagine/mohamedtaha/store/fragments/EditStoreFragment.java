package com.imagine.mohamedtaha.store.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

import static com.imagine.mohamedtaha.store.fragments.AddStoreFragment.ID_STORE;
import static com.imagine.mohamedtaha.store.fragments.AddStoreFragment.NOTES_STORE;
import static com.imagine.mohamedtaha.store.fragments.AddStoreFragment.TYPE_STORE;


public class EditStoreFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private EditText ETTypeStore,ETNotes;
    private Button BTAddOrUpdate, BTDelete;
    private TextView TVTitleStore;
    long id ;
    Bundle intent;
    TaskDbHelper dbHelper;
    AlertDialog dialog;
  /*  public static EditStoreFragment newInstance(Bundle getBundle){
        bundle = new Bundle();
        String id =
        bundle.putBundle(EXTRA_ID, getBundle);

        EditStoreFragment editStoreFragment = new EditStoreFragment();
        editStoreFragment.setArguments(bundle);
        return editStoreFragment;
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_store,null);
        TVTitleStore = (TextView)view.findViewById(R.id.TVTitleStore);
        ETTypeStore = (EditText)view.findViewById(R.id.ETTypeStoreStore);
        ETNotes = (EditText)view.findViewById(R.id.EtNotesStore);
        BTAddOrUpdate = (Button)view.findViewById(R.id.BTAddStore);
        BTDelete = (Button)view.findViewById(R.id.BTDeleteStore);
        dbHelper = new TaskDbHelper(getContext());
         intent = getArguments();

        boolean saveState = true;
        if (intent != null ){
            saveState = false;
            BTAddOrUpdate.setText(getString(R.string.BTUpdate));
            TVTitleStore.setText(getString(R.string.update_store_titile));
            BTDelete.setVisibility(View.VISIBLE);
            ETTypeStore.setText(intent.getString(TYPE_STORE));
            ETNotes.setText(intent.getString(NOTES_STORE));
        }
        BTAddOrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStore();

            }
        });
        BTDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

       // return new AlertDialog.Builder(getActivity()).setTitle(saveState? "Add Store" : "Edit store").setView(view)
         //      .create();
        return dialog ;

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    public void saveStore(){
        String typeStore =ETTypeStore.getText().toString();
        String notes = ETNotes.getText().toString();
        if ( intent == null && TextUtils.isEmpty(typeStore) ||TextUtils.isEmpty(typeStore) ){
           // ETTypeStore.setError("not should leave field name emputy");
           Toast.makeText(getContext(), getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
            return;
        }
        if (intent == null) {
            ItemsStore itemsStoreSave = new ItemsStore();
            itemsStoreSave.setTypeStore(typeStore);
            itemsStoreSave.setNotes(notes);
            if (itemsStoreSave == null) {
                Toast.makeText(getContext(),getString(R.string.error_save_store), Toast.LENGTH_LONG).show();
            }else {
                dbHelper.addStore(itemsStoreSave);
                Toast.makeText(getContext(), getString(R.string.save_store), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }else {
            ItemsStore itemsStoreUpdate = new ItemsStore();
            itemsStoreUpdate.setId(intent.getInt(ID_STORE));
            itemsStoreUpdate.setTypeStore(typeStore);
            itemsStoreUpdate.setNotes(notes);
            if (itemsStoreUpdate != null){
                dbHelper.updateStore(itemsStoreUpdate);
                Toast.makeText(getContext(), getString(R.string.update_store), Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }else {
                Toast.makeText(getContext(), getString(R.string.error_update_store), Toast.LENGTH_LONG).show();
            }
        } }
    public void deleteStore(){
        if (intent != null){
            String typeStore =ETTypeStore.getText().toString();
            String notes = ETNotes.getText().toString();
            ItemsStore itemsStoreDelte = new ItemsStore();
            itemsStoreDelte.setId(intent.getInt(ID_STORE));
            itemsStoreDelte.setTypeStore(typeStore);
            itemsStoreDelte.setNotes(notes);
            if (itemsStoreDelte != null){
                dbHelper.deleteStore(itemsStoreDelte);
                Toast.makeText(getContext(), getString(R.string.delete_store), Toast.LENGTH_LONG).show();
                dialog.dismiss();

               // getActivity().finish();

            }else {
                Toast.makeText(getContext(), getString(R.string.error_delete_store), Toast.LENGTH_LONG).show();
            }
        }else {
         // Toast.makeText(getActivity(), "Not Data For Deleted", Toast.LENGTH_LONG).show();
            return;
        }

    }
    private void showDeleteConfirmationDialog(){
        //Create an AlertDialog.Builder and set the message,and click listeners
        //for the positive and negative buttons on the dialog.
       AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked the "Delete" button,so delete the Category
                deleteStore();

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
}