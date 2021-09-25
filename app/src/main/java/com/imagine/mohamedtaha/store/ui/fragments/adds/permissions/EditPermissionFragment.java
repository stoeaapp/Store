package com.imagine.mohamedtaha.store.ui.fragments.adds.permissions;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.imagine.mohamedtaha.store.Constant;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.databinding.FragmentEditStoreBinding;
import com.imagine.mohamedtaha.store.room.data.Permissions;
import com.imagine.mohamedtaha.store.util.DialogUtils;

import static com.imagine.mohamedtaha.store.Constant.ADD_PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.DELETE_PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.DIALOG_PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.UPDATE_PERMISSION;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditPermissionFragment extends BottomSheetDialogFragment {
    private FragmentEditStoreBinding binding;
    Bundle intent;
    TaskDbHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditStoreBinding.inflate(getLayoutInflater(), container, false);
        dbHelper = new TaskDbHelper(getContext());
        intent = getArguments();
        if (intent != null) {
            binding.BTAddStore.setText(getString(R.string.action_edit));
            binding.TVTitleStore.setText(getString(R.string.update_permission_title));

            binding.BTDeleteStore.setVisibility(View.VISIBLE);
            binding.ETTypeStoreStore.setText(intent.getString(Constant.NAME_PERMISSION));
            binding.EtNotesStore.setText(intent.getString(Constant.NOTES));
        }
        binding.BTAddStore.setOnClickListener(v -> saveStore());
        binding.BTDeleteStore.setOnClickListener(v -> DialogUtils.showMessageWithYesNoMaterialDesign(requireContext(), getString(R.string.title_delete_permission), getString(R.string.delete_dialog_msg_permission), (dialog, which) -> {
            deleteStore();
            dismiss();
        }));
        return binding.getRoot();
    }

    public void saveStore() {
        String namePermission = binding.ETTypeStoreStore.getText().toString().trim();
        String notes = binding.EtNotesStore.getText().toString().trim();
        if (intent == null && TextUtils.isEmpty(namePermission) || TextUtils.isEmpty(namePermission)) {
            binding.ETTypeStoreStore.requestFocus();
            binding.ETTypeStoreStore.setError(getString(R.string.error_empty_text));
            return;
        }
       /* if (isExist ==true){
            ETNamePermission.requestFocus();
            ETNamePermission.setError(getString(R.string.error_exist_permission));
            return;
        }*/
        if (intent == null) {
            Permissions itemSavePermission = new Permissions(namePermission, notes);
            itemSavePermission.setTime(getTime());
            itemSavePermission.setCreatedAt(getDate());
            if (itemSavePermission == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_permission), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.save_permission), Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable(ADD_PERMISSION, itemSavePermission);
                bundle.putString(PERMISSION, ADD_PERMISSION);
                getParentFragmentManager().setFragmentResult(Constant.DIALOG_PERMISSION, bundle);
                dismiss();
            }
        } else {
            Permissions itemUpdatePermision = new Permissions(namePermission, notes);
            itemUpdatePermision.setId(intent.getLong(Constant.ID_PERMISSION));
            itemUpdatePermision.setUpdatedAt(getDate());
            boolean isExistForUpdated = dbHelper.isNamePermissioneUsedDailyMovements(intent.getInt(Constant.ID_PERMISSION));
            if (isExistForUpdated) {
                Toast.makeText(getContext(), getString(R.string.this_permission_not_updated), Toast.LENGTH_SHORT).show();
                return;
            }
            if (itemUpdatePermision != null) {
                Toast.makeText(getContext(), getString(R.string.update_permission), Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable(UPDATE_PERMISSION, itemUpdatePermision);
                bundle.putString(PERMISSION, UPDATE_PERMISSION);
                getParentFragmentManager().setFragmentResult(DIALOG_PERMISSION, bundle);
                dismiss();
            } else {
                Toast.makeText(getContext(), getString(R.string.error_update_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void deleteStore() {
        if (intent != null) {
            boolean isExist = dbHelper.isNamePermissioneUsedDailyMovements(intent.getInt(Constant.ID_PERMISSION));
            if (isExist) {
                Toast.makeText(getContext(), getString(R.string.this_permission_used), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), getString(R.string.delete_permission), Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            bundle.putLong(DELETE_PERMISSION, intent.getLong(Constant.ID_PERMISSION));
            bundle.putString(PERMISSION, DELETE_PERMISSION);
            getParentFragmentManager().setFragmentResult(DIALOG_PERMISSION,bundle);
        }
    }
}