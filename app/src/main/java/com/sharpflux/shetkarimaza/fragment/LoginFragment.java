package com.sharpflux.shetkarimaza.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;
import com.hbb20.CountryCodePicker;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ChooseActivity;
import com.sharpflux.shetkarimaza.activities.DetailFormActivity;
import com.sharpflux.shetkarimaza.activities.ForgotPasswordActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.activities.MobileVerification;
import com.sharpflux.shetkarimaza.activities.TabLayoutLogRegActivity;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.filters.AgeFragment;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.utils.CheckDeviceIsOnline;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    String number;
    RelativeLayout editTextUsername, editTextPassword, login;
    LinearLayout forgotpw;
    ProgressDialog progressDialog;
    EditText eusername, epassword;
    AlertDialog.Builder builder;
    TabLayout tabLayout;
    ViewPager viewPager;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    User user;
    CountryCodePicker ccp;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        viewPager = ((TabLayoutLogRegActivity)getActivity()).findViewById(R.id.viewPager);
        tabLayout = ((TabLayoutLogRegActivity)getActivity()).findViewById(R.id.tabLayout);
        /*tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        Animation animation1 =
                AnimationUtils.loadAnimation(getContext(),
                        R.anim.fade);

        Animation animation2 =
                AnimationUtils.loadAnimation(getContext(),
                        android.R.anim.slide_in_left);

        if (SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
          //  getActivity().finish();
          //  Intent intent = new Intent(getContext(), DetailFormActivity.class);
          //  intent.putExtra("IsNewUser", "true");
          //  startActivity(intent);

        }
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
        builder = new AlertDialog.Builder(getContext());

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = view.findViewById(R.id.activity_login_etusername);
        editTextPassword = view.findViewById(R.id.activity_login_etPassword);
        forgotpw = view.findViewById(R.id.activity_login_tvForgotPassword);
        login = view.findViewById(R.id.activity_login_rlLogin);
        eusername = view.findViewById(R.id.emobnum);
        epassword = view.findViewById(R.id.password);

        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        //ccp.registerCarrierNumberEditText(eusername);


        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fintent = new Intent(getContext(), ForgotPasswordActivity.class);
                startActivity(fintent);
            }
        });

        //if user presses on login
        //calling the method login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!CheckDeviceIsOnline.isNetworkConnected(getContext())/*||!CheckDeviceIsOnline.isWifiConnected(LoginActivity.this)*/) {

                    builder.setCancelable(false);
                    builder.setTitle("Check Internet Connection!");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("Internet not availlable check your Intrnet Connctivity And Try Again!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user pressed "yes", then he is allowed to exit from application
                            getActivity().finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                } else {
                   // userLogin();


                    number = ccp.getFullNumberWithPlus();
                    if (TextUtils.isEmpty(eusername.getText().toString())) {
                        eusername.setError("Please enter your username");
                        eusername.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(epassword.getText().toString())) {
                        epassword.setError("Please enter your password");
                        epassword.requestFocus();
                        return;
                    }


                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    String sleepTime = "1";
                    runner.execute(sleepTime);

                }

            }
        });


        return view;
    }

  /*  class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0]) * 1000;
                userLogin();
                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            // finalResult.setText(result);
        }



        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getContext(),
                    "Loading...",
                    "Wait for result..");
        }



        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }
*/
    private void userLogin() {
        final String username = eusername.getText().toString();
        final String password = epassword.getText().toString();

        number = ccp.getFullNumberWithPlus();
        if (TextUtils.isEmpty(username)) {
            eusername.setError("Please enter your username");
            eusername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            epassword.setError("Please enter your password");
            epassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //  progressBar.setVisibility(View.GONE);
                        try {

                            JSONArray obj = new JSONArray(response);
                            if (obj.length() == 0) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setCancelable(false);
                                builder.setMessage("Invalid Username or Password");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                customDialogLoadingProgressBar.dismiss();
                                return;
                            }

                            for (int i = 0; i < obj.length(); i++) {
                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    if( userJson.getBoolean("IsVerified")) {
                                        user = new User(
                                                userJson.getInt("UserId"),
                                                userJson.getString("FullName"),
                                                userJson.getString("EmailId"),
                                                userJson.getString("MobileNo"), "", "", ""
                                                , String.valueOf(userJson.getInt("RegistrationTypeId")),
                                                userJson.getBoolean("IsProfileComplete"),
                                                userJson.getBoolean("IsVerified")
                                        );
                                        SharedPrefManager.getInstance(getContext()).userLogin(user);
                                        startActivity(new Intent(getContext(), HomeActivity.class));
                                        getActivity().finish();
                                    }
                                    else {

                                        Intent intent = new Intent(getContext(), MobileVerification.class);
                                        intent.putExtra("MobileNo",  userJson.getString("MobileNo"));
                                        intent.putExtra("UserId", String.valueOf( userJson.getInt("UserId")));
                                        intent.putExtra("Otp",userJson.getString("Otp"));
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setCancelable(false);
                                    builder.setMessage("Invalid Username or Password");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //if user pressed "yes", then he is allowed to exit from application
                                            dialog.cancel();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    customDialogLoadingProgressBar.dismiss();

                                }


                            }
                            customDialogLoadingProgressBar.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            customDialogLoadingProgressBar.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(false);
                        builder.setMessage("Invalid Username or Password");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if user pressed "yes", then he is allowed to exit from application
                                dialog.cancel();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        customDialogLoadingProgressBar.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", username);
                params.put("UserPassword", password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0]) * 1000;
                userLogin();
                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {

        }


        @Override
        protected void onPreExecute() {
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
            customDialogLoadingProgressBar.show();
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }


}
