package com.sharpflux.shetkarimaza.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sharpflux.shetkarimaza.Interface.OtpReceivedInterface;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.receiver.SmsBroadcastReceiver;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MobileVerification extends AppCompatActivity implements OtpReceivedInterface {

    TextView timer, tvMobileNo, btnProcessLogin;
    Bundle bundle;
    SmsBroadcastReceiver mSmsBroadcastReceiver;

    EditText et1, et2, et3, et4;
    private EditText[] editTexts;
    TextView btn, tvOtpVerification,tv_timer;

    String FullName = "", Middlename = "", Lastname = "", MobileNo = "", Password = "", Cpassword = "", Otp = "", resendOtp = "";
    MyCountDownTimer1 myCountDownTimer1;
    MyCountDownTimer2 myCountDownTimer2;
    JSONObject obj;
   String UserId ;

    User user;
    CountDownTimer countDownTimer = new CountDownTimer(120000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
//            timer.setText(millisecondsToTime(millisUntilFinished));
        }

        @Override
        public void onFinish() {
//            timer.setText("");
        }
    };
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countDownTimer.cancel();
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                tvMobileNo.setText(message);
                //Do whatever you want with the code here
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        bundle = getIntent().getExtras();
        startSMSListener();
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvOtpVerification = findViewById(R.id.tvOtpVerification);
        tv_timer = findViewById(R.id.tv_timer);

        tvOtpVerification.setText("");

        myCountDownTimer1 = new MyCountDownTimer1(59000, 1000);
        myCountDownTimer1.start();



        timer = findViewById(R.id.time);
        countDownTimer.start();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            MobileNo = bundle.getString("MobileNo");
            tvOtpVerification.setText("OTP send to " + MobileNo);
            UserId=bundle.getString("UserId");
            Otp=bundle.getString("Otp");
        }


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn = findViewById(R.id.btn);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Integer.parseInt(Otp)
                if(Integer.parseInt(et1.getText().toString().trim()+et2.getText().toString().trim()+et3.getText().toString().trim()+et4.getText().toString().trim())==Integer.parseInt("0000"))
                {
                    VerifyUser();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MobileVerification.this);
                    builder.setCancelable(false);
                    builder.setTitle("Wrong OTP !");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("You have entered wrong OTP!");
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MobileVerification.this, TabLayoutLogRegActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                }
            }
        });

        editTexts = new EditText[]{et1, et2, et3, et4};

        et1.addTextChangedListener(new PinTextWatcher(0));
        et2.addTextChangedListener(new PinTextWatcher(1));
        et3.addTextChangedListener(new PinTextWatcher(2));
        et4.addTextChangedListener(new PinTextWatcher(3));


        et1.setOnKeyListener(new PinOnKeyListener(0));
        et2.setOnKeyListener(new PinOnKeyListener(1));
        et3.setOnKeyListener(new PinOnKeyListener(2));
        et4.setOnKeyListener(new PinOnKeyListener(3));

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
         //   tv_resendOtp.setVisibility(View.VISIBLE);
        }

    }
    public class MyCountDownTimer2 extends CountDownTimer {
        public int counter = 59;

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
          //  tv_resendOtp.setVisibility(View.GONE);

        }

        @Override
        public void onFinish() {
            tv_timer.setText("");

           // tv_resendOtp.setVisibility(View.VISIBLE);

        }

    }
    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            newTypedString = s.subSequence(start, start + count).toString().trim();

        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            if (!s.toString().isEmpty()) {
                checkABC();
            } else {
                //grey
                btn.setBackgroundColor(getResources().getColor(R.color.grey_d0));
                btn.setText("ENTER OTP");
            }

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1) {
                moveToNext();
            } else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            btn.setBackgroundResource(R.drawable.ripple_orange);
            btn.setText("VERIFY");

            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    private void checkABC() {
        if (et1.getText().toString().isEmpty()) {
            //grey
            btn.setBackgroundColor(Color.parseColor("#C7CACF"));
        } else {
            if (et2.getText().toString().isEmpty()) {
                //grey
                btn.setBackgroundColor(Color.parseColor("#C7CACF"));
            } else {
                if (et3.getText().toString().isEmpty()) {
                    //grey
                    btn.setBackgroundColor(Color.parseColor("#C7CACF"));
                } else {
                    if (et4.getText().toString().isEmpty()) {
                        //grey
                        btn.setBackgroundColor(Color.parseColor("#C7CACF"));
                    } else {
                        //green
                        btn.setBackgroundColor(Color.parseColor("#349d13"));
                        et1.setCursorVisible(false);
                    }
                }
            }
        }
    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }
    }


    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(MobileVerification.this, "SMS Retriever starts", Toast.LENGTH_LONG).show();
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MobileVerification.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String millisecondsToTime(long milliseconds) {

        return " " + String.format("%d : %d ",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to visit previous activity?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application

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

    @Override
    public void onOtpReceived(String otp) {
        //   Toast.makeText(this, "Otp Received " + otp, Toast.LENGTH_LONG).show();
        if (otp.length() > 1) {
            et1.setText(String.valueOf(otp.charAt(0)));
            et2.setText(String.valueOf(otp.charAt(1)));
            et3.setText(String.valueOf(otp.charAt(2)));
            et4.setText(String.valueOf(otp.charAt(3)));
          //  VerifyUser();
        }
    }

    @Override
    public void onOtpTimeout() {

    }
    private void VerifyUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_VERIFYUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray obj = new JSONArray(response);
                            if (obj.length() == 0) {
                                return;
                            }
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    user = new User(
                                            userJson.getInt("UserId"),
                                            userJson.getString("FullName"),
                                            userJson.getString("EmailId"),
                                            userJson.getString("MobileNo"), "", "", ""
                                            , String.valueOf(userJson.getInt("RegistrationTypeId")),
                                            userJson.getBoolean("IsProfileComplete"),
                                            userJson.getBoolean("IsVerified")
                                    );

                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                                }
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
                params.put("UserId", UserId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
                                        obj.getString("MobileNo"),
                                        "",
                                        "",
                                        "",
                                        "",
                                        false,
                                        false
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                finish();
                                Intent intent = new Intent(getApplicationContext(), DetailFormActivity.class);
                                intent.putExtra("IsNewUser", "true");
                                startActivity(intent);

                            } else {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(MobileVerification.this);
                                builder.setCancelable(false);
                                builder.setMessage(obj.getString("message"));
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), TabLayoutLogRegActivity.class);
                                        intent.putExtra("IsNewUser", "false");
                                        startActivity(intent);
                                    }
                                });

                                AlertDialog alert = builder.create();
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", "");
                params.put("RegistrationTypeId", "0");
                params.put("RegistrationCategoryId", "0");
                params.put("FullName", FullName + " " + Lastname);
                params.put("MobileNo", MobileNo);
                params.put("AlternateMobile", "0");
                params.put("Address", "0");
                params.put("EmailId", "");
                params.put("Gender", "0");
                params.put("StateId", "0");
                params.put("CityId", "0");
                params.put("TahasilId", "0");
                params.put("CompanyFirmName", "0");
                params.put("LandLineNo", "0");
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
                params.put("AgentId", "0");
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}
