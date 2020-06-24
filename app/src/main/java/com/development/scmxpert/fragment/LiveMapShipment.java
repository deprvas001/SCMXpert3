package com.development.scmxpert.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.development.scmxpert.R;
import com.development.scmxpert.apiInterface.CompleteShipment;
import com.development.scmxpert.helper.SessionManager;
import com.development.scmxpert.model.FilterResponse;
import com.development.scmxpert.model.Shippment;
import com.development.scmxpert.model.WayPoint;
import com.development.scmxpert.service.RetrofitClientInstance;
import com.development.scmxpert.views.MapHome;
import com.development.scmxpert.views.ShipmentHome;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class LiveMapShipment extends Fragment implements OnMapReadyCallback {
    ArrayList<Object> waveList =new ArrayList<>();
    private View rootView;
    GoogleMap gMap;
    SessionManager session;
    private String user_name = "";
    private String partner_name = "";
    private String customer_id="";
    private String token = "";
    private ArrayList<Shippment> liveList = new ArrayList<>();
    private ArrayList<Shippment> deliverdList = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public LiveMapShipment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.map_fragment_layout, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(this);
        getUserDetail();
        return rootView;
    }


    private void getAllShipment() {
        ((MapHome) getActivity()).showProgressDialog(getResources().getString(R.string.loading));

        CompleteShipment apiService = RetrofitClientInstance.getClient(getActivity()).create(CompleteShipment.class);
        disposable.add(
                apiService.getAllShipmentDetails(customer_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Shippment>>() {
                            @Override
                            public void onSuccess(List<Shippment> notes) {
                                ((MapHome) getActivity()).hideProgressDialog();

                                liveList.clear();
                                deliverdList.clear();
                                for (Shippment shippment : notes) {
                                    if(shippment.getDelivery_status() !=null){
                                        if (!shippment.getDelivery_status().equals(getString(R.string.deliver))) {

                                            if(((MapHome)getActivity()).getIntent().getExtras()!=null) {
                                                Bundle bundle = ((MapHome) getActivity()).getIntent().getExtras();
                                              /*  FilterItemModel  itemModel = bundle.getParcelable("filter_data");
                                              if(shippment.getRoute_form().equals(itemModel.getFrom()) ||
                                                shippment.getRoute_to().equals(itemModel.getTo()) ||
                                                shippment.getGoods_desc().equals(itemModel.getGoods()) ||
                                                shippment.getType_reference().equals(itemModel.getReference())||
                                                 shippment.getDevice_id().equals(itemModel.getDevice()) *//*||
                                                       shippment.getDepartments().equals(itemModel.getDep_type())*//* ){
                                                  liveList.add(shippment);
                                              }*/

                                                ArrayList<FilterResponse> itemModel = bundle.getParcelableArrayList("filter_data");

                                                for(int i =0;i<itemModel.size();i++){
                                                    FilterResponse response = (FilterResponse) itemModel.get(i);

                                                    if(shippment.getShipment_id().equals(response.getShipment_id())){
                                                        liveList.add(shippment);
                                                    }

                                                }
                                            }else{
                                                liveList.add(shippment);
                                            }

                                         //   ((MapHome) getActivity()).live_count.setText(String.valueOf(liveList.size()));
                                        } else {

                                            if(((MapHome)getActivity()).getIntent().getExtras()!=null) {
                                                Bundle bundle = ((MapHome) getActivity()).getIntent().getExtras();
                                              /*  FilterItemModel  itemModel = bundle.getParcelable("filter_data");
                                                if(shippment.getRoute_form().equals(itemModel.getFrom()) ||
                                                        shippment.getRoute_to().equals(itemModel.getTo()) ||
                                                        shippment.getGoods_desc().equals(itemModel.getGoods()) *//*||*//*
                                                       *//* shippment.getType_reference().equals(itemModel.getReference()) ||
                                                        shippment.getDevice_id().equals(itemModel.getDevice())*//*
                                                        *//*shippment.getDepartments().equals(itemModel.getDep_type())*//*){
                                                         deliverdList.add(shippment);
                                                }*/

                                                ArrayList<FilterResponse> itemModel = bundle.getParcelableArrayList("filter_data");

                                                for(int i =0;i<itemModel.size();i++){
                                                    FilterResponse response = (FilterResponse) itemModel.get(i);

                                                    if(shippment.getShipment_id().equals(response.getShipment_id())){
                                                        deliverdList.add(shippment);
                                                    }

                                                }
                                            }else{
                                                deliverdList.add(shippment);
                                            }
                                          //  ((MapHome) getActivity()).deliver_count.setText(String.valueOf(deliverdList.size()));
                                        }
                                    }else{
                                        liveList.add(shippment);
                                    }
                                }

                                ((MapHome) getActivity()).live_count.setText(String.valueOf(liveList.size()));
                                ((MapHome) getActivity()).deliver_count.setText(String.valueOf(deliverdList.size()));

                                List<WayPoint> userList = new ArrayList<>();
                                for(int k=0;k<liveList.size();k++){
                                    String json = String.valueOf(liveList.get(k).getWaypoint());

                                  if(json !=null){
                                      try {
                                          JSONArray array = new JSONArray(json);
                                          int count = array.length();
                                          if(count>0){
                                              JSONArray innerArray = array.getJSONArray(count-1);
                                              WayPoint point = new WayPoint();
                                              point.setLat(innerArray.get(0).toString());
                                              point.setId(liveList.get(k).getShipment_id());
                                              point.setLongt(innerArray.get(1).toString());
                                              point.setFrom(liveList.get(k).getRoute_form());
                                              point.setTo(liveList.get(k).getRoute_to());
                                              point.setReference(liveList.get(k).getType_reference());
                                              point.setDepartment(liveList.get(k).getDepartments());
                                              point.setGoods(liveList.get(k).getGoods_desc());
                                              point.setDevice(liveList.get(k).getDevice_id());
                                              userList.add(point);
                                          }

                                      }catch (JSONException e){
                                          e.printStackTrace();
                                      }
                                  }
                                }

                                if(userList.size()>0){
                                    addMarker(userList);
                                }else{
                                    Toast.makeText(getActivity(), "No Result Found.", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                ((ShipmentHome) getActivity()).hideProgressDialog();
                                Log.e(TAG, "onError: " + e.getMessage());
                            }
                        })
        );
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        waveList.clear();
        gMap = googleMap;
        getAllShipment();


    }

    private void addMarker(List<WayPoint> wayPoints){
        Double init_lat = Double.parseDouble(wayPoints.get(0).getLat());
        Double init_longt = Double.parseDouble(wayPoints.get(0).getLongt());
        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(init_lat, init_longt))
                .zoom(3)
                .bearing(0.5f)
                .tilt(45)
                .build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));



        for(int i=0;i<wayPoints.size();i++){

            Double lat = Double.parseDouble(wayPoints.get(i).getLat());
            Double longt = Double.parseDouble(wayPoints.get(i).getLongt());

            gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, longt))
                    .title(wayPoints.get(i).getId()));
                    //.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.position)));
                    //.snippet("His Talent : Plenty of money"));



             }

    }

    public void getUserDetail(){
        session = new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        user_name = user.get(SessionManager.USER_NAME);
        partner_name = user.get(SessionManager.PARTNER_NAME);
        customer_id = user.get(SessionManager.CUSTOMER_ID);
        token = user.get(SessionManager.TOKEN);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
