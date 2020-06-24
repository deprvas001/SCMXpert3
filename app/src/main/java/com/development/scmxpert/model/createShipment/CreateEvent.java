package com.development.scmxpert.model.createShipment;

import com.google.gson.annotations.SerializedName;

public class CreateEvent {
    @SerializedName("event_Id")
    private String eventId;
    private String bp_Id;
    private String partner;
    private String event;
    /*private String date;*/
    private String evidence;
    private String event_Status;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getBp_Id() {
        return bp_Id;
    }

    public void setBp_Id(String bp_Id) {
        this.bp_Id = bp_Id;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getEvent_Status() {
        return event_Status;
    }

    public void setEvent_Status(String event_Status) {
        this.event_Status = event_Status;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

   /* public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }*/
}
