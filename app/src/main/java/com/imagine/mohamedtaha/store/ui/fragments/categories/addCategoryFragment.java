package com.imagine.mohamedtaha.store.ui.fragments.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.adapter.AdapterAddCategory;
import com.imagine.mohamedtaha.store.databinding.FragmentCategoryBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Categories;

import java.util.ArrayList;
import java.util.List;

import static com.imagine.mohamedtaha.store.Constant.DIALOG_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.ID_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.NAME_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.NATURAL_CATEGORY;
import static com.imagine.mohamedtaha.store.Constant.NOTES;

public class addCategoryFragment extends Fragment {
    private FragmentCategoryBinding binding;
    AdapterAddCategory adapterAddCategory;
    private List<Categories> itemCategory = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StoreViewModel viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
        final Observer<List<Categories>> categoryObserver = itemCategory -> {
            if (itemCategory.size() > 0) {
                binding.recycleViewAddCategory.setVisibility(View.VISIBLE);
                binding.emptyViewCategory.setVisibility(View.GONE);
                this.itemCategory = itemCategory;
                adapterAddCategory.swapData(itemCategory);
            } else {
                binding.recycleViewAddCategory.setVisibility(View.GONE);
                binding.emptyViewCategory.setVisibility(View.VISIBLE);
            }
        };
        viewModel.getAllCategoriesLiveData().observe(this, categoryObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(getLayoutInflater(), container, false);
        //view = inflater.inflate(R.layout.fragment_category, container, false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recycleViewAddCategory.setLayoutManager(mLayoutManager);
        adapterAddCategory = new AdapterAddCategory(getActivity(), itemCategory, cursor -> {
            Categories itemsStore = itemCategory.get(cursor);
            Bundle bundle = new Bundle();
            if (itemsStore.getId() != null)
                bundle.putLong(ID_CATEGORY, itemsStore.getId());
            bundle.putString(NAME_CATEGORY, itemsStore.getCategoryName());
            bundle.putString(NATURAL_CATEGORY, itemsStore.getNaturalCategory());
            bundle.putString(NOTES, itemsStore.getNotes());
            EditFragmentCategory f = new EditFragmentCategory();
            f.setArguments(bundle);
            f.show(getChildFragmentManager(), DIALOG_CATEGORY);
        });
        binding.recycleViewAddCategory.setAdapter(adapterAddCategory);

        binding.fabAddCategory.setOnClickListener(view -> new EditFragmentCategory().show(getChildFragmentManager(), DIALOG_CATEGORY));
        return binding.getRoot();
    }
}