package com.imagine.mohamedtaha.store.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

/**
 * Created by MANASATT on 08/12/17.
 */

public class LoaderPErmission extends AsyncTaskLoader<ArrayList<ItemsStore>> {
    TaskDbHelper dbHelper;
    ArrayList<ItemsStore> itemsStores = new ArrayList<>();
    private ArrayList<ItemsStore> chachedData;

    public LoaderPErmission(Context context,ArrayList<ItemsStore>itemsStores, TaskDbHelper dbHelper) {
        super(context);
        this.itemsStores= itemsStores;
        this.dbHelper = dbHelper;
    }

    @Override
    protected void onStartLoading() {
        if (chachedData == null) {
            forceLoad();
        } else {
            super.deliverResult(chachedData);
        }
    }


    @Override
    public ArrayList<ItemsStore> loadInBackground() {
        itemsStores = dbHelper.getAllItemsPermission();
        for (int ii = 0; ii < itemsStores.size(); ii++) {
            ItemsStore itemsStore = itemsStores.get(ii);
        }
        return itemsStores;
    }
}