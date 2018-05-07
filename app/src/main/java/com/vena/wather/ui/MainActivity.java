package com.vena.wather.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vena.wather.R;
import com.vena.wather.events.ResultsReceiverEvent;
import com.vena.wather.handler.WeatherJobScheduler;
import com.vena.wather.model.Coordinates;
import com.vena.wather.network.WeatherResponse;
import com.vena.wather.utils.Util;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final long MINUTES = 1000;
    private static final long DISTANCE = 10000;
    private static final int REQUEST_LOCATION = 1;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.date_text_view)
    TextView dateTextView;
    @BindView(R.id.weather_image_view)
    ImageView weatherImageView;
    @BindView(R.id.max_text_view)
    TextView maxTextView;
    @BindView(R.id.min_text_view)
    TextView minTextView;
    @BindView(R.id.location_text_view)
    TextView locationTextView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;

    private Activity activity;
    private PersistableBundle persistableBundle;
    private LocationManager locationManager;
    private WeatherResponse weatherResponse;
    private List<Address> addresses;
    private Coordinates coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = this;
        if (Util.hasInternetConnection(this)) {
            if (coordinates != null) {
                executeApi(coordinates);
            } else {
                getLocation();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.hasInternetConnection(this)) {
            getLocation();
        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void executeApi(Coordinates coordinates) {
        persistableBundle = new PersistableBundle();
        persistableBundle.putDouble("lat", coordinates.getLatitude());
        persistableBundle.putDouble("lon", coordinates.getLongitude());
        jobHandler.execute(new WeatherJobScheduler(weatherResultsReceiverEventListener, persistableBundle));
    }

    private void getLocation() {

        locationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);
        if (hasLocationPermission()) {
            requestLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    private LocationListener locationListener = new LocationListener() {


        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {
                Geocoder geocoder;
                coordinates = new Coordinates(location.getLatitude(), location.getLongitude());
                geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(coordinates.getLatitude(), coordinates.getLongitude(), 1);
                } catch (Exception e) {

                }
                executeApi(coordinates);
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
    };

    private ResultsReceiverEvent weatherResultsReceiverEventListener = new ResultsReceiverEvent<WeatherResponse>() {
        @Override
        public void onSuccess(final WeatherResponse results) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateUI(results);
                }
            });

        }


        @Override
        public void onFailed(String message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    };

    private void updateUI(WeatherResponse results) {
        weatherResponse = results;
        dateTextView.setText(getString(R.string.today) + " " + Util.getDate());
        maxTextView.setText(String.valueOf(weatherResponse.getMain().getMaxTemp()) + (char) 0x00B0 + "C");
        minTextView.setText(String.valueOf(weatherResponse.getMain().getMinTemp()) + (char) 0x00B0 + "C");
        String iconUrl = getString(R.string.image_host) + weatherResponse.getWeatherList().get(0).getIcon() + ".png";
        Picasso.get()
                .load(iconUrl)
                .resize(150, 150)
                .centerInside()
                .into(weatherImageView);
        if (addresses != null && addresses.size() > 0) {
            locationTextView.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName());
        } else {
            //API response not accurate
            locationTextView.setText(weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry());
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (hasLocationPermission()) {
                    requestLocation();
                } else {
                    return;
                }
                break;
            case 2: {
                if (Util.hasInternetPermission(this) && (Util.hasInternetConnection(this))) {
                    if (hasLocationPermission()) {
                        requestLocation();
                    } else {
                        return;
                    }

                } else {
                    return;
                }

            }
        }

    }

    private void requestLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINUTES, DISTANCE, locationListener);

    }

    private boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
