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

public class AdapterAddStokeHouse extends BaseAdapter {
    private final LayoutInflater inflater;
    private  ArrayList<ItemsStore>itemStokeHouses;

    public AdapterAddStokeHouse(Context  context, ArrayList<ItemsStore> itemStokeHouses) {
        this.inflater = LayoutInflater.from(context);
        this.itemStokeHouses = itemStokeHouses;
    }

    @Override
    public int getCount() {
        return itemStokeHouses.size();
    }

    @Override
    public Object getItem(int position) {
        return itemStokeHouses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = inflater.inflate(R.layout.custom_stoke_house,parent,false);
        }
        //Find individual views that we want to modify in the list item layout
        TextView TVID = (TextView)listItemView.findViewById(R.id.TVID);
        TextView TVCodeCategory = (TextView)listItemView.findViewById(R.id.TVCodeCategory);
        TextView TVCodeStore = (TextView)listItemView.findViewById(R.id.TVCodeٍStore);
        TextView TVFirstBalance = (TextView)listItemView.findViewById(R.id.TVFirstBalance);
        TextView TVNotes = (TextView)listItemView.findViewById(R.id.TVNotes);



        //Read the Store attributes from the Cursor for the current Store
        String idStokeHouse  = String.valueOf(itemStokeHouses.get(position).getId());
        String CodeCategory  = String.valueOf(itemStokeHouses.get(position).getNameGategory());
        String  CodeStore    = String.valueOf(itemStokeHouses.get(position).getTypeStore());
        String  FirstBalance = String.valueOf(itemStokeHouses.get(position).getFirst_balanse());
        String  notes        = String.valueOf(itemStokeHouses.get(position).getNotes());


        //Update the TextView with the attributes for the current store
        TVID.setText(idStokeHouse);
        TVCodeCategory.setText(CodeCategory);
        TVCodeStore.setText(CodeStore);
        TVFirstBalance.setText(FirstBalance);
        TVNotes.setText(notes);


        return listItemView;
    }


    public void swapData(Collection<ItemsStore> itemsStokeCollections){
        this.itemStokeHouses.clear();
        this.itemStokeHouses.addAll(itemsStokeCollections);
        notifyDataSetChanged();

    }
    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemStokeHouses = new ArrayList<>();
        itemStokeHouses.addAll(itemStoke);
        notifyDataSetChanged();
    }
}


















