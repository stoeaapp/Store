package com.imagine.mohamedtaha.store.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.adapter.CategoryAdapter;
import com.imagine.mohamedtaha.store.databinding.ActivityForIncludeFragmentsBinding;
import com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse.StockingWarehouse;

public class ActivityForIncludeFragments extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.imagine.mohamedtaha.store.databinding.ActivityForIncludeFragmentsBinding binding = ActivityForIncludeFragmentsBinding.inflate(inflater, container, false);
        CategoryAdapter adapter = new CategoryAdapter(requireActivity(), requireActivity().getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        requireActivity().invalidateOptionsMenu();
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItemAddData = menu.findItem(R.id.add_data);
        MenuItem menuItemActionSearch = menu.findItem(R.id.action_search);

        menuItemAddData.setVisible(false);
        menuItemActionSearch.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_stocking_warehouse:
                Intent intent = new Intent(requireActivity(), StockingWarehouse.class);
                startActivity(intent);
                break;
            case R.id.reportes:
                Intent intentReport = new Intent(requireActivity(), TableDaliyMovmentes.class);
                startActivity(intentReport);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}























