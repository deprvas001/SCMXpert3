package com.development.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class Event_Details {
    @SerializedName("bp_Id")
    private String bp_id;
    @SerializedName("bp_Name")
    private String bp_name;
    @SerializedName("event_Name")
    private String event_name;
    @SerializedName("event_Id")
    private String event_Id;
    @SerializedName("photo_Evidence")
    private String evidence;



    public String getBp_id() {
        return bp_id;
    }

    public void setBp_id(String bp_id) {
        this.bp_id = bp_id;
    }

    public String getBp_name() {
        return bp_name;
    }

    public void setBp_name(String bp_name) {
        this.bp_name = bp_name;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_Id() {
        return event_Id;
    }

    public void setEvent_Id(String event_Id) {
        this.event_Id = event_Id;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}
