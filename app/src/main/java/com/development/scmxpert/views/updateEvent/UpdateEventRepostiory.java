package com.development.scmxpert.views.updateEvent;

import androidx.lifecycle.MutableLiveData;
import com.development.scmxpert.model.ApiResponse;
import com.development.scmxpert.model.UpdateSendRequest;
import com.development.scmxpert.model.updateEventModel.UpdateApiResponse;
import com.development.scmxpert.networking.RetrofitService;
import com.development.scmxpert.networking.ShipmentApi;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEventRepostiory {
    private static UpdateEventRepostiory eventRepostiory=null;
    private ShipmentApi shipmentApi;

    public UpdateEventRepostiory(){
        shipmentApi = RetrofitService.getRetrofitClient().create(ShipmentApi.class);
    }

    public static UpdateEventRepostiory getInstance(){
        if(eventRepostiory == null){
            eventRepostiory = new UpdateEventRepostiory();
        }
        return eventRepostiory;
    }

    public MutableLiveData<UpdateApiResponse> updateEvent(UpdateSendRequest updateSendRequest){
        final MutableLiveData<UpdateApiResponse> responsiveLiveData = new MutableLiveData<>();


        shipmentApi.updateEvent(updateSendRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NotNull Call<ApiResponse> call, @NotNull Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    responsiveLiveData.setValue(new UpdateApiResponse(response.body()));
                }else if(response.code() == 401){
                    responsiveLiveData.setValue(new UpdateApiResponse(response.code(),String.valueOf(response.errorBody())));
                }else{
                    responsiveLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApiResponse> call, @NotNull Throwable t) {
                responsiveLiveData.setValue(new UpdateApiResponse(t));
            }
        });
        return responsiveLiveData;
    }
}
