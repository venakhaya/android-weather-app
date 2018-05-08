package com.vena.wather.events;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.vena.wather.application.WeatherApplication;
import com.vena.wather.model.Coordinates;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


public abstract class LocationEvent implements LocationListener {

    Coordinates coordinates;
    List<Address> addresses;
    @Inject
    public Context context;

    public LocationEvent() {
        WeatherApplication.getAppComponent().inject(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            coordinates = new Coordinates(location.getLatitude(), location.getLongitude());
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(coordinates.getLatitude(), coordinates.getLongitude(), 1);

            } catch (Exception e) {

            }

            onLocationCoordinate(coordinates);
            if (addresses != null && addresses.size() > 0) {
                onLocationAddress(addresses.get(0));
            }
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

    public abstract void onLocationAddress(Address address);

}

