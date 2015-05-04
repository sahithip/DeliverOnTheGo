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
 * Created by Sahithi on 3/18/15.
 */
class MyAsyncDriverTask extends AsyncTask<String, Integer, HttpResponse> {
    Context context;

    public MyAsyncDriverTask(Context context) {
        this.context = context.getApplicationContext();
    }

    public HttpResponse postData(String emailId,String startLat,String startLong, String endLat,String endLong) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://10.0.0.8:8080/Dotg/rest/home/location");

        HttpResponse response = null;
        try {
            JSONObject typeObject = new JSONObject().put("transitionLatitude", startLat).
                    put("transitionLongitude", startLong).put("emailId", emailId).
                    put("stopLatitude", endLat).put("stopLongitude", endLong).put("driverID",emailId);
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
        HttpResponse response= postData(params[0],params[1],params[2],params[3],params[4]);
        Log.v("response",response+"");
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
        return response;
    }
    @Override
    protected void onPostExecute(HttpResponse response) {

    }

}