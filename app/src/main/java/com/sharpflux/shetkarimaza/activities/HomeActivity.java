package com.sharpflux.shetkarimaza.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.fragment.CategoryFragment;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.AppUtils;
import com.sharpflux.shetkarimaza.utils.CheckDeviceIsOnline;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //private TabLayout mTabLayout;
    Locale myLocale;
    //private ViewPager viewPager;
    //Button saleButton;
    TextView navBarName, navMobileNumber;
   // SearchView searchView;
    Bundle bundleProcessor;
    String language;
    Intent bundleIntent;
    String registrationTypeId="";
    String currentLanguage = "en";
    LinearLayout llHeader;
    public static final String KEY_PREF_LANGUAGE = "pref_language";
    public String languagePref_ID;
    dbLanguage mydatabase;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // setLocale("en");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myLocale = getResources().getConfiguration().locale;


        if (!AppUtils.isLocationEnabled(HomeActivity.this)) {

/*            Intent intent = new Intent(HomeActivity.this, EnableLocationActivity.class);
            startActivity(intent);
            finish();*/

           /* AlertDialog.Builder popupSuccess = new AlertDialog.Builder(HomeActivity.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.custom_dialog_enable_location, viewGroup, false);
            popupSuccess.setView(dialogView);
            AlertDialog alertDialog = popupSuccess.create();
            alertDialog.show();

            dialogView.findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });*/

            // notify user
         android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(HomeActivity.this);
            dialog.setMessage("Location not enabled!");
            dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Toast.makeText(HomeActivity.this, "Cannot Proceed Further...!", Toast.LENGTH_SHORT).show();

                }
            });
            dialog.show();
        }



        mydatabase = new dbLanguage(getApplicationContext());
        Cursor cursor = mydatabase.LanguageGet(language);
        if(cursor.getCount()==0) {
            currentLanguage="en";
        }
        else{
            while (cursor.moveToNext()) {
                currentLanguage = cursor.getString(0);
                if( currentLanguage==null)
                {
                    currentLanguage="en";
                }
                changeLang(cursor.getString(0));
            }
        }



        llHeader = findViewById(R. id.llHeader);

        bundle = getIntent().getExtras();
        if (bundle != null) {
          if(bundle.getInt("UserId")!=0) {
              AlertDialog.Builder popupSuccess = new AlertDialog.Builder(HomeActivity.this);
              ViewGroup viewGroup = findViewById(android.R.id.content);
              View dialogView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.custom_dialog_registration_success, viewGroup, false);

              TextView textViewMsg=(TextView) dialogView.findViewById(R.id.tvSuccessMesg);
              textViewMsg.setText(getResources().getString(R.string.registersuccess) +String.valueOf(bundle.getInt("UserId")));
              popupSuccess.setView(dialogView);
              AlertDialog alertDialog = popupSuccess.create();
              alertDialog.show();

              dialogView.findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      alertDialog.dismiss();
                  }
              });
          }

        }


        if (!CheckDeviceIsOnline.isNetworkConnected(this)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Check Internet Connection!");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage("Internet not availlable check your Intrnet Connctivity And Try Again!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        header = navigationView.getHeaderView(0);


        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {

            Intent intent = new Intent(HomeActivity.this, TabLayoutLogRegActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }


        navBarName = header.findViewById(R.id.navheaderName);
        navMobileNumber = header.findViewById(R.id.navheaderMobile);


        User user = SharedPrefManager.getInstance(this).getUser();

        User user1 = SharedPrefManager.getInstance(new HomeActivity()).getUser();
        language = user.getLanguage();
        navBarName.setText("Hey " + user.getUsername() + "!");
        navMobileNumber.setText("+91" + user.getMobile());


        if(!user.getRegistrationComplete()){
            Intent intent = new Intent(getApplicationContext(), DetailFormActivity.class);
            startActivity(intent);
            finish();
        }

        bundleProcessor=new Bundle();
        bundleIntent=getIntent();

        bundleProcessor = bundleIntent.getExtras();

        if (bundleProcessor != null) {

            registrationTypeId = bundleProcessor.getString("RegistrationTypeId");

        }

        if (!CheckDeviceIsOnline.isNetworkConnected(this)/*||!CheckDeviceIsOnline.isWifiConnected(this)*/)

        { AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Check Internet Connection!");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage("Internet not availlable check your Intrnet Connctivity And Try Again!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }


        CategoryFragment categoryFragment = new CategoryFragment();
        displaySelectedFragment(categoryFragment);
        //initViews();

        /*saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivity.this, SellerActivity.class);
                startActivity(in);
            }
        });*/



    }

    public void changeLang(String lang) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            myLocale = new Locale(lang);
            Locale.setDefault(myLocale);
            Configuration conf = new Configuration(config);
            conf.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application

                    if(Build.VERSION.SDK_INT>=16 && Build.VERSION.SDK_INT<21){
                        finishAffinity();
                    } else if(Build.VERSION.SDK_INT>=21){
                        finishAndRemoveTask();
                    }
                    System.exit(0);

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
 }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_edit) {
            Intent lin = new Intent(HomeActivity.this, EditRequestActivity.class);
            startActivity(lin);
        }

        else if(id ==R.id.nav_prof){
            Intent i = new Intent(HomeActivity.this, DetailFormActivity.class);
            i.putExtra("IsNewUser","false");
            startActivity(i);

        }

        else if (id == R.id.nav_logout) {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();

        }else if (id == R.id.nav_lang) {
            mydatabase.DeleteRecordAll();

          Intent lin = new Intent(HomeActivity.this, SelectLanguageActivity.class);
          startActivity(lin);
        }

        else if (id == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.sharpflux.diapertohome")));
        }
        else if (id == R.id.nav_review) {
            Intent lin = new Intent(HomeActivity.this, ReviewActivity.class);
            startActivity(lin);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }


    public void setLocale(String localeName) {

            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }

}
