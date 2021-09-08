package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;
import com.imagine.mohamedtaha.store.room.data.Categories;
import com.imagine.mohamedtaha.store.room.data.Stores;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManasatPC on 13/01/18.
 */

public class AdapterAddCategory extends RecyclerView.Adapter<AdapterAddCategory.CategoryViewHolder> {
    //class variables for the cursor that holds category data and the Context
    private List<Categories> itemCategory;
    private Context mContext;
    //  private List<CategoryFileds>categoryFiledses;

    //Constrctor  for the CategoryCursorAdapter that initializes the Context
    public AdapterAddCategory(Context context,List<Categories> itemCategory,showDetial listener) {
        this.mContext = context;
     this.itemCategory = itemCategory;
        this.mListener = listener;
    }

    private showDetial mListener;

    public AdapterAddCategory(showDetial listener) {
        this.mListener = listener;
//        this.categoryFiledses = categoryFiledses;

    }

    //Called when viewHolders are called to fill a RecycleView.
    //retrun A new CategoryViewHolder that holds the view for each category
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the task_layout to a view
        //  View view = LayoutInflater.from(mContext).inflate(R.layout.custom_category,parent,false);
        //return new CategoryViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category, parent, false);
        final CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = categoryViewHolder.getAdapterPosition();
              //  mCursor.moveToPosition(position);
                if (mListener != null) mListener.itemShowDetail(position);
            }
        });

        return categoryViewHolder;


    }
//Called by the RecycleView to display data at a specified position in the Cursor.

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
/*
        CategoryFileds categoryFileds = categoryFiledses.get(position);
        holder.idCategoryView.setText(categoryFileds.getId());
        holder.nameCategoryView.setText(categoryFileds.getNameGategory());
        holder.dateCategoryView.setText(categoryFileds.getDate());*/
        //Indices for the _id , de

        //Determine the values of the wanted data
        final Long id = itemCategory.get(position).getId();
        String nameCategory =itemCategory.get(position).getCategoryName();
        String date = itemCategory.get(position).getCreatedAt();
        String  time = itemCategory.get(position).getTime();

        //Set values
        // holder.itemView.setTag(id);
        holder.idCategoryView.setText(id + "");
        holder.nameCategoryView.setText(nameCategory);
        holder.dateCategoryView.setText(date);
        holder.timeCategoryView.setText(time);


    }

    public interface showDetial {
        void itemShowDetail(Integer cursor);
    }

    //Returns the number of items todisplay
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
    class CategoryViewHolder extends RecyclerView.ViewHolder {
        //Class variables for the name category and date category
        TextView idCategoryView;
        TextView nameCategoryView;
        TextView dateCategoryView;
        TextView timeCategoryView;

        public CategoryViewHolder(final View itemView) {
            super(itemView);
            idCategoryView = (TextView) itemView.findViewById(R.id.TVIDCategory);
            nameCategoryView = (TextView) itemView.findViewById(R.id.TVNameCategory);
            dateCategoryView = (TextView) itemView.findViewById(R.id.TVDate);
            timeCategoryView = (TextView) itemView.findViewById(R.id.TVTime);

        }
    }
}
