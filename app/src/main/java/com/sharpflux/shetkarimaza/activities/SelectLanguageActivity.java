package com.sharpflux.shetkarimaza.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sharpflux.shetkarimaza.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectLanguageActivity extends AppCompatActivity {

    Button spinner;
    Locale myLocale;
    String currentLanguage = "en", currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   loadLocate();
        setContentView(R.layout.activity_select_language);

        currentLanguage = getIntent().getStringExtra(currentLang);

        spinner = findViewById(R.id.spinner);

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLang();
            }
        });


  /*
        List<String> list = new ArrayList<String>();

        list.add("Select language");
        list.add("English");
        list.add("Marathi");
        list.add("Hindi");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        setLocale("en");
                        break;
                    case 2:
                        setLocale("mr");
                        break;

                    case 3:
                        setLocale("hi");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
*/
        //loadLocate();
    }

    private void showChangeLang() {

        final String [] listItmes = {"English", "Marathi", "Hindi"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language");

        builder.setSingleChoiceItems(listItmes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i == 0) {
                    setLocale("en");
                    recreate();
                } else if (i == 1) {
                    setLocale("mr");
                    recreate();
                } else if (i == 2) {
                    setLocale("hi");
                    recreate();
                }

                dialogInterface.dismiss();

            }
        });



        AlertDialog mDialog = builder.create();

        mDialog.show();

    }



    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this,HomeActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(SelectLanguageActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadLocate() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "");
        setLocale(language);
    }

}
