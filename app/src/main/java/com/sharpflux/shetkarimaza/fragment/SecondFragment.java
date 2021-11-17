package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.DetailFormActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class SecondFragment extends DialogFragment {
    EditText hideStateId, hideDistrictId,hideTalukaId;
    EditText address, city, edtdistrict,edttaluka, edtstate, companyname, license, companyregnno, gstno,name_botanical;
    ArrayList<Product> list;
    LinearLayout btn_next;
    Bundle bundle;
    String name = "", registrationTypeId = "", registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "",SubCategroyTypeId="";
    DataFetcher fetcher;
    private CustomRecyclerViewDialog customDialog;
    Product sellOptions;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    Locale myLocale;
    dbLanguage mydatabase;
    String currentLanguage,language;
    TextInputLayout TICompany,textLayoutAddress;
    User user;

    public String IsNewUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_second, container, false);

        myLocale = getResources().getConfiguration().locale;
        mydatabase = new dbLanguage(getContext());

        list = new ArrayList<Product>();

        btn_next =   (view.findViewById(R.id.secondbtnnext));

        address = view.findViewById(R.id.edtaddress);
        textLayoutAddress=view.findViewById(R.id.textLayoutAddress);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        ((DetailFormActivity) getActivity()).setActionBarTitle(getString(R.string.otherDetails));

        edtstate = view.findViewById(R.id.edtstate);
        edtdistrict = view.findViewById(R.id.edtdistrict);
        edttaluka=view.findViewById(R.id.edttaluka);
        city = view.findViewById(R.id.edtcity);
        name_botanical = view.findViewById(R.id.name_botanical);

        companyname = view.findViewById(R.id.edtcompanyname);
        license = view.findViewById(R.id.edtlicense);
        companyregnno = view.findViewById(R.id.edtcompanyregno);
        gstno = view.findViewById(R.id.edtgstno);

        TICompany = (TextInputLayout)view.findViewById(R.id.TICompany);
        IsNewUser="0";

        hideStateId = view.findViewById(R.id.hideStateId);
        hideDistrictId = view.findViewById(R.id.hideDistrictId);
        hideTalukaId = view.findViewById(R.id.hideTalukaId);

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

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

            }
        }

        bundle = getArguments();

        if (bundle != null) {
            name = bundle.getString("Name");
            registrationTypeId = bundle.getString("RegistrationTypeId");
            registrationCategoryId = bundle.getString("RegistrationCategoryId");
            gender = bundle.getString("Gender");
            mobile = bundle.getString("Mobile");
            alternateMobile = bundle.getString("AlternateMobile");
            email = bundle.getString("Email");
            SubCategroyTypeId= bundle.getString("SubCategroyTypeId");

            IsNewUser = bundle.getString("IsNewUser");

            if(registrationTypeId.contains("32")){
               // companyname.setHint("Nursery Name");
                TICompany.setHint("Nursery Name");
                textLayoutAddress.setHint("Nursery Address");
                companyregnno.setText("0");
                gstno.setText("0");
                license.setText("0");
                license.setVisibility(View.GONE);
                gstno.setVisibility(View.GONE);
                companyregnno.setVisibility(View.GONE);
            }
            else if(registrationTypeId.contains("36") || registrationTypeId.contains("37")){
                // companyname.setHint("Nursery Name");
                TICompany.setHint("Nursery Name");
                TICompany.setVisibility(View.GONE);
                textLayoutAddress.setHint("Address");
                companyregnno.setText("0");
                gstno.setText("0");
                license.setText("0");
                license.setVisibility(View.GONE);
                gstno.setVisibility(View.GONE);
                companyregnno.setVisibility(View.GONE);
            }

        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String addresss = address.getText().toString();

                String state = edtstate.getText().toString();
                String district= edtdistrict.getText().toString();

                String taluka = edttaluka.getText().toString();
                String village= city.getText().toString();

                String firmname = companyname.getText().toString();
                String apmc = license.getText().toString();

                String companyreg = companyregnno.getText().toString();
                String gst= gstno.getText().toString();


                if (TextUtils.isEmpty(addresss)) {
                    address.setError("Please enter your address");
                    address.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(state)) {
                    edtstate.setError("Please select your state");
                    edtstate.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(district)) {
                    edtdistrict.setError("Please select your district");
                    edtdistrict.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(taluka)) {
                    edttaluka.setError("Please select your taluka");
                    edttaluka.requestFocus();
                    return;
                }

              /*  if (TextUtils.isEmpty(village)) {
                    city.setError("Please enter your village");
                    city.requestFocus();
                    return;
                }*/
                if (TextUtils.isEmpty(firmname)) {
                    firmname="0";
                }

                if (TextUtils.isEmpty(apmc)) {
                    apmc="0";
                }

                if (TextUtils.isEmpty(companyreg)) {
                    companyreg="0";
                }

                 if (TextUtils.isEmpty(gst)) {
                  //  gstno.setError("Please enter your gst no");
                    //gstno.requestFocus();
                   // return;

                     gst="0";
                }


                Bundle bundle = new Bundle();
                bundle.putString("RegistrationTypeId", registrationTypeId);
                bundle.putString("RegistrationCategoryId", registrationCategoryId);
                bundle.putString("Name", name);
                bundle.putString("Gender", gender);
                bundle.putString("Mobile", mobile);
                bundle.putString("AlternateMobile", alternateMobile);
                bundle.putString("Email", email);
                bundle.putString("address", address.getText().toString());
                bundle.putString("city","0" ); //city.getText().toString()
                bundle.putString("TalukaId", hideTalukaId.getText().toString());
                bundle.putString("district", edtdistrict.getText().toString());
                bundle.putString("districtId", hideDistrictId.getText().toString());
                bundle.putString("state", edtstate.getText().toString());
                bundle.putString("stateId", hideStateId.getText().toString());
                bundle.putString("companyname", firmname);
                bundle.putString("license", apmc);
                bundle.putString("companyregnno", companyreg);
                bundle.putString("gstno",gst);//gstno.getText().toString()
                bundle.putString("IsNewUser", IsNewUser);
                bundle.putString("SubCategroyTypeId", SubCategroyTypeId);

                FragmentTransaction transection =getActivity().getSupportFragmentManager().beginTransaction();
                transection.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
               // ThirdFragment mfragment = new ThirdFragment();
                SelfieFragment mfragment = new SelfieFragment();
                mfragment.setArguments(bundle); //data being send to SecondFragment
                transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
                transection.addToBackStack("SelfieFragment");
                transection.commit();


            }
        });

        fetcher = new DataFetcher(sellOptions, customDialog, list, getContext(),name_botanical);
        edtstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "state";
                runner.execute(sleepTime);

            }
        });

        edtdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "district";
                runner.execute(sleepTime);
            }

        });
        edttaluka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "taluka";
                runner.execute(sleepTime);
            }

        });

        if (IsNewUser.equals("true")) {

        } else if (IsNewUser.equals("false")) {
            SecondFragment.AsyncTaskRunner runner = new SecondFragment.AsyncTaskRunner();
            String sleepTime = "userdetails";
            runner.execute(sleepTime);
        }



        return view;

    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                if (params[0].toString() == "state")
                    fetcher.loadList("StatesName", edtstate, URLs.URL_STATE + "?StatesID=15&Language="+currentLanguage, "StatesID", hideStateId, "", "","State","",null,null,customDialogLoadingProgressBar);
                   // fetcher.loadList("StatesName", edtstate, URLs.URL_STATE, "StatesID", hideStateId, "", "");

                else if (params[0].toString() == "district")
                    fetcher.loadList("DistrictName", edtdistrict, URLs.URL_DISTRICT + hideStateId.getText()+"," + "&Language="+currentLanguage, "DistrictId", hideDistrictId, "", "","District","",null,null,customDialogLoadingProgressBar);

                else if (params[0].toString() == "taluka")
                    fetcher.loadList("TalukaName", edttaluka, URLs.URL_TALUKA + hideDistrictId.getText()+"," + "&Language="+currentLanguage, "TalukasId", hideTalukaId, "", "","Taluka","",null,null,customDialogLoadingProgressBar);
                else if (params[0].toString() == "userdetails")
                {
                    GetUserDetails();
                }

                Thread.sleep(100);

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
           // progressDialog.dismiss();
            // finalResult.setText(result);
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

    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setMessage("Do you want to leave this page ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                //finish();
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }
    private void GetUserDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_REGISTRATIONGETUSERDETAILS + "UserId="+user.getId() +"&Language=" + currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);


                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    if(!userJson.getString("CompanyFirmName").equals("0"))
                                        companyname.setText(userJson.getString("CompanyFirmName"));


                                    if(!userJson.getString("Address").equals("0"))
                                        address.setText(userJson.getString("Address"));

                                    if(!userJson.getString("StatesName").equals("0"))
                                        edtstate.setText(userJson.getString("StatesName"));


                                    if(!userJson.getString("DistrictName").equals("0"))
                                        edtdistrict.setText(userJson.getString("DistrictName"));

                                    if(!userJson.getString("TalukaName").equals("0"))
                                        edttaluka.setText(userJson.getString("TalukaName"));

                                    if(!userJson.getString("TalukaName").equals("0"))
                                        city.setText(userJson.getString("TalukaName"));

                                    if(!userJson.getString("APMCLicence").equals("0"))
                                        license.setText(userJson.getString("APMCLicence"));


                                    if(!userJson.getString("CompanyRegNo").equals("0"))
                                        companyregnno.setText(userJson.getString("CompanyRegNo"));

                                    if(!userJson.getString("GSTNo").equals("0") && !userJson.getString("GSTNo").equals("null") )
                                        gstno.setText(userJson.getString("GSTNo"));




                                    hideDistrictId.setText(String.valueOf(userJson.getInt("CityId")));
                                    hideStateId.setText(String.valueOf(userJson.getInt("StateId")));
                                    hideTalukaId.setText(String.valueOf(userJson.getInt("TalukaId")));



                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
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
                        customDialogLoadingProgressBar.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}
