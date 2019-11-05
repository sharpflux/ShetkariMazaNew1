package com.sharpflux.shetkarimaza.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.FruitViewHolder> implements Filterable {
    private ArrayList<Product> mDataset;
    private List<Product> exampleListFull;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    public DataAdapter(ArrayList<Product>  myDataset, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.recyclerViewItemClickListener = listener;
        exampleListFull = new ArrayList<>(mDataset);
    }

    @NonNull
    @Override
    public FruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);

        FruitViewHolder vh = new FruitViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull FruitViewHolder fruitViewHolder, int i) {
       fruitViewHolder.mTextView.setText(mDataset.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    @Override
    public Filter getFilter(){
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<Product> founded = new ArrayList<>();
                    for(Product item: mDataset){
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }

                    result.values = founded;
                    result.count = founded.size();
                }else {
                    result.values = mDataset;
                    result.count = mDataset.size();
                }
                return result;


            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataset.clear();
                for (String item : (List<String>) results.values) {
                   // exampleListFull.add();
                }
                notifyDataSetChanged();
            }
        };
    }

   /* private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Product item : exampleListFull) {
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
            mDataset.clear();
            mDataset.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
*/

    public  class FruitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView;

    public FruitViewHolder(View v) {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.textViewName);

        mDataset.size();
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewItemClickListener.clickOnItem(mDataset.get(this.getAdapterPosition()));

    }
}

public interface RecyclerViewItemClickListener {
    void clickOnItem(Product data);
}
}