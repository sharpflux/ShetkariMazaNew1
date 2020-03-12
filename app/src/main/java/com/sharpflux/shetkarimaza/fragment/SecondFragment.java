package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.ArrayList;
import java.util.Locale;


public class SecondFragment extends DialogFragment {
    TextView hideStateId, hideDistrictId,hideTalukaId;
    TextInputEditText address, city, edtdistrict,edttaluka, edtstate, companyname, license, companyregnno, gstno,name_botanical;
    ArrayList<Product> list;
    Button btn_next;
    Bundle bundle;
    String name = "", registrationTypeId = "", registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "";
    DataFetcher fetcher;
    private CustomRecyclerViewDialog customDialog;
    Product sellOptions;

    Locale myLocale;
    dbLanguage mydatabase;
    String currentLanguage,language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_second, container, false);

        myLocale = getResources().getConfiguration().locale;
        mydatabase = new dbLanguage(getContext());

        list = new ArrayList<Product>();

        btn_next = (Button) (view.findViewById(R.id.secondbtnnext));

        address = view.findViewById(R.id.edtaddress);

        edtstate = view.findViewById(R.id.edtstate);
        edtdistrict = view.findViewById(R.id.edtdistrict);
        edttaluka=view.findViewById(R.id.edttaluka);
        city = view.findViewById(R.id.edtcity);
        name_botanical = view.findViewById(R.id.name_botanical);

        companyname = view.findViewById(R.id.edtcompanyname);
        license = view.findViewById(R.id.edtlicense);
        companyregnno = view.findViewById(R.id.edtcompanyregno);
        gstno = view.findViewById(R.id.edtgstno);



        hideStateId = view.findViewById(R.id.hideStateId);
        hideDistrictId = view.findViewById(R.id.hideDistrictId);
        hideTalukaId = view.findViewById(R.id.hideTalukaId);

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);


        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

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

                if (TextUtils.isEmpty(village)) {
                    city.setError("Please enter your village");
                    city.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(firmname)) {
                    companyname.setError("Please enter your firm name");
                    companyname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(apmc)) {
                    license.setError("Please enter your apmc license");
                    license.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(companyreg)) {
                    companyregnno.setError("Please enter your company reg no");
                    companyregnno.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(gst)) {
                    gstno.setError("Please enter your gst no");
                    gstno.requestFocus();
                    return;
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
                bundle.putString("city", city.getText().toString());
                bundle.putString("TalukaId", hideTalukaId.getText().toString());
                bundle.putString("district", edtdistrict.getText().toString());
                bundle.putString("districtId", hideDistrictId.getText().toString());
                bundle.putString("state", edtstate.getText().toString());
                bundle.putString("stateId", hideStateId.getText().toString());
                bundle.putString("companyname", companyname.getText().toString());
                bundle.putString("license", license.getText().toString());
                bundle.putString("companyregnno", companyregnno.getText().toString());
                bundle.putString("gstno", gstno.getText().toString());


                FragmentTransaction transection = getFragmentManager().beginTransaction();
                ThirdFragment mfragment = new ThirdFragment();
                mfragment.setArguments(bundle); //data being send to SecondFragment
                transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
                transection.addToBackStack(null);
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
                    fetcher.loadList("StatesName", edtstate, URLs.URL_STATE + "?StatesID=15&Language=en", "StatesID", hideStateId, "", "","State","",null,null);
                   // fetcher.loadList("StatesName", edtstate, URLs.URL_STATE, "StatesID", hideStateId, "", "");

                else if (params[0].toString() == "district")
                    fetcher.loadList("DistrictName", edtdistrict, URLs.URL_DISTRICT + hideStateId.getText()+"," + "&Language=en", "DistrictId", hideDistrictId, "", "","District","",null,null);

                else if (params[0].toString() == "taluka")
                    fetcher.loadList("TalukaName", edttaluka, URLs.URL_TALUKA + hideDistrictId.getText()+"," + "&Language=en", "TalukasId", hideTalukaId, "", "","Taluka","",null,null);


                Thread.sleep(500);

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
            progressDialog.dismiss();
            // finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getContext(),
                    "Loading...",
                    "");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }



}
