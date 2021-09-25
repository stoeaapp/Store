package com.imagine.mohamedtaha.store.ui.fragments.adds.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.databinding.FragmentCategoryBinding;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Categories;
import com.imagine.mohamedtaha.store.ui.fragments.adds.items.adapter.AdapterAddItems;

import java.util.ArrayList;
import java.util.List;

import static com.imagine.mohamedtaha.store.Constant.ADD_ITEMS;
import static com.imagine.mohamedtaha.store.Constant.DELETE_ITEMS;
import static com.imagine.mohamedtaha.store.Constant.DIALOG_ITEMS;
import static com.imagine.mohamedtaha.store.Constant.ID_ITEM;
import static com.imagine.mohamedtaha.store.Constant.ITEMS;
import static com.imagine.mohamedtaha.store.Constant.NAME_ITEM;
import static com.imagine.mohamedtaha.store.Constant.NATURAL_ITEM;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.Constant.UPDATE_ITEMS;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;

public class ItemsFragment extends Fragment {
    private FragmentCategoryBinding binding;
    private StoreViewModel viewModel;
    AdapterAddItems adapterAddItems;
    private List<Categories> itemCategory = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);
        final Observer<List<Categories>> categoryObserver = itemCategory -> {
            if (itemCategory.size() > 0) {
                binding.recycleViewAddCategory.setVisibility(View.VISIBLE);
                binding.emptyViewCategory.setVisibility(View.GONE);
                this.itemCategory = itemCategory;
                adapterAddItems.swapData(itemCategory);
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
        binding = FragmentCategoryBinding.inflate(getLayoutInflater(), container, false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
        binding.recycleViewAddCategory.setLayoutManager(mLayoutManager);
        adapterAddItems = new AdapterAddItems(itemCategory, (categories, view) -> {
            Bundle bundle = new Bundle();
            if (categories.getId() != null)
                bundle.putLong(ID_ITEM, categories.getId());
            bundle.putString(NAME_ITEM, categories.getCategoryName());
            bundle.putString(NATURAL_ITEM, categories.getNaturalCategory());
            bundle.putString(NOTES, categories.getNotes());
            EditFragmentItems f = new EditFragmentItems();
            f.setArguments(bundle);
            f.show(getChildFragmentManager(), DIALOG_ITEMS);
        });
        binding.recycleViewAddCategory.setAdapter(adapterAddItems);

        binding.fabAddCategory.setOnClickListener(view -> new EditFragmentItems().show(getChildFragmentManager(), DIALOG_ITEMS));

        getChildFragmentManager().setFragmentResultListener(DIALOG_ITEMS, requireActivity(), ((requestKey, result) -> {
            String getKey = result.getString(ITEMS);
            switch (getKey) {
                case ADD_ITEMS:
                    Categories addItem = (Categories) result.getSerializable(ADD_ITEMS);
                    viewModel.insertCategory(addItem);
                    break;
                case UPDATE_ITEMS:
                    Categories updateItem = (Categories) result.getSerializable(UPDATE_ITEMS);
                    viewModel.updateCategory(updateItem.getId(), updateItem.getCategoryName(), updateItem.getNaturalCategory(), updateItem.getNotes(), getDate());
                    break;
                case DELETE_ITEMS:
                    long idItem = result.getLong(DELETE_ITEMS);
                    viewModel.deleteCategory(idItem);
                    break;
            }
        }));
        return binding.getRoot();
    }
}