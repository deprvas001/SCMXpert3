package com.example.scmxpert.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateEventDetails {
    @SerializedName("temp_1")
    private String temp;
    @SerializedName("shipment_Id")
    private String shipment_id;
    @SerializedName("shipment_Num")
    private String shipment_num;
    @SerializedName("customer_Id")
    private String customer_id;
    @SerializedName("device_Id")
    private String device_id;
    @SerializedName("event_Status")
    private String event_status;
    @SerializedName("current_Terminal")
    private String current_terminal;
    @SerializedName("eventReferenceNumber")
    private String event_reference_num;
    @SerializedName("mode_of_Transport")
    private String mode_of_transport;
    @SerializedName("ship_Date_From_BP")
    private String ship_date_fromBP;
    @SerializedName("shipment_Number")
    private String shipmentNumber;
    @SerializedName("typeOfReference")
    private String type_of_reference;
    @SerializedName("internal_temperature")
    private String internal_temperature;
    @SerializedName("expected_Date_At_BP")
    private String expected_date_atBp;
    @SerializedName("event_Exec_Date")
    private String event_exec_date;
    @SerializedName("event_SNo")
    private int event_SNO;
    @SerializedName("comments")
    private List<String> shipment_description;
    @SerializedName("partner_From")
    private String partner_from;
    @SerializedName("event_Name")
    private String event_Name;
    @SerializedName("event_Id")
    private String event_ID;
    @SerializedName("partner_To")
    private String partner_To;
    @SerializedName("imei_Number")
    private String imei_number;
    @SerializedName("parent_Device_Id")
    private String parent_device_id;
    @SerializedName("partner")
    private String partner;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
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

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }

    public String getCurrent_terminal() {
        return current_terminal;
    }

    public void setCurrent_terminal(String current_terminal) {
        this.current_terminal = current_terminal;
    }

    public String getEvent_reference_num() {
        return event_reference_num;
    }

    public void setEvent_reference_num(String event_reference_num) {
        this.event_reference_num = event_reference_num;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }

    public String getShip_date_fromBP() {
        return ship_date_fromBP;
    }

    public void setShip_date_fromBP(String ship_date_fromBP) {
        this.ship_date_fromBP = ship_date_fromBP;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getType_of_reference() {
        return type_of_reference;
    }

    public void setType_of_reference(String type_of_reference) {
        this.type_of_reference = type_of_reference;
    }

    public String getInternal_temperature() {
        return internal_temperature;
    }

    public void setInternal_temperature(String internal_temperature) {
        this.internal_temperature = internal_temperature;
    }

    public String getExpected_date_atBp() {
        return expected_date_atBp;
    }

    public void setExpected_date_atBp(String expected_date_atBp) {
        this.expected_date_atBp = expected_date_atBp;
    }

    public String getEvent_exec_date() {
        return event_exec_date;
    }

    public void setEvent_exec_date(String event_exec_date) {
        this.event_exec_date = event_exec_date;
    }

    public int getEvent_SNO() {
        return event_SNO;
    }

    public void setEvent_SNO(int event_SNO) {
        this.event_SNO = event_SNO;
    }

    public List<String> getShipment_description() {
        return shipment_description;
    }

    public void setShipment_description(List<String> shipment_description) {
        this.shipment_description = shipment_description;
    }

    public String getPartner_from() {
        return partner_from;
    }

    public void setPartner_from(String partner_from) {
        this.partner_from = partner_from;
    }

    public String getEvent_Name() {
        return event_Name;
    }

    public void setEvent_Name(String event_Name) {
        this.event_Name = event_Name;
    }

    public String getEvent_ID() {
        return event_ID;
    }

    public void setEvent_ID(String event_ID) {
        this.event_ID = event_ID;
    }

    public String getPartner_To() {
        return partner_To;
    }

    public void setPartner_To(String partner_To) {
        this.partner_To = partner_To;
    }

    public String getImei_number() {
        return imei_number;
    }

    public void setImei_number(String imei_number) {
        this.imei_number = imei_number;
    }

    public String getParent_device_id() {
        return parent_device_id;
    }

    public void setParent_device_id(String parent_device_id) {
        this.parent_device_id = parent_device_id;
    }
}
