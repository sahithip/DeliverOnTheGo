package com.finalproject.deliveronthego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Anita on 3/22/15.
 */
public class DriverHomePage extends Activity{
    String tag = "DriverHomePage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_first_page);

        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(context);
        Criteria crta = new Criteria();
        crta.setAccuracy(Criteria.ACCURACY_FINE);
        crta.setAltitudeRequired(false);
        crta.setBearingRequired(false);
        crta.setCostAllowed(true);
        crta.setPowerRequirement(Criteria.POWER_LOW);
        // String provider = LocationManager.GPS_PROVIDER;
        String provider = locationManager.getBestProvider(crta, true);

        Location location = locationManager.getLastKnownLocation(provider);
        double lat=location.getLatitude();
        double lon=location.getLongitude();
        Log.i(tag, "lat=" + lat);
        Log.i(tag, "lon=" + lon);
    }

    public void trackDriverLocation(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
