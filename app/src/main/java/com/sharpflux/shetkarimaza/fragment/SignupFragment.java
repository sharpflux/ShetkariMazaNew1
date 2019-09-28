package com.sharpflux.shetkarimaza.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hbb20.CountryCodePicker;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ChooseActivity;
import com.sharpflux.shetkarimaza.activities.TabLayoutLogRegActivity;
import com.sharpflux.shetkarimaza.activities.UserVerificationActivity;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.utils.CheckDeviceIsOnline;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupFragment extends Fragment {

    RelativeLayout signup;
    EditText eusername,edtmiddlename,edtlastname,editTextMobile,epassword,edtcpassword;
    CountryCodePicker ccp;
    String number;
    public SignupFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =inflater.inflate(R.layout.fragment_signup, container, false);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getContext(), TabLayoutLogRegActivity.class));
        }
        eusername=view.findViewById(R.id.eusername);
        edtmiddlename=view.findViewById(R.id.middlename);
        edtlastname=view.findViewById(R.id.lastname);
        editTextMobile=view.findViewById(R.id.emobnum);
        epassword =view.findViewById(R.id.epassword);
        edtcpassword =view.findViewById(R.id.ecpassword);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(editTextMobile);

        signup=view.findViewById(R.id.rSignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CheckDeviceIsOnline.isNetworkConnected(getContext())/*||!CheckDeviceIsOnline.isWifiConnected(SignupActivity.this)*/)

                { AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                registerUser();
            }
        });
        return view;
    }

    private void registerUser() {
        final String username = eusername.getText().toString().trim();
        final String middlename = edtmiddlename.getText().toString().trim();
        final String lastname = edtlastname.getText().toString().trim();
        final String mob = editTextMobile.getText().toString().trim();
        final String password = epassword.getText().toString().trim();
        final String cpassword = edtcpassword.getText().toString().trim();

        number = ccp.getFullNumberWithPlus();

        if (TextUtils.isEmpty(username)) {
            eusername.setError("Please enter username");
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

        if (TextUtils.isEmpty(mob)) {
            editTextMobile.setError("Please enter mobile number");
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



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {

                                User user = new User(
                                        obj.getInt("UserId"),
                                        obj.getString("FullName"),
                                        obj.getString("EmailId"),
                                        obj.getString("MobileNo"),"",""
                                );
                                SharedPrefManager.getInstance(getContext()).userLogin(user);
                                getActivity().finish();
                                startActivity(new Intent(getContext(), UserVerificationActivity.class));
                            } else {
                                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
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

                params.put("UserId","");
                params.put("RegistrationTypeId", "0");
                params.put("RegistrationCategoryId", "0");
                params.put("FullName",username);
                params.put("MobileNo", number);
                params.put("AlternateMobile","0");
                params.put("Address", "");
                params.put("EmailId",lastname);
                params.put("Gender", "0");
                params.put("Address" , "0");
                params.put("StateId", "1");
                params.put("CityId", "1");
                params.put("TahasilId", "1");
                params.put("CompanyFirmName", "0");
                params.put("LandLineNo", "1");
                params.put("APMCLicence", "0");
                params.put("CompanyRegNo","0" );
                params.put("GSTNo", "0");
                params.put("AccountHolderName", "0");
                params.put("BankName", "0");
                params.put("BranchCode","0" );
                params.put("AccountNo", "0");
                params.put("IFSCCode", "0");
                params.put("UploadCancelledCheckUrl","0");
                params.put("UploadAdharCardPancardUrl", "0");
                params.put("ImageUrl","0");
                params.put("UserPassword", password);
                params.put("AgentId", "");

                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
