package com.imagine.mohamedtaha.store.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imagine.mohamedtaha.store.R;

import java.util.ArrayList;

/**
 * Created by MANASATT on 19/12/17.
 */

public class TableHelper {
    Context context ;
    private String[] header = {"نوع الإذن","اسم الصنف","نوع المخزن","الوارد","الصادر","محول إلى"};
    private String[][]spaceProbes;

    public TableHelper(Context context) {
        this.context = context;
    }
    //Return Table Header
    public String []getSpaceProbeHEaders(){
        return header;
    }
    //Return table rows
    public String[][]getSpaceProbes(){
        ArrayList<ItemsStore> itemsStores = new TaskDbHelper(context).getAllDailyMovements();
        ItemsStore itemsStore;
        spaceProbes = new String[itemsStores.size()][6];
        for (int i = 0; i <itemsStores.size(); i++){
            itemsStore = itemsStores.get(i);
          /*  spaceProbes[i][0]= String.valueOf(itemsStore.getId_permission_id());
            spaceProbes[i][1]= String.valueOf(itemsStore.getId_code_category());
            spaceProbes[i][2]= String.valueOf(itemsStore.getId_code_store());
            spaceProbes[i][3]=String.valueOf(itemsStore.getIssued());
            spaceProbes[i][4]=String.valueOf(itemsStore.getIncoming());
            spaceProbes[i][5]=String.valueOf(itemsStore.getId_convert_to());
*/

            spaceProbes[i][0]= itemsStore.getNamePermission();
            spaceProbes[i][1]= itemsStore.getNameGategory();
            spaceProbes[i][2]= itemsStore.getTypeStore();
            spaceProbes[i][3]=String.valueOf(itemsStore.getIssued());
            spaceProbes[i][4]=String.valueOf(itemsStore.getIncoming());
            spaceProbes[i][5]= itemsStore.getConvertTo();

        }
        return spaceProbes;


    }

    public void setFilter(ArrayList<ItemsStore> itemStoke){
        itemStoke = new ArrayList<>();
        itemStoke.addAll(itemStoke);
      //  notifyDataSetChanged();
    }
}
