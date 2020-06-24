package com.development.scmxpert.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.development.scmxpert.apiInterface.CompleteShipment;
import com.development.scmxpert.model.CreateShipmentDrop;
import com.development.scmxpert.model.GetRouteDetails;
import com.development.scmxpert.model.ShipmentDetail;
import com.development.scmxpert.model.Shippment;
import com.development.scmxpert.service.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentRepository {
    private static final String TAG =ShipmentRepository.class.getName();
    private CompleteShipment completeShipment;
    private Application application;

    public ShipmentRepository(Application application) {
        this.application = application;
        completeShipment = RetrofitClientInstance.getRetrofitInstance().create(CompleteShipment.class);
    }

    public LiveData<List<Shippment>> getAllShipment() {
        final MutableLiveData<List<Shippment>> data = new MutableLiveData<>();
        completeShipment.getShipment()
                .enqueue(new Callback<List<Shippment>>() {
                    @Override
                    public void onResponse(Call<List<Shippment>> call, Response<List<Shippment>> response) {
                        Log.d(TAG, "onResponse response:: " + response);
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Shippment>> call, Throwable t) {

                    }
                });
        return data;
    }

    public LiveData<List<ShipmentDetail>> getAllShipmentDetails(String id) {
        final MutableLiveData<List<ShipmentDetail>> data = new MutableLiveData<>();
        completeShipment.getParticularShipment(id)
                .enqueue(new Callback<List<ShipmentDetail>>() {
                    @Override
                    public void onResponse(Call<List<ShipmentDetail>> call, Response<List<ShipmentDetail>> response) {
                        Log.d(TAG, "onResponse response:: " + response);
                        if (response.body() != null) {

                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ShipmentDetail>> call, Throwable t) {
                             data.setValue(null);
                    }
                });
        return data;
    }


    //All drop down data
    public LiveData<CreateShipmentDrop> getAllDDData(String id){
        final MutableLiveData<CreateShipmentDrop> data = new MutableLiveData<>();
        completeShipment.getDDData1("SCM0001")
                .enqueue(new Callback<CreateShipmentDrop>() {
                    @Override
                    public void onResponse(Call<CreateShipmentDrop> call, Response<CreateShipmentDrop> response) {
                        Log.d(TAG, "onResponse response:: " + response);
                        if (response.body() != null) {

                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateShipmentDrop> call, Throwable t) {

                    }
                });
        return data;
    }


    //All route data
    public LiveData<List<GetRouteDetails>> getRouteData(String id){
        final MutableLiveData<List<GetRouteDetails>> data = new MutableLiveData<>();
        completeShipment.getRoute(id)
                .enqueue(new Callback<List<GetRouteDetails>>() {
                    @Override
                    public void onResponse(Call<List<GetRouteDetails>> call, Response<List<GetRouteDetails>> response) {
                        Log.d(TAG, "onResponse response:: " + response);
                        if (response.body() != null) {

                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GetRouteDetails>> call, Throwable t) {
                        Log.i(TAG,"On Failure response: "+t.getMessage());

                    }
                });
        return data;
    }


   /* public LiveData<String> updateEvent(UpdateEventPost event){

        final MutableLiveData<String> data = new MutableLiveData<>();
        completeShipment.setUpdateEvent()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(TAG, "onResponse response:: " + response);
                        if (response.body() != null) {

                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
        return data;
    }*/




}
