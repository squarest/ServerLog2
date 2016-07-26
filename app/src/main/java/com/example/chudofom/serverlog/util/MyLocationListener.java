package com.example.chudofom.serverlog.util;

/**
 * Created by Chudofom on 12.07.16.
 */

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

public class MyLocationListener {

    private LocationManager locationManager;
    private LocationListener locationListener;

    public MyLocationListener(Context context, LocationListener locListener) {
        this.locationListener = locListener;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void connect() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListener);
        } catch (SecurityException ex) {
            Log.d("TAG", "Location checking failed");
        }

    }

    public void destroyConnection() {
        try {
            if (locationManager != null)
                locationManager.removeUpdates(locationListener);
        } catch (SecurityException ex) {
            Log.d("TAG", "SecurityExeptition");
        }
    }

}