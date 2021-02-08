package com.sharpflux.shetkarimaza.customviews;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
import com.sharpflux.shetkarimaza.fragment.SecondFragment;
import com.sharpflux.shetkarimaza.model.AddPersonModel;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomDialogViewSimilarList extends Dialog {
    TextView textView_farmerName, textView_farmerAddress, textView_farmerMobileNo, textView_variety,
            textView_quality, textView_quantity, textView_rate, textView_total, textView_quantityAvailableIn,
            textView_back, textView_call, textView_description;
    TextView textcattoolbar;
    ArrayList<Product> list;
    Button btn_next;
    Bundle bundle;
    LinearLayout linearlayout_call;

    private CustomRecyclerViewDialog customDialog;

    Locale myLocale;

    String currentLanguage,language;
    TextInputLayout TICompany;


    public Context context;



    private ImageView imageView_back;




    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;

    private SimilarList similarList;


    ImageView imageView_product;
    CircleImageView circleImageView_product;

    private AddPersonAdapter addPersonAdapter;
    private ArrayList<AddPersonModel> addPersonModelArrayList;
    private int position;

    public CustomDialogViewSimilarList(Context context, SimilarList similarList)
    {
        super(context);
        this.context = context;
        this.similarList = similarList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);





        setContentView(R.layout.custom_dialog_view_similar_list);



        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        imageView_back = findViewById(R.id.back);
        textcattoolbar = findViewById(R.id.textcattoolbar);
        textcattoolbar.setText("Farmer Details");




        imageView_product = findViewById(R.id.imageView_product);
        circleImageView_product = findViewById(R.id.circleImageView_product);
        textView_farmerName = findViewById(R.id.textView_farmerName);
        textView_farmerAddress = findViewById(R.id.textView_farmerAddress);
        textView_farmerMobileNo = findViewById(R.id.textView_farmerMobileNo);
        textView_variety = findViewById(R.id.textView_variety);
        textView_quality = findViewById(R.id.textView_quality);
        textView_quantity = findViewById(R.id.textView_quantity);
        textView_rate = findViewById(R.id.textView_rate);
        textView_total = findViewById(R.id.textView_total);
        textView_quantityAvailableIn = findViewById(R.id.textView_quantityAvailableIn);
        textView_back = findViewById(R.id.textView_back);
        textView_call = findViewById(R.id.textView_call);
        linearlayout_call = findViewById(R.id.linearlayout_call);
        textView_description = findViewById(R.id.textView_description);


        myLocale =getContext().getResources().getConfiguration().locale;


        Glide.with(context)
                .load(similarList.getImageUrl()).placeholder(R.drawable.kisanmaza)
                .into(imageView_product);
        Glide.with(context)
                .load(similarList.getImageUrl()).placeholder(R.drawable.kisanmaza)
                .into(circleImageView_product);

        textView_farmerName.setText(similarList.getFullName());
        textView_farmerAddress.setText(similarList.getFarm_address()+", "+similarList.getState()+", "+similarList.getTaluka());
        textView_farmerMobileNo.setText(similarList.getMobileNo());
        textView_variety.setText(similarList.getVarietyName());
        textView_quality.setText(similarList.getQuality());
        textView_quantity.setText(similarList.getQuantity());
        textView_rate.setText("₹ "+String.valueOf(Double.valueOf(similarList.getPrice())/ Double.valueOf(similarList.getQuantity())));
        textView_total.setText("₹ "+similarList.getPrice());
        textView_quantityAvailableIn.setText(similarList.getUnit());

        textView_description.setText(context.getResources().getString(R.string.i_want_to_sell)+" "
        +similarList.getUnit()+" "+context.getResources().getString(R.string.of)+" "+similarList.getName()+" "
        +context.getResources().getString(R.string.from)+" "+similarList.getFarm_address()+", "
        +similarList.getState()+", "+similarList.getTaluka());

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getContext().getResources().getConfiguration().locale;
        language = user.getLanguage();


        linearlayout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",  textView_farmerMobileNo.getText().toString(), null));
                    context.startActivity(intent);
                 }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



    }





}
