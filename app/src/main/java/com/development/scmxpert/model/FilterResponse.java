package com.development.scmxpert.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FilterResponse implements Parcelable {
    @SerializedName("shipment_Id")
    private String shipment_id;


    protected FilterResponse(Parcel in) {
        shipment_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shipment_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilterResponse> CREATOR = new Creator<FilterResponse>() {
        @Override
        public FilterResponse createFromParcel(Parcel in) {
            return new FilterResponse(in);
        }

        @Override
        public FilterResponse[] newArray(int size) {
            return new FilterResponse[size];
        }
    };

    public String getShipment_id() {
        return shipment_id;
    }

    public void setShipment_id(String shipment_id) {
        this.shipment_id = shipment_id;
    }

    public static Creator<FilterResponse> getCREATOR() {
        return CREATOR;
    }
}
