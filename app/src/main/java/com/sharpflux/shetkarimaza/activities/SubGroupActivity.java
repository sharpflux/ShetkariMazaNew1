package com.sharpflux.shetkarimaza.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MyBuyerAdapter;
import com.sharpflux.shetkarimaza.adapter.MyGroupAdapter;
import com.sharpflux.shetkarimaza.fragment.GroupFragment;
import com.sharpflux.shetkarimaza.model.GroupData;
import com.sharpflux.shetkarimaza.model.SellOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubGroupActivity extends AppCompatActivity {
    RecyclerView rv_cstGrp;
    LinearLayoutManager mGridLayoutManager;
    Bundle bundle;
    MyGroupAdapter myAdapter2;
    private ArrayList<GroupData> sellOptionsList;
    String ItemTypeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_group);

        rv_cstGrp = findViewById(R.id.rv_cstGrp);
        mGridLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_cstGrp.setLayoutManager(mGridLayoutManager);


        sellOptionsList = new ArrayList<>();


       //subGroup();

    }

    /*public void subGroup() {
        Bundle extras = getArguments();
        if (extras != null) {

            JSONArray obj = null;
            try {
                obj = new JSONArray(extras.getString("jsonObj").toString());

                for (int i = 0; i < obj.length(); i++) {
                    JSONObject userJson = null;
                    try {
                        userJson = obj.getJSONObject(i);

                        GroupData sellOptions =
                                new GroupData
                                        (userJson.getString("ImageUrl"),
                                                userJson.getString("ItemName"),
                                                userJson.getString("ItemTypeId")


                                        );
                        ItemTypeId = userJson.getString("ItemTypeId");
                        //PreviousCategoryId= extras.getString("PreviousCategoryId");

                        sellOptionsList.add(sellOptions);
                        myAdapter2 = new MyGroupAdapter(getApplicationContext(), sellOptionsList, extras.getString("jsonObj").toString(), GroupFragment.this);
                        rv_cstGrp.setAdapter(myAdapter2);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/
}
