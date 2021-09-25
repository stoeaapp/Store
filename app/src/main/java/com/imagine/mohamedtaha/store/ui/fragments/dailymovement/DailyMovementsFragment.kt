package com.imagine.mohamedtaha.store.ui.fragments.dailymovement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.imagine.mohamedtaha.store.Constant.ADD_DAILY_MOVEMENT
import com.imagine.mohamedtaha.store.Constant.CONVERT_TO_DAILY
import com.imagine.mohamedtaha.store.Constant.DAILY_MOVEMENTS
import com.imagine.mohamedtaha.store.Constant.DELETE_DAILY_MOVEMENT
import com.imagine.mohamedtaha.store.Constant.DIALOG_DAILY_MOVEMENTS
import com.imagine.mohamedtaha.store.Constant.INCOMING_DAILY
import com.imagine.mohamedtaha.store.Constant.ISSUED_DAILY
import com.imagine.mohamedtaha.store.Constant.NAME_ITEM
import com.imagine.mohamedtaha.store.Constant.NAME_PERMISSION
import com.imagine.mohamedtaha.store.Constant.TYPE_STORE
import com.imagine.mohamedtaha.store.Constant.UPDATE_DAILY_MOVEMENT
import com.imagine.mohamedtaha.store.MainFragment
import com.imagine.mohamedtaha.store.StoreApplication
import com.imagine.mohamedtaha.store.databinding.FragmentDailyMovementsBinding
import com.imagine.mohamedtaha.store.manager.base.BaseFragment
import com.imagine.mohamedtaha.store.room.StoreViewModel
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory
import com.imagine.mohamedtaha.store.room.data.DailyMovements
import com.imagine.mohamedtaha.store.room.data.ShowDailyMovements
import com.imagine.mohamedtaha.store.ui.fragments.dailymovement.adapter.AdapterAddDailyMovements
import com.imagine.mohamedtaha.store.util.OnRecyclerItemClick
import java.util.*

class DailyMovementsFragment : BaseFragment() {
    private lateinit var binding: FragmentDailyMovementsBinding
    private lateinit var viewModel: StoreViewModel

    private var itemsDaily = ArrayList<ShowDailyMovements>()
    private var adapterAddDailyMovements: AdapterAddDailyMovements? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = StoreViewModelFactory((requireActivity().application as StoreApplication).repository).create(StoreViewModel::class.java)
        viewModel.allDailyMovement.observe(this, { dailyMovement ->
            binding.progressBarDaily.visibility = View.GONE
            if (dailyMovement.isNotEmpty()) {
                binding.recycleViewDailyMovements.visibility = View.VISIBLE
                binding.emptyViewMainActivityRelative.visibility = View.GONE
                adapterAddDailyMovements?.swapData(dailyMovement)
            } else {
                binding.recycleViewDailyMovements.visibility = View.GONE
                binding.emptyViewMainActivityRelative.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentDailyMovementsBinding.inflate(inflater, container, false)
        binding.recycleViewDailyMovements.layoutManager = LinearLayoutManager(requireActivity())
        adapterAddDailyMovements = AdapterAddDailyMovements(object : OnRecyclerItemClick<ShowDailyMovements> {
            override fun onClick(t: ShowDailyMovements, view: View) {
                val bundle = Bundle()
                bundle.putString(NAME_PERMISSION, t.permissionName)
                bundle.putString(TYPE_STORE, t.typeStore)
                bundle.putString(NAME_ITEM, t.categoryName)
                bundle.putInt(INCOMING_DAILY, t.incoming!!)
                bundle.putInt(ISSUED_DAILY, t.issued!!)
                bundle.putString(CONVERT_TO_DAILY, t.convertTo)
                val f = EditDailyMovementsFragment()
                f.arguments = bundle
                f.show(childFragmentManager, DIALOG_DAILY_MOVEMENTS)
            }

        }, itemsDaily)
        binding.recycleViewDailyMovements.adapter = adapterAddDailyMovements
        binding.recycleViewDailyMovements.adapter = adapterAddDailyMovements
        binding.fabDaily.setOnClickListener { EditDailyMovementsFragment().show(childFragmentManager, MainFragment.DIALOG_DALIY_MOVEMENTS) }
        //Callback from edit fragment
        childFragmentManager.setFragmentResultListener(DIALOG_DAILY_MOVEMENTS, requireActivity(), { _: String?,result: Bundle ->
            val getKey = result.getString(DAILY_MOVEMENTS)
            Log.d("iddd", "getKey : $getKey")
            when (getKey) {
                ADD_DAILY_MOVEMENT -> {
                    val addStockWarehouse = result.getSerializable(ADD_DAILY_MOVEMENT) as DailyMovements?
                    viewModel.insertDailyMovement(addStockWarehouse!!)
                }
                UPDATE_DAILY_MOVEMENT -> {
                    val updateStockWarehouse = result.getSerializable(UPDATE_DAILY_MOVEMENT) as DailyMovements?
                    viewModel.updateDailyMovement(updateStockWarehouse?.id!!, updateStockWarehouse.permissionId, updateStockWarehouse.categoryId, updateStockWarehouse.storeId, updateStockWarehouse.convertTo!!, updateStockWarehouse.incoming, updateStockWarehouse.issued, updateStockWarehouse.updatedAt.toString())
                }
                DELETE_DAILY_MOVEMENT -> {
                    val id = result.getLong(DELETE_DAILY_MOVEMENT)
                    viewModel.deleteDailyMovement(id)
                }
            }
        })
        return binding.root
    }
}