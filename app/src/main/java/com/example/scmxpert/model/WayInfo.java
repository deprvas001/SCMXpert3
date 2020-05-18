package com.example.scmxpert.model;

public class WayInfo {
    private String wayPoints;
    private String address;
    private String device_Id;
    private String shipment_Num;
    private String sensorUTC;

    public String getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(String wayPoints) {
        this.wayPoints = wayPoints;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevice_Id() {
        return device_Id;
    }

    public void setDevice_Id(String device_Id) {
        this.device_Id = device_Id;
    }

    public String getShipment_Num() {
        return shipment_Num;
    }

    public void setShipment_Num(String shipment_Num) {
        this.shipment_Num = shipment_Num;
    }

    public String getSensorUTC() {
        return sensorUTC;
    }

    public void setSensorUTC(String sensorUTC) {
        this.sensorUTC = sensorUTC;
    }
}
