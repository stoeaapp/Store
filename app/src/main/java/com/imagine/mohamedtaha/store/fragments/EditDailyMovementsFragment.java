package com.imagine.mohamedtaha.store.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.StoreApplication;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory;
import com.imagine.mohamedtaha.store.room.data.DailyMovements;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;
import static com.imagine.mohamedtaha.store.ui.activity.MainActivity.CONVERT_TO_DAILY;
import static com.imagine.mohamedtaha.store.ui.activity.MainActivity.IDDaily;
import static com.imagine.mohamedtaha.store.ui.activity.MainActivity.INCOMING_DAILY;
import static com.imagine.mohamedtaha.store.ui.activity.MainActivity.ISSUED_DAILY;
import static com.imagine.mohamedtaha.store.ui.activity.MainActivity.NAME_CATEGORY_DAILY;
import static com.imagine.mohamedtaha.store.ui.activity.MainActivity.NAME_PERMISSION_DAILY;
import static com.imagine.mohamedtaha.store.ui.activity.MainActivity.TYPE_STORE_DAILY;


public class EditDailyMovementsFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private StoreViewModel viewModel;
    private EditText ETIssued, ETIncoming;
    private TextView TVTitleStokeWearhouse, ETCurentBalance, TVShowText;
    private Button BTDeleteDailyMovement, BTAddDailyMovement;
    MaterialBetterSpinner SPNamePermisionDaily, SPTypeStoreDaily, SPNameCategoryDaily, SPConvertToDaily;
    Bundle intentDailyMovement;
    TaskDbHelper dbHelperDailyMovement;
    AlertDialog dialogDailyMovement;
    AlertDialog dialogDeleteDailyMovement;
    long idSpinnerCategory, idSpinnerStore, idSpinnerPermission, idSpinnerConvertTo;
    int firstBalance, sumIncoming, sumIssued, sumConvertTo, currentBalance;
    int isLastRow;
    String SpinnerCategory, SpinnerStore, SpinnerPermission;
    String SpinnerConvertTo = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_daily_movements, null);

        viewModel = new StoreViewModelFactory(((StoreApplication) requireActivity().getApplication()).getRepository()).create(StoreViewModel.class);

        TVTitleStokeWearhouse = (TextView) view.findViewById(R.id.TVTitleStokeWearhouse);
        TVShowText = (TextView) view.findViewById(R.id.ETshowText);
        ETIncoming = (EditText) view.findViewById(R.id.ETIncoming);
        ETIssued = (EditText) view.findViewById(R.id.ETIssued);
        ETIncoming.addTextChangedListener(new CheckZero());
        ETIssued.addTextChangedListener(new CheckZero());

        SPNamePermisionDaily = (MaterialBetterSpinner) view.findViewById(R.id.SPermissionDaily);
        SPTypeStoreDaily = (MaterialBetterSpinner) view.findViewById(R.id.SPCodeStoreDialy);
        SPNameCategoryDaily = (MaterialBetterSpinner) view.findViewById(R.id.SPCodeCategoryDialy);
        SPConvertToDaily = (MaterialBetterSpinner) view.findViewById(R.id.SPCovertToDaily);
        BTAddDailyMovement = (Button) view.findViewById(R.id.BTAddDailyMovement);
        BTDeleteDailyMovement = (Button) view.findViewById(R.id.BTDeleteDailyMovement);
        ETCurentBalance = (TextView) view.findViewById(R.id.ETCurentBalance);

        dbHelperDailyMovement = new TaskDbHelper(getContext());

        intentDailyMovement = getArguments();

        //   boolean saveState = true;
        if (intentDailyMovement != null) {
            //      saveState = false;
            BTDeleteDailyMovement.setVisibility(View.VISIBLE);
            BTAddDailyMovement.setText(getString(R.string.action_edit));
            TVTitleStokeWearhouse.setText(getString(R.string.update_daily_movement_titile));
            SPNamePermisionDaily.setText(intentDailyMovement.getString(NAME_PERMISSION_DAILY));
            SPTypeStoreDaily.setText(intentDailyMovement.getString(TYPE_STORE_DAILY));
            SPNameCategoryDaily.setText(intentDailyMovement.getString(NAME_CATEGORY_DAILY));
            if (intentDailyMovement.getInt(INCOMING_DAILY) != 0) {
                ETIncoming.setVisibility(View.VISIBLE);
            }
            ETIncoming.setText(String.valueOf(intentDailyMovement.getInt(INCOMING_DAILY)));
            if (intentDailyMovement.getInt(ISSUED_DAILY) != 0) {
                ETIssued.setVisibility(View.VISIBLE);
            }
            ETIssued.setText(String.valueOf(intentDailyMovement.getInt(ISSUED_DAILY)));
            if (intentDailyMovement.getString(CONVERT_TO_DAILY) != null) {
                SPConvertToDaily.setVisibility(View.VISIBLE);
            }
            SPConvertToDaily.setText(intentDailyMovement.getString(CONVERT_TO_DAILY));


        }
        BTAddDailyMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDailyMovement();
            }
        });
        BTDeleteDailyMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
        SPNamePermisionDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerPermission = parent.getItemAtPosition(position).toString();
                idSpinnerPermission = parent.getItemIdAtPosition(position + 1);
                showStateVisibilty();
                SPNameCategoryDaily.setText(" ");
                SpinnerCategory = "";
                idSpinnerCategory = 0;
                ETCurentBalance.setText("");
                TVShowText.setVisibility(View.INVISIBLE);
                ETIncoming.setText("");
                ETIssued.setText("");

            }
        });
        SPNameCategoryDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerCategory = parent.getItemAtPosition(position).toString();
                idSpinnerCategory = parent.getItemIdAtPosition(position + 1);

                firstBalance = dbHelperDailyMovement.getFirstBalance(idSpinnerCategory, idSpinnerStore);
                sumIssued = dbHelperDailyMovement.getIssedForDailyMovements(idSpinnerCategory, idSpinnerStore);
                sumIncoming = dbHelperDailyMovement.getIncomingForDailyMovements(idSpinnerCategory, idSpinnerStore);
                sumConvertTo = dbHelperDailyMovement.getConvertToForDailyMovements(idSpinnerCategory, idSpinnerStore);
                currentBalance = firstBalance + sumIncoming + sumConvertTo - sumIssued;
                TVShowText.setVisibility(View.VISIBLE);
                ETCurentBalance.setText(currentBalance + "");

            }
        });
        SPTypeStoreDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerStore = parent.getItemAtPosition(position).toString();
                idSpinnerStore = parent.getItemIdAtPosition(position + 1);
                SPNameCategoryDaily.setText("");
                SpinnerCategory = "";
                idSpinnerCategory = 0;
                ETCurentBalance.setText("");
                TVShowText.setVisibility(View.INVISIBLE);


            }
        });
        SPConvertToDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerConvertTo = parent.getItemAtPosition(position).toString();
                idSpinnerConvertTo = parent.getItemIdAtPosition(position + 1);
            }
        });

        loadSpinnerDataForCategory();
        loadSpinnerDataForStores();
        loadSpinnerDataForNamePermissions();
        //loadSpinnerDataForConvertTo();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // builder.setTitle(saveState? "Add" : "Edit");
        builder.setView(view);
        dialogDailyMovement = builder.create();
        dialogDailyMovement.show();
        return dialogDailyMovement;
    }

    public void showStateVisibilty() {
        switch ((int) idSpinnerPermission) {
            case 1:
                ETIssued.setVisibility(View.VISIBLE);
                ETIncoming.setVisibility(View.GONE);
                SPConvertToDaily.setVisibility(View.GONE);

                break;
            case 2:
                ETIssued.setVisibility(View.GONE);
                ETIncoming.setVisibility(View.VISIBLE);
                SPConvertToDaily.setVisibility(View.GONE);
                break;
            case 3:
                ETIssued.setVisibility(View.VISIBLE);
                ETIncoming.setVisibility(View.GONE);
                SPConvertToDaily.setVisibility(View.VISIBLE);
                break;
            case 4:
                ETIssued.setVisibility(View.VISIBLE);
                ETIncoming.setVisibility(View.GONE);
                SPConvertToDaily.setVisibility(View.GONE);
                break;

        }
    }

    public void saveDailyMovement() {
        String incoming = ETIncoming.getText().toString().trim();
        String issued = ETIssued.getText().toString().trim();
        //  int inc = Integer.parseInt(incoming);
        //   int iss = Integer.parseInt(issued);

        if (idSpinnerPermission == 0) {
            SPNamePermisionDaily.requestFocus();
            SPNamePermisionDaily.setError(getString(R.string.error_empty_permission));
            return;

        }
        if (idSpinnerStore == 0) {
            SPTypeStoreDaily.requestFocus();
            SPTypeStoreDaily.setError(getString(R.string.error_empty_store));
            return;

        }
        if (idSpinnerCategory == 0) {
            SPNameCategoryDaily.requestFocus();
            SPNameCategoryDaily.setError(getString(R.string.error_empty_category));
            return;
//|| inc <=0 && iss <=0
        }
        if (TextUtils.isEmpty(incoming) && TextUtils.isEmpty(issued)) {
            Toast.makeText(getContext(), getString(R.string.error_empty_text), Toast.LENGTH_SHORT).show();
            return;
        }
        if (idSpinnerPermission == 3 && idSpinnerConvertTo == 0) {
            SPConvertToDaily.requestFocus();
            SPConvertToDaily.setError(getString(R.string.error_convert_to));
            return;
        }
        if (idSpinnerPermission == 1 || idSpinnerPermission == 3 || idSpinnerPermission == 4) {
            int issuedInteger = Integer.valueOf(issued);
            if (issuedInteger > currentBalance) {
                ETIssued.requestFocus();
                ETIssued.setError(getString(R.string.error_issued_balance));
                return;
            }
        }

        if (intentDailyMovement == null) {
            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)) {
                incomings = Integer.parseInt(incoming);
            }
            int issueds = 0;
            if (!TextUtils.isEmpty(issued)) {
                issueds = Integer.parseInt(issued);
            }
            if (idSpinnerStore == idSpinnerConvertTo) {
                SPConvertToDaily.requestFocus();
                SPConvertToDaily.setError(getString(R.string.error_same_store));
                return;
            }
            DailyMovements itemSaveDaily = new DailyMovements(idSpinnerCategory,idSpinnerStore,idSpinnerPermission,incomings,issueds);
            itemSaveDaily.setCreatedAt(getDate());
            itemSaveDaily.setTime(getTime());
            if (idSpinnerConvertTo > 0)
            itemSaveDaily.setConvertTo(idSpinnerConvertTo);
            if (itemSaveDaily == null) {
                Toast.makeText(getContext(), getString(R.string.error_save_daily), Toast.LENGTH_LONG).show();
            } else {
                viewModel.insertDailyMovement(itemSaveDaily);
               // dbHelperDailyMovement.addDailyMovements(itemSaveDaily);
                Toast.makeText(getContext(), getString(R.string.save_daily), Toast.LENGTH_LONG).show();
                dialogDailyMovement.dismiss();
            }
        } else {
//            DailyMovements itemUpdateDialy = new DailyMovements();
//            itemUpdateDialy.setId(intentDailyMovement.getInt(IDDaily));
//            itemUpdateDialy.setId_permission_id(idSpinnerPermission);
//            itemUpdateDialy.setId_code_store(idSpinnerStore);
//            itemUpdateDialy.setId_code_category(idSpinnerCategory);

            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)) {
                incomings = Integer.parseInt(incoming);
            }
           // itemUpdateDialy.setIncoming(Integer.valueOf(incomings));
            int issueds = 0;
            if (!TextUtils.isEmpty(issued)) {
                issueds = Integer.parseInt(issued);
            }
