package com.example.scmxpert.model.forgotModel;

import com.google.gson.annotations.SerializedName;

public class ForgotResponse {
    @SerializedName("message")
    private String message;
     @SerializedName("Status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
