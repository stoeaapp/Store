package com.imagine.mohamedtaha.store.ui.fragments;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.adapter.AdapterAddPermission;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.databinding.FragmentAddPremissionBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Permissions;

import java.util.ArrayList;
import java.util.List;

public class PermissionsFragment extends Fragment {
    private StoreViewModel viewModel;

    public PermissionsFragment() {
        // Required empty public constructor
    }

    public static final String ID_PERMISSION = "id";
    public static final String NAME_PERMISION = "namePErmission";
    public static final String NOTES_PERMISSION = "notes";
    public static final String DIALOG_PERMISSION = "dialogPermission";

    public static AdapterAddPermission adapterAddPermission;
    TaskDbHelper dbHelper;
    ArrayList<Permissions> itemsPermissions = new ArrayList<Permissions>();
    //Identifier for the category dataloader;
    public static final int PERMISSION_LOADER = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) getActivity().getApplication()).getRepository()).create(StoreViewModel.class);

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
            }
        };
        viewModel.getAllPermissionsLiveData().observe(requireActivity(), permissionsObserver);
        adapterAddPermission = new AdapterAddPermission(getContext(), itemsPermissions);
        binding.listViewAddPermission.setEmptyView(binding.emptyViewPermission);
        binding.listViewAddPermission.setAdapter(adapterAddPermission);
        binding.progressBarPermission.setVisibility(View.GONE);

        dbHelper = new TaskDbHelper(getActivity());


        binding.listViewAddPermission.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //   Toast.makeText(getContext(), "Click :"+ position, Toast.LENGTH_SHORT).show();
                Permissions itemSPermision = itemsPermissions.get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(ID_PERMISSION, itemSPermision.getId());
                bundle.putString(NAME_PERMISION, itemSPermision.getPermissionName());
                bundle.putString(NOTES_PERMISSION, itemSPermision.getNotes());
                EditPermissionFragment f = new EditPermissionFragment();
                f.setArguments(bundle);
                f.show(getFragmentManager(), DIALOG_PERMISSION);
            }
        });
        binding.fabAddPermission.setOnClickListener(view -> new EditPermissionFragment().show(getFragmentManager(), DIALOG_PERMISSION));
        return binding.getRoot();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
