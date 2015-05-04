package com.finalproject.deliveronthego;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by sahithi on 4/4/15.
 */
public class DeliveryActivity extends Activity {
    EditText pickup;
    EditText dropOff;
    EditText length;
    EditText breadth;
    EditText width;
    Button findDrivers;
    Button btnRegId;
    String projectNumber ="709164501120";
    GoogleCloudMessaging gcm;
    String regid;
    EditText etRegId;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_page);
        Intent intent  = getIntent();
        String text = intent.getStringExtra("dropOff");
        btnRegId = (Button) findViewById(R.id.btnGetRegId);
        dropOff = (EditText)findViewById(R.id.dropOffInput);
        pickup = (EditText)findViewById(R.id.pickUpInput);
        length = (EditText)findViewById(R.id.length);
        breadth = (EditText)findViewById(R.id.breadth);
        width = (EditText)findViewById(R.id.width);
        etRegId = (EditText) findViewById(R.id.etRegId);

        dropOff.setText(text);
        findDrivers = (Button)findViewById(R.id.findDrivers);
        final String email = intent.getStringExtra("emailid");

        findDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder gc = new Geocoder(getBaseContext(),
                        Locale.getDefault());
                try {
                   List<Address> add =  gc.getFromLocationName(dropOff.getText().toString(),1);
                    Address adr = add.get(0);
                    double dropOffLat = adr.getLatitude();
                    double dropOffLong = adr.getLongitude();
                    add =  gc.getFromLocationName(pickup.getText().toString(),1);
                     adr = add.get(0);
                    double pickUpLat = adr.getLatitude();
                    double pickUpLong = adr.getLongitude();
                    new MyAsyncTask(getBaseContext()).execute(email,Double.toString(pickUpLat),Double.toString(pickUpLong),Double.toString(dropOffLat),Double.toString(dropOffLong),length.getText().toString(),breadth.getText().toString(),width.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btnRegId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegId();
            }
        });

    }
    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(projectNumber);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                etRegId.setText(msg + "\n");
                Log.v("reg id",msg);
            }
        }.execute(null, null, null);
    }
}
