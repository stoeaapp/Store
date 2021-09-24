package com.imagine.mohamedtaha.store.ui.fragments.dailymovement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.imagine.mohamedtaha.store.Constant.CONVERT_TO_DAILY
import com.imagine.mohamedtaha.store.Constant.DIALOG_DAILY_MOVEMENTS
import com.imagine.mohamedtaha.store.Constant.IDDaily
import com.imagine.mohamedtaha.store.Constant.INCOMING_DAILY
import com.imagine.mohamedtaha.store.Constant.ISSUED_DAILY
import com.imagine.mohamedtaha.store.Constant.NAME_ITEM
import com.imagine.mohamedtaha.store.Constant.NAME_PERMISSION
import com.imagine.mohamedtaha.store.Constant.TYPE_STORE
import com.imagine.mohamedtaha.store.MainFragment
import com.imagine.mohamedtaha.store.StoreApplication
import com.imagine.mohamedtaha.store.adapter.AdapterAddDailyMovements
import com.imagine.mohamedtaha.store.databinding.FragmentDailyMovementsBinding
import com.imagine.mohamedtaha.store.manager.base.BaseFragment
import com.imagine.mohamedtaha.store.room.StoreViewModel
import com.imagine.mohamedtaha.store.room.StoreViewModelFactory
import com.imagine.mohamedtaha.store.room.data.ShowDailyMovements
import java.util.*

class DailyMovementsFragment : BaseFragment() {
    private lateinit var binding: FragmentDailyMovementsBinding
    private lateinit var viewModel: StoreViewModel

    var itemsDaily = ArrayList<ShowDailyMovements>()
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
        adapterAddDailyMovements = AdapterAddDailyMovements(requireActivity(), itemsDaily)
        binding.recycleViewDailyMovements.adapter = adapterAddDailyMovements

        binding.recycleViewDailyMovements.addOnItemTouchListener(AdapterAddDailyMovements.RecycleTouchListener(requireActivity(),
                binding.recycleViewDailyMovements, object : AdapterAddDailyMovements.ClickListener {
            override fun onClick(view: View, position: Int) {
                val (id, categoryName, permissionName, typeStore, incoming, issued, convertTo) = itemsDaily[position]
                val bundle = Bundle()
                bundle.putString(IDDaily, id)
                bundle.putString(NAME_PERMISSION, permissionName)
                bundle.putString(TYPE_STORE, typeStore)
                bundle.putString(NAME_ITEM, categoryName)
                bundle.putInt(INCOMING_DAILY, incoming!!)
                bundle.putInt(ISSUED_DAILY, issued!!)
                bundle.putString(CONVERT_TO_DAILY, convertTo)
                val f = EditDailyMovementsFragment()
                f.arguments = bundle
                f.show(requireActivity().supportFragmentManager, DIALOG_DAILY_MOVEMENTS)
            }

            override fun onLongClick(view: View, position: Int) {}
        }))
        binding.recycleViewDailyMovements.adapter = adapterAddDailyMovements
        binding.fabDaily.setOnClickListener { EditDailyMovementsFragment().show(requireActivity().supportFragmentManager, MainFragment.DIALOG_DALIY_MOVEMENTS) }
        return binding.root
    }
}