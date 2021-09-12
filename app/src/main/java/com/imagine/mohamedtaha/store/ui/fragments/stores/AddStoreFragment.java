package com.imagine.mohamedtaha.store.ui.fragments.stores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.adapter.AdapterAddStore;
import com.imagine.mohamedtaha.store.databinding.FragmentAddStoreBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Stores;

import java.util.ArrayList;
import java.util.List;

import static com.imagine.mohamedtaha.store.Constant.DIALOG_STORE;
import static com.imagine.mohamedtaha.store.Constant.ID_STORE;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.Constant.TYPE_STORE;


public class AddStoreFragment extends Fragment {
    private FragmentAddStoreBinding binding;
    private static AdapterAddStore adapterAddStore;
    public static ArrayList<Stores> itemsStores = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StoreViewModel viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
        final Observer<List<Stores>> storesObserver = itemsStores -> {
            if (itemsStores.size() > 0) {
                binding.progressBar.setVisibility(View.GONE);
                binding.listViewAddStore.setVisibility(View.VISIBLE);
                adapterAddStore.swapData(itemsStores);
            } else {
                binding.listViewAddStore.setVisibility(View.GONE);
                binding.emptyViewStore.setVisibility(View.VISIBLE);
            }
        };
        viewModel.getAllStoresLiveData().observe(this, storesObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddStoreBinding.inflate(getLayoutInflater(), container, false);
        //   view = inflater.inflate(R.layout.fragment_add_store, container, false);
        //Set the RecycleView to its corresponding view
        adapterAddStore = new AdapterAddStore(getContext(), itemsStores);
        binding.listViewAddStore.setEmptyView(binding.emptyViewStore);
        binding.listViewAddStore.setAdapter(adapterAddStore);
        //Setup the item click listener
        binding.listViewAddStore.setOnItemClickListener((parent, view, position, id) -> {
            Stores itemsStore = itemsStores.get(position);
            Bundle bundle = new Bundle();
            if (itemsStore.getId() != null)
                bundle.putLong(ID_STORE, itemsStore.getId());
            bundle.putString(TYPE_STORE, itemsStore.getTypeStore());
            bundle.putString(NOTES, itemsStore.getNotes());
            EditStoreFragment f = new EditStoreFragment();
            f.setArguments(bundle);
            f.show(getChildFragmentManager(), DIALOG_STORE);
        });
        binding.fabAddStore.setOnClickListener(view -> new EditStoreFragment().show(getChildFragmentManager(), DIALOG_STORE));
        return binding.getRoot();
    }
}