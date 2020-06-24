package com.development.scmxpert.apiInterface;

import com.development.scmxpert.constants.ApiConstants;
import com.development.scmxpert.model.AddNewRoute;
import com.development.scmxpert.model.CompleteShipmentModel;
import com.development.scmxpert.model.CreateShipmentDrop;
import com.development.scmxpert.model.CreateShipmentResponse;
import com.development.scmxpert.model.DeviceTempData;
import com.development.scmxpert.model.FilterResponse;
import com.development.scmxpert.model.GetRouteDetails;
import com.development.scmxpert.model.ShipmentDetail;
import com.development.scmxpert.model.Shippment;
import com.development.scmxpert.model.UpdateEventDetails;
import com.development.scmxpert.model.ApiResponse;
import com.development.scmxpert.model.UpdateRoute;
import com.development.scmxpert.model.UpdateSendRequest;
import com.development.scmxpert.model.UserDetails;
import com.development.scmxpert.model.WayInfo;
import com.development.scmxpert.model.filter.FilterGetResponse;
import com.development.scmxpert.model.forgotModel.ForgotRequestModel;
import com.development.scmxpert.model.forgotModel.ForgotResponse;
import com.development.scmxpert.model.loginModel.LoginResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface CompleteShipment {
    String BASE_URL = ApiConstants.BASE_URL;

    @GET("getShipments/SCM0002-A00001/BP0001")
    Call<List<Shippment>>  getShipment();

    @GET("getShipments/{id}/{create}")
    Single<List<Shippment>> getShipment1(@Path("id") String id,@Path("create") String create);

    @GET("getShipmentsList/{Customer_Id}")
    Single<List<Shippment>> getAllShipmentDetails(@Path("Customer_Id") String Customer_Id);

    @GET("userTotaldetails/{id}")
    Single<UserDetails> getUserDetails(@Path("id") String id);

    @GET("getWayInfo/{id}")
    Single<List<WayInfo>> getWayPoints(@Path("id") String id);

    @FormUrlEncoded
    @POST("oauth/token")
    Single<LoginResponse> loginUser(@Header ("Content-Type") String content_type,
                                  @Header ("Authorization") String header,
                                  @Field("username") String userName,
                                  @Field("password") String password,
                                  @Field("grant_type") String grantType);


    @GET("getShipmentTransactionDeviceData/{id}")
    Call<List<ShipmentDetail>>  getShipmentDetails(@Path("id") String id);

    @GET("getShipmentTransactionhistory/{Shipment_Id}")
    Call<List<ShipmentDetail>>  getParticularShipment(@Path("Shipment_Id") String Shipment_Id);

    @GET("getShipmentTransactionDeviceData/{id}")
    Single<List<ShipmentDetail>>  getShipmentDetails1(@Path("id") String id);

    @GET("getDDData/{id}")
    Single<CreateShipmentDrop> getDDData(@Path("id") String id);

    @GET("getFiltersData/{id}")
    Single<FilterGetResponse> getFilterData(@Path("id") String id);

    @GET("searchfilter")
    Single<ArrayList<FilterResponse>> getFilterResponse(@QueryMap HashMap<String,String> filter);


    @GET("getDeviceDataTemp/{id}")
    Single<List<DeviceTempData>> getDeviceDataTemp(@Path("id") String id);

    @GET("getDDData/{id}")
    Call<CreateShipmentDrop> getDDData1(@Path("id") String id);

    @GET("getRoutes/{id}")
    Call<List<GetRouteDetails>> getRoute(@Path("id") String id);

    @GET("getShipmentTransactionhistory/{shipment_number}")
    Single<List<UpdateEventDetails>> getUpdateEventDetails(@Path("shipment_number") String id);

    @POST("createNewShipment")
    Single<CreateShipmentDrop> createShipment(@Path("id") String id);

    @POST("Forgot")
    Single<ForgotResponse> resetPassword(
            @Header("Accept") String accept,
            @Header ("Content-Type") String content_type,
            @Body ForgotRequestModel requestModel);


    @FormUrlEncoded
    @POST("createNewShipment")
    Call<CreateShipmentResponse>  createNewShipment(@Field("internalShipmentId") String shipment_id,
                                                    @Field("shipment_Num") String num,
                                                    @Field("customerId") String customer_id,
                                                    @Field("shipment_Number") String shipment_number,
                                                    @Field("typeOfReference") String typeOfReference,
                                                    @Field("comments") String comments,
                                                    @Field("routeId") String routeId,
                                                    @Field("routeFrom") String routeFrom,
                                                    @Field("routeTo") String routeTo,
                                                    @Field("goodsId") String goodsId,
                                                    @Field("goodsType") String goodsType,
                                                    @Field("parnterFrom") String parnterFrom,
                                                    @Field("deviceId") String deviceId,
                                                    @Field("allEvents") String allEvents,
                                                    @Field("incoTerms") String incoTerms,
                                                    @Field("mode") String mode,
                                                    @Field("temp") String temp,
                                                    @Field("rH") String rH,
                                                    @Field("created_Date") String created_Date,
                                                    @Field("estimatedDeliveryDate") String estimatedDeliveryDate

                                                    );




    @POST("updateEventShip")
    Single<ApiResponse> updateEvent(@Body UpdateSendRequest updateSendRequest);

    @POST("completeNewShipment")
    Single<ApiResponse> completeShipment(@Body CompleteShipmentModel createShipment);

    @POST("addNewRoute")
    Single<ApiResponse> addRoute(@Body AddNewRoute addNewRoute);

    @POST("updateRoute")
    Single<ApiResponse> updateRoute(@Body UpdateRoute updateRoute);



}
