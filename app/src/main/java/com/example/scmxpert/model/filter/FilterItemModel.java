package com.example.scmxpert.model.filter;

import android.os.Parcel;
import android.os.Parcelable;

public class FilterItemModel  implements Parcelable {
    String from;
    String to;
    String goods;
    String device;
    String ship_date;
    String dep_type;
    String reference;
    String delivery_number;

    public static Creator<FilterItemModel> getCREATOR() {
        return CREATOR;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getShip_date() {
        return ship_date;
    }

    public void setShip_date(String ship_date) {
        this.ship_date = ship_date;
    }

    public String getDep_type() {
        return dep_type;
    }

    public void setDep_type(String dep_type) {
        this.dep_type = dep_type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(String delivery_number) {
        this.delivery_number = delivery_number;
    }

    public FilterItemModel(Parcel in) {
        from = in.readString();
        to = in.readString();
        goods = in.readString();
        device = in.readString();
        ship_date = in.readString();
        dep_type = in.readString();
        reference = in.readString();
        delivery_number = in.readString();
    }

    public FilterItemModel(String from,String to,String goods,String device,
                           String ship_date,String dep_type,String reference,String delivery_number){
        this.from = from;
        this.to = to;
        this.goods = goods;
        this.device = device;
        this.ship_date = ship_date;
        this.dep_type = dep_type;
        this.reference = reference;
        this.delivery_number = delivery_number;
    }

    public static final Creator<FilterItemModel> CREATOR = new Creator<FilterItemModel>() {
        @Override
        public FilterItemModel createFromParcel(Parcel in) {
            return new FilterItemModel(in);
        }

        @Override
        public FilterItemModel[] newArray(int size) {
            return new FilterItemModel[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeString(goods);
        parcel.writeString(device);
        parcel.writeString(ship_date);
        parcel.writeString(dep_type);
        parcel.writeString(reference);
        parcel.writeString(delivery_number);
    }
}
