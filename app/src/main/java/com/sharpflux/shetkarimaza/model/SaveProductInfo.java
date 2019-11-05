package com.sharpflux.shetkarimaza.model;

public class SaveProductInfo {
    private String id ;
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
    private String organic;
    private String certificateno;

   public SaveProductInfo(String id, String productType, String productVariety, String quality, String quantity,
                           String unit, String expectedPrice, String days, String availablityInMonths, String address,
                           String state, String district, String taluka,
                           String villagenam, String areaheactor, String imagename, String organic, String certificateno) {
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
        this.organic = organic;
        this.certificateno = certificateno;
    }

    public SaveProductInfo(String id, String productType, String productVariety,
                           String quality, String quantity, String unit, String expectedPrice, String days,
                           String availablityInMonths, String address, String state, String district, String taluka,
                           String villagenam, String areaheactor, String imagename) {
        this.id = this.id;
        this.productType = this.productType;
        this.productVariety = this.productVariety;
        this.quality = this.quality;
        this.quantity = this.quantity;
        this.unit = this.unit;
        this.expectedPrice = this.expectedPrice;
        this.days = this.days;
        this.availablityInMonths = this.availablityInMonths;
        this.address = this.address;
        this.state = this.state;
        this.district = this.district;
        this.taluka = this.taluka;
        this.villagenam = this.villagenam;
        this.areaheactor = this.areaheactor;
        this.imagename = this.imagename;
    }

    public String getOrganic() {
        return organic;
    }

    public void setOrganic(String organic) {
        this.organic = organic;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
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
