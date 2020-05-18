package com.example.scmxpert.callback;

import com.example.scmxpert.model.loginModel.LoginResponse;

public interface LoginCallback {
     void onLoginSuccess(LoginResponse loginResponse);
     void  onLoginFailure(Throwable exception);
}
