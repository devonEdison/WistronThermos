package com.wits.wistronthermos.Hydration;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.wits.wistronthermos.R;

public class HydrationIntensityActivity extends Activity {
	SharedPreferences sharedPref;
	private static final String TAG = "HydrationIntensityActivity";
	TextView mTextView, cancel, next, skip, description,descriptLog;
	HorizontalScrollView hsv;
	ViewTreeObserver observer;
	int my_intensity = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hydration_intensity);

		hsv = (HorizontalScrollView) findViewById(R.id.HRV);
		descriptLog = (TextView)findViewById(R.id.textView17);
		description = (TextView)findViewById(R.id.description);
		mTextView = (TextView) findViewById(R.id.kg);
		mTextView.setText(String.valueOf(my_intensity));
		new Handler().postDelayed((new Runnable() {
			@Override
			public void run() {
				hsv.scrollTo(500, 0);
			}
		}), 50);

		observer = hsv.getViewTreeObserver();
		observer.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
			@Override
			public void onScrollChanged() {
				Log.d(TAG, "hsv getScrollX = " + hsv.getScrollX());
				int scrollX = (int) (hsv.getScrollX());
				//todo: no good agrithm
				if (isBetween(scrollX, 0, 213)) {
					my_intensity = 1;
					description.setText("EASY");
					descriptLog.setText("Could maintain for hours, easy to breathe, no sweat, can carry a conversation");
				} else if (isBetween(scrollX, 214, 426)) {
					my_intensity = 2;
					description.setText("MODERATE");
					descriptLog.setText("Could maintain for hours, easy to breathe, no sweat, can hold a short conversation");
				} else if (isBetween(scrollX, 427, 639)) {
					my_intensity = 3;
					description.setText("VIGOROUS");
					descriptLog.setText("On the verge of being uncomfortable, short of breath, moderate amount of sweat, can speak a sentence.");
				} else if (isBetween(scrollX, 640, 852)) {
					my_intensity = 4;
					description.setText("HARD");
					descriptLog.setText("Very diffcult to maintain exercise, can barely breathe and speak, heavy amount of sweat.");
				} else if (isBetween(scrollX, 853, 1068)) {
					my_intensity = 5;
					description.setText("MEGA");
					descriptLog.setText("Feels almost impossible to keep going, out of breath, unale to talk, extreamely heavy amount of sweat.");
				}
//				if (scrollX == 100) {
//
//				} else if (scrollX == 300) {
//
//				} else if (scrollX == 500) {
//
//				} else if (scrollX == 700) {
//					my_intensity = 4;
//					description.setText("HARD");
//					descriptLog.setText("Very diffcult to maintain exercise, can barely breathe and speak, heavy amount of sweat.");
//				} else if (scrollX == 900) {
//					my_intensity = 5;
//					description.setText("MEGA");
//					descriptLog.setText("Feels almost impossible to keep going, out of breath, unale to talk, extreamely heavy amount of sweat.");
//				}
				mTextView.setText(""+my_intensity);

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
				editor.putString(getString(R.string.preference_user_intensity), String.valueOf(my_intensity));
				editor.commit();

//				Intent intent = new Intent(HydrationIntensityActivity.this, HydrationHeightActivity.class);
//				startActivity(intent);
//				overridePendingTransition(0, 0);
			}
		});
		sharedPref = HydrationIntensityActivity.this.getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		skip = (TextView)findViewById(R.id.skip);
		skip.setPaintFlags(skip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//add underline.= =
		skip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				Intent intent = new Intent(HydrationIntensityActivity.this, HydrationHeightActivity.class);
//				startActivity(intent);
//				overridePendingTransition(0, 0);
			}
		});

	}

	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}

}
