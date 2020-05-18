package com.example.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class CompleteShipmentModel {
    @SerializedName("shipment_Number")
    private String shipment_number;
    private String partner;
    private String event;
    private String dateandTime;
    private String eventId;
    private String partnerFrom;
    private String receivingLocation;
    private String receivingReferenceNumber;
    private String typeOfReference;
    private String comments;
    private String deviceReturnLocation;

    public String getShipment_number() {
        return shipment_number;
    }

    public void setShipment_number(String shipment_number) {
        this.shipment_number = shipment_number;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDateandTime() {
        return dateandTime;
    }

    public void setDateandTime(String dateandTime) {
        this.dateandTime = dateandTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPartnerFrom() {
        return partnerFrom;
    }

    public void setPartnerFrom(String partnerFrom) {
        this.partnerFrom = partnerFrom;
    }

    public String getReceivingLocation() {
        return receivingLocation;
    }

    public void setReceivingLocation(String receivingLocation) {
        this.receivingLocation = receivingLocation;
    }

    public String getReceivingReferenceNumber() {
        return receivingReferenceNumber;
    }

    public void setReceivingReferenceNumber(String receivingReferenceNumber) {
        this.receivingReferenceNumber = receivingReferenceNumber;
    }

    public String getTypeOfReference() {
        return typeOfReference;
    }

    public void setTypeOfReference(String typeOfReference) {
        this.typeOfReference = typeOfReference;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeviceReturnLocation() {
        return deviceReturnLocation;
    }

    public void setDeviceReturnLocation(String deviceReturnLocation) {
        this.deviceReturnLocation = deviceReturnLocation;
    }
}
