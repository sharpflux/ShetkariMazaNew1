package com.sharpflux.shetkarimaza.model;

public class ContactDetail {

    private String image;
    private String fullName;
    private String address;
    private  String mobileNo;
    private  String state;
    private  String district;
    private  String taluka,VehicalType,VehicalNo,Rates,TypeView;


    public ContactDetail(String image, String fullName, String address, String mobileNo) {
        this.image = image;
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public ContactDetail(String image, String fullName, String address, String mobileNo, String state, String district, String taluka) {
        this.image = image;
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
    }

    public ContactDetail(String image, String fullName, String address, String mobileNo, String state, String district, String taluka,String vehicalType,String vehicalNo,String rates,String typeView) {
        this.image = image;
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.VehicalType=vehicalType;
        this.VehicalNo=vehicalNo;
        this.Rates=rates;
        this.TypeView=typeView;
    }

    public String getTypeView() {
        return TypeView;
    }

    public void setTypeView(String typeView) {
        TypeView = typeView;
    }

    public String getVehicalType() {
        return VehicalType;
    }

    public void setVehicalType(String vehicalType) {
        VehicalType = vehicalType;
    }

    public String getVehicalNo() {
        return VehicalNo;
    }

    public void setVehicalNo(String vehicalNo) {
        VehicalNo = vehicalNo;
    }

    public String getRates() {
        return Rates;
    }

    public void setRates(String rates) {
        Rates = rates;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
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
