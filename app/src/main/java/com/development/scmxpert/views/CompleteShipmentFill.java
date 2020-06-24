package com.development.scmxpert.views;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.development.scmxpert.R;
import com.development.scmxpert.ViewModel.ShipmentStatus;
import com.development.scmxpert.ViewModel.ShipmentStatusFactory;
import com.development.scmxpert.adapter.UpdateEventAdapter;
import com.development.scmxpert.apiInterface.CompleteShipment;
import com.development.scmxpert.base.BaseActivity;
import com.development.scmxpert.constants.ApiConstants;
import com.development.scmxpert.databinding.ActivityCompleteShipmentBinding;
import com.development.scmxpert.helper.SessionManager;
import com.development.scmxpert.model.ApiResponse;
import com.development.scmxpert.model.CompleteShipmentModel;
import com.development.scmxpert.model.ShipmentGoods;
import com.development.scmxpert.model.Shippment;
import com.development.scmxpert.model.UpdateEventDetails;
import com.development.scmxpert.model.UpdateEventModel;
import com.development.scmxpert.service.RetrofitClientInstance;
import com.development.scmxpert.viewClick.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import static android.widget.Toast.*;

public class CompleteShipmentFill extends BaseActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener{
    ActivityCompleteShipmentBinding completeShipmentBinding;
    private UpdateEventAdapter eventAdapter;
    Shippment shippment;
    private List<UpdateEventModel> event_list = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    ShipmentStatus shipmentViewModel;
    private List<String> reference_type_list = new ArrayList<>();
    private List<String> partner_list = new ArrayList<>();
    private List<String> return_location = new ArrayList<>();
    private List<String> receiving_location = new ArrayList<>();
    ArrayAdapter<String>  reference_Adapter,partner_Adapter,receiving_location_Adapter,return_location_Adapter;
    SessionManager session;
    String user_name="",partner_name="",return_location_val="",token="",partner_id="",reference_id="",receive_location="",partner_id_value="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        completeShipmentBinding = DataBindingUtil.setContentView(this,R.layout.activity_complete_shipment);
        if (getIntent() != null) {
            shippment = (Shippment) getIntent().getSerializableExtra(ApiConstants.SHIPMENT);
        }
        initializeView();
        setOnClickListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView(){
        setSupportActionBar(completeShipmentBinding.customToolbar);
        completeShipmentBinding.customToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        completeShipmentBinding.layoutCompleteFill.shipmentId.setText(shippment.getShipment_id());
        completeShipmentBinding.layoutCompleteFill.shipmentNumber.setText(shippment.getShipment_num());
        completeShipmentBinding.layoutCompleteFill.refernceType.setText(shippment.getType_reference());
        completeShipmentBinding.layoutCompleteFill.connectedDevice.setText(shippment.getDevice_id());
        completeShipmentBinding.layoutCompleteFill.shipmentDescription.setText(shippment.getGoods_desc());

        eventAdapter =new  UpdateEventAdapter(CompleteShipmentFill.this,event_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        completeShipmentBinding.layoutCompleteFill.recyclerView.setLayoutManager(mLayoutManager);
        completeShipmentBinding.layoutCompleteFill.recyclerView.setItemAnimator(new DefaultItemAnimator());
        completeShipmentBinding.layoutCompleteFill.recyclerView.setAdapter(eventAdapter);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String,String> user = session.getUserDetails();
        user_name = user.get(SessionManager.USER_NAME);
        partner_name = user.get(SessionManager.PARTNER_NAME);
        token = user.get(SessionManager.TOKEN);

        getDDData();

    }
    private void getDDData() {
        showProgressDialog(getResources().getString(R.string.loading));

        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);
        disposable.add(
                apiService.getUpdateEventDetails(shippment.getShipment_id())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<UpdateEventDetails>>() {
                            @Override
                            public void onSuccess(List<UpdateEventDetails> updateEvent) {
                                hideProgressDialog();
                                event_list.clear();
                                for(int i=0;i<updateEvent.size();i++){
                                    String event_id = updateEvent.get(i).getEvent_ID();
                                    String partner = updateEvent.get(i).getPartner_from();
                                    String event = updateEvent.get(i).getEvent_Name();
                                    String status = updateEvent.get(i).getEvent_status();
                                    String date = updateEvent.get(i).getExpected_date_atBp();
                                    String partner_id = updateEvent.get(i).getPartner();
                                    UpdateEventModel model = new UpdateEventModel(event_id,partner,event,date,status,partner_id);
                                    event_list.add(model);
                                }

                                // notify adapter about data set changes
                                // so that it will render the list with new data
                                eventAdapter.notifyDataSetChanged();
                                 getReferenceType();

                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                                makeText(CompleteShipmentFill.this, "Try Later", LENGTH_SHORT).show();
                             //   Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    public void getReferenceType(){
        showProgressDialog(getString(R.string.loading));
        reference_type_list.clear();
        shipmentViewModel = ViewModelProviders.of(this,new ShipmentStatusFactory(getApplication(),user_name)).get(ShipmentStatus.class);

        shipmentViewModel.getDropDownData().observe(this, response -> {
            if (response != null) {
                hideProgressDialog();
                reference_type_list = response.getReference_type();
                reference_type_list.add(0,getString(R.string.select_reference));
                reference_Adapter = new ArrayAdapter<>(CompleteShipmentFill.this, R.layout.spinner_item_layout, reference_type_list);
                reference_Adapter.setDropDownViewResource(R.layout.spinner_item);

                // attaching data adapter to spinner
                completeShipmentBinding.completeShipmentReference.setAdapter(reference_Adapter);


                partner_list = response.getPartner_id();
                partner_list.add(0,getString(R.string.select_partner));
                partner_Adapter = new ArrayAdapter<>(CompleteShipmentFill.this, R.layout.spinner_item_layout, partner_list);
                partner_Adapter.setDropDownViewResource(R.layout.spinner_item);

                // attaching data adapter to spinner
                completeShipmentBinding.completeShipmentPartner.setAdapter(partner_Adapter);

                for (ShipmentGoods route : response.getRoutes_type()) {
                    if(!receiving_location.contains(route.getFrom()))
                        receiving_location.add(route.getFrom());
                    if(!receiving_location.contains(route.getTo()))
                        receiving_location.add(route.getTo());

                    if(!return_location.contains(route.getFrom()))
                        return_location.add(route.getFrom());
                    if(!return_location.contains(route.getTo()))
                        return_location.add(route.getTo());
                }

                receiving_location.add(0,getString(R.string.select_location));
                receiving_location_Adapter = new ArrayAdapter<>(CompleteShipmentFill.this, R.layout.spinner_item_layout, receiving_location);
                receiving_location_Adapter.setDropDownViewResource(R.layout.spinner_item);
                // attaching data adapter to spinner
                completeShipmentBinding.receivingLocation.setAdapter(receiving_location_Adapter);


                return_location.add(0,getString(R.string.return_location));
                return_location_Adapter = new ArrayAdapter<>(CompleteShipmentFill.this, R.layout.spinner_item_layout, return_location);
                return_location_Adapter.setDropDownViewResource(R.layout.spinner_item);
                // attaching data adapter to spinner
                completeShipmentBinding.deviceReturnLocation.setAdapter(return_location_Adapter);
            }else{
                hideProgressDialog();
                Toast.makeText(this, "Please try later.", LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void completeShipment(){
        showProgressDialog(getString(R.string.loading));
        CompleteShipmentModel completeShipment = new CompleteShipmentModel();
        completeShipment.setShipment_number(shippment.getShipment_id());
        completeShipment.setPartner(partner_id_value);
    //    completeShipment.setPartner(completeShipmentBinding.partnerNameEdt.getText().toString());
        completeShipment.setEvent(completeShipmentBinding.eventType.getText().toString());
        completeShipment.setDateandTime(getDatetime());
        completeShipment.setEventId(completeShipmentBinding.eventId.getText().toString());
        completeShipment.setPartnerFrom(completeShipmentBinding.partnerNameEdt.getText().toString());
        completeShipment.setReceivingLocation(receive_location);
        completeShipment.setReceivingReferenceNumber(completeShipmentBinding.receivingReference.getText().toString());
        completeShipment.setTypeOfReference(reference_id);
        completeShipment.setComments(completeShipmentBinding.receivingDescription.getText().toString());
        completeShipment.setDeviceReturnLocation(return_location_val);

        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);
        disposable.add(apiService.completeShipment(completeShipment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse updateEvent) {
                        if(updateEvent.getStatus()){
                            showHomeScreen(CompleteShipmentFill.this,updateEvent.getMessage());

                        }else{
                            showAlertDialog(CompleteShipmentFill.this,updateEvent.getMessage());
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        makeText(CompleteShipmentFill.this, "Try Later", LENGTH_SHORT).show();
                      //  Log.e(TAG, "onError: " + e.getMessage());
                    }
                })
        );
    }

    private void setOnClickListener(){
        completeShipmentBinding.createShipment.setOnClickListener(this);
        completeShipmentBinding.completeShipmentReference.setOnItemSelectedListener(this);
        completeShipmentBinding.completeShipmentPartner.setOnItemSelectedListener(this);
        completeShipmentBinding.receivingLocation.setOnItemSelectedListener(this);
        completeShipmentBinding.deviceReturnLocation.setOnItemSelectedListener(this);

        completeShipmentBinding.layoutCompleteFill.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), completeShipmentBinding.layoutCompleteFill.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UpdateEventModel eventModel = event_list.get(position);
                if(eventModel.getEvent_status() !=null){
                if(!eventModel.getEvent_status().equals(getString(R.string.completed))){
                    completeShipmentBinding.eventId.setText(eventModel.getEvent_id());
                    completeShipmentBinding.eventType.setText(eventModel.getEvent());
                    completeShipmentBinding.partnerNameEdt.setText(eventModel.getPartner());
                    completeShipmentBinding.eventId.requestFocus();
                    partner_id_value = eventModel.getPartner_id();
                }
            }else{
                    completeShipmentBinding.eventId.setText(eventModel.getEvent_id());
                    completeShipmentBinding.eventType.setText(eventModel.getEvent());
                    completeShipmentBinding.partnerNameEdt.setText(eventModel.getPartner());
                    completeShipmentBinding.eventId.requestFocus();
                    partner_id_value = eventModel.getPartner_id();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createShipment) {
            if (completeShipmentBinding.eventId.getText().toString().isEmpty()) {
                showAlertDialog(this, getString(R.string.event_id_error));
            } else if (completeShipmentBinding.eventType.getText().toString().isEmpty()) {
                showAlertDialog(this, getString(R.string.event_type_error));
            } else if (completeShipmentBinding.partnerNameEdt.getText().toString().isEmpty()) {
                showAlertDialog(this, getString(R.string.partner_id_empty));
            } else if (receive_location.isEmpty()) {
                showAlertDialog(this, getString(R.string.receive_location_error));
            } else if (reference_id.isEmpty()) {
                showAlertDialog(this, getString(R.string.type_reference_empty));
            } else if (return_location_val.isEmpty()) {
                showAlertDialog(this, getString(R.string.return_location));
            }else if(completeShipmentBinding.receivingDescription.getText().toString().isEmpty()){
                showAlertDialog(this, getString(R.string.event_description_error));
            }else if(completeShipmentBinding.receivingReference.getText().toString().isEmpty()){
                showAlertDialog(this, getString(R.string.event_reference_error));
            }
            else {
                completeShipment();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()){
            case R.id.complete_shipment_partner:
                if(position>0){
                    partner_id = adapterView.getSelectedItem().toString();
                 //   makeText(this, partner_id, LENGTH_SHORT).show();
                }else{
                    partner_id="";
                }
                break;

            case R.id.receiving_location:
                if(position>0){
                   receive_location = adapterView.getSelectedItem().toString();
                   // makeText(this, receive_location, LENGTH_SHORT).show();
                }else{
                    receive_location ="";
                }
                break;

            case R.id.complete_shipment_reference:
                if(position>0){
                    reference_id = adapterView.getSelectedItem().toString();
                   // makeText(this, reference_id, LENGTH_SHORT).show();
                }else{
                    reference_id ="";
                }
                break;

            case R.id.device_return_location:
                if(position>0){
                    return_location_val = adapterView.getSelectedItem().toString();
                   // makeText(this,  return_location_val, LENGTH_SHORT).show();
                }else{
                    return_location_val ="";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showHomeScreen(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    builder.create().dismiss();
                    startActivity(new Intent(CompleteShipmentFill.this,ShipmentHome.class));
                    finishAffinity();

                });
        builder.create().show();
    }
}
