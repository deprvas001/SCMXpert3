package com.development.scmxpert.model;

import java.util.List;

public class ShippmentList {
    public List<Shippment> getShippments() {
        return shippments;
    }

    public void setShippments(List<Shippment> shippments) {
        this.shippments = shippments;
    }

    private List<Shippment> shippments;
}
