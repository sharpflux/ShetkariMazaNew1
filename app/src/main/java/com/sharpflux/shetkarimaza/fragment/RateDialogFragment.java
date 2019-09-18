package com.sharpflux.shetkarimaza.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.RateAdapter;
import com.sharpflux.shetkarimaza.model.RateData;
import com.sharpflux.shetkarimaza.volley.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateDialogFragment extends AppCompatDialogFragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<RateData> ratelist;
    String ItemTypeId="",VarityId="",QualityId="";
    Bundle extras;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.rate_recyclerview,null);

        ratelist=new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.edit_rvrate);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        builder.setView(view)
                .setTitle("Rate Guide")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        extras = new Bundle();
        if (extras != null) {
            ItemTypeId = getArguments().getString("ItemTypeId");
            VarityId = getArguments().getString("VarityId");
            QualityId = getArguments().getString("QualityId");
        }




        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_RATE + "ItemTypeId=" + "1" + "&VarietyId=" + "164" +"&QualityId=" + "3",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject userJson = array.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    RateData sellOptions;
                                    sellOptions = new RateData
                                            (userJson.getString("FromDates"),
                                                    userJson.getString("ToDates"),
                                                    userJson.getString("Rates")
                                            );

                                    ratelist.add(sellOptions);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }
                                RateAdapter myAdapter = new RateAdapter(getContext(), ratelist);
                                recyclerView.setAdapter(myAdapter);

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
        Volley.newRequestQueue(getContext()).add(stringRequest);
        return  builder.show();
    }
}
