package com.finalproject.deliveronthego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anita on 3/22/15.
 */
public class DriverHomePage extends Activity{
    String tag = "DriverHomePage";
    EditText startLocation;
    EditText endLocation;
    Button getRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_first_page);
        startLocation = (EditText)findViewById(R.id.startLocation);
        endLocation = (EditText)findViewById(R.id.endLocation);
        getRequests = (Button)findViewById(R.id.getRequests);
        getRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder gc = new Geocoder(getBaseContext(),
                        Locale.getDefault());
                try {
                    final String email = getIntent().getStringExtra("emailid");

                    List<Address> add =  gc.getFromLocationName(startLocation.getText().toString(),1);
                    Address adr = add.get(0);
                    double dropOffLat = adr.getLatitude();
                    double dropOffLong = adr.getLongitude();
                    add =  gc.getFromLocationName(endLocation.getText().toString(),1);
                    adr = add.get(0);
                    double pickUpLat = adr.getLatitude();
                    double pickUpLong = adr.getLongitude();
                    new MyAsyncDriverTask(getBaseContext()).execute(email,Double.toString(pickUpLat),Double.toString(pickUpLong),Double.toString(dropOffLat),Double.toString(dropOffLong));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
