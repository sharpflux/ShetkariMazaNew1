package com.sharpflux.shetkarimaza.model;

public class SellOptions {

    //private int image;
    private String image;
    private String productlist;
    private String productId;
    private  String CategoryId;
    private  String ItemName;
    private boolean IsVarietyAvailable,IsQuality,IsAge;
    private boolean IsGroupItem;

    public SellOptions(String image, String productlist, String productId) {
        this.image = image;
        this.productlist = productlist;
        this.productId = productId;
    }

    public SellOptions(String image, String productlist, String productId, String categoryId) {
        this.image = image;
        this.productlist = productlist;
        this.productId = productId;
        CategoryId = categoryId;
    }


    public SellOptions(String image, String productlist, String productId, String categoryId, String itemName) {
        this.image = image;
        this.productlist = productlist;
        this.productId = productId;
        CategoryId = categoryId;
        ItemName = itemName;
    }

    public SellOptions(String image, String productlist, String productId, String categoryId, String itemName, boolean isVarietyAvailable,boolean isGroupItem) {
        this.image = image;
        this.productlist = productlist;
        this.productId = productId;
        CategoryId = categoryId;
        ItemName = itemName;
        IsVarietyAvailable = isVarietyAvailable;
        IsGroupItem=isGroupItem;
    }

    public SellOptions(String image, String productlist, String productId, String categoryId, String itemName, boolean isVarietyAvailable,boolean isQuality,boolean isAge,boolean isGroupItem) {
        this.image = image;
        this.productlist = productlist;
        this.productId = productId;
        CategoryId = categoryId;
        ItemName = itemName;
        IsVarietyAvailable = isVarietyAvailable;
        IsQuality = isQuality;
        IsAge = isAge;
        IsGroupItem=isGroupItem;
    }

    public boolean isQuality() {
        return IsQuality;
    }

    public void setQuality(boolean quality) {
        IsQuality = quality;
    }

    public boolean isAge() {
        return IsAge;
    }

    public void setAge(boolean age) {
        IsAge = age;
    }

    public boolean isGroupItem() {
        return IsGroupItem;
    }

    public void setGroupItem(boolean groupItem) {
        IsGroupItem = groupItem;
    }

    public boolean isVarietyAvailable() {
        return IsVarietyAvailable;
    }

    public void setVarietyAvailable(boolean varietyAvailable) {
        IsVarietyAvailable = varietyAvailable;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public SellOptions(String image, String productlist) {
        this.image = image;
        this.productlist = productlist;
    }
    public SellOptions( String productlist) {

        this.productlist = productlist;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductlist() {
        return productlist;
    }

    public void setProductlist(String productlist) {
        this.productlist = productlist;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
