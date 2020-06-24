package com.development.scmxpert.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddNewRoute {
    private String customerId;
    private String businessId;
    @SerializedName("Route_Id")
    private String routeId;
    private String From;
    private String To;
    @SerializedName("Primary_Mode_Of_Transport")
    private String mode_of_transport;
    @SerializedName("Inco_Term")
    private String incoTerm;
    @SerializedName("Description")
    private String description;
    @SerializedName("Default_Business_Partners_and_Events")
    private List<AddNewRouteEvent> partnerEvents;

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

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }

    public String getIncoTerm() {
        return incoTerm;
    }

    public void setIncoTerm(String incoTerm) {
        this.incoTerm = incoTerm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AddNewRouteEvent> getPartnerEvents() {
        return partnerEvents;
    }

    public void setPartnerEvents(List<AddNewRouteEvent> partnerEvents) {
        this.partnerEvents = partnerEvents;
    }
}
