package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.StoreDiffCallback;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.room.data.Stores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by MANASATT on 29/11/17.
 */

public class AdapterAddStore extends BaseAdapter {
    private ArrayList<Stores> itemsStores;
    private final LayoutInflater inflater;

    public AdapterAddStore(Context context, ArrayList<Stores> itemsStores) {
        //  super(context, itemStore/*flags*/);
        this.inflater = LayoutInflater.from(context);
        this.itemsStores = new ArrayList<>();
        this.itemsStores = itemsStores;
    }


    @Override
    public int getCount() {
        return itemsStores.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsStores.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.custom_store_listview, parent, false);
        }
        //Find individual views that we want to modify in the list item layout
        TextView TVTypeStore = (TextView) listItemView.findViewById(R.id.TVType_store);
        TextView TVDateStore = (TextView) listItemView.findViewById(R.id.TVDdate_store);
        TextView TVTimeStore = (TextView) listItemView.findViewById(R.id.TVTime_store);

        TextView TVID = (TextView) listItemView.findViewById(R.id.TVID);

        //Read the Store attributes from the Cursor for the current Store
        String idStore = String.valueOf(itemsStores.get(position).getId());
        String typeStore = itemsStores.get(position).getTypeStore();
        String  dateStore = String.valueOf(itemsStores.get(position).getCreatedAt());
        String  timestore = String.valueOf(itemsStores.get(position).getTime());

        //Update the TextView with the attributes for the current store
        TVID.setText(idStore);
        TVTypeStore.setText(typeStore);
        TVDateStore.setText(dateStore);
        TVTimeStore.setText(timestore);


        return listItemView;
    }

    public void swapData(List<Stores> itemsStoreCollections) {
        this.itemsStores.clear();
        this.itemsStores.addAll(itemsStoreCollections);
        notifyDataSetChanged();
    }
    }