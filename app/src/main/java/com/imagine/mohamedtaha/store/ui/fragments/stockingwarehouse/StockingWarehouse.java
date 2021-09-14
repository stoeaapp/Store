package com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse;


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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.adapter.AdapterAddStokeHouse;
import com.imagine.mohamedtaha.store.databinding.StockingWarehouseBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.ShowStockWare;

import java.util.ArrayList;

import static com.imagine.mohamedtaha.store.Constant.CODE_NAME_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.CODE_TYPE_STORE;
import static com.imagine.mohamedtaha.store.Constant.DIALOG_STOKE_WEAR_HOUSE;
import static com.imagine.mohamedtaha.store.Constant.FIRST_BALANCE;
import static com.imagine.mohamedtaha.store.Constant.NOTES;

public class StockingWarehouse extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private StockingWarehouseBinding binding;
    public static AdapterAddStokeHouse adapterAddStokeHouse;
    public static ArrayList<ShowStockWare> itemStokeHouses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StockingWarehouseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StoreViewModel viewModel = new StoreViewModelFactory(((StoreApplication) getApplication()).getRepository()).create(StoreViewModel.class);
        viewModel.getAllStokeWareHouseWitCategoriesAndStoresShow().observe(this, categories -> {
            Log.d("iddd" ," " +categories.size());
            if (categories.size() > 0) {
                adapterAddStokeHouse.swapData(categories);
                binding.emptyViewStokeWearehouse.setVisibility(View.GONE);
                binding.progressBarStoke.setVisibility(View.GONE);
                binding.recycleViewAddStokeWarehouse.setVisibility(View.VISIBLE);
            } else {
                binding.emptyViewStokeWearehouse.setVisibility(View.VISIBLE);
                binding.progressBarStoke.setVisibility(View.VISIBLE);
                binding.recycleViewAddStokeWarehouse.setVisibility(View.GONE);
            }
        });

        binding.recycleViewAddStokeWarehouse.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterAddStokeHouse = new AdapterAddStokeHouse(this, itemStokeHouses);
        binding.recycleViewAddStokeWarehouse.setAdapter(adapterAddStokeHouse);

        binding.recycleViewAddStokeWarehouse.addOnItemTouchListener(new AdapterAddStokeHouse.RecycleTouchListener(getApplicationContext(),
                binding.recycleViewAddStokeWarehouse, new AdapterAddStokeHouse.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ShowStockWare itemStoke = itemStokeHouses.get(position);
                Bundle bundle = new Bundle();
                //bundle.putInt(ID_STOKE, itemStoke.getId());
                bundle.putString(CODE_NAME_CATEGORY, itemStoke.getCategoryName());
                bundle.putString(CODE_TYPE_STORE, itemStoke.getTypeStore());
                bundle.putString(FIRST_BALANCE, itemStoke.getFirstBalance());
                bundle.putString(NOTES, itemStoke.getNotes());
                EditStockingWarehouseFragment f = new EditStockingWarehouseFragment();
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(), DIALOG_STOKE_WEAR_HOUSE);
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        binding.fabAddStockWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditStockingWarehouseFragment().show(getSupportFragmentManager(), DIALOG_STOKE_WEAR_HOUSE);
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