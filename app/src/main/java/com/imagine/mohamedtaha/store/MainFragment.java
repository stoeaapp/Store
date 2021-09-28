package com.imagine.mohamedtaha.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.imagine.mohamedtaha.store.ui.fragments.dailymovement.adapter.AdapterAddDailyMovements;
import com.imagine.mohamedtaha.store.data.BackupData;
import com.imagine.mohamedtaha.store.data.ItemsStore;
import com.imagine.mohamedtaha.store.data.TaskDbHelper;
import com.imagine.mohamedtaha.store.databinding.FragmentMainBinding;
import com.imagine.mohamedtaha.store.ui.fragments.dailymovement.EditDailyMovementsFragment;
import com.imagine.mohamedtaha.store.room.data.ShowDailyMovements;
import com.imagine.mohamedtaha.store.ui.fragments.adds.AddsFragment;
import com.imagine.mohamedtaha.store.ui.activity.ReportesActivity;
import com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse.StockingWarehouse;

import java.util.ArrayList;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ItemsStore>>
        , SearchView.OnQueryTextListener, BackupData.OnBackupListener {
    private FragmentMainBinding binding;

    private static final int Daily_LOADER = 4;
    TaskDbHelper dbHelper;
    ArrayList<ShowDailyMovements> itemsDaily = new ArrayList<>();
    //private AdapterAddDailyMovements adapterAddDailyMovements;

    private AdapterAddDailyMovements adapterAddDailyMovements;
    public static final String IDDaily = "id";
    public static final String NAME_PERMISSION_DAILY = "namePermission";
    public static final String TYPE_STORE_DAILY = "typeStore";
    public static final String NAME_CATEGORY_DAILY = "nameCategory";
    public static final String CONVERT_TO_DAILY = "convert_to";
    public static final String INCOMING_DAILY = "incoming";
    public static final String ISSUED_DAILY = "issued";
    public static final String DIALOG_DALIY_MOVEMENTS = "dialogDaliy";
    private BackupData backupData;
    private static boolean showInformation = false;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);

        //inflater.inflate(R.layout.fragment_main, container, false);


        dbHelper = new TaskDbHelper(getActivity());
        backupData = new BackupData(getActivity());
        backupData.setOnBackListener(this);
        binding.recycleViewDailyMovements.setLayoutManager(new LinearLayoutManager(getActivity()));
   //     adapterAddDailyMovements = new AdapterAddDailyMovements(getActivity(), itemsDaily);
        //  emptView = (TextView)findViewById(R.id.empty_view_category);

//
//        binding.recycleViewDailyMovements.addOnItemTouchListener(new AdapterAddDailyMovements.RecycleTouchListener(getActivity(),
//                binding.recycleViewDailyMovements, new AdapterAddDailyMovements.ClickListener() {
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
//                f.show(getActivity().getSupportFragmentManager(), DIALOG_DALIY_MOVEMENTS);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        binding.recycleViewDailyMovements.setAdapter(adapterAddDailyMovements);

        binding.fabDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditDailyMovementsFragment().show(getActivity().getSupportFragmentManager(), DIALOG_DALIY_MOVEMENTS);
            }
        });

        getActivity().getSupportLoaderManager().initLoader(Daily_LOADER, null, this);
//        getActivity().setSupportActionBar(binding.toolbar);


        return binding.getRoot();

    }

