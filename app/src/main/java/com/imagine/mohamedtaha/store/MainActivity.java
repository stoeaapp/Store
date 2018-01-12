package com.imagine.mohamedtaha.store;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imagine.mohamedtaha.store.adapter.AdapterAddDailyMovements;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.fragments.EditDailyMovementsFragment;
//import com.imagine.mohamedtaha.store.fragments.EditStoreFragment;
import com.imagine.mohamedtaha.store.informationInrto.TapTarget;
import com.imagine.mohamedtaha.store.informationInrto.TapTargetSequence;
import com.imagine.mohamedtaha.store.informationInrto.TapTargetView;
import com.imagine.mohamedtaha.store.loaders.LoaderDailyMovements;

import java.util.ArrayList;
import java.util.Collections;

import tourguide.tourguide.TourGuide;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>,SearchView.OnQueryTextListener {
    private static final int Daily_LOADER = 4;
    TaskDbHelper dbHelper ;
    ArrayList<ItemsStore>itemsDaily = new ArrayList<>();
    private ProgressBar progressBarDaily;
    //private AdapterAddDailyMovements adapterAddDailyMovements;

    private AdapterAddDailyMovements adapterAddDailyMovements;

    private RecyclerView recyclerViewDailyMovements;
    public static final String IDDaily = "id";
    public static final String NAME_PERMISSION_DAILY = "namePermission";
    public static final String TYPE_STORE_DAILY = "typeStore";
    public static final String NAME_CATEGORY_DAILY = "nameCategory";
    public static final String CONVERT_TO_DAILY = "convert_to";
    public static final String INCOMING_DAILY = "incoming";
    public static final String ISSUED_DAILY = "issued";

    public static final String DIALOG_DALIY_MOVEMENTS = "dialogDaliy";

    View emptView;

    TextView convertTo ;
    TextView TVConvertToShow;
    TourGuide tourGuide;
    View homeButton;
    public Activity mActivity;
    Toolbar toolbar;

    private static boolean showInformation =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if (!showInformation){
            showInformation();
            showInformation = true;
        }*/
        dbHelper = new TaskDbHelper(this);
        recyclerViewDailyMovements = (RecyclerView)findViewById(R.id.recycleViewDailyMovements);
        progressBarDaily = (ProgressBar)findViewById(R.id.progressBarDaily);
        recyclerViewDailyMovements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterAddDailyMovements = new AdapterAddDailyMovements(getApplicationContext(),itemsDaily);
          emptView = findViewById(R.id.empty_view_main_activity_relative);
      //  emptView = (TextView)findViewById(R.id.empty_view_category);


        convertTo = (TextView)findViewById(R.id.TVConvertTo);
         TVConvertToShow = (TextView)findViewById(R.id.TVConvertToShow);


        recyclerViewDailyMovements.addOnItemTouchListener(new AdapterAddDailyMovements.RecycleTouchListener(getApplicationContext(),
                recyclerViewDailyMovements, new AdapterAddDailyMovements.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemsStore itemsStore = itemsDaily.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(IDDaily, itemsStore.getId());
                bundle.putString(NAME_PERMISSION_DAILY, itemsStore.getNamePermission());
                bundle.putString(TYPE_STORE_DAILY, itemsStore.getTypeStore());
                bundle.putString(NAME_CATEGORY_DAILY, itemsStore.getNameGategory());
                bundle.putInt(INCOMING_DAILY, itemsStore.getIncoming());
                bundle.putInt(ISSUED_DAILY, itemsStore.getIssued());
                bundle.putString(CONVERT_TO_DAILY, itemsStore.getConvertTo());
                EditDailyMovementsFragment f = new EditDailyMovementsFragment();
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(),DIALOG_DALIY_MOVEMENTS);
     }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerViewDailyMovements.setAdapter(adapterAddDailyMovements);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDaily);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditDailyMovementsFragment().show(getSupportFragmentManager(),DIALOG_DALIY_MOVEMENTS);
            }
        });

        getSupportLoaderManager().initLoader(Daily_LOADER,null,this);
        final  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.inflateMenu(R.menu.menu_main);




    }
    public void showInformation(){

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
                        TapTarget.forToolbarMenuItem(toolbar,R.id.add_data,"هذا الزر يقوم بالإضافة","وهو يقوم بإضافة الأصناف والمخازن والإذونات")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(3),
                        //Note Add StkoeWearhouse
                        TapTarget.forToolbarMenuItem(toolbar,R.id.add_stocking_warehouse,"هذا الزر يقوم بترصيد المستودع","وعن طريقه يتم إضافة جميع الأصناف والكميات الموجودة في المستودع داخل التطبيق")
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
        if (id == R.id.add_data){
            Intent intent = new Intent(MainActivity.this,ActivityForIncludeFragments.class);
            startActivity(intent);

        }if (id == R.id.add_stocking_warehouse){
            Intent intent = new Intent(MainActivity.this,StockingWarehouse.class);
            startActivity(intent);

        }
        if (id == R.id.add_reportes){
              Intent intent = new Intent(MainActivity.this,ReportStokeFragment.class);
            startActivity(intent);
          /*  ReportStokeFragment fragment = new ReportStokeFragment();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contaner,fragment);
            transaction.commit();*/
        }
        if (id == R.id.add_reportes_daily){
            Intent intent = new Intent(MainActivity.this,ReportDailyMovements.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
        return new LoaderDailyMovements(getApplicationContext(),itemsDaily,dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        if (data.isEmpty()){
            //recyclerViewDailyMovements.setVisibility(View.GONE);
            progressBarDaily.setVisibility(View.GONE);
//            emptView.setVisibility(View.VISIBLE);
        }else {
            recyclerViewDailyMovements.setVisibility(View.VISIBLE);
            adapterAddDailyMovements.swapData(data);
            progressBarDaily.setVisibility(View.GONE);
            emptView.setVisibility(View.GONE);

           /* if (CONVERT_TO_DAILY !=null){
                convertTo.setVisibility(View.VISIBLE);
                TVConvertToShow.setVisibility(View.VISIBLE);

            }
*/
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
        adapterAddDailyMovements.swapData(Collections.<ItemsStore>emptyList());
        adapterAddDailyMovements.notifyItemChanged(recyclerViewDailyMovements.indexOfChild(emptView));

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
         itemsDaily = dbHelper.getAllDailyMovementsByNamePermission(newText);
        if (itemsDaily !=null){
            adapterAddDailyMovements.setFilter(itemsDaily);
          //  getSupportLoaderManager().restartLoader(Daily_LOADER,null,this);

        }
        return false;
    }

}
