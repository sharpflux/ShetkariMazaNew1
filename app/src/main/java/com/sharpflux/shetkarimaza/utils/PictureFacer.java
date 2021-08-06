package com.sharpflux.shetkarimaza.utils;

import java.util.Date;

public class PictureFacer implements Comparable<PictureFacer> {

    private String picturName;
    private String picturePath;
    private String pictureSize;
    private String imageUri;
    private String type;


    private Date dateTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date datetime) {
        this.dateTime = datetime;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PictureFacer() {

    }

    public PictureFacer(String picturName, String picturePath, String pictureSize, String imageUri, String type) {
        this.picturName = picturName;
        this.picturePath = picturePath;
        this.pictureSize = pictureSize;
        this.imageUri = imageUri;
        this.type = type;
    }


    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    @Override
    public int compareTo(PictureFacer pictureFacer) {
        return getDateTime().compareTo(pictureFacer.getDateTime());
    }
}
