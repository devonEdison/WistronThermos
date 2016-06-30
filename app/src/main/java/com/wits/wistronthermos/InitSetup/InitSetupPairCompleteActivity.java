package com.wits.wistronthermos.InitSetup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wits.wistronthermos.R;

public class InitSetupPairCompleteActivity extends Activity {
	TextView next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_setup_pair_complete);
		next = (TextView)findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(InitSetupPairCompleteActivity.this, InitSetupNoSmartLidFoundActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});
	}
}
