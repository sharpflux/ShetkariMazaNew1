package com.sharpflux.shetkarimaza.model;

public class RateData {
    private String fromdate;
    private String todate;
    private String rate;
    private String unit;

    public RateData(String fromdate, String todate, String rate, String unit) {
        this.fromdate = fromdate;
        this.todate = todate;
        this.rate = rate;
        this.unit = unit;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
