package com.imagine.mohamedtaha.store.manager

import com.imagine.mohamedtaha.store.manager.base.BaseActivity
import com.imagine.mohamedtaha.store.manager.base.BaseFragment

interface Navigator {
    fun <T : BaseFragment>load(tClass: Class<T>): FragmentActionPerformer<T>
    fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder
    fun <T: BaseFragment>loadActivity(aClass: Class<out BaseActivity>, pageTClass: Class<T>): ActivityBuilder
    fun goBack()
    fun finish()
}