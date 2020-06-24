package com.development.scmxpert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.development.scmxpert.R;
import com.development.scmxpert.model.UpdateEventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UpdateEventAdapter  extends RecyclerView.Adapter<UpdateEventAdapter.MyViewHolder> {
    private List<UpdateEventModel> eventList;
    public ImageView update;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_id,event,event_status,partner,date;


        public MyViewHolder(View view){
            super(view);
            event_id = (TextView)view.findViewById(R.id.update_event_id);
            event= (TextView)view.findViewById(R.id.update_event);
            event_status = (TextView)view.findViewById(R.id.update_status);
            partner = (TextView)view.findViewById(R.id.update_partner);
            date = (TextView)view.findViewById(R.id.update_date);
            update = (ImageView) view.findViewById(R.id.update);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_event_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UpdateEventModel updateEvent = eventList.get(position);
        holder.event_id.setText(updateEvent.getEvent_id());
        holder.event.setText(updateEvent.getEvent());
        holder. event_status.setText(updateEvent.getEvent_status());
        holder. partner.setText(updateEvent.getPartner());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

      /*  if(updateEvent.getDate() !=null)
            holder.date.setText(updateEvent.getDate());
        else{
            holder.date.setText("");
        }*/

        try {
            if(updateEvent.getDate() !=null)
            holder.date.setText(sdf1.format(sdf.parse(updateEvent.getDate())));
            else{
                holder.date.setText("");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(updateEvent.getEvent_status()!=null){
            if(updateEvent.getEvent_status().equals(context.getString(R.string.completed))){
                update.setVisibility(View.INVISIBLE);
            }else{
                update.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }



    public  UpdateEventAdapter(Context context, List<UpdateEventModel> eventList){
        this.context = context;
        this.eventList = eventList;
    }
}
