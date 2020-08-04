package com.imagine.mohamedtaha.store.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

/**
 * Created by ManasatPC on 06/04/18.
 */

public class LoaderReportCategory extends AsyncTaskLoader<ArrayList<ItemsStore>> {
    TaskDbHelper dbHelper;
    ArrayList<ItemsStore> itemsCategory = new ArrayList<>();
    private ArrayList<ItemsStore> chachedData;

    public LoaderReportCategory(Context context, ArrayList<ItemsStore>itemsCategory, TaskDbHelper dbHelper) {
        super(context);
        this.itemsCategory= itemsCategory;
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
        itemsCategory = dbHelper.getAllItemsCategories();
        for (int i = 0; i < itemsCategory.size(); i++) {
            ItemsStore itemsCategorys = itemsCategory.get(i);
        }
        return itemsCategory;    }
}
