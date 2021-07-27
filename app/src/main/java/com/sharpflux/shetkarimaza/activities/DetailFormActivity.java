package com.sharpflux.shetkarimaza.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.fragment.FirstFragment;
import com.sharpflux.shetkarimaza.fragment.SecondFragment;
import com.sharpflux.shetkarimaza.model.SimilarList;

import java.util.List;

public class DetailFormActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<SimilarList> productlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_form);


/*        FragmentTransaction transection = this.getSupportFragmentManager().beginTransaction();
        FirstFragment mfragment = new FirstFragment();
        transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
        transection.commit();*/



        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.dynamic_fragment_frame_layout, new FirstFragment());
        fragmentTransaction.addToBackStack("FirstFragment");
        fragmentTransaction.commit();

        setTitle(R.string.primaryInfo);


    }
    public void setActionBarTitle(String title){
        setTitle(title);
    }
    @Override
    public void onBackPressed() {

       /* final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setMessage("Do you want to leave this page ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                //finish();
             *//*   Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);*//*


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();*/

    /* if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }*/

   /*     List fragmentList = getSupportFragmentManager().getFragments();

        Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount() - 1);
        if (currentFragment instanceof SecondFragment) {
            ((SecondFragment) currentFragment).onBackPressed();
        }
        super.onBackPressed();*/
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStackImmediate();
        }
        else{
            super.onBackPressed();
        }
    }

    private void tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof SecondFragment)
                ((SecondFragment)f).onBackPressed();
        }
    }
}
