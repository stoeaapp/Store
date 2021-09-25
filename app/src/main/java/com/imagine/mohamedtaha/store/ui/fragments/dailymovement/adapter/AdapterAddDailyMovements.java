package com.imagine.mohamedtaha.store.ui.fragments.dailymovement.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.databinding.CustomDailyMovementBinding;
import com.imagine.mohamedtaha.store.room.data.ShowDailyMovements;
import com.imagine.mohamedtaha.store.util.OnRecyclerItemClick;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mohamed Taha on 09/12/17.
 */

public class AdapterAddDailyMovements extends RecyclerView.Adapter<AdapterAddDailyMovements.DailyMovementsViewHolder> {
    private ArrayList<ShowDailyMovements> itemsDailyMovement;
    private final OnRecyclerItemClick<ShowDailyMovements> onClick;

    public AdapterAddDailyMovements(OnRecyclerItemClick<ShowDailyMovements> onClick, ArrayList<ShowDailyMovements> itemsDailyMovement) {
        this.onClick = onClick;
        this.itemsDailyMovement = itemsDailyMovement;
    }

    @NonNull
    @Override
    public DailyMovementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomDailyMovementBinding binding = CustomDailyMovementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DailyMovementsViewHolder(binding);
    }

    public void swapData(Collection<ShowDailyMovements> itemsStoreCollections) {
        this.itemsDailyMovement.clear();
        this.itemsDailyMovement.addAll(itemsStoreCollections);
        if (itemsDailyMovement != null) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(DailyMovementsViewHolder holder, int position) {
        ShowDailyMovements itemDailyMovement = itemsDailyMovement.get(position);
        holder.binding.setDailyMovement(itemDailyMovement);
        holder.itemView.setOnClickListener(v -> onClick.onClick(itemDailyMovement, v));
    }

    @Override
    public int getItemCount() {
        return itemsDailyMovement.size();
    }

    //Inner class for creating ViewHolders
    static class DailyMovementsViewHolder extends RecyclerView.ViewHolder {
        private final CustomDailyMovementBinding binding;

        public DailyMovementsViewHolder(CustomDailyMovementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setFilter(ArrayList<ShowDailyMovements> itemStoke) {
        itemsDailyMovement = new ArrayList<>();
        itemsDailyMovement.addAll(itemStoke);
        notifyDataSetChanged();
    }
}