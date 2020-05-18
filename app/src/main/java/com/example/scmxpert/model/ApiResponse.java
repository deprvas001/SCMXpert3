package com.example.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("Status")
    private Boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
