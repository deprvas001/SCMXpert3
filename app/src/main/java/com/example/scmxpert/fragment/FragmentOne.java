package com.example.scmxpert.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scmxpert.BuildConfig;
import com.example.scmxpert.R;
import com.example.scmxpert.ViewModel.ShipmentStatus;
import com.example.scmxpert.ViewModel.ShipmentStatusFactory;
import com.example.scmxpert.adapter.ShipmentDetailAdapter;
import com.example.scmxpert.constants.ApiConstants;
import com.example.scmxpert.databinding.FragmentOneBinding;
import com.example.scmxpert.model.ShipmentDetail;
import com.example.scmxpert.model.Shippment;
import com.example.scmxpert.views.CompleteShipmentFill;
import com.example.scmxpert.views.ShipmentDetails;
import com.example.scmxpert.views.ShipmentHome;
import com.example.scmxpert.views.updateEvent.UpdateEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment implements View.OnClickListener {
    private ShipmentDetailAdapter mAdapter;
    Shippment shippment;
    View view;
    private ArrayList<ShipmentDetail> shipmentArrayList = new ArrayList<>();
    ShipmentStatus shipmentViewModel;
    FragmentOneBinding fragmentOneBinding;
    Intent intent;
    int event_status_count;
    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_one,container,false);

        // Inflate the layout for this fragment
         view = fragmentOneBinding.getRoot();
         if(getActivity().getIntent()!=null){
            shippment = (Shippment) getActivity().getIntent().getSerializableExtra(ApiConstants.SHIPMENT);
          }
         initializeView();
         setView();

        mAdapter = new ShipmentDetailAdapter(shipmentArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentOneBinding.recyclerView.setLayoutManager(mLayoutManager);
        fragmentOneBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentOneBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        fragmentOneBinding.recyclerView.setAdapter(mAdapter);
        getShipmentDetails();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.complete_shipment:
               fragmentOneBinding.shipmentLayout.updateEvent.
                       setBackgroundDrawable(getResources().getDrawable(R.drawable.button_second_background));
               fragmentOneBinding.shipmentLayout.updateEvent.setTextColor(getResources().getColor(R.color.view_background));
                fragmentOneBinding.shipmentLayout.shareButton.
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.button_second_background));
                fragmentOneBinding.shipmentLayout.shareButton.setTextColor(getResources().getColor(R.color.view_background));
                fragmentOneBinding.shipmentLayout.completeShipment.
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background));
                fragmentOneBinding.shipmentLayout.completeShipment.setTextColor(getResources().getColor(R.color.color_white));

                if(event_status_count==0){
                    showCustomAlert(getActivity(),getActivity().getString(R.string.event_completed));
                }else if(event_status_count ==1){
                    startCompleteEvent();
                }else{
                    showCustomAlert(getActivity(),getActivity().getString(R.string.complete_status_event));
                }

                break;

            case R.id.update_event:
                int count =0;
                fragmentOneBinding.shipmentLayout.completeShipment.
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.button_second_background));
                fragmentOneBinding.shipmentLayout.completeShipment.setTextColor(getResources().getColor(R.color.view_background));
                fragmentOneBinding.shipmentLayout.updateEvent.
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background));
                fragmentOneBinding.shipmentLayout.updateEvent.setTextColor(getResources().getColor(R.color.color_white));

                fragmentOneBinding.shipmentLayout.shareButton.
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.button_second_background));
                fragmentOneBinding.shipmentLayout.shareButton.setTextColor(getResources().getColor(R.color.view_background));

                if(event_status_count==0){
                    showCustomAlert(getActivity(),getActivity().getString(R.string.event_completed));
                }else if(event_status_count ==1){
                    showCustomAlert(getActivity(),getActivity().getString(R.string.update_event_status));
                }else{
                    intent= new Intent(getActivity(),UpdateEvent.class);
                    intent.putExtra(ApiConstants.SHIPMENT, shippment);
                    startActivity(intent);
                }

                 break;

            case R.id.share_button:
                fragmentOneBinding.shipmentLayout.completeShipment.
                    setBackgroundDrawable(getResources().getDrawable(R.drawable.button_second_background));
                fragmentOneBinding.shipmentLayout.completeShipment.setTextColor(getResources().getColor(R.color.view_background));
                fragmentOneBinding.shipmentLayout.updateEvent.
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.button_second_background));
                fragmentOneBinding.shipmentLayout.updateEvent.setTextColor(getResources().getColor(R.color.view_background));

                fragmentOneBinding.shipmentLayout.shareButton.
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background));
                fragmentOneBinding.shipmentLayout.shareButton.setTextColor(getResources().getColor(R.color.color_white));

                 shareApp(getActivity());
                break;


        }
    }

    public void getShipmentDetails(){
        ((ShipmentDetails)getActivity()).showProgressDialog(getActivity().getString(R.string.loading));
        shipmentViewModel = ViewModelProviders.of(this,new ShipmentStatusFactory(getActivity().getApplication(),shippment.getShipment_id())).get(ShipmentStatus.class);

        shipmentViewModel.getShipmentDetaillData().observe(this, new Observer<List<ShipmentDetail>>() {
            @Override
            public void onChanged(List<ShipmentDetail> articleResponse) {
                ((ShipmentDetails)getActivity()).hideProgressDialog();
                if (articleResponse != null) {
                     int count =0 ;

                    List<ShipmentDetail> shippments = articleResponse;
                    shipmentArrayList.addAll(shippments);
                    mAdapter.notifyDataSetChanged();

                    for(int i =0;i<shipmentArrayList.size();i++){
                        if(shipmentArrayList.get(i).getEvent_status()!=null){
                            if(shipmentArrayList.get(i).getEvent_status().equals("Completed")){
                                count++;
                            }
                        }
                    }

                    if(count  == shipmentArrayList.size()){
                       event_status_count =0;
                    }else if(count == shipmentArrayList.size()-1){
                        event_status_count =1;
                    }else{
                        event_status_count =2;
                    }
                }
            }
        });
    }

    public void initializeView(){
        fragmentOneBinding.shipmentLayout.updateEvent.setOnClickListener(this);
        fragmentOneBinding.shipmentLayout.completeShipment.setOnClickListener(this);
        fragmentOneBinding.shipmentLayout.shareButton.setOnClickListener(this);
        fragmentOneBinding.shipmentLayout.shareButton.setOnClickListener(this);
    }


    public  String getDate(String time_date){
        if(time_date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                time_date = (sdf1.format(sdf.parse(time_date)));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return  time_date;
    }

    public void setView(){
        fragmentOneBinding.shipmentLayout.customerShippmentId.setText(shippment.getShipment_id());
        String created_date = getDate(shippment.getCreated_date());
        String delivery_date = getDate(shippment.getDelivery_date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        if(!shippment.getDelivery_status().equals(getString(R.string.deliver))){

            if(shippment.getCreated_date()!=null && shippment.getEst_delivery_date()!=null){
                try {
                    fragmentOneBinding.shipmentLayout.createDate.setText(sdf1.format(sdf.parse(shippment.getCreated_date())));
                    fragmentOneBinding.shipmentLayout.deliverDate.setText(sdf1.format(sdf.parse(shippment.getEst_delivery_date())));
                } catch (ParseException e) {
                    e.printStackTrace();
                    if(shippment.getEst_delivery_date()!=null)
                    fragmentOneBinding.shipmentLayout.deliverDate.setText(shippment.getEst_delivery_date());
                    else{
                        fragmentOneBinding.shipmentLayout.deliverDate.setText("NA");
                    }
                }

            }else{
                fragmentOneBinding.shipmentLayout.createDate.setText("NA");
                fragmentOneBinding.shipmentLayout.deliverDate.setText("NA");
            }
        }else{
            if(shippment.getCreated_date()!=null && shippment.getDelivery_date()!=null){
                try {
                    fragmentOneBinding.shipmentLayout.createDate.setText(sdf1.format(sdf.parse(shippment.getCreated_date())));
                    fragmentOneBinding.shipmentLayout.deliverDate.setText(sdf1.format(sdf.parse(shippment.getDelivery_date())));
                } catch (ParseException e) {
                    e.printStackTrace();
                    if(shippment.getDelivery_date()!=null){
                        fragmentOneBinding.shipmentLayout.deliverDate.setText(shippment.getDelivery_date());
                    }else{
                        fragmentOneBinding.shipmentLayout.deliverDate.setText("NA");
                    }
                }

            }else{
                fragmentOneBinding.shipmentLayout.createDate.setText("NA");
                fragmentOneBinding.shipmentLayout.deliverDate.setText("NA");
            }
        }

         if(shippment.getRoute_form()!=null){
             fragmentOneBinding.shipmentLayout.fromDate.setText(shippment.getRoute_form());
         }else{
             fragmentOneBinding.shipmentLayout.fromDate.setText("NA");
         }

         if(shippment.getRoute_to()!=null){
             fragmentOneBinding.shipmentLayout.toDate.setText(shippment.getRoute_to());
         }else{
             fragmentOneBinding.shipmentLayout.toDate.setText("NA");
         }

        if(shippment.getDelivery_status()!=null){
            fragmentOneBinding.shipmentLayout.status.setText("Status: "+shippment.getDelivery_status());
        }else{
            fragmentOneBinding.shipmentLayout.status.setText("Status: NA");
        }

       /* fragmentOneBinding.shipmentLayout.createDate.setText(created_date);
        fragmentOneBinding.shipmentLayout.deliverDate.setText(delivery_date);*/

        if(shippment.getDelivery_status()!=null){
            if(shippment.getDelivery_status().equals("Delivered")){
                fragmentOneBinding.shipmentLayout.progressBar.setProgress(100);
            } else{
                int val = Math.round(Float.parseFloat(shippment.getEvent_status()));
                fragmentOneBinding.shipmentLayout.progressBar.setProgress(val);
            }
        }else{
            fragmentOneBinding.shipmentLayout.progressBar.setProgress(0);
        }

    }


    private static void shareApp(Context context) {
        final String appPackageName = BuildConfig.APPLICATION_ID;
        final String appName = context.getString(R.string.app_name);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBodyText = "https://play.google.com/store/apps/details?id=" +
                appPackageName;
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        context.startActivity(Intent.createChooser(shareIntent, "Share"));
    }

    public void showCustomAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    builder.create().dismiss();

                });
        builder.create().show();

    }

    private void startCompleteEvent(){
        intent = new Intent(getActivity(),CompleteShipmentFill.class);
        intent.putExtra(ApiConstants.SHIPMENT, shippment);
        startActivity(intent);
    }
}
