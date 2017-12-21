package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreDiffCallback;
import com.imagine.mohamedtaha.store.data.ItemsStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MANASATT on 09/12/17.
 */

public class AdapterAddDailyMovements extends RecyclerView.Adapter<AdapterAddDailyMovements.DailyMovementsViewHolder>{
   private   ArrayList<ItemsStore> itemsDailyMovement ;
   //  final LayoutInflater inflater;
    private showDetial mListener;
    Cursor cursor;
    public interface showDetial{
        void itemShowDetial(Cursor cursor);
    }
    public AdapterAddDailyMovements( showDetial mListener){
        this.mListener =mListener;
    }
    public AdapterAddDailyMovements(Context context, ArrayList<ItemsStore>itemsDailyMovement) {
        //  super(context, itemStore/*flags*/);
       // this.inflater= LayoutInflater.from(context);
        this.itemsDailyMovement = itemsDailyMovement;
    }

    @Override
    public DailyMovementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_daily_movement,parent,false);
        final DailyMovementsViewHolder holder = new DailyMovementsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
             //   itemsDailyMovement.moveToPosition(position);
                if (mListener != null)mListener.itemShowDetial(cursor);

            }
        });
        return holder;
    }
    public void swapData(Collection<ItemsStore> itemsStoreCollections){
        this.itemsDailyMovement.clear();
        this.itemsDailyMovement.addAll(itemsStoreCollections);
        if (itemsDailyMovement != null){

            this.notifyDataSetChanged();


        }

    }
    @Override
    public void onBindViewHolder(DailyMovementsViewHolder holder, int position) {
        ItemsStore data = itemsDailyMovement.get(position);
        holder.namePermessionView.setText(data.getNamePermission());
        holder.typeStoreView.setText(data.getTypeStore());
        holder.nameCategoryView.setText(data.getNameGategory());
       // holder.dateView.setText(data.getCreatedDate());


    }
    @Override
    public int getItemCount() {
        return itemsDailyMovement.size();
    }
    //Inner class for creating ViewHolders
    class DailyMovementsViewHolder extends RecyclerView.ViewHolder {
        //Class variables for the name DailyMovements
        TextView namePermessionView;
        TextView typeStoreView;
        TextView nameCategoryView;
        TextView dateView;

        public DailyMovementsViewHolder(final View itemView) {
            super(itemView);
            namePermessionView = (TextView) itemView.findViewById(R.id.TVNamePermissionDaily);
            typeStoreView = (TextView) itemView.findViewById(R.id.TVNameStoreDaily);
            nameCategoryView = (TextView) itemView.findViewById(R.id.TVNameCategoryDaily);
            dateView = (TextView) itemView.findViewById(R.id.TVDateDaily);

        }
    }

    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);

    }
    public static class RecycleTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private AdapterAddDailyMovements.ClickListener clickListener;

        public RecycleTouchListener(Context context, final RecyclerView recycleView, final AdapterAddDailyMovements.ClickListener clickLstener)
        {
            this.clickListener = clickLstener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recycleView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemsDailyMovement = new ArrayList<>();
        itemsDailyMovement.addAll(itemStoke);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}



























