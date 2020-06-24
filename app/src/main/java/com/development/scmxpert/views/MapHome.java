package com.development.scmxpert.views;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.development.scmxpert.fragment.LiveMapShipment;
import com.development.scmxpert.fragment.MapShipment;
import com.development.scmxpert.views.createShipment.CreateShipment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MapHome extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView create_shipment,filter,back;
    private FloatingActionButton fab;
    public TextView live_count,deliver_count;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_home);
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
        adapter.addFragment(new LiveMapShipment(), "Live");
        adapter.addFragment(new MapShipment(), "Delivered");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_shipment:
                if (isNetworkAvailable(this)) {
                    startActivity(new Intent(this, CreateShipment.class));
                } else {
                    showAlertDialog(this, getString(R.string.no_connection));
                }

                break;

            case R.id.filter:
                if (isNetworkAvailable(this)) {
                    Intent intent = new Intent(this,FilterScreen.class);
                    intent.putExtra("tag","2");
                    startActivity(intent);
                } else {
                    showAlertDialog(this, getString(R.string.no_connection));
                }

                break;

            case R.id.fab:
                finish();
                break;

            case R.id.back:
                finish();
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
            selectedText.setTextColor(ContextCompat.getColor(this, R.color.view_background));
        }else if(tab.getPosition()==1) {
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.deliver_title);
            View current_state = (View)view.findViewById(R.id.view);
            current_state.setVisibility(View.VISIBLE);
            selectedText.setTextColor(ContextCompat.getColor(this, R.color.view_background));
        }


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if(tab.getPosition()==0){
            View view = tab.getCustomView();
            View current_state = (View)view.findViewById(R.id.view1);
            current_state.setVisibility(View.INVISIBLE);
            TextView selectedText = (TextView) view.findViewById(R.id.tab_selected_title);
            selectedText.setTextColor(ContextCompat.getColor(this, R.color.shipment_detail));
        }else if(tab.getPosition()==1) {
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.deliver_title);
            View current_state = (View)view.findViewById(R.id.view);
            current_state.setVisibility(View.INVISIBLE);
            selectedText.setTextColor(ContextCompat.getColor(this, R.color.shipment_detail));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView(){
      /*  toolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        setSupportActionBar(toolbar);*/
        back = (ImageView)findViewById(R.id.back);
        create_shipment = (ImageView)findViewById(R.id.create_shipment);
        fab =  findViewById(R.id.fab);
        fab.setImageResource(R.drawable.home_icon);
        fab.setOnClickListener(this);
        filter = (ImageView)findViewById(R.id.filter);
        filter.setOnClickListener(this);
        create_shipment.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        back.setOnClickListener(this);
    }


}