//    public void showInformation(){
//
//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        getActivity().setSupportActionBar(toolbar);
//
//        toolbar.inflateMenu(R.menu.menu_main);
//
//               /* toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_back));
//
//        // We load a drawable and create a location to show a tap target here
//        // We need the display to get the width and height at this point in time
//        final Display display = getWindowManager().getDefaultDisplay();
//        // Load our little droid guy
//        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_action_back);
//        // Tell our droid buddy where we want him to appear
//        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
//        // Using deprecated methods makes you look way cool
//        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);
//*/
//        // final SpannableString sassyDesc = new SpannableString("هذا الزر يقوم بالرجوع للصفحة السابقة");
//
//        final TapTargetSequence sequence = new TapTargetSequence(this)
//                .targets(
//                        // This tap target will target the back button, we just need to pass its containing toolbar
//                        //  TapTarget.forToolbarNavigationIcon(toolbar, "", sassyDesc).id(1).outerCircleColor(R.color.colorAccent),
//                        // Likewise, this tap target will target the search button
//                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_search, "هذا زر يستخدم في البحث السريع ", "في صفحة الحركات اليومية وايضاً في صفحة ترصيد المستودع")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black)
//                                // .descriptionTextColor(R.color.backgroundCardView2)
//                                .titleTextSize(18)
//                                .id(2),
//                        //Note Add Store and Permission and Category
//                        TapTarget.forToolbarMenuItem(toolbar,R.id.add_data,"هذا الزر يقوم بالإضافة","وهو يقوم بإضافة الأصناف والمخازن والإذونات")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(3),
//                        //Note Add StkoeWearhouse
//                        TapTarget.forToolbarMenuItem(toolbar,R.id.add_stocking_warehouse,"هذا الزر يقوم بترصيد المستودع","وعن طريقه يتم إضافة جميع الأصناف والكميات الموجودة في المستودع داخل التطبيق")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(4),
//
//                        // You can also target the overflow button in your toolbar
//                        TapTarget.forToolbarOverflow(toolbar, "  هذا الزر به تفاصيل منها", "      التقارير والتي عن طريقها يتم معرفة \n      الأصناف الموجودة داخل المستودع \n     والحركات التي تمت خلال فتره محددة \n     وغيرها من التقارير")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(5)
//                        // This tap target will target our droid buddy at the given target rect
//                      /*  TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
//                                .cancelable(false)
//                                .icon(droid)
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(5)*/
//                        //.id(4)
//                )
//                .listener(new TapTargetSequence.Listener() {
//                    // This listener will tell us when interesting(tm) events happen in regards
//                    // to the sequence
//                    @Override
//                    public void onSequenceFinish() {
//                        //   ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
//                    }
//
//                    @Override
//                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
//                    }
//
//                    @Override
//                    public void onSequenceCanceled(TapTarget lastTarget) {
//                        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
//                                //   .setTitle("انتهاء")
//                                //   .setMessage("لقد قمت بإخفاء التعليمات")
//                                .setPositiveButton("للأسف", null).show();
//                        TapTargetView.showFor(dialog,
//                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "إنتهاء", "لقد قمت بإلغاء التعليمات التي تستعرض شرح التطبيق " /*+ lastTarget.id()*/)
//                                        .cancelable(false)
//                                        .dimColor(android.R.color.black)
//                                        .outerCircleColor(R.color.colorAccent)
//                                        .targetCircleColor(android.R.color.black)
//                                        .transparentTarget(true)
//                                        .textColor(android.R.color.black)
//                                        .tintTarget(false), new TapTargetView.Listener() {
//                                    @Override
//                                    public void onTargetClick(TapTargetView view) {
//                                        super.onTargetClick(view);
//                                        dialog.dismiss();
//                                    }
//                                });
//                    }
//                });
//
//        // You don't always need a sequence, and for that there's a single time tap target
//        final SpannableString spannedDesc = new SpannableString("يقوم بإضافة البيانات في جميع الصفحات ");
//        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "TapTargetView".length(), spannedDesc.length(), 0);
//        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.fabDaily), "هذا الزر ", spannedDesc)
//                .cancelable(false)
//                .drawShadow(true)
//                .outerCircleColor(R.color.colorPrimaryDark)
//                .titleTextDimen(R.dimen.title_text_size)
//                .descriptionTextColor(android.R.color.white)
//                .textColor(android.R.color.white)
//                //.descriptionTextDimen(R.dimen.title_text_size)
//                .tintTarget(false), new TapTargetView.Listener() {
//            @Override
//            public void onTargetClick(TapTargetView view) {
//                super.onTargetClick(view);
//                // .. which evidently starts the sequence we defined earlier
//                sequence.start();
//            }
//
//            @Override
//            public void onOuterCircleClick(TapTargetView view) {
//                super.onOuterCircleClick(view);
//                //  Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
//                Log.d("TapTargetViewSample", "You dismissed me :(");
//            }
//        });
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }
////        if (id == R.id.add_data) {
////            Intent intent = new Intent(getActivity(), AddsFragment.class);
////            startActivity(intent);
////
////        }
////        if (id == R.id.add_stocking_warehouse) {
////            Intent intent = new Intent(getActivity(), StockingWarehouse.class);
////            startActivity(intent);
////
////        }
//        if (id == R.id.reportes) {
//            Intent intent = new Intent(getActivity(), ReportesActivity.class);
//            startActivity(intent);
//          /*  ReportStokeFragment fragment = new ReportStokeFragment();
//            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.contaner,fragment);
//            transaction.commit();*/
//        }
//        if (id == R.id.backup) {
//            backupData.exportToSD();
//            // Toast.makeText(MainActivity.this, "Exoprotdata", Toast.LENGTH_SHORT).show();
//        }
//        if (id == R.id.import_backup) {
//            backupData.importFromSD();
//            // Toast.makeText(MainActivity.this, "Exoprotdata", Toast.LENGTH_SHORT).show();
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<ItemsStore>> onCreateLoader(int id, Bundle args) {
      //  return new LoaderDailyMovements(getActivity(), itemsDaily, dbHelper);
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemsStore>> loader, ArrayList<ItemsStore> data) {
        if (data.isEmpty()) {
            //recyclerViewDailyMovements.setVisibility(View.GONE);
            binding.progressBarDaily.setVisibility(View.GONE);
//            emptView.setVisibility(View.VISIBLE);
        } else {
            binding.recycleViewDailyMovements.setVisibility(View.VISIBLE);
           // adapterAddDailyMovements.swapData(data);
            binding.progressBarDaily.setVisibility(View.GONE);
            binding.emptyViewMainActivityRelative.setVisibility(View.GONE);

           /* if (CONVERT_TO_DAILY !=null){
                convertTo.setVisibility(View.VISIBLE);
                TVConvertToShow.setVisibility(View.VISIBLE);

            }
*/
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemsStore>> loader) {
      //  adapterAddDailyMovements.swapData(Collections.<ItemsStore>emptyList());
        adapterAddDailyMovements.notifyItemChanged(binding.recycleViewDailyMovements.indexOfChild(binding.emptyViewMainActivityRelative));

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
      //  itemsDaily = dbHelper.getAllDailyMovementsByNamePermission(newText);
        if (itemsDaily != null) {
            adapterAddDailyMovements.setFilter(itemsDaily);
            //  getSupportLoaderManager().restartLoader(Daily_LOADER,null,this);

        }
        return false;
    }

    @Override
    public void onFinishExport(String error) {
        String notify = error;
        if (error == null) {
            notify = "تم تصدير قاعدة البيانات بنجاح";
        }
        Toast.makeText(getActivity(), notify, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFinishImport(String error) {
        String notify = error;
        if (error == null) {
            notify = "تم إستيراد قاعدة البيانات بنجاح";
            //   updateListNote();
        }
        Toast.makeText(getActivity(), notify, Toast.LENGTH_SHORT).show();

    }


}