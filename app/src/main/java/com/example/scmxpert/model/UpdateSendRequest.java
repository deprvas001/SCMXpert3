package com.example.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class UpdateSendRequest {
    @SerializedName("shipment_Number")
    private String shipment_number;
    @SerializedName("partner")
    private String partner;
    @SerializedName("eventType")
    private String event_type;
    @SerializedName("dateandTime")
    private String date_time;
    @SerializedName("eventId")
    private String event_id;
    @SerializedName("partnerFrom")
    private String partner_from;
    @SerializedName("eventReferenceNumber")
    private String event_reference_number;
    @SerializedName("typeOfReference")
    private String type_of_reference;
    @SerializedName("comments")
    private String comments;

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

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getPartner_from() {
        return partner_from;
    }

    public void setPartner_from(String partner_from) {
        this.partner_from = partner_from;
    }

    public String getEvent_reference_number() {
        return event_reference_number;
    }

    public void setEvent_reference_number(String event_reference_number) {
        this.event_reference_number = event_reference_number;
    }

    public String getType_of_reference() {
        return type_of_reference;
    }

    public void setType_of_reference(String type_of_reference) {
        this.type_of_reference = type_of_reference;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
