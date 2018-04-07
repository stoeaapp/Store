package com.imagine.mohamedtaha.store;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.adapter.AdapterReportStoke;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.loaders.LoaderStokeWearehouse;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Collections;


public class ReportStokeFragment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>,SearchView.OnQueryTextListener{
    public static AdapterReportStoke adapterReportStoke;
    TaskDbHelper dbHelper;
    RecyclerView recyclerViewReportStore;
    ArrayList<ItemsStore> itemsStoresReport = new ArrayList<ItemsStore>();
    private ProgressBar progressBarPermission;
    //Identifier for the category dataloader;
    public static final int STORE_REPORT_LOADER = 6;
    private LinearLayoutManager mLayoutManager;
    RadioButton RBNameGategory,RBFirstBalance,RBChooseTypeStoreStoke,RBAllStoreStoke;
    MaterialBetterSpinner SPSelectTypeStoreStoke;
    String selectTypeStore;
    TextView showCurrentBalance,title_current_balance_stoke;
    int showScreenCurrentBalance;
    CheckBox CBShowCurentBalance;



    @Override
    public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
        //   view = inflater.inflate(R.layout.fragment_report_stoke, container, false);
           setContentView(R.layout.fragment_report_stoke);
           RBNameGategory=(RadioButton)findViewById(R.id.RBNameCategory);
           RBFirstBalance=(RadioButton)findViewById(R.id.RBFirstBalance);
           SPSelectTypeStoreStoke = (MaterialBetterSpinner)findViewById(R.id.SPSelectTypeStoreStoke);
           RBChooseTypeStoreStoke = (RadioButton)findViewById(R.id.RBChooseTypeStoreStoke);
           RBAllStoreStoke = (RadioButton)findViewById(R.id.RBAllStoreStoke);
           CBShowCurentBalance = (CheckBox)findViewById(R.id.CBShowCurrentBalance);
           showCurrentBalance  =(TextView)findViewById(R.id.show_current_balance_stoke);


        SPSelectTypeStoreStoke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectTypeStore = parent.getItemAtPosition(position).toString();

            }
        });
           dbHelper = new TaskDbHelper(this);
           recyclerViewReportStore = (RecyclerView) findViewById(R.id.recycleViewReportStore);
           recyclerViewReportStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

          adapterReportStoke = new AdapterReportStoke(this, itemsStoresReport);
           recyclerViewReportStore.setAdapter(adapterReportStoke);
           loadSpinnerDataForStore();

        getSupportLoaderManager().initLoader(STORE_REPORT_LOADER,null,this);


       }
       /*
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_report_stoke, container, false);

        dbHelper = new TaskDbHelper(getActivity());
        recyclerViewReportStore = (RecyclerView) view.findViewById(R.id.recycleViewReportStore);

        mLayoutManager =     new LinearLayoutManager(getActivity());

        // progressBarPermission =(ProgressBar)view.findViewById(R.id.progressBarPermission);

        adapterReportStoke = new AdapterReportStoke(getContext(), itemsStoresReport);
        recyclerViewReportStore.setAdapter(adapterReportStoke);

        return view;
    }*/


    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
       return new LoaderStokeWearehouse(getApplicationContext(),itemsStoresReport,dbHelper);
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        //recyclerViewReportStore.setVisibility(View.VISIBLE);
        adapterReportStoke.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterReportStoke.swapData(Collections.<ItemsStore>emptyList());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
     //  showCurrentBalance.setText(showScreenCurrentBalance+ " ");

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (RBAllStoreStoke.isChecked()){

            if (RBNameGategory.isChecked()){
            itemsStoresReport = dbHelper.getAllStokeHouseBySearchCategoryName(newText);
            if (itemsStoresReport !=null) adapterReportStoke.setFilter(itemsStoresReport);
            } else  {
            itemsStoresReport = dbHelper.getAllStokeHouseBySearchFirstBalance(newText);
            if (itemsStoresReport !=null) adapterReportStoke.setFilter(itemsStoresReport);
        }

        }else if (RBChooseTypeStoreStoke.isChecked()){
            if (RBNameGategory.isChecked()){

                itemsStoresReport = dbHelper.getAllStokeHouseBySearchCategoryNameAndTypeStore(newText,selectTypeStore);
                showScreenCurrentBalance = dbHelper.getFirstBalanceByNameCategoryAndTypeStore(newText,selectTypeStore);
                showCurrentBalance.setText(showScreenCurrentBalance+ " ");

                if (itemsStoresReport !=null) adapterReportStoke.setFilter(itemsStoresReport);
            } else  {
                itemsStoresReport = dbHelper.getAllStokeHouseBySearchFirstBalanceAndTypeStore(newText,selectTypeStore);
                if (itemsStoresReport !=null) adapterReportStoke.setFilter(itemsStoresReport);
            }
        }


        return false;
    }
    public void checkCheckBox(View view){
        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.CBShowCurrentBalance:
                if(checked){
                    showCurrentBalance.setVisibility(View.VISIBLE);
                    break;
                }else {
                    showCurrentBalance.setVisibility(View.INVISIBLE);
                    break;
                }

        }

    }
    public void checkSpinner(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.RBAllStoreStoke:
                if (checked)
                SPSelectTypeStoreStoke.setVisibility(View.INVISIBLE);
                CBShowCurentBalance.setVisibility(View.INVISIBLE);
                showCurrentBalance.setVisibility(View.INVISIBLE);
                CBShowCurentBalance.setChecked(false);
                break;
            case R.id.RBChooseTypeStoreStoke:
                if (checked)
                 SPSelectTypeStoreStoke.setVisibility(View.VISIBLE);
                if (RBNameGategory.isChecked()){
                    CBShowCurentBalance.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.RBNameCategory:
                if (RBChooseTypeStoreStoke.isChecked()){
                    CBShowCurentBalance.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.RBFirstBalance:
                CBShowCurentBalance.setVisibility(View.INVISIBLE);
                showCurrentBalance.setVisibility(View.INVISIBLE);
                CBShowCurentBalance.setChecked(false);

                break;
        }
    }

    public void loadSpinnerDataForStore(){
        ArrayList<String> IDStore = dbHelper.getDataForSpinnerStore();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,IDStore);
        //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPSelectTypeStoreStoke.setAdapter(arrayAdapter);
    }

}
