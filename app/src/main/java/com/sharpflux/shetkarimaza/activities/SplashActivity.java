package com.sharpflux.shetkarimaza.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.filters.VarietyFragment;
import com.sharpflux.shetkarimaza.fragment.FirstFragment;

public class SplashActivity extends AppCompatActivity {


    RelativeLayout spalsh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        spalsh = findViewById(R.id.splash);

        spalsh.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this,WelcomeActivity.class);
                    startActivity(intent);



                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
