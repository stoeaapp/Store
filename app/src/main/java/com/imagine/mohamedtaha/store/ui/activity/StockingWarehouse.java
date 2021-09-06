package com.imagine.mohamedtaha.store.ui.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.adapter.AdapterAddStokeHouse;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.databinding.StockingWarehouseBinding;
import com.imagine.mohamedtaha.store.fragments.EditStockingWarehouseFragment;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.ItemStore;
import com.imagine.mohamedtaha.store.room.data.StockingHouse;

import java.util.ArrayList;
import java.util.List;

public class StockingWarehouse extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private StockingWarehouseBinding binding;
    private StoreViewModel viewModel;

    public static AdapterAddStokeHouse adapterAddStokeHouse;
    public static TaskDbHelper dbHelper;
    private static final int STOKE_LOADER = 3;
    public static ArrayList<ItemStore> itemStokeHouses = new ArrayList<ItemStore>();
    public static final String ID_STOKE = "id";
    public static final String CODE_CATEGORY = "codeCategory";
    public static final String CODE_STORE = "codeStore";
    public static final String FIRST_BALANCE = "firstBalance";
    public static final String NOTESTOKE = "notesStoke";
    public static final String DIALOG_STOKE_WEAREHOUSE = "dialogStokeWearhouse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StockingWarehouseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new StoreViewModelFactory(((StoreApplication) getApplication()).getRepository()).create(StoreViewModel.class);

        dbHelper = new TaskDbHelper(this);
        binding.recycleViewAddStokeWarehouse.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterAddStokeHouse = new AdapterAddStokeHouse(this, itemStokeHouses);
        binding.recycleViewAddStokeWarehouse.setAdapter(adapterAddStokeHouse);

//        final Observer<List<ItemStore>> StockWareHouseObserver = itemStores -> {
//            if (itemStores.size() > 0) {
//                adapterAddStokeHouse.swapData(itemStores);
//                binding.emptyViewStokeWearehouse.setVisibility(View.GONE);
//                binding.progressBarStoke.setVisibility(View.GONE);
//                binding.recycleViewAddStokeWarehouse.setVisibility(View.VISIBLE);
//            } else {
//                binding.emptyViewStokeWearehouse.setVisibility(View.VISIBLE);
//                binding.progressBarStoke.setVisibility(View.VISIBLE);
//                binding.recycleViewAddStokeWarehouse.setVisibility(View.GONE);
//            }
//        };
//        viewModel.getAllStokeHouseByCategoryAndStory().observe(this, StockWareHouseObserver);

        final Observer<List<StockingHouse>> StockWareHouseObserver = itemStores -> {
            Log.d("itemstt"," " + itemStores.size());
            if (itemStores.size() > 0) {
//                adapterAddStokeHouse.swapData(itemStores);
//                binding.emptyViewStokeWearehouse.setVisibility(View.GONE);
//                binding.progressBarStoke.setVisibility(View.GONE);
//                binding.recycleViewAddStokeWarehouse.setVisibility(View.VISIBLE);
//            } else {
//                binding.emptyViewStokeWearehouse.setVisibility(View.VISIBLE);
//                binding.progressBarStoke.setVisibility(View.VISIBLE);
//                binding.recycleViewAddStokeWarehouse.setVisibility(View.GONE);
            }
        };
        viewModel.getAllStokeWareHouse().observe(this, StockWareHouseObserver);

        binding.recycleViewAddStokeWarehouse.addOnItemTouchListener(new AdapterAddStokeHouse.RecycleTouchListener(getApplicationContext(),
                binding.recycleViewAddStokeWarehouse, new AdapterAddStokeHouse.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemStore itemStoke = itemStokeHouses.get(position);
                Bundle bundle = new Bundle();
                //bundle.putInt(ID_STOKE, itemStoke.getId());
                bundle.putString(CODE_CATEGORY, itemStoke.getNameCategory());
                bundle.putString(CODE_STORE, itemStoke.getTypeStore());
                //   bundle.putInt(FIRST_BALANCE, itemStoke.getFirst_balanse());
                bundle.putString(NOTESTOKE, itemStoke.getNotes());
                EditStockingWarehouseFragment f = new EditStockingWarehouseFragment();
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(), DIALOG_STOKE_WEAREHOUSE);
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        binding.fabAddStockWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditStockingWarehouseFragment().show(getSupportFragmentManager(), DIALOG_STOKE_WEAREHOUSE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
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
//        itemStokeHouses = dbHelper.getAllStokeHouseBySearchCategoryName(newText);
//        if (itemStokeHouses !=null){
//            adapterAddStokeHouse.setFilter(itemStokeHouses);
//        }
        return false;
    }


    private void showPopupMenu(View view) {
        //inflate Menu
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_store_category_permission, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(StockingWarehouse.this, "Edit", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popupMenu.show();
    }
}