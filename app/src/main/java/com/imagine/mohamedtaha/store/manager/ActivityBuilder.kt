package com.imagine.mohamedtaha.store.manager

import android.os.Bundle
import com.imagine.mohamedtaha.store.manager.base.BaseFragment

interface ActivityBuilder {
    fun start()
    fun addBundle(bundle: Bundle): ActivityBuilder
  //  fun addBundle(bundle: Bundle)
    fun byFinishAll(): ActivityBuilder
    fun forResult(requestCode:Int): ActivityBuilder
    fun <T: BaseFragment>setPage(page: Class<T>): ActivityBuilder
}