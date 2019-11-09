package com.sharpflux.shetkarimaza.model;

public class TransporterDetails {

    String id;
    String VehicleType;
    String VehicleNo;
    String Rate;

    public TransporterDetails(String id, String vehicleType, String vehicleNo, String rate) {
        this.id = id;
        VehicleType = vehicleType;
        VehicleNo = vehicleNo;
        Rate = rate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
