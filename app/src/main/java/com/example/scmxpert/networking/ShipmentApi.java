package com.example.scmxpert.networking;

import com.example.scmxpert.model.ApiResponse;
import com.example.scmxpert.model.UpdateSendRequest;
import com.example.scmxpert.model.createShipment.CreateShipmentRequest;
import com.example.scmxpert.model.createShipment.CreateShipmentResponse;
import com.example.scmxpert.model.forgotModel.ForgotRequestModel;
import com.example.scmxpert.model.forgotModel.ForgotResponse;
import com.example.scmxpert.model.loginModel.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ShipmentApi {
    @FormUrlEncoded
    @POST("oauth/token")
    Call<LoginResponse> loginUser(@Header ("Content-Type") String content_type,
                                  @Header ("Authorization") String header,
                                  @Field("username") String userName,
                                  @Field("password") String password,
                                  @Field("grant_type") String grantType);


    @POST("Forgot")
    Call<ForgotResponse> forgotPasswrod(
            @Header("Accept") String accept,
            @Header ("Content-Type") String content_type,
            @Body ForgotRequestModel requestModel);

    @POST("updateEventShip")
    Call<ApiResponse> updateEvent(@Body UpdateSendRequest updateSendRequest);

    @POST("createNewShipment")
    Call<CreateShipmentResponse> createShipment(
            @Header ("Content-Type") String content_type,
            @Header ("Authorization") String header,
            @Body CreateShipmentRequest shipmentRequest);

}
