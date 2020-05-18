package com.example.scmxpert.views.createShipment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.scmxpert.model.createShipment.CreateShipmentApiResponse;
import com.example.scmxpert.model.createShipment.CreateShipmentRequest;

public class CreateShipmentViewModel extends ViewModel {
    private MediatorLiveData<CreateShipmentApiResponse> apiResponseMediatorLiveData;
    private CreateShipmentRepository shipmentRepository;

    public  CreateShipmentViewModel(){
        apiResponseMediatorLiveData = new MediatorLiveData<>();
        shipmentRepository = new CreateShipmentRepository();
    }

    public LiveData<CreateShipmentApiResponse> createShipment(String token, CreateShipmentRequest createShipmentResponse){
        apiResponseMediatorLiveData.addSource(shipmentRepository.createShipment(token,createShipmentResponse), apiResponse -> apiResponseMediatorLiveData.setValue(apiResponse));
        return apiResponseMediatorLiveData;
    }
}
