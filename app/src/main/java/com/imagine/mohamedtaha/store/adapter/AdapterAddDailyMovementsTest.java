package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskContract;

/**
 * Created by MANASATT on 18/12/17.
 */

public class AdapterAddDailyMovementsTest extends RecyclerView.Adapter<AdapterAddDailyMovementsTest.CategoryViewHolder> {
    //class variables for the cursor that holds category data and the Context
    private Cursor mCursor;
    private Context mContext;
    //  private List<CategoryFileds>categoryFiledses;

    //Constrctor  for the CategoryCursorAdapter that initializes the Context
    public AdapterAddDailyMovementsTest(Context context) {
        this.mContext = context;
//        this.categoryFiledses = categoryFiledses;

    }

    private showDetial mListener;

    public AdapterAddDailyMovementsTest(showDetial listener) {
        this.mListener = listener;
//        this.categoryFiledses = categoryFiledses;

    }

    //Called when viewHolders are called to fill a RecycleView.
    //retrun A new CategoryViewHolder that holds the view for each category
    @Override
    public AdapterAddDailyMovementsTest.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the task_layout to a view
        //  View view = LayoutInflater.from(mContext).inflate(R.layout.custom_category,parent,false);
        //return new CategoryViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_daily_movement, parent, false);
        final CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = categoryViewHolder.getAdapterPosition();
                mCursor.moveToPosition(position);
                if (mListener != null) mListener.itemShowDetail(mCursor);
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

        int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        int namePermissionIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.KEY_NAME_PERMISSION);
        int typeStoreIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.KEY_TYPE_STORE);
        int nameCategoryIndex =mCursor.getColumnIndex(TaskContract.TaskEntry.KEY_NAME_CATEGORY);

        mCursor.moveToPosition(position); //get to the right location in the cursor

        //Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String namePermission = mCursor.getString(namePermissionIndex);
        String typeStore = mCursor.getString(typeStoreIndex);
        String  nameCategory= mCursor.getString(nameCategoryIndex);

        //Set values
        // holder.itemView.setTag(id);
      //  holder.idCategoryView.setText(id + "");
        holder.namePermessionView.setText(namePermission);
        holder.typeStoreView.setText(typeStore);
        holder.nameCategoryView.setText(nameCategory);


    }

    public interface showDetial {
        void itemShowDetail(Cursor cursor);
    }

    //Returns the number of items todisplay
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        //check if this cursor is the same as the previous cursor(mCursor)
        if (mCursor == c) {
            return null; //bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; //new cursor value assigned

        //check if this is a valid cursor ,then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    //Inner class for creating ViewHolders
    class CategoryViewHolder extends RecyclerView.ViewHolder {
        //Class variables for the name category and date category
        TextView namePermessionView;
        TextView typeStoreView;
        TextView nameCategoryView;
        TextView dateView;

        public CategoryViewHolder(final View itemView) {
            super(itemView);
            namePermessionView = (TextView) itemView.findViewById(R.id.TVNamePermissionDaily);
            typeStoreView = (TextView) itemView.findViewById(R.id.TVNameStoreDaily);
            nameCategoryView = (TextView) itemView.findViewById(R.id.TVNameCategoryDaily);
            dateView = (TextView) itemView.findViewById(R.id.TVDateDaily);

        }
    }
}
