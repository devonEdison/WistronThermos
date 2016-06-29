package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FitbitEnableActivity extends Activity {
	//this activity has no history
	//calling from SetupProfileActivity intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	TextView next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fitbit_enable);
		next = (TextView)findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//進入下一個activity
				Intent intent = new Intent(FitbitEnableActivity.this, SetupProfileNameActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});
	}

	@Override
	public void onBackPressed() {
		//do nothing
	}
}
