package com.sharpflux.shetkarimaza.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;

public class OtpActivity extends AppCompatActivity {
    Bundle bundle;
    private EditText enterOTPCodeEt;
    private TextView verifyButton;
    AlertDialog.Builder builder;
    String otp = "", enteredOtp = "";
    Integer UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyButton = findViewById(R.id.verifyButton);

        enterOTPCodeEt = findViewById(R.id.enterOTPCodeEt);



        bundle = getIntent().getExtras();

        if (bundle != null) {
            otp = bundle.getString("otp");
            UserId = bundle.getInt("UserId");

        }

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredOtp = enterOTPCodeEt.getText().toString();
                if (otp.equals(enteredOtp)) {
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
    }
}


