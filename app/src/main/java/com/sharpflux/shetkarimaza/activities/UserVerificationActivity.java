package com.sharpflux.shetkarimaza.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;


public class UserVerificationActivity extends AppCompatActivity {
    TextView tv_verify,tv_resendOtp;
    EditText edtenterOTP;
    String otp = "", enteredOtp = "";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);

        tv_verify = findViewById(R.id.tv_verify);
        tv_resendOtp = findViewById(R.id.tv_resendOtp);
        edtenterOTP = findViewById(R.id.edtenterOTP);
        builder = new AlertDialog.Builder(this);

        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserVerificationActivity.this,ChooseActivity.class);
                startActivity(i);
            }
        });


    }
}
