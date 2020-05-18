package com.example.scmxpert.views.createShipment;

import androidx.lifecycle.MutableLiveData;
import com.example.scmxpert.model.createShipment.CreateShipmentApiResponse;
import com.example.scmxpert.model.createShipment.CreateShipmentRequest;
import com.example.scmxpert.model.createShipment.CreateShipmentResponse;
import com.example.scmxpert.networking.RetrofitService;
import com.example.scmxpert.networking.ShipmentApi;
import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateShipmentRepository {

    private static CreateShipmentRepository shipmentRepository = null;
    private ShipmentApi shipmentApi;

    public  CreateShipmentRepository(){
        shipmentApi = RetrofitService.getRetrofitClient().create(ShipmentApi.class);
    }

    public static CreateShipmentRepository getInstance(){
        if(shipmentRepository == null)
            shipmentRepository =new CreateShipmentRepository();
        return  shipmentRepository;
    }

    public MutableLiveData<CreateShipmentApiResponse> createShipment(String token,CreateShipmentRequest createShipmentResponse){
        final MutableLiveData<CreateShipmentApiResponse> responsiveLiveData = new MutableLiveData<>();

        String bearer = "Bearer ".concat(token);

        shipmentApi.createShipment("application/json",bearer,createShipmentResponse).enqueue(new Callback<CreateShipmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<CreateShipmentResponse> call, @NotNull Response<CreateShipmentResponse> response) {
                if(response.isSuccessful()){
                    responsiveLiveData.setValue(new CreateShipmentApiResponse(response.body()));
                }else if(response.code() == 401){
                    responsiveLiveData.setValue(new CreateShipmentApiResponse(response.code(),String.valueOf(response.errorBody())));
                }else{
                    responsiveLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CreateShipmentResponse> call, @NotNull Throwable t) {
                responsiveLiveData.setValue(new CreateShipmentApiResponse(t));
            }
        });
        return responsiveLiveData;
    }
}
