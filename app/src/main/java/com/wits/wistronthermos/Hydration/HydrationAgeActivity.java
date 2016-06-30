package com.wits.wistronthermos.Hydration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wits.wistronthermos.R;

import java.util.Calendar;
import java.util.Date;

public class HydrationAgeActivity extends AppCompatActivity {
	TextView ageTextView,cancel, next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hydration_age);

		DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
		datePicker.setMaxDate(new Date().getTime());
		Calendar calendar = Calendar.getInstance();
		final int yearCurrent = calendar.get(Calendar.YEAR);
		int monthCurrent = calendar.get(Calendar.MONTH);
		int dayCurrent = calendar.get(Calendar.DATE);

		ageTextView = (TextView) findViewById(R.id.age_text);
		ageTextView.setText("0");
		datePicker.init(yearCurrent, monthCurrent, dayCurrent, new DatePicker.OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int yearChange, int monthOfYear, int dayOfMonth){
				int age = yearCurrent - yearChange;
				ageTextView.setText("" + age);
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
				Intent intent = new Intent(HydrationAgeActivity.this, HydrationSexActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}
}
