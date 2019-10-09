package com.sharpflux.shetkarimaza.model;

public class ContactDetail {

    private String image;
    private String fullName;
    private String address;
    private  String mobileNo;

    public ContactDetail(String image, String fullName, String address, String mobileNo) {
        this.image = image;
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
