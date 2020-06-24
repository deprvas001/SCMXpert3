package com.development.scmxpert.model;

public class NewShipment {
    String internal_shipment_id;
    String customer_name;
    String partner_name;
    String shipment_number;
    String type_of_reference;
    String shipment_description;
    String route_details;
    String good_type;
    String device;
    String expected_date;

    public String getShipment_number() {
        return shipment_number;
    }

    public void setShipment_number(String shipment_number) {
        this.shipment_number = shipment_number;
    }

    public String getType_of_reference() {
        return type_of_reference;
    }

    public void setType_of_reference(String type_of_reference) {
        this.type_of_reference = type_of_reference;
    }

    public String getShipment_description() {
        return shipment_description;
    }

    public void setShipment_description(String shipment_description) {
        this.shipment_description = shipment_description;
    }

    public String getRoute_details() {
        return route_details;
    }

    public void setRoute_details(String route_details) {
        this.route_details = route_details;
    }

    public String getGood_type() {
        return good_type;
    }

    public void setGood_type(String good_type) {
        this.good_type = good_type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getExpected_date() {
        return expected_date;
    }

    public void setExpected_date(String expected_date) {
        this.expected_date = expected_date;
    }

    public String getInternal_shipment_id() {
        return internal_shipment_id;
    }

    public void setInternal_shipment_id(String internal_shipment_id) {
        this.internal_shipment_id = internal_shipment_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }
}
