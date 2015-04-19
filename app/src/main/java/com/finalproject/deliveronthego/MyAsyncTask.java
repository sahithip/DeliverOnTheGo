package com.finalproject.deliveronthego;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anita on 3/18/15.
 */
class MyAsyncTask extends AsyncTask<String, Integer, HttpResponse> {
    Context context;

    public MyAsyncTask(Context context) {
        this.context = context.getApplicationContext();
    }

    public HttpResponse postData(String emailId,String pickUpLat,String pickupLong, String dropOffLat,String dropOffLong ,String length,String breadth,String width) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://10.0.0.8:8080/Dotg/rest/home/findDrivers");

        HttpResponse response = null;
        try {
            JSONObject typeObject = new JSONObject().put("pickupLatitude", pickUpLat).
                    put("pickupLongitude", pickupLong).put("emailId", emailId).
                    put("dropOffLatitude", dropOffLat).put("dropOffLongitude", dropOffLong).
                    put("length", length).put("breadth", breadth).put("width", width);
            Log.v("check this",typeObject+"");
            // Add your data
            //  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            // nameValuePairs.add(new BasicNameValuePair("pickUp", pickUp));
            //nameValuePairs.add(new BasicNameValuePair("dropOff", dropOff));
            //nameValuePairs.add(new BasicNameValuePair("length", length));
            //nameValuePairs.add(new BasicNameValuePair("breadth", breadth));
            //nameValuePairs.add(new BasicNameValuePair("width", width));

            StringEntity se = new StringEntity(typeObject.toString());

            httpPost.setEntity(se);
            httpPost.setHeader("Content-type", "application/json");

            // Execute HTTP Post Request
            response = httpclient.execute(httpPost);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected HttpResponse doInBackground(String... params) {
        HttpResponse response= postData(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);
        Log.v("response",response+"");
        return response;
    }
    @Override
    protected void onPostExecute(HttpResponse response) {
        try {
            //Log.v("in on post execute",response.getEntity().getContent()+"");
            InputStream content = response.getEntity().getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            String responseValue="";

            Log.v("buffer ",buffer+"");
            while ((s = buffer.readLine()) != null) {
                Log.v("here","Value"+s);
                responseValue += s;
            }

            Log.v("respon val",responseValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}