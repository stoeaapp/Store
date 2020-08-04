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
 * Created by ManasatPC on 07/04/18.
 */

public class AdapterReportStore extends RecyclerView.Adapter<AdapterReportStore.ReportStoreViewHolder> {
private ArrayList<ItemsStore> itemsStoreReport ;
        Cursor mCursor;
private showDetial mListener;
public interface showDetial{
    void itemShowDetial(Cursor cursor);
}
    public AdapterReportStore( showDetial mListener){
        this.mListener =mListener;
    }

    public AdapterReportStore(Context context, ArrayList<ItemsStore>adapterReportStore) {

        this.itemsStoreReport = adapterReportStore;
    }
    @Override
    public ReportStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_report_store,parent,false);
        final ReportStoreViewHolder holder = new ReportStoreViewHolder(view);
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
        ItemsStore data = itemsStoreReport.get(position);
        holder.idView.setText(String.valueOf(data.getId()));
        holder.TypeStoreView.setText(data.getTypeStore());
        holder.DateCreatedView.setText(data.getCreatedDate()+"\n"+data.getCreatedTime());
        holder.nameUserView.setText(data.getUserName());
        holder.notesView.setText(data.getNotes());
    }

    @Override
    public int getItemCount() {
        return itemsStoreReport.size();
    }


//Inner class for creating ViewHolders

class ReportStoreViewHolder extends RecyclerView.ViewHolder{
    //Class variables for the id Store and Name Store
    TextView idView;
    TextView TypeStoreView;
    TextView DateCreatedView;
    TextView nameUserView;
    TextView notesView;

    public ReportStoreViewHolder(final View itemView){
        super(itemView);
        idView = (TextView)itemView.findViewById(R.id.TVIDCategory);
        TypeStoreView = (TextView)itemView.findViewById(R.id.TVNameCategory);
        DateCreatedView = (TextView)itemView.findViewById(R.id.TVDateAdd);
        nameUserView = (TextView)itemView.findViewById(R.id.TVNameUser);
        notesView = (TextView)itemView.findViewById(R.id.TVNotes);

    }


}
    public void swapData(Collection<ItemsStore> itemsStoreCollections){
        this.itemsStoreReport.clear();
        this.itemsStoreReport.addAll(itemsStoreCollections);
        if (itemsStoreReport != null){

            this.notifyDataSetChanged();
        }
    }
    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemsStoreReport = new ArrayList<>();
        itemsStoreReport.addAll(itemStoke);
        notifyDataSetChanged();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
