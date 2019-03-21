package com.alanmbennett.petcare;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.content.Context.LOCATION_SERVICE;

public class LocationTracker {
    private LocationManager locManager;
    private LocationListener locListener;
    private Activity activity;
    private double latitude;
    private double longitude;

    public LocationTracker(Activity activity) {
        this.activity = activity;
        locManager = (LocationManager) this.activity.getSystemService(LOCATION_SERVICE);

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this.activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            if(locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Location lastKnown = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                latitude = lastKnown.getLatitude();
                longitude = lastKnown.getLongitude();
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, locListener);
            }
            else {
                Location lastKnown = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = lastKnown.getLatitude();
                longitude = lastKnown.getLongitude();
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locListener);
            }
        }
    }

    public LocationManager getLocManager() {
        return locManager;
    }

    public LocationListener getLocListener() {
        return locListener;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
