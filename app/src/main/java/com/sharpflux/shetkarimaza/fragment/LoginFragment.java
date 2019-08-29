package com.sharpflux.shetkarimaza.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hbb20.CountryCodePicker;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ChooseActivity;
import com.sharpflux.shetkarimaza.activities.ForgotPasswordActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.customviews.CustomProgressDialog;
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
    CountryCodePicker ccp;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Animation animation1 =
                AnimationUtils.loadAnimation(getContext(),
                        R.anim.fade);

        Animation animation2 =
                AnimationUtils.loadAnimation(getContext(),
                        android.R.anim.slide_in_left);

        if (SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getContext(), ChooseActivity.class));
        }

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = view.findViewById(R.id.activity_login_etusername);
        editTextPassword = view.findViewById(R.id.activity_login_etPassword);
        forgotpw = view.findViewById(R.id.activity_login_tvForgotPassword);
        login = view.findViewById(R.id.activity_login_rlLogin);
        eusername = view.findViewById(R.id.emobnum);
        epassword = view.findViewById(R.id.password);

        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(eusername);

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

                userLogin();


            }
        });


        return view;
    }

    private void userLogin() {
        //first getting the values
        final String username = eusername.getText().toString();
        final String password = epassword.getText().toString();

        number = ccp.getFullNumberWithPlus();
        //validating inputs
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
        CustomProgressDialog.showSimpleProgressDialog(getContext(),
                "Logging In...", "", false);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        //  progressBar.setVisibility(View.GONE);
                        try {

                            CustomProgressDialog.removeSimpleProgressDialog();
                            //converting response to json object
                            //JSONObject obj = new JSONObject(response);
                            JSONArray obj = new JSONArray(response);
                            //if no error in response
                            //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < obj.length(); i++) {
                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    User user = new User(
                                            userJson.getInt("UserId"),
                                            userJson.getString("FullName"),
                                            userJson.getString("EmailId"),
                                            userJson.getString("MobileNo"),"",""

                                    );
                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getContext()).userLogin(user);
                                    //starting the profile activity
                                    getActivity().finish();
                                    startActivity(new Intent(getContext(), HomeActivity.class));
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", number);
                params.put("UserPassword", password);
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
