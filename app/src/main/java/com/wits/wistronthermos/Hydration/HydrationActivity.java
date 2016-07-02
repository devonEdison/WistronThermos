package com.wits.wistronthermos.Hydration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wits.wistronthermos.R;
import com.wits.wistronthermos.adapters.SimpleDarkAdapter;

public class HydrationActivity extends Activity {
	SimpleDarkAdapter adapter;
	ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hydration);
		mListView = (ListView)findViewById(R.id.listView);
		//set adapter
		adapter = new SimpleDarkAdapter(HydrationActivity.this);
		adapter.add("Setup My Suggested Goal");
		adapter.add("Continue With General Baseline Goal");

		//set mListView
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				switch (position){
					case 0://Terms of Use
						Intent intent = new Intent(HydrationActivity.this, HydrationPolicyActivity.class);
						intent.putExtra("show_confirm_list","yes");
						startActivity(intent);
						overridePendingTransition(0, 0);
						break;
					case 1://
//						Intent intent2 = new Intent(HydrationActivity.this, InitSetupLegalMessageActivity.class);
//						intent2.putExtra(Constants.TITLE, "Privacy Policy");
//						intent2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//						startActivity(intent2);
//						overridePendingTransition(0,0);
						break;
				}
			}
		});
	}
}
