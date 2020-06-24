package com.development.scmxpert.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateShipmentDrop {
    @SerializedName("device_Id")
    private List<String> deviceId;
    @SerializedName("typeOfReferences")
    private List<String> reference_type;
    @SerializedName("goods")
    private List<ShipmentGoods> goods_type;
    @SerializedName("route")
    private List<ShipmentGoods> routes_type;
    @SerializedName("internalShipmentId")
    private String internal_shipment_id;

    public List<String> getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(List<String> partner_id) {
        this.partner_id = partner_id;
    }

    @SerializedName("business_Partner_Id")
    private List<String> partner_id;


    public String getInternal_shipment_id() {
        return internal_shipment_id;
    }

    public void setInternal_shipment_id(String internal_shipment_id) {
        this.internal_shipment_id = internal_shipment_id;
    }

    public List<ShipmentGoods> getGoods_type() {
        return goods_type;
    }

    public List<ShipmentGoods> getRoutes_type() {
        return routes_type;
    }

    public void setRoutes_type(List<ShipmentGoods> routes_type) {
        this.routes_type = routes_type;
    }

    public void setGoods_type(List<ShipmentGoods> goods_type) {
        this.goods_type = goods_type;
    }

    public List<String> getReference_type() {
        return reference_type;
    }

    public void setReference_type(List<String> reference_type) {
        this.reference_type = reference_type;
    }

    public List<String> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(List<String> deviceId) {
        this.deviceId = deviceId;
    }
}