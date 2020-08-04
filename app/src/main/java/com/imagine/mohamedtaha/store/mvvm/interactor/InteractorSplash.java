package com.imagine.mohamedtaha.store.mvvm.interactor;

import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.mvvm.presenter.PresenterSplash;
import com.imagine.mohamedtaha.store.mvvm.view.ViewSplash;

public class InteractorSplash implements PresenterSplash {
    private static boolean splashLoad = false;
    private static final int splashTime = 1750;
    private Context context;
    private ViewSplash viewSplash;

    public InteractorSplash(Context context, ViewSplash viewSplash) {
        this.context = context;
        this.viewSplash = viewSplash;
    }

    @Override
    public void showSplashAnimation() {
        if (!splashLoad) {

            Animation animationImage = AnimationUtils.loadAnimation(context, R.anim.move_splash);
            //  Animation animationText = AnimationUtils.loadAnimation(context,R.anim.move_text);

            //   idLogo.startAnimation(animationText);
            viewSplash.startAnimation(animationImage);
            //   imageViewSplash.startAnimation(animationImage);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewSplash.goToMainActivity();
                }
            }, splashTime);
            splashLoad = true;

        } else {
            viewSplash.goToMainActivity();
        }
    }
}
