package com.example.scmxpert.views.login;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.scmxpert.constants.ApiConstants;
import com.example.scmxpert.model.loginModel.LoginApiResponse;
import com.example.scmxpert.model.loginModel.LoginResponse;
import com.example.scmxpert.networking.RetrofitService;
import com.example.scmxpert.networking.ShipmentApi;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private static LoginRepository loginRepository = null;
    private ShipmentApi shipmentApi;

    public  LoginRepository(){
       shipmentApi = RetrofitService.getRetrofitInstance().create(ShipmentApi.class);
    }

    public static LoginRepository getInstance(){
           if(loginRepository == null)
               loginRepository =new LoginRepository();
           return  loginRepository;
    }


     public LiveData<LoginApiResponse> loginUser(Context context, String userName, String password ){
       final MutableLiveData<LoginApiResponse> loginResponseLiveData = new MutableLiveData<>();
         String creds = String.format("%s:%s", ApiConstants.AUTH_USER_NAME, ApiConstants.AUTH_PASSWORD);
         String auth1 = ApiConstants.BASIC + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);

         shipmentApi.loginUser(ApiConstants.CONTENT_TYPE_VAL,auth1,userName,password,ApiConstants.GRANT_TYPE_VAL).enqueue(new Callback<LoginResponse>() {
             @Override
             public void onResponse(@NonNull Call<LoginResponse> call,@NonNull Response<LoginResponse> response) {
                 if(response.code() == 401){
                     loginResponseLiveData.setValue(null);
                 }else if(response.code() == 400){
                     loginResponseLiveData.setValue(null);
                 }
                 else {
                     if(response.isSuccessful()){
                         loginResponseLiveData.setValue(new LoginApiResponse(response.body()));
                     }
                 }
             }
             @Override
             public void onFailure(@NonNull Call<LoginResponse> call, @NotNull Throwable t) {
                loginResponseLiveData.setValue(new LoginApiResponse(t));
             }


         });


      return loginResponseLiveData;
     }
}
