package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.filters.FilterActivity;
import com.sharpflux.shetkarimaza.filters.SubCategoryFilter;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyBuyerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
      dbBuyerFilter myFilter;


    private Context mContext;
    public String id;
    private static int currentPosition = 0;

    private ArrayList<SellOptions> mList;
    private ArrayList<SellOptions> exampleListFull;



    public MyBuyerAdapter(Context mContext, ArrayList<SellOptions> mList,ArrayList<SellOptions> tempProductList, dbBuyerFilter myFilter) {
        this.mContext = mContext;
        this.mList = mList;
        this.myFilter=myFilter;

        exampleListFull =tempProductList ;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_ITEM)
        {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_buyer, parent, false);
            return new FlowerViewHolder(mView);
        }
        else if(viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_group_layout, parent,false);
            return  new GroupViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Log.d("Image Path", mList.get(position).getImage());

        if (holder instanceof FlowerViewHolder) {

            try {
                if (mList.get(position).getImage() != null) {
                    Picasso.get().load(URLs.Main_URL+mList.get(position).getImage()).resize(300, 300).into(((FlowerViewHolder) holder).mImage);
                    ((FlowerViewHolder) holder).mTitle.setText(mList.get(position).getProductlist());
                    ((FlowerViewHolder) holder).ItemTypeId = mList.get(position).getProductId();
                    ((FlowerViewHolder) holder).categoryId = mList.get(position).getCategoryId();
                    ((FlowerViewHolder) holder).ItemName=mList.get(position).getProductlist();
                    ((FlowerViewHolder) holder).IsVarietyAvailable=mList.get(position).isVarietyAvailable();
                    ((FlowerViewHolder) holder).IsQuality=mList.get(position).isQuality();
                    ((FlowerViewHolder) holder).IsAge=mList.get(position).isAge();
                    ((FlowerViewHolder) holder).IsGroup = mList.get(position).isGroupItem();
                    ((FlowerViewHolder) holder).myFilter=this.myFilter;



                    if (position==mList.size()-1 || position==mList.size()-2)
                    {
                        ViewGroup.MarginLayoutParams cardViewMargins = (ViewGroup.MarginLayoutParams)((FlowerViewHolder) holder).cardView.getLayoutParams();
                        int start = ((ViewGroup.MarginLayoutParams) ((FlowerViewHolder) holder).cardView.getLayoutParams()).getMarginStart();
                        int end = ((ViewGroup.MarginLayoutParams) ((FlowerViewHolder) holder).cardView.getLayoutParams()).getMarginEnd();
                        int top = ((ViewGroup.MarginLayoutParams) ((FlowerViewHolder) holder).cardView.getLayoutParams()).topMargin;
                        cardViewMargins.setMargins(start,top,end,22);
                        ((FlowerViewHolder) holder).cardView.requestLayout();
                    }





                }
            } catch (Exception d) {

            }
        }
        else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
        else if(holder instanceof GroupViewHolder)
        {

        }


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }



    @Override
    public Filter getFilter(){
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<SellOptions> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(exampleListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (SellOptions item : exampleListFull) {
                        if (item.getProductlist().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;

            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList.clear();
                mList.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }
/*

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SellOptions> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SellOptions item : exampleListFull) {
                    if (item.getProductlist().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
*/


    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }


}

class FlowerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImage;
    TextView mTitle;
    String ItemTypeId,categoryId,ItemName;
    List<SubCategoryFilter> mlist;
    Boolean IsVarietyAvailable,IsGroup,IsAge,IsQuality;
    dbBuyerFilter myFilter;
    CardView cardView;

    FlowerViewHolder(View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.cardView);
        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context context=v.getContext();
        Intent intent;

        myFilter.CategoryInsert(categoryId,ItemTypeId);

        intent =  new Intent(context, AllSimilarDataActivity.class);
      //  intent =  new Intent(context, FilterActivity.class);
        intent.putExtra("ItemTypeId",ItemTypeId);
        intent.putExtra("categoryId",categoryId);
        intent.putExtra("ItemName",ItemName);
        intent.putExtra("IsVarietyAvailable",IsVarietyAvailable);
        intent.putExtra("IsQuality",IsQuality);
        intent.putExtra("IsAge",IsAge);
        intent.putExtra("IsGroup",IsGroup.toString());
        intent.putExtra("SortBy","ASC");
        context.startActivity(intent);


    }

}



class LoadingViewHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }


}


class GroupViewHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;
    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }


}