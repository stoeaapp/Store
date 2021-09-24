package com.imagine.mohamedtaha.store.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.imagine.mohamedtaha.store.adapter.AdapterConvertStore;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.loaders.LoaderConvertStore;
import com.imagine.mohamedtaha.store.ui.fragments.add.stores.EditStoreFragment;

import java.util.ArrayList;
import java.util.Collections;


public class AddConvertStoreFragment extends Fragment {

        public AddConvertStoreFragment() {
            // Required empty public constructor
        }

        // TODO: Rename parameter arguments, choose names that match
        FloatingActionButton fab_add_convert_store;
        public static ListView mListView;
        public static AdapterConvertStore adapterConvertStore;
        TaskDbHelper dbHelper;
        public static ArrayList<ItemsStore> itemsStores = new ArrayList<ItemsStore>();
        private ProgressBar progressBar;
        public static final String DIALOD_STORE = "dialogStore";
        public static final String ID_STORE = "id";
        public static final String TYPE_CONVERT_STORE = "typeConvertStore";
        public static final String NOTES_STORE = "notes";

        //Identifier for the category dataloader;
        private static final int STORE_LOADER = 5;
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
            view = inflater.inflate(R.layout.fragment_convert_store, container, false);

            dbHelper = new TaskDbHelper(getActivity());
            //Set the RecycleView to its corresponding view
            mListView = (ListView) view.findViewById(R.id.RecyclerViewAddStore);
            progressBar =(ProgressBar)view.findViewById(R.id.progressBar);
            adapterConvertStore = new AdapterConvertStore(getContext(),itemsStores);
            View emptyView = view.findViewById(R.id.empty_view_store);
            mListView.setEmptyView(emptyView);
            mListView.setAdapter(adapterConvertStore);

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
            //  adapterAddCategoryContentProvider = new AdapterAddCategoryContentProvider(getActivity());


