package com.example.scmxpert.model;

public class UpdateEventPost {
    private String shipment_number;
    private String partner;
    private String event;
    private String dateTime;
    private String eventId;
    private String partnerFrom;
    private String referenceNumber;
    private String reference_type;
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReference_type() {
        return reference_type;
    }

    public void setReference_type(String reference_type) {
        this.reference_type = reference_type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
