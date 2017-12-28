package com.imagine.mohamedtaha.store;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

public class EditStore extends AppCompatActivity {
    TaskDbHelper dbHelper;
    Bundle intent ;
    EditText ETTypeStore, ETNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_category);
         intent= getIntent().getExtras();

        dbHelper = new TaskDbHelper(this);
       // TextView showTypeStore = (TextView)findViewById(R.id.ETNameCategory);
       // TextView showNotes = (TextView)findViewById(R.id.EtNotes);

          ETTypeStore = (EditText)findViewById(R.id.ETNameCategory);
           ETNotes = (EditText)findViewById(R.id.EtNotes);

        if (intent == null){
            setTitle("Add Store");
        }else {
            setTitle("Edit Store");

        }

        ETTypeStore.setText(intent.getString("name"));
        ETNotes.setText(intent.getString("notes"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    public void saveStore(){

        String typeStore =ETTypeStore.getText().toString();
        String notes = ETNotes.getText().toString();
        if (intent == null && TextUtils.isEmpty(typeStore)){
            Toast.makeText(getApplicationContext(), "not should leave field name emputy", Toast.LENGTH_SHORT).show();
            return;
        }
        if (intent == null) {
            ItemsStore itemsStoreSave = new ItemsStore();
            itemsStoreSave.setTypeStore(typeStore);
            itemsStoreSave.setNotes(notes);
            dbHelper.addStore(itemsStoreSave);
            if (itemsStoreSave == null) {
                Toast.makeText(getApplicationContext(), "Not save", Toast.LENGTH_LONG).show();

            }else {

            Toast.makeText(getApplicationContext(), "Saved Succeflly", Toast.LENGTH_LONG).show();
        }
        }else {
            ItemsStore itemsStoreUpdate = new ItemsStore();
            itemsStoreUpdate.setTypeStore(typeStore);
            itemsStoreUpdate.setNotes(notes);
            dbHelper.updateStore(itemsStoreUpdate);
            if (itemsStoreUpdate != null){
                Toast.makeText(getApplicationContext(), "Updated Succeflly", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_LONG).show();


            }

        }


    }
    public void deleteStore(){
       String typeStore =ETTypeStore.getText().toString();
        String notes = ETNotes.getText().toString();

        ItemsStore itemsStoreDelte = new ItemsStore();
        itemsStoreDelte.setTypeStore(typeStore);
        itemsStoreDelte.setNotes(notes);
        if (intent != null){
            dbHelper.deleteStore(itemsStoreDelte);
            Toast.makeText(getApplicationContext(), "Deleted Record", Toast.LENGTH_LONG).show();
            finish();

        }else {
            Toast.makeText(getApplicationContext(), "Not Deleted Record", Toast.LENGTH_LONG).show();

        }

    }
    private void showDeleteConfirmationDialog(){
        //Create an AlertDialog.Builder and set the message,and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
