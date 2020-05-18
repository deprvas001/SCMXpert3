package com.example.scmxpert.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.scmxpert.R;
import com.example.scmxpert.adapter.CustomMarkerInfoWindowView;
import com.example.scmxpert.apiInterface.CompleteShipment;
import com.example.scmxpert.constants.ApiConstants;
import com.example.scmxpert.model.ShipmentDetail;
import com.example.scmxpert.model.Shippment;
import com.example.scmxpert.model.UserDetails;
import com.example.scmxpert.model.WayInfo;
import com.example.scmxpert.model.WayPoint;
import com.example.scmxpert.service.RetrofitClientInstance;
import com.example.scmxpert.views.ShipmentHome;
import com.example.scmxpert.views.login.LoginScreen;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap googleMap;
    Shippment shippment;
    private ArrayList<ShipmentDetail> data = new ArrayList<>();
    private CompositeDisposable disposable_map = new CompositeDisposable();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment_layout, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(this);

        if(getActivity().getIntent()!=null){
            shippment = (Shippment) getActivity().getIntent().getSerializableExtra(ApiConstants.SHIPMENT);
        }
        return rootView;
    }


    public void getShipmentDetails(){


      /*  List<WayPoint> userList = new  ArrayList<>();
        List<LatLng> route_array = new ArrayList<LatLng>();
        String json = String.valueOf(shippment.getWaypoint());
        if(json !=null) {
            try {
                JSONArray array = new JSONArray(json);
                int count = array.length();
                for (int i = 0; i < count; i++) {
                    JSONArray innerArray = array.getJSONArray(i);
                    WayPoint point = new WayPoint();
                    point.setLat(innerArray.get(0).toString());
                    point.setLongt(innerArray.get(1).toString());
                    point.setId(shippment.getShipment_id());

                    String lat = innerArray.get(0).toString();
                    String longt = innerArray.get(1).toString();
                    userList.add(point);

                 //   createMarker(Double.parseDouble(lat),Double.parseDouble(longt),shippment.getShipment_id());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            if(userList!=null){
                for (int k=0;k<userList.size();k++){
                    String lat = userList.get(k).getLat();
                    String longt = userList.get(k).getLongt();
                    LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(longt));

                    if(!route_array.contains(latLng)){
                        route_array.add(latLng);
                    }
                }
                if(route_array.size()>0)
                drawLine(route_array,shippment.getShipment_id());
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/



       /* ((ShipmentDetails)getActivity()).showProgressDialog(getResources().getString(R.string.loading));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CompleteShipment.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CompleteShipment api = retrofit.create(CompleteShipment.class);
        Call<List<ShipmentDetail>> call = api.getShipmentDetails(shippment.getShipment_id());

        call.enqueue(new Callback<List<ShipmentDetail>>() {
            @Override
            public void onResponse(Call<List<ShipmentDetail>> call, retrofit2.Response<List<ShipmentDetail>> response) {
                try {
                    ((ShipmentDetails)getActivity()).hideProgressDialog();
                    data = (ArrayList<ShipmentDetail>) response.body();
                    Log.e("SCMXpert",data.toString());

                    for(int i=0;i<data.size();i++){
                        Double latitude = Double.parseDouble(data.get(i).getLatitdue());
                        Double longitude = Double.parseDouble(data.get(i).getLongitude());
                        String title = data.get(i).getId();

                        createMarker(latitude,longitude,title*//*, markersArray.get(i).getTitle(), markersArray.get(i).getSnippet(), markersArray.get(i).getIconResID()*//*);
                    }

                } catch (Exception e) {
                    ((ShipmentDetails)getActivity()).hideProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentDetail>> call, Throwable t) {
                ((ShipmentDetails)getActivity()).hideProgressDialog();
                Log.e("SCMXPERT",t.getMessage());
            }
        });*/
    }

    protected Marker createMarker(double latitude, double longitude,String title/*, String title, String snippet, int iconResID*/) {
        LatLng position = new LatLng(latitude, longitude);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(position)
                .zoom(5)
               // .bearing(0)
                .tilt(45)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title));
              /*  .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));*/
    }

    protected Marker createMapMarker(WayInfo wayInfo) {
            String[] wayPoints = wayInfo.getWayPoints().split(",");
            String latitude = wayPoints[0];
            String longitude = wayPoints[1];
            LatLng position = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        CameraPosition googlePlex = CameraPosition.builder()
                .target(position)
                .zoom(3)
                .bearing(0.5f)
                .tilt(45)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

            Marker marker = googleMap.addMarker(new MarkerOptions().position(position));
            marker.setTag(wayInfo);

            return  marker;


        /*return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                .anchor(0.5f, 0.5f)
                *//*.title(wayInfo.get(0).getShipment_Num())*//*);
*/
              /*  .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if(shippment !=null){
            getWayPoints(shippment.getShipment_id());
        }

        CustomMarkerInfoWindowView markerInfoWindowAdapter = new CustomMarkerInfoWindowView(getActivity());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);



        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                 marker.hideInfoWindow();
            }
        });

       /* googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                googleMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.showInfoWindow();
            }
        });*/
       /* googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v =  inflater.inflate(R.layout.marker_info_window, null);
                WayInfo wayInfo =(WayInfo) marker.getTag();
                //  LatLng latLng = arg0.getPosition();
                TextView tvLat = (TextView) v.findViewById(R.id.shipment_number);
                *//* TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);*//*
                tvLat.setText("Latitude:" + wayInfo.getSensorUTC());
                // tvLng.setText("Longitude:"+ latLng.longitude);
            }
        });*/

    }

    public void drawLine(List<WayInfo> points) {
        List<LatLng> route_array = new ArrayList<LatLng>();


        for (int i=0;i<points.size();i++){
            String[] wayPo = points.get(i).getWayPoints().split(",");
            String lati =wayPo[0];
            String longi = wayPo[1];
            LatLng latLng = new LatLng(Double.parseDouble(lati),Double.parseDouble(longi));

            if(!route_array.contains(latLng)){
                route_array.add(latLng);
            }

            createMapMarker(points.get(i));
        }


       /* List<LatLng> source_destination = new ArrayList<>();
       source_destination.add(points.get(0));
       source_destination.add(points.get(points.size()-1));*/

       /* for(int i=0;i<points.size();i++){

            LatLng latLng = points.get(i);
            double lat = latLng.latitude;
            double longt = latLng.longitude;
            createMarker(lat,longt,shipment_id);
        }*/
        if (points == null) {
            Log.e("Draw Line", "got null as parameters");
            return;
        }

        Polyline line = googleMap.addPolyline(new PolylineOptions().width(3).color(Color.RED));

        line.setPoints(route_array);
    }

    private void getWayPoints(String id){
        RetrofitClientInstance.setRetrofit();

        CompleteShipment apiService = RetrofitClientInstance.getClient(getActivity()).create(CompleteShipment.class);
        disposable_map.add(
                apiService.getWayPoints(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<WayInfo>>() {
                            @Override
                            public void onSuccess(List<WayInfo> wayInfo) {
                                if(wayInfo.size()>0){
                                   // createMapMarker(wayInfo);
                                    drawLine(wayInfo);
                                }
                            }
                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
    }
}
