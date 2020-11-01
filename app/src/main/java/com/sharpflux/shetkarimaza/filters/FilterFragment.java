package com.sharpflux.shetkarimaza.filters;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.SubCategoryFilter1;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;


public class FilterFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    ArrayList<SubCategoryFilter1> productlist;
    Button btn_next,btn_back,btnFilterData;
    String DistrictId="", TalukaId="",VarityId="",QualityId="",itemTypeId="",StatesID="";
    Bundle extras;
    StringBuilder taluka_builder_id = new StringBuilder();
    SearchView searchView;
    Filter1Adapter myAdapter;
    dbFilter mydatabase;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.filter_fragment, container, false);
        recyclerView = view.findViewById(R.id.rcv_vriety);
        productlist = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
       searchView =view.findViewById(R.id.searchView);

        String filter = getArguments().getString("Filter");

        mydatabase = new dbFilter(getContext());
        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = filter;
        runner.execute(sleepTime);
        return view;

    }
    private void SetDynamicDATA(final String FilterBy) {

       String Url="",IdColumn="",NameColumn="",itemId="";

       final String ActualUrl,Id,Name,Filter;


        switch (FilterBy){

            case "STATE":

                Url= URLs.URL_STATE+"?Language=en";
                IdColumn="StatesID";
                NameColumn="StatesName";
                break;


            case "DISTRICT":

                String Stateids="";
                Cursor StateCursor = mydatabase.FilterGetByFilterName("STATE");
                while (StateCursor.moveToNext()) {
                    Stateids=Stateids+StateCursor.getString(0)+",";
                }

                Url= URLs.URL_DISTRICT+Stateids+"&Language=en";
                IdColumn="DistrictId";
                NameColumn="DistrictName";
                break;


            case "TALUKA":

                String Districtids="";
                Cursor Talukacursor = mydatabase.FilterGetByFilterName("DISTRICT");
                while (Talukacursor.moveToNext()) {
                    Districtids=Districtids+Talukacursor.getString(0)+",";
                }


                Url= URLs.URL_TALUKA+Districtids+"&Language=en";
                IdColumn="TalukasId";
                NameColumn="TalukaName";
                break;


        }
        ActualUrl=Url;
        Id=IdColumn;
        Name=NameColumn;
        Filter=FilterBy;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ActualUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                SubCategoryFilter1 sellOptions =
                                            new SubCategoryFilter1
                                                    (
                                                            userJson.getString(Id),
                                                            userJson.getString(Name),
                                                            false,FilterBy);

                                    productlist.add(sellOptions);


                                myAdapter = new Filter1Adapter(getContext(), productlist);
                                recyclerView.setAdapter(myAdapter);

                              searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        myAdapter.getFilter().filter(newText);
                                        return false;
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {


                 SetDynamicDATA( params[0]);
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
