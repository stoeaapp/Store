package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.ui.fragments.PermissionsFragment;
import com.imagine.mohamedtaha.store.fragments.AddStoreFragment;
import com.imagine.mohamedtaha.store.fragments.Add_Category_Fragment_ContentProvider;

/**
 * Created by MANASATT on 25/11/17.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    //Context of the app
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new Add_Category_Fragment_ContentProvider();
        }else if (position == 1){
            return new AddStoreFragment();
        }else
        {
            return new PermissionsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.add_category);
        }else if (position == 1){
            return mContext.getString(R.string.add_store);
        }else {
            return mContext.getString(R.string.add_permission);

        }
    }
}