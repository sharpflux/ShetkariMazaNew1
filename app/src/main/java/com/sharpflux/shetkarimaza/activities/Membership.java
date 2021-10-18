package com.sharpflux.shetkarimaza.activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.CouponAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.model.CouponModel;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Membership extends AppCompatActivity  implements PaymentResultWithDataListener {

    private CouponAdapter couponAdapter;
    private ArrayList<CouponModel> couponModels;
    private RecyclerView recyclerView;
    String[] txt_CouponCode = {"GROCY50", "GROCY10", "GROCY60", "GROCY30"};
    String[] txt_CouponDiscount = {"Get Rs. 50 Off", "Get Rs. 10% Off", "Get Rs. 10% Off", "Get Rs. 50 Off"};
    String[] txt_CouponTitle = {"Get Rs 200 off on purchase of Rs 300 and above", "Get Rs 300 off on purchase of Rs 600 and above", "Get Rs 200 off on your first order", "Get Rs 200 off on purchase of Rs 300 and above"};

    dbLanguage mydatabase;
    String currentLanguage, language;
    RazorpayClient razorpayClient;
    JSONObject orderRequest;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    User user;
    AlertDialog.Builder builder;
    String SelectedPackageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        Checkout.preload(Membership.this);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        SelectedPackageId = "0";
        builder = new AlertDialog.Builder(this);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerCoupon);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.couponModels = new ArrayList<>();

        CouponAdapter couponAdapter2 = new CouponAdapter(this, this.couponModels);
        this.couponAdapter = couponAdapter2;
        this.recyclerView.setAdapter(couponAdapter2);
        mydatabase = new dbLanguage(getApplicationContext());
        Cursor cursor = mydatabase.LanguageGet(language);
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(Membership.this);
        if (cursor.getCount() == 0) {
            currentLanguage = "en";
        } else {
            while (cursor.moveToNext()) {
                currentLanguage = cursor.getString(0);
                if (currentLanguage == null) {
                    currentLanguage = "en";
                }

            }
        }

        loadMore(1);

        user = SharedPrefManager.getInstance(this).getUser();
       // orderAPI("100", "1");


       /* AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("Payment", "1","1");*/


        setTitle("Membership");
    }


    public void orderAPI(final String amount, final String packageId) {


        SelectedPackageId=packageId;
        orderRequest = new JSONObject();
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            razorpayClient = new RazorpayClient("rzp_live_CMT7eyRNO7UCnQ", "Vm9BZntrswJr9jZeChmPb6nr");
            double Paise = Double.parseDouble(amount);
            orderRequest.put("amount", Paise * 100);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt",String.valueOf( user.getId()));
            Order order = razorpayClient.Orders.create(orderRequest);
            orderRequest.put("order_id", order.toJson().getString("id"));
            Checkout checkout = new Checkout();
            JSONObject options = new JSONObject();
            options.put("name", "Kisan Maza");
            options.put("description", "App Charges");
            options.put("image", URLs.Main_URL+"kisanmaza.png");
            options.put("order_id", order.toJson().getString("id"));//from response of step 3.
            options.put("currency", "INR");
            options.put("theme.color", getResources().getColor(R.color.colorPrimary));
            //  options.put("theme.color", getResources().getColor(R.color.black));

            JSONObject preFill = new JSONObject();
            preFill.put("email", user.getEmail());
            preFill.put("contact", user.getMobile());
            options.put("prefill", preFill);

            checkout.setKeyID("rzp_live_CMT7eyRNO7UCnQ");
            checkout.open(Membership.this, options);

        } catch (JSONException | RazorpayException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e("ERROR", "\n\n--------------------------------------\n"
                    + exception.getMessage() + "\n--------------------------------------------\n\n");
        }
    }



    private class AsyncTaskRunner extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(final String... params) {


            if (params[0].equals("Payment")) {
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try  {
                            orderAPI(params[1].toString(), params[2].toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                t.start();

            }

            if (params[0].equals("Membership")) {
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try  {
                          //  saveMembership(params[1].toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                t.start();

            }
            return params[0];
        }

        @Override
        protected void onPreExecute() {
            customDialogLoadingProgressBar.show();
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }



    private void loadMore(final Integer currentPage) {


        customDialogLoadingProgressBar.show();
        if (!currentPage.equals(1)) {
            customDialogLoadingProgressBar.dismiss();
            couponModels.add(null);
            couponAdapter.notifyItemInserted(couponModels.size() - 1);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!currentPage.equals(1)) {
                    couponModels.remove(couponModels.size() - 1);
                    int scrollPosition = couponModels.size();
                    couponAdapter.notifyItemRemoved(scrollPosition);
                    final int currentSize = scrollPosition;
                    final int nextLimit = currentSize + 10;
                }
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                            URLs.URL_PackageMasterGETApi + "?Language=" + currentLanguage + "&RegistrationTypeId=" + "2",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray obj = new JSONArray(response);
                                        for (int i = 0; i < obj.length(); i++) {
                                            JSONObject userJson = obj.getJSONObject(i);
                                            if (!userJson.getBoolean("error")) {
                                                CouponModel sellOptions;
                                                sellOptions = new CouponModel
                                                        (
                                                                 userJson.getString("Amount"),
                                                                userJson.getString("Descriptions"),
                                                                userJson.getString("TotalMonths"),
                                                                userJson.getString("Descriptions"),
                                                                String.valueOf(userJson.getInt("PackageId"))
                                                        );
                                                couponModels.add(sellOptions);

                                            } else {
                                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                            }
                                            couponAdapter.notifyDataSetChanged();

                                        }



                                        customDialogLoadingProgressBar.dismiss();


                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    customDialogLoadingProgressBar.dismiss();

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        }, 500);


    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            customDialogLoadingProgressBar.dismiss();
            saveMembership(paymentData.getPaymentId());
            Payment payment = razorpayClient.Payments.capture(paymentData.getPaymentId(), orderRequest);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }


    void saveMembership(final String TransactionId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_POST_MEMBERSHIP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            customDialogLoadingProgressBar.dismiss();

                            //converting response to json object
                            JSONObject jsonArray = new JSONObject(response);
                            builder.setMessage("Thanks for the payment !")
                                    .setCancelable(false)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert = builder.create();
                            alert.setTitle("Payment Received");
                            alert.show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Membership.this, "Something went wrong POST ! ", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MembershipId", "0");
                params.put("UserId",String.valueOf(user.getId()));
                params.put("PackageId", SelectedPackageId);
                params.put("TransactionId", TransactionId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(Membership.this).addToRequestQueue(stringRequest);
    }
}