package com.imagine.mohamedtaha.store.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.imagine.mohamedtaha.store.ui.fragments.categories.addCategoryFragment;
import com.imagine.mohamedtaha.store.ui.fragments.permissions.PermissionsFragment;
import com.imagine.mohamedtaha.store.ui.fragments.stores.AddStoreFragment;
/**
 * Created by Mohamed Taha on 25/11/17.
 */

public class CategoryAdapter extends FragmentStateAdapter {
    public CategoryAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new addCategoryFragment();
        } else if (position == 1) {
            return new AddStoreFragment();
        } else {
            return new PermissionsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}