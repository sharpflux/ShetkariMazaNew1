package com.sharpflux.shetkarimaza.model;

public class MyData {

    private int flowerName;
    private String flowerDescription;
    private int flowerImage;

    public MyData( int flowerName, String flowerDescription, int flowerImage) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
        this.flowerImage = flowerImage;
    }

    public  int getFlowerName() {
        return flowerName;
    }

    public String getFlowerDescription() {
        return flowerDescription;
    }

    public int getFlowerImage() {
        return flowerImage;
    }
}
