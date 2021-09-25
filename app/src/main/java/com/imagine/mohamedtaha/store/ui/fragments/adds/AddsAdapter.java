package com.imagine.mohamedtaha.store.ui.fragments.adds;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.imagine.mohamedtaha.store.ui.fragments.adds.items.ItemsFragment;
import com.imagine.mohamedtaha.store.ui.fragments.adds.permissions.PermissionsFragment;
import com.imagine.mohamedtaha.store.ui.fragments.adds.stores.StoresFragment;
/**
 * Created by Mohamed Taha on 25/11/17.
 */

public class AddsAdapter extends FragmentStateAdapter {
    public AddsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ItemsFragment();
        } else if (position == 1) {
            return new StoresFragment();
        } else {
            return new PermissionsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}