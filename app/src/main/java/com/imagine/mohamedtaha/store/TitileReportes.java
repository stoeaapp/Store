package com.imagine.mohamedtaha.store;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;


/**
 * Created by ManasatPC on 09/01/18.
 */

public class TitileReportes extends ListFragment {
    OnReportesSelectedListener mCallback;

    public interface OnReportesSelectedListener{
        public void onReportSelected(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout,ItemsReportes.titleReportes));
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager().findFragmentById(R.id.reportes_fragment)!=null){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback =(OnReportesSelectedListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implement OnReportesSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onReportSelected(position);
        getListView().setItemChecked(position,true);
    }
}

























