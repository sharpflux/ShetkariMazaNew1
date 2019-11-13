package com.sharpflux.shetkarimaza.model;

public class CursorData {
    String ImageUrl;
    String name;
    String variety;
    String quality;
    String price;
    String PKey;

    public CursorData(String imageUrl, String name, String variety, String quality, String price) {
        ImageUrl = imageUrl;
        this.name = name;
        this.variety = variety;
        this.quality = quality;
        this.price = price;
    }

    public CursorData(String imageUrl, String name, String variety, String quality, String price, String PKey) {
        ImageUrl = imageUrl;
        this.name = name;
        this.variety = variety;
        this.quality = quality;
        this.price = price;
        this.PKey = PKey;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getPrice() {
        return price;
    }

    public String getPKey() {
        return PKey;
    }

    public void setPKey(String PKey) {
        this.PKey = PKey;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
