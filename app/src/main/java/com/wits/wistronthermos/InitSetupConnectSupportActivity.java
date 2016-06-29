package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wits.wistronthermos.adapters.SimpleDarkAdapter;

public class InitSetupConnectSupportActivity extends Activity {
	RelativeLayout cancel_releative;
	SimpleDarkAdapter adapter;
	ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_setup_connect_support);
		cancel_releative = (RelativeLayout)findViewById(R.id.cancel_releative);
		cancel_releative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		mListView = (ListView)findViewById(R.id.listView);
		//set adapter
		adapter = new SimpleDarkAdapter(InitSetupConnectSupportActivity.this);
		adapter.add("Connecting Your Smart Lid");
		adapter.add("Smart Lid Product Manuals");
		adapter.add("Online Support Resources");
		adapter.add("Contact Support");
		adapter.add("Join Mailing List");
		adapter.add("FAQs");
		adapter.add("Legal");


		//set mListView
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				switch (position){
					case 0:
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/smartlid.aspx")));
						break;
					case 1:
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/smartlid.aspx")));
						break;
					case 2:
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/smartlid.aspx")));
						break;
					case 3:
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/contact.aspx")));
						break;
					case 4://Join Mailing List
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/newsletter-signup.aspx")));
						break;
					case 5://FAQs
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/contact.aspx")));
						break;
					case 6:
						Intent intent = new Intent(InitSetupConnectSupportActivity.this, InitSetupLegalActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
						break;
				}
			}
		});
		//沒有要用actoinbar
//		getSupportActionBar().setTitle("Connect");
//		getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
//		getSupportActionBar().setHomeButtonEnabled(true);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	//沒有要用actonbar
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case android.R.id.home:
//				//Write your logic here
//				this.finish();
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//	}
}
