package com.example.scmxpert.views.createShipment;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;
import com.example.scmxpert.R;
import com.example.scmxpert.adapter.EventsAdapter;
import com.example.scmxpert.apiInterface.CompleteShipment;
import com.example.scmxpert.base.BaseActivity;
import com.example.scmxpert.databinding.ActivityCreateShipmentBinding;
import com.example.scmxpert.helper.CommonMethod;
import com.example.scmxpert.helper.SessionManager;
import com.example.scmxpert.model.CreateShipmentDrop;
import com.example.scmxpert.model.Event_Details;
import com.example.scmxpert.model.GoodsItem;
import com.example.scmxpert.model.RouteSpinnerData;
import com.example.scmxpert.model.ShipmentGoods;
import com.example.scmxpert.model.createShipment.CreateEvent;
import com.example.scmxpert.model.createShipment.CreateShipmentRequest;
import com.example.scmxpert.service.RetrofitClientInstance;
import com.example.scmxpert.viewClick.RecyclerItemTouchHelper;
import com.example.scmxpert.views.AddRoute;
import com.example.scmxpert.views.QrCodeScreen;
import com.example.scmxpert.views.ShipmentHome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import static com.example.scmxpert.constants.ApiConstants.QRCODE_KEY;

public class CreateShipment extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    ActivityCreateShipmentBinding createShipmentBinding;
    ArrayAdapter<String> device_Adapter, reference_Adapter;
    ArrayAdapter<RouteSpinnerData> route_adapter;
    ArrayAdapter<GoodsItem> good_adapter;
    private DatePickerDialog mDatePickerDialog;
    private EventsAdapter mAdapter;
    public static final int QRCODE_REQUEST_CODE = 99;
    public static final int QRCODE_DEVICE=100;
    private List<String> reference_type_list = new ArrayList<>();
    private List<String> device_id_list = new ArrayList<>();
    private List<String> good_type_list = new ArrayList<>();
    private List<String> route_list = new ArrayList<>();
    private List<RouteSpinnerData> route_data = new ArrayList<>();
    private List<GoodsItem> goodsItems = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private String type_reference_data = "";
    private String device_detail_data = "";
    private String route_id = "";
    private String goods_data = "";
    private String goods_id_data = "";
    private String user_name = "";
    private String customer_name="";
    private String partner_name = "";
    private String customer_id="";
    private String token = "";
    private String fdate = "";
    CreateShipmentViewModel viewModel;
    private RouteSpinnerData routeSpinnerData;
    SessionManager session;
    List<Event_Details> event_list = new ArrayList<>();
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createShipmentBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_shipment);
        setSupportActionBar(createShipmentBinding.customToolbar);
        createShipmentBinding.customToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        setClickListener();
        getShipmentDetail();

    }

    private void setClickListener() {
        createShipmentBinding.shipmentNumber.setOnClickListener(this);
        createShipmentBinding.expectDate.setOnClickListener(this);
        createShipmentBinding.submitShipment.setOnClickListener(this);
        createShipmentBinding.routeView.setOnClickListener(this);
        createShipmentBinding.typeReference.setOnItemSelectedListener(this);
        createShipmentBinding.routeSpinner.setOnItemSelectedListener(this);
        createShipmentBinding.deviceSpinner.setOnItemSelectedListener(this);
        createShipmentBinding.goodsSpinner.setOnItemSelectedListener(this);
        createShipmentBinding.deviceNumber.setOnClickListener(this);

        createShipmentBinding.shipmentNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (createShipmentBinding.shipmentNumber.getRight() - createShipmentBinding.shipmentNumber.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        i = new Intent(CreateShipment.this, QrCodeScreen.class);
                        startActivityForResult(i, QRCODE_REQUEST_CODE);
                        return true;
                    }
                }
                return false;
            }
        });

        createShipmentBinding.deviceNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (createShipmentBinding.deviceNumber.getRight() - createShipmentBinding.deviceNumber.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        i = new Intent(CreateShipment.this, QrCodeScreen.class);
                        startActivityForResult(i, QRCODE_DEVICE);
                        return true;
                    }
                }
                return false;
            }
        });

        setDateTimeField();
        createShipmentBinding.expectDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mDatePickerDialog.show();
                return false;
            }
        });

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(createShipmentBinding.recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shipment_number:
              /*   i = new Intent(CreateShipment.this, QrCodeScreen.class);
                startActivityForResult(i, QRCODE_REQUEST_CODE);*/
                break;

            case R.id.device_number:
                showDeviceList();
                break;

           /* case R.id.expect_date:

               new DatePick(this, createShipmentBinding.expectDate);
                break;*/

            case R.id.route_view:
                i = new Intent(CreateShipment.this, AddRoute.class);
                startActivity(i);
                break;

            case R.id.submit_shipment:
                if (CommonMethod.isNetworkAvailable(CreateShipment.this)) {
                    String internal_shipment_id = createShipmentBinding.shipmentDetails.internalShipmentId.getText().toString();
                    String customer_name = createShipmentBinding.shipmentDetails.customerName.getText().toString();
                    String partner_name = createShipmentBinding.shipmentDetails.partnerName.getText().toString();
                    String shipment_number = createShipmentBinding.shipmentNumber.getText().toString();
                    String description = createShipmentBinding.shipmentDescription.getText().toString();
                    String expect_date = createShipmentBinding.expectDate.getText().toString();

                    if (internal_shipment_id.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.internal_shipment_empty));
                    } else if (customer_name.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.customer_name_empty));
                    } else if (partner_name.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.partner_name_empty));
                    } else if (shipment_number.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.shipment_number_empty));
                    } else if (description.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.description_empty));
                    } else if (type_reference_data.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.type_reference_empty));
                    } else if (route_id.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.route_empty));
                    } else if (createShipmentBinding.deviceNumber.getText().toString().isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.device_empty));
                    } else if (expect_date.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.expect_date_empty));
                    } else if (goods_data.isEmpty()) {
                        showAlertDialog(CreateShipment.this, getString(R.string.goods_empty));
                    } else {
                        createShipment();
                    }
                } else {
                    CommonMethod.showAlert(getString(R.string.no_connection), CreateShipment.this);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QRCODE_REQUEST_CODE) {
                String qr_code = data.getExtras().getString(QRCODE_KEY);
                createShipmentBinding.shipmentNumber.setText(qr_code);
            }else if (requestCode == QRCODE_DEVICE) {
                String qr_code = data.getExtras().getString(QRCODE_KEY);
                createShipmentBinding.deviceNumber.setText(qr_code);
            }

        }
    }

    private void getShipmentDetail() {
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        customer_name = user.get(SessionManager.CUSTOMER_NAME);
        user_name = user.get(SessionManager.USER_NAME);
        partner_name = user.get(SessionManager.PARTNER_NAME);
        customer_id = user.get(SessionManager.CUSTOMER_ID);
        token = user.get(SessionManager.TOKEN);

        if (user_name != null) {
            createShipmentBinding.shipmentDetails.customerName.setText(customer_name);
            createShipmentBinding.shipmentDetails.partnerName.setText(partner_name);
        }
        getDDData();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void getDDData() {
        showProgressDialog(getResources().getString(R.string.loading));

        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);
        disposable.add(
                apiService.getDDData(customer_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CreateShipmentDrop>() {
                            @Override
                            public void onSuccess(CreateShipmentDrop shipmentDropDown) {
                                hideProgressDialog();
                                setDropDownItem(shipmentDropDown);
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                                Toast.makeText(CreateShipment.this, getString(R.string.try_later), Toast.LENGTH_SHORT).show();
                                finish();
                              //  Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    private void setDropDownItem(CreateShipmentDrop dropDownItem) {
        reference_type_list.clear();
        device_id_list.clear();
        good_type_list.clear();
        route_list.clear();
        route_data.clear();
        goodsItems.clear();
        device_id_list = dropDownItem.getDeviceId();
        reference_type_list = dropDownItem.getReference_type();
        good_type_list.add(0, "Select Goods");
    //    device_id_list.add(0, "Select Device");
        route_list.add(0, "Select Route");
        reference_type_list.add(0, "Select Reference");


        RouteSpinnerData route_start = new RouteSpinnerData();
        route_start.setRoute_from("Select Route");
        route_start.setRoute_to("");
        route_start.setMode_of_transport("");
        route_start.setInco_term("");


        route_data.add(0, route_start);
        for (ShipmentGoods route : dropDownItem.getRoutes_type()) {
            RouteSpinnerData route_spinner = new RouteSpinnerData();
            route_spinner.setRoute_from(route.getFrom());
            route_spinner.setRoute_to(route.getTo());
            route_spinner.setMode_of_transport(route.getMode_of_transport());
            route_spinner.setInco_term(route.getInco_term());
            route_spinner.setRoute_id(route.getRouteId());
            route_spinner.setEvent_details(route.getEvents());

            route_data.add(route_spinner);
            // route_list.add(route.getFrom()+","+route.getTo()+","+route.getMode_of_transport()+","+route.getInco_term());
        }

        GoodsItem goodsItem = new GoodsItem();
        goodsItem.setGoods("Select Goods");

        goodsItems.add(0, goodsItem);

        for (ShipmentGoods route : dropDownItem.getGoods_type()) {

            GoodsItem good_spinner = new GoodsItem();
            good_spinner.setGoods(route.getGood_item());
            good_spinner.setGoods_id(route.getGoods_id());

            goodsItems.add(good_spinner);
            // route_list.add(route.getFrom()+","+route.getTo()+","+route.getMode_of_transport()+","+route.getInco_term());
        }

        device_Adapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, device_id_list);
        device_Adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        createShipmentBinding.deviceSpinner.setAdapter(device_Adapter);

        reference_Adapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, reference_type_list);
        reference_Adapter.setDropDownViewResource(R.layout.spinner_item);

        createShipmentBinding.typeReference.setAdapter(reference_Adapter);

        good_adapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, goodsItems);
        good_adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        createShipmentBinding.goodsSpinner.setAdapter(good_adapter);

        route_adapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, route_data);
        route_adapter.setDropDownViewResource(R.layout.spinner_item);
        // attaching data adapter to spinner
        createShipmentBinding.routeSpinner.setAdapter(route_adapter);

        createShipmentBinding.shipmentDetails.internalShipmentId.setText(dropDownItem.getInternal_shipment_id());
    }

    private void createShipment() {
        showProgressDialog(getString(R.string.loading));

        List<CreateEvent> createEventList = new ArrayList<>();
        List<String> comments = new ArrayList<>();


            for (int i = 0; i < event_list.size(); i++) {
                CreateEvent event = new CreateEvent();
                Event_Details details = event_list.get(i);
                event.setEventId(details.getEvent_Id());
                event.setBp_Id(details.getBp_id());
                event.setPartner(details.getBp_name());
                event.setEvent(details.getEvent_name());
                event.setEvidence(details.getEvidence());
              //  event.setDate("");
                createEventList.add(event);
            }

            for(int i=0;i<createEventList.size();i++){
                if(i==0){
                    createEventList.get(i).setEvent_Status("Initialized");
                }else{
                    createEventList.get(i).setEvent_Status("Queued");
                }

            }

            comments.add(createShipmentBinding.shipmentDescription.getText().toString());

            CreateShipmentRequest shipmentRequest = new CreateShipmentRequest();
            shipmentRequest.setInternalShipmentId(createShipmentBinding.shipmentDetails.internalShipmentId.getText().toString());
            shipmentRequest.setShipmentNumber(createShipmentBinding.shipmentNumber.getText().toString());
            shipmentRequest.setShipmentId(createShipmentBinding.shipmentDetails.internalShipmentId.getText().toString());
            shipmentRequest.setCustomerId(customer_id);
            shipmentRequest.setTypeOfReference(type_reference_data);
            shipmentRequest.setComment(comments);
            shipmentRequest.setRouteId(routeSpinnerData.getRoute_id());
            shipmentRequest.setRouteFrom(routeSpinnerData.getRoute_from());
            shipmentRequest.setRouteTo(routeSpinnerData.getRoute_to());
            shipmentRequest.setGoodsId(goods_id_data);
            shipmentRequest.setGoodsType(goods_data);
            shipmentRequest.setParnterFrom(partner_name);
            shipmentRequest.setDeviceId(createShipmentBinding.deviceNumber.getText().toString());
            shipmentRequest.setAllEvents(createEventList);
            shipmentRequest.setIncoTerms(routeSpinnerData.getInco_term());
            shipmentRequest.setMode(routeSpinnerData.getMode_of_transport());
            shipmentRequest.setTemp("");
            shipmentRequest.setrH("");
            shipmentRequest.setDatee(timeCreate());
            shipmentRequest.setEvent("Preparation");
            shipmentRequest.setEstimatedDeliveryDate(fdate);

        viewModel = ViewModelProviders.of(this).get(CreateShipmentViewModel.class);

        viewModel.createShipment(token,shipmentRequest).observe(this, apiResponse -> {

            if(apiResponse.code == 401){
                Toast.makeText(this, getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
               // showAlertDialog(CreateShipment.this, getString(R.string.session_expire));
                session.logoutUser();
                RetrofitClientInstance.setRetrofit();
            }
            else if (apiResponse == null) {
                hideProgressDialog();
                // handle error here
                showAlertDialog(CreateShipment.this, getString(R.string.try_later));
                return;
            } else if (apiResponse.getError() == null) {
                 hideProgressDialog();
                // call is successful
                //  Log.i(TAG, "Data response is " + apiResponse.getPosts());
                showCustomDialog(CreateShipment.this, getString(R.string.shipment_created_successful));
              //  Toast.makeText(this,"Shipment Create Successfully.", Toast.LENGTH_SHORT).show();

             //   finish();
            } else {
                // call failed.
                hideProgressDialog();
                Throwable e = apiResponse.getError();
                Toast.makeText(CreateShipment.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Log.e(TAG, "Error is " + e.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        switch (adapterView.getId()) {
            case R.id.route_spinner:
                if (position > 0) {
                    RouteSpinnerData data = (RouteSpinnerData) createShipmentBinding.routeSpinner.getSelectedItem();
                    routeSpinnerData = data;
                    route_id = data.getRoute_id();
                    event_list = data.getEvent_details();
                    setRecyclerView();
                    mAdapter.notifyDataSetChanged();
                }else{
                    event_list.clear();
                   // mAdapter.notifyDataSetChanged();
                    setRecyclerView();
                }
                break;

            case R.id.type_reference:
                if (position > 0) {
                    type_reference_data = createShipmentBinding.typeReference.getSelectedItem().toString();
                }
                break;

            case R.id.device_spinner:
                if (position > 0) {
                    device_detail_data = createShipmentBinding.deviceSpinner.getSelectedItem().toString();
                }
                break;

            case R.id.goods_spinner:
                if (position > 0) {
                    GoodsItem item = (GoodsItem) createShipmentBinding.goodsSpinner.getSelectedItem();
                    goods_data = item.getGoods();
                    goods_id_data = item.getGoods_id();
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private String timeCreate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
     //   sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timezone = sdf.format(c.getTime());
        return timezone;
    }


   private void setRecyclerView(){
       mAdapter = new EventsAdapter(this, event_list);
       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
       createShipmentBinding.recyclerView.setLayoutManager(mLayoutManager);
       createShipmentBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
     //  createShipmentBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
       createShipmentBinding.recyclerView.setAdapter(mAdapter);
   }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof EventsAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = event_list.get(viewHolder.getAdapterPosition()).getEvent_name();

            // backup of removed item for undo purpose
            final Event_Details deletedItem = event_list.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
          /*  Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
        }
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
              //  sd.setTimeZone(TimeZone.getTimeZone("UTC"));
                final Date startDate = newDate.getTime();
                 fdate = sd.format(startDate);
                String final_date = sd1.format(startDate);

                createShipmentBinding.expectDate.setText(final_date);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
      /*  mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());*/

    }

    public String convertDate(String input){
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            //convert string to date
            d = inputFormat.parse(input);
        } catch (ParseException e) {
            System.out.println("Date Format Not Supported");
            e.printStackTrace();
        }

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return  outputFormat.format(d).toString();
    }

    public void showCustomDialog(Context context, String message) {
        String shipment_id = createShipmentBinding.shipmentDetails.internalShipmentId.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SCMXpert")
                .setMessage("Shipment No: "+shipment_id+" "+message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    dialog.dismiss();
                    startActivity(new Intent(this, ShipmentHome.class));
                    finish();
                });
        builder.show();
    }

   private void showDeviceList(){
       AlertDialog.Builder builder = new AlertDialog.Builder(CreateShipment.this);
       builder.setTitle("Select Device");

       String device_list[] = new String[device_id_list.size()];

       // ArrayList to Array Conversion
       for (int j = 0; j < device_id_list.size(); j++) {

           // Assign each value to String array
           device_list[j] = device_id_list.get(j);
       }
// add a list

       builder.setItems(device_list, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
              // createShipmentBinding.deviceNumber.setText(dialog.);
              createShipmentBinding.deviceNumber.setText(device_list[which]);
           }
       });

// create and show the alert dialog
       AlertDialog dialog = builder.create();
       dialog.show();
    }
}
