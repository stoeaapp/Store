package com.imagine.mohamedtaha.store.di.modules

import com.imagine.mohamedtaha.store.manager.FragmentHandler
import com.imagine.mohamedtaha.store.manager.FragmentManager
import com.imagine.mohamedtaha.store.manager.FragmentNavigationFactory
import com.imagine.mohamedtaha.store.manager.Navigator
import com.imagine.mohamedtaha.store.manager.base.BaseActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class ActivityModule {

    @Binds
    abstract fun navigator(activity: BaseActivity): Navigator

    @Binds
    abstract fun fragmentHandler(fragmentManager: FragmentManager): FragmentHandler
//    @Binds
//    abstract fun fragmentNavigationFactory(fragmentHandler: FragmentNavigationFactory): FragmentHandler

//    @Provides
//    fun fragmentNavigationFactory(fragmentNavigationFactory: FragmentNavigationFactory):FragmentNavigationFactory{
//        return fragmentNavigationFactory
//    }
//    @Provides
//     fun fragmentHandler(fragmentNavigationFactory: FragmentHandler): FragmentHandler {
//        return fragmentNavigationFactory
//    }


}