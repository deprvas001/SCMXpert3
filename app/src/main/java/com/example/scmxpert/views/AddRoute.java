package com.example.scmxpert.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.scmxpert.R;
import com.example.scmxpert.ViewModel.ShipmentStatus;
import com.example.scmxpert.ViewModel.ShipmentStatusFactory;
import com.example.scmxpert.apiInterface.CompleteShipment;
import com.example.scmxpert.base.BaseActivity;
import com.example.scmxpert.constants.ApiConstants;
import com.example.scmxpert.databinding.ActivityAddRouteBinding;
import com.example.scmxpert.helper.SessionManager;
import com.example.scmxpert.model.AddNewRoute;
import com.example.scmxpert.model.AddNewRouteEvent;
import com.example.scmxpert.model.ApiResponse;
import com.example.scmxpert.model.CreateShipmentDrop;
import com.example.scmxpert.model.Event_Details;
import com.example.scmxpert.model.GetRouteDetails;
import com.example.scmxpert.model.RouteSpinnerData;
import com.example.scmxpert.model.Shippment;
import com.example.scmxpert.model.UpdateEventDetails;
import com.example.scmxpert.model.UpdateEventModel;
import com.example.scmxpert.model.UpdateRoute;
import com.example.scmxpert.model.UpdateSendRequest;
import com.example.scmxpert.service.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class AddRoute extends BaseActivity  implements Spinner.OnItemSelectedListener , View.OnClickListener {
    ActivityAddRouteBinding addRouteBinding;
    Shippment shippment;
    SessionManager session;
    ShipmentStatus shipmentViewModel;
    ArrayAdapter<GetRouteDetails>route_desc_adapter;
    ArrayAdapter<String> route_status_adapter,inco_term_adapter,duration_spinner,
            route_from_adapter,route_to_adapter,primary_mode_adapter;
    private List<GetRouteDetails> route_desc_list = new ArrayList<>();
    private List<String> route_from_list = new ArrayList<>();
    private List<String> route_to_list = new ArrayList<>();
    private List<String> primary_mode_list = new ArrayList<>();
    private List<String> inco_term_list = new ArrayList<>();
    private List<String> route_status_list = new ArrayList<>();
    private String type_reference_data = "", device_detail_data = "", route_id = "", goods_data = "",
            goods_id_data = "", user_name = "", partner_name = "", timezone, token = "";
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addRouteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_route);
        setSupportActionBar(addRouteBinding.customToolbar);
        addRouteBinding.customToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));

     /*   if (getIntent() != null) {
            shippment = (Shippment) getIntent().getSerializableExtra(ApiConstants.SHIPMENT);
        }*/

        getShipmentDetail();
        setClickListener();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDDData() {
        showProgressDialog(getResources().getString(R.string.loading));

        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);
        disposable.add(
                apiService.getDDData("SCM0001-A00001")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CreateShipmentDrop>() {
                            @Override
                            public void onSuccess(CreateShipmentDrop shipmentDropDown) {
                                hideProgressDialog();
                                /*   setDropDownItem(shipmentDropDown);*/
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                                Toast.makeText(AddRoute.this, "Try Later", Toast.LENGTH_SHORT).show();
                                finish();
                                Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    private void getShipmentDetail() {
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        user_name = user.get(SessionManager.USER_NAME);
        partner_name = user.get(SessionManager.PARTNER_NAME);
        token = user.get(SessionManager.TOKEN);

        if (user_name != null) {
            addRouteBinding.routeHeaderLayout.internalShipmentId.setText(user_name);
            addRouteBinding.routeHeaderLayout.partnerName.setText(partner_name);
        }
        getRouteDetails();
    }

    private void getRouteDetails() {
        shipmentViewModel = ViewModelProviders.of(this, new ShipmentStatusFactory(getApplication(), user_name)).get(ShipmentStatus.class);
        shipmentViewModel.getRouteDetails().observe(this, new Observer<List<GetRouteDetails>>() {
            @Override
            public void onChanged(List<GetRouteDetails> response) {
                if (response != null) {
                    setData(response);
                }
            }
        });

    }

    private void setData(List<GetRouteDetails> routeDetails) {
        route_desc_list.clear();
        route_from_list.clear();
        route_to_list.clear();
        route_status_list.clear();

        route_from_list.add(0,getString(R.string.select_from));
        route_to_list.add(0,getString(R.string.select_to));
        route_status_list.add(0,getString(R.string.select_status));
        route_status_list.add(1,getString(R.string.active));
        route_status_list.add(2,getString(R.string.draft));
        route_status_list.add(3,getString(R.string.inactive));

         GetRouteDetails route_info = new GetRouteDetails();
         route_info.setDescription(getString(R.string.create_route));
         route_desc_list.add(route_info);

        for(GetRouteDetails route:routeDetails){
            if(route.getDescription()!= null){
               /* if(!route_desc_list.contains(route.getDescription()))*/
                    route_desc_list.add(route);
            }

            if(route.getFrom()!=null){
                /*if(!route_from_list.contains(route.getFrom()))*/
                    route_from_list.add(route.getFrom());
            }

            if(route.getTo() !=null){
               /* if(!route_to_list.contains(route.getTo()))*/
                    route_to_list.add(route.getTo());
            }
        }


        setAdapter();
    }

    private void setAdapter(){
        route_desc_adapter = new ArrayAdapter<GetRouteDetails>(this, android.R.layout.simple_spinner_item, route_desc_list);
        route_desc_adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        addRouteBinding.routeDescription.setAdapter(route_desc_adapter);

        route_from_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, route_from_list);
        route_from_adapter.setDropDownViewResource(R.layout.spinner_item);
        addRouteBinding.routeFrom.setAdapter(route_from_adapter);

        route_to_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, route_to_list);
        route_to_adapter.setDropDownViewResource(R.layout.spinner_item);
        addRouteBinding.routeTo.setAdapter(route_to_adapter);

        primary_mode_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.primary_mode));
        primary_mode_adapter.setDropDownViewResource(R.layout.spinner_item);
        addRouteBinding.primaryMode.setAdapter(primary_mode_adapter);

        inco_term_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inco_terms));
        inco_term_adapter.setDropDownViewResource(R.layout.spinner_item);
        addRouteBinding.incoTerms.setAdapter(inco_term_adapter);

        route_status_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, route_status_list);
        route_status_adapter.setDropDownViewResource(R.layout.spinner_item);
        addRouteBinding.routeStatus.setAdapter(route_status_adapter);

        duration_spinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.time_interval));
        duration_spinner.setDropDownViewResource(R.layout.spinner_item);
        addRouteBinding.timeView.setAdapter(duration_spinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()){
            case R.id.route_description:

                if(position>0){
                    GetRouteDetails routeDetails = (GetRouteDetails) adapterView.getSelectedItem();
                    addRouteBinding.routeId.setText(routeDetails.getRoute_Id());
                    addRouteBinding.customerRouteId.setText(routeDetails.getCustomer_route_id());
                    addRouteBinding.routeDescriptionComment.setText(routeDetails.getDescription());

                    String from_desc = routeDetails.getFrom();

                    for(String from:route_from_list)
                    {
                        if(from_desc.equals(from)){
                            int index = route_from_list.indexOf(from);
                            addRouteBinding.routeFrom.setSelection(position);
                        }
                    }
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setClickListener(){
        addRouteBinding.routeDescription.setOnItemSelectedListener(this);
        addRouteBinding.routeFrom.setOnItemSelectedListener(this);
        addRouteBinding.routeTo.setOnItemSelectedListener(this);
        addRouteBinding.primaryMode.setOnItemSelectedListener(this);
        addRouteBinding.incoTerms.setOnItemSelectedListener(this);
        addRouteBinding.routeStatus.setOnItemSelectedListener(this);
        addRouteBinding.addRoute.setOnClickListener(this);
        addRouteBinding.updateRoute.setOnClickListener(this);
    }

    public void addRoute(){
        showProgressDialog(getString(R.string.loading));
        List<AddNewRouteEvent> routeEvents = new ArrayList<>();

        AddNewRoute addNewRoute = new AddNewRoute();
        addNewRoute.setCustomerId("SCM0001-A00001");
        addNewRoute.setBusinessId("BP00001");
        addNewRoute.setRouteId("20003");
        addNewRoute.setFrom("Exton");
        addNewRoute.setTo("New York");
        addNewRoute.setMode_of_transport("Primary_Mode_Of_Transport");
        addNewRoute.setIncoTerm("DDP");
        addNewRoute.setDescription("Exton To New York, PA by Road, DDP");
        addNewRoute.setPartnerEvents(routeEvents);

        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);


        disposable.add(apiService.addRoute(addNewRoute)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        if(response.getStatus()){
                            showAlertDialog(AddRoute.this,response.getMessage());
                        }else{
                            showAlertDialog(AddRoute.this,response.getMessage());
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        Toast.makeText(AddRoute.this, "Try Later", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                })
        );

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_route:
                addRoute();
                break;

            case R.id.update_route:
                updateRoute();
                break;
        }

    }


    private void updateRoute(){
        showAlertDialog(AddRoute.this,getString(R.string.loading));
        List<Event_Details> event_details = new ArrayList<>();

        UpdateRoute updateRoute = new UpdateRoute();
        updateRoute.setCustomerId("SCM0002");
        updateRoute.setBusinessId("BP00001");
        updateRoute.setRoute_id("10033");
        updateRoute.setShipFrom("Bangalore");
        updateRoute.setShipTo("Mumbai");
        updateRoute.setPrimaryMode("primaryMode");
        updateRoute.setIncoTerms("2");
        updateRoute.setStandradDuration("2 days");
        updateRoute.setRouteStatus("yes");
        updateRoute.setDescription("Bangalore to Mumbai DDP");
        updateRoute.setPartner_events(event_details);

        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);


        disposable.add(apiService.updateRoute(updateRoute)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        if(response.getStatus()){
                            showAlertDialog(AddRoute.this,response.getMessage());
                        }else{
                            showAlertDialog(AddRoute.this,response.getMessage());
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        Toast.makeText(AddRoute.this, "Try Later", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                })
        );

    }
}
