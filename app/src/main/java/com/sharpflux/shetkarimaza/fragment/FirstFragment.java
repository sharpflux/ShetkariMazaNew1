package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.model.MyProcessor;
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


public class FirstFragment extends Fragment {

    EditText Rtype_edit, Rcategory_edit, editfullname, mobileNo, AlternateMobile, Email, name_botanical;

    ArrayList<Product> list;
    private ArrayList<MyProcessor> processorList;
    Locale myLocale;
    private CustomRecyclerViewDialog customDialog;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    DataFetcher fetcher;

    TextView hidRegTypeId, hidRegCagteId;

    MyProcessor myProcessor;
    Product sellOptions;

    RadioGroup rg;
    RadioButton rb1, rb2;
    View view;
    LinearLayout btn_next;
    String gender = "";
    private OnStepOneListener mListener;
    private String UserId, username, usermobileno, useremail;

    dbLanguage mydatabase;
    String currentLanguage, language;

    public static final String ALTERNATE_MOBILE_KEY = "AlternateMobile";

    private String userEmail = "";
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first, container, false);
        Rtype_edit = view.findViewById(R.id.edtRegitype);
        Rcategory_edit = view.findViewById(R.id.edtRegicategory);
        hidRegTypeId = view.findViewById(R.id.hidRegTypeId);
        hidRegCagteId = view.findViewById(R.id.hidRegCagteId);

        rg = view.findViewById(R.id.radioGroup1);
        rb1 = view.findViewById(R.id.radio1);
        rb2 = view.findViewById(R.id.radio2);


        btn_next = (LinearLayout) (view.findViewById(R.id.footer));
        editfullname = view.findViewById(R.id.editfullname);
        mobileNo = view.findViewById(R.id.mobileNo);
        name_botanical = view.findViewById(R.id.name_botanical);

        AlternateMobile = view.findViewById(R.id.AlternateMobile);
        Email = view.findViewById(R.id.Email);
        list = new ArrayList<Product>();

        AlternateMobile.setSaveEnabled(false);

        mydatabase = new dbLanguage(getContext());

        user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        username = user.getUsername();
        usermobileno = user.getMobile();
        useremail = user.getEmail();

        Cursor cursor = mydatabase.LanguageGet(language);

        if (cursor.getCount() == 0) {

        }



        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }


        editfullname.setText(username);
        mobileNo.setText(usermobileno);
        // Email.setText(useremail);

        processorList = new ArrayList<>();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regType = Rtype_edit.getText().toString();
                // String regCategory = Rcategory_edit.getText().toString();
                String rb = Rcategory_edit.getText().toString();
                String alterNo = AlternateMobile.getText().toString();

                String email = Email.getText().toString();


                if (TextUtils.isEmpty(regType)) {
                    Rtype_edit.setError("Please enter your Registration Type");
                    Rtype_edit.requestFocus();
                    return;
                }

              /*  if (TextUtils.isEmpty(regCategory)) {
                    Rcategory_edit.setError("Please enter your quality");
                    Rcategory_edit.requestFocus();
                    return;
                }*/

                //int id = rg.getCheckedRadioButtonId();
                if (!rb1.isChecked() || !rb2.isChecked()) {//Grp is your radio group object
                    rb1.setError("please select Gender");
                    rb1.requestFocus();
                    rb2.setError("please select Gender");
                    rb2.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(alterNo)) {
                    // AlternateMobile.setError("Please enter your Alternate Mobile No.");
                    // AlternateMobile.requestFocus();
                    //return;

                    alterNo = "0";
                }

                if (TextUtils.isEmpty(email)) {
                    email = "0";
                }


                String name = editfullname.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("RegistrationTypeId", hidRegTypeId.getText().toString());
                bundle.putString("RegistrationCategoryId", hidRegCagteId.getText().toString());
                bundle.putString("Name", name);
                bundle.putString("Gender", gender);
                bundle.putString("Mobile", mobileNo.getText().toString());
                bundle.putString("AlternateMobile", alterNo);
                bundle.putString("Email", email);
                FragmentTransaction transection = getFragmentManager().beginTransaction();
                SecondFragment mfragment = new SecondFragment();
                mfragment.setArguments(bundle); //data being send to SecondFragment
                transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
                transection.commit();

            }
        });


        fetcher = new DataFetcher(sellOptions, customDialog, list, getContext(), name_botanical);

        Rtype_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirstFragment.AsyncTaskRunner runner = new FirstFragment.AsyncTaskRunner();
                String sleepTime = "type";
                runner.execute(sleepTime);


            }
        });


        Rcategory_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstFragment.AsyncTaskRunner runner = new FirstFragment.AsyncTaskRunner();
                String sleepTime = "cate";
                runner.execute(sleepTime);
            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rb1 = (RadioButton) view.findViewById(checkedId);
                rb2 = (RadioButton) view.findViewById(checkedId);

                if (rb1 != null) {

                    gender = rb1.getHint().toString();

                    Toast.makeText(getContext(),
                            gender,
                            Toast.LENGTH_LONG).show();
                } else if (rb2 != null) {
                    gender = rb2.getHint().toString();

                    Toast.makeText(getContext(),
                            gender,
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        FirstFragment.AsyncTaskRunner runner = new FirstFragment.AsyncTaskRunner();
        String sleepTime = "userdetails";
        runner.execute(sleepTime);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null) {
            // Save user email instance variable value in bundle.
            outState.putString(this.ALTERNATE_MOBILE_KEY, AlternateMobile.getText().toString());

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            // Retrieve the user email value from bundle.
            userEmail = savedInstanceState.getString(this.ALTERNATE_MOBILE_KEY);
            AlternateMobile.setText(userEmail);
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                if (params[0].toString() == "type")
                    fetcher.loadList("RegistrationType", Rtype_edit, URLs.URL_RType + "1&PageSize=15&Language=" + currentLanguage,
                            "RegistrationTypeId", hidRegTypeId, "", "", "Registration Type", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "cate")
                    fetcher.loadList("RegistrationCategoryName",
                            Rcategory_edit, URLs.URL_RCategary, "RegistrationCategoryId", hidRegCagteId,
                            "", "", "Registration Category Name", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "userdetails")
                {
                    GetUserDetails();
                }
                Thread.sleep(10);

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
            //progressDialog.dismiss();
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

    public interface OnStepOneListener {

        void onNextPressed(Fragment fragment);
    }


    private void GetUserDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_REGISTRATIONGETUSERDETAILS + "&UserId="+user.getId() +"&Language=" + currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);


                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    hidRegTypeId.setText(userJson.getString("RegistrationTypeId"));
                                    Rtype_edit.setText(userJson.getString("RegistrationType"));
                                    if(userJson.getString("Gender").equals("Male"))
                                        rb1.setChecked(true);
                                    else
                                        rb2.setChecked(true);


                                   mobileNo.setText(userJson.getString("MobileNo"));
                                   AlternateMobile.setText(userJson.getString("AlternateMobile"));
                                   if(!userJson.getString("EmailId").equals("0"))
                                    Email.setText(userJson.getString("EmailId"));

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