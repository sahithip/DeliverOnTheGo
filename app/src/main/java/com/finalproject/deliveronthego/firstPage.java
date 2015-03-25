package com.finalproject.deliveronthego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class firstPage extends Activity {

    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Intent intent = getIntent();
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Welcome to DeliverOnTheGO");
        setContentView(textView);*/
        setContentView(R.layout.activity_first_page);

        createMapView();
        addMarker();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createMapView(){
        googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        try {
            if(null == googleMap){
                googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

                if(null == googleMap) {
                   System.out.print("error creating map");
                }
            }
        } catch (NullPointerException exception){
            exception.printStackTrace();
        }
    }

    private void addMarker(){

        /** Make sure that the map has been initialised **/
        if(null != googleMap){
            googleMap.setMyLocationEnabled(true);
           /* googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(0, 0))
                            .title("Marker")
                            .draggable(true)
            );*/

            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            final LatLng CIU = new LatLng(35.21843892856462, 33.41662287712097);
            googleMap.addMarker(new MarkerOptions()
                    .position(CIU).title("Sample Location"));
            googleMap.getMyLocation();
        }
    }


}
