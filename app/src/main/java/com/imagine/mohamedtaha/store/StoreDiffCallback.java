package com.imagine.mohamedtaha.store;


import androidx.recyclerview.widget.DiffUtil;

import com.imagine.mohamedtaha.store.data.ItemsStore;

import java.util.ArrayList;

/**
 * Created by MANASATT on 14/12/17.
 */

public class StoreDiffCallback extends DiffUtil.Callback {
    private final ArrayList<ItemsStore> mOldItemStore;
        private final ArrayList<ItemsStore> mNewItemStore;

    public StoreDiffCallback(ArrayList<ItemsStore>oldItemStore, ArrayList<ItemsStore> newItemStore) {
        this.mOldItemStore = oldItemStore;
        this.mNewItemStore = newItemStore;
    }

    @Override
    public int getOldListSize() {
        return mOldItemStore.size();
    }

    @Override
    public int getNewListSize() {
        return mNewItemStore.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItemStore.get(oldItemPosition).getId() == mNewItemStore.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final  ItemsStore oldItem = mOldItemStore.get(oldItemPosition);
        final  ItemsStore newItem = mNewItemStore.get(newItemPosition);
        return oldItem.getTypeStore().equals(newItem.getTypeStore());
    }
}
























