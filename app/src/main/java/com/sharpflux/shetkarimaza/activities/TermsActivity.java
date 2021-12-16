package com.sharpflux.shetkarimaza.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sharpflux.shetkarimaza.R;

public class TermsActivity extends AppCompatActivity {

    LinearLayout linearclose;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        linearclose=findViewById(R.id.linearclose);
        back=findViewById(R.id.back);

        linearclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsActivity.this, TabLayoutLogRegActivity.class);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsActivity.this, TabLayoutLogRegActivity.class);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}