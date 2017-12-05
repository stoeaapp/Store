package com.imagine.mohamedtaha.store.fragments;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;

import android.support.v7.widget.LinearLayoutManager;


import com.imagine.mohamedtaha.store.AdapterAddCategory;
import com.imagine.mohamedtaha.store.CategoryFileds;
import com.imagine.mohamedtaha.store.EditCategory;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskContract.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Add_Category_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    FloatingActionButton fabAddCategory;
    RecyclerView mRecycleView;
    AdapterAddCategory adapterAddCategory;

    List<CategoryFileds>categoryFildes;

   //Identifier for the category dataloader;
    private static final int CATEGORY_LOADER = 0;

    public Add_Category_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  categoryFildes = new ArrayList<>();


    }
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add__category_, container, false);
        //Set the RecycleView to its corresponding view
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleViewAddCategory);
        //Set the layout for the RecycleView to be a linear layout,which measures and
        //positions items within a RecycleView into a linear list
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //Initalize the adapter and attach it to the RecycleView
     //  adapterAddCategory = new AdapterAddCategory(getActivity());

        adapterAddCategory = new AdapterAddCategory(new AdapterAddCategory.showDetial() {
            @Override
            public void itemShowDetail(Cursor cursor) {
              Intent intent = new Intent(getActivity(), EditCategory.class);
                long id = cursor.getLong(cursor.getColumnIndex(TaskEntry._ID));

                Uri currentCategoryUri = ContentUris.withAppendedId(TaskEntry.CONTENT_URI,id);
                intent.setData(currentCategoryUri);
                startActivity(intent);
            }
        });
        mRecycleView.setAdapter(adapterAddCategory);
/*
        mRecycleView.addOnItemTouchListener(new AdapterAddCategory.RecycleTouchListener(getActivity(),
                mRecycleView, new AdapterAddCategory.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), EditCategory.class);
              //  intent.putExtra("name",categoryFildes.get(position).getNameGategory());
              //  intent.putExtra("nautral",categoryFildes.get(position).getNauralCategory());
                //intent.putExtra("notes",categoryFildes.get(position).getNotes());
               Uri currentCategoryUri = ContentUris.withAppendedId(TaskEntry.CONTENT_URI,position);
                intent.setData(currentCategoryUri);
                startActivity(intent);

               Toast.makeText(getActivity()," You select :" +position,Toast.LENGTH_LONG).show();

            }


            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity()," You select :" + position ,Toast.LENGTH_LONG).show();

            }
        }));*/

        fabAddCategory = (FloatingActionButton)view.findViewById(R.id.fab_add_category);
        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditCategory.class);
                startActivity(intent);

/*
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.activity_dialog_add_category,null);
                final EditText ETNameCategory = (EditText)view1.findViewById(R.id.ETNameCategory);
                final EditText ETNaturalCategory =(EditText)view1.findViewById(R.id.ETNaturalGategory);
                final EditText ETNotes =(EditText)view1.findViewById(R.id.EtNotes);

                Button BTAddOrUpdate = (Button)view1.findViewById(R.id.BTAdd);
                Button BTDelete = (Button)view1.findViewById(R.id.BTDelete);
                 BTAddOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nameCategory =ETNameCategory.getText().toString();
                        String naturalCategory = ETNaturalCategory.getText().toString();
                        String notes = ETNotes.getText().toString();

                        if (nameCategory.length() == 0){
                            Toast.makeText(getContext(), "not should leave field name emputy", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        //new Content values object
                        ContentValues values = new ContentValues();
                        values.put(TaskEntry.KEY_NAME_CATEGORY,nameCategory);
                        values.put(TaskEntry.KEY_NATURAL_CATEGORY,naturalCategory );
                        values.put(TaskEntry.KEY_NOTES,notes);
                        values.put(TaskEntry.KEY_DATE,getDateTime());

                        //Insert new category data via a ContentResolver
                        Uri uri = getActivity().getContentResolver().insert(TaskEntry.CONTENT_URI, values);
                        if (uri != null){
                            Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_SHORT).show();
                        }
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
*/
            }
        });
           return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Kick off the loader
        getActivity().getSupportLoaderManager().initLoader(CATEGORY_LOADER, null, this);

    }

     @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[]projection={
                TaskEntry._ID,TaskEntry.KEY_NAME_CATEGORY,TaskEntry.KEY_DATE };
       return new CursorLoader(getContext(),
                TaskEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);                // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapterAddCategory.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapterAddCategory.swapCursor(null);

    }
    //get datetime
    public static String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }
}