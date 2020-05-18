package com.example.scmxpert.views;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.scmxpert.R;
import com.example.scmxpert.adapter.ShipmentAdapter;
import com.example.scmxpert.apiInterface.CompleteShipment;
import com.example.scmxpert.base.BaseActivity;
import com.example.scmxpert.map.Map;
import com.example.scmxpert.model.Shippment;
import com.example.scmxpert.viewClick.RecyclerTouchListener;
import com.example.scmxpert.views.createShipment.CreateShipment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeScreen extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ShipmentAdapter mAdapter;
    ImageView create_shipment,filter;
    private ArrayList<Shippment> data = new ArrayList<>();
    private ArrayList<Object> waveList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_home_screen);
         recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
         create_shipment = (ImageView)findViewById(R.id.create_shipment);
         filter = (ImageView)findViewById(R.id.filter);
         filter.setOnClickListener(this);
         create_shipment.setOnClickListener(this);
         FloatingActionButton fab = findViewById(R.id.fab);
         fab.setImageResource(R.drawable.map_show);
         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<data.size();i++){
                   Shippment shippment =   data.get(i);
                   waveList.add(shippment.getWaypoint());
                }
                Bundle b = new Bundle();
                b.putSerializable("wave_point", (Serializable) waveList);
                Intent intent = new Intent(HomeScreen.this,Map.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        getAllShipment();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Shippment shippment =  data.get(position);
                Intent intent = new Intent(HomeScreen.this,ShipmentDetails.class);
                intent.putExtra("shippment",shippment);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }
    private void getAllShipment() {
         showProgressDialog(getResources().getString(R.string.loading));

         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CompleteShipment.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CompleteShipment api = retrofit.create(CompleteShipment.class);
        Call<List<Shippment>> call = api.getShipment();

        call.enqueue(new Callback<List<Shippment>>() {
            @Override
            public void onResponse(Call<List<Shippment>> call, retrofit2.Response<List<Shippment>> response) {
                try {
                    hideProgressDialog();
                    data = (ArrayList<Shippment>) response.body();
                    mAdapter = new ShipmentAdapter(data);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(HomeScreen.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(mAdapter);

                } catch (Exception e) {
                      hideProgressDialog();
                      e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Shippment>> call, Throwable t) {
                 hideProgressDialog();
                 Log.e("SCMXPERT",t.getMessage());
        }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_shipment:
                startActivity(new Intent(HomeScreen.this, CreateShipment.class));
                break;

            case R.id.filter:
                startActivity(new Intent(HomeScreen.this,FilterScreen.class));
                break;
        }
    }
}


