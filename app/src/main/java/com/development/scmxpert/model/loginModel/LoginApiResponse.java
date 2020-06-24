package com.development.scmxpert.model.loginModel;

public class LoginApiResponse {
    public LoginResponse response;
    private Throwable error;

    public LoginApiResponse(LoginResponse response) {
        this.response = response;
        this.error = null;
    }

    public LoginApiResponse(Throwable error) {
        this.error = error;
        this.response = null;
    }

    public LoginResponse getResponse() {
        return response;
    }

    public void setResponse(LoginResponse response) {
        this.response = response;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

}
