package com.imagine.mohamedtaha.store.ui.fragments.adds.permissions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.Constant;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.ui.fragments.adds.permissions.adapter.AdapterAddPermission;
import com.imagine.mohamedtaha.store.databinding.FragmentAddPremissionBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Permissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.imagine.mohamedtaha.store.Constant.ADD_PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.DELETE_PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.PERMISSION;
import static com.imagine.mohamedtaha.store.Constant.UPDATE_PERMISSION;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;

public class PermissionsFragment extends Fragment {
    @Inject
    StoreViewModel viewModel;
    public static AdapterAddPermission adapterAddPermission;
    ArrayList<Permissions> itemsPermissions = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAddPremissionBinding binding = FragmentAddPremissionBinding.inflate(getLayoutInflater(), container, false);
        final Observer<List<Permissions>> permissionsObserver = itemsPermissions -> {
            if (itemsPermissions.size() > 0) {
                binding.progressBarPermission.setVisibility(View.GONE);
                binding.listViewAddPermission.setVisibility(View.VISIBLE);
                adapterAddPermission.swapData(itemsPermissions);
                binding.emptyViewPermission.setVisibility(View.GONE);
            } else {
                binding.listViewAddPermission.setVisibility(View.GONE);
                binding.emptyViewPermission.setVisibility(View.VISIBLE);
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.listViewAddPermission.setLayoutManager(layoutManager);
        viewModel.getAllPermissionsLiveData().observe(requireActivity(), permissionsObserver);
        adapterAddPermission = new AdapterAddPermission((permissions, view) -> {
            Bundle bundle = new Bundle();
            if (permissions.getId() != null)
                bundle.putLong(Constant.ID_PERMISSION, permissions.getId());
            bundle.putString(Constant.NAME_PERMISSION, permissions.getPermissionName());
            bundle.putString(Constant.NOTES, permissions.getNotes());
            EditPermissionFragment f = new EditPermissionFragment();
            f.setArguments(bundle);
            f.show(getChildFragmentManager(), Constant.DIALOG_PERMISSION);
        }, itemsPermissions);
        binding.listViewAddPermission.setAdapter(adapterAddPermission);
        binding.progressBarPermission.setVisibility(View.GONE);
        binding.fabAddPermission.setOnClickListener(view ->
                new EditPermissionFragment().show(getChildFragmentManager(), Constant.DIALOG_PERMISSION));

        getChildFragmentManager().setFragmentResultListener(Constant.DIALOG_PERMISSION, requireActivity(),
                (requestKey, result) -> {
                    String getKey = result.getString(PERMISSION);
                    switch (getKey) {
                        case ADD_PERMISSION:
                            Permissions addPermissions = (Permissions) result.getSerializable(ADD_PERMISSION);
                            viewModel.insertPermissions(addPermissions);
                            break;
                        case UPDATE_PERMISSION:
                            Permissions updatePermissions = (Permissions) result.getSerializable(UPDATE_PERMISSION);
                            viewModel.updatePermissions(updatePermissions.getId(), updatePermissions.getPermissionName(), updatePermissions.getNotes(), getDate());
                            break;
                        case DELETE_PERMISSION:
                            long id = result.getLong(DELETE_PERMISSION);
                            viewModel.deletePermissions(id);
                            break;
                    }
                });
        return binding.getRoot();
    }
}
