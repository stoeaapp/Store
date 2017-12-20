package com.imagine.mohamedtaha.store;

import android.app.LauncherActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.adapter.AdapterAddDailyMovements;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskContract;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.fragments.EditDailyMovementsFragment;
import com.imagine.mohamedtaha.store.fragments.EditStoreFragment;
import com.imagine.mohamedtaha.store.fragments.TestFragment;
import com.imagine.mohamedtaha.store.loaders.LoaderDailyMovements;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>,SearchView.OnQueryTextListener {
    private static final int Daily_LOADER = 4;
    TaskDbHelper dbHelper ;
    ArrayList<ItemsStore>itemsDaily = new ArrayList<>();
    private ProgressBar progressBarDaily;
    //private AdapterAddDailyMovements adapterAddDailyMovements;

    private AdapterAddDailyMovements adapterAddDailyMovements;

    private RecyclerView recyclerViewDailyMovements;
    public static final String IDDaily = "id";
    public static final String NAME_PERMISSION_DAILY = "namePermission";
    public static final String TYPE_STORE_DAILY = "typeStore";
    public static final String NAME_CATEGORY_DAILY = "nameCategory";
    public static final String CONVERT_TO_DAILY = "convert_to";
    public static final String INCOMING_DAILY = "incoming";
    public static final String ISSUED_DAILY = "issued";

    public static final String DIALOG_DALIY_MOVEMENTS = "dialogDaliy";

    View emptView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        dbHelper = new TaskDbHelper(this);
        recyclerViewDailyMovements = (RecyclerView)findViewById(R.id.recycleViewDailyMovements);
        progressBarDaily = (ProgressBar)findViewById(R.id.progressBarDaily);
        recyclerViewDailyMovements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterAddDailyMovements = new AdapterAddDailyMovements(getApplicationContext(),itemsDaily);
          emptView = findViewById(R.id.empty_view_main_activity_relative);

      //  emptView = (TextView)findViewById(R.id.empty_view_category);

        recyclerViewDailyMovements.addOnItemTouchListener(new AdapterAddDailyMovements.RecycleTouchListener(getApplicationContext(),
                recyclerViewDailyMovements, new AdapterAddDailyMovements.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemsStore itemsStore = itemsDaily.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(IDDaily, itemsStore.getId());
                bundle.putString(NAME_PERMISSION_DAILY, itemsStore.getNamePermission());
                bundle.putString(TYPE_STORE_DAILY, itemsStore.getTypeStore());
                bundle.putString(NAME_CATEGORY_DAILY, itemsStore.getNameGategory());
                bundle.putInt(INCOMING_DAILY, itemsStore.getIncoming());
                bundle.putInt(ISSUED_DAILY, itemsStore.getIssued());
                bundle.putString(CONVERT_TO_DAILY, itemsStore.getConverTo());
                EditDailyMovementsFragment f = new EditDailyMovementsFragment();
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(),DIALOG_DALIY_MOVEMENTS);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerViewDailyMovements.setAdapter(adapterAddDailyMovements);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDaily);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditDailyMovementsFragment().show(getSupportFragmentManager(),DIALOG_DALIY_MOVEMENTS);


            }
        });
        getSupportLoaderManager().initLoader(Daily_LOADER,null,this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add_data){
            Intent intent = new Intent(MainActivity.this,ActivityForIncludeFragments.class);
            startActivity(intent);

        }if (id == R.id.add_stocking_warehouse){
            Intent intent = new Intent(MainActivity.this,StockingWarehouse.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
        return new LoaderDailyMovements(getApplicationContext(),itemsDaily,dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        if (data.isEmpty()){
            //recyclerViewDailyMovements.setVisibility(View.GONE);
            progressBarDaily.setVisibility(View.GONE);
//            emptView.setVisibility(View.VISIBLE);
        }else {
            recyclerViewDailyMovements.setVisibility(View.VISIBLE);
            adapterAddDailyMovements.swapData(data);
            progressBarDaily.setVisibility(View.GONE);
            emptView.setVisibility(View.GONE);
        }
   //  progressBarDaily.setVisibility(View.GONE);
    //  recyclerViewDailyMovements.setVisibility(View.VISIBLE);
    //   adapterAddDailyMovements.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterAddDailyMovements.swapData(Collections.<ItemsStore>emptyList());
        adapterAddDailyMovements.notifyItemChanged(recyclerViewDailyMovements.indexOfChild(emptView));

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
         itemsDaily = dbHelper.getAllDailyMovementsBySearch(newText);
        if (itemsDaily !=null){
            adapterAddDailyMovements.setFilter(itemsDaily);
        }
        return false;
    }

}
