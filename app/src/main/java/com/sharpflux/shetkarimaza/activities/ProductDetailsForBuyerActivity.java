package com.sharpflux.shetkarimaza.activities;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.adapter.TransporterDetailsAdapter;
import com.sharpflux.shetkarimaza.adapter.ViewpagerAdapterForDescription;
import com.sharpflux.shetkarimaza.fragment.CategoryFragment;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.model.TransporterDetails;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailsForBuyerActivity extends AppCompatActivity {


    ViewPager viewPager;
    LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;
    Button btn_call;
    Bundle extras;
    String name,mobileNo,address,state,district,taluka,newAddress;
    TextView tvName,tvAddress,tvMobileNo;

    private View mLoadingView;
    ImageView imgProductDetails,img_buyerDetails;
    private int mAnimationDuration;

    private TableLayout mTableLayout;
    String colSq,colVehicletype,colVehicleNo,colrate;
    JSONArray obj;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    TransporterDetailsAdapter myCategoryTypeAdapter;
    ArrayList<TransporterDetails> categoryList;
    TransporterDetails myCategoryType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_for_buyer);

       // viewPager = (ViewPager) findViewById(R.id.viewPager);
        SliderDots = (LinearLayout) findViewById(R.id.SliderDots);
        btn_call=findViewById(R.id.btn_call);
        tvName=findViewById(R.id.tvName);
        tvAddress=findViewById(R.id.tvAddress);
        tvMobileNo=findViewById(R.id.tvMobileNo);


        mAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);
        imgProductDetails = findViewById(R.id.imgProductDetails);
        mLoadingView = findViewById(R.id.loading_spinner);


        mRecyclerView = findViewById(R.id.rv_transporter);
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        categoryList = new ArrayList<>();



        extras = getIntent().getExtras();
        if (extras != null) {
            new DownloadImageTask((ImageView) findViewById(R.id.imgProductDetails), mLoadingView, mAnimationDuration)
                    .execute(extras.getString("ImageUrl"));

            name = extras.getString("Name");
            mobileNo = extras.getString("MobileNo");
            address = extras.getString("Address");
            state = extras.getString("State");
            district = extras.getString("District");
            taluka = extras.getString("Taluka");

        }

        newAddress=address+" "+state+" "+district+" "+taluka;

        tvName.setText(name);
        tvAddress.setText(" "+newAddress);
        tvMobileNo.setText(mobileNo);


        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(mobileNo));
                getApplicationContext().startActivity(callIntent);
            }
        });


        tvMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("8605121954"));
                getApplicationContext().startActivity(callIntent);
            }
        });

        setDynamicFragmentToTabLayout();

        //view pager code
       /* final float startSize = 00; // Size in pixels
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
*/

       /* ProductDetailsForBuyerActivity.AsyncTaskRunner runner = new ProductDetailsForBuyerActivity.AsyncTaskRunner();
        String sleepTime = "1";
        runner.execute(sleepTime);*/


        //init();
    }

   /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                        super.onScrollStateChanged(recyclerView, newState);
                                    }

                                });*//*
                            }
                          *//*  parentShimmerLayout.stopShimmerAnimation();
                            parentShimmerLayout.setVisibility(View.GONE);*//*

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
//
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }*/






    /*public void  init()  {
        TableLayout stk = (TableLayout) findViewById(R.id.table_transporter);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_TRANSPORTER_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TableLayout stk = (TableLayout) findViewById(R.id.table_transporter);

                        try {
                           obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                     colSq=userJson.getString("TransportId");
                                     colVehicletype=userJson.getString("VehicalType");
                                     colVehicleNo=userJson.getString("VehicalNo");
                                     colrate=userJson.getString("Rate");


                                } else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int j=0;j<obj.length();j++){
                            tv_transporterId.setText(colSq);
                            tv_transporterType.setText(colVehicletype);
                            tv_transporterNo.setText(colVehicleNo);
                            tv_transporterRate.setText(colrate);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }*/
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        private View mContent;
        private View mSpinner;
        private int mDuration;

        public DownloadImageTask(ImageView bmImage, View spinner, int duration) {
            this.bmImage = bmImage;
            mSpinner = spinner;
            mDuration = duration;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

            mSpinner.animate()
                    .alpha(0f)
                    .setDuration(mDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoadingView.setVisibility(View.GONE);
                        }
                    });
        }
    }
    private void setDynamicFragmentToTabLayout() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_TRANSPORTER_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {
                                    myCategoryType = new TransporterDetails
                                            (       userJson.getString("TransportId"),
                                                    userJson.getString("VehicalType"),
                                                    userJson.getString("VehicalNo"),
                                                    userJson.getString("Rate")
                                );





                                    categoryList.add(myCategoryType);
                                } else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myCategoryTypeAdapter = new TransporterDetailsAdapter(getApplicationContext(), categoryList);
                                mRecyclerView.setAdapter(myCategoryTypeAdapter);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onBackPressed() {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        alert.show();*/

        finish();
    }


}
