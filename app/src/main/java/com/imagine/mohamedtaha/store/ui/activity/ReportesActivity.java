package com.imagine.mohamedtaha.store.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.adapter.AdapterShowReportes;

import java.util.List;

public class ReportesActivity extends AppCompatActivity {
    ListView listViewShowReportes;
    AdapterShowReportes adapterShowReportes;
  public static    int images []= {R.mipmap.ic_reports,R.mipmap.ic_reports,R.mipmap.ic_reports,R.mipmap.ic_reports,R.mipmap.ic_reports,R.mipmap.ic_reports};
     public  String texts[] = {" تقارير  الأصناف"," تقارير  المخازن"," تقارير الأذونات "," تقارير ترصيد المستودع","تقارير الحركات اليومية"," تقارير  المستخدمين"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        listViewShowReportes = (ListView)findViewById(R.id.listViewShowReportes);
        adapterShowReportes =new AdapterShowReportes(this,texts,images);
        listViewShowReportes.setAdapter(adapterShowReportes);
    }
}

