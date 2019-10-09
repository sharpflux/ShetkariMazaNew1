package com.sharpflux.shetkarimaza.model;

public class MyCategoryType {


    private String image;
    private String categoryTypeName;
    private String categoryTypeId;


    public MyCategoryType() {

    }


    public MyCategoryType(String image, String categoryTypeName, String categoryTypeId) {
        this.image = image;
        this.categoryTypeName = categoryTypeName;
        this.categoryTypeId = categoryTypeId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryTypeName() {
        return categoryTypeName;
    }

    public void setCategoryTypeName(String categoryTypeName) {
        this.categoryTypeName = categoryTypeName;
    }

    public String getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(String categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }
}
