package com.imagine.mohamedtaha.store.manager

import android.view.View
import androidx.fragment.app.FragmentManager
import com.imagine.mohamedtaha.store.manager.base.BaseFragment
import javax.inject.Inject
import javax.inject.Named

class FragmentManager @Inject constructor(private val fragmentManager: FragmentManager,
@param:Named("placeholder")private val placeholder: Int):FragmentHandler {
    override fun openFragment(baseFragment: BaseFragment, option: FragmentHandler.Option, isToBaskStack: Boolean, tag: String, sharedElements: List<Pair<View, String>>?) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        when(option){
            FragmentHandler.Option.ADD -> fragmentTransaction.add(placeholder,baseFragment,tag)
            FragmentHandler.Option.REPLACE -> fragmentTransaction.replace(placeholder,baseFragment,tag)
            FragmentHandler.Option.SHOW -> if (baseFragment.isAdded) fragmentTransaction.show(baseFragment)
            FragmentHandler.Option.HIDE -> if (baseFragment.isAdded) fragmentTransaction.hide(baseFragment)
        }
        if (isToBaskStack) fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun clearFragmentHistory(tag: String?) {
        fragmentManager.popBackStackImmediate(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}