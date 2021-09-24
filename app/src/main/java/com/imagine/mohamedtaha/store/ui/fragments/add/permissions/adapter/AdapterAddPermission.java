package com.imagine.mohamedtaha.store.ui.fragments.add.permissions.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.databinding.CustomStoreBinding;
import com.imagine.mohamedtaha.store.room.data.Permissions;
import com.imagine.mohamedtaha.store.util.OnRecyclerItemClick;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mohamed Taha on 03/12/17.
 */

public class AdapterAddPermission extends RecyclerView.Adapter<AdapterAddPermission.ViewHolderPermission> {
    private final List<Permissions> itemsPermissions;
    private final OnRecyclerItemClick<Permissions> itemClick;

    public AdapterAddPermission(OnRecyclerItemClick<Permissions> itemClick, ArrayList<Permissions> itemsPermissions) {
        this.itemsPermissions = itemsPermissions;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolderPermission onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomStoreBinding binding = CustomStoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderPermission(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPermission holder, int position) {
        Permissions itemPermission = itemsPermissions.get(position);
        String idStore = String.valueOf(itemPermission.getId());
        String typeStore = itemPermission.getPermissionName();
        String dateStore = String.valueOf(itemPermission.getCreatedAt());
        String timeStore = itemPermission.getTime();

        //Update the TextView with the attributes for the current store
        holder.binding.TVID.setText(idStore);
        holder.binding.TVTypeStore.setText(typeStore);
        holder.binding.TVTitleName.setText(R.string.type_permission_);
        holder.binding.TVDateStore.setText(dateStore);
        holder.binding.TVTimeStore.setText(timeStore);
        holder.itemView.setOnClickListener(v -> itemClick.onClick(itemPermission, v));
    }

    @Override
    public int getItemCount() {
        return itemsPermissions.size();
    }


    public void swapData(Collection<Permissions> itemsStoreCollections) {
        this.itemsPermissions.clear();
        this.itemsPermissions.addAll(itemsStoreCollections);
        notifyDataSetChanged();

    }

    static class ViewHolderPermission extends RecyclerView.ViewHolder {
        public CustomStoreBinding binding;

        public ViewHolderPermission(CustomStoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
