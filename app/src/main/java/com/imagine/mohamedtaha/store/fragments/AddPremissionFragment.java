package com.imagine.mohamedtaha.store.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.AdapterAddPermission;
import com.imagine.mohamedtaha.store.AdapterAddStore;
import com.imagine.mohamedtaha.store.ItemsPermission;
import com.imagine.mohamedtaha.store.ItemsStore;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;

public class AddPremissionFragment extends Fragment {
    public AddPremissionFragment() {
        // Required empty public constructor
    }

    FloatingActionButton fab_add_permission;
    ListView mListView;
    AdapterAddPermission adapterAddPermission;
    TaskDbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_premission, container, false);
        TextView namePremission = (TextView)view.findViewById(R.id.ETTypeStore);

        dbHelper = new TaskDbHelper(getActivity());
        mListView = (ListView) view.findViewById(R.id.listViewAddPermission);


        ArrayList<ItemsPermission> itemsPermissions = dbHelper.getAllItemsPermission();
        for (int ii = 0; ii < itemsPermissions.size(); ii++) {
            ItemsPermission itemsPermission = itemsPermissions.get(ii);


        }
        adapterAddPermission = new AdapterAddPermission(getContext(), itemsPermissions);
        mListView.setAdapter(adapterAddPermission);
    //    namePremission.setText("Name Permission");
        fab_add_permission = (FloatingActionButton)view.findViewById(R.id.fab_add_permission);
        fab_add_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent intent = new Intent(getActivity(), EditCategory.class);
                //    startActivity(intent);


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.activity_dialog_add_category,null);
                final EditText ETNamePermission = (EditText)view1.findViewById(R.id.ETNameCategory);
                final EditText ETNotes = (EditText)view1.findViewById(R.id.EtNotes);

                Button BTAddOrUpdate = (Button)view1.findViewById(R.id.BTAdd);
                Button BTDelete = (Button)view1.findViewById(R.id.BTDelete);
                BTAddOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String namePErmission =ETNamePermission.getText().toString();
                        String notes = ETNotes.getText().toString();

                        if (namePErmission.length() == 0){
                            Toast.makeText(getContext(), "not should leave field name emputy", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        ItemsPermission itemsPermission = new ItemsPermission();
                        itemsPermission.setNamePermission(namePErmission);
                        itemsPermission.setNotes(notes);
                        dbHelper.addPErmission(itemsPermission);
                        if (itemsPermission == null){
                            Toast.makeText(getActivity(),"Not save",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getActivity(),"Saved Succeflly",Toast.LENGTH_LONG).show();

                        }


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
                      //  getActivity().finish();


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
}
