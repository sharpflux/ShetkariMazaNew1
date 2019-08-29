package com.sharpflux.shetkarimaza.customviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;

public class CustomAlertDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_alertdialog,null);

        builder.setView(view)
                .setTitle("Add Variety")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        TextView txt_custom;
        txt_custom = view.findViewById(R.id.txt_custom);
        return builder.create();

    }
}
