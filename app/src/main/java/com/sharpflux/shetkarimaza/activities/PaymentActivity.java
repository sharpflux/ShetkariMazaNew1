package com.sharpflux.shetkarimaza.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.sharpflux.shetkarimaza.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class PaymentActivity extends Activity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    private CircleImageView circleImageView_razopPay;
    //https://s3.amazonaws.com/rzp-mobile/images/rzp.png

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        circleImageView_razopPay = findViewById(R.id.circleImageView_razopPay);
        Glide.with(PaymentActivity.this).load("https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                .placeholder(R.drawable.kisanmaza).into(circleImageView_razopPay);
        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            Bundle bundle = getIntent().getExtras();
            int amount =0;
            int amount_in_rs=0;
            if(bundle!=null)
            {
                JSONObject options = new JSONObject();
                options.put("name", "Razorpay Corp");
                options.put("description", "Demoing Charges");
                //You can omit the image option to fetch the image from dashboard
                //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

                options.put("currency", "INR");
                options.put("theme.color", "#7EAA18");


                amount = bundle.getInt("Amount");
                amount_in_rs = amount*100;
                options.put("amount", amount_in_rs);

                co.setKeyID("rzp_live_CMT7eyRNO7UCnQ");
            co.open(activity, options);
            }
/*
            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9527643352");

            options.put("prefill", preFill);
*/


        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}

