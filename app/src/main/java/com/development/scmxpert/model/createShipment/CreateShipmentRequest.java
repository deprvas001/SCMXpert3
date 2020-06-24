package com.development.scmxpert.model.createShipment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateShipmentRequest {
    @SerializedName("internalShipmentId")
    private String internalShipmentId;
    @SerializedName("shipment_Number")
    private String shipmentId;
    @SerializedName("shipment_Num")
    private String shipmentNumber;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("typeOfReference")
    private String typeOfReference;
    @SerializedName("comments")
    private List<String> comment;
    @SerializedName("routeId")
    private String routeId;
    @SerializedName("routeFrom")
    private String routeFrom;
    @SerializedName("routeTo")
    private String routeTo;
    @SerializedName("goodsId")
    private String goodsId;
    @SerializedName("goodsType")
    private String goodsType;
    @SerializedName("parnterFrom")
    private String parnterFrom;
    @SerializedName("deviceId")
    private String deviceId;
    private List<CreateEvent> allEvents;
    private String incoTerms;
    private String mode;
    private String temp;
    private String rH;
    private String datee;
    private String estimatedDeliveryDate;
    private String event;

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getInternalShipmentId() {
        return internalShipmentId;
    }

    public void setInternalShipmentId(String internalShipmentId) {
        this.internalShipmentId = internalShipmentId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTypeOfReference() {
        return typeOfReference;
    }

    public void setTypeOfReference(String typeOfReference) {
        this.typeOfReference = typeOfReference;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteFrom() {
        return routeFrom;
    }

    public void setRouteFrom(String routeFrom) {
        this.routeFrom = routeFrom;
    }

    public String getRouteTo() {
        return routeTo;
    }

    public void setRouteTo(String routeTo) {
        this.routeTo = routeTo;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getParnterFrom() {
        return parnterFrom;
    }

    public void setParnterFrom(String parnterFrom) {
        this.parnterFrom = parnterFrom;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<CreateEvent> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<CreateEvent> allEvents) {
        this.allEvents = allEvents;
    }

    public String getIncoTerms() {
        return incoTerms;
    }

    public void setIncoTerms(String incoTerms) {
        this.incoTerms = incoTerms;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getrH() {
        return rH;
    }

    public void setrH(String rH) {
        this.rH = rH;
    }



    public String getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
}
