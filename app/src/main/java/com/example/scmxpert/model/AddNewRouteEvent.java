package com.example.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class AddNewRouteEvent {
    @SerializedName("BP_Id")
    private String bpId;
    @SerializedName("BP_Name")
    private String bpName;
    @SerializedName("Event_Id")
    private String eventId;
    @SerializedName("Event_Name")
    private String eventName;

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
