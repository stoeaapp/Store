package com.imagine.mohamedtaha.store.ui.fragments.adds.stores;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.databinding.FragmentEditStoreBinding;
import com.imagine.mohamedtaha.store.room.data.Stores;
import com.imagine.mohamedtaha.store.util.DialogUtils;

import static com.imagine.mohamedtaha.store.Constant.ADD_STORES;
import static com.imagine.mohamedtaha.store.Constant.DELETE_STORES;
import static com.imagine.mohamedtaha.store.Constant.DIALOG_STORE;
import static com.imagine.mohamedtaha.store.Constant.ID_STORE;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.Constant.STORES;
import static com.imagine.mohamedtaha.store.Constant.TYPE_STORE;
import static com.imagine.mohamedtaha.store.Constant.UPDATE_STORES;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditStoreFragment extends BottomSheetDialogFragment {
    private FragmentEditStoreBinding binding;
    Bundle intent;
    TaskDbHelper dbHelper;
    String checkTypeStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditStoreBinding.inflate(getLayoutInflater());
        dbHelper = new TaskDbHelper(getContext());
        intent = getArguments();
        binding.ETTypeStoreMaterial.setHint(getString(R.string.type_store));
        if (intent != null) {
            binding.BTAddStore.setText(getString(R.string.action_edit));
            binding.TVTitleStore.setText(getString(R.string.update_store_title));
            binding.BTDeleteStore.setVisibility(View.VISIBLE);
            binding.ETTypeStoreStore.setText(intent.getString(TYPE_STORE));
            binding.EtNotesStore.setText(intent.getString(NOTES));
            checkTypeStore = intent.getString(TYPE_STORE);
        }

        binding.BTAddStore.setOnClickListener(v -> saveStore());
        binding.BTDeleteStore.setOnClickListener(v -> DialogUtils.showMessageWithYesNoMaterialDesign(requireContext(), getString(R.string.title_delete_item), getString(R.string.delete_dialog_msg), (dialog, which) -> {
            deleteStore();
            dismiss();
        }));
        return binding.getRoot();
    }

    public void saveStore() {
        String typeStore = binding.ETTypeStoreStore.getText().toString().trim();
        String notesStore = binding.EtNotesStore.getText().toString().trim();
        boolean isExist = dbHelper.isExistTypeStore(typeStore);

        if (intent == null && TextUtils.isEmpty(typeStore) || TextUtils.isEmpty(typeStore)) {
            // ETTypeStore.setError("not should leave field name emputy");
            //   Toast.makeText(getContext(), getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
            binding.ETTypeStoreStore.requestFocus();
            binding.ETTypeStoreStore.setError(getString(R.string.error_empty_text));
            return;
        }

        if (intent == null) {
            Stores itemStoreSave = new Stores(typeStore, notesStore);
            itemStoreSave.setCreatedAt(getDate());
            itemStoreSave.setTime(getTime());
            if (isExist) {
                binding.ETTypeStoreStore.requestFocus();
                binding.ETTypeStoreStore.setError(getString(R.string.error_exist_name));
                return;
            }
            if (itemStoreSave == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_store), Toast.LENGTH_LONG).show();
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ADD_STORES, itemStoreSave);
                bundle.putString(STORES, ADD_STORES);
                getParentFragmentManager().setFragmentResult(DIALOG_STORE, bundle);
                Toast.makeText(getContext(), getString(R.string.save_store), Toast.LENGTH_LONG).show();
                dismiss();
            }
        } else {
            boolean isExistDialyMovements = dbHelper.isTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));
            boolean isExistStokeWearehouse = dbHelper.isTypeStoreUsedStokewearhouse(intent.getInt(ID_STORE));
            boolean isExistConvertDailyMovements = dbHelper.isConvertTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));


//            if (isExistDialyMovements == true || isExistStokeWearehouse == true || isExistConvertDailyMovements == true) {
//                Toast.makeText(getContext(), getString(R.string.this_store_not_updated), Toast.LENGTH_SHORT).show();
//                return;
//            }

            if (!checkTypeStore.equals(typeStore) && isExist) {
                binding.ETTypeStoreStore.requestFocus();
                binding.ETTypeStoreStore.setError(getString(R.string.error_exist_name));
                return;
            }
            Stores updateItemStore = new Stores(typeStore, notesStore);
            updateItemStore.setId(intent.getLong(ID_STORE));
            updateItemStore.setCreatedAt(getDate());
            Bundle bundle = new Bundle();
            bundle.putSerializable(UPDATE_STORES, updateItemStore);
            bundle.putString(STORES, UPDATE_STORES);
            getParentFragmentManager().setFragmentResult(DIALOG_STORE, bundle);
            Toast.makeText(getContext(), getString(R.string.update_store), Toast.LENGTH_LONG).show();
            dismiss();
        }
    }

    public void deleteStore() {
        if (intent != null) {
            boolean isExistDialyMovements = dbHelper.isTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));
            boolean isExistStokeWearehouse = dbHelper.isTypeStoreUsedStokewearhouse(intent.getInt(ID_STORE));
            boolean isExistConvertDailyMovements = dbHelper.isConvertTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));
//            if (isExistDialyMovements == true || isExistStokeWearehouse == true || isExistConvertDailyMovements == true) {
//                Toast.makeText(getContext(), getString(R.string.this_store_used), Toast.LENGTH_SHORT).show();
//                return;
//            }
            Bundle bundle = new Bundle();
            bundle.putLong(DELETE_STORES, requireArguments().getLong(ID_STORE));
            bundle.putString(STORES, DELETE_STORES);
            getParentFragmentManager().setFragmentResult(DIALOG_STORE, bundle);
            Toast.makeText(getContext(), getString(R.string.delete_store), Toast.LENGTH_LONG).show();
        }
    }
}