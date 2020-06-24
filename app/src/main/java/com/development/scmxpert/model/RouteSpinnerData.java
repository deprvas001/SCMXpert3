package com.development.scmxpert.model;

import androidx.annotation.NonNull;

import java.util.List;

public class RouteSpinnerData {
    private String route_id;
    private String route_from;
    private String route_to;
    private String mode_of_transport;
    private String inco_term;
    private List<Event_Details> event_details;

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getRoute_from() {
        return route_from;
    }

    public void setRoute_from(String route_from) {
        this.route_from = route_from;
    }

    public String getRoute_to() {
        return route_to;
    }

    public void setRoute_to(String route_to) {
        this.route_to = route_to;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }

    public String getInco_term() {
        return inco_term;
    }

    public void setInco_term(String inco_term) {
        this.inco_term = inco_term;
    }

    public List<Event_Details> getEvent_details() {
        return event_details;
    }

    public void setEvent_details(List<Event_Details> event_details) {
        this.event_details = event_details;
    }

    @NonNull
    @Override
    public String toString() {
        return route_from+" "+route_to+" "+mode_of_transport+" "+inco_term;
    }
}
