package com.finalproject.deliveronthego;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anita on 3/18/15.
 */
class MyAsyncTask extends AsyncTask<String, Integer, Double> {


    public void postData(String pickUp, String dropOff ,String length,String breadth,String width) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://somewebsite.com/receiver.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("pickUp", pickUp));
            nameValuePairs.add(new BasicNameValuePair("dropOff", dropOff));
            nameValuePairs.add(new BasicNameValuePair("length", length));
            nameValuePairs.add(new BasicNameValuePair("breadth", breadth));
            nameValuePairs.add(new BasicNameValuePair("width", width));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
           e.printStackTrace();
        }
    }

    @Override
    protected Double doInBackground(String... params) {
        postData(params[0],params[1],params[2],params[3],params[4]);
        return null;
    }

}