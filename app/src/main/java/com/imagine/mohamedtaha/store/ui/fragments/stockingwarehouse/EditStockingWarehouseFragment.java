package com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.databinding.FragmentEditStockingWarehouseBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Categories;
import com.imagine.mohamedtaha.store.room.data.StockingHouse;
import com.imagine.mohamedtaha.store.room.data.Stores;
import com.imagine.mohamedtaha.store.util.DialogUtils;

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
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditStockingWarehouseFragment extends BottomSheetDialogFragment {
    private StoreViewModel viewModel;
    private FragmentEditStockingWarehouseBinding binding;
    Bundle intentStokeWearehouse;
    long idSpinnerCategory, idSpinnerStore;
    // SpinAdapter arrayAdapter;
    ArrayAdapter<Categories> arrayAdapterCategoryName;
    ArrayAdapter<Stores> arrayAdapterTypeStore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditStockingWarehouseBinding.inflate(getLayoutInflater());
        intentStokeWearehouse = getArguments();
        binding.ETFirstBalanceStoke.addTextChangedListener(new CheckZero());
        if (intentStokeWearehouse != null) {
            binding.BTDeleteStokeWarehouse.setVisibility(View.VISIBLE);
            binding.BTAddStokeWarehouse.setText(getString(R.string.action_edit));
            binding.TVTitleStokeWearhouse.setText(getString(R.string.update_stoke_title));
            binding.SPCodeCategoryStock.setText(intentStokeWearehouse.getString(CODE_NAME_CATEGORY));
            binding.SPCodeStoreStock.setText(intentStokeWearehouse.getString(CODE_TYPE_STORE));
            binding.ETFirstBalanceStoke.setText(intentStokeWearehouse.getString(FIRST_BALANCE));
            binding.ETNotesStoke.setText(intentStokeWearehouse.getString(NOTES));
        }
        binding.SPCodeCategoryStock.setOnItemClickListener((parent, view, position, id) -> {
            Categories categoryItem = arrayAdapterCategoryName.getItem(position);
            idSpinnerCategory = categoryItem.getId();
        });
        binding.SPCodeStoreStock.setOnItemClickListener((parent, view, position, id) -> {
            Stores stores = arrayAdapterTypeStore.getItem(position);
            idSpinnerStore = stores.getId();
        });
        loadSpinnerDataForCategory();
        loadSpinnerDataForStore();
        binding.BTAddStokeWarehouse.setOnClickListener(v -> saveStockWarehouse());
        binding.BTDeleteStokeWarehouse.setOnClickListener(v -> DialogUtils.showMessageWithYesNoMaterialDesign(requireContext(), getString(R.string.title_delete_item), getString(R.string.delete_dialog_msg), (dialog, which) -> {
            deleteStoke();
            dismiss();
        }));
        return binding.getRoot();
    }

    public void saveStockWarehouse() {
        String firstBalance = binding.ETFirstBalanceStoke.getText().toString().trim();
        String noteStoke = binding.ETNotesStoke.getText().toString().trim();
        if (idSpinnerCategory == 0) {
            binding.SPCodeCategoryStock.requestFocus();
            binding.SPCodeCategoryStock.setError(getString(R.string.error_empty_category));
            return;
        }
        if (idSpinnerStore == 0) {
            binding.SPCodeStoreStock.requestFocus();
            binding.SPCodeStoreStock.setError(getString(R.string.error_empty_store));
            return;

        }
        if (intentStokeWearehouse == null && TextUtils.isEmpty(firstBalance) || TextUtils.isEmpty(firstBalance)) {
            binding.ETFirstBalanceStoke.requestFocus();
            binding.ETFirstBalanceStoke.setError(getString(R.string.error_first_balance));
            return;
        }
        if (intentStokeWearehouse == null) {
            StockingHouse itemsSaveStoke = new StockingHouse(idSpinnerCategory, idSpinnerStore, firstBalance, "", noteStoke);
            itemsSaveStoke.setCreatedAt(getDate());
            itemsSaveStoke.setTime(getTime());
            if (itemsSaveStoke == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_category), Toast.LENGTH_LONG).show();
            } else {
                Log.d("iddd", "idSpinnerCategory : " + idSpinnerCategory + " idSpinnerStore  " + idSpinnerStore);

                Bundle bundle = new Bundle();
                bundle.putSerializable(ADD_STOKE_WEAR_HOUSES, itemsSaveStoke);
                bundle.putString(STOKE_WEAR_HOUSES, ADD_STOKE_WEAR_HOUSES);
                getParentFragmentManager().setFragmentResult(DIALOG_STOKE_WEAR_HOUSE, bundle);
                Toast.makeText(getContext(), getString(R.string.save_successfully), Toast.LENGTH_LONG).show();
                dismiss();
            }
        } else {
            StockingHouse itemUpdateStoke = new StockingHouse(idSpinnerCategory, idSpinnerStore, firstBalance, "", noteStoke);
            itemUpdateStoke.setId(intentStokeWearehouse.getLong(ID_STOCK_WAREHOUSE));
            itemUpdateStoke.setUpdatedAt(getDate());
            //    boolean isExistConvertDailyMovements = dbHelperStokeWearehouse.isFirstBalanceUsedStokewearhouse(intentStokeWearehouse.getInt(ID_STOKE));
//            if (isExistConvertDailyMovements ==true){
//                Toast.makeText(getContext(), getString(R.string.this_category_not_updated), Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (itemUpdateStoke != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(UPDATE_STOKE_WEAR_HOUSES, itemUpdateStoke);
                bundle.putString(STOKE_WEAR_HOUSES, UPDATE_STOKE_WEAR_HOUSES);
                getParentFragmentManager().setFragmentResult(DIALOG_STOKE_WEAR_HOUSE, bundle);
                Toast.makeText(getContext(), getString(R.string.update_successfully), Toast.LENGTH_LONG).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), getString(R.string.error_update_category), Toast.LENGTH_LONG).show();
            }
        }
        //       getItems(null);

    }

    public void deleteStoke() {
        Bundle bundle = new Bundle();
        bundle.putLong(DELETE_STOKE_WEAR_HOUSES, intentStokeWearehouse.getLong(ID_STOCK_WAREHOUSE));
        bundle.putString(STOKE_WEAR_HOUSES, DELETE_STOKE_WEAR_HOUSES);
        getParentFragmentManager().setFragmentResult(DIALOG_STOKE_WEAR_HOUSE, bundle);
        Toast.makeText(getContext(), getString(R.string.delete_successfully), Toast.LENGTH_LONG).show();
    }

    public void loadSpinnerDataForCategory() {
//        viewModel.getAllNameCategoriesLiveData().observe(this, nameCategory -> {
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nameCategory);
//            binding.SPCodeCategoryStock.setAdapter(arrayAdapter);
//        });

        viewModel.getAllCategoriesLiveData().observe(this, nameCategory -> {
            arrayAdapterCategoryName = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nameCategory);
            binding.SPCodeCategoryStock.setAdapter(arrayAdapterCategoryName);
        });
    }

//    public void loadSpinnerDataForStore() {
//        viewModel.getAllNameStoresLiveData().observe(this, nameStore -> {
//            arrayAdapterTypeStore = new ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nameStore);
//
//            //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nameStore);
//            binding.SPCodeStoreStock.setAdapter(arrayAdapterTypeStore);
//
//        });
//    }

    public void loadSpinnerDataForStore() {
        viewModel.getAllStoresLiveData().observe(this, nameStore -> {
            arrayAdapterTypeStore = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, nameStore);
            binding.SPCodeStoreStock.setAdapter(arrayAdapterTypeStore);

        });
    }

    static class CheckZero implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                if (Integer.parseInt(s.toString()) < 1)
                    s.delete(0, s.length());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }
}