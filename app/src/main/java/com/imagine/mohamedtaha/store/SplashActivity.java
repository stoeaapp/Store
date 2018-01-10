package com.imagine.mohamedtaha.store;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private static boolean splashLoad = false;
    ImageView imageViewSplash;
    TextView idLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       if (!splashLoad){
           setContentView(R.layout.activity_splash);
           imageViewSplash = (ImageView)findViewById(R.id.imageSplash);
           //  idLogo = (TextView)findViewById(R.id.TVIdLogo);
           Animation animationImage = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_splash);
           //   Animation animationText = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_text);

           //   idLogo.startAnimation(animationText);
           imageViewSplash.startAnimation(animationImage);

            int splashTime = 1750;
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
