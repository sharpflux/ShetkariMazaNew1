package com.sharpflux.shetkarimaza.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.fragment.LoginFragment;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {
    Bundle bundle;
    private EditText enterOTPCodeEt;
    private TextView verifyButton;
    AlertDialog.Builder builder;
    String otp = "", enteredOtp = "",MobileNo="";
    Integer UserId;
    TextView tv_timer1,resendOtpTv;
    MyCountDownTimerotp1 myCountDownTimer1;
    MyCountDownTimer2otp2 myCountDownTimer2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyButton = findViewById(R.id.verifyButton);

        enterOTPCodeEt = findViewById(R.id.enterOTPCodeEt);
        tv_timer1 = findViewById(R.id.tv_timerotp);

        myCountDownTimer1 = new MyCountDownTimerotp1(59000, 1000);
        myCountDownTimer1.start();
        resendOtpTv = findViewById(R.id.resendOtpTv);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            otp = bundle.getString("otp");
            MobileNo = bundle.getString("MobileNo");
            UserId = bundle.getInt("UserId");

        }

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredOtp = enterOTPCodeEt.getText().toString();
                if (!otp.equals(enteredOtp)) {
                    Intent in = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                    in.putExtra("UserId", UserId.toString());
                    startActivity(in);
                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(OtpActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Enter Valid OTP");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });


        resendOtpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RegenerateOTP(bundle.getString("MobileNo"));

                myCountDownTimer1.onFinish();
                RegenerateOTP(MobileNo);


            }
        });

    }



    private void RegenerateOTP(final String ToMobile) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_OTP2+ToMobile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                otp = obj.getString("OTP");
                                myCountDownTimer2 = new MyCountDownTimer2otp2(59000, 1000);
                                myCountDownTimer2.start();

                                myCountDownTimer2.onFinish();




                            } else {
                                builder.setMessage(obj.getString("message"))
                                        .setCancelable(false)

                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();

                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.setTitle("Error");
                                alert.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        builder.setMessage(error.getMessage())
                                .setCancelable(false)

                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle("Error");
                        alert.show();
                        // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("OTPMobileNo", ToMobile);
                params.put("OTPType", "FORGOT");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public class MyCountDownTimerotp1 extends CountDownTimer {
        //public int counter=59;
        public MyCountDownTimerotp1(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


            NumberFormat f = new DecimalFormat("00");
            long hour = (millisUntilFinished / 3600000) % 24;
            long min = (millisUntilFinished / 60000) % 60;
            long sec = (millisUntilFinished / 1000) % 60;

            tv_timer1.setText(f.format(min) + ":" + f.format(sec));
            resendOtpTv.setVisibility(View.GONE);
            // f.format(hour)

        }

        @Override
        public void onFinish() {
            tv_timer1.setText("");
            resendOtpTv.setVisibility(View.VISIBLE);
        }

    }

    public class MyCountDownTimer2otp2 extends CountDownTimer {
        public int counter=59;
        public MyCountDownTimer2otp2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


            NumberFormat f = new DecimalFormat("00");
            long hour = (millisUntilFinished / 3600000) % 24;
            long min = (millisUntilFinished / 60000) % 60;
            long sec = (millisUntilFinished / 1000) % 60;

            tv_timer1.setText(f.format(min) + ":" + f.format(sec));
            resendOtpTv.setVisibility(View.GONE);

        }

        @Override
        public void onFinish() {
            tv_timer1.setText("");

            resendOtpTv.setVisibility(View.VISIBLE);

        }

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
                Intent intent = new Intent(getApplicationContext(), TabLayoutLogRegActivity.class);
                startActivity(intent);
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


