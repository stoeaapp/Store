package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreDiffCallback;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.room.data.ShowDailyMovements;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MANASATT on 09/12/17.
 */

public class AdapterAddDailyMovements extends RecyclerView.Adapter<AdapterAddDailyMovements.DailyMovementsViewHolder>{
   private   ArrayList<ShowDailyMovements> itemsDailyMovement ;
   //  final LayoutInflater inflater;
    private showDetial mListener;
    Cursor cursor;
    public interface showDetial{
        void itemShowDetial(Cursor cursor);
    }
    public AdapterAddDailyMovements( showDetial mListener){
        this.mListener =mListener;
    }
    public AdapterAddDailyMovements(Context context, ArrayList<ShowDailyMovements>itemsDailyMovement) {
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
    public void swapData(Collection<ShowDailyMovements> itemsStoreCollections){
        this.itemsDailyMovement.clear();
        this.itemsDailyMovement.addAll(itemsStoreCollections);
        if (itemsDailyMovement != null){

            this.notifyDataSetChanged();


        }

    }
    @Override
    public void onBindViewHolder(DailyMovementsViewHolder holder, int position) {
        ShowDailyMovements data = itemsDailyMovement.get(position);
        holder.namePermessionView.setText(data.getPermissionName());
        holder.typeStoreView.setText(data.getTypeStore());
        holder.nameCategoryView.setText(data.getCategoryName());
        holder.convertView.setText(data.getConvertTo());
        holder.dateView.setText(data.getCreatedAt());


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
        TextView convertView;

        public DailyMovementsViewHolder(final View itemView) {
            super(itemView);
            namePermessionView = (TextView) itemView.findViewById(R.id.TVNamePermissionDaily);
            typeStoreView = (TextView) itemView.findViewById(R.id.TVNameStoreDaily);
            nameCategoryView = (TextView) itemView.findViewById(R.id.TVNameCategoryDaily);
            dateView = (TextView) itemView.findViewById(R.id.TVDateDaily);
            convertView = (TextView) itemView.findViewById(R.id.TVConvertTo);

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
    public void setFilter(ArrayList<ShowDailyMovements> itemStoke){
        itemsDailyMovement = new ArrayList<>();
        itemsDailyMovement.addAll(itemStoke);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}