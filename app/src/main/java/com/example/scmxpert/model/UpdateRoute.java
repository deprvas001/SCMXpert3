package com.example.scmxpert.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateRoute {
    private String customerId;
    private String businessId;
    private String route_id;
    private String shipFrom;
    private String shipTo;
    private String primaryMode;
    private String incoTerms;
    private String standradDuration;
    private String routeStatus;
    private String description;
    @SerializedName("default_Business_Partners_And_Events")
    private List<Event_Details> partner_events;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getShipFrom() {
        return shipFrom;
    }

    public void setShipFrom(String shipFrom) {
        this.shipFrom = shipFrom;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getPrimaryMode() {
        return primaryMode;
    }

    public void setPrimaryMode(String primaryMode) {
        this.primaryMode = primaryMode;
    }

    public String getIncoTerms() {
        return incoTerms;
    }

    public void setIncoTerms(String incoTerms) {
        this.incoTerms = incoTerms;
    }

    public String getStandradDuration() {
        return standradDuration;
    }

    public void setStandradDuration(String standradDuration) {
        this.standradDuration = standradDuration;
    }

    public String getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Event_Details> getPartner_events() {
        return partner_events;
    }

    public void setPartner_events(List<Event_Details> partner_events) {
        this.partner_events = partner_events;
    }
}
