package com.sharpflux.shetkarimaza.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.filters.VarietyFragment;
import com.sharpflux.shetkarimaza.fragment.LoginFragment;
import com.sharpflux.shetkarimaza.utils.CommonUtils;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnResetPassword;
    private EditText etMobileNo;
    AlertDialog.Builder  builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnResetPassword=findViewById(R.id.btnResetPassword);
        etMobileNo=findViewById(R.id.etMobileNo);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getOtp();
            }
        });
    }


    private void getOtp() {
        //first getting the values
        final String mobNo =  etMobileNo.getText().toString();

        if (!CommonUtils.isValidPhone(mobNo)) {
            etMobileNo.setError("Invalid Mobile number");
            return;
        }
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_RESETPASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            //converting response to json object
                            final JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {

                                if (obj.getString("message").equals("Success")) {
                                    builder.setMessage("Password Sucessfully updated")
                                            .setCancelable(false)

                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //  Action for 'NO' Button
                                                 Intent i = new Intent(ForgotPasswordActivity.this,LoginFragment.class);
                                                    try {
                                                        i.putExtra("otp",obj.getString("OTPMobileNo"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    try {
                                                        i.putExtra("UserId",obj.getInt("UserId"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    startActivity(i);
                                                }
                                            });

                                    AlertDialog alert = builder.create();
                                    alert.setTitle("Success");
                                    alert.show();
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
                        /*builder.setMessage(error.getMessage())
                                .setCancelable(false)

                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle("Error");
                        alert.show();*/
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("otp", "OTPMobileNo");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
