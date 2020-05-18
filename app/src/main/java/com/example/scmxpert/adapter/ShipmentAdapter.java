package com.example.scmxpert.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.scmxpert.R;
import com.example.scmxpert.model.Shippment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.MyViewHolder>{
    private List<Shippment> shipmentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView shipment_id,from_place,deliver_place,
                status,created_date,delivery_date;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            shipment_id = (TextView)view.findViewById(R.id.shippment_id);
            from_place = (TextView)view.findViewById(R.id.from_date);
            deliver_place = (TextView)view.findViewById(R.id.to_date);
            status = (TextView)view.findViewById(R.id.status);
            created_date = (TextView)view.findViewById(R.id.create_date);
            delivery_date = (TextView)view.findViewById(R.id.deliver_date);
            progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        }
    }

    public ShipmentAdapter(List<Shippment> shipmentList) {
        this.shipmentList = shipmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shippment shippment = shipmentList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if(shippment.getCreated_date()!=null ){
                holder.created_date.setText(sdf1.format(sdf.parse(shippment.getCreated_date())));
            }else{
                holder.created_date.setText("NA");
            }

           if(!shippment.getDelivery_status().equals("Delivered")){
                if(shippment.getEst_delivery_date()!=null){
                    holder.delivery_date.setText(sdf1.format(sdf.parse(shippment.getEst_delivery_date())));
                }else{
                    holder.delivery_date.setText("NA");
                }
            }else{
                if(shippment.getDelivery_date()!=null){
                    holder.delivery_date.setText(sdf1.format(sdf.parse(shippment.getDelivery_date())));
                }else{
                    holder.delivery_date.setText("NA");
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(shippment.getShipment_id()!=null){
            holder.shipment_id.setText(shippment.getShipment_id());
        }else{
            holder.shipment_id.setText("NA");
        }
        if(shippment.getRoute_form()!=null){

            holder.from_place.setText(shippment.getRoute_form());
        }else{
            holder.from_place.setText("NA");
        }
        if(shippment.getRoute_to()!=null){
            holder.deliver_place.setText(shippment.getRoute_to());
        }else{
            holder.deliver_place.setText("NA");
        }

        if(shippment.getEvent_status()!=null){
            int val = Math.round(Float.parseFloat(shippment.getEvent_status()));
            holder.progressBar.setProgress(val);
        }else{
            holder.progressBar.setProgress(0);
        }

        if(shippment.getDelivery_status()!=null){
            if(shippment.getDelivery_status().equals("Delivered")){
                holder.progressBar.setProgress(100);
            }
            holder.status.setText("Status:"+shippment.getDelivery_status());
        }else{
            holder.status.setText("Status: N/A");
        }
    }

    @Override
    public int getItemCount() {
        return shipmentList.size();
    }
}
