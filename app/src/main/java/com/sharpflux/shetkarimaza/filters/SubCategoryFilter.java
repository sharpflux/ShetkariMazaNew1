package com.sharpflux.shetkarimaza.filters;

public class SubCategoryFilter {


    private String id;
    private String mName;
    public  boolean IsChecked;


    public SubCategoryFilter()
    {

    }

    public SubCategoryFilter(String name) {
        mName = name;
    }

    public SubCategoryFilter(String id, String mName) {
        this.id = id;
        this.mName = mName;
    }

    public SubCategoryFilter(String id, String mName, boolean isChecked) {
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
}
