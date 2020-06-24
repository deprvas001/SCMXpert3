package com.development.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class CreateShipmentResponse {
    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
