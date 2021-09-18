package com.imagine.mohamedtaha.store.manager

import android.view.View
import androidx.annotation.UiThread
import com.imagine.mohamedtaha.store.manager.base.BaseFragment

@UiThread
interface FragmentHandler {
    fun openFragment(baseFragment: BaseFragment, option:Option, isToBaskStack:Boolean, tag:String, sharedElements:List<Pair<View,String>>?)

    /**
     * @param fragmentToShow Fragment to show
     * @param fragmentToHide array of fragments to hide
     */
//    fun showFragment(fragmentToShow:BaseFragment,vararg fragmentToHide:BaseFragment)
    fun clearFragmentHistory(tag:String?)
    enum class Option{
        ADD, REPLACE,SHOW,HIDE,ANIMATION
    }
}