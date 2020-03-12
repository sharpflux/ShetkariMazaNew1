package com.sharpflux.shetkarimaza.model;

import java.util.ArrayList;

public class Product {

    private String name;
    private String productId;
    private ArrayList<Product> productlist;
    private String BotanicalName;
    private  boolean IsVarietyAvailable;

    public Product(String name, String productId,String BotanicalName) {
        this.name = name;
        this.productId = productId;
        this.BotanicalName = BotanicalName;
    }

    public Product(String name, String productId, ArrayList productlist,String BotanicalName) {
        this.name = name;
        this.productId = productId;
        this.productlist = productlist;
        this.BotanicalName = BotanicalName;
    }

    public Product(String name, String productId,String BotanicalName,boolean IsVarietyAvailable) {
        this.name = name;
        this.productId = productId;
        this.productlist = productlist;
        this.BotanicalName = BotanicalName;
        this.IsVarietyAvailable=IsVarietyAvailable;
    }

    public Product(String name, String productId) {
        this.name = name;
        this.productId = productId;
    }

    public String getBotanicalName() {
        return BotanicalName;
    }

    public void setBotanicalName(String botanicalName) {
        BotanicalName = botanicalName;
    }

    public ArrayList<Product> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList productlist) {
        this.productlist = productlist;
    }

    public Product(String name) {
        this.name = name;
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

    public boolean isVarietyAvailable() {
        return IsVarietyAvailable;
    }

    public void setVarietyAvailable(boolean varietyAvailable) {
        IsVarietyAvailable = varietyAvailable;
    }
}
