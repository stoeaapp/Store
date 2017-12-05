package com.imagine.mohamedtaha.store.fragments;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.AdapterAddCategory;
import com.imagine.mohamedtaha.store.AdapterAddStore;
import com.imagine.mohamedtaha.store.CategoryFileds;
import com.imagine.mohamedtaha.store.EditCategory;
import com.imagine.mohamedtaha.store.EditStore;
import com.imagine.mohamedtaha.store.ItemsPermission;
import com.imagine.mohamedtaha.store.ItemsStore;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddStoreFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    public AddStoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename parameter arguments, choose names that match
    FloatingActionButton fabAddCategory;
    ListView mListView;
    AdapterAddStore adapterAddStore;
    TaskDbHelper dbHelper;
    ArrayList<ItemsStore> itemsStores ;
    //Identifier for the category dataloader;
    private static final int STORE_LOADER = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    View view;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_store, container, false);

        dbHelper = new TaskDbHelper(getActivity());
        //Set the RecycleView to its corresponding view
        mListView = (ListView) view.findViewById(R.id.listViewAddStore);


          itemsStores = dbHelper.getAllItemsStore();
        for (int ii = 0; ii<itemsStores.size(); ii++){
            ItemsStore itemsStore = itemsStores.get(ii);
        }
     //   adapterAddStore = new AdapterAddStore(getContext(),null);
      //  mListView.setAdapter(adapterAddStore);
        adapterAddStore = new AdapterAddStore(getContext(),itemsStores);
        mListView.setAdapter(adapterAddStore);

        //Find and set empty view on the ListView, so that it only shows when the list has 0 otems.
     //   View emptyView = view.findViewById(R.id.);

        //Set the layout for the RecycleView to be a linear layout,which measures and
        //positions items within a RecycleView into a linear list

     //   mListView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //Initalize the adapter and attach it to the RecycleView
        //  adapterAddCategory = new AdapterAddCategory(getActivity());


        //Setup the item click listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemsStore itemsStore = itemsStores.get(position);
              Intent intent = new Intent(getActivity(),EditStore.class);
                intent.putExtra("name",itemsStore.getTypeStore());
                intent.putExtra("notes",itemsStore.getNotes());
                startActivity(intent);
            }
        });
        fabAddCategory = (FloatingActionButton)view.findViewById(R.id.fab_add_store);
        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent = new Intent(getActivity(), EditCategory.class);
            //    startActivity(intent);


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.activity_dialog_add_category,null);
                final EditText ETTypeStore = (EditText)view1.findViewById(R.id.ETNameCategory);
                final EditText ETNotes = (EditText)view1.findViewById(R.id.EtNotes);

                Button BTAddOrUpdate = (Button)view1.findViewById(R.id.BTAdd);
                Button BTDelete = (Button)view1.findViewById(R.id.BTDelete);
                 BTAddOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String typeStore =ETTypeStore.getText().toString();
                        String notes = ETNotes.getText().toString();

                        if (typeStore.length() == 0){
                            Toast.makeText(getContext(), "not should leave field name emputy", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        ItemsStore itemsStore = new ItemsStore();
                        itemsStore.setTypeStore(typeStore);
                        itemsStore.setNotes(notes);
                        dbHelper.addStore(itemsStore);
                        Toast.makeText(getActivity(),"Saved Succeflly",Toast.LENGTH_LONG).show();


                        /*
                        //new Content values object
                        ContentValues values = new ContentValues();
                        values.put(TaskEntry.KEY_TYPE_STORE,typeStore);
                        values.put(TaskEntry.KEY_NOTES,notes);
                        values.put(TaskEntry.KEY_DATE,getDateTime());

                        //Insert new category data via a ContentResolver
                        Uri uri = getActivity().getContentResolver().insert(TaskEntry.CONTENT_URISTORE, values);
                        if (uri != null){
                            Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_SHORT).show();
                        }
                        */
                        getActivity().finish();


                    }
                });
               BTDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();


                    }
                });
                mBuilder.setView(view1);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Kick off the loader
//        getActivity().getSupportLoaderManager().initLoader(STORE_LOADER, null, this);

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


        // Define a projection that specifies the columns from the table we care about.
       /* String[]projection={
                TaskEntry._ID, TaskEntry.KEY_TYPE_STORE, TaskEntry.KEY_DATE };
        return new CursorLoader(getContext(),
                TaskEntry.CONTENT_URISTORE,
                projection,
                null,
                null,
                null);*/
      // Default sort order
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      //  adapterAddStore.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
      //  adapterAddStore.swapCursor(null);

    }
    //get datetime
    public static String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }

}
