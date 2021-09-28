package com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.databinding.StockingWarehouseBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.ShowStockWare;
import com.imagine.mohamedtaha.store.room.data.StockingHouse;
import com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse.adapter.AdapterAddStokeHouse;

import java.util.ArrayList;

import static com.imagine.mohamedtaha.store.Constant.ADD_STOKE_WEAR_HOUSES;
import static com.imagine.mohamedtaha.store.Constant.CODE_NAME_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.CODE_TYPE_STORE;
import static com.imagine.mohamedtaha.store.Constant.DELETE_STOKE_WEAR_HOUSES;
import static com.imagine.mohamedtaha.store.Constant.DIALOG_STOKE_WEAR_HOUSE;
import static com.imagine.mohamedtaha.store.Constant.FIRST_BALANCE;
import static com.imagine.mohamedtaha.store.Constant.ID_STOCK_WAREHOUSE;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.Constant.STOKE_WEAR_HOUSES;
import static com.imagine.mohamedtaha.store.Constant.UPDATE_STOKE_WEAR_HOUSES;

public class StockingWarehouse extends Fragment implements SearchView.OnQueryTextListener {
    private StockingWarehouseBinding binding;
    private StoreViewModel viewModel;
    public static AdapterAddStokeHouse adapterAddStokeHouse;
    public static ArrayList<ShowStockWare> itemStokeHouses = new ArrayList<>();

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_editor, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setOnQueryTextListener(this);
//        return true;
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
        viewModel.getAllStokeWareHouseWitCategoriesAndStoresShow().observe(this, categories -> {
            if (categories.size() > 0) {
                Log.d("iddd", "categories : " + categories.size());
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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StockingWarehouseBinding.inflate(getLayoutInflater(), container, false);
        binding.recycleViewAddStokeWarehouse.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        adapterAddStokeHouse = new AdapterAddStokeHouse((showStockWare, view) -> {
            Bundle bundle = new Bundle();
            if (showStockWare.getId() != null)
                bundle.putLong(ID_STOCK_WAREHOUSE, showStockWare.getId());
            bundle.putString(CODE_NAME_CATEGORY, showStockWare.getCategoryName());
            bundle.putString(CODE_TYPE_STORE, showStockWare.getTypeStore());
            bundle.putString(FIRST_BALANCE, showStockWare.getFirstBalance());
            bundle.putString(NOTES, showStockWare.getNotes());
            EditStockingWarehouseFragment f = new EditStockingWarehouseFragment();
            f.setArguments(bundle);
            f.show(getChildFragmentManager(), DIALOG_STOKE_WEAR_HOUSE);

        }, itemStokeHouses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        binding.recycleViewAddStokeWarehouse.setLayoutManager(layoutManager);
        binding.recycleViewAddStokeWarehouse.setAdapter(adapterAddStokeHouse);
        binding.fabAddStockWarehouse.setOnClickListener(v -> new EditStockingWarehouseFragment().show(getChildFragmentManager(), DIALOG_STOKE_WEAR_HOUSE));

        //Callback from edit fragment
        getChildFragmentManager().setFragmentResultListener(DIALOG_STOKE_WEAR_HOUSE, requireActivity(), (requestKey, result) -> {
            String getKey = result.getString(STOKE_WEAR_HOUSES);
            Log.d("iddd", "getKey : " + getKey);
            switch (getKey) {
                case ADD_STOKE_WEAR_HOUSES:
                    StockingHouse addStockWarehouse = (StockingHouse) result.getSerializable(ADD_STOKE_WEAR_HOUSES);
                    viewModel.insertStokeWarehouse(addStockWarehouse);
                    break;
                case UPDATE_STOKE_WEAR_HOUSES:
                    StockingHouse updateStockWarehouse = (StockingHouse) result.getSerializable(UPDATE_STOKE_WEAR_HOUSES);
                    viewModel.updateStockWarehouse(updateStockWarehouse.getId(), updateStockWarehouse.getStoreId(), updateStockWarehouse.getFirstBalance(), updateStockWarehouse.getNotes());
                    break;
                case DELETE_STOKE_WEAR_HOUSES:
                    long id = result.getLong(DELETE_STOKE_WEAR_HOUSES);
                    viewModel.deleteStokeWarehouse(id);
                    break;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
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
        PopupMenu popupMenu = new PopupMenu(requireActivity(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_store_category_permission, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_settings:
//                        Toast.makeText(requireActivity(), "Edit", Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        });
        popupMenu.show();
    }
}