package com.finalproject.deliveronthego;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import android.view.View.OnClickListener;


import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class CollectDeliveryDetails extends Activity implements OnClickListener{
    EditText pickUpLocation, dropOffLocation, length, breadth, width;
    Button buttonPost;
    private static final String TAG = "MyActivity";

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_page);

        //get Reference to the views
        pickUpLocation = (EditText) findViewById(R.id.pickUpInput);
        dropOffLocation=(EditText) findViewById(R.id.dropOffInput);
        length=(EditText) findViewById(R.id.length);
        breadth=(EditText) findViewById(R.id.breadth);
        width=(EditText) findViewById(R.id.width);

        buttonPost=(Button) findViewById(R.id.findDrivers);

    }



   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_first_page, menu);
        return true;
    }

    /* @Override
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
    }*/
    public void clickFindAvailableDrivers(View view)
    {
        Log.i(TAG, "Clicking");
        Intent intent = new Intent(this, firstPage.class);
        startActivity(intent);
        new MyAsyncTask().execute(pickUpLocation.getText().toString(),dropOffLocation.getText().toString(),length.getText().toString(),breadth.getText().toString(),width.getText().toString());
    }

    public void onClick(View v) {
        new MyAsyncTask().execute(pickUpLocation.getText().toString(), dropOffLocation.getText().toString(), length.getText().toString(), breadth.getText().toString(), width.getText().toString());
    }


}
