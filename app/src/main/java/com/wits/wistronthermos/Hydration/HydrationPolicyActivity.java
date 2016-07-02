package com.wits.wistronthermos.Hydration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wits.wistronthermos.R;
import com.wits.wistronthermos.adapters.SimpleDarkAdapter;

public class HydrationPolicyActivity extends Activity {
	SimpleDarkAdapter adapter;
	ListView mListView;
	TextView cancel;
	LinearLayout listViewLinearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hydration_policy);
		mListView = (ListView)findViewById(R.id.listView);
		//set adapter
		adapter = new SimpleDarkAdapter(HydrationPolicyActivity.this);
		adapter.add("Accept Terms And Conditions");
		adapter.add("Decline Terms And Exit");

		//set mListView
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				switch (position){
					case 0://Terms of Use
						Intent intent = new Intent(HydrationPolicyActivity.this, HydrationAgeActivity.class);
						startActivity(intent);
						overridePendingTransition(0, 0);
						break;
					case 1://
						onBackPressed();
						break;
				}
			}
		});

		cancel = (TextView)findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		listViewLinearLayout = (LinearLayout)findViewById(R.id.listViewLinearLayout);
		String name = this.getIntent().getStringExtra("show_confirm_list");
		if (name != null && name.equals("no")){
			listViewLinearLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}
}
