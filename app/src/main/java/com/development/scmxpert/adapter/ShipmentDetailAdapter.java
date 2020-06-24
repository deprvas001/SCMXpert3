package com.development.scmxpert.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.scmxpert.R;
import com.development.scmxpert.model.ShipmentDetail;

import java.util.List;

public class ShipmentDetailAdapter extends RecyclerView.Adapter<ShipmentDetailAdapter.MyViewHolder> {
    private List<ShipmentDetail> detail_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ship_id,device_id,temp,event_name,battery,report_type,mode_of;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            ship_id = (TextView)view.findViewById(R.id.shippment_id);
            device_id = (TextView)view.findViewById(R.id.device_id);
            temp = (TextView)view.findViewById(R.id.internal_temp);
            event_name = (TextView)view.findViewById(R.id.event_name);
            battery = (TextView)view.findViewById(R.id.battery);
            report_type = (TextView)view.findViewById(R.id.report_type);
            mode_of = (TextView)view.findViewById(R.id.mode_of);
        }
    }


    public ShipmentDetailAdapter(List<ShipmentDetail> detail_list) {
        this.detail_list = detail_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_detail_id, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShipmentDetail detail = detail_list.get(position);
        if(detail.getId()!=null){
            String id  ="<b>" + detail.getId()+ "</b> ";
            holder.ship_id.setText((Html.fromHtml(id)));
        }else{
            holder.ship_id.setText("NA");
        }

        if(detail.getDevice_id()!=null){
            String device_id ="<b>" + detail.getDevice_id()+ "</b> ";
            holder.device_id.setText("Device ID: "+(Html.fromHtml(device_id)));
        }else{
            holder.device_id.setText("Device ID: NA");
        }

        if(detail.getMode_of()!=null){
            String mode_of ="<b>" + detail.getMode_of()+ "</b> ";
            holder.mode_of.setText("Mode of: "+(Html.fromHtml(mode_of)));
        }else{
            holder.mode_of.setText("Mode of: NA");
        }

        if(detail.getReport_type()!=null){
            String report_type ="<b>" + detail.getReport_type()+ "</b> ";
            holder.report_type.setText("Report type: "+(Html.fromHtml(report_type)));
        }else{
            holder.report_type.setText("Report type: NA");
        }

        if(detail.getBattery()!=null){
            String battery ="<b>" + detail.getBattery()+ "</b> ";
            holder.battery.setText("Battery: "+(Html.fromHtml(battery)));
        }else{
            holder.battery.setText("Battery: NA");
        }

        if(detail.getInternal_tmp()!=null){
            String temp ="<b>" + detail.getInternal_tmp()+ "</b> ";
            holder.temp.setText("Internal temp: "+(Html.fromHtml(temp)+"C"));
        }else{
            holder.temp.setText("Internal temp: NA");
        }

        if(detail.getEvent_name()!=null){
            String event_name ="<b>" + detail.getEvent_name()+ "</b> ";
            holder.event_name.setText("Event name: "+(Html.fromHtml(event_name)));
        }else{
            holder.event_name.setText("Event name: NA");
        }
    }

    @Override
    public int getItemCount() {
        return detail_list.size();
    }

}
