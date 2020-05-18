package com.example.scmxpert.views;

import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
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
import com.example.scmxpert.apiInterface.CompleteShipment;
import com.example.scmxpert.base.BaseActivity;
import com.example.scmxpert.databinding.ActivityFilterBinding;
import com.example.scmxpert.helper.SessionManager;
import com.example.scmxpert.model.CreateShipmentDrop;
import com.example.scmxpert.model.FilterResponse;
import com.example.scmxpert.model.GoodsItem;
import com.example.scmxpert.model.ShipmentGoods;
import com.example.scmxpert.model.filter.FilterGetResponse;
import com.example.scmxpert.service.RetrofitClientInstance;
import com.example.scmxpert.viewClick.DatePick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FilterScreen extends BaseActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener{
ActivityFilterBinding filterBinding;
    ArrayAdapter<String> device_Adapter, from_Adapter,to_Adapter,department_Adapter;
    private List<GoodsItem> goodsItems = new ArrayList<>();
    private List<String> device_id_list = new ArrayList<>();
    private List<String> from_list = new ArrayList<>();
    private List<String> to_list = new ArrayList<>();
    private List<String> department_list = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    ArrayAdapter<GoodsItem> good_adapter;
    private DatePickerDialog mDatePickerDialog;
    String from_city,to_city,select_good,device, dep_type ;
    Intent intent;
    SessionManager session;
    private String created_date="";
    private String user_name = "";
    private String partner_name = "";
    private String customer_id="";
    private String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterBinding = DataBindingUtil.setContentView(this,R.layout.activity_filter);
        session = new SessionManager(getApplicationContext());
        setSupportActionBar(filterBinding.customToolbar);
        filterBinding.customToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        getUserData();
        getDDData();
        setClickListener();
    }

    private void getUserData(){
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        user_name = user.get(SessionManager.USER_NAME);
        partner_name = user.get(SessionManager.PARTNER_NAME);
        customer_id = user.get(SessionManager.CUSTOMER_ID);
        token = user.get(SessionManager.TOKEN);

        HashMap<String, String> filter_data = session.getFilter();
        String from = filter_data.get(SessionManager.FROM);
        String to = filter_data.get(SessionManager.TO);
        String goods = filter_data.get(SessionManager.GOODS);
        String date = filter_data.get(SessionManager.DATE);
        String reference = filter_data.get(SessionManager.REFERENCE);
        String device = filter_data.get(SessionManager.DEVICE);
        String dept = filter_data.get(SessionManager.DEPT);
        String ship_number = filter_data.get(SessionManager.SHIP_NUM);

        /*if(!date.isEmpty() && from !=null){
            filterBinding.shipDateTxt.setText(date);
        }else{
            filterBinding.shipDateTxt.setText(null);
        }*/
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
                apiService.getDDData(customer_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CreateShipmentDrop>() {
                            @Override
                            public void onSuccess(CreateShipmentDrop shipmentDropDown) {
                                hideProgressDialog();
                               setDropDownItem(shipmentDropDown);
                               getFilterData();
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                                Toast.makeText(FilterScreen.this, getString(R.string.try_later), Toast.LENGTH_SHORT).show();
                                finish();
                                //  Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
      //  getIntent().removeExtra("tag");
    }

    private void setDropDownItem(CreateShipmentDrop dropDownItem) {
        goodsItems.clear();
        device_id_list.clear();

        for (ShipmentGoods route : dropDownItem.getRoutes_type()) {
            if(!from_list.contains(route.getFrom()))
            from_list.add(route.getFrom());
            if(!to_list.contains(route.getTo()))
            to_list.add(route.getTo());
            }

        from_list.add(0,"Select From");
        to_list.add(0,"Select To");

       /* device_id_list = dropDownItem.getDeviceId();
        device_id_list.add(0, "Select Device");*/
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

        /*device_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, device_id_list);
        device_Adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        filterBinding.deviceIdSpinner.setAdapter(device_Adapter);*/

        /*

        reference_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reference_type_list);
        reference_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        createShipmentBinding.typeReference.setAdapter(reference_Adapter);*/

        good_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goodsItems);
        good_adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        filterBinding.goodSpinner.setAdapter(good_adapter);


        from_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, from_list);
        from_Adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        filterBinding.fromSpinner.setAdapter(from_Adapter);


        to_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, to_list);
        to_Adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        filterBinding.toSpinner.setAdapter(to_Adapter);
    }

    private void setClickListener(){
        filterBinding.searchButton.setOnClickListener(this);
     //   filterBinding.shipDateTxt.setOnClickListener(this);
        filterBinding.cancelButton.setOnClickListener(this);
        filterBinding.fromSpinner.setOnItemSelectedListener(this);
        filterBinding.toSpinner.setOnItemSelectedListener(this);
        filterBinding.goodSpinner.setOnItemSelectedListener(this);
        filterBinding.deviceIdSpinner.setOnItemSelectedListener(this);
        filterBinding.departmentType.setOnItemSelectedListener(this);
        filterBinding.cancelButton.setOnClickListener(this);

        setDateTimeField();
        filterBinding.shipDateTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mDatePickerDialog.show();
                return false;
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ship_date_txt:
                new DatePick(this, filterBinding.shipDateTxt);
                break;

            case R.id.search_button:

                if (isNetworkAvailable(this)) {
                    searchShipment();
                } else {
                    showAlertDialog(this, getString(R.string.no_connection));
                }

                 break;

            case R.id.cancel_button:
                 resetView();
                 break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()){
            case R.id.from_spinner:
                if(position > 0){
                   from_city = adapterView.getSelectedItem().toString();
                }else{
                    from_city ="";
                }
                break;

            case R.id.to_spinner:
                if(position > 0){
                    to_city = adapterView.getSelectedItem().toString();
                }else{
                    to_city ="";
                }
                break;

            case R.id.good_spinner:
                if(position > 0){
                    select_good = adapterView.getSelectedItem().toString();
                }else{
                    select_good ="";
                }
                break;

            case R.id.device_id_spinner:
                if(position > 0){
                    device = adapterView.getSelectedItem().toString();
                }else{
                    device ="";
                }
                break;

            case R.id.department_type:
                if(position > 0){
                    dep_type  = adapterView.getSelectedItem().toString();
                }else{
                    dep_type  ="";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void searchShipment(){
        HashMap<String,String> filter_key = new HashMap<>();
        String ship_date = filterBinding.shipDateTxt.getText().toString();

        String reference=filterBinding.referenceTxt.getText().toString();
        String delivery_number = filterBinding.deliveryNumber.getText().toString();
        if(!from_city.isEmpty()){
            filter_key.put("from",from_city);
        }
        if(!to_city.isEmpty()){
            filter_key.put("to",to_city);
        }
        if(!select_good.isEmpty()){
            filter_key.put("goods",select_good);
        }

        if(!device.isEmpty()){
            filter_key.put("Device_Id",device);
        }

        if(!delivery_number.isEmpty()){
            filter_key.put("Shipment_Num",delivery_number);
        }

        if(!reference.isEmpty()){
            filter_key.put("reference",reference);
        }

        if(!ship_date.isEmpty()){
            filter_key.put("Created_Date",created_date);
        }

        if(dep_type!=null){
            if(!dep_type.isEmpty()){
                filter_key.put("Customer_Business",dep_type);
            }
        }

            filter_key.put("Customer_Id",customer_id);

        session.setFilter(from_city,to_city,select_good,
                ship_date,delivery_number,reference,
                device,dep_type);


        /*if(!ship_date.isEmpty()){
            filter_key.put("created_Date",ship_date);
        }*/
        /*if(!ship_date.isEmpty()){
            filter_key.put("","");
        }*/

            findFilterData(filter_key);

       /* FilterItemModel*/
       /* FilterItemModel itemModel = new FilterItemModel(from_city,to_city,
                select_good,device,dep_type,ship_date,reference,delivery_number);


        if(getIntent().getExtras()!= null){
            Bundle bundle = getIntent().getExtras();
           // String tag = bundle.getString("tag");
            intent = new Intent(FilterScreen.this,MapHome.class);
            intent.putExtra("filter_data",itemModel);
            startActivity(intent);
            finish();
        }else{
            intent = new Intent(FilterScreen.this,ShipmentHome.class);
            intent.putExtra("filter_data",itemModel);
            startActivity(intent);
            finish();
        }*/
    }


    private void getFilterData(){
        showProgressDialog(getResources().getString(R.string.loading));
        department_list.clear();
        device_id_list.clear();
        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);
        disposable.add(
                apiService.getFilterData(customer_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<FilterGetResponse>() {
                            @Override
                            public void onSuccess(FilterGetResponse filterGetResponse) {
                                hideProgressDialog();
                                department_list = filterGetResponse.getDepartments();
                                device_id_list = filterGetResponse.getDevice_Id();
                                setFilterSpinner(department_list,device_id_list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                                Toast.makeText(FilterScreen.this, getString(R.string.try_later), Toast.LENGTH_SHORT).show();
                                finish();
                                //  Log.e(TAG, "onError: " + e.getMessage());
                            }


                        })


        );
    }

    private void setFilterSpinner(List<String> department_list,List<String> device_id_list){
        department_list.add(0,"Select Dept/Type");
        department_Adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, department_list);
        department_Adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        filterBinding.departmentType.setAdapter(department_Adapter);

        device_id_list.add(0,"Select Device");
        device_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, device_id_list);
        device_Adapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        filterBinding.deviceIdSpinner.setAdapter(device_Adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void findFilterData(HashMap<String,String> filter){
        showProgressDialog(getResources().getString(R.string.loading));
        department_list.clear();
        CompleteShipment apiService = RetrofitClientInstance.getClient(this).create(CompleteShipment.class);
        disposable.add(
                apiService.getFilterResponse(filter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArrayList<FilterResponse>>() {
                            @Override
                            public void onSuccess(ArrayList<FilterResponse> shippments) {
                                hideProgressDialog();
                               /* manager.createLoginSession
                                        (userDetails.getCustomer_Name(),userDetails.getUserName(), userDetails.getUserName(), token,userDetails.getCustomer_Id());*/

                                if(getIntent().getExtras()!= null){
                                    // String tag = bundle.getString("tag");
                                    intent = new Intent(FilterScreen.this,MapHome.class);
                                    intent.putParcelableArrayListExtra("filter_data",shippments);
                                   // intent.putExtra("filter_data",shippments);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    intent = new Intent(FilterScreen.this,ShipmentHome.class);
                                    intent.putParcelableArrayListExtra("filter_data",shippments);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgressDialog();
                                Toast.makeText(FilterScreen.this, getString(R.string.try_later), Toast.LENGTH_SHORT).show();
                                finish();
                                //  Log.e(TAG, "onError: " + e.getMessage());
                            }


                        })


        );
    }


    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
            //  sd.setTimeZone(TimeZone.getTimeZone("UTC"));
            final Date startDate = newDate.getTime();
            String fdate = sd1.format(startDate);
            created_date =   sd.format(startDate);
            filterBinding.shipDateTxt.setText(fdate);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    //    mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        /*  mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());*/

    }

    private void resetView(){
        filterBinding.shipDateTxt.setText(null);
        filterBinding.fromSpinner.setSelection(0);
        filterBinding.referenceTxt.setText(null);
        filterBinding.deliveryNumber.setText(null);
        filterBinding.toSpinner.setSelection(0);
        filterBinding.goodSpinner.setSelection(0);
        filterBinding.deviceIdSpinner.setSelection(0);
        from_city="";
        to_city="";
        select_good="";
        device="";
        dep_type="";

    }
}
