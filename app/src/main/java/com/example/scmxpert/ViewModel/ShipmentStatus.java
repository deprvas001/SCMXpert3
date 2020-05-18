package com.example.scmxpert.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.scmxpert.apiInterface.CompleteShipment;
import com.example.scmxpert.constants.ApiConstants;
import com.example.scmxpert.fragment.LiveShipment;
import com.example.scmxpert.model.CreateShipmentDrop;
import com.example.scmxpert.model.CreateShipmentResponse;
import com.example.scmxpert.model.GetRouteDetails;
import com.example.scmxpert.model.ShipmentDetail;
import com.example.scmxpert.model.Shippment;
import com.example.scmxpert.model.UpdateEventPost;
import com.example.scmxpert.repository.ShipmentRepository;
import com.google.android.gms.common.api.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipmentStatus extends AndroidViewModel {
  private ShipmentRepository shipmentRepository;
  private LiveData<List<Shippment>> shipmentLiveData;
  private LiveData<List<ShipmentDetail>> shipmentDetaill;
  private LiveData<List<GetRouteDetails>> getRouteList;
  private MutableLiveData<CreateShipmentResponse> createShipmentResponse;
  private LiveData<CreateShipmentDrop> dropDownList;
  private LiveData<String> updateEvent;

    public ShipmentStatus(@NonNull Application application,String id) {
        super(application);
        shipmentRepository = new ShipmentRepository(application);
        this.shipmentLiveData = shipmentRepository.getAllShipment();
        this.shipmentDetaill = shipmentRepository.getAllShipmentDetails(id);
        this.dropDownList = shipmentRepository.getAllDDData(id);
        this.getRouteList = shipmentRepository.getRouteData(id);

    }

   /* public ShipmentStatus(@NonNull Application application, UpdateEventPost event) {
        super(application);
        shipmentRepository = new ShipmentRepository(application);
        updateEvent = shipmentRepository.updateEvent(event);

    }
*/
    //we will call this method to get the data
    public MutableLiveData<CreateShipmentResponse> createShipment() {
        //if the list is null
        if (createShipmentResponse == null) {

            createShipmentResponse = new MutableLiveData<CreateShipmentResponse>();
            //we will load it asynchronously from server in this method
            createNewShipment();
        }

        //finally we will return the list
        return createShipmentResponse;
    }



    public LiveData<List<Shippment>> getResponseLiveData() {
        return shipmentLiveData;
    }

    public LiveData<List<ShipmentDetail>> getShipmentDetaillData() {
        return shipmentDetaill;
    }

    public LiveData<CreateShipmentDrop> getDropDownData(){
        return dropDownList;
    }

    public LiveData<List<GetRouteDetails>> getRouteDetails(){
        return getRouteList;
    }

    public LiveData<String> updateEvent(){
        return updateEvent;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void createNewShipment() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CompleteShipment api = retrofit.create(CompleteShipment.class);
        Call<CreateShipmentResponse> call = api.createNewShipment("T000000023","5000000832",
                "SCM0001","T000000023","Purchase Order","tressdkjldfg lkfglfg kjdfg dfkjg",
                "20001","Philadelphia","Pittsburgh","SCM0001_G0001","Fruits","BP0001",
                "037075054","","DDP","Road","","","2019-09-03T06:40:37.000Z","2019-09-04T14:30:00.000Z");


        call.enqueue(new Callback<CreateShipmentResponse>() {
            @Override
            public void onResponse(Call<CreateShipmentResponse> call, Response<CreateShipmentResponse> response) {

                //finally we are setting the list to our MutableLiveData
                createShipmentResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CreateShipmentResponse> call, Throwable t) {

            }
        });
    }

}
