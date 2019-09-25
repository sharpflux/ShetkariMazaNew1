package com.sharpflux.shetkarimaza.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.adapter.DataAdapter;
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

    private  Context context;


    public DataFetcher(Product prod, CustomRecyclerViewDialog customDialog, ArrayList<Product> list, Context context) {
        this.prod = prod;
        this.customDialog = customDialog;
        this.list = list;
        this.context = context;
    }




    public void loadList(final String ColumnName, final EditText editText, final  String URL,final String id,final TextView hiddenText,final String ParameterName, final String ParameterValue) {
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


                                    prod = new Product(userJson.getString(ColumnName),
                                            userJson.getString(id));

                                    list.add(prod);

                                } else {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }


                            }
                            DataAdapter dataAdapter = new DataAdapter(list, new DataAdapter.RecyclerViewItemClickListener() {
                                @Override
                                public void clickOnItem(Product data) {
                                    editText.setText(data.getName());
                                    hiddenText.setText(data.getProductId());
                                    if (customDialog != null) {
                                        customDialog.dismiss();
                                    }

                                }
                            });
                            customDialog = new CustomRecyclerViewDialog(context, dataAdapter);
                            customDialog.show();
                            customDialog.setCanceledOnTouchOutside(false);


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
