package com.sharpflux.shetkarimaza.model;

public class SubCategory {
    String Image;
    String id;
    String Name;

    public SubCategory(String image, String id, String name) {
        Image = image;
        this.id = id;
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
