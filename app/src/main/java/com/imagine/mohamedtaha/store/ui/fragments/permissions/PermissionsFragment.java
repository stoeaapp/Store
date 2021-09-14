package com.imagine.mohamedtaha.store.ui.fragments.permissions;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;

import com.imagine.mohamedtaha.store.Constant;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.ResultCallback;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.adapter.AdapterAddPermission;
import com.imagine.mohamedtaha.store.databinding.FragmentAddPremissionBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Permissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
                Log.d("iddd"," " +itemsPermissions.size() );
                binding.progressBarPermission.setVisibility(View.GONE);
                binding.listViewAddPermission.setVisibility(View.VISIBLE);
                adapterAddPermission.swapData(itemsPermissions);
            } else {
                binding.listViewAddPermission.setVisibility(View.GONE);
                binding.emptyViewPermission.setVisibility(View.VISIBLE);

            }
        };
        viewModel.getAllPermissionsLiveData().observe(requireActivity(), permissionsObserver);
        adapterAddPermission = new AdapterAddPermission(getContext(), itemsPermissions);
        binding.listViewAddPermission.setEmptyView(binding.emptyViewPermission);
        binding.listViewAddPermission.setAdapter(adapterAddPermission);
        binding.progressBarPermission.setVisibility(View.GONE);
        binding.listViewAddPermission.setOnItemClickListener((parent, view, position, id) -> {
            Permissions itemPermission = itemsPermissions.get(position);
            Bundle bundle = new Bundle();
            if (itemPermission.getId() != null)
                bundle.putLong(Constant.ID_PERMISSION, itemPermission.getId());
            bundle.putString(Constant.NAME_PERMISSION, itemPermission.getPermissionName());
            bundle.putString(Constant.NOTES, itemPermission.getNotes());
            EditPermissionFragment f = new EditPermissionFragment();
            f.setArguments(bundle);
            f.show(getChildFragmentManager(), Constant.DIALOG_PERMISSION);
        });
        binding.fabAddPermission.setOnClickListener(view ->
        //        getContent.launch("next act"));
        new EditPermissionFragment().show(getChildFragmentManager(), Constant.DIALOG_PERMISSION));

        getChildFragmentManager().setFragmentResultListener(Constant.DIALOG_PERMISSION, getActivity(),
                       (requestKey, result) ->{
                           if (result != null) {
                               Permissions permissions = (Permissions) result.getSerializable("taha");
                               Log.d("iddd", requestKey + " " + result.toString());
                               viewModel.insertPermissions(permissions);



                           //    viewModel.updatePermissions(intent.getLong(Constant.ID_PERMISSION), namePermission, notes, getDate());

                           }
                           });
        return binding.getRoot();
    }

    private final ActivityResultLauncher<String> getContent = registerForActivityResult(new ResultCallback(), result -> Log.d("iddd", result));


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        // Inflate the menu; this adds items to the action bar if it is present.
        requireActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
