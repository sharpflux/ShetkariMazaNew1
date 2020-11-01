package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AddListActivity;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.model.CursorData;
import com.sharpflux.shetkarimaza.model.RateData;
import com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager;
import com.squareup.picasso.Picasso;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyCursorAdapter extends RecyclerView.Adapter<MyCursorAdapterViewHolder> {
    private Context mContext;
    private ArrayList<CursorData> mlist;
    Bitmap bitmap;
    private static int currentPosition = 0;
    String Pkey;
    int Postition;
    public UserInfoDBManager userInfoDBManager;

    public MyCursorAdapter(Context mContext, ArrayList<CursorData> mlist, UserInfoDBManager userInfoDBManager) {
        this.mContext = mContext;
        this.mlist = mlist;
        userInfoDBManager = new UserInfoDBManager(mContext);
    }

    public MyCursorAdapter(Context applicationContext, ArrayList<CursorData> cursorDataList) {
        this.mContext = applicationContext;
        this.mlist = cursorDataList;
    }

    @NonNull
    @Override
    public MyCursorAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_user_account_list_view_item, viewGroup, false);
        return new MyCursorAdapterViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyCursorAdapterViewHolder holder, int i) {
        // Picasso.get().load(mlist.get(i).getImageUrl()).resize(300, 300).into(((MyCursorAdapterViewHolder) holder).Imgage);


        Pkey = mlist.get(i).getPKey();

        Postition = i;
        byte[] imgbytes = new byte[]{};
        if (mlist.get(i).getImageUrl() != null) {
            imgbytes = Base64.decode(mlist.get(i).getImageUrl(), Base64.DEFAULT);
        }
        bitmap = BitmapFactory.decodeByteArray(imgbytes, 0, imgbytes.length);
        holder.Imgage.setImageBitmap(bitmap);

        holder.mname.setText(mlist.get(i).getName());
        holder.mvarity.setText(mlist.get(i).getVariety());
        holder.mquality.setText(mlist.get(i).getQuality());
        holder.mrate.setText(mlist.get(i).getPrice());
        holder.row_cartlist_ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
                alertDialogBuilder.setMessage("Are you sure you want to delete?");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                userInfoDBManager = new UserInfoDBManager(view.getRootView().getContext());
                                userInfoDBManager.open();
                                if (userInfoDBManager != null) {
                                    userInfoDBManager.deleteAccount(Integer.valueOf(Pkey));
                                    mlist.remove(Postition);
                                    notifyItemRemoved(Postition);
                                }

                            }
                        });

                alertDialogBuilder.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {

            return mlist.size();

    }
}


class MyCursorAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView Imgage, row_cartlist_ivDelete;
    TextView mname, mvarity, mquality, mrate;

    MyCursorAdapterViewHolder(View itemView) {
        super(itemView);
        Imgage = itemView.findViewById(R.id.ImgageAddList);
        mname = itemView.findViewById(R.id.user_account_list_item_id);
        mvarity = itemView.findViewById(R.id.user_account_list_item_user_name);
        mquality = itemView.findViewById(R.id.user_account_list_item_password);
        mrate = itemView.findViewById(R.id.user_account_list_item_email);
        row_cartlist_ivDelete = itemView.findViewById(R.id.row_cartlist_ivDelete);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

    }
}
