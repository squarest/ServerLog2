package com.example.chudofom.serverlog.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.chudofom.serverlog.DB.UserRepository;
import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityMenuBinding;
import com.example.chudofom.serverlog.util.MyLocationListener;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import rx.Subscription;

public class MenuActivity extends BaseActivity implements LocationListener {
    private ActivityMenuBinding binding;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(this);
        setListeners();
    }

    private void setListeners() {
        MyLocationListener locationListener = new MyLocationListener(this, this);
        binding.setSwitchCheck(false);
        Subscription subscription = RxCompoundButton.checkedChanges(binding.gpsSwitch)
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
        addSubscription(subscription);
        binding.userLine.setOnClickListener(view -> {
            if (userRepository.getUser() != null) {
                Intent intent = new Intent(MenuActivity.this, AccountActivity.class);
                intent.putExtra(EditAccountActivity.USER_IS_FOUND, true);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MenuActivity.this, EditAccountActivity.class);
                intent.putExtra(EditAccountActivity.USER_IS_FOUND, false);
                startActivity(intent);
            }
        });
        binding.startCamera.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, CameraActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void inflateToolbar() {
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