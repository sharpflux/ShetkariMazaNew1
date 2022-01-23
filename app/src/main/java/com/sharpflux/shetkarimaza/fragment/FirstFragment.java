package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
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

    EditText editSubType,Rtype_edit, Rcategory_edit, editfullname, mobileNo, AlternateMobile, Email, name_botanical,hidRegTypeId;

    ArrayList<Product> list;
    private ArrayList<MyProcessor> processorList;
    Locale myLocale;
    private CustomRecyclerViewDialog customDialog;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    DataFetcher fetcher;

    TextView  hidRegCagteId,hidSubCategoryId;

    MyProcessor myProcessor;
    Product sellOptions;

    RadioGroup rg;
    RadioButton rb1, rb2;
    View view;
    LinearLayout btn_next;
    String gender = "";
    private OnStepOneListener mListener;
    private String UserId, username, usermobileno, useremail,ModelName;

    dbLanguage mydatabase;
    String currentLanguage, language;

    public static final String ALTERNATE_MOBILE_KEY = "AlternateMobile";
    public static final String REGISTATIONTYPEID_KEY = "RegistrationTypeId";

    private String userEmail = "";
    User user;
    Bundle bundle;
    public String IsNewUser;


    TextInputLayout tabSubCategoryType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first, container, false);
        Rtype_edit = view.findViewById(R.id.edtRegitype);
        Rcategory_edit = view.findViewById(R.id.edtRegicategory);
        hidRegTypeId = view.findViewById(R.id.hidRegTypeId);
        hidRegCagteId = view.findViewById(R.id.hidRegCagteId);
        hidSubCategoryId=view.findViewById(R.id.hidSubCategoryId);
        IsNewUser="0";
        rg = view.findViewById(R.id.radioGroup1);
        rb1 = view.findViewById(R.id.radio1);
        rb2 = view.findViewById(R.id.radio2);


        btn_next = (LinearLayout) (view.findViewById(R.id.footer));
        editfullname = view.findViewById(R.id.editfullname);
        mobileNo = view.findViewById(R.id.mobileNo);
        name_botanical = view.findViewById(R.id.name_botanical);


        tabSubCategoryType=view.findViewById(R.id.tabSubCategoryType);
        editSubType=view.findViewById(R.id.editSubType);

        editSubType.setHint( getResources().getString(R.string.Type));

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


        if (savedInstanceState != null) {
            hidRegTypeId.setText( savedInstanceState.getString(REGISTATIONTYPEID_KEY));
            // Do something with value if needed
        }




        bundle = getArguments();

        if (bundle != null) {


            IsNewUser = bundle.getString("IsNewUser");

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


                if(hidRegTypeId.getText().toString().equals("36")||hidRegTypeId.getText().toString().equals("37")||hidRegTypeId.getText().toString().equals("1")||hidRegTypeId.getText().toString().equals("3")||hidRegTypeId.getText().toString().equals("24")){

                    if (hidSubCategoryId.getText().toString().equals("0")) {
                        editSubType.setError("Please select type");
                        editSubType.requestFocus();
                        return;
                    }

                }

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
                bundle.putString("IsNewUser", IsNewUser);
                bundle.putString("SubCategroyTypeId", hidSubCategoryId.getText().toString());

                FragmentTransaction transection = getActivity().getSupportFragmentManager().beginTransaction();
                SecondFragment mfragment = new SecondFragment();
                transection.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                mfragment.setArguments(bundle); //data being send to SecondFragment
                transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
                transection.addToBackStack("SecondFragment");
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


        editSubType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hidRegTypeId.getText().toString().equals("23")|| hidRegTypeId.toString().trim().equals("37")||hidRegTypeId.getText().toString().equals("1") ||hidRegTypeId.getText().toString().equals("24")||hidRegTypeId.getText().toString().equals("3")) {
                    FirstFragment.AsyncTaskRunner runner = new FirstFragment.AsyncTaskRunner();
                    String sleepTime = "Category";
                    runner.execute(sleepTime);
                }
                else {

                    FirstFragment.AsyncTaskRunner runner = new FirstFragment.AsyncTaskRunner();
                    String sleepTime = "SubCate";
                    runner.execute(sleepTime);

                }

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


        hidRegTypeId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,  int before, int count) {

                if(s.toString().trim().equals("36") || s.toString().trim().equals("37")||hidRegTypeId.getText().toString().equals("24")||hidRegTypeId.getText().toString().equals("26")|| s.toString().trim().equals("37")||hidRegTypeId.getText().toString().equals("1")|| s.toString().trim().equals("23")|| s.toString().trim().equals("3")){
                    tabSubCategoryType.setVisibility(View.VISIBLE);
                }
                else {
                    tabSubCategoryType.setVisibility(View.GONE);
                }
                if(s.equals("36")) {
                    ModelName = getResources().getString(R.string.HiringMachinery);
                    editSubType.setHint( getResources().getString(R.string.WhichMachine));

                    //tabSubCategoryType.setHint(getResources().getString(R.string.WhichMachine));
                }
                if(s.equals("23") || s.equals("3")) {
                    ModelName = getResources().getString(R.string.LabourforAgri);
                    editSubType.setHint( getResources().getString(R.string.WhoYouAre));
                }
                if(s.equals("26")) {
                    ModelName = getResources().getString(R.string.LabourforAgri);
                    editSubType.setHint( getResources().getString(R.string.WhoYouAre));
                }



                editSubType.setText("");
                hidSubCategoryId.setText("");
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


        if (IsNewUser.equals("true")) {

        } else if (IsNewUser.equals("false")) {
            FirstFragment.AsyncTaskRunner runner = new FirstFragment.AsyncTaskRunner();
            String sleepTime = "userdetails";
            runner.execute(sleepTime);
        }


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null) {
            outState.putString(this.ALTERNATE_MOBILE_KEY, AlternateMobile.getText().toString());
            outState.putString(this.REGISTATIONTYPEID_KEY, hidRegTypeId.getText().toString());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            // Retrieve the user email value from bundle.
            userEmail = savedInstanceState.getString(this.ALTERNATE_MOBILE_KEY);
            AlternateMobile.setText(userEmail);
            hidRegTypeId.setText(savedInstanceState.getString(REGISTATIONTYPEID_KEY) );
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
                    fetcher.loadList("RegistrationType", Rtype_edit, URLs.URL_RType + "1&PageSize=100&Language=" + currentLanguage + "&UserId=0",
                            "RegistrationTypeId", hidRegTypeId, "", "", "Registration Type", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "SubCate")
                    fetcher.loadList("SubCategoriesName", editSubType, URLs.URL_MasterSubCategoriesGET + "Language=" + currentLanguage + "&CategoryId="+hidRegTypeId.getText(),
                            "MasterSubCategoriesId", hidSubCategoryId, "", "", ModelName, "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "Category")
                    fetcher.loadList("CategoryName_EN", editSubType, URLs.URL_RECYCLER+ currentLanguage,
                            "CategoryId", hidSubCategoryId, "", "", getResources().getString(R.string.category), "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "cate")
                    fetcher.loadList("SubCategoriesName",
                            Rcategory_edit, URLs.URL_RCategary, "MasterSubCategoriesId", hidSubCategoryId,
                            "", "", ModelName, "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "userdetails") {
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_REGISTRATIONGETUSERDETAILS + "UserId=" + user.getId() + "&Language=" + currentLanguage,
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

                                    if (!userJson.getString("Gender").equals("0")) {
                                        if (userJson.getString("Gender").equals("Male"))
                                            rb1.setChecked(true);
                                        else
                                            rb2.setChecked(true);
                                    }

                                    Rtype_edit.setEnabled(false);
                                    editSubType.setEnabled(false);
                                    Rtype_edit.setTextColor(getResources().getColor(R.color.gray));
                                    editSubType.setTextColor(getResources().getColor(R.color.gray));
                                    editfullname.setTextColor(getResources().getColor(R.color.gray));
                                    editfullname.setEnabled(false);
                                    mobileNo.setEnabled(false);
                                    mobileNo.setTextColor(getResources().getColor(R.color.gray));
                                 //   rb1.setEnabled(false);
                                   // rb2.setEnabled(false);
                                    rg.setEnabled(false);

                                    mobileNo.setText(userJson.getString("MobileNo"));

                                    if (!userJson.getString("RegistrationCategoryId").equals("0"))
                                        hidSubCategoryId.setText(userJson.getString("RegistrationCategoryId"));

                                    if (!userJson.getString("SubCategoryNameText").equals("0"))
                                         editSubType.setText(userJson.getString("SubCategoryNameText"));

                                    if (!userJson.getString("AlternateMobile").equals("0"))
                                        AlternateMobile.setText(userJson.getString("AlternateMobile"));

                                    if (!userJson.getString("EmailId").equals("0"))
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

}