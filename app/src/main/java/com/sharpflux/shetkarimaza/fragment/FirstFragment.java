package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.ArrayList;


public class FirstFragment extends Fragment {

    TextInputEditText Rtype_edit, Rcategory_edit, editfullname, mobileNo, AlternateMobile, Email;
    ArrayList<Product> list;
    private CustomRecyclerViewDialog customDialog;
    DataFetcher fetcher;
    TextView hidRegTypeId, hidRegCagteId;
    Product sellOptions;
    RadioGroup rg;
    RadioButton rb1,rb2;
    View view;
    Button btn_next;
    String gender = "";
    private OnStepOneListener mListener;
    private String UserId, username, usermobileno, useremail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first, container, false);
        Rtype_edit = view.findViewById(R.id.edtRegitype);
        Rcategory_edit = view.findViewById(R.id.edtRegicategory);
        hidRegTypeId = view.findViewById(R.id.hidRegTypeId);
        hidRegCagteId = view.findViewById(R.id.hidRegCagteId);

        rg = view.findViewById(R.id.radioGroup1);
        rb1=view.findViewById(R.id.radio1);
        rb2=view.findViewById(R.id.radio2);

        btn_next = (Button) (view.findViewById(R.id.firstbtnnext));
        editfullname = view.findViewById(R.id.editfullname);
        mobileNo = view.findViewById(R.id.mobileNo);
        AlternateMobile = view.findViewById(R.id.AlternateMobile);
        Email = view.findViewById(R.id.Email);
        list = new ArrayList<Product>();

        User user = SharedPrefManager.getInstance(getContext()).getUser();

        username = user.getUsername();
        usermobileno = user.getMobile();
        useremail = user.getEmail();

        editfullname.setText(username);
        mobileNo.setText(usermobileno);
        // Email.setText(useremail);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regType = Rtype_edit.getText().toString();
                String regCategory = Rcategory_edit.getText().toString();
                String rb= Rcategory_edit.getText().toString();

                if (TextUtils.isEmpty(regType)) {
                    Rtype_edit.setError("Please enter your quality");
                    Rtype_edit.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(regCategory)) {
                    Rcategory_edit.setError("Please enter your quality");
                    Rcategory_edit.requestFocus();
                    return;
                }

                //int id = rg.getCheckedRadioButtonId();
                if(!rb1.isChecked()||!rb2.isChecked())
                {//Grp is your radio group object
                    rb1.setError("please select Gender");
                    rb1.requestFocus();
                    rb2.setError("please select Gender");
                    rb2.requestFocus();
                    return;
                }

                String name = editfullname.getText().toString();
                Bundle bundle = new Bundle();

                bundle.putString("RegistrationTypeId", hidRegTypeId.getText().toString());
                bundle.putString("RegistrationCategoryId", hidRegCagteId.getText().toString());
                bundle.putString("Name", name);
                bundle.putString("Gender", gender);
                bundle.putString("Mobile", mobileNo.getText().toString());
                bundle.putString("AlternateMobile", AlternateMobile.getText().toString());
                bundle.putString("Email", Email.getText().toString());
                FragmentTransaction transection = getFragmentManager().beginTransaction();
                SecondFragment mfragment = new SecondFragment();
                mfragment.setArguments(bundle); //data being send to SecondFragment
                transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
                transection.commit();

            }
        });


        fetcher = new DataFetcher(sellOptions, customDialog, list, getContext());

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
                }
                else if (rb2 != null)
                {
                    gender = rb2.getHint().toString();

                    Toast.makeText(getContext(),
                            gender,
                            Toast.LENGTH_LONG).show();
                }

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

                if (params[0].toString() == "type")
                    fetcher.loadList("RegistrationType", Rtype_edit, URLs.URL_RType, "RegistrationTypeId", hidRegTypeId, "", "");
                else if (params[0].toString() == "cate")
                    fetcher.loadList("RegistrationCategoryName", Rcategory_edit, URLs.URL_RCategary, "RegistrationCategoryId", hidRegCagteId, "", "");

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

    public interface OnStepOneListener {

        void onNextPressed(Fragment fragment);
    }


}