package com.imagine.mohamedtaha.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.ReportCategory;
import com.imagine.mohamedtaha.store.ReportDailyMovements;
import com.imagine.mohamedtaha.store.ReportStokeFragment;
import com.imagine.mohamedtaha.store.ReportStores;
import com.imagine.mohamedtaha.store.ReportesActivity;

import java.util.List;

/**
 * Created by ManasatPC on 13/01/18.
 */

public class AdapterShowReportes extends BaseAdapter {private Context mContext;
    private final String [] texts;
    private final LayoutInflater inflater;
    int[] imageId ;



    public AdapterShowReportes(Context context,String[]texts,int []images) {
        this.mContext = context;
        this.texts = texts;
        this.imageId = images;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return texts.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View contentView = convertView;
        if (contentView == null){
            contentView = inflater.inflate(R.layout.custom_show_reportes,parent,false);
            //Find individual views that we want to modify in the list item layout
        }
        TextView TVShowReportes = (TextView)contentView.findViewById(R.id.TVshowReportes);
        ImageView IVShowImageReportes= (ImageView)contentView.findViewById(R.id.imageShowReportes);
        TVShowReportes.setText(texts[position]);
        //Setting Image
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),imageId[position]);
        IVShowImageReportes.setImageBitmap(bitmap);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent intentReportCategory = new Intent(mContext,ReportCategory.class);
                        mContext.startActivity(intentReportCategory);
                        break;
                    case 1:
                        Intent intentReportStore = new Intent(mContext,ReportStores.class);
                        mContext.startActivity(intentReportStore);
                        break;
                    case 3:
                        Intent intentReportFirstBalance = new Intent(mContext,ReportStokeFragment.class);
                        mContext.startActivity(intentReportFirstBalance);
                        break;
                    case 4:
                        Intent intentReportDailyMovements = new Intent(mContext,ReportDailyMovements.class);
                        mContext.startActivity(intentReportDailyMovements);
                        break;

                    default:
                    Toast.makeText(mContext, "لم يتم تجهيزه", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return contentView;
    }
}






















