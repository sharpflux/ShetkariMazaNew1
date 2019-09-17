package com.sharpflux.shetkarimaza.model;

public class RateData {
    private String fromdate;
    private String todate;
    private String rate;


    public RateData(String fromdate, String todate, String rate) {
        this.fromdate = fromdate;
        this.todate = todate;
        this.rate = rate;
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
}
