package com.example.scmxpert.model;

import com.example.scmxpert.map.Map;

public class MapModel {
    private String latitude;
    private String longitude;
    private String id;

  /*  public MapModel(String latitude,String longitude,String id){
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }*/

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
