package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wits.wistronthermos.adapters.SimpleDarkAdapter;

import multipleimageselect.helpers.Constants;

public class InitSetupLegalActivity extends Activity {
	RelativeLayout cancel_releative;
	SimpleDarkAdapter adapter;
	ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_setup_legal);
		cancel_releative = (RelativeLayout)findViewById(R.id.cancel_releative);
		cancel_releative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		mListView = (ListView)findViewById(R.id.listView);
		//set adapter
		adapter = new SimpleDarkAdapter(InitSetupLegalActivity.this);
		adapter.add("Terms of Use");
		adapter.add("Privacy Policy");
		adapter.add("Hydration Disclaimers");

		//set mListView
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				switch (position){
					case 0://Terms of Use
						Intent intent = new Intent(InitSetupLegalActivity.this, InitSetupLegalMessageActivity.class);
						intent.putExtra(Constants.TITLE, "Terms of Use");
						intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(intent);
						overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
						break;
					case 1://Privacy Policy
						Intent intent2 = new Intent(InitSetupLegalActivity.this, InitSetupLegalMessageActivity.class);
						intent2.putExtra(Constants.TITLE, "Privacy Policy");
						intent2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(intent2);
						overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
						break;
					case 2://Hydration Disclaimers
						Intent intent3 = new Intent(InitSetupLegalActivity.this, InitSetupLegalMessageActivity.class);
						intent3.putExtra(Constants.TITLE, "Hydration Disclaimers");
						intent3.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(intent3);
						overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
						break;
				}
			}
		});
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}
}
