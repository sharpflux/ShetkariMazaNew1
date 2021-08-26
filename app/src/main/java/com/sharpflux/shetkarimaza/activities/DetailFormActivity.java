package com.sharpflux.shetkarimaza.activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.fragment.FirstFragment;
import com.sharpflux.shetkarimaza.fragment.SecondFragment;
import com.sharpflux.shetkarimaza.fragment.ThirdFragment;
import com.sharpflux.shetkarimaza.model.SimilarList;

import java.util.List;

public class DetailFormActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<SimilarList> productlist;
    private Intent iin;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_form);

        iin = getIntent();
        bundle = iin.getExtras();

        FragmentTransaction transection =this.getSupportFragmentManager().beginTransaction();
        transection.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        FirstFragment mfragment = new FirstFragment();
        mfragment.setArguments(bundle); //data being send to SecondFragment
        transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
        transection.addToBackStack("ThirdFragment");
        transection.commit();


        setTitle(R.string.primaryInfo);


    }
    public void setActionBarTitle(String title){
        setTitle(title);
    }





    @Override
    public void onBackPressed() {

        final FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            builder.setCancelable(false);
            builder.setMessage("Do you want to leave this page ?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                  /*  Intent intent = new Intent(getApplicationContext(), TabLayoutLogRegActivity.class);
                    startActivity(intent);
                    finish();*/
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();



        } else {



            super.onBackPressed();
        }





    }private void tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof SecondFragment)
                ((SecondFragment)f).onBackPressed();
        }
    }
}
