package com.imagine.mohamedtaha.store.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.Permissions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.imagine.mohamedtaha.store.ui.fragments.PermissionsFragment.ID_PERMISSION;
import static com.imagine.mohamedtaha.store.ui.fragments.PermissionsFragment.NAME_PERMISION;
import static com.imagine.mohamedtaha.store.ui.fragments.PermissionsFragment.NOTES_PERMISSION;


public class EditPermissionFragment extends DialogFragment implements DialogInterface.OnClickListener{
    private StoreViewModel viewModel;
    private EditText ETNamePermission,ETNotesPermission;
    private Button BTAddOrUpdatePermission, BTDeletePermission;
    private TextView TVTitlePermission;
    Bundle intent;
    TaskDbHelper dbHelper;
    AlertDialog dialog;
    AlertDialog alertDialogDelete;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_store,null);
        TextInputLayout ETTypeStoreMaterial = (TextInputLayout)view.findViewById(R.id.ETTypeStoreMaterial);
        viewModel = new StoreViewModelFactory(((StoreApplication)getActivity().getApplication()).getRepository()).create(StoreViewModel.class);
        TVTitlePermission = (TextView)view.findViewById(R.id.TVTitleStore);
        ETNamePermission = (EditText)view.findViewById(R.id.ETTypeStoreStore);
        ETNotesPermission = (EditText)view.findViewById(R.id.EtNotesStore);
        BTAddOrUpdatePermission = (Button)view.findViewById(R.id.BTAddStore);
        BTDeletePermission = (Button)view.findViewById(R.id.BTDeleteStore);
        dbHelper = new TaskDbHelper(getContext());
        intent = getArguments();
        TVTitlePermission.setText(getString(R.string.add_permission_titile));
        ETTypeStoreMaterial.setHint(getString(R.string.type_permission));

        boolean saveState = true;
        if (intent != null ){
            saveState = false;
            BTAddOrUpdatePermission.setText(getString(R.string.action_edit));
            TVTitlePermission.setText(getString(R.string.update_permission_titile));

            BTDeletePermission.setVisibility(View.VISIBLE);
            ETNamePermission.setText(intent.getString(NAME_PERMISION));
            ETNotesPermission.setText(intent.getString(NOTES_PERMISSION));
        }
        BTAddOrUpdatePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStore();

            }
        });
        BTDeletePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        // return new AlertDialog.Builder(getActivity()).setTitle(saveState? "Add Store" : "Edit store").setView(view)
        //      .create();
        return dialog ;

    }


    public void saveStore(){
        String namePermission =ETNamePermission.getText().toString().trim();
        String notes = ETNotesPermission.getText().toString().trim();
        boolean isExist = dbHelper.isExistNamePErmission(namePermission);

        if ( intent == null && TextUtils.isEmpty(namePermission)|| TextUtils.isEmpty(namePermission) ){
            ETNamePermission.requestFocus();
            ETNamePermission.setError(getString(R.string.error_empty_text));
            return;
        }
       /* if (isExist ==true){
            ETNamePermission.requestFocus();
            ETNamePermission.setError(getString(R.string.error_exist_permission));
            return;
        }*/
        if (intent == null) {

            Permissions itemSavePermission = new Permissions(namePermission,notes);
            itemSavePermission.setTime(getTime());
            itemSavePermission.setCreatedAt(getDate());
            if (itemSavePermission == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_permission), Toast.LENGTH_LONG).show();
            }else {
                viewModel.insertPermissions(itemSavePermission);
              //  dbHelper.addPermission(itemSavePErmission);
                Toast.makeText(getContext(), getString(R.string.save_permission), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }else {

            Permissions itemUpdatePermision = new Permissions(namePermission,notes);
            itemUpdatePermision.setId(intent.getLong(ID_PERMISSION));
            itemUpdatePermision.setUpdatedAt(getDate());
//            itemUpdatePermision.setNotes(notes);
            boolean isExistForUpdated = dbHelper.isNamePermissioneUsedDailyMovements(intent.getInt(ID_PERMISSION));
            if (isExistForUpdated == true){
                Toast.makeText(getContext(), getString(R.string.this_permission_not_updated), Toast.LENGTH_SHORT).show();
                return;
            }

            if (itemUpdatePermision != null){
                viewModel.updatePermissions(intent.getLong(ID_PERMISSION),namePermission,notes,getDate());
                //dbHelper.updatePermission(itemUpdatePermision);
                Toast.makeText(getContext(), getString(R.string.update_permission), Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }else {
                Toast.makeText(getContext(), getString(R.string.error_update_permission), Toast.LENGTH_LONG).show();
            }

        } }
    public void deleteStore(){
        if (intent != null){
            boolean isExist = dbHelper.isNamePermissioneUsedDailyMovements(intent.getInt(ID_PERMISSION));
            if (isExist == true){
                Toast.makeText(getContext(), getString(R.string.this_permission_used), Toast.LENGTH_SHORT).show();
                return;
            }

          //  if (itemDeletePermision != null){
                viewModel.deletePermissions(intent.getLong(ID_PERMISSION));
                Toast.makeText(getContext(), getString(R.string.delete_permission), Toast.LENGTH_LONG).show();
                dialog.dismiss();
                alertDialogDelete.dismiss();

               // getActivity().finish();

//            }else {
//                Toast.makeText(getContext(), getString(R.string.error_delete_permission), Toast.LENGTH_LONG).show();
//            }
        }else {
            // Toast.makeText(getActivity(), "Not Data For Deleted", Toast.LENGTH_LONG).show();
            return;
        }

    }
    private void showDeleteConfirmationDialog(){
        //Create an AlertDialog.Builder and set the message,and click listeners
        //for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.BTDelete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked the "Delete" button,so delete the Category
                deleteStore();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //USer clciked the "cancel" button ,so dismiss the dialog and continue editing the category
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
        //Create and show the AlertDialog
        alertDialogDelete = builder.create();
        alertDialogDelete.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    //get date
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }
    //get time
    public static String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        Date time=new Date();
        return dateFormat.format(time);
    }
}
