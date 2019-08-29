package com.sharpflux.shetkarimaza.model;

public class SaveProductInfo {
    private  int id ;
    private String productType;
    private String productVariety;
    private String quality;
    private String quantity;
    private String unit;
    private String expectedPrice;
    private String days;
    private String availablityInMonths;
    private String address;
    private String state;
    private String district;
    private String taluka;
    private String villagenam;
    private String areaheactor;
    private String imagename;

    public SaveProductInfo() {
    }

    public SaveProductInfo(int id, String productType, String productVariety, String quality, String quantity, String unit, String expectedPrice, String days, String availablityInMonths, String address, String state, String district, String taluka, String villagenam, String areaheactor, String imagename) {
        this.id = id;
        this.productType = productType;
        this.productVariety = productVariety;
        this.quality = quality;
        this.quantity = quantity;
        this.unit = unit;
        this.expectedPrice = expectedPrice;
        this.days = days;
        this.availablityInMonths = availablityInMonths;
        this.address = address;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.villagenam = villagenam;
        this.areaheactor = areaheactor;
        this.imagename = imagename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(String expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAvailablityInMonths() {
        return availablityInMonths;
    }

    public void setAvailablityInMonths(String availablityInMonths) {
        this.availablityInMonths = availablityInMonths;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getVillagenam() {
        return villagenam;
    }

    public void setVillagenam(String villagenam) {
        this.villagenam = villagenam;
    }

    public String getAreaheactor() {
        return areaheactor;
    }

    public void setAreaheactor(String areaheactor) {
        this.areaheactor = areaheactor;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
}
