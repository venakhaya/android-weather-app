package com.vena.wather.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;

import com.vena.wather.application.WeatherApplication;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static boolean hasInternetConnection(Activity context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static boolean hasInternetPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static String getDate() {
        Format formatter = new SimpleDateFormat("dd-MMMM-yyyy");
        String s = formatter.format(new Date());
        return s;
    }
}

