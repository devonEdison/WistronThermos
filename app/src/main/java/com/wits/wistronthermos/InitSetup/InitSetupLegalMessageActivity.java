package com.wits.wistronthermos.InitSetup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wits.wistronthermos.R;

import multipleimageselect.helpers.Constants;

public class InitSetupLegalMessageActivity extends Activity {
	TextView titleTextView, contentTextView;
	RelativeLayout cancel_releative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_setup_legal_message);
		Intent intent = getIntent();
		String title = intent.getStringExtra(Constants.TITLE);

		titleTextView = (TextView) findViewById(R.id.title);
		if (title != null) {
			titleTextView.setText(title);
		}

		contentTextView = (TextView) findViewById(R.id.content);
		if (title != null) {
			contentTextView.setText(title + " 的一些policy 文章");
		}

		cancel_releative = (RelativeLayout)findViewById(R.id.cancel_releative);
		cancel_releative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}
}
