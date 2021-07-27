package com.sharpflux.shetkarimaza.model;

public class MyCategoryType {


    private String image;
    private String categoryTypeName;
    private String categoryTypeId,UserRegistrationTypeId;


    public MyCategoryType() {

    }


    public MyCategoryType(String image, String categoryTypeName, String categoryTypeId,String userRegistrationTypeId) {
        this.image = image;
        this.categoryTypeName = categoryTypeName;
        this.categoryTypeId = categoryTypeId;
        this.UserRegistrationTypeId=userRegistrationTypeId;
    }

    public String getUserRegistrationTypeId() {
        return UserRegistrationTypeId;
    }

    public void setUserRegistrationTypeId(String userRegistrationTypeId) {
        UserRegistrationTypeId = userRegistrationTypeId;
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
