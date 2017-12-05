package com.imagine.mohamedtaha.store;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.data.TaskContract;

import java.util.ArrayList;

/**
 * Created by MANASATT on 29/11/17.
 */

public class AdapterAddStore extends BaseAdapter {
    private final ArrayList<ItemsStore>itemsStores;
    private final LayoutInflater inflater;
    public AdapterAddStore(Context context, ArrayList<ItemsStore>itemStore) {
      //  super(context, itemStore/*flags*/);
        this.inflater= LayoutInflater.from(context);
        itemsStores = itemStore;
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
        if (listItemView == null){
            listItemView = inflater.inflate(R.layout.custom_store_listview,parent,false);
        }
        //Find individual views that we want to modify in the list item layout
        TextView TVTypeStore = (TextView)listItemView.findViewById(R.id.TVType_store);
        TextView TVDateStore = (TextView)listItemView.findViewById(R.id.TVDdate_store);
        TextView TVID = (TextView)listItemView.findViewById(R.id.TVID);

               //Read the Store attributes from the Cursor for the current Store
        String idStore = String.valueOf(itemsStores.get(position).getId());
        String typeStore = itemsStores.get(position).getTypeStore();
       String  dateStore = itemsStores.get(position).getCreatedDate();

        //Update the TextView with the attributes for the current store
        TVID.setText(idStore);
        TVTypeStore.setText(typeStore);
        TVDateStore.setText(dateStore);


        return listItemView;
    }
}

















