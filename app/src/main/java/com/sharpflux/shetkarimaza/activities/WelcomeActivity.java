package com.sharpflux.shetkarimaza.activities;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.ViewPagerAdapter;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;

public class WelcomeActivity extends AppCompatActivity {
    SharedPreferences prefs;
    ViewPager viewPager;
    LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;
    Button btn_skip;
    TextView tvTitleOne,tvDesOne;
    dbLanguage mydatabase;
    String currentLanguage,language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample2);

        mydatabase = new dbLanguage(getApplicationContext());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SliderDots = (LinearLayout) findViewById(R.id.SliderDots);
        tvTitleOne=findViewById(R.id.tvTitleOne);
        tvDesOne=findViewById(R.id.tvDesOne);
        btn_skip= findViewById(R.id.btn_skip);

        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

       /* SharedPreferences myPref = this.getSharedPreferences("prefName", Context.MODE_PRIVATE);
        boolean firstLaunch = myPref.getBoolean("firstLaunch", true);

        if(!firstLaunch){
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
            finish();
        }
        myPref.edit().putBoolean("firstLaunch", false).commit();*/
        CallData();
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
                else {

                    Cursor cursor = mydatabase.LanguageGet(language);
                    if(cursor.getCount()==0) {
                        Intent intent = new Intent(WelcomeActivity.this,SelectLanguageActivity.class);
                        startActivity(intent);
                        finish();

                  /*  Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                    intent.putExtra("ActivityState", "started");
                    startActivity(intent);
                    finish();*/
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(), TabLayoutLogRegActivity.class));
                        finish();
                    }
                }




             /*   Intent i = new Intent(WelcomeActivity.this, SelectLanguageActivity.class);//Activity to be  launched For the First time
                startActivity(i);*/
              //  finish();


               /*SharedPreferences settings = getSharedPreferences("prefs", 0);
                boolean firstRun = settings.getBoolean("firstRun", false);
                if (firstRun == false)//if running for first time
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("firstRun", true);
                    editor.commit();
                    Intent intent = new Intent(WelcomeActivity.this, SelectLanguageActivity.class);//Activity to be  launched For the First time
                    startActivity(intent);
                    finish();
                } else {
                    Intent a = new Intent(WelcomeActivity.this, HomeActivity.class);//Default Activity
                    startActivity(a);
                    finish();
                }*/



            }
        }) ;
    }


    public void CallData(){

        final float startSize = 00; // Size in pixels
        final float endSize = 25;
        final int animationDuration = 2000; // Animation duration in ms

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                tvTitleOne.setTextSize(animatedValue);
                tvDesOne.setTextSize((float) animatedValue/(float)1.5);

            }
        });

        animator.start();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();

        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            SliderDots.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                    dots[i].setContentDescription("Best Quality Ever");
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                if(position==0){
                    tvTitleOne.setText(getResources().getString(R.string.slide1Title));
                    //    tvDesOne.setText("With Fast Transaction ");
                }
                if(position==1){
                    tvTitleOne.setText(getResources().getString(R.string.slide2Title));
                //    tvDesOne.setText("With Fast Transaction ");
                }
                if(position==2){
                    tvTitleOne.setText(getResources().getString(R.string.slide3Title));
                   // tvDesOne.setText("With Fast Transaction ");
                }

                final float startSize = 00; // Size in pixels
                final float endSize = 25;
                final int animationDuration = 500; // Animation duration in ms

                ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
                animator.setDuration(animationDuration);

                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedValue = (float) valueAnimator.getAnimatedValue();
                        tvTitleOne.setTextSize(animatedValue);
                        tvDesOne.setTextSize((float) animatedValue/(float)1.5);

                    }
                });

                animator.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

  /*  @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}