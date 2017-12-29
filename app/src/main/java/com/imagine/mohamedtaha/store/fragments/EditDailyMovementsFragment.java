package com.imagine.mohamedtaha.store.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import static com.imagine.mohamedtaha.store.MainActivity.CONVERT_TO_DAILY;
import static com.imagine.mohamedtaha.store.MainActivity.IDDaily;
import static com.imagine.mohamedtaha.store.MainActivity.INCOMING_DAILY;
import static com.imagine.mohamedtaha.store.MainActivity.ISSUED_DAILY;
import static com.imagine.mohamedtaha.store.MainActivity.NAME_CATEGORY_DAILY;
import static com.imagine.mohamedtaha.store.MainActivity.NAME_PERMISSION_DAILY;
import static com.imagine.mohamedtaha.store.MainActivity.TYPE_STORE_DAILY;


public class EditDailyMovementsFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private EditText ETIssued,ETIncoming;
    private TextView TVTitleStokeWearhouse;
    private Button BTDeleteDailyMovement, BTAddDailyMovement;
    MaterialBetterSpinner SPNamePermisionDaily, SPTypeStoreDaily, SPNameCategoryDaily, SPConvertToDaily;
    Bundle intentDailyMovement;
    TaskDbHelper dbHelperDailyMovement;
    AlertDialog dialogDailyMovement;
    AlertDialog dialogDeleteDailyMovement;
    long idSpinnerCategory,idSpinnerStore, idSpinnerPermission, idSpinnerConvertTo;
    String SpinnerCategory,SpinnerStore, SpinnerPermission, SpinnerConvertTo;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_daily_movements,null);
        TVTitleStokeWearhouse = (TextView)view.findViewById(R.id.TVTitleStokeWearhouse);
        ETIncoming = (EditText)view.findViewById(R.id.ETIncoming);
        ETIssued = (EditText)view.findViewById(R.id.ETIssued);
        SPNamePermisionDaily =(MaterialBetterSpinner)view.findViewById(R.id.SPermissionDaily);
        SPTypeStoreDaily = (MaterialBetterSpinner)view.findViewById(R.id.SPCodeStoreDialy);
        SPNameCategoryDaily = (MaterialBetterSpinner)view.findViewById(R.id.SPCodeCategoryDialy);
        SPConvertToDaily = (MaterialBetterSpinner)view.findViewById(R.id.SPCovertToDaily);
        BTAddDailyMovement = (Button)view.findViewById(R.id.BTAddDailyMovement);
        BTDeleteDailyMovement = (Button)view.findViewById(R.id.BTDeleteDailyMovement);

        dbHelperDailyMovement = new TaskDbHelper(getContext());

        intentDailyMovement = getArguments();
        boolean saveState = true;
       if (intentDailyMovement != null){
            saveState = false;
            BTDeleteDailyMovement.setVisibility(View.VISIBLE);
            BTAddDailyMovement.setText(getString(R.string.BTUpdate));
           TVTitleStokeWearhouse.setText(getString(R.string.update_daily_movement_titile));
           SPNamePermisionDaily.setText(intentDailyMovement.getString(NAME_PERMISSION_DAILY));
           SPTypeStoreDaily.setText(intentDailyMovement.getString(TYPE_STORE_DAILY));
           SPNameCategoryDaily.setText(intentDailyMovement.getString(NAME_CATEGORY_DAILY));
           if (intentDailyMovement.getInt(INCOMING_DAILY)!=0){
               ETIncoming.setVisibility(View.VISIBLE);
           }
           ETIncoming.setText(String.valueOf(intentDailyMovement.getInt(INCOMING_DAILY)));
           if (intentDailyMovement.getInt(ISSUED_DAILY)!=0){
               ETIssued.setVisibility(View.VISIBLE);
           }
           ETIssued.setText(String.valueOf(intentDailyMovement.getInt(ISSUED_DAILY)));
           if (intentDailyMovement.getInt(CONVERT_TO_DAILY)!=0){
               SPConvertToDaily.setVisibility(View.VISIBLE);
           }
           SPConvertToDaily.setText(intentDailyMovement.getString(CONVERT_TO_DAILY));


       }
        BTAddDailyMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDailyMovement();
            }
        });
        BTDeleteDailyMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
        SPNamePermisionDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerPermission = parent.getItemAtPosition(position).toString();
                idSpinnerPermission =parent.getItemIdAtPosition(position+1);
                showStateVisibilty();

            }
        });
        SPNameCategoryDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerCategory = parent.getItemAtPosition(position).toString();
                idSpinnerCategory =parent.getItemIdAtPosition(position+1);
            }
        });
        SPTypeStoreDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerStore = parent.getItemAtPosition(position).toString();
                idSpinnerStore =parent.getItemIdAtPosition(position+1);
            }
        });
        SPConvertToDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerConvertTo = parent.getItemAtPosition(position).toString();
                idSpinnerConvertTo =parent.getItemIdAtPosition(position+1);
            }
        });

        loadSpinnerDataForCategory();
        loadSpinnerDataForStore();
        loadSpinnerDataForPermission();
        loadSpinnerDataForConvertTo();
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
       // builder.setTitle(saveState? "Add" : "Edit");
        builder.setView(view);
        dialogDailyMovement = builder.create();
        dialogDailyMovement.show();
        return dialogDailyMovement;
    }
    public void showStateVisibilty(){
        switch ((int) idSpinnerPermission){
            case 1:
                ETIssued.setVisibility(View.VISIBLE);
                ETIncoming.setVisibility(View.GONE);
                SPConvertToDaily.setVisibility(View.GONE);

                break;
            case 2:
                ETIssued.setVisibility(View.GONE);
                ETIncoming.setVisibility(View.VISIBLE);
                SPConvertToDaily.setVisibility(View.GONE);
                break;
            case 3:
                ETIssued.setVisibility(View.VISIBLE);
                ETIncoming.setVisibility(View.GONE);
                SPConvertToDaily.setVisibility(View.VISIBLE);
                break;

        }
            }
    public void saveDailyMovement(){
         String  incoming =ETIncoming.getText().toString().trim();
         String  issued =ETIssued.getText().toString().trim();
        if (idSpinnerPermission == 0 ){
            SPNamePermisionDaily.requestFocus();
            SPNamePermisionDaily.setError(getString(R.string.error_empty_permission));
          //  Toast.makeText(getContext(),getString(R.string.error_empty_permission), Toast.LENGTH_SHORT).show();
            return;

        }if (idSpinnerStore == 0){
            SPTypeStoreDaily.requestFocus();
            SPTypeStoreDaily.setError(getString(R.string.error_empty_store));
          //  Toast.makeText(getContext(), getString(R.string.error_empty_category_store), Toast.LENGTH_SHORT).show();
            return;

        }
        if (idSpinnerCategory == 0) {
            SPNameCategoryDaily.requestFocus();
            SPNameCategoryDaily.setError(getString(R.string.error_empty_category));
            return;

        }if ( TextUtils.isEmpty(incoming)&&  TextUtils.isEmpty(issued) ){
                // ETTypeStore.setError("not should leave field name emputy");
                Toast.makeText(getContext(),getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
                return;
            }
            if (idSpinnerPermission == 3 &&idSpinnerConvertTo == 0 ){
                SPConvertToDaily.requestFocus();
                SPConvertToDaily.setError(getString(R.string.error_convert_to));
                return;
            }


        if (intentDailyMovement == null) {
            ItemsStore itemSaveDaily = new ItemsStore();
            itemSaveDaily.setId_permission_id(idSpinnerPermission);
            itemSaveDaily.setId_code_store(idSpinnerStore);
            itemSaveDaily.setId_code_category(idSpinnerCategory);
            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)){
                incomings = Integer.parseInt(incoming);
            }
            itemSaveDaily.setIncoming(Integer.valueOf(incomings));
            int issueds = 0;
            if (!TextUtils.isEmpty(issued)){
                issueds = Integer.parseInt(issued);
            }
            itemSaveDaily.setIssued(Integer.valueOf(issueds));

            if (idSpinnerStore == idSpinnerConvertTo){
                SPConvertToDaily.requestFocus();
                SPConvertToDaily.setError(getString(R.string.error_same_store));
                return;
            }
            itemSaveDaily.setId_conert_to(idSpinnerConvertTo);

            if (itemSaveDaily == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_daily), Toast.LENGTH_LONG).show();
            }else {
                dbHelperDailyMovement.addDailyMovements(itemSaveDaily);
                Toast.makeText(getContext(), getString(R.string.save_daily), Toast.LENGTH_LONG).show();
                dialogDailyMovement.dismiss();
            }
        }else {
            ItemsStore itemUpdateDialy = new ItemsStore();
            itemUpdateDialy.setId(intentDailyMovement.getInt(IDDaily));
            itemUpdateDialy.setId_permission_id(idSpinnerPermission);
            itemUpdateDialy.setId_code_store(idSpinnerStore);
            itemUpdateDialy.setId_code_category(idSpinnerCategory);
            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)){
                incomings = Integer.parseInt(incoming);
            }
            itemUpdateDialy.setIncoming(Integer.valueOf(incomings));
            int issueds = 0;
            if (!TextUtils.isEmpty(issued)){
                issueds = Integer.parseInt(issued);
            }
            itemUpdateDialy.setIssued(Integer.valueOf(issueds));
            itemUpdateDialy.setId_conert_to(idSpinnerConvertTo);

            if (itemUpdateDialy != null){
                dbHelperDailyMovement.updateDailyMovements(itemUpdateDialy);
                Toast.makeText(getContext(), getString(R.string.update_daily), Toast.LENGTH_LONG).show();
                dialogDailyMovement.dismiss();

            }else {
                Toast.makeText(getContext(), getString(R.string.error_update_daily), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void deleteDaily(){
        if (intentDailyMovement != null){
            String incoming =ETIncoming.getText().toString();
            String issued = ETIssued.getText().toString();
            ItemsStore itemDeleteDaily = new ItemsStore();
            itemDeleteDaily.setId(intentDailyMovement.getInt(IDDaily));
            itemDeleteDaily.setIncoming(Integer.valueOf(incoming));
            itemDeleteDaily.setIssued(Integer.valueOf(issued));
            if (itemDeleteDaily != null){
                dbHelperDailyMovement.deleteDailyMovements(itemDeleteDaily);
                Toast.makeText(getContext(), getString(R.string.delete_daily), Toast.LENGTH_LONG).show();
                dialogDailyMovement.dismiss();
                dialogDeleteDailyMovement.dismiss();
   }else {
                Toast.makeText(getContext(), getString(R.string.error_delete_daily), Toast.LENGTH_LONG).show();
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
                deleteDaily();

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
        dialogDeleteDailyMovement = builder.create();
        dialogDeleteDailyMovement.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    public void loadSpinnerDataForCategory(){
        ArrayList<String > IDCategory = dbHelperDailyMovement.getDataForSpinnerCategory();
        ArrayAdapter<String> arrayAdapterCategory = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,IDCategory);
        // arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPNameCategoryDaily.setAdapter(arrayAdapterCategory);
    }
    public void loadSpinnerDataForStore(){
        ArrayList<String> IDStore = dbHelperDailyMovement.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapterStore = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,IDStore);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPTypeStoreDaily.setAdapter(arrayAdapterStore);
    }
    public void loadSpinnerDataForPermission(){
        ArrayList<String> IDPErmission = dbHelperDailyMovement.getDataForSpinnerPermission();
        ArrayAdapter<String> arrayAdapterPermission = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,IDPErmission);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPNamePermisionDaily.setAdapter(arrayAdapterPermission);
    }
    public void loadSpinnerDataForConvertTo(){
        ArrayList<String> IDConvertTo = dbHelperDailyMovement.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapterConvertTo = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,IDConvertTo);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPConvertToDaily.setAdapter(arrayAdapterConvertTo);
    }

}
