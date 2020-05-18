package com.example.scmxpert.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class ShipmentGoods {
    @SerializedName("goods_Item")
    private String good_item;

    @SerializedName("goods_Id")
    private String goods_id;
    @SerializedName("route_Id")
    private String routeId;

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("inco_Term")
    private String inco_term;

    @SerializedName("primary_Mode_Of_Transport")
    private String mode_of_transport;

    @SerializedName("default_Business_Partners_and_Events")
    private List<Event_Details> events;

    public List<Event_Details> getEvents() {
        return events;
    }

    public void setEvents(List<Event_Details> events) {
        this.events = events;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getInco_term() {
        return inco_term;
    }

    public void setInco_term(String inco_term) {
        this.inco_term = inco_term;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }

    public String getGood_item() {
        return good_item;
    }

    public void setGood_item(String good_item) {
        this.good_item = good_item;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
}
