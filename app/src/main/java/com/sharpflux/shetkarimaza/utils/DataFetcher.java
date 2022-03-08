package com.sharpflux.shetkarimaza.utils;

import android.content.Context;

import android.view.View;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.DataAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataFetcher {
    private Product prod;
    private CustomRecyclerViewDialog customDialog;
    private ArrayList<Product>  list;
    SearchView searchView;
    DataAdapter dataAdapter;
    EditText name_botanical;


    private  Context context;


    public DataFetcher(Product prod, CustomRecyclerViewDialog customDialog, ArrayList<Product> list, Context context,EditText name_botanical) {
        this.prod = prod;
        this.customDialog = customDialog;
        this.list = list;
        this.context = context;
        this.searchView = searchView;
        this.name_botanical = name_botanical;

    }

    public void loadList(final String ColumnName, final EditText editText, final  String URL, final String id, final TextView hiddenText,  final String ParameterName, final String ParameterValue, final String Title, final  String BotinicalName, final EditText name_botanical, final TextInputLayout edtproductVariety, final CustomDialogLoadingProgressBar customDialogLoadingProgressBar) {
        list.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);

                            for (int i = 0; i < obj.length(); i++) {
                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    String botnilcalName="";
                                    boolean IsVarietyAvailable=false;
                                    if(userJson.has(BotinicalName)) {
                                        botnilcalName = userJson.getString(BotinicalName);
                                    }

                                    if(userJson.has("IsVarietyAvailable")) {
                                        IsVarietyAvailable = userJson.getBoolean("IsVarietyAvailable");

                                    }
                                    prod = new Product(
                                            userJson.getString(ColumnName),
                                            userJson.getString(id),
                                            botnilcalName,
                                            IsVarietyAvailable
                                    );

                                    list.add(prod);

                                } else {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }


                            }
                            dataAdapter = new DataAdapter(list, new DataAdapter.RecyclerViewItemClickListener() {
                                @Override
                                public void clickOnItem(Product data) {

                                    editText.setText(data.getName());
                                    if(name_botanical!=null) {
                                        name_botanical.setText(data.getBotanicalName());

                                        if(String.valueOf(data.getBotanicalName()).equals("")){
                                            name_botanical.setText("Not updated");
                                        }
                                    }
                                   /* else {
                                       // name_botanical.setText("Not updated");
                                    }*/
                                    // name_botanical.setText("Test");
                                    hiddenText.setText(data.getProductId());

                                    if(ColumnName=="ItemName"){
                                        if(edtproductVariety!=null){
                                            if(data.isVarietyAvailable()==false) {
                                                edtproductVariety.setVisibility(View.GONE);
                                            }else {
                                                edtproductVariety.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }

                                    if (customDialog != null) {
                                        customDialog.dismiss();
                                    }

                                }
                            });

                            customDialog = new CustomRecyclerViewDialog(context, dataAdapter);
                            customDialog.show();
                            customDialog.setCanceledOnTouchOutside(false);

                            TextView txtTitle=customDialog.findViewById(R.id.tv_tittle);
                            txtTitle.setText(Title);

                            searchView =customDialog.findViewById(R.id.searchView_customDialog);

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    dataAdapter.getFilter().filter(newText);
                                    return false;
                                }
                            });

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
                       // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        customDialogLoadingProgressBar.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ParameterName, ParameterValue);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }


    public void loadList(final String ColumnName, final EditText editText, final  String URL,final String id,final TextView hiddenText,
                         final String ParameterName, final String ParameterValue,final String Title,final  String BotinicalName,final EditText name_botanical,final EditText edtproductVariety) {
        list.clear();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);

                            for (int i = 0; i < obj.length(); i++) {
                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    String botnilcalName="";
                                    boolean IsVarietyAvailable=false;
                                    if(userJson.has(BotinicalName)) {
                                        botnilcalName = userJson.getString(BotinicalName);
                                    }

                                    if(userJson.has("IsVarietyAvailable")) {
                                        IsVarietyAvailable = userJson.getBoolean("IsVarietyAvailable");
                                    }
                                    prod = new Product(
                                            userJson.getString(ColumnName),
                                            userJson.getString(id),
                                            botnilcalName,
                                            IsVarietyAvailable
                                    );

                                    list.add(prod);






                                } else {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }


                            }
                           dataAdapter = new DataAdapter(list, new DataAdapter.RecyclerViewItemClickListener() {
                                @Override
                                public void clickOnItem(Product data) {


                                    editText.setText(data.getName());
                                    if(name_botanical!=null) {
                                        name_botanical.setText(data.getBotanicalName());
                                    }
                                   // name_botanical.setText("Test");
                                    hiddenText.setText(data.getProductId());

                                    if(ColumnName=="ItemName"){
                                        if(edtproductVariety!=null){
                                            if(data.isVarietyAvailable()==false) {
                                                edtproductVariety.setVisibility(View.GONE);
                                            }else {
                                                edtproductVariety.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }

                                    if (customDialog != null) {
                                        customDialog.dismiss();
                                    }

                                }
                            });

                            customDialog = new CustomRecyclerViewDialog(context, dataAdapter);
                            customDialog.show();
                            customDialog.setCanceledOnTouchOutside(false);

                            TextView txtTitle=customDialog.findViewById(R.id.tv_tittle);
                            txtTitle.setText(Title);

                            searchView =customDialog.findViewById(R.id.searchView_customDialog);

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {

                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {

                                    dataAdapter.getFilter().filter(newText);
                                    return false;

                                }
                            });



                            } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(ParameterName, ParameterValue);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS *
                2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }
}
