package com.imagine.mohamedtaha.store.ui.fragments.stores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.ConvertStores;
import com.imagine.mohamedtaha.store.room.data.Stores;

import static com.imagine.mohamedtaha.store.Constant.ID_STORE;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.Constant.TYPE_STORE;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditStoreFragment extends DialogFragment {
    private StoreViewModel viewModel;
    private EditText ETTypeStore, ETNotesStore;
    private Button BTAddOrUpdate, BTDelete;
    private TextView TVTitleStore;
    Bundle intent;
    TaskDbHelper dbHelper;
    AlertDialog dialog;
    String checkTypeStore;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.fragment_edit_store, null);
        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);

        TextInputLayout ETTypeStoreMaterial = (TextInputLayout) view.findViewById(R.id.ETTypeStoreMaterial);
        TVTitleStore = (TextView) view.findViewById(R.id.TVTitleStore);
        ETTypeStore = (EditText) view.findViewById(R.id.ETTypeStoreStore);
        ETNotesStore = (EditText) view.findViewById(R.id.EtNotesStore);
        BTAddOrUpdate = (Button) view.findViewById(R.id.BTAddStore);
        BTDelete = (Button) view.findViewById(R.id.BTDeleteStore);
        dbHelper = new TaskDbHelper(getContext());
        intent = getArguments();
        ETTypeStoreMaterial.setHint(getString(R.string.type_store));
        if (intent != null) {
            BTAddOrUpdate.setText(getString(R.string.action_edit));
            TVTitleStore.setText(getString(R.string.update_store_titile));
            BTDelete.setVisibility(View.VISIBLE);
            ETTypeStore.setText(intent.getString(TYPE_STORE));
            ETNotesStore.setText(intent.getString(NOTES));
            checkTypeStore = intent.getString(TYPE_STORE);
            Log.d("iddd","  " +intent.getLong(ID_STORE) );
        }

        BTAddOrUpdate.setOnClickListener(v -> saveStore());
        BTDelete.setOnClickListener(v -> showDeleteConfirmationDialog());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        return dialog;

    }

    public void saveStore() {
        String typeStore = ETTypeStore.getText().toString().trim();
        String notesStore = ETNotesStore.getText().toString().trim();
        boolean isExist = dbHelper.isExistTypeStore(typeStore);

        if (intent == null && TextUtils.isEmpty(typeStore) || TextUtils.isEmpty(typeStore)) {
            // ETTypeStore.setError("not should leave field name emputy");
            //   Toast.makeText(getContext(), getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
            ETTypeStore.requestFocus();
            ETTypeStore.setError(getString(R.string.error_empty_text));
            return;
        }

        if (intent == null) {
            Stores itemStoreSave = new Stores(typeStore, notesStore);
            itemStoreSave.setCreatedAt(getDate());
            itemStoreSave.setTime(getTime());
            ConvertStores itemConvertStoreSave = new ConvertStores(typeStore, notesStore);
            if (isExist == true) {
                ETTypeStore.requestFocus();
                ETTypeStore.setError(getString(R.string.error_exist_name));
                return;
            }
            if (itemStoreSave == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_store), Toast.LENGTH_LONG).show();
            } else {
                viewModel.insertStore(itemStoreSave);
                viewModel.insertConvertStore(itemConvertStoreSave);
//                dbHelper.addStore(itemsStoreSave);
//                dbHelper.addConvertStore(itemsStoreSave);

                Toast.makeText(getContext(), getString(R.string.save_store), Toast.LENGTH_LONG).show();
                // getPlanets();

                dialog.dismiss();
            }
        } else {
            boolean isExistDialyMovements = dbHelper.isTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));
            boolean isExistStokeWearehouse = dbHelper.isTypeStoreUsedStokewearhouse(intent.getInt(ID_STORE));
            boolean isExistConvertDailyMovements = dbHelper.isConvertTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));


//            if (isExistDialyMovements == true || isExistStokeWearehouse == true || isExistConvertDailyMovements == true) {
//                Toast.makeText(getContext(), getString(R.string.this_store_not_updated), Toast.LENGTH_SHORT).show();
//                return;
//            }

            if (!checkTypeStore.equals(typeStore) && isExist == true) {
                ETTypeStore.requestFocus();
                ETTypeStore.setError(getString(R.string.error_exist_name));
                return;
            }
            viewModel.updateStore(intent.getLong(ID_STORE), typeStore, notesStore, getDate());
            viewModel.updateConvertStore(intent.getLong(ID_STORE), typeStore, notesStore, getDate());
            Toast.makeText(getContext(), getString(R.string.update_store), Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }

    public void deleteStore() {
        if (intent != null) {
            boolean isExistDialyMovements = dbHelper.isTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));
            boolean isExistStokeWearehouse = dbHelper.isTypeStoreUsedStokewearhouse(intent.getInt(ID_STORE));
            boolean isExistConvertDailyMovements = dbHelper.isConvertTypeStoreUsedDailyMovements(intent.getInt(ID_STORE));
//            if (isExistDialyMovements == true || isExistStokeWearehouse == true || isExistConvertDailyMovements == true) {
//                Toast.makeText(getContext(), getString(R.string.this_store_used), Toast.LENGTH_SHORT).show();
//                return;
//            }
            Log.d("iddd","  " +requireArguments().getLong(ID_STORE) );
                viewModel.deleteStore(requireArguments().getLong(ID_STORE));
                viewModel.deleteConvertStore(requireArguments().getLong(ID_STORE));
                Toast.makeText(getContext(), getString(R.string.delete_store), Toast.LENGTH_LONG).show();
                dialog.dismiss();
        }
    }

    private void showDeleteConfirmationDialog() {
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
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        //Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}