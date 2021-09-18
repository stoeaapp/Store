package com.imagine.mohamedtaha.store.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.imagine.mohamedtaha.store.R
import com.imagine.mohamedtaha.store.databinding.ActivitySplashBinding
import com.imagine.mohamedtaha.store.manager.ActivityStarter
import com.imagine.mohamedtaha.store.manager.FragmentHandler
import com.imagine.mohamedtaha.store.manager.FragmentNavigationFactory
import com.imagine.mohamedtaha.store.manager.Navigator
import com.imagine.mohamedtaha.store.manager.base.BaseActivity
import com.imagine.mohamedtaha.store.manager.base.BaseFragment
import com.imagine.mohamedtaha.store.mvvm.interactor.InteractorSplash
import com.imagine.mohamedtaha.store.mvvm.presenter.PresenterSplash
import com.imagine.mohamedtaha.store.mvvm.view.ViewSplash
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class SplashActivity : BaseActivity(), ViewSplash {
    private lateinit var binding: ActivitySplashBinding
  //  lateinit var navigaftionFactory: FragmentNavigationFactory
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
     //   navigator(this)
      //  activityStarter = ActivityStarter(this)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val presenterSplash: PresenterSplash = InteractorSplash(applicationContext, this)
        presenterSplash.showSplashAnimation()
    }

    override fun goToMainActivity() {
        navigator.loadActivity(MainActivity::class.java).byFinishAll().start()
//        val goToMainActivity = Intent(this@SplashActivity, MainActivity::class.java)
//        startActivity(goToMainActivity)
//        finish()
    }

    override fun startAnimation(animation: Animation) {
        binding.imageSplash.startAnimation(animation)
    }

    override fun findFragmentPlaceHolder() = R.layout.activity_splash

}