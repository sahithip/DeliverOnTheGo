package com.finalproject.deliveronthego;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String  TAG = "Login";
    Context context;
    public MyAsyncTaskLogin(Context context) {
        this.context = context.getApplicationContext();
    }
    public HttpResponse postData(String userName, String password,String userType) {
        // Create a new HttpClient and Post Header
        HttpResponse response = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://10.0.0.8:8080/Dotg/rest/home/login");

        try {
           // Log.v("gggdhd","printing response" + response.toString());

            // Add your data
            JSONObject typeObject = new JSONObject().put("emailId", userName).put("password", password).put("userType", userType);

    Log.v(userName+" "+password+" "+userType,"here");

            StringEntity se = new StringEntity(typeObject.toString());

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            //httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // Execute HTTP Post Request
            response = httpclient.execute(httpPost);
            Log.i(TAG, "printing response" + response.toString());


        } catch (ClientProtocolException e) {
           e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected HttpResponse doInBackground(String... params) {
        Log.v("I am here","in background");
        HttpResponse response=postData(params[0],params[1],params[2]);
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
            Log.v("buffer ",buffer+"");
            while ((s = buffer.readLine()) != null) {
                Log.i(TAG,"Value"+s);
                responseValue += s;
            }
            JSONObject res = new JSONObject(responseValue);
            Log.v("responseValue ",responseValue);
            String type =(String)res.get("userType");

            if(type.equalsIgnoreCase("user")) {
                Intent intent = new Intent(context, LocationFragmentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("emailid",(String)res.get("emailid"));
                context.startActivity(intent);
            }
            else if(type.equalsIgnoreCase("driver")){
                Intent intent = new Intent(context, DriverHomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("emailid",(String)res.get("emailid"));
                context.startActivity(intent);
            }else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}