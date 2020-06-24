package com.development.scmxpert.model;

public class UpdateEventModel {
    private String event_id;
    private String partner;
    private String event;
    private String date;
    private String event_status;
    private String partner_id;
    public UpdateEventModel(){

    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public UpdateEventModel(String event_id, String partner, String event, String date, String event_status, String partner_id){
        this.event_id = event_id;
        this.partner = partner;
        this.event = event;
        this.date = date;
        this.event_status = event_status;
        this.partner_id = partner_id;

    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }
}
