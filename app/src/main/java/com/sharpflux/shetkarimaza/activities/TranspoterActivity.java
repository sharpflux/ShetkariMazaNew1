package com.sharpflux.shetkarimaza.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;

public class TranspoterActivity extends AppCompatActivity {
    EditText editTransporterType,edittransporterRate;
    TextView tv_add_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transpoter);
        editTransporterType = findViewById(R.id.editTransporterType);
        edittransporterRate = findViewById(R.id.edittransporterRate);
        editTransporterType = findViewById(R.id.editTransporterType);

    }
}
