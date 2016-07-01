package com.wits.wistronthermos.Hydration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wits.wistronthermos.R;

public class HydrationSexActivity extends Activity {
	private static final String TAG = "HydrationSexActivity";
	TextView next,cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hydration_sex);
		setupMyGender();
		next = (TextView)findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//setup
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString(getString(R.string.preference_user_gender), my_sex);
				editor.commit();

				Intent intent = new Intent(HydrationSexActivity.this, HydrationWeightActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		cancel = (TextView)findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	String my_sex="";
	private ToggleButton iamman, iamwomen;
	SharedPreferences sharedPref;
	private void setupMyGender() {
		sharedPref = HydrationSexActivity.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		my_sex = sharedPref.getString(getString(R.string.preference_user_gender), "female");
		Log.e(TAG,"my_sex " + sharedPref.getString(getString(R.string.preference_user_gender), "female"));
		iamman = (ToggleButton) HydrationSexActivity.this.findViewById(R.id.iamman);
		iamman.setOnCheckedChangeListener(changeChecker);

		iamwomen = (ToggleButton) HydrationSexActivity.this.findViewById(R.id.iamwomen);
		iamwomen.setOnCheckedChangeListener(changeChecker);


		if (my_sex.equals("male")){
			iamman.setChecked(true);
		}else {
			iamwomen.setChecked(true);
		}
	}
	CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
									 boolean isChecked) {
			SharedPreferences sp = HydrationSexActivity.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			if (isChecked) {
				if (buttonView == iamman) {
					my_sex = "male";
					iamwomen.setChecked(false);
				}
				if (buttonView == iamwomen) {
					my_sex = "female";
					iamman.setChecked(false);
				}
			}

			if (!isChecked){
				//取消男人
				if (buttonView == iamman) {
					if(!iamwomen.isChecked()){
						iamman.setChecked(true);
						my_sex = "male";
						Toast.makeText(HydrationSexActivity.this, "must choose at least one gender", Toast.LENGTH_SHORT).show();
					}
				}
				if (buttonView == iamwomen) {
					if(!iamman.isChecked()){
						iamwomen.setChecked(true);
						my_sex = "female";
						Toast.makeText(HydrationSexActivity.this, "must choose at least one gender", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}
}
