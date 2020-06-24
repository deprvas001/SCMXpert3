package com.development.scmxpert.callback;

import com.development.scmxpert.model.loginModel.LoginResponse;

public interface LoginCallback {
     void onLoginSuccess(LoginResponse loginResponse);
     void  onLoginFailure(Throwable exception);
}
