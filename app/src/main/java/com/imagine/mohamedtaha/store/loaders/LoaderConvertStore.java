package com.imagine.mohamedtaha.store.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

/**
 * Created by ManasatPC on 01/01/18.
 */

public class LoaderConvertStore extends AsyncTaskLoader<ArrayList<ItemsStore>> {

        TaskDbHelper dbHelper  ;
        ArrayList<ItemsStore> itemsConvertStores = new ArrayList<>() ;
        private ArrayList<ItemsStore> cachedData;
        public LoaderConvertStore(Context context, ArrayList<ItemsStore>itemsConvertStores, TaskDbHelper d) {
            super(context);
            this.itemsConvertStores = itemsConvertStores;
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
            itemsConvertStores = dbHelper.getAllItemsConvertStore();
            for (int ii = 0; ii<itemsConvertStores.size(); ii++){
                ItemsStore itemsStore = itemsConvertStores.get(ii);

            }
            return itemsConvertStores;
        }

        @Override
        public void deliverResult(ArrayList<ItemsStore> data) {
            cachedData = data;
            super.deliverResult(data);
        }
}
