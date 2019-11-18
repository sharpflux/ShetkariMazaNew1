package com.sharpflux.shetkarimaza.model;

public class GroupData {
    String Image;
    String Name;
    String TypeId;

    public GroupData(String image, String name, String typeId) {
        Image = image;
        Name = name;
        TypeId = typeId;
    }

    public GroupData(String image, String name) {
        Image = image;
        Name = name;
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

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }
}
