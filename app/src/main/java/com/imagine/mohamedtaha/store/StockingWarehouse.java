package com.imagine.mohamedtaha.store;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.adapter.AdapterAddStokeHouse;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.fragments.EditStockingWarehouseFragment;
import com.imagine.mohamedtaha.store.loaders.LoaderStokeWearehouse;

import java.util.ArrayList;
import java.util.Collections;

public class StockingWarehouse extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>> , SearchView.OnQueryTextListener{
FloatingActionButton fab_add_stock_warehouse;
 public static ListView recycleViewAddCategory;
  public static AdapterAddStokeHouse adapterAddStokeHouse;
   public static TaskDbHelper dbHelper;
    ProgressBar progressBarStoke;
    Toolbar toolbar;
    private static final int STOKE_LOADER = 3;
   public static ArrayList<ItemsStore> itemStokeHouses = new ArrayList<ItemsStore>();
    public static final String ID_STOKE = "id";
    public static final String  CODE_CATEGORY= "codeCategory";
    public static final String CODE_STORE = "codeStore";
    public static final String FIRST_BALANCE = "firstBalance";
    public static final String NOTESTOKE = "notesStoke";
    public static final String DIALOG_STOKE_WEAREHOUSE = "dialogStokeWearhouse";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocking_warehouse);
        dbHelper = new TaskDbHelper(this);
       // toolbar = (Toolbar)findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        recycleViewAddCategory =(ListView)findViewById(R.id.recycleViewAddStokeWarehouse);
        progressBarStoke = (ProgressBar)findViewById(R.id.progressBarStoke);

     /*   ArrayList<ItemsStore> itemStokeHouses = dbHelper.getAllStokeHouseByCategoryAndStory();
        for (int ii = 0; ii < itemStokeHouses.size(); ii++) {
            ItemsStore itemStokeHouse = itemStokeHouses.get(ii);
        }*/
        adapterAddStokeHouse = new AdapterAddStokeHouse(this, itemStokeHouses);
        recycleViewAddCategory.setAdapter(adapterAddStokeHouse);
        adapterAddStokeHouse.notifyDataSetChanged();
        recycleViewAddCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemsStore itemStoke = itemStokeHouses.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(ID_STOKE, itemStoke.getId());
                bundle.putString(CODE_CATEGORY,itemStoke.getNameGategory());
                bundle.putString(CODE_STORE,itemStoke.getTypeStore());
                bundle.putInt(FIRST_BALANCE, itemStoke.getFirst_balanse());
                bundle.putString(NOTESTOKE, itemStoke.getNotes());
                EditStockingWarehouseFragment f = new EditStockingWarehouseFragment();
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(),DIALOG_STOKE_WEAREHOUSE);
            }
        });
        fab_add_stock_warehouse= (FloatingActionButton)findViewById(R.id.fab_add_stock_warehouse);
        fab_add_stock_warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditStockingWarehouseFragment().show(getSupportFragmentManager(),DIALOG_STOKE_WEAREHOUSE);
            }
        });
        getSupportLoaderManager().initLoader(STOKE_LOADER,null,this);
    }
    @Override
        public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
        return new LoaderStokeWearehouse(getApplicationContext(),itemStokeHouses,dbHelper);
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        progressBarStoke.setVisibility(View.GONE);
        recycleViewAddCategory.setVisibility(View.VISIBLE);
        adapterAddStokeHouse.swapData(data);
    }
    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterAddStokeHouse.swapData(Collections.<ItemsStore>emptyList());
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
        itemStokeHouses = dbHelper.getAllStokeHouseBySearch(newText);
        if (itemStokeHouses !=null){
            adapterAddStokeHouse.setFilter(itemStokeHouses);
        }

             return false;
    }

}











