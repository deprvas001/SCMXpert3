package com.development.scmxpert.views.forgotPassword;

import androidx.lifecycle.MutableLiveData;
import com.development.scmxpert.model.forgotModel.ForgotApiResponse;
import com.development.scmxpert.model.forgotModel.ForgotRequestModel;
import com.development.scmxpert.model.forgotModel.ForgotResponse;
import com.development.scmxpert.networking.RetrofitService;
import com.development.scmxpert.networking.ShipmentApi;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotRepository {

    private static ForgotRepository forgotRepository = null;
    private ShipmentApi shipmentApi;

    public  ForgotRepository(){
        shipmentApi = RetrofitService.getRetrofitClient().create(ShipmentApi.class);
    }

    public static ForgotRepository getInstance(){
        if(forgotRepository == null)
            forgotRepository =new ForgotRepository();
        return  forgotRepository;
    }


    public MutableLiveData<ForgotApiResponse> forgotPassword(String email){
        final MutableLiveData<ForgotApiResponse> responsiveLiveData = new MutableLiveData<>();
        ForgotRequestModel requestModel = new ForgotRequestModel();
        requestModel.setEmail(email);

        shipmentApi.forgotPasswrod("application/json","application/json",requestModel).enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(@NotNull  Call<ForgotResponse> call,@NotNull Response<ForgotResponse> response) {
                if(response.isSuccessful()){
                    responsiveLiveData.setValue(new ForgotApiResponse(response.body()));
                }else{
                    responsiveLiveData.setValue(new ForgotApiResponse(response.body()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ForgotResponse> call, @NotNull Throwable t) {
                responsiveLiveData.setValue(null);
            }
        });
        return responsiveLiveData;
    }
}
