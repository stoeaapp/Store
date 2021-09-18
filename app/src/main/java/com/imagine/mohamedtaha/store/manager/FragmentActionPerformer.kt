package com.imagine.mohamedtaha.store.manager

import android.os.Bundle

interface FragmentActionPerformer<T> {
    fun add(toBackStack:Boolean)
    fun add(toBackStack: Boolean,tag:String)
    fun replace(toBackStack: Boolean)
    fun replace(toBackStack: Boolean,tag: String)
    fun setBundle(bundle: Bundle): FragmentActionPerformer<*>
    fun clearHistory(tag: String): FragmentActionPerformer<*>

}