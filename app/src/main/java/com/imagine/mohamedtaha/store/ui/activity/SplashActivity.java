package com.imagine.mohamedtaha.store.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.mvvm.interactor.InteractorSplash;
import com.imagine.mohamedtaha.store.mvvm.presenter.PresenterSplash;
import com.imagine.mohamedtaha.store.mvvm.view.ViewSplash;

public class SplashActivity extends AppCompatActivity implements ViewSplash {
    ImageView imageViewSplash;
    TextView idLogo;
    private PresenterSplash presenterSplash;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageViewSplash = (ImageView) findViewById(R.id.imageSplash);
        idLogo = (TextView) findViewById(R.id.TVIdLogo);
        presenterSplash = new InteractorSplash(getApplicationContext(), this);
        presenterSplash.showSplashAnimation();
    }

    @Override
    public void goToMainActivity() {
        Intent goToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(goToMainActivity);
        finish();
    }

    @Override
    public void startAnimation(Animation animation) {
        imageViewSplash.startAnimation(animation);
    }
}
