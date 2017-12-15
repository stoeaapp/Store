package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.R;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MANASATT on 03/12/17.
 */

public class AdapterAddPermission extends BaseAdapter {
    private final ArrayList<ItemsStore> itemsPermissions;
    private final LayoutInflater inflater;

    public AdapterAddPermission(Context context,ArrayList<ItemsStore> itemsPermissions) {
        this.inflater = LayoutInflater.from(context);
        this.itemsPermissions = itemsPermissions;
    }

    @Override
    public int getCount() {
        return itemsPermissions.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsPermissions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = inflater.inflate(R.layout.custom_store_listview,parent,false);
        }
        //Find individual views that we want to modify in the list item layout
        TextView TVTypeStore = (TextView)listItemView.findViewById(R.id.TVType_store);
        TextView TVDateStore = (TextView)listItemView.findViewById(R.id.TVDdate_store);
        TextView TVTitle = (TextView)listItemView.findViewById(R.id.titleName);

        //Read the Store attributes from the Cursor for the current Store
        String idStore = String.valueOf(itemsPermissions.get(position).getId());
        String typeStore = itemsPermissions.get(position).getNamePermission();
        String  dateStore = itemsPermissions.get(position).getCreatedDate();


        //Update the TextView with the attributes for the current store
        TVTypeStore.setText(typeStore);
        TVDateStore.setText(dateStore);
        TVTitle.setText("Name Permission");


        return listItemView;    }
    public void swapData(Collection<ItemsStore> itemsStoreCollections){
        this.itemsPermissions.clear();
        this.itemsPermissions.addAll(itemsStoreCollections);
        notifyDataSetChanged();

    }
}
