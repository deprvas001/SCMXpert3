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
import com.development.scmxpert.model.Event_Details;

import java.text.SimpleDateFormat;
import java.util.List;

import android.widget.RelativeLayout;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private List<Event_Details> eventList;
    public ImageView update;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_id,event,partner_name,partner_id;
        public RelativeLayout viewBackground, viewForeground;


        public MyViewHolder(View view){
            super(view);
            event_id = (TextView)view.findViewById(R.id.event_id);
            event= (TextView)view.findViewById(R.id.event_name);
            partner_name = (TextView)view.findViewById(R.id.partner_name);
            partner_id = (TextView)view.findViewById(R.id.partner_id);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

        }
    }

    @NonNull
    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_view,parent,false);
        return new EventsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.MyViewHolder holder, int position) {
        Event_Details updateEvent = eventList.get(position);
        String updateId  =updateEvent.getEvent_Id();
        holder.event_id.setText(updateId);
        holder.event.setText(updateEvent.getEvent_name());
        holder.partner_name.setText(updateEvent.getBp_name());
        holder.partner_id.setText(updateEvent.getBp_id());
        /*holder.event_id.setText(updateEvent.getEvent_id());
        holder.event.setText(updateEvent.getEvent());
        holder. event_status.setText(updateEvent.getEvent_status());
        holder. partner.setText(updateEvent.getPartner());*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        /*try {
            if(updateEvent.getDate() !=null)
                holder.date.setText(sdf1.format(sdf.parse(updateEvent.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(updateEvent.getEvent_status()!=null){
            if(updateEvent.getEvent_status().equals(context.getString(R.string.completed))){
                update.setVisibility(View.INVISIBLE);
            }else{
                update.setVisibility(View.VISIBLE);
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }



    public  EventsAdapter(Context context, List<Event_Details> eventList){
        this.context = context;
        this.eventList = eventList;
    }

    public void removeItem(int position) {
        eventList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Event_Details item, int position) {
        eventList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
