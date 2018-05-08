package com.vena.wather.events;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.vena.wather.model.Coordinates;


public abstract class LocationEvent implements LocationListener {

    Coordinates coordinates;

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            coordinates = new Coordinates(location.getLatitude(), location.getLongitude());
            onLocationCoordinate(coordinates);
        }

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

    public abstract void onLocationCoordinate(Coordinates coordinates);

}

