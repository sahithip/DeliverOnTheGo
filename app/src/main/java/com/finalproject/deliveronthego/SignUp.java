package com.finalproject.deliveronthego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class SignUp extends Activity {
    private EditText firstName;
    private EditText lastName;
    private EditText emailid;
    private EditText password;
    private EditText phoneNumber;
    private Button signupUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        emailid = (EditText) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.password);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        signupUser = (Button) findViewById(R.id.userSignup);
        signupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyUserSignup(getBaseContext()).execute(firstName.getText().toString(), lastName.getText().toString(), password.getText().toString(), emailid.getText().toString(), phoneNumber.getText().toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
    class MyUserSignup extends AsyncTask<String, String, HttpResponse> {

        private static final String TAG = "Login";
        Context context;

        public MyUserSignup(Context context) {
            this.context = context.getApplicationContext();
        }

        public HttpResponse postData(String firstName,String lastName, String password, String emailid,String phoneNo) {
            // Create a new HttpClient and Post Header
            HttpResponse response = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.1.53:8080/Dotg/rest/home/customersignup");

            try {
                // Log.v("gggdhd","printing response" + response.toString());

                // Add your data
                JSONObject typeObject = new JSONObject().put("emailid", emailid).put("password", password).put("firstName",firstName).put("lastName",lastName).put("phoneNumber",phoneNo);

                Log.v(firstName + " " + password + " " , "here");

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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected HttpResponse doInBackground(String... params) {
            Log.v("I am here", "in background");
            HttpResponse response = postData(params[0], params[1], params[2],params[3],params[4]);
            return response;
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            String responseValue = "";
            HttpEntity httpEntity = response.getEntity();
            try {
                InputStream content = httpEntity.getContent();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    Log.i(TAG, "Value" + s);
                    responseValue += s;
                }
                if (responseValue.equalsIgnoreCase("user")) {
                    Intent intent = new Intent(context, LocationFragmentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                } else if (responseValue.equalsIgnoreCase("driver")) {
                    Intent intent = new Intent(context, DriverHomePage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                } else {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
