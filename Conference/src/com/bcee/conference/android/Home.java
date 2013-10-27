package com.bcee.conference.android;

import com.bcee.conference.android.models.DataCentre;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		DataCentre.createDefaultInstance();
		initvars();



	}

	private void initvars() {
		// TODO Auto-generated method stub
		TextView sessions = (TextView) findViewById(R.id.welcomeB1);
		sessions.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(),ExpandableList.class);
				startActivityForResult(i,0);
			}
		});
		TextView generalSurvey = (TextView) findViewById(R.id.conferenceGeneralSurvey);
		generalSurvey.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
