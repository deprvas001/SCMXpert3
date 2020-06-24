package com.development.scmxpert.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.development.scmxpert.R;
import com.development.scmxpert.adapter.ShipmentAdapter;
import com.development.scmxpert.apiInterface.CompleteShipment;
import com.development.scmxpert.constants.ApiConstants;
import com.development.scmxpert.helper.SessionManager;
import com.development.scmxpert.model.FilterResponse;
import com.development.scmxpert.model.Shippment;
import com.development.scmxpert.service.RetrofitClientInstance;
import com.development.scmxpert.viewClick.RecyclerTouchListener;
import com.development.scmxpert.views.MapHome;
import com.development.scmxpert.views.ShipmentDetails;
import com.development.scmxpert.views.ShipmentHome;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import static android.content.Context.MODE_PRIVATE;
import static com.development.scmxpert.constants.ApiConstants.SHARED_PREF_NAME;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class LiveShipment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private RecyclerView recyclerView;
    private ShipmentAdapter mAdapter;
    private FloatingActionButton fab;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    SessionManager session;
    private String user_name = "";
    private String partner_name = "";
    private String customer_id="";
    private String token = "";
    SwipeRefreshLayout swipeRefresh;
    private ArrayList<Shippment> liveList = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ArrayList<Shippment> deliverdList = new ArrayList<>();
    int live_count =0,deliver_count=0;
    public LiveShipment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.live_shipment, container, false);
        initializeView();

        getAllShipment();

        mAdapter = new ShipmentAdapter(liveList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        swipeRefresh.setOnRefreshListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Shippment shippment = liveList.get(position);
                Intent intent = new Intent(getActivity(), ShipmentDetails.class);
                intent.putExtra(ApiConstants.SHIPMENT, shippment);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startActivity(new Intent(getActivity(), MapHome.class));
                break;
        }
    }

    public void initializeView() {
        recyclerView =  view.findViewById(R.id.recycler_view);
        swipeRefresh = view.findViewById(R.id.swiperefresh);
        fab =  view.findViewById(R.id.fab);
        sp = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sp.edit();
        fab.setImageResource(R.drawable.map_show);
        fab.setOnClickListener(this);

        session = new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        user_name = user.get(SessionManager.USER_NAME);
        partner_name = user.get(SessionManager.PARTNER_NAME);
        customer_id = user.get(SessionManager.CUSTOMER_ID);
        token = user.get(SessionManager.TOKEN);
    }


    private void getAllShipment() {

        swipeRefresh.setRefreshing(true);
    //    ((ShipmentHome)getActivity()).showProgressDialog(getResources().getString(R.string.loading));


        CompleteShipment apiService = RetrofitClientInstance.getClient(getActivity()).create(CompleteShipment.class);
        disposable.add(
                apiService.getAllShipmentDetails(customer_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Shippment>>() {
                            @Override
                            public void onSuccess(List<Shippment> notes) {


                               swipeRefresh.setRefreshing(false);
                             //   ((ShipmentHome)getActivity()).hideProgressDialog();

                              /*  editor.putString(CUSTOMER_NAME, customer_id);
                                editor.putString(CREATED_BY, partner);
                                editor.apply();*/

                                deliverdList.clear();
                                liveList.clear();
                                if(notes.size() >0){
                                    for(Shippment shippment:notes){
                                        try {
                                            String status = shippment.getDelivery_status();
                                            if(status !=null){
                                                if(!status.equals(getString(R.string.deliver)) ){
                                                    if(((ShipmentHome)getActivity()).getIntent().getExtras()!=null){
                                                        Bundle bundle  = ((ShipmentHome)getActivity()).getIntent().getExtras();
                                                       ArrayList<FilterResponse> itemModel = bundle.getParcelableArrayList("filter_data");

                                                       for(int i =0;i<itemModel.size();i++){
                                                           FilterResponse response = (FilterResponse) itemModel.get(i);

                                                           if(shippment.getShipment_id().equals(response.getShipment_id())){
                                                               liveList.add(shippment);
                                                           }

                                                       }

                                                     /*   if(shippment.getRoute_form().equals(itemModel)
                                                               *//* || shippment.getDepartments().equals(itemModel.getDep_type())*//*
                                                            *//*|| shippment.getDevice_id().equals(itemModel.getDevice())*//* *//* ||
                                                            shippment.getType_reference().equals(itemModel.getReference()) ||
                                                            shippment.getDepartments().equals(itemModel.getDep_type())*//*
                                                        ){
                                                            liveList.add(shippment);
                                                        }*/
                                                    }else{
                                                        liveList.add(shippment);
                                                    }
                                                }
                                                else {

                                                    if(((ShipmentHome)getActivity()).getIntent().getExtras()!=null){
                                                        Bundle bundle  = ((ShipmentHome)getActivity()).getIntent().getExtras();

                                                        ArrayList<FilterResponse> itemModel = bundle.getParcelableArrayList("filter_data");

                                                        for(int i =0;i<itemModel.size();i++){
                                                            FilterResponse response = (FilterResponse) itemModel.get(i);

                                                            if(shippment.getShipment_id().equals(response.getShipment_id())){
                                                                deliverdList.add(shippment);
                                                            }

                                                        }

                                                      /*  if(shippment.getRoute_form().equals(itemModel.getFrom())
                                                                || shippment.getRoute_to().equals(itemModel.getTo())
                                                                || shippment.getGoods_desc().equals(itemModel.getGoods())
                                                                || shippment.getType_reference().equals(itemModel.getReference())
                                                                *//*|| shippment.getDepartments().equals(itemModel.getDep_type())*//*
                                                            *//* || shippment.getDevice_id().equals(itemModel.getDevice())*//* *//*

                                                            shippment.getGoods_desc().equals(itemModel.getGoods()) ||
                                                            shippment.getDevice_id().equals(itemModel.getDevice()) ||
                                                            shippment.getType_reference().equals(itemModel.getReference()) ||
                                                            shippment.getDepartments().equals(itemModel.getDep_type())*//*
                                                        ){
                                                            deliverdList.add(shippment);
                                                        }*/
                                                    }else {
                                                        deliverdList.add(shippment);
                                                    }
                                                }
                                            }else{
                                                liveList.add(shippment);
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }

                                    if(liveList.size()==0){
                                        Snackbar.make(view, "No Data Available", Snackbar.LENGTH_LONG).show();
                                    }
                                    ((ShipmentHome)getActivity()).deliver_count.setText(String.valueOf(deliverdList.size()));
                                    ((ShipmentHome)getActivity()).live_count.setText(String.valueOf(liveList.size()));
                                    mAdapter.notifyDataSetChanged();
                                }else{
                                    Snackbar.make(view, "No Data Available", Snackbar.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {

                                swipeRefresh.setRefreshing(false);
                             //   ((ShipmentHome)getActivity()).hideProgressDialog();
                                Log.e(TAG, "onError: " + e.getMessage());

                                showCustomAlert();

                            }
                        })

        );
    }

    @Override
    public void onRefresh() {
        getActivity().getIntent().removeExtra("filter_data");
        getAllShipment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    public void showCustomAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.internal_server_error))
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    dialog.dismiss();
                    getActivity().finish();
                });
        builder.show();
    }

}
