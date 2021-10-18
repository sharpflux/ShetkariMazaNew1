package com.sharpflux.shetkarimaza.model;

/* renamed from: ws.design.dailygrocery.model.CouponModel */
public class CouponModel {
    String txt_CouponCode;
    String txt_CouponDiscount;
    String txt_CouponTitle;

    String txt_Descriptions;

    String PackageId;

    public String getTxt_CouponDiscount() {
        return this.txt_CouponDiscount;
    }

    public void setTxt_CouponDiscount(String txt_CouponDiscount2) {
        this.txt_CouponDiscount = txt_CouponDiscount2;
    }

    public String getPackageId() {
        return PackageId;
    }

    public void setPackageId(String packageId) {
        PackageId = packageId;
    }

    public String getTxt_CouponCode() {
        return this.txt_CouponCode;
    }

    public void setTxt_CouponCode(String txt_CouponCode2) {
        this.txt_CouponCode = txt_CouponCode2;
    }

    public String getTxt_CouponTitle() {
        return this.txt_CouponTitle;
    }

    public void setTxt_CouponTitle(String txt_CouponTitle2) {
        this.txt_CouponTitle = txt_CouponTitle2;
    }

    public String getTxt_Descriptions() {
        return txt_Descriptions;
    }

    public void setTxt_Descriptions(String txt_Descriptions) {
        this.txt_Descriptions = txt_Descriptions;
    }

    public CouponModel(String txt_CouponDiscount2, String txt_CouponCode2, String txt_CouponTitle2,String Descriptions,String packageId) {
        this.txt_CouponDiscount = txt_CouponDiscount2;
        this.txt_CouponCode = txt_CouponCode2;
        this.txt_CouponTitle = txt_CouponTitle2;
        this.txt_Descriptions = Descriptions;
        this.PackageId = packageId;
    }
}
