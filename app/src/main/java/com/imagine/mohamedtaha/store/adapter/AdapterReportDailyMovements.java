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
 * Created by ManasatPC on 10/01/18.
 */

public class AdapterReportDailyMovements extends RecyclerView.Adapter<AdapterReportDailyMovements.ReportDailyViewHolder> {
    private ArrayList<ItemsStore> itemsDailyReport ;
    Cursor mCursor;
    private showDetial mListener;
    public interface showDetial{
        void itemShowDetial(Cursor cursor);
    }
    public AdapterReportDailyMovements( showDetial mListener){
        this.mListener =mListener;
    }

    public AdapterReportDailyMovements(Context context, ArrayList<ItemsStore>AdapterReportDaily) {
        //  super(context, itemStore/*flags*/);
        // this.inflater= LayoutInflater.from(context);
        this.itemsDailyReport = AdapterReportDaily;
    }
    @Override
    public ReportDailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_report_daliy_movements,parent,false);
        final ReportDailyViewHolder holder = new ReportDailyViewHolder(view);
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
    public void onBindViewHolder(ReportDailyViewHolder holder, int position) {
        ItemsStore data = itemsDailyReport.get(position);
        holder.idView.setText(String.valueOf(data.getId()));
        holder.namePermissionView.setText(data.getNamePermission());
        holder.typeStoreView.setText(data.getTypeStore());
        holder.nameCategoryView.setText(data.getNameGategory());
        holder.incomingView.setText(String.valueOf(data.getIncoming()));
        holder.issuedView.setText(String.valueOf(data.getIssued()));
        holder.convertToView.setText(data.getConvertTo());
    }

    @Override
    public int getItemCount() {
        return itemsDailyReport.size();
    }


    //Inner class for creating ViewHolders

    class ReportDailyViewHolder extends RecyclerView.ViewHolder{
        //Class variables for the id Store and Name Store
        TextView idView;
        TextView namePermissionView;
        TextView typeStoreView;
        TextView nameCategoryView;
        TextView incomingView;
        TextView issuedView;
        TextView convertToView;

        public ReportDailyViewHolder(final  View itemView){
            super(itemView);
            idView = (TextView)itemView.findViewById(R.id.TVIDReportDailyMovements);
            namePermissionView = (TextView)itemView.findViewById(R.id.TVNamePermissionReportDailyMovements);
            typeStoreView = (TextView)itemView.findViewById(R.id.TVNameStoreReportDailyMovements);
            nameCategoryView = (TextView)itemView.findViewById(R.id.TVNameCategoryReportDailyMovements);
            incomingView = (TextView)itemView.findViewById(R.id.TVIncomingReportDailyMovements);
            issuedView = (TextView)itemView.findViewById(R.id.TVIssuedeportDailyMovements);
            convertToView = (TextView)itemView.findViewById(R.id.TVConvertToReportDailyMovements);

        }


    }
    public void swapData(Collection<ItemsStore> itemsStoreCollections){
        this.itemsDailyReport.clear();
        this.itemsDailyReport.addAll(itemsStoreCollections);
        if (itemsDailyReport != null){

            this.notifyDataSetChanged();
        }
    }
    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemsDailyReport = new ArrayList<>();
        itemsDailyReport.addAll(itemStoke);
        notifyDataSetChanged();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
