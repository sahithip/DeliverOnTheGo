package com.finalproject.deliveronthego;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anita on 3/18/15.
 */
class MyAsyncTaskLogin extends AsyncTask<String, String, HttpResponse> {

    private static final String TAG = "Login";

    public HttpResponse postData(String userName, String password) {
        // Create a new HttpClient and Post Header
        HttpResponse response = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.0.0.8:3000/cs/signup");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("csemail", userName));
            nameValuePairs.add(new BasicNameValuePair("cspwd", password));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);
            Log.i(TAG, "printing response" + response.toString());


        } catch (ClientProtocolException e) {
           e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected HttpResponse doInBackground(String... params) {
        HttpResponse response=postData(params[0],params[1]);
        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse response)
    {
        String responseValue="";
        HttpEntity httpEntity = response.getEntity();
        try {
            InputStream content = httpEntity.getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                Log.i(TAG,"Value"+s);
                responseValue += s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}