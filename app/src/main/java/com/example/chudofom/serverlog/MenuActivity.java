package com.example.chudofom.serverlog;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.chudofom.serverlog.databinding.ActivityMenuBinding;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import rx.Subscription;

public class MenuActivity extends AppCompatActivity implements LocationListener {
    ActivityMenuBinding binding;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        MyLocationListener locationListener = new MyLocationListener(this, this);
        createToolbar();
        binding.setSwitchCheck(false);
        subscription = RxCompoundButton.checkedChanges(binding.gpsSwitch)
                .subscribe(b -> {
                    if (b) {
                        binding.setGpsStatus("Соединение...");
                        locationListener.connect();
                        binding.setGpsStatus("Маяк работает");
                    } else {
                        locationListener.destroyConnection();
                        binding.setGpsStatus("Включить маяк");
                    }
                });
        binding.userLine.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, PersonalAreaActivity.class);
            startActivity(intent);
        });
    }

    private void createToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("Меню");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitleTextColor(Color.WHITE);
    }

    public void showLocation(Location location) {
        String locationOutput = String.format("Latitude= %15f Longitude= %15f",
                location.getLatitude(), location.getLongitude());
        binding.setGpsStatus(locationOutput);
    }

    @Override
    protected void onDestroy() {
        if (subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}