//            itemUpdateDialy.setIssued(Integer.valueOf(issueds));
//            itemUpdateDialy.setId_convert_to(idSpinnerConvertTo);
//
//            if (itemUpdateDialy != null) {
                viewModel.updateDailyMovement(intentDailyMovement.getInt(IDDaily),idSpinnerPermission,idSpinnerCategory,idSpinnerStore,idSpinnerConvertTo
                ,incomings,issueds,getDate());
              //  dbHelperDailyMovement.updateDailyMovements(itemUpdateDialy);
                Toast.makeText(getContext(), getString(R.string.update_daily), Toast.LENGTH_LONG).show();
                dialogDailyMovement.dismiss();
//
//            } else {
//                Toast.makeText(getContext(), getString(R.string.error_update_daily), Toast.LENGTH_LONG).show();
//            }
        }
    }

    public void deleteDaily() {
        if (intentDailyMovement != null) {
            String incoming = ETIncoming.getText().toString();
            String issued = ETIssued.getText().toString();


            ItemsStore itemDeleteDaily = new ItemsStore();
            itemDeleteDaily.setId(intentDailyMovement.getInt(IDDaily));
            int incomings = 0;
            if (!TextUtils.isEmpty(incoming)) {
                incomings = Integer.parseInt(incoming);
            }
            itemDeleteDaily.setIncoming(Integer.valueOf(incomings));

            int issueds = 0;
            if (!TextUtils.isEmpty(issued)) {
                issueds = Integer.parseInt(issued);
            }
            itemDeleteDaily.setIssued(Integer.valueOf(issueds));

            isLastRow = dbHelperDailyMovement.isLastRow();

            if (isLastRow != intentDailyMovement.getInt(IDDaily)) {
                Toast.makeText(getContext(), getString(R.string.this_movement_not_allow), Toast.LENGTH_SHORT).show();
                return;
            }

            if (itemDeleteDaily != null) {
                viewModel.deleteDailyMovement(intentDailyMovement.getInt(IDDaily));
                //dbHelperDailyMovement.deleteDailyMovements(itemDeleteDaily);
                Toast.makeText(getContext(), getString(R.string.delete_daily), Toast.LENGTH_LONG).show();
                dialogDailyMovement.dismiss();
                dialogDeleteDailyMovement.dismiss();
            } else {
                Toast.makeText(getContext(), getString(R.string.error_delete_daily), Toast.LENGTH_LONG).show();
            }
        } else {
            // Toast.makeText(getActivity(), "Not Data For Deleted", Toast.LENGTH_LONG).show();
            return;
        }

    }

    class CheckZero implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                if (Integer.parseInt(s.toString()) < 1)
                    s.delete(0, s.length());
                //   s.replace(0,s.length(),"1");
            } catch (NumberFormatException e) {

            }

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
                deleteDaily();

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
        dialogDeleteDailyMovement = builder.create();
        dialogDeleteDailyMovement.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public void loadSpinnerDataForCategory() {
        final Observer<List<String>> categoryName = itemCategory -> {
            ArrayAdapter<String> arrayAdapterNameCategory = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemCategory);
            SPNameCategoryDaily.setAdapter(arrayAdapterNameCategory);

        };
        viewModel.getAllNameCategoriesLiveData().observe(this, categoryName);
    }

    public void loadSpinnerDataForStores() {
        final Observer<List<String>> nameStores = itemStores -> {
            ArrayAdapter<String> arrayAdapterStore = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemStores);
            SPTypeStoreDaily.setAdapter(arrayAdapterStore);
            SPConvertToDaily.setAdapter(arrayAdapterStore);

        };
        viewModel.getAllNameStoresLiveData().observe(this, nameStores);
    }

    public void loadSpinnerDataForNamePermissions() {
        final Observer<List<String>> permissionObserver = itemsPermissions -> {
            if (itemsPermissions.size() > 0) {
                ArrayAdapter<String> arrayAdapterPermission = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemsPermissions);
                SPNamePermisionDaily.setAdapter(arrayAdapterPermission);
            }
        };
        viewModel.getAllNamePermissionsLiveData().observe(this, permissionObserver);

    }
}