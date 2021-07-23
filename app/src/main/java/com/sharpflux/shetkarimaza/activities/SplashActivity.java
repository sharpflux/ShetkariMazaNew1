package com.sharpflux.shetkarimaza.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {


    RelativeLayout spalsh;
    dbLanguage mydatabase;
    String currentLanguage,language;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        spalsh = findViewById(R.id.splash);
        mydatabase = new dbLanguage(getApplicationContext());




        spalsh.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    SharedPreferences myPref = getSharedPreferences("prefName", Context.MODE_PRIVATE);
                    boolean firstLaunch = myPref.getBoolean("firstLaunch", true);

                    if(!firstLaunch){

                        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else {

                            Cursor cursor = mydatabase.LanguageGet(language);
                            if(cursor.getCount()==0) {
                                Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                                intent.putExtra("ActivityState", "started");
                                finish();
                                startActivity(intent);
                            }
                            else {
                                finish();
                                startActivity(new Intent(getApplicationContext(), TabLayoutLogRegActivity.class));
                            }
                        }


                    }
                    else {
                        myPref.edit().putBoolean("firstLaunch", false).commit();
                        Intent intent = new Intent(SplashActivity.this,WelcomeActivity.class);
                        startActivity(intent);

                    }




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
