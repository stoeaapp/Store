package com.imagine.mohamedtaha.store.manager

import android.os.Bundle
import android.view.View
import com.imagine.mohamedtaha.store.di.scopes.PerActivity
import com.imagine.mohamedtaha.store.manager.base.BaseFragment
import javax.inject.Inject
class FragmentNavigationFactory @Inject constructor(val fragmentHandler: FragmentHandler) {
    private var fragment: BaseFragment? = null
    private var tag: String? = null

    fun <T : BaseFragment> make(aClass: Class<T>): FragmentActionPerformer<T> {
        return make(com.imagine.mohamedtaha.store.manager.FragmentFactory.getFragment(aClass))
    }

    private fun <T : BaseFragment> make(fragment: T?): FragmentActionPerformer<T> {
        this.fragment = fragment
        this.tag = fragment?.javaClass?.simpleName
        return Provider(fragment!!, this)
    }

    private inner class Provider<T : BaseFragment>
    (private val fragment: T, private val navigationFactory: FragmentNavigationFactory) : FragmentActionPerformer<T> {
        var sharedElements: List<Pair<View, String>>? = null
        override fun add(toBackStack: Boolean) {
            navigationFactory.fragmentHandler.openFragment(fragment, FragmentHandler.Option.ADD, toBackStack, tag.toString(), sharedElements)
        }

        override fun add(toBackStack: Boolean, tag: String) {
            navigationFactory.fragmentHandler.openFragment(fragment, FragmentHandler.Option.ADD, toBackStack, tag, sharedElements)
        }

        override fun replace(toBackStack: Boolean) {
            navigationFactory.fragmentHandler.openFragment(fragment, FragmentHandler.Option.REPLACE, toBackStack, tag.toString(), sharedElements)
        }

        override fun replace(toBackStack: Boolean, tag: String) {
            navigationFactory.fragmentHandler.openFragment(fragment, FragmentHandler.Option.REPLACE, toBackStack, tag, sharedElements)
        }

        override fun setBundle(bundle: Bundle): FragmentActionPerformer<*> {
            fragment.arguments = bundle
            return this
        }

        override fun clearHistory(tag: String): FragmentActionPerformer<*> {
            navigationFactory.fragmentHandler.clearFragmentHistory(tag)
            return this
        }

    }
}