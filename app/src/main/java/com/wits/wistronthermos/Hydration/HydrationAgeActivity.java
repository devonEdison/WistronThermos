package com.wits.wistronthermos.Hydration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wits.wistronthermos.R;

import java.util.Calendar;
import java.util.Date;

public class HydrationAgeActivity extends AppCompatActivity {
	private static final String TAG = "HydrationAgeActivity";
	TextView ageTextView,cancel, next;
	DatePicker datePicker;
	int yearCurrent,monthCurrent,dayCurrent,age=0;
	SharedPreferences sharedPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hydration_age);
		//set time
		Calendar calendar = Calendar.getInstance();
		yearCurrent = calendar.get(Calendar.YEAR);
		monthCurrent = calendar.get(Calendar.MONTH);
		dayCurrent = calendar.get(Calendar.DATE);
		//set age Text View
		ageTextView = (TextView) findViewById(R.id.age_text);

		datePicker = (DatePicker) findViewById(R.id.datePicker);
		datePicker.setMaxDate(new Date().getTime());
		datePicker.init(yearCurrent, monthCurrent, dayCurrent, new DatePicker.OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int yearChange, int monthOfYear, int dayOfMonth){
				age = yearCurrent - yearChange;
				ageTextView.setText(""+age);
			}
		});

		cancel = (TextView)findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		next = (TextView)findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//setup
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString(getString(R.string.preference_user_age), String.valueOf(age));
				editor.commit();

				Intent intent = new Intent(HydrationAgeActivity.this, HydrationSexActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		sharedPref = HydrationAgeActivity.this.getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (ageTextView != null) {
			ageTextView.setText("" + age);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}
}
