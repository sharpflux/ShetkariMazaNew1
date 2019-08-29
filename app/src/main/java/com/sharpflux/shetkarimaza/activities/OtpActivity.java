package com.sharpflux.shetkarimaza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;

public class OtpActivity extends AppCompatActivity {
    Bundle bundle;
    private EditText enterOTPCodeEt;
    private TextView verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyButton = findViewById(R.id.verifyButton);

        enterOTPCodeEt = findViewById(R.id.enterOTPCodeEt);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            enterOTPCodeEt.setText(bundle.getString("otp"));


            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                    startActivity(in);
                }
            });
        }
    }
}


