package com.vena.wather.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vena.wather.R;
import com.vena.wather.events.ResultsReceiverEvent;
import com.vena.wather.handler.WeatherJobScheduler;
import com.vena.wather.model.Coordinates;
import com.vena.wather.network.WeatherResponse;
import com.vena.wather.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements LocationListener {
    private PersistableBundle persistableBundle;
    static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private Coordinates coordinates;
    private WeatherResponse weatherResponse;
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
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationListener = this;
        getLocation();
    }

    private void getLocation() {

        persistableBundle = new PersistableBundle();
        coordinates = new Coordinates(-33.8894603, 18.4781874);
        persistableBundle.putDouble("lat", coordinates.getLatitude());
        persistableBundle.putDouble("lon", coordinates.getLongitude());
        jobHandler.execute(new WeatherJobScheduler(weatherResultsReceiverEventListener, persistableBundle));
//        locationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);
//        if (hasLocationPermission()) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        }
    }


    private ResultsReceiverEvent weatherResultsReceiverEventListener = new ResultsReceiverEvent<WeatherResponse>() {
        @Override
        public void onSuccess(WeatherResponse results) {
            weatherResponse = results;
            dateTextView.setText("TODAY," + Util.getDate());
            maxTextView.setText(String.valueOf(weatherResponse.getMain().getMaxTemp()) + (char) 0x00B0 + "C");
            minTextView.setText(String.valueOf(weatherResponse.getMain().getMinTemp()) + (char) 0x00B0 + "C");
            String iconUrl = getString(R.string.image_host) + weatherResponse.getWeatherList().get(0).getIcon() + ".png";
            Picasso.get()
                    .load(iconUrl)
                    .resize(150, 150)
                    .centerInside()
                    .into(weatherImageView);
            locationTextView.setText(weatherResponse.getName() + "," + weatherResponse.getSys().getCountry());
        }

        public Bitmap StringToBitMap(String encodedString) {
            try {
                byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }

        @Override
        public void onFailed(String message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
//            persistableBundle = new PersistableBundle();
//            persistableBundle.putDouble("lat", location.getLatitude());
//            persistableBundle.putDouble("lon", location.getLongitude());
//            jobHandler.execute(new WeatherJobScheduler(weatherResultsReceiverEventListener, persistableBundle));
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    private boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
