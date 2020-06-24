package com.development.scmxpert.model.forgotModel;

public class ForgotApiResponse {
    public ForgotResponse response;
    private Throwable error;

    public ForgotApiResponse(ForgotResponse response) {
        this.response = response;
        this.error = null;
    }

    public ForgotApiResponse(Throwable error) {
        this.error = error;
        this.response = null;
    }

    public ForgotResponse getResponse() {
        return response;
    }

    public void setResponse(ForgotResponse response) {
        this.response = response;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
