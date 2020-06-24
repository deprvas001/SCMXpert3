package com.development.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class ShipmentDetail {
    @SerializedName("shipment_Num")
    private String id;
    @SerializedName("device_Id")
    private String device_id;
    @SerializedName("internal_temperature")
    private String internal_tmp;
    @SerializedName("event_Name")
    private String event_name;
    @SerializedName("bat")
    private String battery;
    @SerializedName("report_type")
    private String report_type;
    @SerializedName("mode_of_Transport")
    private String mode_of;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitdue;
    @SerializedName("event_Status")
    private String event_status;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitdue() {
        return latitdue;
    }

    public void setLatitdue(String latitdue) {
        this.latitdue = latitdue;
    }

    public ShipmentDetail(String id, String device_id, String internal_tmp, String event_name,
                          String battery, String report_type, String mode_of,
                          String longitude, String latitdue,String event_status){
        this.id= id;
        this.device_id = device_id;
        this.internal_tmp = internal_tmp;
        this.event_name = event_name;
        this.battery = battery;
        this.report_type = report_type;
        this.mode_of = mode_of;
        this.longitude = longitude;
        this.longitude = latitdue;
        this.event_status = event_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getInternal_tmp() {
        return internal_tmp;
    }

    public void setInternal_tmp(String internal_tmp) {
        this.internal_tmp = internal_tmp;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getMode_of() {
        return mode_of;
    }

    public void setMode_of(String mode_of) {
        this.mode_of = mode_of;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }
}
