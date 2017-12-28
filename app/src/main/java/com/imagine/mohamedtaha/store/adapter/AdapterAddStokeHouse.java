package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.fragments.EditStockingWarehouseFragment;

import java.util.ArrayList;
import java.util.Collection;

import static com.imagine.mohamedtaha.store.StockingWarehouse.CODE_CATEGORY;
import static com.imagine.mohamedtaha.store.StockingWarehouse.CODE_STORE;
import static com.imagine.mohamedtaha.store.StockingWarehouse.DIALOG_STOKE_WEAREHOUSE;
import static com.imagine.mohamedtaha.store.StockingWarehouse.FIRST_BALANCE;
import static com.imagine.mohamedtaha.store.StockingWarehouse.ID_STOKE;
import static com.imagine.mohamedtaha.store.StockingWarehouse.NOTESTOKE;

/**
 * Created by MANASATT on 03/12/17.
 */

public class AdapterAddStokeHouse extends RecyclerView.Adapter<AdapterAddStokeHouse.StokeWearhouseViewHolder> {
    private final LayoutInflater inflater;
    private  ArrayList<ItemsStore>itemStokeHouses;
    private showDetial mListener;
    Cursor cursor;
    public interface showDetial{
        void itemShowDetial(Cursor cursor);
    }

    public AdapterAddStokeHouse(Context  context, ArrayList<ItemsStore> itemStokeHouses) {
        this.inflater = LayoutInflater.from(context);
        this.itemStokeHouses = itemStokeHouses;
    }


    @Override
    public AdapterAddStokeHouse.StokeWearhouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stoke_house,parent,false);
        final StokeWearhouseViewHolder holder = new StokeWearhouseViewHolder(view);
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

    @Override
    public void onBindViewHolder(final StokeWearhouseViewHolder holder, int position) {
        ItemsStore data = itemStokeHouses.get(position);
        holder.idView.setText(String.valueOf(data.getId()));
        holder.codeCategoryView.setText(data.getNameGategory());
        holder.codeTypeStoreView.setText(data.getTypeStore());
        holder.firstBalanceView.setText(String.valueOf(data.getFirst_balanse()));
        holder.noteView.setText(data.getNotes());

       // holder.dateView.setText(data.getCreatedDate());
        holder.image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.image_edit);
            }
        });

    }

      @Override
    public int getItemCount() {
        return itemStokeHouses.size();
    }
    //Inner class for creating ViewHolders
    class StokeWearhouseViewHolder extends RecyclerView.ViewHolder {
        //Class variables for the name StokeWearhehouse
        TextView idView;
        TextView codeCategoryView;
        TextView codeTypeStoreView;
        TextView firstBalanceView;
        TextView noteView;
        ImageView image_edit;

        public StokeWearhouseViewHolder(final View itemView) {
            super(itemView);
             idView = (TextView)itemView.findViewById(R.id.TVID);
             codeCategoryView = (TextView)itemView.findViewById(R.id.TVCodeCategory);
             codeTypeStoreView = (TextView)itemView.findViewById(R.id.TVCodeٍStore);
             firstBalanceView = (TextView)itemView.findViewById(R.id.TVFirstBalance);
             noteView = (TextView)itemView.findViewById(R.id.TVNotes);
            image_edit = (ImageView)itemView.findViewById(R.id.Image_edit);

        }
    }

    public void swapData(Collection<ItemsStore> itemsStokeCollections){
        this.itemStokeHouses.clear();
        this.itemStokeHouses.addAll(itemsStokeCollections);
        notifyDataSetChanged();

    }


    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemStokeHouses = new ArrayList<>();
        itemStokeHouses.addAll(itemStoke);
        notifyDataSetChanged();
    }
    private void showPopupMenu(View view){
        //inflate Menu
        PopupMenu popupMenu = new PopupMenu(inflater.getContext(),view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_store_category_permission,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener() );
        popupMenu.show();


    }
    //Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{
        public MyMenuItemClickListener(){

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_settings:
                    Toast.makeText(inflater.getContext(), "Edit ", Toast.LENGTH_SHORT).show();


                           }
            return true;
        }

            }

    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);

    }
    public static class RecycleTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecycleTouchListener(Context context, final RecyclerView recycleView, final ClickListener clickLstener)
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
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}


















