package com.sharpflux.shetkarimaza.filters;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.adapter.DataAdapter;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AgeFragment extends Fragment {


    Button btn_next, btn_back, btnFilterData;
    String VarityId = "", itemTypeId = "";
    Bundle extras;
    EditText editMinAge,editMaxAge;


    public AgeFragment() {

    }

    String varietyid = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_age, container, false);


        btn_next = view.findViewById(R.id.btnnextAge);
        btn_back = view.findViewById(R.id.btnbackAge);
        btnFilterData = view.findViewById(R.id.btnSkipAge);


        extras = new Bundle();

        if (extras != null) {

            VarityId = extras.getString("VarietyId");
            itemTypeId = extras.getString("ItemTypeId");
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extras = new Bundle();
                if (extras != null) {
                    extras.putString("ItemTypeId", itemTypeId);


                }

                FragmentTransaction transection = getFragmentManager().beginTransaction();
                VarietyFragment mfragment = new VarietyFragment();
                mfragment.setArguments(extras);
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("ItemTypeId", itemTypeId);
                    // Log.e("IDS",districtIds);
                }


                FragmentTransaction transection = getFragmentManager().beginTransaction();
                QualityFragment mfragment = new QualityFragment();
                mfragment.setArguments(extras);
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });
        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
                intent.putExtra("ItemTypeId", itemTypeId);
                intent.putExtra("VarietyId", VarityId);

                startActivity(intent);
            }
        });


        return view;
    }


}



