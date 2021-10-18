package com.sharpflux.shetkarimaza.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.EditRequestAdapter;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditRequestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<SimilarList> productlist;
    Bundle bundle;
    Locale myLocale;
    int userId;
    TextView txt_emptyView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    dbLanguage mydatabase;
    String currentLanguage,language;
    List mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_request);

        recyclerView =findViewById(R.id.edit_rvProductList);
        txt_emptyView =findViewById(R.id.txt_emptyView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);

        User user = SharedPrefManager.getInstance(new EditRequestActivity()).getUser();
        userId = user.getId();

        productlist=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<SimilarList> mList = new ArrayList<>();


      //  Settings.Secure.putString(this.getContentResolver(), "accessibility_display_daltonizer_enabled", "1");
        //Settings.Secure.putString(this.getContentResolver(), "accessibility_display_daltonizer", "0");

       // startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));


        myLocale = getResources().getConfiguration().locale;
        mydatabase = new dbLanguage(getApplicationContext());

        Cursor cursor = mydatabase.LanguageGet(language);

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }

        EditRequestActivity.AsyncTaskRunner runner = new EditRequestActivity.AsyncTaskRunner();
        String sleepTime = "10";
        runner.execute(sleepTime);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //shuffle();
               /* EditRequestActivity.AsyncTaskRunner runner = new EditRequestActivity.AsyncTaskRunner();
                String sleepTime = "10";
                runner.execute(sleepTime);*/

                loadProducts();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });




    }


  /*  public void shuffle(){
        EditRequestAdapter myAdapter = new EditRequestAdapter(EditRequestActivity.this, mList);
        recyclerView.setAdapter(myAdapter);
    }*/
    private void loadProducts() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_ORDERDETAILS+userId+"&Language="+currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            if( array.length()==0){
                                txt_emptyView.setVisibility(View.VISIBLE);
                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject userJson = array.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    SimilarList sellOptions;
                                    sellOptions = new SimilarList
                                                    (userJson.getString("ImageUrl"),
                                                    "",
                                                    "",
                                                    userJson.getString("ItemName"),
                                                    userJson.getString("VarietyName"),
                                                    userJson.getString("QualityType"),
                                                    String.valueOf(userJson.getDouble("AvailableQuantity")),
                                                    userJson.getString("MeasurementType"),
                                                    String.valueOf(userJson.getDouble("ExpectedPrice")),
                                                    userJson.getString("AvailableMonths"),
                                                    userJson.getString("FarmAddress"),
                                                    userJson.getString("StatesName"),
                                                    userJson.getString("DistrictName"),
                                                    userJson.getString("TalukaName"),
                                                    userJson.getString("VillageName"),
                                                    userJson.getString("Hector"),
                                                            String.valueOf(userJson.getInt("ItemTypeId")),
                                                            String.valueOf(userJson.getInt("VarietyId")),
                                                            String.valueOf(userJson.getInt("QualityId")),
                                                            String.valueOf(userJson.getInt("MeasurementId")),
                                                            String.valueOf(userJson.getInt("StateId")),
                                                            String.valueOf(userJson.getInt("DistrictId")),
                                                            String.valueOf(userJson.getInt("TalukaId")),
                                                            String.valueOf(userJson.getInt("RequstId")),
                                                            userJson.getString("SurveyNo"),
                                                            String.valueOf(userJson.getInt("CategoryId")),
                                                            userJson.getString("BotanicalName"),
                                                            ""

                                            );

                                    productlist.add(sellOptions);
                                } else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }
                                EditRequestAdapter myAdapter = new EditRequestAdapter(EditRequestActivity.this, productlist);
                                recyclerView.setAdapter(myAdapter);
                                myAdapter.notifyDataSetChanged();


                                if(myAdapter.getItemCount()==0);{
                                    txt_emptyView.setVisibility(View.VISIBLE);
                                }
                                txt_emptyView.setVisibility(View.GONE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);


    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                loadProducts();
                Thread.sleep(2000);
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
            progressDialog = ProgressDialog.show(EditRequestActivity.this,
                    "Loading...",
                    "Wait for result..");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }
    }
}
