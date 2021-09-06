package com.imagine.mohamedtaha.store.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.databinding.ActivitySplashBinding;
import com.imagine.mohamedtaha.store.mvvm.interactor.InteractorSplash;
import com.imagine.mohamedtaha.store.mvvm.presenter.PresenterSplash;
import com.imagine.mohamedtaha.store.mvvm.view.ViewSplash;

public class SplashActivity extends AppCompatActivity implements ViewSplash {
    private ActivitySplashBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PresenterSplash presenterSplash = new InteractorSplash(getApplicationContext(), this);
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
        binding.imageSplash.startAnimation(animation);
    }
}
