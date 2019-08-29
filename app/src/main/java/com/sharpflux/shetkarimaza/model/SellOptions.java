package com.sharpflux.shetkarimaza.model;

public class SellOptions {

    //private int image;
    private String image;
    private String productlist;
    private String productId;
    private  String CategoryId;

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
