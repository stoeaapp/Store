package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ManasatPC on 01/01/18.
 */

public class AdapterConvertStore extends BaseAdapter  {


        private ArrayList<ItemsStore> itemsConvertStores = new ArrayList<>();
        private final LayoutInflater inflater;

        public AdapterConvertStore(Context context, ArrayList<ItemsStore> itemsStores) {
            //  super(context, itemStore/*flags*/);
            this.inflater = LayoutInflater.from(context);
            this.itemsConvertStores = itemsStores;
        }


        @Override
        public int getCount() {
            return itemsConvertStores.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsConvertStores.get(position);
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
            TextView TVTypeConvertStore = (TextView) listItemView.findViewById(R.id.TVType_store);
            TextView TVDateStore = (TextView) listItemView.findViewById(R.id.TVDdate_store);
            TextView TVTimeStore = (TextView) listItemView.findViewById(R.id.TVTime_store);

            TextView TVID = (TextView) listItemView.findViewById(R.id.TVID);

            //Read the Store attributes from the Cursor for the current Store
            String idStore = String.valueOf(itemsConvertStores.get(position).getId());
            String typeConvertStore = itemsConvertStores.get(position).getConvertTo();
            String  dateStore = String.valueOf(itemsConvertStores.get(position).getCreatedDate());
            String  timestore = String.valueOf(itemsConvertStores.get(position).getCreatedTime());

            //Update the TextView with the attributes for the current store
            TVID.setText(idStore);
            TVTypeConvertStore.setText(typeConvertStore);
            TVDateStore.setText(dateStore);
            TVTimeStore.setText(timestore);


            return listItemView;
        }

        public void swapData(Collection<ItemsStore> itemsStoreCollections) {
            this.itemsConvertStores.clear();
            this.itemsConvertStores.addAll(itemsStoreCollections);
            notifyDataSetChanged();
        }
    }

