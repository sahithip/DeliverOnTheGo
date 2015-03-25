package com.finalproject.deliveronthego;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Anita on 3/20/15.
 */
class AsyncTaskUserRegistration extends AsyncTask<String, Integer, Double> {
    private static final String TAG = "Registration";

    public void postData(String firstName, String lastName, String emailId, String password,String phoneNumber) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://somewebsite.com/receiver.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("firstName", firstName));
            nameValuePairs.add(new BasicNameValuePair("lastName", lastName));
            nameValuePairs.add(new BasicNameValuePair("emailId", emailId));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("phoneNumber", phoneNumber));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            Log.i(TAG,"printing response"+response.toString());

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
