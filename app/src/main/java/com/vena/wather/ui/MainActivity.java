package com.vena.wather.ui;

import android.app.Activity;
import android.location.Address;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vena.wather.R;
import com.vena.wather.events.LocationEvent;
import com.vena.wather.events.ResultsReceiverEvent;
import com.vena.wather.handler.WeatherJobScheduler;
import com.vena.wather.model.Coordinates;
import com.vena.wather.network.WeatherResponse;
import com.vena.wather.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final long MINUTES = 60000;
    private static final long DISTANCE = 100;

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
    private Address address;
    private boolean isNetworkCallBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        activity = this;
        getLocationDetails();
    }

    private ResultsReceiverEvent weatherResultsReceiverEventListener = new ResultsReceiverEvent<WeatherResponse>() {
        @Override
        public void onSuccess(final WeatherResponse results) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateUI(results);
                    isNetworkCallBusy = !isNetworkCallBusy;
                }
            });

        }

        @Override
        public void onFailed(String message) {
            isNetworkCallBusy = !isNetworkCallBusy;
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    };

    private void getLocationDetails() {
        if (Util.hasInternetConnection(this)) {
            isNetworkCallBusy = !isNetworkCallBusy;
            if (!isNetworkCallBusy) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINUTES, DISTANCE, locationListener);
            }
        } else {
            Toast.makeText(context, getString(R.string.no_connection), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocationDetails();
    }

    private void executeApi(Coordinates coordinates) {
        persistableBundle = new PersistableBundle();
        persistableBundle.putDouble("lat", coordinates.getLatitude());
        persistableBundle.putDouble("lon", coordinates.getLongitude());
        jobHandler.execute(new WeatherJobScheduler(weatherResultsReceiverEventListener, persistableBundle));
    }

    private LocationEvent locationListener = new LocationEvent() {

        @Override
        public void onLocationCoordinate(final Coordinates coordinates) {
            executeApi(coordinates);
        }

        @Override
        public void onLocationAddress(final Address locationAddress) {
            address = locationAddress;
        }
    };

    private void updateUI(final WeatherResponse weatherResponse) {
        dateTextView.setText(getString(R.string.today) + " " + Util.getDate());
        maxTextView.setText(getString(R.string.max) + " " + String.valueOf(weatherResponse.getMain().getMaxTemp()) + (char) 0x00B0 + "C");
        minTextView.setText(getString(R.string.min) + " " + String.valueOf(weatherResponse.getMain().getMinTemp()) + (char) 0x00B0 + "C");
        String iconUrl = getString(R.string.image_host) + weatherResponse.getWeatherList().get(0).getIcon() + ".png";
        Picasso.get()
                .load(iconUrl)
                .resize(150, 150)
                .centerInside()
                .into(weatherImageView);
        if (address != null) {
            locationTextView.setText(address.getLocality() + ", " + address.getCountryName());
        } else {
            //API response not accurate
            locationTextView.setText(weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry());
        }
        progressBar.setVisibility(View.GONE);
    }
}
