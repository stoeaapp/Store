package com.imagine.mohamedtaha.store.data;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.imagine.mohamedtaha.store.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileInputStream;

/**
 * Created by ManasatPC on 05/03/18.
 */

public class BackupData {
    //url for database
    private final String dataPath ="//data//com.imagine.mohamedtaha.store//databases//";

    //name of main data
    private final String dataName = TaskDbHelper.DATABASE_NAME;

    // data main
    private final String data = dataPath + dataName ;

    //name of temp data
    private final String dataTempName = TaskDbHelper.DATABASE_NAME + "_temp";

    //temp data for copy data from sd then copy data temp into main data
    private final String dataTemp = dataPath + dataTempName;

    //folder on sd to backup data
    private final String folderSD = Environment.getExternalStorageDirectory() + "/YSYP";
    private Context context;
    public BackupData(Context context){
        this.context = context;
    }
    // create folder if it not exit
    private void createFolder(){
        File sd = new File(folderSD);
        if (!sd.exists()){
            sd.mkdir();
            System.out.print("create folder");
        }else {
            System.out.print("exits");
        }
    }
    /**
     * Copy database to sd card
     * name of file = database name + time when copy
     * When finish, we call onFinishExport method to send notify for activity
     */
    public void exportToSD(){

        String error = null;
        try {
            createFolder();

            File sd = new File(folderSD);

            if (sd.canWrite()){

                SimpleDateFormat formatTime = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
                String backupDBPath = dataName + "_" + formatTime.format(new Date());

                File currentDB = new File(Environment.getDataDirectory(),data);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()){
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src,0,src.size());
                    src.close();
                    dst.close();
                }else {
                    System.out.print("db not exist");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            error = "Error backup";
        }
        onBackupListener.onFinishExport(error);
    }


