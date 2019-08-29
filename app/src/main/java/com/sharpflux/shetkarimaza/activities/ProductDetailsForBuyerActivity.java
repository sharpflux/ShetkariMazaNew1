package com.sharpflux.shetkarimaza.activities;


import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.ViewpagerAdapterForDescription;

public class ProductDetailsForBuyerActivity extends AppCompatActivity {


    ViewPager viewPager;
    LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;
    Button btnCall;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_for_buyer);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SliderDots = (LinearLayout) findViewById(R.id.SliderDots);
        btnCall=findViewById(R.id.btnCall);


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ProductDetailsForBuyerActivity.this, PaymentActivity.class);
                startActivity(in);
            }
        });




        final float startSize = 00; // Size in pixels
        final float endSize = 20;
        final int animationDuration = 2000; // Animation duration in ms

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);



        animator.start();
        ViewpagerAdapterForDescription viewPagerAdapter = new ViewpagerAdapterForDescription(this);

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




                final float startSize = 00; // Size in pixels
                final float endSize = 20;
                final int animationDuration = 500; // Animation duration in ms

                ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
                animator.setDuration(animationDuration);

                animator.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
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
    }


}
