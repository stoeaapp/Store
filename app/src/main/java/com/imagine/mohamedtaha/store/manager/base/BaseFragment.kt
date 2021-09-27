package com.imagine.mohamedtaha.store.manager.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.imagine.mohamedtaha.store.manager.*
import javax.inject.Inject

open class BaseFragment :Fragment() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var fragmentNavigationFactory: FragmentNavigationFactory
    protected lateinit var toolbar: HasToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is HasToolbar)toolbar = activity as HasToolbar
    }

}