package com.example.scmxpert.model.createShipment;

import com.example.scmxpert.model.forgotModel.ForgotResponse;

public class CreateShipmentApiResponse {

    public CreateShipmentResponse response;
    public int code;
    public String error_status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_status() {
        return error_status;
    }

    public void setError_status(String error_status) {
        this.error_status = error_status;
    }

    private Throwable error;

    public CreateShipmentApiResponse(CreateShipmentResponse response) {
        this.response = response;
        this.error = null;
    }

    public CreateShipmentApiResponse(int code,String error_status){
        this.code = code;
        this.error_status = error_status;
    }

    public CreateShipmentApiResponse(Throwable error) {
        this.error = error;
        this.response = null;
    }

    public CreateShipmentResponse getResponse() {
        return response;
    }

    public void setResponse(CreateShipmentResponse response) {
        this.response = response;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
