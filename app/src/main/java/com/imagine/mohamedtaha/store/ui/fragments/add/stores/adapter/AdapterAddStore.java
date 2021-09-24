package com.imagine.mohamedtaha.store.ui.fragments.add.stores.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.databinding.CustomStoreBinding;
import com.imagine.mohamedtaha.store.room.data.Stores;
import com.imagine.mohamedtaha.store.util.OnRecyclerItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Taha on 29/11/17.
 */

public class AdapterAddStore extends RecyclerView.Adapter<AdapterAddStore.StoreViewHolder> {
    private final ArrayList<Stores> itemsStores;
    private final OnRecyclerItemClick<Stores> onClick;

    public AdapterAddStore(OnRecyclerItemClick<Stores> onClick, ArrayList<Stores> itemsStores) {
        this.onClick = onClick;
        this.itemsStores = itemsStores;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomStoreBinding binding = CustomStoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StoreViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Stores itemStore = itemsStores.get(position);

        //Read the Store attributes from the Cursor for the current Store
        String idStore = String.valueOf(itemStore.getId());
        String typeStore = itemStore.getTypeStore();
        String dateStore = String.valueOf(itemStore.getCreatedAt());
        String timeStore = String.valueOf(itemStore.getTime());

        //Update the TextView with the attributes for the current store
        holder.binding.TVID.setText(idStore);
        holder.binding.TVTypeStore.setText(typeStore);
        holder.binding.TVDateStore.setText(dateStore);
        holder.binding.TVTimeStore.setText(timeStore);
        holder.itemView.setOnClickListener(v -> onClick.onClick(itemStore, v));
    }

    @Override
    public int getItemCount() {
        return itemsStores.size();
    }

    public void swapData(List<Stores> itemsStoreCollections) {
        this.itemsStores.clear();
        this.itemsStores.addAll(itemsStoreCollections);
        notifyDataSetChanged();
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        private final CustomStoreBinding binding;

        public StoreViewHolder(CustomStoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}