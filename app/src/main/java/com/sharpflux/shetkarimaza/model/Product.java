package com.sharpflux.shetkarimaza.model;

public class Product {

    private String name;
    private String productId;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, String productId) {
        this.name = name;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
