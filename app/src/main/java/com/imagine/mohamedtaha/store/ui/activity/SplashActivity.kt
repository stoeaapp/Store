package com.imagine.mohamedtaha.store.ui.activity

import android.os.Bundle
import android.view.animation.Animation
import com.imagine.mohamedtaha.store.R
import com.imagine.mohamedtaha.store.databinding.ActivitySplashBinding
import com.imagine.mohamedtaha.store.manager.base.BaseActivity
import com.imagine.mohamedtaha.store.mvvm.interactor.InteractorSplash
import com.imagine.mohamedtaha.store.mvvm.presenter.PresenterSplash
import com.imagine.mohamedtaha.store.mvvm.view.ViewSplash

class SplashActivity : BaseActivity(), ViewSplash {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val presenterSplash: PresenterSplash = InteractorSplash(applicationContext, this)
        presenterSplash.showSplashAnimation()
    }

    override fun goToMainActivity() {
        navigator.loadActivity(MainActivity::class.java).byFinishAll().start()
    }

    override fun startAnimation(animation: Animation) {
        binding.imageSplash.startAnimation(animation)
    }

    override fun findFragmentPlaceHolder() = R.layout.activity_splash

}