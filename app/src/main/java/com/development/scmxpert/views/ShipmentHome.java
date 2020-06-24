package com.development.scmxpert.views;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.development.scmxpert.R;
import com.development.scmxpert.base.BaseActivity;
import com.development.scmxpert.fragment.DeliveredShipment;
import com.development.scmxpert.fragment.LiveShipment;
import com.development.scmxpert.helper.SessionManager;
import com.development.scmxpert.service.RetrofitClientInstance;
import com.development.scmxpert.views.createShipment.CreateShipment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipmentHome extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView create_shipment,filter,logout;
    Toolbar toolbar;

    public TextView live_count,deliver_count;
    SessionManager session;
       Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_home);
        session = new SessionManager(getApplicationContext());
/*
        if(getIntent().getExtras() != null){
            intent = getIntent();
            Toast.makeText(this, "Value", Toast.LENGTH_SHORT).show();
        }*/


        initializeView();
        setupTabIcons();
    }

    private void setupTabIcons() {
        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.main_screen_tab, null);
        live_count = tabOne.findViewById(R.id.count_live);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.main_screen_tab2, null);
        deliver_count = tabTwo.findViewById(R.id.count_deliver);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LiveShipment(), "Live");
        adapter.addFragment(new DeliveredShipment(), "Delivered");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_shipment:
                if (isNetworkAvailable(this)) {
                    startActivity(new Intent(ShipmentHome.this, CreateShipment.class));
                } else {
                    showAlertDialog(this, getString(R.string.no_connection));
                }

                break;

            case R.id.filter:

                if (isNetworkAvailable(this)) {
                    startActivity(new Intent(ShipmentHome.this,FilterScreen.class));
                } else {
                    showAlertDialog(this, getString(R.string.no_connection));
                }

                break;

            case R.id.logout:
               showAlertDialog();
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
       if(tab.getPosition()==0){
           View view = tab.getCustomView();
           TextView selectedText = (TextView) view.findViewById(R.id.tab_selected_title);
           View current_state = (View)view.findViewById(R.id.view1);
           current_state.setVisibility(View.VISIBLE);
           selectedText.setTextColor(ContextCompat.getColor(ShipmentHome.this, R.color.view_background));
       }else if(tab.getPosition()==1) {
           View view = tab.getCustomView();
           TextView selectedText = (TextView) view.findViewById(R.id.deliver_title);
           View current_state = (View)view.findViewById(R.id.view);
           current_state.setVisibility(View.VISIBLE);
           selectedText.setTextColor(ContextCompat.getColor(ShipmentHome.this, R.color.view_background));
       }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if(tab.getPosition()==0){
            View view = tab.getCustomView();
            View current_state = (View)view.findViewById(R.id.view1);
            current_state.setVisibility(View.INVISIBLE);
            TextView selectedText = (TextView) view.findViewById(R.id.tab_selected_title);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentHome.this, R.color.shipment_detail));
        }else if(tab.getPosition()==1) {
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.deliver_title);
            View current_state = (View)view.findViewById(R.id.view);
            current_state.setVisibility(View.INVISIBLE);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentHome.this, R.color.shipment_detail));
        }


    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void initializeView(){
        create_shipment = (ImageView)findViewById(R.id.create_shipment);
        /*toolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.logout));*/

        filter = (ImageView)findViewById(R.id.filter);
        logout = (ImageView)findViewById(R.id.logout) ;
        filter.setOnClickListener(this);
        logout.setOnClickListener(this);
        create_shipment.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAlertDialog() {
        HashMap<String,String> user = session.getUserDetails();
        String user_name = user.get(SessionManager.USER_NAME);
        String admin_name = user.get(SessionManager.PARTNER_NAME);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(user_name)
                .setMessage(getString(R.string.logout))
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    builder.create().dismiss();
                    session.logoutUser();
                    RetrofitClientInstance.setRetrofit();

                })
                .setNegativeButton("No",(dialog, which) -> {
                            builder.create().dismiss();
                        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
        finishAffinity();
    }
}
