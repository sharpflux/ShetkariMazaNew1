package com.sharpflux.shetkarimaza.model;

public class GroupData {
    String Image;
    String Name;
    String ItemTypeId;




    public GroupData(String image, String name) {
        Image = image;
        Name = name;
    }

    public GroupData(String image, String name, String itemTypeId) {
        Image = image;
        Name = name;
        ItemTypeId = itemTypeId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getItemTypeId() {
        return ItemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        ItemTypeId = itemTypeId;
    }
}