            //Setup the item click listener
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ItemsStore itemsStore = itemsStores.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID_STORE, itemsStore.getId());
                    bundle.putString(TYPE_CONVERT_STORE, itemsStore.getConvertTo());
                    bundle.putString(NOTES_STORE, itemsStore.getNotes());
                    EditStoreFragment f = new EditStoreFragment();
                    f.setArguments(bundle);
                    f.show(getChildFragmentManager(),DIALOD_STORE);
                }
            });

            fab_add_convert_store = (FloatingActionButton)view.findViewById(R.id.fab_add_convert_store);
            fab_add_convert_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new EditConvertStoreFragment().show(getChildFragmentManager(),DIALOD_STORE);
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

                return new LoaderConvertStore(getContext().getApplicationContext(),itemsStores,dbHelper);
            }
            @Override
            public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
                progressBar.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                adapterConvertStore.swapData(data);

            }
            @Override
            public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
                adapterConvertStore.swapData(Collections.<ItemsStore>emptyList());


            }
        };


        public static class  EditConvertStoreFragment extends DialogFragment implements DialogInterface.OnClickListener {
            private EditText ETTypeStore,ETNotes;
            private Button BTAddOrUpdate, BTDelete;
            private TextView TVTitleStore;
            Bundle intent;
            TaskDbHelper dbHelper;
            AlertDialog dialog;
  /*  public static EditStoreFragment newInstance(Bundle getBundle){
        bundle = new Bundle();
        String id =
        bundle.putBundle(EXTRA_ID, getBundle);

        EditStoreFragment editStoreFragment = new EditStoreFragment();
        editStoreFragment.setArguments(bundle);
        return editStoreFragment;
    }*/

            @NonNull
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_store,null);
                TextInputLayout ETTypeStoreMaterial = (TextInputLayout)view.findViewById(R.id.ETTypeStoreMaterial);
                TVTitleStore = (TextView)view.findViewById(R.id.TVTitleStore);
                ETTypeStore = (EditText)view.findViewById(R.id.ETTypeStoreStore);
                ETNotes = (EditText)view.findViewById(R.id.EtNotesStore);
                BTAddOrUpdate = (Button)view.findViewById(R.id.BTAddStore);
                BTDelete = (Button)view.findViewById(R.id.BTDeleteStore);
                dbHelper = new TaskDbHelper(getContext());
                intent = getArguments();
                ETTypeStoreMaterial.setHint(getString(R.string.type_store));


                boolean saveState = true;
                if (intent != null ){
                    saveState = false;
                    BTAddOrUpdate.setText(getString(R.string.action_edit));
                    TVTitleStore.setText(getString(R.string.update_store_title));
                    BTDelete.setVisibility(View.VISIBLE);
                    ETTypeStore.setText(intent.getString(TYPE_CONVERT_STORE));
                    ETNotes.setText(intent.getString(NOTES_STORE));
                }
                BTAddOrUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveStore();

                    }
                });
                BTDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteConfirmationDialog();

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                dialog = builder.create();
                dialog.show();

                // return new AlertDialog.Builder(getActivity()).setTitle(saveState? "Add Store" : "Edit store").setView(view)
                //      .create();
                return dialog ;

            }

            private   void getPlanets()
            {
                itemsStores.clear();
                TaskDbHelper db=new TaskDbHelper(getContext());
                ItemsStore p=null;
                Cursor c=  db.getAllItemsStoreWithCursor();
                while (c.moveToNext())
                {
                    int id=c.getInt(0);
                    String name=c.getString(1);
                    String date = c.getString(2);
                    String time = c.getString(3);
                    p=new ItemsStore();
                    p.setId(id);
                    p.setTypeStore(name);
                    p.setCreatedDate(date);
                    p.setCreatedTime(time);
                    itemsStores.add(p);
                }
                if(itemsStores.size()>0)
                {
                    mListView.setAdapter(adapterConvertStore);
                }
            }
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
            public void saveStore(){
                String typeConvertStore =ETTypeStore.getText().toString().trim();
                boolean isExist = dbHelper.isExistTypeConvertStore(typeConvertStore);
                String notes = ETNotes.getText().toString().trim();

                if ( intent == null && TextUtils.isEmpty(typeConvertStore) ||TextUtils.isEmpty(typeConvertStore) ){
                    // ETTypeStore.setError("not should leave field name emputy");
                    //   Toast.makeText(getContext(), getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
                    ETTypeStore.requestFocus();
                    ETTypeStore.setError(getString(R.string.error_empty_text));
                    return;
                }
                if (isExist ==true){
                    ETTypeStore.requestFocus();
                    ETTypeStore.setError(getString(R.string.error_exist_name));
                    return;
                }
                if (intent == null) {

                    ItemsStore itemsConvertStoreSave = new ItemsStore();
                    itemsConvertStoreSave.setConvertTo(typeConvertStore);
                    itemsConvertStoreSave.setNotes(notes);
                    if (itemsConvertStoreSave == null) {
                        Toast.makeText(getContext(),getString(R.string.error_save_store), Toast.LENGTH_LONG).show();
                    }else {
                        dbHelper.addConvertStore(itemsConvertStoreSave);
                        Toast.makeText(getContext(), getString(R.string.save_store), Toast.LENGTH_LONG).show();
                        // getPlanets();

                        dialog.dismiss();
                    }
                }else {
                    ItemsStore itemsStoreUpdate = new ItemsStore();
                    itemsStoreUpdate.setId(intent.getInt(ID_STORE));
                    itemsStoreUpdate.setConvertTo(typeConvertStore);
                    itemsStoreUpdate.setNotes(notes);
                    if (itemsStoreUpdate != null){
                        dbHelper.updateConvertStore(itemsStoreUpdate);
                        Toast.makeText(getContext(), getString(R.string.update_store), Toast.LENGTH_LONG).show();
                        getPlanets();


                        dialog.dismiss();

                    }else {
                        Toast.makeText(getContext(), getString(R.string.error_update_store), Toast.LENGTH_LONG).show();
                    }
                } }
            public void deleteStore(){
                if (intent != null){
                    String typeStore =ETTypeStore.getText().toString();
                    String notes = ETNotes.getText().toString();

                    ItemsStore itemsStoreDelte = new ItemsStore();
                    itemsStoreDelte.setId(intent.getInt(ID_STORE));
                    itemsStoreDelte.setConvertTo(typeStore);
                    itemsStoreDelte.setNotes(notes);
                    boolean isExistDialyMovements = dbHelper.isTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));
                    boolean isExistStokeWearehouse = dbHelper.isTypeStoreUsedStokewearhouse(intent.getInt(ID_STORE));

                    if (isExistDialyMovements == true || isExistStokeWearehouse == true){
                        Toast.makeText(getContext(), getString(R.string.this_store_used), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (itemsStoreDelte != null){
                        dbHelper.deleteStore(itemsStoreDelte);
                        Toast.makeText(getContext(), getString(R.string.delete_store), Toast.LENGTH_LONG).show();
                        getPlanets();

                        dialog.dismiss();

                        // getActivity().finish();

                    }else {
                        Toast.makeText(getContext(), getString(R.string.error_delete_store), Toast.LENGTH_LONG).show();
                    }
                }else {
                    // Toast.makeText(getActivity(), "Not Data For Deleted", Toast.LENGTH_LONG).show();
                    return;
                }

            }
            private void showDeleteConfirmationDialog(){
                //Create an AlertDialog.Builder and set the message,and click listeners
                //for the positive and negative buttons on the dialog.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.delete_dialog_msg);
                builder.setPositiveButton(R.string.BTDelete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //User clicked the "Delete" button,so delete the Category
                        deleteStore();

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //USer clciked the "cancel" button ,so dismiss the dialog and continue editing the category
                        if (dialog != null){
                            dialog.dismiss();
                        }
                    }
                });
                //Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }




    }