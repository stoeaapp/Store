package com.imagine.mohamedtaha.store.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.adapter.AdapterAddDailyMovements;
import com.imagine.mohamedtaha.store.data.BackupData;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.databinding.ActivityMainBinding;
import com.imagine.mohamedtaha.store.informationInrto.TapTarget;
import com.imagine.mohamedtaha.store.informationInrto.TapTargetSequence;
import com.imagine.mohamedtaha.store.informationInrto.TapTargetView;
import com.imagine.mohamedtaha.store.manager.base.BaseActivity;
import com.imagine.mohamedtaha.store.room.StoreViewModel;
import com.imagine.mohamedtaha.store.room.data.ShowDailyMovements;
import com.imagine.mohamedtaha.store.ui.fragments.BottomNavigationFragment;
import com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse.StockingWarehouse;

import java.util.ArrayList;

import tourguide.tourguide.TourGuide;

//import com.imagine.mohamedtaha.store.fragments.EditStoreFragment;
public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener, BackupData.OnBackupListener {
    private ActivityMainBinding binding;
    private BackupData backupData;
    Toolbar toolbar;

    private static boolean showInformation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        toolbar.inflateMenu(R.menu.menu_main);
        load(BottomNavigationFragment.class).add(true, "");

//        viewModel = new StoreViewModelFactory(((StoreApplication) getApplication()).getRepository()).create(StoreViewModel.class);
//        viewModel.getAllDailyMovement().observe(this, new Observer<List<ShowDailyMovements>>() {
//            @Override
//            public void onChanged(List<ShowDailyMovements> showDailyMovements) {
//                if (showDailyMovements.size() > 0) {
//                    recyclerViewDailyMovements.setVisibility(View.VISIBLE);
//                    adapterAddDailyMovements.swapData(showDailyMovements);
//                    progressBarDaily.setVisibility(View.GONE);
//                    emptView.setVisibility(View.GONE);
//
//                } else {
//                    recyclerViewDailyMovements.setVisibility(View.GONE);
//                    progressBarDaily.setVisibility(View.GONE);
//                    emptView.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        binding.BottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.daily_movements: {
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.frameLayoutMainActivity, new PermissionsFragment());
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                    break;
//
//
//                }
//                case R.id.stockingWarehouse: {
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.frameLayoutMainActivity, new StockingWarehouse());
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                    break;
//                }
//                case R.id.adds: {
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.frameLayoutMainActivity, new ActivityForIncludeFragments());
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                    break;
//
//                }
//                case R.id.profile: {
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.frameLayoutMainActivity, new ProfileFragment());
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                    break;
//                }
//            }
//            return false;
//        });
//
//     /*   if (!showInformation){
//            showInformation();
//            showInformation = true;
//        }*/
//        dbHelper = new TaskDbHelper(this);
//        backupData = new BackupData(this);
//        backupData.setOnBackListener(this);
//        recyclerViewDailyMovements = (RecyclerView) findViewById(R.id.recycleViewDailyMovements);
//        progressBarDaily = (ProgressBar) findViewById(R.id.progressBarDaily);
//        recyclerViewDailyMovements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        adapterAddDailyMovements = new AdapterAddDailyMovements(getApplicationContext(), itemsDaily);
//        emptView = findViewById(R.id.empty_view_main_activity_relative);
//        //  emptView = (TextView)findViewById(R.id.empty_view_category);
//
//
//        convertTo = (TextView) findViewById(R.id.TVConvertTo);
//        TVConvertToShow = (TextView) findViewById(R.id.TVConvertToShow);
//
//
//        recyclerViewDailyMovements.addOnItemTouchListener(new AdapterAddDailyMovements.RecycleTouchListener(getApplicationContext(),
//                recyclerViewDailyMovements, new AdapterAddDailyMovements.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                ShowDailyMovements itemsStore = itemsDaily.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putString(IDDaily, itemsStore.getId());
//                bundle.putString(NAME_PERMISSION_DAILY, itemsStore.getPermissionName());
//                bundle.putString(TYPE_STORE_DAILY, itemsStore.getTypeStore());
//                bundle.putString(NAME_CATEGORY_DAILY, itemsStore.getCategoryName());
//                bundle.putInt(INCOMING_DAILY, itemsStore.getIncoming());
//                bundle.putInt(ISSUED_DAILY, itemsStore.getIssued());
//                bundle.putString(CONVERT_TO_DAILY, itemsStore.getConvertTo());
//                EditDailyMovementsFragment f = new EditDailyMovementsFragment();
//                f.setArguments(bundle);
//                f.show(getSupportFragmentManager(), DIALOG_DALIY_MOVEMENTS);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        recyclerViewDailyMovements.setAdapter(adapterAddDailyMovements);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDaily);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new EditDailyMovementsFragment().show(getSupportFragmentManager(), DIALOG_DALIY_MOVEMENTS);
//            }
//        });
//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        // toolbar.inflateMenu(R.menu.menu_main);


    }

    public void showInformation() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.menu_main);

               /* toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_back));

        // We load a drawable and create a location to show a tap target here
        // We need the display to get the width and height at this point in time
        final Display display = getWindowManager().getDefaultDisplay();
        // Load our little droid guy
        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_action_back);
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);
*/
        // final SpannableString sassyDesc = new SpannableString("هذا الزر يقوم بالرجوع للصفحة السابقة");

        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        //  TapTarget.forToolbarNavigationIcon(toolbar, "", sassyDesc).id(1).outerCircleColor(R.color.colorAccent),
                        // Likewise, this tap target will target the search button
                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_search, "هذا زر يستخدم في البحث السريع ", "في صفحة الحركات اليومية وايضاً في صفحة ترصيد المستودع")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                // .descriptionTextColor(R.color.backgroundCardView2)
                                .titleTextSize(18)
                                .id(2),
                        //Note Add Store and Permission and Category
                        TapTarget.forToolbarMenuItem(toolbar, R.id.add_data, "هذا الزر يقوم بالإضافة", "وهو يقوم بإضافة الأصناف والمخازن والإذونات")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(3),
                        //Note Add StkoeWearhouse
                        TapTarget.forToolbarMenuItem(toolbar, R.id.add_stocking_warehouse, "هذا الزر يقوم بترصيد المستودع", "وعن طريقه يتم إضافة جميع الأصناف والكميات الموجودة في المستودع داخل التطبيق")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(4),

                        // You can also target the overflow button in your toolbar
                        TapTarget.forToolbarOverflow(toolbar, "  هذا الزر به تفاصيل منها", "      التقارير والتي عن طريقها يتم معرفة \n      الأصناف الموجودة داخل المستودع \n     والحركات التي تمت خلال فتره محددة \n     وغيرها من التقارير")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(5)
                        // This tap target will target our droid buddy at the given target rect
                      /*  TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                                .cancelable(false)
                                .icon(droid)
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(5)*/
                        //.id(4)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        //   ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                //   .setTitle("انتهاء")
                                //   .setMessage("لقد قمت بإخفاء التعليمات")
                                .setPositiveButton("للأسف", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "إنتهاء", "لقد قمت بإلغاء التعليمات التي تستعرض شرح التطبيق " /*+ lastTarget.id()*/)
                                        .cancelable(false)
                                        .dimColor(android.R.color.black)
                                        .outerCircleColor(R.color.colorAccent)
                                        .targetCircleColor(android.R.color.black)
                                        .transparentTarget(true)
                                        .textColor(android.R.color.black)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

        // You don't always need a sequence, and for that there's a single time tap target
        final SpannableString spannedDesc = new SpannableString("يقوم بإضافة البيانات في جميع الصفحات ");
        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "TapTargetView".length(), spannedDesc.length(), 0);
        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.fabDaily), "هذا الزر ", spannedDesc)
                .cancelable(false)
                .drawShadow(true)
                .outerCircleColor(R.color.colorPrimaryDark)
                .titleTextDimen(R.dimen.title_text_size)
                .descriptionTextColor(android.R.color.white)
                .textColor(android.R.color.white)
                //.descriptionTextDimen(R.dimen.title_text_size)
                .tintTarget(false), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                // .. which evidently starts the sequence we defined earlier
                sequence.start();
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
                //  Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.add_data) {
            Intent intent = new Intent(MainActivity.this, AddsFragment.class);
            startActivity(intent);

        }
        if (id == R.id.add_stocking_warehouse) {
            Intent intent = new Intent(MainActivity.this, StockingWarehouse.class);
            startActivity(intent);

        }
        if (id == R.id.reportes) {
            Intent intent = new Intent(MainActivity.this, ReportesActivity.class);
            startActivity(intent);
          /*  ReportStokeFragment fragment = new ReportStokeFragment();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contaner,fragment);
            transaction.commit();*/
        }
        if (id == R.id.backup) {
            backupData.exportToSD();
            // Toast.makeText(MainActivity.this, "Exoprotdata", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.import_backup) {
            backupData.importFromSD();
            // Toast.makeText(MainActivity.this, "Exoprotdata", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        itemsDaily = dbHelper.getAllDailyMovementsByNamePermission(newText);
//        if (itemsDaily != null) {
//            adapterAddDailyMovements.setFilter(itemsDaily);
//            //  getSupportLoaderManager().restartLoader(Daily_LOADER,null,this);
//        }
        return false;
    }

    @Override
    public void onFinishExport(String error) {
        String notify = error;
        if (error == null) {
            notify = "تم تصدير قاعدة البيانات بنجاح";
        }
        Toast.makeText(MainActivity.this, notify, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFinishImport(String error) {
        String notify = error;
        if (error == null) {
            notify = "تم إستيراد قاعدة البيانات بنجاح";
            //   updateListNote();
        }
        Toast.makeText(MainActivity.this, notify, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  updateListNote();

    }

    @Override
    public int findFragmentPlaceHolder() {
        return R.id.frameLayout;
    }


    /**
     * select all note from database and set to ls
     * use for loop to add into listNote.
     * We must add all item in ls into listNote then adapter can update
     * we add reverse ls to show new note at top of list
     */
//    private void updateListNote() {
//        // clear old list
//        itemsDaily.clear();
//        // add all notes from database, reverse list
//    ArrayList<ItemsStore> ls = dbHelper.getListNote( "SELECT * FROM " + TaskContract.TaskEntry.TABLE_DAILY_MOVEMENTS);
//
//        // reverse list
//        for (int i = ls.size() - 1; i >= 0; i--) {
//            itemsDaily.add(ls.get(i));
//        }
//
//        adapterAddDailyMovements.notifyDataSetChanged();
//    }


}






















