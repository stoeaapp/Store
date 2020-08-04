package com.imagine.mohamedtaha.store.ui.activity;


import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.adapter.AdapterReportDailyMovements;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.loaders.LoaderDailyMovements;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Collections;

public class ReportDailyMovements  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>, SearchView.OnQueryTextListener{
    public static AdapterReportDailyMovements adapterReportDailyMovements;
    TaskDbHelper dbHelper;
    RecyclerView recyclerViewReportDailyMovements;
    ArrayList<ItemsStore> itemsStoresReport = new ArrayList<ItemsStore>();
    private ProgressBar progressBarPermission;
    //Identifier for the category dataloader;
    public static final int STORE_REPORT_LOADER = 7;
    private LinearLayoutManager mLayoutManager;
    RadioButton RBNameGategory,RBTypeStoreDaily,RBIssuedBalance,RBNamePermission,RBIncomingBalance,RBAllStore;
    MaterialBetterSpinner SPSelectTypeStore;
    String selectTypeStore;
    CheckBox CBShowCurrentBalanceDaily;
    TextView show_current_balance_daily;
    int showScreenCurrentBalance,firstBalance ,sumIncoming, sumIssued,sumConvertTo;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   view = inflater.inflate(R.layout.fragment_report_stoke, container, false);
        setContentView(R.layout.report_daily_movements);
        RBNameGategory=(RadioButton)findViewById(R.id.RBNameCategoryDaily);
        RBTypeStoreDaily=(RadioButton)findViewById(R.id.RBChooseTypeStore);
        RBIssuedBalance=(RadioButton)findViewById(R.id.RBIssuedBalanceDialy);
        RBNamePermission=(RadioButton)findViewById(R.id.RBNamePermissionDaily);
        RBIncomingBalance=(RadioButton)findViewById(R.id.RBIncomingBalanceDaily);
        RBAllStore=(RadioButton)findViewById(R.id.RBAllStore);
        CBShowCurrentBalanceDaily = (CheckBox)findViewById(R.id.CBShowCurrentBalanceDaily);
        show_current_balance_daily=(TextView)findViewById(R.id.show_current_balance_daily);
        SPSelectTypeStore =(MaterialBetterSpinner) findViewById(R.id.SPSelectTypeStore);

        SPSelectTypeStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectTypeStore = parent.getItemAtPosition(position).toString();

            }
        });

        dbHelper = new TaskDbHelper(this);
        recyclerViewReportDailyMovements = (RecyclerView) findViewById(R.id.recycleViewReportDailyMovements);
        recyclerViewReportDailyMovements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapterReportDailyMovements = new AdapterReportDailyMovements(this, itemsStoresReport);
        recyclerViewReportDailyMovements.setAdapter(adapterReportDailyMovements);
        loadSpinnerDataForStore();
        getSupportLoaderManager().initLoader(STORE_REPORT_LOADER,null,this);
      //  if(RBTypeStore.isChecked())SPSelectTypeStore.setVisibility(View.VISIBLE);
        //else SPSelectTypeStore.setVisibility(View.INVISIBLE);

}
       /*
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_report_stoke, container, false);

        dbHelper = new TaskDbHelper(getActivity());
        recyclerViewReportStore = (RecyclerView) view.findViewById(R.id.recycleViewReportStore);

        mLayoutManager =     new LinearLayoutManager(getActivity());

        // progressBarPermission =(ProgressBar)view.findViewById(R.id.progressBarPermission);

        adapterReportStoke = new AdapterReportStoke(getContext(), itemsStoresReport);
        recyclerViewReportStore.setAdapter(adapterReportStoke);

        return view;
    }*/


    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
        return new LoaderDailyMovements(getApplicationContext(),itemsStoresReport,dbHelper);
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        //recyclerViewReportStore.setVisibility(View.VISIBLE);
        adapterReportDailyMovements.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterReportDailyMovements.swapData(Collections.<ItemsStore>emptyList());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (RBAllStore.isChecked()){
            if (RBNameGategory.isChecked()){
                itemsStoresReport = dbHelper.getAllDailyMovementsByCategoryName(newText);
                if (itemsStoresReport !=null){
                    adapterReportDailyMovements.setFilter(itemsStoresReport);
                    //   sumIncoming = dbHelper.getIncomingReportesForDailyMovements(newText);
                }

                //  getSupportLoaderManager().restartLoader(STOKE_LOADER,null,this);
            }else if (RBNamePermission.isChecked()){
                itemsStoresReport = dbHelper.getAllDailyMovementsByNamePermission(newText);
                if (itemsStoresReport !=null)adapterReportDailyMovements.setFilter(itemsStoresReport);
            }else if (RBIncomingBalance.isChecked()){
                itemsStoresReport = dbHelper.getAllDailyMovementsByIncoming(newText);
                if (itemsStoresReport !=null)adapterReportDailyMovements.setFilter(itemsStoresReport);

            }
            else  {
                itemsStoresReport = dbHelper.getAllDailyMovementsByIssued(newText);
                if (itemsStoresReport !=null)adapterReportDailyMovements.setFilter(itemsStoresReport);
            }

        }else if (RBTypeStoreDaily.isChecked()){

            if (RBNameGategory.isChecked()){
                itemsStoresReport = dbHelper.getAllDailyMovementsByCategoryNameAndTypeStore(newText,selectTypeStore);
                sumIncoming = dbHelper.getIncomingByNameCategoryAndTypeStore(newText,selectTypeStore);
                sumIssued = dbHelper.getIssuedByNameCategoryAndTypeStore(newText,selectTypeStore);
                sumConvertTo = dbHelper.getConvertToByNameCategoryAndTypeStore(newText,selectTypeStore);
                firstBalance = dbHelper.getFirstBalanceByNameCategoryAndTypeStore(newText,selectTypeStore);
                showScreenCurrentBalance = firstBalance + sumIncoming + sumConvertTo -  sumIssued;
                show_current_balance_daily.setText(showScreenCurrentBalance + " ");
                if (itemsStoresReport !=null){
                    adapterReportDailyMovements.setFilter(itemsStoresReport);
                    //   sumIncoming = dbHelper.getIncomingReportesForDailyMovements(newText);
                }

                //  getSupportLoaderManager().restartLoader(STOKE_LOADER,null,this);
            }else if (RBNamePermission.isChecked()){
                itemsStoresReport = dbHelper.getAllDailyMovementsByNamePermissionAndTypeStore(newText,selectTypeStore);
                if (itemsStoresReport !=null)adapterReportDailyMovements.setFilter(itemsStoresReport);
            }else if (RBIncomingBalance.isChecked()){
                itemsStoresReport = dbHelper.getAllDailyMovementsByIncomingAndTypeStore(newText,selectTypeStore);
                if (itemsStoresReport !=null)adapterReportDailyMovements.setFilter(itemsStoresReport);

            }
            else  {
                itemsStoresReport = dbHelper.getAllDailyMovementsByIssuedAndTypeStore(newText,selectTypeStore);
                if (itemsStoresReport !=null)adapterReportDailyMovements.setFilter(itemsStoresReport);
            }

        }
        return false;
    }

    public void checkSpinner(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.RBAllStore:
                if (checked){
                    SPSelectTypeStore.setVisibility(View.INVISIBLE);
                    CBShowCurrentBalanceDaily.setVisibility(View.GONE);
                    CBShowCurrentBalanceDaily.setChecked(false);
                    show_current_balance_daily.setVisibility(View.GONE);
                }
                        break;
            case R.id.RBChooseTypeStore:
                if (checked)
                    SPSelectTypeStore.setVisibility(View.VISIBLE);
                if (RBNameGategory.isChecked()){
                    CBShowCurrentBalanceDaily.setVisibility(View.VISIBLE);
                    show_current_balance_daily.setVisibility(View.INVISIBLE);

                }

                break;
            case R.id.RBNameCategoryDaily:
                if (RBTypeStoreDaily.isChecked()){
                    CBShowCurrentBalanceDaily.setVisibility(View.VISIBLE);
                    show_current_balance_daily.setVisibility(View.INVISIBLE);

                }

                break;
                default:{
                    CBShowCurrentBalanceDaily.setVisibility(View.GONE);
                    show_current_balance_daily.setVisibility(View.GONE);
                    CBShowCurrentBalanceDaily.setChecked(false);

                }
        }
    }
    public void checkCheckBoxDaily(View view){
        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.CBShowCurrentBalanceDaily:
                if(checked){
                    show_current_balance_daily.setVisibility(View.VISIBLE);
                    break;
                }else {
                    show_current_balance_daily.setVisibility(View.INVISIBLE);
                    break;
                }

        }
    }

    public void loadSpinnerDataForStore(){
        ArrayList<String> IDStore = dbHelper.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,IDStore);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPSelectTypeStore.setAdapter(arrayAdapter);
    }
}

















