package com.imagine.mohamedtaha.store.fragments;

import android.os.Bundle;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.imagine.mohamedtaha.store.loaders.LoaderPErmission;
import com.imagine.mohamedtaha.store.adapter.AdapterAddPermission;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.Collections;

public class AddPremissionFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>, SearchView.OnQueryTextListener {
    public AddPremissionFragment() {
        // Required empty public constructor
    }
    public static final String ID_PERMISSION = "id";
    public static final String NAME_PERMISION = "namePErmission";
    public static final String NOTES_PERMISSION = "notes";
    public static final String DIALOG_PERMISSION = "dialogPermission";

    FloatingActionButton fab_add_permission;
    ListView mListView;
    public static  AdapterAddPermission adapterAddPermission;
    TaskDbHelper dbHelper;
    ArrayList<ItemsStore> itemsPermissions = new ArrayList<ItemsStore>();
    private ProgressBar progressBarPermission;
    //Identifier for the category dataloader;
    public static final int PERMISSION_LOADER = 2;

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
        progressBarPermission =(ProgressBar)view.findViewById(R.id.progressBarPermission);

        adapterAddPermission = new AdapterAddPermission(getContext(), itemsPermissions);
        View emptyView = view.findViewById(R.id.empty_view_permission);
        mListView.setEmptyView(emptyView);
        mListView.setAdapter(adapterAddPermission);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //   Toast.makeText(getContext(), "Click :"+ position, Toast.LENGTH_SHORT).show();
                ItemsStore itemSPermision = itemsPermissions.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(ID_PERMISSION, itemSPermision.getId());
                bundle.putString(NAME_PERMISION, itemSPermision.getNamePermission());
                bundle.putString(NOTES_PERMISSION, itemSPermision.getNotes());
                // startActivity(intent);
                //  long id = cursor.getLong(cursor.getColumnIndex(TaskContract.TaskEntry._ID));
                EditPermissionFragment f = new EditPermissionFragment();
                f.setArguments(bundle);
                f.show(getFragmentManager(),DIALOG_PERMISSION);}});
        //    namePremission.setText("Name Permission");
        fab_add_permission = (FloatingActionButton)view.findViewById(R.id.fab_add_permission);
        fab_add_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditPermissionFragment().show(getFragmentManager(),DIALOG_PERMISSION);



            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Kick off the loader
        getActivity().getSupportLoaderManager().initLoader(PERMISSION_LOADER, null, this);
    }

    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
        return new LoaderPErmission(getContext().getApplicationContext(),itemsPermissions,dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        progressBarPermission.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        adapterAddPermission.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterAddPermission.swapData(Collections.<ItemsStore>emptyList());

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        itemsPermissions = dbHelper.getAllItemsPermissionBySearch(newText);
        if (itemsPermissions !=null){
            adapterAddPermission.setFilter(itemsPermissions);
            //  getSupportLoaderManager().restartLoader(Daily_LOADER,null,this);

        }
        return false;
    }
}
