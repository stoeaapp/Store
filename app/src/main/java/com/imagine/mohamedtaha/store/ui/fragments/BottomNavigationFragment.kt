package com.imagine.mohamedtaha.store.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView
import com.imagine.mohamedtaha.store.R
import com.imagine.mohamedtaha.store.data.BackupData
import com.imagine.mohamedtaha.store.data.BackupData.OnBackupListener
import com.imagine.mohamedtaha.store.data.TaskDbHelper
import com.imagine.mohamedtaha.store.databinding.FragmentBottomNavigationBinding
import com.imagine.mohamedtaha.store.manager.base.BaseFragment
import com.imagine.mohamedtaha.store.ui.activity.ActivityForIncludeFragments
import com.imagine.mohamedtaha.store.ui.activity.ReportesActivity
import com.imagine.mohamedtaha.store.ui.fragments.permissions.PermissionsFragment
import com.imagine.mohamedtaha.store.ui.fragments.stockingwarehouse.StockingWarehouse

class BottomNavigationFragment : BaseFragment(), SearchView.OnQueryTextListener, OnBackupListener {
    private lateinit var binding: FragmentBottomNavigationBinding
    var dbHelper: TaskDbHelper? = null

    private var backupData: BackupData? = null
    var toolbar: Toolbar? = null

    private val showInformation = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentBottomNavigationBinding.inflate(layoutInflater, container, false);
//inflater.inflate(R.layout.fragment_bottom_navigation, container, false)

        binding.BottomNavigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.daily_movements -> {
                        run {
                            val manager: FragmentManager = requireActivity().getSupportFragmentManager()
                            val transaction = manager.beginTransaction()
                            transaction.replace(R.id.frameLayout, PermissionsFragment()).commit()
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                        run {
                            val manager: FragmentManager = requireActivity().getSupportFragmentManager()
                            val transaction = manager.beginTransaction()
                            transaction.replace(R.id.frameLayout, PermissionsFragment())
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    }
                    R.id.stockingWarehouse -> {
                        run {
                            val manager: FragmentManager = requireActivity().getSupportFragmentManager()
                            val transaction = manager.beginTransaction()
                            transaction.replace(R.id.frameLayout, PermissionsFragment())
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    }
                    R.id.adds -> {
                        run {
                            val manager: FragmentManager = requireActivity().getSupportFragmentManager()
                            val transaction = manager.beginTransaction()
                            transaction.replace(R.id.frameLayout, PermissionsFragment())
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    }
                    R.id.profile -> {
                    }
                }
                return false
            }
        })
        return binding.root
    }