    /**
     * import data from file backup on sd card
     * we must create a temp database for copy file on sd card to it.
     * Then we copy all row of temp database into main database.
     * It will keep struct of curren database not change when struct backup database is old
     *
     * @param fileNameOnSD name of file database backup on sd card
     */
    public void importData(String fileNameOnSD){
        File sd = new File(folderSD);

        //create temp database
        SQLiteDatabase dbBackup = context.openOrCreateDatabase(dataTempName,SQLiteDatabase.CREATE_IF_NECESSARY,null);

        String error =null;

        if (sd.canWrite()){
            File currentDB = new File (Environment.getDataDirectory(), dataTemp);
            File backupDB = new File(sd, fileNameOnSD);

            if (currentDB.exists()){
                FileChannel src;
                try {
                    src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src,0,src.size());
                    src.close();
                    dst.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    error = "Error load file";
                } catch (IOException e) {
                    e.printStackTrace();
                    error = "Error import";
                }
            }

        } /**
         *when copy old database into temp database success
         * we copy all row of table into main database
         */
        if (error == null){
            new CopyDataAsyncTask(dbBackup).execute();
        }else {
            onBackupListener.onFinishImport(error);
        }
    }
    /**
     * show dialog for select backup database before import database
     * if user select yes, we will export curren database
     * then show dialog to select old database to import
     * else we onoly show dialog to select old database to import
     */
    public void importFromSD(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("استيراد البيانات").setIcon(R.mipmap.ic_dark_sortable)
                .setMessage("هل تريد أخذ نسخةاحتياطيه قبل استيراد البيانات القديمة");
        builder.setPositiveButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialogListFile(folderSD);

            }
        });
        builder.setNegativeButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialogListFile(folderSD);
                exportToSD();
            }
        });
        builder.show();

    }
    /**
     * show dialog list all backup file on sd card
     * @param forderPath folder conatain backup file
     */
    private void showDialogListFile(String forderPath){
        createFolder();

        File forder = new File(forderPath);
        File[] listFile = forder.listFiles();

        final String[] listFileName = new String[listFile.length];
        for (int i = 0, j = listFile.length - 1; i < listFile.length; i++, j--){
            listFileName[j] = listFile[i].getName();
        }
        if (listFileName.length > 0){
            //get layout for list
            LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.list_backup_file,null);

            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

            //set view for dialog
            builder.setView(convertView);
            builder.setTitle("اختيار ملف ").setIcon(R.mipmap.ic_launcher);
            final AlertDialog alert = builder.create();

            ListView lv =(ListView)convertView.findViewById(R.id.lv_backup);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listFileName);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    alert.dismiss();
                    importData(listFileName[position]);
                }
            });
            alert.show();
        }else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AppCompatAlertDialogStyle);
            builder.setTitle("مسح").setIcon(R.mipmap.ic_launcher).setMessage("لا توجد نسخة أحتياطية إلى الان");
            builder.show();
        }
    }



    /**
     * AsyncTask for copy data
     */
    class CopyDataAsyncTask extends AsyncTask<Void,Void,Void>{
        ProgressDialog progress = new ProgressDialog(context);
        SQLiteDatabase db;
        public CopyDataAsyncTask(SQLiteDatabase dbBackup){
            this.db = dbBackup;
        }
        /**
         * will call first
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("جاري استيراد البيانات....");
           progress.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            copyDataForTableDailyMovements(db);
            copyDataForTablePermission(db);
            copyDataForTableStore(db);
            copyDataForTableCategoryes(db);
            copyDataForTableConvertStore(db);
            copyDataForTableStoke(db);
            return null;
        }

        /**
         * end process
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progress.isShowing()){
                progress.dismiss();
            }
            onBackupListener.onFinishImport(null);

        }
    }



    /**
     * copy all row of temp database into main database
     * @param dbBackup
     */
    private void copyDataForTableDailyMovements(SQLiteDatabase dbBackup){
        TaskDbHelper db = new TaskDbHelper(context);
        db.deleteMovementsBackup(null);
        /** copy all row of subject table */
        Cursor cursor = dbBackup.query(true, TaskContract.TaskEntry.TABLE_DAILY_MOVEMENTS,null,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemsStore itemsStore = db.cursorToNoteForTableDailyMovements(cursor);
            db.addDailyMovements(itemsStore);
            cursor.moveToNext();
        }
        cursor.close();
        context.deleteDatabase(dataTempName);

    }
    private void copyDataForTablePermission(SQLiteDatabase dbBackup){
        TaskDbHelper db = new TaskDbHelper(context);
        db.deletePermissionBackup(null);
        /** copy all row of subject table */
        Cursor cursor = dbBackup.query(true, TaskContract.TaskEntry.TABLE_PERMISSION,null,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemsStore itemsStore = db.cursorToNoteForTablePermission(cursor);
            db.addPermission(itemsStore);
            cursor.moveToNext();
        }
        cursor.close();
        context.deleteDatabase(dataTempName);

    }
    private void copyDataForTableStore(SQLiteDatabase dbBackup){
        TaskDbHelper db = new TaskDbHelper(context);
        db.deleteStoreBackup(null);
        /** copy all row of subject table */
        Cursor cursor = dbBackup.query(true, TaskContract.TaskEntry.TABLE_STORE,null,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemsStore itemsStore = db.cursorToNoteForTableStore(cursor);
            db.addStore(itemsStore);
            cursor.moveToNext();
        }
        cursor.close();
        context.deleteDatabase(dataTempName);

    }
    private void copyDataForTableCategoryes(SQLiteDatabase dbBackup){
        TaskDbHelper db = new TaskDbHelper(context);
        db.deleteCategoresBackup(null);
        /** copy all row of subject table */
        Cursor cursor = dbBackup.query(true, TaskContract.TaskEntry.TABLE_CATEGORIES,null,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemsStore itemsStore = db.cursorToNoteForTableCategores(cursor);
            db.addCategory(itemsStore);
            cursor.moveToNext();
        }
        cursor.close();
        context.deleteDatabase(dataTempName);

    }
    private void copyDataForTableUsers(SQLiteDatabase dbBackup){
        TaskDbHelper db = new TaskDbHelper(context);
        db.deleteUsersBackup(null);
        /** copy all row of subject table */
        Cursor cursor = dbBackup.query(true, TaskContract.TaskEntry.TABLE_USERS,null,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemsStore itemsStore = db.cursorToNoteForTablePermission(cursor);
            db.addPermission(itemsStore);
            cursor.moveToNext();
        }
        cursor.close();
        context.deleteDatabase(dataTempName);

    }
    private void copyDataForTableConvertStore(SQLiteDatabase dbBackup){
        TaskDbHelper db = new TaskDbHelper(context);
        db.deleteConvertStoreBackup(null);
        /** copy all row of subject table */
        Cursor cursor = dbBackup.query(true, TaskContract.TaskEntry.TABLE_CONVERT_STORE,null,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemsStore itemsStore = db.cursorToNoteForTableConvertStore(cursor);
            db.addConvertStore(itemsStore);
            cursor.moveToNext();
        }
        cursor.close();
        context.deleteDatabase(dataTempName);

    }
    private void copyDataForTableStoke(SQLiteDatabase dbBackup){
        TaskDbHelper db = new TaskDbHelper(context);
        db.deleteStokeBackup(null);
        /** copy all row of subject table */
        Cursor cursor = dbBackup.query(true, TaskContract.TaskEntry.TABLE_STOCKING_WAREHOUSE,null,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemsStore itemsStore = db.cursorToNoteForTableStokeWarehouse(cursor);
            db.addStokeWarehouse(itemsStore);
            cursor.moveToNext();
        }
        cursor.close();
        context.deleteDatabase(dataTempName);

    }

    private OnBackupListener onBackupListener;
    public void setOnBackListener(OnBackupListener onBackListener){
        this.onBackupListener =onBackListener;

    }
    public interface OnBackupListener{
        public void onFinishExport(String error);
        public void onFinishImport(String error);
    }


}


























