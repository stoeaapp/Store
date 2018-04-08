package com.imagine.mohamedtaha.store;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.imagine.mohamedtaha.store.adapter.AdapterReportCategory;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskContract;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.loaders.LoaderReportCategory;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ReportCategory extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>,SearchView.OnQueryTextListener
,DatePickerDialog.OnDateSetListener{
    public static AdapterReportCategory adapterReportCategory;
    TaskDbHelper dbHelper;
    RecyclerView recyclerViewReportCategory;
    ArrayList<ItemsStore> itemsCategoryReport = new ArrayList<ItemsStore>();
    private ProgressBar progressBarPermission;
    //Identifier for the category dataloader;
    public static final int STORE_REPORT_LOADER = 8;
    private LinearLayoutManager mLayoutManager;
    Button showDate;
    RadioButton RBNameCategory,RBDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_category);
        RBNameCategory = (RadioButton)findViewById(R.id.RB_NameCategory);
        RBDate = (RadioButton)findViewById(R.id.RB_date);
        showDate = (Button)findViewById(R.id.showDate);
        showDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                         ReportCategory.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setThemeDark(true);//set dark them for dialog?
                datePickerDialog.vibrate(true); //vibrate on choosing date?
                datePickerDialog.dismissOnPause(true);//dismiss dialog when onPause() called?
                datePickerDialog.showYearPickerFirst(false);//choose year first ?
                datePickerDialog.setAccentColor(Color.parseColor("#9c27A0"));// custom accent
                datePickerDialog.setTitle("اختر التاريخ"); // dialog title
                datePickerDialog.show(getFragmentManager(),"DatePickerdialog");//show dialog

            }
        });


        dbHelper = new TaskDbHelper(this);
        final Cursor cursor = dbHelper.getAllItemsCategoriesCusror();
        recyclerViewReportCategory = (RecyclerView) findViewById(R.id.recycleViewReportCategory);
        recyclerViewReportCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapterReportCategory = new AdapterReportCategory(this, itemsCategoryReport);
        recyclerViewReportCategory.setAdapter(adapterReportCategory);
/*

 */
 //check if available and not read only

        //Create Excel file path
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "MyCategory.xls";

        File directory = new File(sd.getAbsolutePath());
        //Create directory if not exist
        if (!directory.isDirectory()){
            directory.mkdirs();
        }
        try {
            //file path
            File file = new File(directory,csvFile);

            WorkbookSettings wbsettings = new WorkbookSettings();
            wbsettings.setLocale(new Locale("en","EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file,wbsettings);
            //Excel sheet name. 0reperesents first sheet
            WritableSheet sheet = workbook.createSheet("UserList",0);
            //column adn row
            sheet.addCell(new Label(0,0,"UserName"));
            sheet.addCell(new Label(1,0,"PhoneNumber"));

            if (cursor.moveToFirst()){
                do {
                    int  IDCategory = cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID));
                    String nameCategory = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.KEY_NAME_CATEGORY));
                    String dateCategory = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.KEY_DATE));
                    String timeCategory = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.KEY_TIME));
                    String naturalCategory = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.KEY_NATURAL_CATEGORY));
                    String notesCategory = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.KEY_NOTES));
                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0,i,String.valueOf(IDCategory)));
                    sheet.addCell(new Label(0,i,nameCategory));
                    sheet.addCell(new Label(0,i,dateCategory));
                    sheet.addCell(new Label(0,i,timeCategory));
                    sheet.addCell(new Label(0,i,nameCategory));
                    sheet.addCell(new Label(1,i,notesCategory));
                }while (cursor.moveToNext());
            }
            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(this, "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "the Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getSupportLoaderManager().initLoader(STORE_REPORT_LOADER,null,this);

    }

//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
//        textView.setText(date);
//    }

    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
        return new LoaderReportCategory(getApplicationContext(),itemsCategoryReport,dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        adapterReportCategory.swapData(data);


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterReportCategory.swapData(Collections.<ItemsStore>emptyList());

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
//        itemsCategoryReport = dbHelper.getAllCategoryByCategoryName(query);
//        if (itemsCategoryReport !=null){
//            adapterReportCategory.setFilter(itemsCategoryReport);
//        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (RBNameCategory.isChecked()){
            itemsCategoryReport = dbHelper.getAllCategoryByCategoryName(newText);
            if (itemsCategoryReport !=null){
                adapterReportCategory.setFilter(itemsCategoryReport);
            }

        }else if (RBDate.isChecked()){
            itemsCategoryReport = dbHelper.getAllCategoryByDate(newText);
            if (itemsCategoryReport !=null){
                adapterReportCategory.setFilter(itemsCategoryReport);
            }
        }
        return false;
    }
    public void checkCheckBoxCategory(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.RB_NameCategory:
                if(checked){
                    showDate.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.RB_date:
                if (checked){
                    showDate.setVisibility(View.VISIBLE);
                }
                break;
                }
            }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Toast.makeText(this, String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth), Toast.LENGTH_SHORT).show();

    }
}


























