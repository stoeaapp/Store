package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ManasatPC on 06/04/18.
 */

public class AdapterReportCategory extends RecyclerView.Adapter<AdapterReportCategory.ReportCategoryViewHolder> {
    private ArrayList<ItemsStore> itemsCategoryReport ;
    Cursor mCursor;
    private showDetial mListener;
    public interface showDetial{
        void itemShowDetial(Cursor cursor);
    }
    public AdapterReportCategory( showDetial mListener){
        this.mListener =mListener;
    }

    public AdapterReportCategory(Context context, ArrayList<ItemsStore>AdapterReportCategory) {

        this.itemsCategoryReport = AdapterReportCategory;
    }
    @Override
    public ReportCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_report_category,parent,false);
        final ReportCategoryViewHolder holder = new ReportCategoryViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //   itemsDailyMovement.moveToPosition(position);
                if (mListener != null)mListener.itemShowDetial(mCursor);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ReportCategoryViewHolder holder, int position) {
        ItemsStore data = itemsCategoryReport.get(position);
        holder.idView.setText(String.valueOf(data.getId()));
        holder.nameCategoryView.setText(data.getNameGategory());
        holder.DateCreatedView.setText(data.getCreatedDate());
        holder.naturalCategoryView.setText(data.getNauralCategory());
        holder.nameUserView.setText(data.getUserName());
        holder.notesView.setText(data.getNotes());
    }

    @Override
    public int getItemCount() {
        return itemsCategoryReport.size();
    }


    //Inner class for creating ViewHolders

    class ReportCategoryViewHolder extends RecyclerView.ViewHolder{
        //Class variables for the id Store and Name Store
        TextView idView;
        TextView nameCategoryView;
        TextView DateCreatedView;
        TextView naturalCategoryView;
        TextView nameUserView;
        TextView notesView;

        public ReportCategoryViewHolder(final  View itemView){
            super(itemView);
            idView = (TextView)itemView.findViewById(R.id.TVIDCategory);
            nameCategoryView = (TextView)itemView.findViewById(R.id.TVNameCategory);
            DateCreatedView = (TextView)itemView.findViewById(R.id.TVDateAdd);
            naturalCategoryView = (TextView)itemView.findViewById(R.id.TVNaturalCategory);
            nameUserView = (TextView)itemView.findViewById(R.id.TVNameUser);
            notesView = (TextView)itemView.findViewById(R.id.TVNotes);

        }


    }
    public void swapData(Collection<ItemsStore> itemsStoreCollections){
        this.itemsCategoryReport.clear();
        this.itemsCategoryReport.addAll(itemsStoreCollections);
        if (itemsCategoryReport != null){

            this.notifyDataSetChanged();
        }
    }
    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemsCategoryReport = new ArrayList<>();
        itemsCategoryReport.addAll(itemStoke);
        notifyDataSetChanged();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
