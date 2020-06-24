package com.development.scmxpert.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Shippment implements Serializable {
    @SerializedName("estimated_Delivery_Date")
    private String est_delivery_date;
    @SerializedName("delivered_Date")
    private String delivery_date;
    @SerializedName("delivered_Status")
    private String delivery_status;
    @SerializedName("type_Of_Reference")
    private String type_reference;
    @SerializedName("event_Status")
    private String event_status;
    @SerializedName("shipment_Id")
    private String shipment_id;
    @SerializedName("shipment_Num")
    private String shipment_num;
    @SerializedName("customer_Id")
    private String customer_id;
    @SerializedName("route_From")
    private String route_form;
    @SerializedName("created_By")
    private String created_by;
    @SerializedName("route_To")
    private String route_to;
    @SerializedName("goods_Desc")
    private String goods_desc;
    @SerializedName("created_Date")
    private String created_date;
    @SerializedName("departments")
    private String departments;
    @SerializedName("device_Id")
    private String device_id;
    @SerializedName("wayPoints")
    private List<Object> waypoint;

    @SerializedName("event_status")
    private String status_progress;

    public String getStatus_progress() {
        return status_progress;
    }

    public void setStatus_progress(String status_progress) {
        this.status_progress = status_progress;
    }

    public List<Object> getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(List<Object> waypoint) {
        this.waypoint = waypoint;
    }

    public String getEst_delivery_date() {
        return est_delivery_date;
    }

    public void setEst_delivery_date(String est_delivery_date) {
        this.est_delivery_date = est_delivery_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getType_reference() {
        return type_reference;
    }

    public void setType_reference(String type_reference) {
        this.type_reference = type_reference;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }

    public String getShipment_id() {
        return shipment_id;
    }

    public void setShipment_id(String shipment_id) {
        this.shipment_id = shipment_id;
    }

    public String getShipment_num() {
        return shipment_num;
    }

    public void setShipment_num(String shipment_num) {
        this.shipment_num = shipment_num;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getRoute_form() {
        return route_form;
    }

    public void setRoute_form(String route_form) {
        this.route_form = route_form;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getRoute_to() {
        return route_to;
    }

    public void setRoute_to(String route_to) {
        this.route_to = route_to;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
