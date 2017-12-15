package com.imagine.mohamedtaha.store.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

/**
 * Created by MANASATT on 09/12/17.
 */

public class LoaderStokeWearehouse extends AsyncTaskLoader<ArrayList<ItemsStore>> {
    ArrayList<ItemsStore> itemsStoke = new ArrayList<>();
    TaskDbHelper dbHelperStoke;
    private ArrayList<ItemsStore> cachedData;

    public LoaderStokeWearehouse(Context context, ArrayList<ItemsStore>itemsStoke,TaskDbHelper dbHelperStoke) {
        super(context);
        this.dbHelperStoke = dbHelperStoke;
        this.itemsStoke = itemsStoke;
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

        itemsStoke = dbHelperStoke.getAllStokeHouseByCategoryAndStory();
        for (int ii = 0; ii<itemsStoke.size(); ii++){
            ItemsStore itemsStokes = itemsStoke.get(ii);
        }
        return itemsStoke;
    }

    @Override
    public void deliverResult(ArrayList<ItemsStore> data) {
        cachedData = data;
        super.deliverResult(data);
    }
}
