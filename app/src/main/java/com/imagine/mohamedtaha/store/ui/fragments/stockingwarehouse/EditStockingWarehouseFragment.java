package com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.databinding.FragmentEditStockingWarehouseBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Categories;
import com.imagine.mohamedtaha.store.room.data.StockingHouse;

import java.util.List;

import static com.imagine.mohamedtaha.store.Constant.CODE_NAME_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.CODE_TYPE_STORE;
import static com.imagine.mohamedtaha.store.Constant.FIRST_BALANCE;
import static com.imagine.mohamedtaha.store.Constant.ID_STOKE;
import static com.imagine.mohamedtaha.store.Constant.NOTES;

public class EditStockingWarehouseFragment extends DialogFragment {
    private StoreViewModel viewModel;
    private FragmentEditStockingWarehouseBinding binding;

    AlertDialog dialogStokeWearehouse;
    AlertDialog dialogDeleteStokeWearhouse;
    String SpinnerCategory, SpinnerStore;
    Bundle intentStokeWearehouse;
    long idSpinnerCategory, idSpinnerStore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        binding = FragmentEditStockingWarehouseBinding.inflate(getLayoutInflater());
        intentStokeWearehouse = getArguments();
        if (intentStokeWearehouse != null) {
            binding.BTDeleteStokeWarehouse.setVisibility(View.VISIBLE);
            binding.BTAddStokeWarehouse.setText(getString(R.string.action_edit));
            binding.TVTitleStokeWearhouse.setText(getString(R.string.update_stoke_titile));
            binding.SPCodeCategoryStock.setText(intentStokeWearehouse.getString(CODE_NAME_CATEGORY));
            binding.SPCodeStoreStock.setText(intentStokeWearehouse.getString(CODE_TYPE_STORE));
            binding.ETFirstBalanceStoke.setText(String.valueOf(intentStokeWearehouse.getInt(FIRST_BALANCE)));
            binding.ETNotesStoke.setText(intentStokeWearehouse.getString(NOTES));

        }
        binding.SPCodeCategoryStock.setOnItemClickListener((parent, view, position, id) -> {
            SpinnerCategory = parent.getItemAtPosition(position).toString();
            idSpinnerCategory = parent.getItemIdAtPosition(position + 1);
        });
        binding.SPCodeStoreStock.setOnItemClickListener((parent, view, position, id) -> {
            SpinnerStore = parent.getItemAtPosition(position).toString();
            idSpinnerStore = parent.getItemIdAtPosition(position + 1);
        });
        loadSpinnerDataForCategory();
        loadSpinnerDataForStore();
        binding.BTAddStokeWarehouse.setOnClickListener(v -> saveStokeWearehouse());
        binding.BTDeleteStokeWarehouse.setOnClickListener(v -> showDeleteConfirmationDialog());
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(binding.getRoot());
        dialogStokeWearehouse = builder.create();
        dialogStokeWearehouse.show();
        return dialogStokeWearehouse;
    }

    public void saveStokeWearehouse() {
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
            StockingHouse itemsSaveStoke = new StockingHouse(idSpinnerCategory, idSpinnerStore, firstBalance, "", noteStoke, "", "");
//            itemsSaveStoke.setId_code_category(idSpinnerCategory);
//            itemsSaveStoke.setId_code_store(idSpinnerStore);
//            itemsSaveStoke.setFirst_balanse(Integer.valueOf(firstBalance));
//            itemsSaveStoke.setNotes_(noteStoke);
            if (itemsSaveStoke == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_category), Toast.LENGTH_LONG).show();
            } else {

                viewModel.insertStokeWarehouse(itemsSaveStoke);
                //  dbHelperStokeWearehouse.addStokeWarehouse(itemsSaveStoke);
                Toast.makeText(getContext(), getString(R.string.save_category), Toast.LENGTH_LONG).show();
                dialogStokeWearehouse.dismiss();
            }
        } else {
            ItemsStore itemUpdateStoke = new ItemsStore();
            itemUpdateStoke.setId(intentStokeWearehouse.getInt(ID_STOKE));
            itemUpdateStoke.setId_code_category(idSpinnerCategory);
            itemUpdateStoke.setId_code_store(idSpinnerStore);
            itemUpdateStoke.setFirst_balanse(Integer.valueOf(firstBalance));
            itemUpdateStoke.setNotes(noteStoke);
            //    boolean isExistConvertDailyMovements = dbHelperStokeWearehouse.isFirstBalanceUsedStokewearhouse(intentStokeWearehouse.getInt(ID_STOKE));
//            if (isExistConvertDailyMovements ==true){
//                Toast.makeText(getContext(), getString(R.string.this_category_not_updated), Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (itemUpdateStoke != null) {

                //     dbHelperStokeWearehouse.updateStokeWarehouse(itemUpdateStoke);
                Toast.makeText(getContext(), getString(R.string.update_category), Toast.LENGTH_LONG).show();
                dialogStokeWearehouse.dismiss();
            } else {
                Toast.makeText(getContext(), getString(R.string.error_update_category), Toast.LENGTH_LONG).show();
            }
        }
        //       getItems(null);

    }

    public void deleteStoke() {
        if (intentStokeWearehouse != null) {
            ItemsStore itemDeletePermision = new ItemsStore();
            itemDeletePermision.setId(intentStokeWearehouse.getInt(ID_STOKE));
            //itemDeletePermision.setFirst_balanse(Integer.valueOf(firstBalance));
            //itemDeletePermision.setNotes(noteStoke);
//            boolean isExistConvertDailyMovements = dbHelperStokeWearehouse.isFirstBalanceUsedStokewearhouse(intentStokeWearehouse.getInt(ID_STOKE));
//            if (isExistConvertDailyMovements ==true){
//                Toast.makeText(getContext(), getString(R.string.this_category_not_updated), Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (itemDeletePermision != null) {
                //  dbHelperStokeWearehouse.deleteStokeWareHouse(itemDeletePermision);
                Toast.makeText(getContext(), getString(R.string.delete_category), Toast.LENGTH_LONG).show();
                dialogStokeWearehouse.dismiss();
                dialogDeleteStokeWearhouse.dismiss();

                // getActivity().finish();

            } else {
                Toast.makeText(getContext(), getString(R.string.error_delete_category), Toast.LENGTH_LONG).show();
            }
        } else {
            // Toast.makeText(getActivity(), "Not Data For Deleted", Toast.LENGTH_LONG).show();
            return;
        }

    }

    private void showDeleteConfirmationDialog() {
        //Create an AlertDialog.Builder and set the message,and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.BTDelete, (dialog, which) -> {
            //User clicked the "Delete" button,so delete the Category
            deleteStoke();

        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            //USer clciked the "cancel" button ,so dismiss the dialog and continue editing the category
            if (dialog != null) {
                dialog.dismiss();
            }
        });
        //Create and show the AlertDialog
        dialogDeleteStokeWearhouse = builder.create();
        dialogDeleteStokeWearhouse.show();
    }

    public void loadSpinnerDataForCategory() {
        viewModel.getAllNameCategoriesLiveData().observe(this, nameCategory -> {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nameCategory);
            binding.SPCodeCategoryStock.setAdapter(arrayAdapter);
        });
    }

    public void loadSpinnerDataForStore() {
        viewModel.getAllNameStoresLiveData().observe(this, nameStore -> {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nameStore);
            binding.SPCodeStoreStock.setAdapter(arrayAdapter);

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

            }

        }
    }
}