package com.imagine.mohamedtaha.store.ui.fragments.add.items.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.databinding.CustomItemBinding;
import com.imagine.mohamedtaha.store.room.data.Categories;
import com.imagine.mohamedtaha.store.util.OnRecyclerItemClick;

import java.util.List;

/**
 * Created by Mohamed Taha on 13/01/18.
 */

public class AdapterAddItems extends RecyclerView.Adapter<AdapterAddItems.ItemViewHolder> {
    private final List<Categories> itemCategory;
    private final OnRecyclerItemClick<Categories> onClick;

    public AdapterAddItems(List<Categories> itemCategory, OnRecyclerItemClick<Categories> onClick) {
        this.itemCategory = itemCategory;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomItemBinding binding = CustomItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Categories items = itemCategory.get(position);
        //Determine the values of the wanted data
        final Long id = items.getId();
        String nameCategory = items.getCategoryName();
        String date = items.getCreatedAt();
        String time = items.getTime();
        holder.binding.TVIDCategory.setText(String.valueOf(id));
        holder.binding.TVNameCategory.setText(nameCategory);
        holder.binding.TVDate.setText(date);
        holder.binding.TVTime.setText(time);
        holder.itemView.setOnClickListener(v -> onClick.onClick(items, v));
    }

    @Override
    public int getItemCount() {
        if (itemCategory == null) {
            return 0;
        }
        return itemCategory.size();
    }

    public void swapData(List<Categories> itemsStoreCollections) {
        this.itemCategory.clear();
        this.itemCategory.addAll(itemsStoreCollections);
        notifyDataSetChanged();
    }

    //Inner class for creating ViewHolders
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final CustomItemBinding binding;

        public ItemViewHolder(CustomItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
