package com.development.scmxpert.views.forgotPassword;

import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.development.scmxpert.R;
import com.development.scmxpert.apiInterface.CompleteShipment;
import com.development.scmxpert.base.BaseActivity;
import com.development.scmxpert.databinding.ActivityForgotPasswordBinding;
import com.development.scmxpert.model.forgotModel.ForgotRequestModel;
import com.development.scmxpert.model.forgotModel.ForgotResponse;
import com.development.scmxpert.service.RetrofitClientInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPassword extends BaseActivity implements View.OnClickListener {
    ActivityForgotPasswordBinding passwordBinding;
    ForgotViewModel viewModel;
    private CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);

        setSupportActionBar(passwordBinding.customToolbar);
        passwordBinding.customToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        setClickListener();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_reset) {
            checkLoginCredentials();
        }
    }

    private void setClickListener() {
        passwordBinding.btnReset.setOnClickListener(this);
    }

    private void checkLoginCredentials() {
        if (TextUtils.isEmpty(passwordBinding.inputEmail.getText().toString())) {
            showAlertDialog(this, getString(R.string.email_field_empty));
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(passwordBinding.inputEmail.getText().toString()).matches()) {
                userReset(passwordBinding.inputEmail.getText().toString());
            }
            else{
                passwordBinding.inputEmail.setError("Invalid Email address");
            }

        }
    }

    private void userReset(String email) {
        showProgressDialog(getString(R.string.loading));
        ForgotRequestModel requestModel = new ForgotRequestModel();
        requestModel.setEmail(email);

      /*  viewModel = ViewModelProviders.of(this).get(ForgotViewModel.class);

        viewModel.forgotPassword(email).observe(this, forgotApiResponse -> {
            *//*if (forgotApiResponse == null) {
                hideProgressDialog();
                // handle error here
                ForgotPassword.this.showAlertDialog(ForgotPassword.this, ForgotPassword.this.getString(R.string.invalid_credentails));
                return;
            }else*//* if (forgotApiResponse.getError() == null && forgotApiResponse.getResponse() ==null) {
                hideProgressDialog();
                showCustomDialog(ForgotPassword.this, ForgotPassword.this.getString(R.string.internal_server_error));
                // call is successful
                //  Log.i(TAG, "Data response is " + apiResponse.getPosts());
            }else if(forgotApiResponse.getError() == null){
                hideProgressDialog();
                String access = forgotApiResponse.getResponse().getMessage();
                Toast.makeText(ForgotPassword.this, access, Toast.LENGTH_SHORT).show();
                ForgotPassword.this.finish();
            } else {
                hideProgressDialog();
                // call failed.
                Throwable e = forgotApiResponse.getError();
                Toast.makeText(ForgotPassword.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Log.e(TAG, "Error is " + e.getLocalizedMessage());
            }
        });*/

        showProgressDialog(getResources().getString(R.string.loading));

        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);
        disposable.add(
                apiService.resetPassword("application/json","application/json",requestModel)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ForgotResponse>() {
                            @Override
                            public void onSuccess(ForgotResponse forgotResponse) {
                                hideProgressDialog();
                              //  setDropDownItem(shipmentDropDown);
                                String access = forgotResponse.getMessage();
                                showCustomDialog(ForgotPassword.this,  access);
                            //    Toast.makeText(ForgotPassword.this, access, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                                showCustomDialog(ForgotPassword.this,  getString(R.string.internal_server_error));
                                //  Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    public void showCustomDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    builder.create().dismiss();
                });
        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        finish();*/
    }
}
