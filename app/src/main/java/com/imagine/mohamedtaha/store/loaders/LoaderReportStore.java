package com.imagine.mohamedtaha.store.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

/**
 * Created by ManasatPC on 07/04/18.
 */

public class LoaderReportStore extends AsyncTaskLoader<ArrayList<ItemsStore>> {
    TaskDbHelper dbHelper;
    ArrayList<ItemsStore> itemsStore = new ArrayList<>();
    private ArrayList<ItemsStore> chachedData;

    public LoaderReportStore(Context context, ArrayList<ItemsStore>itemsStore, TaskDbHelper dbHelper) {
        super(context);
        this.itemsStore= itemsStore;
        this.dbHelper = dbHelper;
    }

    @Override
    protected void onStartLoading() {
        if (chachedData == null) {
            forceLoad();
        } else {
            super.deliverResult(chachedData);
        }    }

    @Override
    public ArrayList<ItemsStore> loadInBackground() {
        itemsStore = dbHelper.getAllItemsStore();
        for (int i = 0; i < itemsStore.size(); i++) {
            ItemsStore itemsStores = itemsStore.get(i);
        }
        return itemsStore;    }
}
