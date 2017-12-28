package com.imagine.mohamedtaha.store;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.adapter.AdapterAddDailyMovements;
import com.imagine.mohamedtaha.store.adapter.AdapterAddStokeHouse;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.fragments.EditStockingWarehouseFragment;
import com.imagine.mohamedtaha.store.loaders.LoaderStokeWearehouse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class StockingWarehouse extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>> , SearchView.OnQueryTextListener{
FloatingActionButton fab_add_stock_warehouse;
 public static RecyclerView recycleViewAStokeWearehouse;
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

    View emptViewStokeWearehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocking_warehouse);
        dbHelper = new TaskDbHelper(this);
        final ImageView imageView = (ImageView)findViewById(R.id.image_titile);

       // toolbar = (Toolbar)findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        recycleViewAStokeWearehouse =(RecyclerView)findViewById(R.id.recycleViewAddStokeWarehouse);
        recycleViewAStokeWearehouse.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        progressBarStoke = (ProgressBar)findViewById(R.id.progressBarStoke);
        emptViewStokeWearehouse = findViewById(R.id.empty_view_stoke_wearehouse);

     /*   ArrayList<ItemsStore> itemStokeHouses = dbHelper.getAllStokeHouseByCategoryAndStory();
        for (int ii = 0; ii < itemStokeHouses.size(); ii++) {
            ItemsStore itemStokeHouse = itemStokeHouses.get(ii);
        }*/
        adapterAddStokeHouse = new AdapterAddStokeHouse(this, itemStokeHouses);
        recycleViewAStokeWearehouse.setAdapter(adapterAddStokeHouse);

     /*   adapterAddStokeHouse.notifyDataSetChanged();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });*/

        recycleViewAStokeWearehouse.addOnItemTouchListener(new AdapterAddStokeHouse.RecycleTouchListener(getApplicationContext(),
                recycleViewAStokeWearehouse, new AdapterAddStokeHouse.ClickListener() {
            @Override
            public void onClick(View view, int position) {
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


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
        if (data.isEmpty()){
            progressBarStoke.setVisibility(View.GONE);
        }else {
            recycleViewAStokeWearehouse.setVisibility(View.VISIBLE);
            adapterAddStokeHouse.swapData(data);
            progressBarStoke.setVisibility(View.GONE);
            emptViewStokeWearehouse.setVisibility(View.GONE);
        }

    }
    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterAddStokeHouse.swapData(Collections.<ItemsStore>emptyList());
        adapterAddStokeHouse.notifyItemChanged(recycleViewAStokeWearehouse.indexOfChild(emptViewStokeWearehouse));

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
          //  getSupportLoaderManager().restartLoader(STOKE_LOADER,null,this);

        }

             return false;
    }


    private void showPopupMenu(View view){
        //inflate Menu
        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_store_category_permission,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_settings:
                        Toast.makeText(StockingWarehouse.this, "Edit", Toast.LENGTH_SHORT).show();
                }
                return true;            }
        });
        popupMenu.show();


    }

}











