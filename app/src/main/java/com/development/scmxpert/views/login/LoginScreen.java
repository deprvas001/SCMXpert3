package com.development.scmxpert.views.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.development.scmxpert.R;
import com.development.scmxpert.apiInterface.CompleteShipment;
import com.development.scmxpert.base.BaseActivity;
import com.development.scmxpert.databinding.ActivityLoginScreenBinding;
import com.development.scmxpert.helper.SessionManager;
import com.development.scmxpert.model.UserDetails;
import com.development.scmxpert.service.RetrofitClientInstance;
import com.development.scmxpert.views.forgotPassword.ForgotPassword;
import com.development.scmxpert.views.ShipmentHome;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class LoginScreen extends BaseActivity implements View.OnClickListener {
    SessionManager manager;
    ActivityLoginScreenBinding screenBinding;
    LoginViewModel loginViewModel;
    private static Retrofit retrofit = null;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_login_screen);
        setClickListener();

        manager = new SessionManager(getApplicationContext());

        // Check if UserResponse is Already Logged In
        if (manager.isLoggedIn()) {
              startActivity(new Intent(LoginScreen.this, ShipmentHome.class));
              finish();
        } else {
            screenBinding.layout.setVisibility(View.VISIBLE);
        }
    }

    private void setClickListener() {
        screenBinding.btnLogin.setOnClickListener(this);
        screenBinding.forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkLoginCredentials();
                break;

            case R.id.forgot_password:
                goToForgotPassword();
                break;
        }

    }

    private void checkLoginCredentials() {
        if (TextUtils.isEmpty(screenBinding.inputEmail.getText().toString())) {
            showAlertDialog(this, getString(R.string.error_user_empty));
        } else if (TextUtils.isEmpty(screenBinding.inputPassword.getText().toString())) {
            showAlertDialog(this, getString(R.string.error_passwrod_empty));
        } else {
            if (isNetworkAvailable(this)) {
                userLogin(screenBinding.inputEmail.getText().toString(), screenBinding.inputPassword.getText().toString());
            } else {
                showAlertDialog(this, getString(R.string.no_connection));
            }
        }
    }

    private void goToForgotPassword() {
        startActivity(new Intent(this, ForgotPassword.class));
    }

    /**
     * Login API call
     */
    private void userLogin(String username, String password) {
        showProgressDialog(getString(R.string.loading));
        /*CompleteShipment apiService = RetrofitClientInstance.getRetrofitLoginInstance(LoginScreen.this).create(CompleteShipment.class);
        String creds = String.format("%s:%s", ApiConstants.AUTH_USER_NAME, ApiConstants.AUTH_PASSWORD);
        String auth1 = ApiConstants.BASIC + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);

        disposable.add(
                apiService.loginUser(ApiConstants.CONTENT_TYPE_VAL,auth1,username,password,ApiConstants.GRANT_TYPE_VAL)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                            @Override
                            public void onSuccess(LoginResponse loginResponse) {
                                // call is successful
                                String access = loginResponse.getAccessToken();
                                getUserDetails(username,access);
                            }
                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                             int code =   ((HttpException) e).code();
                             if(code == 401){
                                 showAlertDialog(LoginScreen.this, getString(R.string.invalid_credentails));
                             }else if(code == 400){
                                 showAlertDialog(LoginScreen.this, getString(R.string.invalid_credentails));
                             }else{
                                 Toast.makeText(LoginScreen.this, "Something went wrong please try later.", Toast.LENGTH_SHORT).show();
                             }
                            }
                        })
        );*/
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getLoginUser(this, username, password).observe(this, loginApiResponse -> {
            if (loginApiResponse == null) {
                // handle error here
                hideProgressDialog();
                showAlertDialog(this, getString(R.string.invalid_credentails));
             //   Toast.makeText(this, getString(R.string.invalid_credentails), Toast.LENGTH_SHORT).show();
            }else if (loginApiResponse.getError() == null) {

                // call is successful
                String access = loginApiResponse.getResponse().getAccessToken();
                getUserDetails(username,access);
               // Toast.makeText(LoginScreen.this, access, Toast.LENGTH_SHORT).show();
               // finish();
            } else {
                // call failed.
                hideProgressDialog();
                Throwable e = loginApiResponse.getError();
                Toast.makeText(LoginScreen.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Log.e(TAG, "Error is " + e.getLocalizedMessage());
            }
        });
    }

    private void getUserDetails(String username,String token){
        RetrofitClientInstance.setRetrofit();

        CompleteShipment apiService = RetrofitClientInstance.getClient(LoginScreen.this).create(CompleteShipment.class);
        disposable.add(
                apiService.getUserDetails(username)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserDetails>() {
                            @Override
                            public void onSuccess(UserDetails userDetails) {
                                hideProgressDialog();
                             //   Toast.makeText(LoginScreen.this, userDetails.getUserName(), Toast.LENGTH_SHORT).show();
                                manager.createLoginSession(userDetails.getCustomer_Name(),userDetails.getUserName(), userDetails.getUserName(), token,userDetails.getCustomer_Id());
                                startActivity(new Intent(LoginScreen.this, ShipmentHome.class));
                                finish();
                            }
                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                            }
                        })
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  noteViewModel.getTrashedNotesLiveData().removeObservers(this);
    }
}
