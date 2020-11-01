package com.sharpflux.shetkarimaza.filters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ContactDetailActivity;

public class BottomSheetDialogSorting extends BottomSheetDialogFragment implements View.OnClickListener {

    public static String PickupAddress;
    public static String DeliveryAddress;
    RadioButton RdoAsc, rdoDesc;
    Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_2, container, false);
        RdoAsc = v.findViewById(R.id.RdoAsc);
        rdoDesc = v.findViewById(R.id.rdoDesc);
        rdoDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = getArguments();
                if (bundle != null) {
                    Intent i = new Intent(getContext(), ContactDetailActivity.class);
                    i.putExtra("Search", bundle.getString("Search"));
                    i.putExtra("Id", bundle.getString("Id"));
                    i.putExtra("SortBy", "ASC");
                    startActivity(i);
                }
            }
        });

        RdoAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = getArguments();
                if (bundle != null) {
                    Intent i = new Intent(getContext(), ContactDetailActivity.class);
                    i.putExtra("Search", bundle.getString("Search"));
                    i.putExtra("Id", bundle.getString("Id"));
                    i.putExtra("SortBy", "DESC");
                    startActivity(i);
                }
            }
        });

        return v;
    }


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                // dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        //super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_2, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String ReceiverName = data.getStringExtra("ReceiverName");
                String ReceiverMobile = data.getStringExtra("ReceiverMobile");

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}