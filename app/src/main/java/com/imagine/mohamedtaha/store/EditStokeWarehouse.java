package com.imagine.mohamedtaha.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

public class EditStokeWarehouse extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner SPCodeCategory,SPCodeStore;
    private EditText ETFisrtBalance,EtNotes;
    private Button addStokeWarehouse;

    String SpinnerCategory,SpinnerStore;
    TaskDbHelper dbHelper;
    Bundle intent ;
    long idSpinnerCategory,idSpinnerStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent().getExtras();
        if (intent == null){
            setTitle("Add StokeWarehouse");
        }else {
            setTitle("Edit StokeWarehouse");

        }

        dbHelper = new TaskDbHelper(this);
        setContentView(R.layout.edit_stoke_warehouse);
        SPCodeCategory = (Spinner)findViewById(R.id.SPCodeCategory);
        SPCodeCategory.setOnItemSelectedListener(this);

        SPCodeStore = (Spinner)findViewById(R.id.SPCodeStore);
        loadSpinnerDataForCategory();
        loadSpinnerDataForStore();

        ETFisrtBalance = (EditText)findViewById(R.id.ETFirstBalance);
        EtNotes = (EditText)findViewById(R.id.ETNotesStokewarehouse);


        addStokeWarehouse = (Button)findViewById(R.id.BTAddStokeWarehouse);
        addStokeWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStokeWarehouse();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == R.id.action_save){

        }if (idItem == R.id.action_delete){

        }
        return super.onOptionsItemSelected(item);
    }
    public void loadSpinnerDataForCategory(){
        ArrayList<String> IDCategory = dbHelper.getDataForSpinnerCategory();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,IDCategory);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCodeCategory.setAdapter(arrayAdapter);
    }
    public void loadSpinnerDataForStore(){
        ArrayList<String> IDStore = dbHelper.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,IDStore);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCodeStore.setAdapter(arrayAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerCategory = parent.getItemAtPosition(position).toString();
        SpinnerStore = parent.getItemAtPosition(position).toString();

        idSpinnerCategory =parent.getItemIdAtPosition(position+1);
        idSpinnerStore =parent.getItemIdAtPosition(position+1);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void saveStokeWarehouse(){

        String firstBalance =ETFisrtBalance.getText().toString();
        String notes = EtNotes.getText().toString();
        if (intent == null && TextUtils.isEmpty(firstBalance)){
            Toast.makeText(getApplicationContext(), "not should leave field name emputy", Toast.LENGTH_SHORT).show();
            return;
        }
        if (intent == null) {
            ItemStokeHouse itemStokeHouse = new ItemStokeHouse();
            itemStokeHouse.setId_code_category(idSpinnerCategory);
            itemStokeHouse.setId_code_store(idSpinnerStore);
            itemStokeHouse.setFirst_balanse(Integer.valueOf(firstBalance));
            itemStokeHouse.setNotes(notes);
            dbHelper.addStokeWarehouse(itemStokeHouse);
            if (itemStokeHouse == null) {
                Toast.makeText(getApplicationContext(), "Not save", Toast.LENGTH_LONG).show();

            }else {

                Toast.makeText(getApplicationContext(), "Saved Succeflly", Toast.LENGTH_LONG).show();
            }
        }else {
           // ItemStokeHouse itemsStokeWarehouseUpdate = new ItemStokeHouse();
           // itemsStokeWarehouseUpdate.setTypeStore(firstBalance);
           // itemsStokeWarehouseUpdate.setNotes(notes);
           // dbHelper.updateStore(itemsStokeWarehouseUpdate);
           // if (itemsStokeWarehouseUpdate != null){
              //  Toast.makeText(getApplicationContext(), "Updated Succeflly", Toast.LENGTH_LONG).show();

//            }else {
  //              Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_LONG).show();


    //        }

        }


    }

}


























