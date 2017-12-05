package com.imagine.mohamedtaha.store;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.imagine.mohamedtaha.store.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.List;

public class StockingWarehouse extends AppCompatActivity {
FloatingActionButton fab_add_stock_warehouse;
    ListView recycleViewAddCategory;
    AdapterAddStokeHouse adapterAddStokeHouse;
    TaskDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocking_warehouse);
        dbHelper = new TaskDbHelper(this);
        recycleViewAddCategory =(ListView)findViewById(R.id.recycleViewAddStokeWarehouse);


        ArrayList<ItemStokeHouse> itemStokeHouses = dbHelper.getAllItemsStokeWarehouseWithCategoruNameandStoreName();
        for (int ii = 0; ii < itemStokeHouses.size(); ii++) {
            ItemStokeHouse itemStokeHouse = itemStokeHouses.get(ii);


        }
        adapterAddStokeHouse = new AdapterAddStokeHouse(this, itemStokeHouses);
        recycleViewAddCategory.setAdapter(adapterAddStokeHouse);

        fab_add_stock_warehouse= (FloatingActionButton)findViewById(R.id.fab_add_stock_warehouse);
        fab_add_stock_warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockingWarehouse.this,EditStokeWarehouse.class);
                startActivity(intent);
            }
        });
    }
}
