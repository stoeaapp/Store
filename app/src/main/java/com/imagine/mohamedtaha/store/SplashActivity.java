package com.imagine.mohamedtaha.store;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private static boolean splashLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!splashLoad){
            setContentView(R.layout.activity_splash);
            int splashTime = 3000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            },splashTime);
            splashLoad = true;

        }else {
            Intent goToMainActivity = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(goToMainActivity);
            finish();
        }
    }
}
