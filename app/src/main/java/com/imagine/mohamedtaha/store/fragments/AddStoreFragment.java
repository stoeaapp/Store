package com.imagine.mohamedtaha.store.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.imagine.mohamedtaha.store.loaders.LoaderStore;
import com.imagine.mohamedtaha.store.adapter.AdapterAddStore;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.Collections;


public class AddStoreFragment extends Fragment {

    public AddStoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename parameter arguments, choose names that match
    FloatingActionButton fabAddCategory;
    ListView mListView;
    AdapterAddStore adapterAddStore;
    TaskDbHelper dbHelper;
    ArrayList<ItemsStore> itemsStores = new ArrayList<ItemsStore>();
    private ProgressBar progressBar;
    public static final String DIALOD_STORE = "dialogStore";

    public static final String ID_STORE = "id";
    public static final String TYPE_STORE = "typeStore";
    public static final String NOTES_STORE = "notes";

    //Identifier for the category dataloader;
    private static final int STORE_LOADER = 1;
    Bundle intent;
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
        progressBar =(ProgressBar)view.findViewById(R.id.progressBar);
        adapterAddStore = new AdapterAddStore(getContext(),itemsStores);
        View emptyView = view.findViewById(R.id.empty_view_store);
        mListView.setEmptyView(emptyView);
        mListView.setAdapter(adapterAddStore);

      /*   itemsStores = dbHelper.getAllItemsStore();
        for (int ii = 0; ii<itemsStores.size(); ii++){
            ItemsStore itemsStore = itemsStores.get(ii);
        }*/
     //   adapterAddStore = new AdapterAddStore(getContext(),null);
      //  mListView.setAdapter(adapterAddStore);

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
                Bundle bundle = new Bundle();
                bundle.putInt(ID_STORE, itemsStore.getId());
                bundle.putString(TYPE_STORE, itemsStore.getTypeStore());
                bundle.putString(NOTES_STORE, itemsStore.getNotes());
                EditStoreFragment f = new EditStoreFragment();
                f.setArguments(bundle);
                f.show(getFragmentManager(),DIALOD_STORE);
            }
        });

        fabAddCategory = (FloatingActionButton)view.findViewById(R.id.fab_add_store);
        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditStoreFragment().show(getFragmentManager(),DIALOD_STORE);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Kick off the loader
        getActivity().getSupportLoaderManager().initLoader(STORE_LOADER, null, loaderCallbacks);

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>> loaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>() {
        @Override
        public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {

            return new LoaderStore(getContext().getApplicationContext(),itemsStores,dbHelper);
        }
        @Override
        public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
            progressBar.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            adapterAddStore.swapData(data);

        }
        @Override
        public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
           adapterAddStore.swapData(Collections.<ItemsStore>emptyList());


        }
    };

}