//    fun showInformation() {
//        toolbar = findViewById<View>(R.id.toolbar) as Toolbar?
//        setSupportActionBar(toolbar)
//        toolbar!!.inflateMenu(R.menu.menu_main)
//
//        /* toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_back));
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
//        val sequence: TapTargetSequence = TapTargetSequence(this)
//                .targets( // This tap target will target the back button, we just need to pass its containing toolbar
//                        //  TapTarget.forToolbarNavigationIcon(toolbar, "", sassyDesc).id(1).outerCircleColor(R.color.colorAccent),
//                        // Likewise, this tap target will target the search button
//                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_search, "هذا زر يستخدم في البحث السريع ", "في صفحة الحركات اليومية وايضاً في صفحة ترصيد المستودع")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black) // .descriptionTextColor(R.color.backgroundCardView2)
//                                .titleTextSize(18)
//                                .id(2),  //Note Add Store and Permission and Category
//                        TapTarget.forToolbarMenuItem(toolbar, R.id.add_data, "هذا الزر يقوم بالإضافة", "وهو يقوم بإضافة الأصناف والمخازن والإذونات")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(3),  //Note Add StkoeWearhouse
//                        TapTarget.forToolbarMenuItem(toolbar, R.id.add_stocking_warehouse, "هذا الزر يقوم بترصيد المستودع", "وعن طريقه يتم إضافة جميع الأصناف والكميات الموجودة في المستودع داخل التطبيق")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(4),  // You can also target the overflow button in your toolbar
//                        TapTarget.forToolbarOverflow(toolbar, "  هذا الزر به تفاصيل منها", "      التقارير والتي عن طريقها يتم معرفة \n      الأصناف الموجودة داخل المستودع \n     والحركات التي تمت خلال فتره محددة \n     وغيرها من التقارير")
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(5) // This tap target will target our droid buddy at the given target rect
//                        /*  TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
//                                .cancelable(false)
//                                .icon(droid)
//                                .dimColor(android.R.color.black)
//                                .outerCircleColor(R.color.colorAccent)
//                                .targetCircleColor(android.R.color.black)
//                                .transparentTarget(true)
//                                .textColor(android.R.color.black).id(5)*/
//                        //.id(4)
//                )
//                .listener(object : TapTargetSequence.Listener {
//                    // This listener will tell us when interesting(tm) events happen in regards
//                    // to the sequence
//                    override fun onSequenceFinish() {
//                        //   ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
//                    }
//
//                    override fun onSequenceStep(lastTarget: TapTarget, targetClicked: Boolean) {
//                        Log.d("TapTargetView", "Clicked on " + lastTarget.id())
//                    }
//
//                    override fun onSequenceCanceled(lastTarget: TapTarget) {
//                        val dialog = AlertDialog.Builder(this@MainActivity) //   .setTitle("انتهاء")
//                                //   .setMessage("لقد قمت بإخفاء التعليمات")
//                                .setPositiveButton("للأسف", null).show()
//                        TapTargetView.showFor(dialog,
//                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "إنتهاء", "لقد قمت بإلغاء التعليمات التي تستعرض شرح التطبيق " /*+ lastTarget.id()*/)
//                                        .cancelable(false)
//                                        .dimColor(android.R.color.black)
//                                        .outerCircleColor(R.color.colorAccent)
//                                        .targetCircleColor(android.R.color.black)
//                                        .transparentTarget(true)
//                                        .textColor(android.R.color.black)
//                                        .tintTarget(false), object : TapTargetView.Listener() {
//                            override fun onTargetClick(view: TapTargetView) {
//                                super.onTargetClick(view)
//                                dialog.dismiss()
//                            }
//                        })
//                    }
//                })
//
//        // You don't always need a sequence, and for that there's a single time tap target
//        val spannedDesc = SpannableString("يقوم بإضافة البيانات في جميع الصفحات ")
//        spannedDesc.setSpan(UnderlineSpan(), spannedDesc.length - "TapTargetView".length, spannedDesc.length, 0)
//        TapTargetView.showFor(this, TapTarget.forView(findViewById<View>(R.id.fabDaily), "هذا الزر ", spannedDesc)
//                .cancelable(false)
//                .drawShadow(true)
//                .outerCircleColor(R.color.colorPrimaryDark)
//                .titleTextDimen(R.dimen.title_text_size)
//                .descriptionTextColor(android.R.color.white)
//                .textColor(android.R.color.white) //.descriptionTextDimen(R.dimen.title_text_size)
//                .tintTarget(false), object : TapTargetView.Listener() {
//            override fun onTargetClick(view: TapTargetView) {
//                super.onTargetClick(view)
//                // .. which evidently starts the sequence we defined earlier
//                sequence.start()
//            }
//
//            override fun onOuterCircleClick(view: TapTargetView) {
//                super.onOuterCircleClick(view)
//                //  Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
//            }
//
//            override fun onTargetDismissed(view: TapTargetView, userInitiated: Boolean) {
//                Log.d("TapTargetViewSample", "You dismissed me :(")
//            }
//        })
//    }

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        layoutInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        if (id == R.id.add_data) {
            val intent = Intent(requireActivity(), ActivityForIncludeFragments::class.java)
            startActivity(intent)
        }
        if (id == R.id.add_stocking_warehouse) {
            val intent = Intent(requireActivity(), StockingWarehouse::class.java)
            startActivity(intent)
        }
        if (id == R.id.reportes) {
            val intent = Intent(requireActivity(), ReportesActivity::class.java)
            startActivity(intent)
            /*  ReportStokeFragment fragment = new ReportStokeFragment();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contaner,fragment);
            transaction.commit();*/
        }
        if (id == R.id.backup) {
            backupData!!.exportToSD()
            // Toast.makeText(MainActivity.this, "Exoprotdata", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.import_backup) {
            backupData!!.importFromSD()
            // Toast.makeText(MainActivity.this, "Exoprotdata", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
//        itemsDaily = dbHelper.getAllDailyMovementsByNamePermission(newText);
//        if (itemsDaily != null) {
//            adapterAddDailyMovements.setFilter(itemsDaily);
//            //  getSupportLoaderManager().restartLoader(Daily_LOADER,null,this);
//        }
        return false
    }

    override fun onFinishExport(error: String?) {
        var notify = error
        if (error == null) {
            notify = "تم تصدير قاعدة البيانات بنجاح"
        }
        Toast.makeText(requireActivity(), notify, Toast.LENGTH_SHORT).show()
    }

    override fun onFinishImport(error: String?) {
        var notify = error
        if (error == null) {
            notify = "تم إستيراد قاعدة البيانات بنجاح"
            //   updateListNote();
        }
        Toast.makeText(requireActivity(), notify, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        //  updateListNote();
    }
}