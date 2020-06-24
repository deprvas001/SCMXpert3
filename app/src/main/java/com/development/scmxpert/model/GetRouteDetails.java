package com.development.scmxpert.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRouteDetails {
    private String from;
    private String description;
    @SerializedName("primary_Mode_Of_Transport")
    private String mode_of_transport;
    private String to;
    private String  route_Id;
    private String businessId;
    @SerializedName("std_Duration")
    private String standard_duration;
    @SerializedName("custRouteId")
    private String customer_route_id;
    private String inco_Term;
    @SerializedName("default_Business_Partners_and_Events")
    private List<Event_Details> businessPartnerEvent;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRoute_Id() {
        return route_Id;
    }

    public void setRoute_Id(String route_Id) {
        this.route_Id = route_Id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getStandard_duration() {
        return standard_duration;
    }

    public void setStandard_duration(String standard_duration) {
        this.standard_duration = standard_duration;
    }

    public String getCustomer_route_id() {
        return customer_route_id;
    }

    public void setCustomer_route_id(String customer_route_id) {
        this.customer_route_id = customer_route_id;
    }

    public String getInco_Term() {
        return inco_Term;
    }

    public void setInco_Term(String inco_Term) {
        this.inco_Term = inco_Term;
    }

    public List<Event_Details> getBusinessPartnerEvent() {
        return businessPartnerEvent;
    }

    public void setBusinessPartnerEvent(List<Event_Details> businessPartnerEvent) {
        this.businessPartnerEvent = businessPartnerEvent;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
