package com.imagine.mohamedtaha.store.manager.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.imagine.mohamedtaha.store.manager.*

open class BaseFragment :Fragment() {
    lateinit var navigator: Navigator
    lateinit var fragmentNavigationFactory: FragmentNavigationFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}