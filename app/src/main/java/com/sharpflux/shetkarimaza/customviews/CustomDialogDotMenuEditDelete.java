package com.sharpflux.shetkarimaza.customviews;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
import com.sharpflux.shetkarimaza.model.AddPersonModel;

import java.util.ArrayList;

public class CustomDialogDotMenuEditDelete extends Dialog {

    public Context context;
    String id, details_id;
    public TextView textView_edit, textView_delete;
    private AddPersonAdapter addPersonAdapter;
    private ArrayList<AddPersonModel> addPersonModelArrayList;
    private int position;
    String relation ;

    public CustomDialogDotMenuEditDelete(Context context, String id, String details_id, AddPersonAdapter addPersonAdapter,
                                         ArrayList<AddPersonModel> addPersonModelArrayList, int position, String relation) {
        super(context);
        this.context = context;
        this.id = id;
        this.details_id = details_id;
        this.addPersonAdapter = addPersonAdapter;
        this.addPersonModelArrayList = addPersonModelArrayList;
        this.position = position;
        this.relation = relation;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //blurBackground();

        setContentView(R.layout.custom_dialog_dot_menu_edit_delete);

        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textView_edit= findViewById(R.id.textView_edit);
        textView_delete = findViewById(R.id.textView_delete);

        textView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                dismiss();
                //Intent intent = new Intent(context, LoginActivity.class);
                //context.startActivity(intent);
            }
        });

        textView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

    }

}
