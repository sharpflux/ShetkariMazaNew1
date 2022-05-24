package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.sliderModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    ArrayList<Uri> arrayList_imageSlider;
    ArrayList<sliderModel> sliderModelArrayList;

    public SliderAdapter(Context context, ArrayList<sliderModel> arrayList_imageSlider) {
        this.context = context;
        this.sliderModelArrayList = arrayList_imageSlider;

    }

    @Override
    public int getCount() {
        return sliderModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_image_slider, null);

        ImageView imageView_slider = (ImageView) view.findViewById(R.id.imageView_slider);

        Picasso.get().load(sliderModelArrayList.get(position).getSliderImgUrl()).into(imageView_slider);


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}