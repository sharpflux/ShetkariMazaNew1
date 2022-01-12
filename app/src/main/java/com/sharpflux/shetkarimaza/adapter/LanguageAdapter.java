package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.MyLanguage;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.SingleViewHolder> {

    private Context context;
    private ArrayList<MyLanguage> employees;
    public int checkedPosition = -1;
    private int lastSelectedPosition = -1;
    public LanguageAdapter(Context context, ArrayList<MyLanguage> employees) {
        this.context = context;
        this.employees = employees;
    }

    public void setEmployees(ArrayList<MyLanguage> employees) {
        this.employees = new ArrayList<>();
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_language, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
         // singleViewHolder.bind(employees.get(position));

      singleViewHolder.textView.setText(employees.get(position).getLanguageName());
        if (position == -1) {
            singleViewHolder.imageView.setVisibility(View.GONE);
        } else {
            if(employees.get(position).isSelected()){
                singleViewHolder.imageView.setVisibility(View.VISIBLE);
            }
            else {
                singleViewHolder.imageView.setVisibility(View.GONE);
            }
        }

        singleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                   // employees.get(position).setSelected(true);
                    checkedPosition = position;
                    for(int i=0;i<=employees.size()-1;i++){
                        if(position!=i) {
                            employees.get(i).setSelected(false);
                            //singleViewHolder.imageView.setVisibility(View.VISIBLE);
                        }
                        else {
                            //singleViewHolder.imageView.setVisibility(View.GONE);

                            employees.get(i).setSelected(true);
                        }
                    }
                    notifyDataSetChanged();



            }
        });

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final MyLanguage employee) {
            if (checkedPosition == -1) {
                imageView.setVisibility(View.GONE);
                employee.setSelected(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    imageView.setVisibility(View.VISIBLE);
                    employee.setSelected(true);
                } else {
                    imageView.setVisibility(View.GONE);
                    employee.setSelected(false);
                }
            }
            textView.setText(employee.getLanguageName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        employee.setSelected(true);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public MyLanguage getSelected() {
        if (checkedPosition != -1) {

            return employees.get(checkedPosition);
        }
        return null;
    }

}