package com.sharpflux.shetkarimaza.model;

public class MyProcessor {

    private String ItemTypeId;
    private String ItemName;
    public  boolean IsChecked;


    public MyProcessor(String itemTypeId, String itemName) {
        ItemTypeId = itemTypeId;
        ItemName = itemName;
    }

    public MyProcessor(String itemTypeId, String itemName, boolean isChecked) {
        ItemTypeId = itemTypeId;
        ItemName = itemName;
        IsChecked = isChecked;
    }

    public String getItemTypeId() {
        return ItemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        ItemTypeId = itemTypeId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }

    public boolean getSelected() {
        return IsChecked;
    }

    public void setSelected(boolean selected) {
        IsChecked = selected;
    }

}
