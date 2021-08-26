package com.sharpflux.shetkarimaza.model;

public class SimilarList {
    private String ImageUrl;
    private String FullName;
    private String MobileNo;
    private String name;
    private String VarietyName;
    private String Quality;
    private String quantity;
    private String unit;
    private String price;
    private String available_month;
    private String farm_address;
    private String state;
    private String district;
    private String taluka;
    private String village;
    private String hector;
    private String ItemTypeId;
    private String VarietyId;
    private String QualityId;
    private String MeasurementId;
    private String StateId;
    private String DistrictId;
    private String TalukaId;
    private String RequstId;
    private String surveyNo,categoryId;
    private String categeryName;
    private String organic;
    private String certificateNo;
    private String BotanicalName;
    private String   PerUnitPrice;


    public SimilarList(String url, String imageUrl, String fullName, String mobileNo, String name,
                       String varietyName, String quality, String quantity, String unit, String price,
                       String available_month, String farm_address, String state, String district, String taluka,
                       String village, String hector, String itemTypeId, String varietyId, String qualityId,
                       String measurementId, String stateId, String districtId, String talukaId, String requstId) {
        ImageUrl = imageUrl;
        FullName = fullName;
        MobileNo = mobileNo;
        this.name = name;
        VarietyName = varietyName;
        Quality = quality;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.available_month = available_month;
        this.farm_address = farm_address;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.village = village;
        this.hector = hector;
        ItemTypeId = itemTypeId;
        VarietyId = varietyId;
        QualityId = qualityId;
        MeasurementId = measurementId;
        StateId = stateId;
        DistrictId = districtId;
        TalukaId = talukaId;
        RequstId = requstId;
    }

    public SimilarList(String imageUrl, String fullName, String mobileNo, String name, String varietyName,
                       String quality, String quantity, String unit, String price, String available_month,
                       String farm_address, String state, String district, String taluka, String village, String hector,
                       String itemTypeId, String varietyId, String qualityId, String measurementId, String stateId,
                       String districtId, String talukaId, String requstId, String surveyNo, String categoryId, String categeryName,String perUnitPrice) {
        ImageUrl = imageUrl;
        FullName = fullName;
        MobileNo = mobileNo;
        this.name = name;
        VarietyName = varietyName;
        Quality = quality;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.available_month = available_month;
        this.farm_address = farm_address;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.village = village;
        this.hector = hector;
        ItemTypeId = itemTypeId;
        VarietyId = varietyId;
        QualityId = qualityId;
        MeasurementId = measurementId;
        StateId = stateId;
        DistrictId = districtId;
        TalukaId = talukaId;
        RequstId = requstId;
        this.surveyNo = surveyNo;
        this.categoryId = categoryId;
        this.categeryName = categeryName;
        this.PerUnitPrice=perUnitPrice;
    }

    public SimilarList(String imageUrl, String fullName, String mobileNo,
                       String name, String varietyName, String quality, String quantity, String unit,
                       String price, String available_month, String farm_address, String state, String district,
                       String taluka, String village, String hector, String itemTypeId, String varietyId, String qualityId,
                       String measurementId, String stateId, String districtId, String talukaId, String requstId, String surveyNo,
                       String categoryId) {
        ImageUrl = imageUrl;
        FullName = fullName;
        MobileNo = mobileNo;
        this.name = name;
        VarietyName = varietyName;
        Quality = quality;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.available_month = available_month;
        this.farm_address = farm_address;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.village = village;
        this.hector = hector;
        ItemTypeId = itemTypeId;
        VarietyId = varietyId;
        QualityId = qualityId;
        MeasurementId = measurementId;
        StateId = stateId;
        DistrictId = districtId;
        TalukaId = talukaId;
        RequstId = requstId;
        this.surveyNo = surveyNo;
        this.categoryId = categoryId;
    }

