package com.sharpflux.shetkarimaza.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.LanguageAdapter;
import com.sharpflux.shetkarimaza.model.MyLanguage;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;

import java.util.ArrayList;
import java.util.Locale;

public class SelectLanguageActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<MyLanguage> employees = new ArrayList<>();
    private LanguageAdapter adapter;
    private TextView btnGetSelected;
    Button spinner;
    Locale myLocale;
    String currentLanguage = "en", currentLang;
    User user;
    dbLanguage mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   loadLocate();
        setContentView(R.layout.language_sample_activity);

        currentLanguage = getIntent().getStringExtra(currentLang);

        /*spinner = findViewById(R.id.spinner);

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLang();
            }
        });*/
        mydatabase = new dbLanguage(getApplicationContext());

        this.btnGetSelected = findViewById(R.id.btnGetSelected);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Cursor res = mydatabase.getAllData();
        /*if(res.getCount()>0){
            Intent intent = new Intent(SelectLanguageActivity.this, HomeActivity.class);
            startActivity(intent);
        }*/
        /*mydatabase.LanguageInsert("en");
        setLocale("en");*/

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new LanguageAdapter(this, employees);
        recyclerView.setAdapter(adapter);

        createList();

        adapter.checkedPosition=0;


        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected() != null) {

                    showToast(adapter.getSelected().getLanguageName());

                    if(adapter.getSelected().getLanguageName().equals("English"))
                    {
                        mydatabase.LanguageInsert("en");
                        setLocale("en");
                        recreate();
                    }

                    else if(adapter.getSelected().getLanguageName().equals("हिन्दी"))
                    {
                        mydatabase.LanguageInsert("hi");
                        setLocale("hi");
                        recreate();
                    }
                    else if(adapter.getSelected().getLanguageName().equals("मराठी"))
                    {
                        mydatabase.LanguageInsert("mr");
                        setLocale("mr");
                        recreate();
                    }


                } else {
                         showToast("No Selection");
                    }


            }
        });




    }

    private void createList() {
        employees = new ArrayList<>();

        employees.add(new MyLanguage("English"));
        employees.add(new MyLanguage("हिन्दी"));
        employees.add(new MyLanguage("मराठी"));

        adapter.setEmployees(employees);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void showChangeLang() {

        final String[] listItmes = {"English", "Marathi", "Hindi"};

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

            Intent refresh = new Intent(SelectLanguageActivity.this, HomeActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);


            finish();
           /* SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
            finish();*/

        } else {
            Toast.makeText(SelectLanguageActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }




}
