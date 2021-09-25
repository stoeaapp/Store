package com.imagine.mohamedtaha.store.ui.fragments.adds.stores;

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

import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.databinding.FragmentAddStoreBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Stores;
import com.imagine.mohamedtaha.store.ui.fragments.adds.stores.adapter.AdapterAddStore;

import java.util.ArrayList;
import java.util.List;

import static com.imagine.mohamedtaha.store.Constant.ADD_STORES;
import static com.imagine.mohamedtaha.store.Constant.DELETE_STORES;
import static com.imagine.mohamedtaha.store.Constant.DIALOG_STORE;
import static com.imagine.mohamedtaha.store.Constant.ID_STORE;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.Constant.STORES;
import static com.imagine.mohamedtaha.store.Constant.TYPE_STORE;
import static com.imagine.mohamedtaha.store.Constant.UPDATE_STORES;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;

public class StoresFragment extends Fragment {
    private FragmentAddStoreBinding binding;
    private static AdapterAddStore adapterAddStore;
    private StoreViewModel viewModel;
    public static ArrayList<Stores> itemsStores = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
        final Observer<List<Stores>> storesObserver = itemsStores -> {
            if (itemsStores.size() > 0) {
                binding.progressBar.setVisibility(View.GONE);
                binding.RecyclerViewAddStore.setVisibility(View.VISIBLE);
                adapterAddStore.swapData(itemsStores);
                binding.emptyViewStore.setVisibility(View.GONE);
            } else {
                binding.RecyclerViewAddStore.setVisibility(View.GONE);
                binding.emptyViewStore.setVisibility(View.VISIBLE);
            }
        };
        viewModel.getAllStoresLiveData().observe(this, storesObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddStoreBinding.inflate(getLayoutInflater(), container, false);
        adapterAddStore = new AdapterAddStore((stores, view) -> {
            Bundle bundle = new Bundle();
            if (stores.getId() != null) bundle.putLong(ID_STORE, stores.getId());
            bundle.putString(TYPE_STORE, stores.getTypeStore());
            bundle.putString(NOTES, stores.getNotes());
            EditStoreFragment f = new EditStoreFragment();
            f.setArguments(bundle);
            f.show(getChildFragmentManager(), DIALOG_STORE);
        }, itemsStores);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        binding.RecyclerViewAddStore.setLayoutManager(layoutManager);
        binding.RecyclerViewAddStore.setAdapter(adapterAddStore);
        binding.fabAddStore.setOnClickListener(view -> new EditStoreFragment().show(getChildFragmentManager(), DIALOG_STORE));
        getChildFragmentManager().setFragmentResultListener(DIALOG_STORE, requireActivity(), (requestKey, result) -> {
            String getKey = result.getString(STORES);
            switch (getKey) {
                case ADD_STORES:
                    Stores addStore = (Stores) result.getSerializable(ADD_STORES);
                    viewModel.insertStore(addStore);
                    break;
                case UPDATE_STORES:
                    Stores updateStore = (Stores) result.getSerializable(UPDATE_STORES);
                    viewModel.updateStore(updateStore.getId(), updateStore.getTypeStore(), updateStore.getNotes(), getDate());
                    break;
                case DELETE_STORES:
                    long id = result.getLong(DELETE_STORES);
                    viewModel.deleteStore(id);
                    break;
            }
        });
        return binding.getRoot();
    }
}