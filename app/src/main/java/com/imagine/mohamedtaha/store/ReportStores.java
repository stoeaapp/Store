package com.imagine.mohamedtaha.store;


import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.adapter.AdapterReportCategory;
import com.imagine.mohamedtaha.store.adapter.AdapterReportStore;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.loaders.LoaderReportCategory;
import com.imagine.mohamedtaha.store.loaders.LoaderReportStore;

import java.util.ArrayList;
import java.util.Collections;

public class ReportStores extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>, SearchView.OnQueryTextListener{
    public static AdapterReportStore adapterReportStore;
    TaskDbHelper dbHelper;
    RecyclerView recycleViewReportStore;
    ArrayList<ItemsStore> itemsStoreReport = new ArrayList<ItemsStore>();
    private ProgressBar progressBarPermission;
    //Identifier for the category dataloader;
    public static final int STORE_REPORT_LOADER = 9;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_stores);


        dbHelper = new TaskDbHelper(this);
        recycleViewReportStore = (RecyclerView) findViewById(R.id.recycleViewReportStore);
        recycleViewReportStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapterReportStore = new AdapterReportStore(this, itemsStoreReport);
        recycleViewReportStore.setAdapter(adapterReportStore);

        getSupportLoaderManager().initLoader(STORE_REPORT_LOADER,null,this);

    }

    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
        return new LoaderReportStore(getApplicationContext(),itemsStoreReport,dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
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
//        itemsCategoryReport = dbHelper.getAllCategoryByCategoryName(query);
//        if (itemsCategoryReport !=null){
//            adapterReportCategory.setFilter(itemsCategoryReport);
//        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        itemsStoreReport = dbHelper.getAllStoresByTypeStore(newText);
        if (itemsStoreReport !=null){
            adapterReportStore.setFilter(itemsStoreReport);
        }
        return false;
    }
}
