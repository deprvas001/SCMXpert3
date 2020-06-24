package com.development.scmxpert.model.updateEventModel;

import com.development.scmxpert.model.ApiResponse;

public class UpdateApiResponse {
    public ApiResponse response;
    private Throwable error;
    public int code;
    public String error_status;

    public UpdateApiResponse(ApiResponse response) {
        this.response = response;
        this.error = null;
    }

    public UpdateApiResponse(int code, String error_status) {
        this.code = code;
        this.error_status = error_status;
    }

    public UpdateApiResponse(Throwable error) {
        this.error = error;
        this.response = null;
    }

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

    public ApiResponse getResponse() {
        return response;
    }

    public void setResponse(ApiResponse response) {
        this.response = response;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
