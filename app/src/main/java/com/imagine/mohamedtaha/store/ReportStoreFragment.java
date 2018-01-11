package com.imagine.mohamedtaha.store;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.imagine.mohamedtaha.store.adapter.AdapterReportStore;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.loaders.LoaderStokeWearehouse;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Collections;


public class ReportStoreFragment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>,SearchView.OnQueryTextListener{
    public static AdapterReportStore adapterReportStore;
    TaskDbHelper dbHelper;
    RecyclerView recyclerViewReportStore;
    ArrayList<ItemsStore> itemsStoresReport = new ArrayList<ItemsStore>();
    private ProgressBar progressBarPermission;
    //Identifier for the category dataloader;
    public static final int STORE_REPORT_LOADER = 6;
    private LinearLayoutManager mLayoutManager;
    RadioButton RBNameGategory,RBFirstBalance,RBChooseTypeStoreStoke,RBAllStoreStoke;
    MaterialBetterSpinner SPSelectTypeStoreStoke;
    String selectTypeStore;



    @Override
    public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
        //   view = inflater.inflate(R.layout.fragment_report_store, container, false);
           setContentView(R.layout.fragment_report_store);
           RBNameGategory=(RadioButton)findViewById(R.id.RBNameCategory);
           RBFirstBalance=(RadioButton)findViewById(R.id.RBFirstBalance);
           SPSelectTypeStoreStoke = (MaterialBetterSpinner)findViewById(R.id.SPSelectTypeStoreStoke);
           RBChooseTypeStoreStoke = (RadioButton)findViewById(R.id.RBChooseTypeStoreStoke);
           RBAllStoreStoke = (RadioButton)findViewById(R.id.RBAllStoreStoke);


        SPSelectTypeStoreStoke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectTypeStore = parent.getItemAtPosition(position).toString();

            }
        });
           dbHelper = new TaskDbHelper(this);
           recyclerViewReportStore = (RecyclerView) findViewById(R.id.recycleViewReportStore);
           recyclerViewReportStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

          adapterReportStore = new AdapterReportStore(this, itemsStoresReport);
           recyclerViewReportStore.setAdapter(adapterReportStore);
           loadSpinnerDataForStore();
           getSupportLoaderManager().initLoader(STORE_REPORT_LOADER,null,this);

       }
       /*
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_report_store, container, false);

        dbHelper = new TaskDbHelper(getActivity());
        recyclerViewReportStore = (RecyclerView) view.findViewById(R.id.recycleViewReportStore);

        mLayoutManager =     new LinearLayoutManager(getActivity());

        // progressBarPermission =(ProgressBar)view.findViewById(R.id.progressBarPermission);

        adapterReportStore = new AdapterReportStore(getContext(), itemsStoresReport);
        recyclerViewReportStore.setAdapter(adapterReportStore);

        return view;
    }*/


    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
       return new LoaderStokeWearehouse(getApplicationContext(),itemsStoresReport,dbHelper);
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        //recyclerViewReportStore.setVisibility(View.VISIBLE);
        adapterReportStore.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterReportStore.swapData(Collections.<ItemsStore>emptyList());


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
        if (RBAllStoreStoke.isChecked()){

            if (RBNameGategory.isChecked()){
            itemsStoresReport = dbHelper.getAllStokeHouseBySearchCategoryName(newText);
            if (itemsStoresReport !=null)adapterReportStore.setFilter(itemsStoresReport);
                //  getSupportLoaderManager().restartLoader(STOKE_LOADER,null,this);
            } else  {
            itemsStoresReport = dbHelper.getAllStokeHouseBySearchFirstBalance(newText);
            if (itemsStoresReport !=null)adapterReportStore.setFilter(itemsStoresReport);
        }

        }else if (RBChooseTypeStoreStoke.isChecked()){
            if (RBNameGategory.isChecked()){
                itemsStoresReport = dbHelper.getAllStokeHouseBySearchCategoryNameAndTypeStore(newText,selectTypeStore);
                if (itemsStoresReport !=null)adapterReportStore.setFilter(itemsStoresReport);
            } else  {
                itemsStoresReport = dbHelper.getAllStokeHouseBySearchFirstBalanceAndTypeStore(newText,selectTypeStore);
                if (itemsStoresReport !=null)adapterReportStore.setFilter(itemsStoresReport);
            }
        }


        return false;
    }
    public void checkSpinner(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.RBAllStoreStoke:
                if (checked)
                    SPSelectTypeStoreStoke.setVisibility(View.INVISIBLE);
                break;
            case R.id.RBChooseTypeStoreStoke:
                if (checked)
                    SPSelectTypeStoreStoke.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void loadSpinnerDataForStore(){
        ArrayList<String> IDStore = dbHelper.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,IDStore);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPSelectTypeStoreStoke.setAdapter(arrayAdapter);
    }

}
