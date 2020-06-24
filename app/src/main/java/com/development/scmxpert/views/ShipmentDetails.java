package com.development.scmxpert.views;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.development.scmxpert.R;
import com.development.scmxpert.base.BaseActivity;
import com.development.scmxpert.fragment.FragmentOne;
import com.development.scmxpert.fragment.GraphFragment;
import com.development.scmxpert.fragment.MapFragment;
import com.development.scmxpert.model.Shippment;
import com.development.scmxpert.viewClick.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ShipmentDetails extends BaseActivity implements View.OnClickListener , TabLayout.OnTabSelectedListener{
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    public Shippment shippment;
    private int[] tabIcons = {
            R.drawable.info_icon,
            R.drawable.info_icon,
            R.drawable.info_icon
    };
    Object object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setEnableSwipe(false);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        setupTabIcons();
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Info");
        tabOne.setTextColor(getResources().getColor(R.color.view_background));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info_icon, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Map");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.map_icon_resize,0 , 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Live Chart");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.live_chart_resize,0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne(), "Info");
        adapter.addFragment(new MapFragment(), "Map");
        adapter.addFragment(new GraphFragment(), "Live Chart");
        viewPager.setAdapter(adapter);
   }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition()==0){
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.tab);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info_icon,0, 0, 0);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentDetails.this, R.color.view_background));
        }else if(tab.getPosition() == 1){
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.tab);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.map_red_icon,0, 0, 0);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentDetails.this, R.color.view_background));
        }else if(tab.getPosition() == 2){
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.tab);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.chart_red_icon,0, 0, 0);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentDetails.this, R.color.view_background));
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if(tab.getPosition()==0){
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.tab);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info_grey_icon,0, 0, 0);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentDetails.this, R.color.shipment_detail));
        }else if(tab.getPosition() == 1){
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.tab);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.map_icon_resize,0, 0, 0);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentDetails.this, R.color.shipment_detail));
        }else if(tab.getPosition() ==2){
            View view = tab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.tab);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.live_chart_resize,0, 0, 0);
            selectedText.setTextColor(ContextCompat.getColor(ShipmentDetails.this, R.color.shipment_detail));
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                //checkLoginCredentials();
                break;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //  alertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
