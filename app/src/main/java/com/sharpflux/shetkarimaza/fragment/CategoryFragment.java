package com.sharpflux.shetkarimaza.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.model.User;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    MyCategoryTypeAdapter myCategoryTypeAdapter;
    ArrayList<MyCategoryType> categoryList;
    Locale myLocale;
    String language;
    MyCategoryType myCategoryType;
   // ShimmerFrameLayout parentShimmerLayout;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_categorytype);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        categoryList = new ArrayList<>();
       // parentShimmerLayout =view.findViewById(R.id.shimmer_view_container);

     /*   myLocale = getResources().getConfiguration().locale;
        Context context = getContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "shetkariMaza", Context.MODE_PRIVATE);
        String language = sharedPref.getString("KEY_LANGUAGE", null);*/


        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        //parentShimmerLayout.startShimmerAnimation();
       setDynamicFragmentToTabLayout();

        return view;
    }



    private void setDynamicFragmentToTabLayout() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_RType+myLocale,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {
                                    myCategoryType = new MyCategoryType
                                            (       userJson.getString("ImageUrl"),
                                                    userJson.getString("RegistrationType"),
                                                    userJson.getString("RegistrationTypeId"));


                                    categoryList.add(myCategoryType);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myCategoryTypeAdapter = new MyCategoryTypeAdapter(getContext(), categoryList);
                                mRecyclerView.setAdapter(myCategoryTypeAdapter);

                                /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                        super.onScrollStateChanged(recyclerView, newState);
                                    }

                                });*/
                            }
                          /*  parentShimmerLayout.stopShimmerAnimation();
                            parentShimmerLayout.setVisibility(View.GONE);*/

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

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
      //  parentShimmerLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
       // parentShimmerLayout.stopShimmerAnimation();
    }
}
