package com.sharpflux.shetkarimaza.model;

public class BuyerFilter {


    private String id;
    private String mName;
    public  boolean IsChecked;



    public String FilterBy;

    public BuyerFilter()
    {

    }

    public BuyerFilter(String id, String mName, boolean isChecked, String filterBy) {
        this.id = id;
        this.mName = mName;
        IsChecked = isChecked;
        FilterBy = filterBy;
    }

    public BuyerFilter(String name) {
        mName = name;
    }

    public BuyerFilter(String id, String mName) {
        this.id = id;
        this.mName = mName;
    }

    public BuyerFilter(String id, String mName, boolean isChecked) {
        this.id = id;
        this.mName = mName;
        IsChecked = isChecked;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return mName;
    }

    public boolean getSelected() {
        return IsChecked;
    }

    public void setSelected(boolean selected) {
        IsChecked = selected;
    }

    public String getFilterBy() {
        return FilterBy;
    }

    public void setFilterBy(String filterBy) {
        FilterBy = filterBy;
    }
}
