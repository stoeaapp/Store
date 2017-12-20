package com.imagine.mohamedtaha.store;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.data.TableHelper;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TableDaliyMovmentes extends AppCompatActivity {
    TableView<String[]> tb;
    TableHelper tableHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_daliy_movmentes);
        tableHelper = new TableHelper(this);
        tb = (TableView<String[]>)findViewById(R.id.tableView);
        tb.setColumnCount(6);
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        tb.setHeaderBackground(R.color.colorAccent);
        tb.setBackgroundResource(R.color.colorPrimary);
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getSpaceProbeHEaders()));
        tb.setDataAdapter(new SimpleTableDataAdapter(this,tableHelper.getSpaceProbes()));
        tb.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                Toast.makeText(TableDaliyMovmentes.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
            }
        });

    }
}
