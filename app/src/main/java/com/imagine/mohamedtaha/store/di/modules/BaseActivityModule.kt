package com.imagine.mohamedtaha.store.di.modules

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.imagine.mohamedtaha.store.manager.FragmentHandler
import com.imagine.mohamedtaha.store.manager.FragmentNavigationFactory
import com.imagine.mohamedtaha.store.manager.base.BaseActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
object BaseActivityModule {
    @Provides
    fun provideBaseActivity(activity:Activity):BaseActivity{
        check(activity is BaseActivity)
        return activity as BaseActivity
    }
//    @Provides
//    fun fragmentNavigation(fragmentNavigationFactory: FragmentNavigationFactory): FragmentHandler {
//        return fragmentNavigationFactory.fragmentHandler
//    }

//
    @Provides
    internal fun fragmentManager(baseActivity: BaseActivity): FragmentManager {
        return baseActivity.supportFragmentManager
    }

    @Provides
    @Named("placeholder")
    internal fun placeHolder(baseActivity: BaseActivity): Int {
        return baseActivity.findFragmentPlaceHolder()
    }

//
//    @Provides
//    internal fun fragmentHandler(fragmentNavigationFactory: FragmentNavigationFactory): FragmentHandler {
//        return fragmentNavigationFactory.fragmentHandler
//    }
}

