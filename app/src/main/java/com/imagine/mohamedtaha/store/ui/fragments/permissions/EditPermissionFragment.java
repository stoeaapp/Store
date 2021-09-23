package com.imagine.mohamedtaha.store.ui.fragments.permissions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.imagine.mohamedtaha.store.Constant;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.databinding.FragmentEditStoreBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Permissions;
import com.imagine.mohamedtaha.store.util.DialogUtils;

import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditPermissionFragment extends BottomSheetDialogFragment {
    private FragmentEditStoreBinding binding;
    private StoreViewModel viewModel;
    Bundle intent;
    TaskDbHelper dbHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
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
                //  viewModel.insertPermissions(itemSavePermission);
                Toast.makeText(getContext(), getString(R.string.save_permission), Toast.LENGTH_LONG).show();

                //  requireActivity().finish();
                Bundle bundle = new Bundle();
                bundle.putSerializable("taha", itemSavePermission);
                getParentFragmentManager().setFragmentResult(Constant.DIALOG_PERMISSION, bundle);
                dismiss();
            }
        } else {

            Permissions itemUpdatePermision = new Permissions(namePermission, notes);
            itemUpdatePermision.setId(intent.getLong(Constant.ID_PERMISSION));
            itemUpdatePermision.setUpdatedAt(getDate());
            boolean isExistForUpdated = dbHelper.isNamePermissioneUsedDailyMovements(intent.getInt(Constant.ID_PERMISSION));
            if (isExistForUpdated == true) {
                Toast.makeText(getContext(), getString(R.string.this_permission_not_updated), Toast.LENGTH_SHORT).show();
                return;
            }

            if (itemUpdatePermision != null) {
                viewModel.updatePermissions(intent.getLong(Constant.ID_PERMISSION), namePermission, notes, getDate());
                //dbHelper.updatePermission(itemUpdatePermision);
                Toast.makeText(getContext(), getString(R.string.update_permission), Toast.LENGTH_LONG).show();
                dismiss();
                //  dialog.dismiss();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("taha",itemUpdatePermision);
//                getParentFragmentManager().setFragmentResult(Constant.DIALOG_PERMISSION,bundle);
            } else {
                Toast.makeText(getContext(), getString(R.string.error_update_permission), Toast.LENGTH_LONG).show();
            }

        }
//        Intent resultCalue = new Intent() ;
//        resultCalue.putExtra("data"," Iam back");
//        getActivity().setResult(Activity.RESULT_OK, resultCalue);
//        getActivity().onBackPressed();
    }

    public void deleteStore() {
        if (intent != null) {
            boolean isExist = dbHelper.isNamePermissioneUsedDailyMovements(intent.getInt(Constant.ID_PERMISSION));
            if (isExist) {
                Toast.makeText(getContext(), getString(R.string.this_permission_used), Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.deletePermissions(intent.getLong(Constant.ID_PERMISSION));
            Toast.makeText(getContext(), getString(R.string.delete_permission), Toast.LENGTH_LONG).show();
        }
    }
}