package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ManasatPC on 08/01/18.
 */


public class AdapterReportStoke extends RecyclerView.Adapter<AdapterReportStoke.ReportStoreViewHolder> {
        private ArrayList<ItemsStore>itemsStoresReport ;
        Cursor mCursor;
    private showDetial mListener;
    public interface showDetial{
        void itemShowDetial(Cursor cursor);
    }
    public AdapterReportStoke(showDetial mListener){
        this.mListener =mListener;
    }

    public AdapterReportStoke(Context context, ArrayList<ItemsStore>AdapterReportStore) {
        //  super(context, itemStore/*flags*/);
        // this.inflater= LayoutInflater.from(context);
        this.itemsStoresReport = AdapterReportStore;
    }
    @Override
    public ReportStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_report_stoke,parent,false);
        final  ReportStoreViewHolder holder = new ReportStoreViewHolder(view);
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
    public void onBindViewHolder(ReportStoreViewHolder holder, int position) {
        ItemsStore data = itemsStoresReport.get(position);
        holder.idView.setText(String.valueOf(data.getId()));
        holder.typeStoreView.setText(data.getTypeStore());
        holder.nameCategoryView.setText(data.getNameGategory());
        holder.firstBalanceView.setText(String.valueOf(data.getFirst_balanse()));
    }

    @Override
    public int getItemCount() {
        return itemsStoresReport.size();
    }


    //Inner class for creating ViewHolders

    class ReportStoreViewHolder extends RecyclerView.ViewHolder{
        //Class variables for the id Store and Name Store
        TextView idView;
        TextView typeStoreView;
        TextView nameCategoryView;
        TextView firstBalanceView;
        public ReportStoreViewHolder(final  View itemView){
            super(itemView);
            idView = (TextView)itemView.findViewById(R.id.TVIDReportStoke);
            typeStoreView = (TextView)itemView.findViewById(R.id.TVNameStoreReportStoke);
            nameCategoryView = (TextView)itemView.findViewById(R.id.TVNameCategoryReportStoke);
            firstBalanceView = (TextView)itemView.findViewById(R.id.TVFirstBalanceReportStoke);
    }


    }
    public void swapData(Collection<ItemsStore> itemsStoreCollections){
        this.itemsStoresReport.clear();
        this.itemsStoresReport.addAll(itemsStoreCollections);
        if (itemsStoresReport != null){

            this.notifyDataSetChanged();
        }
    }
    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemsStoresReport = new ArrayList<>();
        itemsStoresReport.addAll(itemStoke);
        notifyDataSetChanged();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);

    }
    public static class RecycleTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private AdapterReportStoke.ClickListener clickListener;

        public RecycleTouchListener(Context context, final RecyclerView recycleView, final AdapterReportStoke.ClickListener clickLstener)
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


    }



