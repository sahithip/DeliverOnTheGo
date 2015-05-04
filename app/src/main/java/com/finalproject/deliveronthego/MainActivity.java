package com.finalproject.deliveronthego;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends Activity {
    EditText userId,password;
    CardView login;
    RadioGroup userType;
    RadioButton type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //get Reference to the views

        login=(CardView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = (RadioGroup)findViewById(R.id.radiogrp);
                userId= (EditText)findViewById(R.id.userID);
                password=(EditText) findViewById(R.id.password);
                int selectedId = userType.getCheckedRadioButtonId();
                type = (RadioButton)findViewById(selectedId);

                Log.v("I am here","in background"+selectedId+" "+userId+" "+password);
                new MyAsyncTaskLogin(getBaseContext()).execute(userId.getText().toString(),password.getText().toString(),type.getText().toString());

            }
        });


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void registration(View view)
    {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

}
