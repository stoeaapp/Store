package com.imagine.mohamedtaha.store.ui.fragments.dailymovement;

import android.app.AlertDialog;
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
import androidx.lifecycle.Observer;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.databinding.FragmentEditDailyMovementsBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.DailyMovements;
import com.imagine.mohamedtaha.store.util.DialogUtils;

import java.util.List;

import static com.imagine.mohamedtaha.store.Constant.CONVERT_TO_DAILY;
import static com.imagine.mohamedtaha.store.Constant.IDDaily;
import static com.imagine.mohamedtaha.store.Constant.INCOMING_DAILY;
import static com.imagine.mohamedtaha.store.Constant.ISSUED_DAILY;
import static com.imagine.mohamedtaha.store.Constant.NAME_ITEM;
import static com.imagine.mohamedtaha.store.Constant.NAME_PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.TYPE_STORE;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditDailyMovementsFragment extends BottomSheetDialogFragment {
    private FragmentEditDailyMovementsBinding binding;
    private StoreViewModel viewModel;
    Bundle intentDailyMovement;
    AlertDialog dialogDeleteDailyMovement;
    long idSpinnerCategory, idSpinnerStore, idSpinnerPermission, idSpinnerConvertTo;
    int mFirstBalance = 0;
    int mSumIncoming = 0;
    int mSumIssued = 0;
    int mSumConvertTo = 0;
    int currentBalance = 0;
    int isLastRow;
    String SpinnerCategory, SpinnerStore, SpinnerPermission;
    String SpinnerConvertTo = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditDailyMovementsBinding.inflate(getLayoutInflater());

        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);

        binding.ETIncoming.addTextChangedListener(new CheckZero());
        binding.ETIssued.addTextChangedListener(new CheckZero());
        intentDailyMovement = getArguments();
        if (intentDailyMovement != null) {
            binding.BTDeleteDailyMovement.setVisibility(View.VISIBLE);
            binding.BTAddDailyMovement.setText(getString(R.string.action_edit));
            binding.TVTitleDailyMovement.setText(getString(R.string.update_daily_movement_titile));
            binding.SPermissionDaily.setText(intentDailyMovement.getString(NAME_PERMISSION));
            binding.SPStoreDaily.setText(intentDailyMovement.getString(TYPE_STORE));
            binding.SPCategoryDaily.setText(intentDailyMovement.getString(NAME_ITEM));
            if (intentDailyMovement.getInt(INCOMING_DAILY) != 0) {
                binding.ETIncoming.setVisibility(View.VISIBLE);
            }
            binding.ETIncoming.setText(String.valueOf(intentDailyMovement.getInt(INCOMING_DAILY)));
            if (intentDailyMovement.getInt(ISSUED_DAILY) != 0) {
                binding.ETIssued.setVisibility(View.VISIBLE);
            }
            binding.ETIssued.setText(String.valueOf(intentDailyMovement.getInt(ISSUED_DAILY)));
            if (intentDailyMovement.getString(CONVERT_TO_DAILY) != null) {
                binding.SPCovertToDaily.setVisibility(View.VISIBLE);
            }
            binding.SPCovertToDaily.setText(intentDailyMovement.getString(CONVERT_TO_DAILY));


        }
        binding.BTAddDailyMovement.setOnClickListener(v -> saveDailyMovement());
        binding.BTDeleteDailyMovement.setOnClickListener(v -> DialogUtils.showMessageWithYesNoMaterialDesign(requireContext(), getString(R.string.title_delete_permission), getString(R.string.delete_dialog_msg_permission), (dialog, which) -> {
            deleteDaily();
            dismiss();
        }));
        binding.SPermissionDaily.setOnItemClickListener((parent, view, position, id) -> {
            SpinnerPermission = parent.getItemAtPosition(position).toString();
            idSpinnerPermission = parent.getItemIdAtPosition(position + 1);
            showStateVisibility();
            binding.SPCategoryDaily.setText(" ");
            SpinnerCategory = "";
            idSpinnerCategory = 0;
            binding.ETCurentBalance.setText("");
            binding.ETShowText.setVisibility(View.INVISIBLE);
            binding.ETIncoming.setText("");
            binding.ETIssued.setText("");

        });
        binding.SPCategoryDaily.setOnItemClickListener((parent, view, position, id) -> {
            SpinnerCategory = parent.getItemAtPosition(position).toString();
            idSpinnerCategory = parent.getItemIdAtPosition(position + 1);
            viewModel.getFirstBalanceString(idSpinnerCategory, idSpinnerStore).observe(requireActivity(), firstBalance -> {
                if (firstBalance != null)
                    mFirstBalance = firstBalance;
                Log.d("iddd", " mFirstBalance " + mFirstBalance);
                getIssuedString();
            });
        });
        binding.SPStoreDaily.setOnItemClickListener((parent, view, position, id) -> {
            SpinnerStore = parent.getItemAtPosition(position).toString();
            idSpinnerStore = parent.getItemIdAtPosition(position + 1);
            binding.SPCategoryDaily.setText("");
            SpinnerCategory = "";
            idSpinnerCategory = 0;
            binding.ETCurentBalance.setText("");
            binding.ETShowText.setVisibility(View.INVISIBLE);
        });
        binding.SPCovertToDaily.setOnItemClickListener((parent, view, position, id) -> {
            SpinnerConvertTo = parent.getItemAtPosition(position).toString();
            idSpinnerConvertTo = parent.getItemIdAtPosition(position + 1);
        });
        loadSpinnerDataForCategory();
        loadSpinnerDataForStores();
        loadSpinnerDataForNamePermissions();
        return binding.getRoot();
    }

    private void getIssuedString() {
        viewModel.getIssuedString(idSpinnerCategory, idSpinnerStore).observe(requireActivity(), sumIssued -> {
            if (sumIssued != null)
                mSumIssued = sumIssued;
            getIncomingString();

        });
    }

    private void getIncomingString() {
        viewModel.getIncomingString(idSpinnerCategory, idSpinnerStore).observe(requireActivity(), sumIncoming -> {
            if (sumIncoming != null)
                mSumIncoming = sumIncoming;
            getIssuedConvertToString();

        });
    }

    private void getIssuedConvertToString() {
        viewModel.getIssuedConvertToString(idSpinnerCategory, idSpinnerStore).observe(requireActivity(), sumConvertTo -> {
            if (sumConvertTo != null)
                mSumConvertTo = sumConvertTo;
            currentBalance = mFirstBalance + mSumIncoming + mSumConvertTo - mSumIssued;
            binding.ETShowText.setVisibility(View.VISIBLE);
            binding.ETCurentBalance.setText(String.valueOf(currentBalance));
        });
    }

    public void showStateVisibility() {
        switch ((int) idSpinnerPermission) {
            case 1:
                binding.ETIssued.setVisibility(View.VISIBLE);
                binding.ETIncoming.setVisibility(View.GONE);
                binding.SPCovertToDaily.setVisibility(View.GONE);
                break;
            case 2:
                binding.ETIssued.setVisibility(View.GONE);
                binding.ETIncoming.setVisibility(View.VISIBLE);
                binding.SPCovertToDaily.setVisibility(View.GONE);
                break;
            case 3:
                binding.ETIssued.setVisibility(View.VISIBLE);
                binding.ETIncoming.setVisibility(View.GONE);
                binding.SPCovertToDaily.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void saveDailyMovement() {
        String incoming = binding.ETIncoming.getText().toString().trim();
        String issued = binding.ETIssued.getText().toString().trim();
        if (idSpinnerPermission == 0) {
            binding.SPermissionDaily.requestFocus();
            binding.SPermissionDaily.setError(getString(R.string.error_empty_permission));
            return;
        }
        if (idSpinnerStore == 0) {
            binding.SPStoreDaily.requestFocus();
            binding.SPStoreDaily.setError(getString(R.string.error_empty_store));
            return;
        }
        if (idSpinnerCategory == 0) {
            binding.SPCategoryDaily.requestFocus();
            binding.SPCategoryDaily.setError(getString(R.string.error_empty_category));
            return;
        }
        if (TextUtils.isEmpty(incoming) && TextUtils.isEmpty(issued)) {
            Toast.makeText(getContext(), getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
            return;
        }
        if (idSpinnerPermission == 3 && idSpinnerConvertTo == 0) {
            binding.SPCovertToDaily.requestFocus();
            binding.SPCovertToDaily.setError(getString(R.string.error_convert_to));
            return;
        }
        if (idSpinnerPermission == 1 || idSpinnerPermission == 3 || idSpinnerPermission == 4) {
            int issuedInteger = Integer.parseInt(issued);
            if (issuedInteger > currentBalance) {
                binding.ETIssued.requestFocus();
                binding.ETIssued.setError(getString(R.string.error_issued_balance));
                return;
            }
        }

        if (intentDailyMovement == null) {
            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)) {
                incomings = Integer.parseInt(incoming);
            }
            int issueds = 0;
            if (!TextUtils.isEmpty(issued)) {
                issueds = Integer.parseInt(issued);
            }
            if (idSpinnerStore == idSpinnerConvertTo) {
                binding.SPCovertToDaily.requestFocus();
                binding.SPCovertToDaily.setError(getString(R.string.error_same_store));
                return;
            }
            DailyMovements itemSaveDaily = new DailyMovements(idSpinnerCategory, idSpinnerStore, idSpinnerPermission, incomings, issueds);
            itemSaveDaily.setCreatedAt(getDate());
            itemSaveDaily.setTime(getTime());
            if (idSpinnerConvertTo > 0)
                itemSaveDaily.setConvertTo(idSpinnerConvertTo);
            viewModel.insertDailyMovement(itemSaveDaily);
            Toast.makeText(getContext(), getString(R.string.save_daily), Toast.LENGTH_LONG).show();
            dismiss();
        } else {
            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)) {
                incomings = Integer.parseInt(incoming);
            }
            int issueds = 0;
            if (!TextUtils.isEmpty(issued)) {
                issueds = Integer.parseInt(issued);
            }
            viewModel.updateDailyMovement(intentDailyMovement.getInt(IDDaily), idSpinnerPermission, idSpinnerCategory, idSpinnerStore, idSpinnerConvertTo
                    , incomings, issueds, getDate());
            Toast.makeText(getContext(), getString(R.string.update_daily), Toast.LENGTH_LONG).show();
            dismiss();
        }
    }

    public void deleteDaily() {
        if (intentDailyMovement != null) {
            String incoming = binding.ETIncoming.getText().toString();
            String issued = binding.ETIssued.getText().toString();
            ItemsStore itemDeleteDaily = new ItemsStore();
            itemDeleteDaily.setId(intentDailyMovement.getInt(IDDaily));
            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)) {
                incomings = Integer.parseInt(incoming);
            }
            itemDeleteDaily.setIncoming(incomings);

            int issueds = 0;
            if (!TextUtils.isEmpty(issued)) {
                issueds = Integer.parseInt(issued);
            }
            itemDeleteDaily.setIssued(issueds);
            if (isLastRow != intentDailyMovement.getInt(IDDaily)) {
                Toast.makeText(getContext(), getString(R.string.this_movement_not_allow), Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.deleteDailyMovement(intentDailyMovement.getInt(IDDaily));
            Toast.makeText(getContext(), getString(R.string.delete_daily), Toast.LENGTH_LONG).show();
            dismiss();
            dialogDeleteDailyMovement.dismiss();
        }
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

    public void loadSpinnerDataForCategory() {
        final Observer<List<String>> categoryName = itemCategory -> {
            ArrayAdapter<String> arrayAdapterNameCategory = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemCategory);
            binding.SPCategoryDaily.setAdapter(arrayAdapterNameCategory);
        };
        viewModel.getAllNameCategoriesLiveData().observe(this, categoryName);
    }

    public void loadSpinnerDataForStores() {
        final Observer<List<String>> nameStores = itemStores -> {
            ArrayAdapter<String> arrayAdapterStore = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemStores);
            binding.SPStoreDaily.setAdapter(arrayAdapterStore);
            binding.SPCovertToDaily.setAdapter(arrayAdapterStore);
        };
        viewModel.getAllNameStoresLiveData().observe(this, nameStores);
    }

    public void loadSpinnerDataForNamePermissions() {
        final Observer<List<String>> permissionObserver = itemsPermissions -> {
            if (itemsPermissions.size() > 0) {
                ArrayAdapter<String> arrayAdapterPermission = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemsPermissions);
                binding.SPermissionDaily.setAdapter(arrayAdapterPermission);
            }
        };
        viewModel.getAllNamePermissionsLiveData().observe(this, permissionObserver);
    }
}