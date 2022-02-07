package com.sharpflux.shetkarimaza.activities;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.AppUtils;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class SplashActivity extends AppCompatActivity {


    RelativeLayout spalsh;
    dbLanguage mydatabase;
    String currentLanguage,language;


    private static final int TIME_INTERVAL = 5000;
    private static final int PERMISSION_ALL = 1;
    //String[] PERMISSIONS = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE,READ_PHONE_STATE,ACCESS_FINE_LOCATION};

    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};


//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spalsh = findViewById(R.id.splash);
        mydatabase = new dbLanguage(getApplicationContext());

        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_splash);
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }

        spalsh.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));
        checkPermisssions();
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   finish();
    }
    private void checkPermisssions() {

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
          checkLocIsEnabled();
        }
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(SplashActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(SplashActivity.this, WRITE_EXTERNAL_STORAGE);
            int result2 = ContextCompat.checkSelfPermission(SplashActivity.this, READ_PHONE_STATE);
            int result3 = ContextCompat.checkSelfPermission(SplashActivity.this, ACCESS_FINE_LOCATION);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED  && result3 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void checkLocIsEnabled() {
            if (!AppUtils.isLocationEnabled(SplashActivity.this)) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        someActivityResultLauncher.launch(myIntent);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        Toast.makeText(SplashActivity.this, "Cannot Proceed Further...!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                dialog.show();
            } else {
                startTimer();
            }

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        startTimer();
                    }
                }
            });



    public static boolean hasPermissions(Context context, String... permissions) {
        if (SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            startTimer();
        } else {

            GoActivity();

        }
    }


    private  void GoActivity(){
        SharedPreferences myPref = getSharedPreferences("prefName", Context.MODE_PRIVATE);
        boolean firstLaunch = myPref.getBoolean("firstLaunch", true);

    /*    if(!firstLaunch){*/

            if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
            else {

                Cursor cursor = mydatabase.LanguageGet(language);
                if(cursor.getCount()==0) {
                    Intent intent = new Intent(SplashActivity.this,SelectLanguageActivity.class);
                    startActivity(intent);
                    finish();

                  /*  Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                    intent.putExtra("ActivityState", "started");
                    startActivity(intent);
                    finish();*/
                }
                else {
                    startActivity(new Intent(getApplicationContext(), TabLayoutLogRegActivity.class));
                    finish();
                }
            }


       /* }
        else {
            myPref.edit().putBoolean("firstLaunch", false).commit();
            Intent intent = new Intent(SplashActivity.this,WelcomeActivity.class);
            startActivity(intent);

        }*/


    }

    private void startTimer() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GoActivity();
            }
        }, TIME_INTERVAL);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
                Boolean permission = false;
                for (int i = 0; i <= permissions.length - 1; i++) {

                    if (grantResults.length > 0  && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        Log.e("PERMISSION_GRANTED", String.valueOf(grantResults[i]));
                        permission = true;

                    } else {
                        permission = false;
                        Toast.makeText(this, "Permission denied ,cannot proceed Further", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                }

                if (permission) {
                    checkLocIsEnabled();

                    //startTimer();
                }
                return;

            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
