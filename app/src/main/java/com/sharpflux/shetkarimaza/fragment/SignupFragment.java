package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hbb20.CountryCodePicker;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.TabLayoutLogRegActivity;
import com.sharpflux.shetkarimaza.activities.UserVerificationActivity;
import com.sharpflux.shetkarimaza.utils.CheckDeviceIsOnline;
import com.sharpflux.shetkarimaza.utils.CommonUtils;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupFragment extends Fragment {

    RelativeLayout signup;
    EditText eusername, edtmiddlename, edtlastname, editTextMobile, epassword, edtcpassword;
    CountryCodePicker ccp;
    String number;
    AlertDialog.Builder builder;

    public SignupFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getContext(), TabLayoutLogRegActivity.class));
        }
        eusername = view.findViewById(R.id.eusername);
        edtmiddlename = view.findViewById(R.id.middlename);
        edtlastname = view.findViewById(R.id.lastname);
        editTextMobile = view.findViewById(R.id.emobnum);
        epassword = view.findViewById(R.id.epassword);
        edtcpassword = view.findViewById(R.id.ecpassword);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(editTextMobile);
        builder = new AlertDialog.Builder(getContext());
        signup = view.findViewById(R.id.rSignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CheckDeviceIsOnline.isNetworkConnected(getContext())/*||!CheckDeviceIsOnline.isWifiConnected(SignupActivity.this)*/) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                }




                number = ccp.getFullNumberWithPlus();

                if (TextUtils.isEmpty(eusername.getText().toString())) {
                    eusername.setError("Please enter your username");
                    eusername.requestFocus();

                    return;
                }


                if (TextUtils.isEmpty(edtmiddlename.getText().toString())) {
                    edtlastname.setError("Please enter your last name");
                    edtlastname.requestFocus();

                    return;
                }

                if (TextUtils.isEmpty(edtlastname.getText().toString())) {
                    editTextMobile.setError("Please enter mobile number");
                    editTextMobile.requestFocus();
                    return;
                }
                if(editTextMobile.getText().toString().length()>10){
                    editTextMobile.setError("Please enter valid mobile number");
                    editTextMobile.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(epassword.getText().toString())) {
                    epassword.setError("Please Enter a password");
                    epassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(edtcpassword.getText().toString())) {
                    edtcpassword.setError("Please Enter a confirm password");
                    edtcpassword.requestFocus();
                    return;
                }
                if (!epassword.getText().toString().equals(edtcpassword.getText().toString())) {
                    edtcpassword.setError("Please enter correct password");
                    edtcpassword.requestFocus();
                    return;
                }



                SignupFragment.AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "1";
                runner.execute(sleepTime);

               /* UserVerificationActivity.MyCountDownTimer m = new UserVerificationActivity.MyCountDownTimer(1000,1000);
                m.start();*/
            }
        });



        return view;
    }





    private void getOtp() {

        final String username = eusername.getText().toString();
        final String middlename = edtmiddlename.getText().toString();
        final String lastname = edtlastname.getText().toString();
        final String mob = editTextMobile.getText().toString();
        final String password = epassword.getText().toString();
        final String cpassword = edtcpassword.getText().toString();

        number = ccp.getFullNumberWithPlus();

        if (TextUtils.isEmpty(username)) {
            eusername.setError("Please enter your username");
            eusername.requestFocus();

            return;
        }

      /*  if (TextUtils.isEmpty(middlename)) {
            edtmiddlename.setError("Please enter middle name");
            edtmiddlename.requestFocus();
            return;
        }*/

        if (TextUtils.isEmpty(lastname)) {
            edtlastname.setError("Please enter your last name");
            edtlastname.requestFocus();

            return;
        }
//
        if (TextUtils.isEmpty(mob)) {
            editTextMobile.setError("Please enter mobile number");
            editTextMobile.requestFocus();
            return;
        }
        if(mob.length()>10){
            editTextMobile.setError("Please enter valid mobile number");
            editTextMobile.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            epassword.setError("Please Enter a password");
            epassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cpassword)) {
            edtcpassword.setError("Please Enter a confirm password");
            edtcpassword.requestFocus();
            return;
        }
        if (!password.equals(cpassword)) {
            edtcpassword.setError("Please enter correct password");
            edtcpassword.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_OTP2 + mob,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {

                                Intent intent = new Intent(getContext(), UserVerificationActivity.class);

                                intent.putExtra("OTP", obj.getString("OTP"));
                                intent.putExtra("FullName", username);
                                intent.putExtra("Middlename", middlename);
                                intent.putExtra("Lastname", lastname);
                                intent.putExtra("MobileNo", mob);
                                intent.putExtra("Password", password);
                                intent.putExtra("Cpassword", cpassword);

                              /*  SharedPrefManager.getInstance(getContext()).userLogin(user);
                                finish();*/

                                startActivity(intent);


                            } else {
                                builder.setMessage("Invalid User")
                                        .setCancelable(false)

                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();

                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.setTitle("User already exists with same Mobile No");
                                alert.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        builder.setMessage(error.getMessage())
                                .setCancelable(false)

                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle("Error");
                        alert.show();
                        // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("OTPMobileNo", mob);
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
                getOtp();
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
            progressDialog = ProgressDialog.show(getContext(),
                    "Loading...",
                    "Wait for result..");
        }


        @Override
        protected void onProgressUpdate(String... text) {


        }

    }
    /*private void getOtp1() {
        //first getting the values
        final String mobNo = editTextMobile.getText().toString();
        final String mob = editTextMobile.getText().toString();
        if (!CommonUtils.isValidPhone(mobNo)) {
            editTextMobile.setError("Invalid Mobile number");
            return;
        }
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_OTP2+mob,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Intent in = new Intent(SignupFragment.this,
                                        UserVerificationActivity.class);
                                in.putExtra("otp", obj.getString("OTP"));
                                startActivity(in);
                            } else {

                                builder.setMessage("Invalid User")
                                        .setCancelable(false)

                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();

                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.setTitle(response);
                                alert.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        builder.setMessage("Invalid User")
                                .setCancelable(false)

                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle(error.getMessage());
                        alert.show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("OTPMobileNo", mob);
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }*/
}
