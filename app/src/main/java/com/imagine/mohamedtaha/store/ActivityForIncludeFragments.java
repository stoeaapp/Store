package com.imagine.mohamedtaha.store;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityForIncludeFragments extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_for_include_fragments);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);

        CategoryAdapter adapter = new CategoryAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }


}























