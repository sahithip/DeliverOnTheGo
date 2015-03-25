package com.finalproject.deliveronthego;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    EditText userId,password;
    Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //get Reference to the views
        userId= (EditText)findViewById(R.id.userID);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.loginButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void login(View view) {
         new MyAsyncTaskLogin().execute(userId.getText().toString(),password.getText().toString());
        Intent intent = new Intent(this, CollectDeliveryDetails.class);
        startActivity(intent);
    }
    public void registration(View view)
    {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

}
