package com.imagine.mohamedtaha.store;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.imagine.mohamedtaha.store.adapter.AdapterReportDailyMovements;
import com.imagine.mohamedtaha.store.adapter.AdapterReportStore;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.loaders.LoaderDailyMovements;
import com.imagine.mohamedtaha.store.loaders.LoaderStokeWearehouse;

import java.util.ArrayList;
import java.util.Collections;

public class ReportDailyMovements  extends  AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>,SearchView.OnQueryTextListener{
    public static AdapterReportDailyMovements adapterReportDailyMovements;
    TaskDbHelper dbHelper;
    RecyclerView recyclerViewReportDailyMovements;
    ArrayList<ItemsStore> itemsStoresReport = new ArrayList<ItemsStore>();
    private ProgressBar progressBarPermission;
    //Identifier for the category dataloader;
    public static final int STORE_REPORT_LOADER = 7;
    private LinearLayoutManager mLayoutManager;
    RadioButton RBNameGategory,RBTypeStore,RBIssuedBalance,RBNamePermission,RBIncomingBalance;
    long sumIncoming;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   view = inflater.inflate(R.layout.fragment_report_store, container, false);
        setContentView(R.layout.report_daily_movements);
        RBNameGategory=(RadioButton)findViewById(R.id.RBNameCategoryDaily);
        RBTypeStore=(RadioButton)findViewById(R.id.RBTypeStoreDaily);
        RBIssuedBalance=(RadioButton)findViewById(R.id.RBIssuedBalanceDialy);
        RBNamePermission=(RadioButton)findViewById(R.id.RBNamePermissionDaily);
        RBIncomingBalance=(RadioButton)findViewById(R.id.RBIncomingBalanceDaily);

        dbHelper = new TaskDbHelper(this);
        recyclerViewReportDailyMovements = (RecyclerView) findViewById(R.id.recycleViewReportDailyMovements);
        recyclerViewReportDailyMovements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapterReportDailyMovements = new AdapterReportDailyMovements(this, itemsStoresReport);
        recyclerViewReportDailyMovements.setAdapter(adapterReportDailyMovements);

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
        if (RBNameGategory.isChecked()){
            itemsStoresReport = dbHelper.getAllDailyMovementsByCategoryName(newText);
            if (itemsStoresReport !=null){
                adapterReportDailyMovements.setFilter(itemsStoresReport);
             //   sumIncoming = dbHelper.getIncomingReportesForDailyMovements(newText);
            }

            //  getSupportLoaderManager().restartLoader(STOKE_LOADER,null,this);
        }else if (RBTypeStore.isChecked()){
            itemsStoresReport = dbHelper.getAllDailyMovementsByTypeStore(newText);
            if (itemsStoresReport !=null)adapterReportDailyMovements.setFilter(itemsStoresReport);
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


        return false;
    }}