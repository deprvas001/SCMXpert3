package com.example.scmxpert.views.updateEvent;

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
import com.example.scmxpert.R;
import com.example.scmxpert.ViewModel.ShipmentStatus;
import com.example.scmxpert.ViewModel.ShipmentStatusFactory;
import com.example.scmxpert.adapter.UpdateEventAdapter;
import com.example.scmxpert.apiInterface.CompleteShipment;
import com.example.scmxpert.base.BaseActivity;
import com.example.scmxpert.constants.ApiConstants;
import com.example.scmxpert.databinding.ActivityUpdateEventBinding;
import com.example.scmxpert.helper.SessionManager;
import com.example.scmxpert.model.Shippment;
import com.example.scmxpert.model.UpdateEventDetails;
import com.example.scmxpert.model.UpdateEventModel;
import com.example.scmxpert.model.UpdateSendRequest;
import com.example.scmxpert.service.RetrofitClientInstance;
import com.example.scmxpert.viewClick.RecyclerTouchListener;
import com.example.scmxpert.views.ShipmentHome;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UpdateEvent extends BaseActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener{
    Shippment shippment;
    ActivityUpdateEventBinding updateEventBinding;
    private List<UpdateEventModel> event_list = new ArrayList<>();
    private UpdateEventAdapter eventAdapter;
    ArrayAdapter<String>  reference_Adapter,partner_Adapter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<String> reference_type_list = new ArrayList<>();
    private List<String> partner_list = new ArrayList<>();
    SessionManager session;
    UpdateEventViewModel viewModel;
    String user_name="",partner_name="",token="",partner_id="",reference_id="",customer_id="",partner_id_value="";
    ShipmentStatus shipmentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_event);

        if (getIntent() != null) {
            shippment = (Shippment) getIntent().getSerializableExtra(ApiConstants.SHIPMENT);
        }

        initializeView();
        setClickListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        setSupportActionBar(updateEventBinding.customToolbar);
        updateEventBinding.customToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        updateEventBinding.updateShipmentId.setText(shippment.getShipment_id());
        updateEventBinding.updateShipmentNum.setText(shippment.getShipment_num());
        updateEventBinding.updateTypeReference.setText(shippment.getType_reference());
        updateEventBinding.updateConnectedId.setText(shippment.getDevice_id());
        updateEventBinding.updateDescription.setText(shippment.getGoods_desc());

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String,String> user = session.getUserDetails();
        user_name = user.get(SessionManager.USER_NAME);
        partner_name = user.get(SessionManager.PARTNER_NAME);
        customer_id = user.get(SessionManager.CUSTOMER_ID);
        token = user.get(SessionManager.TOKEN);
   //     prepareData();

        eventAdapter =new  UpdateEventAdapter(UpdateEvent.this,event_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        updateEventBinding.recyclerView.setLayoutManager(mLayoutManager);
        updateEventBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        updateEventBinding.recyclerView.setAdapter(eventAdapter);

        updateEventBinding.referenceType.setOnItemSelectedListener(this);
        updateEventBinding.partnerSpiner.setOnItemSelectedListener(this);
        updateEventBinding.resetEvent.setOnClickListener(this);
        updateEventBinding.updateButton.setOnClickListener(this);
        updateEventBinding.cancelEvent.setOnClickListener(this);

        if (isNetworkAvailable(this)) {
            getDDData();
        } else {
            showAlertDialog(this, getString(R.string.no_connection));
        }

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
                                    String date = updateEvent.get(i).getShip_date_fromBP();
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
                                Toast.makeText(UpdateEvent.this, "Try Later", Toast.LENGTH_SHORT).show();
                            //    Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    private void setClickListener(){
        // row click listener
        updateEventBinding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), updateEventBinding.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UpdateEventModel eventModel = event_list.get(position);

                 if(eventModel.getEvent_status() !=null){
                     if(!eventModel.getEvent_status().equals(getString(R.string.completed))){
                         updateEventBinding.eventIdEt.setText(eventModel.getEvent_id());
                         updateEventBinding.eventTypeEt.setText(eventModel.getEvent());
                         updateEventBinding.partnerNameEdt.setText(eventModel.getPartner());
                         partner_id = eventModel.getPartner();
                         partner_id_value = eventModel.getPartner_id();
                         updateEventBinding.eventIdEt.requestFocus();
                     }
                 }else{
                     updateEventBinding.eventIdEt.setText(eventModel.getEvent_id());
                     updateEventBinding.eventTypeEt.setText(eventModel.getEvent());
                     updateEventBinding.partnerNameEdt.setText(eventModel.getPartner());
                     partner_id = eventModel.getPartner();
                     partner_id_value = eventModel.getPartner_id();
                     updateEventBinding.eventIdEt.requestFocus();
                 }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public void getReferenceType(){
        reference_type_list.clear();
        shipmentViewModel = ViewModelProviders.of(this,new ShipmentStatusFactory(getApplication(),customer_id)).get(ShipmentStatus.class);

        shipmentViewModel.getDropDownData().observe(this, response -> {
            if (response != null) {

                reference_type_list = response.getReference_type();
                reference_type_list.add(0,getString(R.string.select_reference));
                reference_Adapter = new ArrayAdapter<>(UpdateEvent.this, R.layout.spinner_item_layout, reference_type_list);
                reference_Adapter.setDropDownViewResource(R.layout.spinner_item);

                // attaching data adapter to spinner
                updateEventBinding.referenceType.setAdapter(reference_Adapter);

                partner_list = response.getPartner_id();
                partner_list.add(0,getString(R.string.select_partner));
                partner_Adapter = new ArrayAdapter<>(UpdateEvent.this, R.layout.spinner_item_layout, partner_list);
                partner_Adapter.setDropDownViewResource(R.layout.spinner_item);

                // attaching data adapter to spinner
                updateEventBinding.partnerSpiner.setAdapter(partner_Adapter);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_event:
                resetView();
                break;

            case R.id.cancel_event:
                startActivity(new Intent(this, ShipmentHome.class));
                finishAffinity();
                break;

            case R.id.update_button:

                if(updateEventBinding.eventIdEt.getText().toString().isEmpty()){
                    showAlertDialog(UpdateEvent.this,getString(R.string.event_id_error));
                }else if(updateEventBinding.eventTypeEt.getText().toString().isEmpty()){
                    showAlertDialog(UpdateEvent.this,getString(R.string.event_type_error));
                }else if(updateEventBinding.eventReferenceEt.getText().toString().isEmpty()){
                    showAlertDialog(UpdateEvent.this,getString(R.string.event_refer_error));
                }else if( updateEventBinding.eventDescriptionEt.getText().toString().isEmpty()){
                    showAlertDialog(UpdateEvent.this,getString(R.string.event_descrp_error));
                }else if(reference_id.isEmpty()){
                    showAlertDialog(UpdateEvent.this,getString(R.string.type_reference_empty));
                }else if(updateEventBinding.partnerNameEdt.getText().toString().isEmpty()){
                    showAlertDialog(UpdateEvent.this,getString(R.string.partner_id_empty));
                }
                else{
                    if (isNetworkAvailable(this)) {
                        updateEvent();
                    } else {
                        showAlertDialog(this, getString(R.string.no_connection));
                    }

                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
     switch (adapterView.getId()){
         case R.id.reference_type:
             if(position>0){
                 reference_id = adapterView.getSelectedItem().toString();
             //    Toast.makeText(this, reference_id, Toast.LENGTH_SHORT).show();
             }else{
                 reference_id="";
             }
             break;

         case R.id.partner_spiner:
             if(position>0){
                 partner_id = adapterView.getSelectedItem().toString();
               //  Toast.makeText(this, partner_id, Toast.LENGTH_SHORT).show();
             }else{
                 partner_id ="";
             }
             break;
     }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void resetView(){
        updateEventBinding.referenceType.setSelection(0);
        updateEventBinding.partnerSpiner.setSelection(0);
        updateEventBinding.eventIdEt.setText(null);
        updateEventBinding.eventTypeEt.setText(null);
        updateEventBinding.eventReferenceEt.setText(null);
        updateEventBinding.eventDescriptionEt.setText(null);
        updateEventBinding.partnerNameEdt.setText(null);
    }

    public void updateEvent(){
        showProgressDialog(getString(R.string.loading));

        String eventId = updateEventBinding.eventIdEt.getText().toString();
        String eventType = updateEventBinding.eventTypeEt.getText().toString();
        String reference_event = updateEventBinding.eventReferenceEt.getText().toString();
        String comments = updateEventBinding.eventDescriptionEt.getText().toString();
        String event_partner_id  =updateEventBinding.partnerNameEdt.getText().toString();

        UpdateSendRequest updateSendRequest = new UpdateSendRequest();

        updateSendRequest.setShipment_number(shippment.getShipment_id());
     //   updateSendRequest.setPartner_from(event_partner_id);
        updateSendRequest.setPartner(partner_id_value);
        updateSendRequest.setEvent_type(eventType);
        updateSendRequest.setDate_time(getDatetime());
        updateSendRequest.setEvent_id(eventId);
        updateSendRequest.setEvent_reference_number(reference_event);
        updateSendRequest.setType_of_reference(reference_id);
        updateSendRequest.setComments(comments);
        updateSendRequest.setPartner_from(event_partner_id);

        viewModel = ViewModelProviders.of(this).get(UpdateEventViewModel.class);

        viewModel.updateEvent(updateSendRequest).observe(this, updateApiResponse -> {
            hideProgressDialog();
            if(updateApiResponse.code == 401){
                Toast.makeText(this, getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                // showAlertDialog(CreateShipment.this, getString(R.string.session_expire));
                session.logoutUser();
                RetrofitClientInstance.setRetrofit();
            }else if (updateApiResponse == null) {
                // handle error here
                showAlertDialog(UpdateEvent.this,getString(R.string.try_later));
                return;
            }else if (updateApiResponse.getError() == null) {
                // call is successful
                //  Log.i(TAG, "Data response is " + apiResponse.getPosts());
                showCustomAlert(UpdateEvent.this,updateApiResponse.getResponse().getMessage());
            } else {
                // call failed.
                Throwable e = updateApiResponse.getError();
                Toast.makeText(UpdateEvent.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Log.e(TAG, "Error is " + e.getLocalizedMessage());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    public void showCustomAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    builder.create().dismiss();
                    startActivity(new Intent(UpdateEvent.this,ShipmentHome.class));
                    finishAffinity();
                });
        builder.create().show();
    }


}
