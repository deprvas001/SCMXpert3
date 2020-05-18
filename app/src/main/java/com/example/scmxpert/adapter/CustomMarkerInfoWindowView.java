package com.example.scmxpert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.scmxpert.R;
import com.example.scmxpert.model.WayInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import java.util.List;

public class CustomMarkerInfoWindowView implements GoogleMap.InfoWindowAdapter {
    private Context context;
    public CustomMarkerInfoWindowView(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.marker_info_window, null);
        WayInfo wayInfo =(WayInfo) marker.getTag();
      //  LatLng latLng = arg0.getPosition();
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView latlong = (TextView)v.findViewById(R.id.lat_long);
        TextView address = (TextView)v.findViewById(R.id.address);
        TextView device = (TextView)v.findViewById(R.id.device);
        TextView shipment_number = (TextView)v.findViewById(R.id.shipment_number);
       /* TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);*/
        date.setText(wayInfo.getSensorUTC());
        latlong.setText(wayInfo.getWayPoints());
        address.setText(wayInfo.getAddress());
        device.setText(wayInfo.getDevice_Id());
        shipment_number.setText(wayInfo.getShipment_Num());

       // tvLng.setText("Longitude:"+ latLng.longitude);
        return v;
    }
}
