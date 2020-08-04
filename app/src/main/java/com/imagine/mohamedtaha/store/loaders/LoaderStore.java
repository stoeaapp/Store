package com.imagine.mohamedtaha.store.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskContract;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

/**
 * Created by MANASATT on 08/12/17.
 */

public class LoaderStore extends AsyncTaskLoader<ArrayList<ItemsStore>> {
    TaskDbHelper dbHelper  ;
    ArrayList<ItemsStore> itemsStores = new ArrayList<>() ;
    private ArrayList<ItemsStore> cachedData;
    public LoaderStore(Context context, ArrayList<ItemsStore>itemsStores,TaskDbHelper d) {
        super(context);
        this.itemsStores = itemsStores;
        this.dbHelper = d;

    }

    @Override
    protected void onStartLoading() {
        if (cachedData == null){
            forceLoad();
        }else {
            super.deliverResult(cachedData);
        }

    }

    @Override
    public ArrayList<ItemsStore> loadInBackground() {
     /*   try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        itemsStores = dbHelper.getAllItemsStore();
        for (int ii = 0; ii<itemsStores.size(); ii++){
            ItemsStore itemsStore = itemsStores.get(ii);

        }
        return itemsStores;
    }

    @Override
    public void deliverResult(ArrayList<ItemsStore> data) {
        cachedData = data;
        super.deliverResult(data);
    }
}