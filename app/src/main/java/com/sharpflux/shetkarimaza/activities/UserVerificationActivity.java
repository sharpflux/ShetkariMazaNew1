package com.sharpflux.shetkarimaza.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.utils.CommonUtils;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


public class UserVerificationActivity extends AppCompatActivity {
    TextView tv_verify, tv_resendOtp,tv_timer;
    EditText edtenterOTP;
    String enteredOtp = "";
    String FullName = "", Middlename = "", Lastname = "", MobileNo = "", Password = "", Cpassword = "", Otp = "",resendOtp="";
    Bundle bundle;
    AlertDialog.Builder builder;
    ProgressBar progressBar;
    MyCountDownTimer1 myCountDownTimer1;
    MyCountDownTimer2 myCountDownTimer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);

        tv_verify = findViewById(R.id.tv_verify);
        tv_timer = findViewById(R.id.tv_timer);
        tv_resendOtp = findViewById(R.id.tv_resendOtp);
        edtenterOTP = findViewById(R.id.edtenterOTP);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        builder = new AlertDialog.Builder(this);

        myCountDownTimer1 = new MyCountDownTimer1(59000, 1000);
        myCountDownTimer1.start();

        tv_resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(UserVerificationActivity.this,ChooseActivity.class);
                startActivity(i);*/
                bundle = getIntent().getExtras();
                MobileNo = bundle.getString("MobileNo");

                myCountDownTimer1.onFinish();
                RegenerateOTP(MobileNo);



            }
        });

        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(UserVerificationActivity.this,ChooseActivity.class);
                startActivity(i);*/

                registerUser();
                //myCountDownTimer1.onFinish();
            }
        });



    }

    private void registerUser() {
        bundle = getIntent().getExtras();
        FullName = bundle.getString("FullName");
        Middlename = bundle.getString("Middlename");
        Lastname = bundle.getString("Lastname");
        MobileNo = bundle.getString("MobileNo");
        Password = bundle.getString("Password");
        Cpassword = bundle.getString("Cpassword");
        Otp = bundle.getString("OTP");


        enteredOtp = edtenterOTP.getText().toString();

        if (Otp.equals(enteredOtp)||resendOtp.equals(enteredOtp)) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject obj = new JSONObject(response);

                                if (!obj.getBoolean("error")) {

                                    User user = new User(
                                            obj.getInt("UserId"),
                                            obj.getString("FullName"),
                                            obj.getString("EmailId"),
                                            obj.getString("MobileNo"), "", "",""
                                    );
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), DetailFormActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();


                     /* UserId:0
                RegistrationTypeId:0
                RegistrationCategoryId:0
                FullName:SadamHasan
                MobileNo:9340105834
                AlternateMobile:0
                Address:0
                emailid:aks@gmail.com
                StateId:1
                CityId:1
                TahasilId:1
                CompanyFirmName:gdgdg
                LandLineNo:555555
                APMCLicence:727272
                CompanyRegNo:7275272
                GSTNo:287528
                AccountHolderName:fhfhfh
                BankName:bcabca
                BranchCode:55555
                AccountNo:472727
                IFSCCode:35374
                UploadCancelledCheckUrl:0
                UploadAdharCardPancardUrl:0
                UserPassword:123
                Gender:female
                ImageUrl:0
                AgentId:1*/

                    params.put("UserId", "");
                    params.put("RegistrationTypeId", "0");
                    params.put("RegistrationCategoryId", "0");
                    params.put("FullName", FullName);
                    params.put("MobileNo", MobileNo);

                    params.put("AlternateMobile", "0");
                    params.put("Address", "0");
                    params.put("EmailId", Lastname);
                    params.put("Gender", "0");
                    params.put("StateId", "1");

                    params.put("CityId", "1");
                    params.put("TahasilId", "1");
                    params.put("CompanyFirmName", "0");
                    params.put("LandLineNo", "1");
                    params.put("APMCLicence", "0");

                    params.put("CompanyRegNo", "0");
                    params.put("GSTNo", "0");
                    params.put("AccountHolderName", "0");
                    params.put("BankName", "0");
                    params.put("BranchCode", "0");

                    params.put("AccountNo", "0");
                    params.put("IFSCCode", "0");
                    params.put("UploadCancelledCheckUrl", "0");
                    params.put("UploadAdharCardPancardUrl", "0");
                    params.put("ImageUrl", "0");

                    params.put("UserPassword", Password);
                    params.put("AgentId", "");

                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        } else
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(UserVerificationActivity.this);
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


   private void RegenerateOTP(final String ToMobile) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URLs.URL_OTP2+ToMobile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                resendOtp=obj.getString("OTP");

                                myCountDownTimer2 = new MyCountDownTimer2(59000, 1000);
                                myCountDownTimer2.start();

                                myCountDownTimer2.onFinish();
                            }
                            else{
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
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public class MyCountDownTimer1 extends CountDownTimer {
        //public int counter=59;
        public MyCountDownTimer1(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


            NumberFormat f = new DecimalFormat("00");
            long hour = (millisUntilFinished / 3600000) % 24;
            long min = (millisUntilFinished / 60000) % 60;
            long sec = (millisUntilFinished / 1000) % 60;

            tv_timer.setText(f.format(min) + ":" + f.format(sec));
           // f.format(hour)

        }

        @Override
        public void onFinish() {
           tv_timer.setText("");
           tv_resendOtp.setVisibility(View.VISIBLE);
        }

    }

    public class MyCountDownTimer2 extends CountDownTimer {
        public int counter=59;
        public MyCountDownTimer2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


            NumberFormat f = new DecimalFormat("00");
            long hour = (millisUntilFinished / 3600000) % 24;
            long min = (millisUntilFinished / 60000) % 60;
            long sec = (millisUntilFinished / 1000) % 60;

            tv_timer.setText(f.format(min) + ":" + f.format(sec));
            tv_resendOtp.setVisibility(View.GONE);

        }

        @Override
        public void onFinish() {
            tv_timer.setText("");

            tv_resendOtp.setVisibility(View.VISIBLE);

        }

    }

}
