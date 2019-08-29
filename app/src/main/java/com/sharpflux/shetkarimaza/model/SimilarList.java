package com.sharpflux.shetkarimaza.model;

public class SimilarList {
    private String ImageUrl;
    private String name;
    private String quantity;
    private String price;

    public SimilarList(String ImageUrl, String name, String quantity, String price) {
        this.ImageUrl = ImageUrl;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImageId() {
        return ImageUrl;
    }

    public void setImageId(String imageId) {
        this.ImageUrl = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
