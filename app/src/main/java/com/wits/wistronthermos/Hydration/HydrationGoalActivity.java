package com.wits.wistronthermos.Hydration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wits.wistronthermos.R;
import com.wits.wistronthermos.adapters.SimpleDarkAdapter;

public class HydrationGoalActivity extends Activity {
	SimpleDarkAdapter adapter;
	ListView mListView;
	TextView skip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hydration_goal);

		mListView = (ListView)findViewById(R.id.listView);
		//set adapter
		adapter = new SimpleDarkAdapter(HydrationGoalActivity.this);
		adapter.add("Start Using Your Product");

		//set mListView
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				switch (position){
					case 0:
//						Intent intent = new Intent(HydrationGoalActivity.this, HydrationPolicyActivity.class);
//						startActivity(intent);
//						overridePendingTransition(0, 0);
						break;
				}
			}
		});

		skip = (TextView)findViewById(R.id.skip);
		skip.setPaintFlags(skip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//add underline.= =
		skip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(HydrationGoalActivity.this, HydrationPolicyActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("show_confirm_list","no");
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});
	}
}
