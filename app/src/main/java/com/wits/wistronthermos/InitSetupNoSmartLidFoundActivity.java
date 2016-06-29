package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wits.wistronthermos.adapters.SimpleAdapter;

public class InitSetupNoSmartLidFoundActivity extends Activity {
	ListView mListView;
	SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_setup_no_smart_lid_found);

		mListView = (ListView)findViewById(R.id.listView);
		//set adapter
		adapter = new SimpleAdapter(InitSetupNoSmartLidFoundActivity.this);
		adapter.add("Smart Lid Connection Support");
		adapter.add("Shop Thermos Connected Products");
		adapter.add("About Thermos Connected Products");
		//set mListView
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				if (position == 0 ){
					Intent intent = new Intent(InitSetupNoSmartLidFoundActivity.this, InitSetupConnectSupportActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
				} else if(position == 1 ){
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/product_catalog.aspx?CatCode=CONN")));
				} else if(position == 2 ){
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/about.aspx")));
				}
			}
		});
	}
}
