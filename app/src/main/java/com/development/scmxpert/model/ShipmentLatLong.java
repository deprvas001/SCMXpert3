package com.development.scmxpert.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ShipmentLatLong implements ClusterItem {

    private final String username;
    private final LatLng latLng;

    public ShipmentLatLong(String username, LatLng latLng) {
        this.username = username;
        this.latLng = latLng;

    }

        @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public String getTitle() {
        return username;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}
