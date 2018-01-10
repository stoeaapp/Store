package com.imagine.mohamedtaha.store.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

/**
 * Created by MANASATT on 09/12/17.
 */

public class LoaderDailyMovements extends AsyncTaskLoader<ArrayList<ItemsStore>> {
    TaskDbHelper dbHelper;
    ArrayList<ItemsStore> itemsDaily = new ArrayList<>();
    private ArrayList<ItemsStore> chachedData;

    public LoaderDailyMovements(Context context,ArrayList<ItemsStore>itemsDaily, TaskDbHelper dbHelper) {
        super(context);
        this.itemsDaily= itemsDaily;
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
        itemsDaily = dbHelper.getAllDailyMovements();
        for (int i = 0; i < itemsDaily.size(); i++) {
            ItemsStore itemsDailys = itemsDaily.get(i);
        }
        return itemsDaily;    }
}
