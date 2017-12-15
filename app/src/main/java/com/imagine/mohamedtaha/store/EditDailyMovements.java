package com.imagine.mohamedtaha.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

public class EditDailyMovements extends AppCompatActivity {
    private Spinner SPCodeStoreDialy, SPCodeCategoryDialy, SPermissionDaily, SPCovertTo;
    private EditText ETIncoming, ETIssued;
    TaskDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_daily_movements);
        dbHelper = new TaskDbHelper(this);
       // SPCodeCategoryDialy = (Spinner)findViewById(R.id.SPCodeCategoryDialy);
       // SPCodeStoreDialy =(Spinner)findViewById(R.id.SPCodeStoreDialy);
       // SPermissionDaily = (Spinner)findViewById(R.id.SPCovertTo);

        ETIncoming = (EditText)findViewById(R.id.ETIncoming);
        ETIssued = (EditText)findViewById(R.id.ETIssued);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        switch (idItem){
            case R.id.action_save:
                break;
            case R.id.action_delete:
        }

        return super.onOptionsItemSelected(item);
    }
    public void loadSpinnerDataForCategory(){
        ArrayList<String> IDCategory = dbHelper.getDataForSpinnerCategory();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,IDCategory);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCodeStoreDialy.setAdapter(arrayAdapter);
    }
    public void loadSpinnerDataForStore(){
        ArrayList<String> IDStore = dbHelper.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,IDStore);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCodeStoreDialy.setAdapter(arrayAdapter);
    }

}
