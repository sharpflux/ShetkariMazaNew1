package com.sharpflux.shetkarimaza.customviews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;

import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.DataAdapter;

public class CustomRecyclerViewDialog extends Dialog implements View.OnClickListener {

/*
    public CustomRecyclerViewDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomRecyclerViewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }*/


    public Context context;
    public Dialog dialog;
    public Button yes;
    SearchView searchView;

    TextView title;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    DataAdapter adapter;


    public CustomRecyclerViewDialog(Context context, DataAdapter adapter) {
        super(context);
        this.context = context;
        this.adapter = adapter;
        setupLayout();
    }

    private void setupLayout() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.custom_dialog_layout);
        yes = (Button) findViewById(R.id.yes);
        //no = (Button) findViewById(R.id.no);
        title = findViewById(R.id.tv_tittle);
        recyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        searchView = findViewById(R.id.searchView_customDialog);

        //recyclerView.setNestedScrollingEnabled(false);


        recyclerView.setAdapter(adapter);

        yes.setOnClickListener(this);
        //no.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }



}
