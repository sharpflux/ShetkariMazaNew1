package com.sharpflux.shetkarimaza.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.Product;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.FruitViewHolder>  {
    private ArrayList<Product> mDataset;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    public DataAdapter(ArrayList<Product>  myDataset, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.recyclerViewItemClickListener = listener;
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
        return mDataset.size();
    }



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