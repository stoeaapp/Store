package com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.databinding.CustomStokeHouseBinding;
import com.imagine.mohamedtaha.store.room.data.ShowStockWare;
import com.imagine.mohamedtaha.store.util.OnRecyclerItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed taha on 03/12/17.
 */

public class AdapterAddStokeHouse extends RecyclerView.Adapter<AdapterAddStokeHouse.StokeWarehouseViewHolder> {
    private final ArrayList<ShowStockWare> itemStokeHouses;
    private final OnRecyclerItemClick<ShowStockWare> onClick;


    public AdapterAddStokeHouse(OnRecyclerItemClick<ShowStockWare> onClick, ArrayList<ShowStockWare> itemStokeHouses) {
        this.onClick = onClick;
        this.itemStokeHouses = itemStokeHouses;
    }


    @NonNull
    @Override
    public StokeWarehouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomStokeHouseBinding binding = CustomStokeHouseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StokeWarehouseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StokeWarehouseViewHolder holder, int position) {
        ShowStockWare data = itemStokeHouses.get(position);
        holder.binding.setStackWarehouse(data);
        holder.itemView.setOnClickListener(v -> onClick.onClick(data, v));
    }

    @Override
    public int getItemCount() {
        return itemStokeHouses.size();
    }

    static class StokeWarehouseViewHolder extends RecyclerView.ViewHolder {
        private final CustomStokeHouseBinding binding;

        public StokeWarehouseViewHolder(final CustomStokeHouseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void swapData(List<ShowStockWare> itemsStokeCollections) {
        this.itemStokeHouses.clear();
        this.itemStokeHouses.addAll(itemsStokeCollections);
        notifyDataSetChanged();
    }
}