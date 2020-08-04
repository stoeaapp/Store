package com.imagine.mohamedtaha.store.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import static com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse.CODE_CATEGORY;
import static com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse.CODE_STORE;
import static com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse.FIRST_BALANCE;
import static com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse.ID_STOKE;
import static com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse.NOTESTOKE;
import static com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse.dbHelper;
import static com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse.itemStokeHouses;


public class EditStockingWarehouseFragment extends DialogFragment implements DialogInterface.OnClickListener {


    private MaterialBetterSpinner SPCodeCategory,SPCodeStore;
    private EditText ETFisrtBalance,EtNotesStoke;
    private Button BTAddStokeWarehouse,BTDeleteStokeWarehouse;
    private TextView TVTitleStokeWearhouse;
    AlertDialog dialogStokeWearehouse;
    AlertDialog dialogDeleteStokeWearhouse;
    String SpinnerCategory,SpinnerStore;
    TaskDbHelper dbHelperStokeWearehouse;
    Bundle intentStokeWearehouse ;
    long idSpinnerCategory,idSpinnerStore;
    ArrayList<ItemsStore>itemsStores = new ArrayList<>();
    private ArrayList<ItemsStore> getItems(String items){
       // ItemsStore itemsStore  = new ItemsStore();
        itemStokeHouses = dbHelper.getAllStokeHouseByCategoryAndStory();
        for (int ii = 0; ii<itemStokeHouses.size(); ii++){
            ItemsStore itemsStokes = itemStokeHouses.get(ii);
        }
      //  itemStokeHouses.removeAll(itemStokeHouses);

        // recycleViewAddCategory.setAdapter(adapterAddStokeHouse);
        return itemStokeHouses;
      //  dbHelper.getAllStokeHouseByCategoryAndStory();
     //   Cursor c =dbHelper.getAllStokeHouseByCategoryAndStory();
     /*   while (c.moveToNext()){
            int id = c.getInt(0);
            String nameCategory = c.getString(1);
            String typeStore = c.getString(2);
            int firstBalance = c.getInt(3);
            String notes = c.getString(4);
            itemsStore.setId(id);
            itemsStore.setNameGategory(nameCategory);
            itemsStore.setTypeStore(typeStore);
            itemsStore.setFirst_balanse(firstBalance);
            itemsStore.setNotes(notes);
            itemStokeHouses.add(itemsStore);
        }*/
      //  adapterAddStokeHouse.swapData(itemsStores);




    }
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        View viewStockWarehouse = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_stocking_warehouse,null);
        TVTitleStokeWearhouse = (TextView)viewStockWarehouse.findViewById(R.id.TVTitleStokeWearhouse);
        SPCodeCategory = (MaterialBetterSpinner)viewStockWarehouse.findViewById(R.id.SPCodeCategoryStock);
        SPCodeStore = (MaterialBetterSpinner)viewStockWarehouse.findViewById(R.id.SPCodeStoreStock);
        ETFisrtBalance = (EditText)viewStockWarehouse.findViewById(R.id.ETFirstBalanceStoke);
        ETFisrtBalance.addTextChangedListener(new CheckZero());
        EtNotesStoke = (EditText)viewStockWarehouse.findViewById(R.id.ETNotesStoke);
        BTAddStokeWarehouse =(Button)viewStockWarehouse.findViewById(R.id.BTAddStokeWarehouse);
        BTDeleteStokeWarehouse = (Button)viewStockWarehouse.findViewById(R.id.BTDeleteStokeWarehouse);
        dbHelperStokeWearehouse = new TaskDbHelper(getContext());
        intentStokeWearehouse = getArguments();
        boolean saveState = true;
        if (intentStokeWearehouse != null){
            saveState = false;
            BTDeleteStokeWarehouse.setVisibility(View.VISIBLE);
            BTAddStokeWarehouse.setText(getString(R.string.action_edit));
            TVTitleStokeWearhouse.setText(getString(R.string.update_stoke_titile));
            SPCodeCategory.setText(intentStokeWearehouse.getString(CODE_CATEGORY));
            SPCodeStore.setText(intentStokeWearehouse.getString(CODE_STORE));
            ETFisrtBalance.setText(String.valueOf(intentStokeWearehouse.getInt(FIRST_BALANCE)));
            EtNotesStoke.setText(intentStokeWearehouse.getString(NOTESTOKE));

        }
        SPCodeCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerCategory = parent.getItemAtPosition(position).toString();
                idSpinnerCategory =parent.getItemIdAtPosition(position+1);
            }
        });
        SPCodeStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerStore = parent.getItemAtPosition(position).toString();
                idSpinnerStore =parent.getItemIdAtPosition(position+1);
            }
        });
        loadSpinnerDataForCategory();
        loadSpinnerDataForStore();
        BTAddStokeWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStokeWearehouse();
            }
        });
        BTDeleteStokeWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      //  builder.setTitle(saveState? "Add StokeWearehouse" : "Edit StokeWearehouse");
        builder.setView(viewStockWarehouse);
        dialogStokeWearehouse = builder.create();
        dialogStokeWearehouse.show();
        return dialogStokeWearehouse ;
    }

    public void saveStokeWearehouse(){
        String firstBalance =ETFisrtBalance.getText().toString().trim();
        String noteStoke = EtNotesStoke.getText().toString().trim();
        if (idSpinnerCategory == 0){
            SPCodeCategory.requestFocus();
            SPCodeCategory.setError(getString(R.string.error_empty_category));
          //  Toast.makeText(getContext(), getString(R.string.error_empty_category_store), Toast.LENGTH_SHORT).show();
            return;
        }
        if (idSpinnerStore == 0){
            SPCodeStore.requestFocus();
            SPCodeStore.setError(getString(R.string.error_empty_store));
            return;

        }
        if ( intentStokeWearehouse == null && TextUtils.isEmpty(firstBalance) ||TextUtils.isEmpty(firstBalance) ){
            ETFisrtBalance.requestFocus();
            ETFisrtBalance.setError(getString(R.string.error_first_balance));
         //   Toast.makeText(getContext(), getString(R.string.error_first_balance), Toast.LENGTH_SHORT).show();
            return;
        }
        if (intentStokeWearehouse == null) {
            ItemsStore itemsSaveStoke = new ItemsStore();
            itemsSaveStoke.setId_code_category(idSpinnerCategory);
            itemsSaveStoke.setId_code_store(idSpinnerStore);
            itemsSaveStoke.setFirst_balanse(Integer.valueOf(firstBalance));
            itemsSaveStoke.setNotes(noteStoke);
            if (itemsSaveStoke == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_category), Toast.LENGTH_LONG).show();
            }else {
                dbHelperStokeWearehouse.addStokeWarehouse(itemsSaveStoke);
                Toast.makeText(getContext(), getString(R.string.save_category), Toast.LENGTH_LONG).show();
                dialogStokeWearehouse.dismiss();
            }
        }else {
            ItemsStore itemUpdateStoke = new ItemsStore();
            itemUpdateStoke.setId(intentStokeWearehouse.getInt(ID_STOKE));
            itemUpdateStoke.setId_code_category(idSpinnerCategory);
            itemUpdateStoke.setId_code_store(idSpinnerStore);
            itemUpdateStoke.setFirst_balanse(Integer.valueOf(firstBalance));
            itemUpdateStoke.setNotes(noteStoke);
            boolean isExistConvertDailyMovements = dbHelperStokeWearehouse.isFirstBalanceUsedStokewearhouse(intentStokeWearehouse.getInt(ID_STOKE));
            if (isExistConvertDailyMovements ==true){
                Toast.makeText(getContext(), getString(R.string.this_category_not_updated), Toast.LENGTH_SHORT).show();
                return;
            }
            if (itemUpdateStoke != null){
                dbHelperStokeWearehouse.updateStokeWarehouse(itemUpdateStoke);
                Toast.makeText(getContext(), getString(R.string.update_category), Toast.LENGTH_LONG).show();
                dialogStokeWearehouse.dismiss();
            }else {
                Toast.makeText(getContext(), getString(R.string.error_update_category), Toast.LENGTH_LONG).show();
            }
        }
 //       getItems(null);

    }

    public void deleteStoke(){
        if (intentStokeWearehouse != null){
            String firstBalance =ETFisrtBalance.getText().toString();
            String noteStoke = EtNotesStoke.getText().toString();
            ItemsStore itemDeletePermision = new ItemsStore();
            itemDeletePermision.setId(intentStokeWearehouse.getInt(ID_STOKE));
            //itemDeletePermision.setFirst_balanse(Integer.valueOf(firstBalance));
            //itemDeletePermision.setNotes(noteStoke);
            boolean isExistConvertDailyMovements = dbHelperStokeWearehouse.isFirstBalanceUsedStokewearhouse(intentStokeWearehouse.getInt(ID_STOKE));
            if (isExistConvertDailyMovements ==true){
                Toast.makeText(getContext(), getString(R.string.this_category_not_updated), Toast.LENGTH_SHORT).show();
                return;
            }
            if (itemDeletePermision != null){
                dbHelperStokeWearehouse.deleteStokeWareHouse(itemDeletePermision);
                Toast.makeText(getContext(), getString(R.string.delete_category), Toast.LENGTH_LONG).show();
                dialogStokeWearehouse.dismiss();
                dialogDeleteStokeWearhouse.dismiss();

                // getActivity().finish();

            }else {
                Toast.makeText(getContext(), getString(R.string.error_delete_category), Toast.LENGTH_LONG).show();
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
        builder.setPositiveButton(R.string.BTDelete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked the "Delete" button,so delete the Category
                deleteStoke();

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
        dialogDeleteStokeWearhouse = builder.create();
        dialogDeleteStokeWearhouse.show();
    }
    public void loadSpinnerDataForCategory(){
        ArrayList<String >IDCategory = dbHelperStokeWearehouse.getDataForSpinnerCategory();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, IDCategory);
        // arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCodeCategory.setAdapter(arrayAdapter);
    }
    public void loadSpinnerDataForStore(){
        ArrayList<String> IDStore = dbHelperStokeWearehouse.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,IDStore);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPCodeStore.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    // holder.dateView.setText(data.getCreatedDate());


    class CheckZero implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try{
                if (Integer.parseInt(s.toString())<1)
                    s.delete(0,s.length());
            }catch (NumberFormatException e){

            }

        }
    }


}









