package com.imagine.mohamedtaha.store;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.imagine.mohamedtaha.store.adapter.CategoryAdapter;

public class ActivityForIncludeFragments extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_for_include_fragments);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);

        CategoryAdapter adapter = new CategoryAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        invalidateOptionsMenu();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItemAddData = menu.findItem(R.id.add_data);
        MenuItem menuItemActionSearch = menu.findItem(R.id.action_search);

        menuItemAddData.setVisible(false);
        menuItemActionSearch.setVisible(false);

        return super.onPrepareOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_stocking_warehouse:
                Intent intent = new Intent(ActivityForIncludeFragments.this,StockingWarehouse.class);
                startActivity(intent);
                break;
            case R.id.reportes:
                Intent intentReport = new Intent(ActivityForIncludeFragments.this,TableDaliyMovmentes.class);
                startActivity(intentReport);
                break;
                }
        return super.onOptionsItemSelected(item);

    }
}























