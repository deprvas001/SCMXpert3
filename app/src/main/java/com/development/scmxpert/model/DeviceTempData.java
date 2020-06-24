package com.development.scmxpert.model;

import com.google.gson.annotations.SerializedName;

public class DeviceTempData {
    @SerializedName("internal_Temperature")
    private String internal_temp;
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("utc")
    private String date;

    public String getInternal_temp() {
        return internal_temp;
    }

    public void setInternal_temp(String internal_temp) {
        this.internal_temp = internal_temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