    public SimilarList(String imageUrl, String fullName, String mobileNo, String name, String varietyName,
                       String quality, String quantity, String unit, String price, String available_month, String farm_address,
                       String state, String district, String taluka, String village, String hector, String itemTypeId, String varietyId,
                       String qualityId, String measurementId, String stateId, String districtId,
                       String talukaId, String requstId, String surveyNo, String categoryId,
                       String categeryName, String organic, String certificateNo) {
        ImageUrl = imageUrl;
        FullName = fullName;
        MobileNo = mobileNo;
        this.name = name;
        VarietyName = varietyName;
        Quality = quality;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.available_month = available_month;
        this.farm_address = farm_address;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.village = village;
        this.hector = hector;
        ItemTypeId = itemTypeId;
        VarietyId = varietyId;
        QualityId = qualityId;
        MeasurementId = measurementId;
        StateId = stateId;
        DistrictId = districtId;
        TalukaId = talukaId;
        RequstId = requstId;
        this.surveyNo = surveyNo;
        this.categoryId = categoryId;
        this.categeryName = categeryName;
        this.organic = organic;
        this.certificateNo = certificateNo;
    }


    public SimilarList(String imageUrl, String fullName, String mobileNo, String name,
                       String varietyName, String quality, String quantity, String unit, String price,
                       String available_month, String farm_address, String state, String district, String taluka, String village,
                       String hector, String itemTypeId, String varietyId, String qualityId, String measurementId, String stateId, String districtId, String talukaId, String requstId, String surveyNo, String categoryId, String categeryName, String organic, String certificateNo, String botanicalName) {
        ImageUrl = imageUrl;
        FullName = fullName;
        MobileNo = mobileNo;
        this.name = name;
        VarietyName = varietyName;
        Quality = quality;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.available_month = available_month;
        this.farm_address = farm_address;
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.village = village;
        this.hector = hector;
        ItemTypeId = itemTypeId;
        VarietyId = varietyId;
        QualityId = qualityId;
        MeasurementId = measurementId;
        StateId = stateId;
        DistrictId = districtId;
        TalukaId = talukaId;
        RequstId = requstId;
        this.surveyNo = surveyNo;
        this.categoryId = categoryId;
        this.categeryName = categeryName;
        this.organic = organic;
        this.certificateNo = certificateNo;
        BotanicalName = botanicalName;
    }

    public String getBotanicalName() {
        return BotanicalName;
    }

    public void setBotanicalName(String botanicalName) {
        BotanicalName = botanicalName;
    }

    public String getOrganic() {
        return organic;
    }

    public void setOrganic(String organic) {
        this.organic = organic;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCategeryName() {
        return categeryName;
    }

    public void setCategeryName(String categeryName) {
        this.categeryName = categeryName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVarietyName() {
        return VarietyName;
    }

    public void setVarietyName(String varietyName) {
        VarietyName = varietyName;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailable_month() {
        return available_month;
    }

    public void setAvailable_month(String available_month) {
        this.available_month = available_month;
    }

    public String getFarm_address() {
        return farm_address;
    }

    public void setFarm_address(String farm_address) {
        this.farm_address = farm_address;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getHector() {
        return hector;
    }

    public void setHector(String hector) {
        this.hector = hector;
    }

    public String getItemTypeId() {
        return ItemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        ItemTypeId = itemTypeId;
    }

    public String getVarietyId() {
        return VarietyId;
    }

    public void setVarietyId(String varietyId) {
        VarietyId = varietyId;
    }

    public String getQualityId() {
        return QualityId;
    }

    public void setQualityId(String qualityId) {
        QualityId = qualityId;
    }

    public String getMeasurementId() {
        return MeasurementId;
    }

    public void setMeasurementId(String measurementId) {
        MeasurementId = measurementId;
    }

    public String getStateId() {
        return StateId;
    }

    public void setStateId(String stateId) {
        StateId = stateId;
    }

    public String getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(String districtId) {
        DistrictId = districtId;
    }

    public String getTalukaId() {
        return TalukaId;
    }

    public void setTalukaId(String talukaId) {
        TalukaId = talukaId;
    }

    public String getRequstId() {
        return RequstId;
    }

    public void setRequstId(String requstId) {
        RequstId = requstId;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public String getPerUnitPrice() {
        return PerUnitPrice;
    }

    public void setPerUnitPrice(String perUnitPrice) {
        PerUnitPrice = perUnitPrice;
    }
}
