package com.wits.wistronthermos;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.wits.wistronthermos.adapters.SimpleAdapter;

public class FragmentLeftList extends ListFragment {

	private static final String TAG = "FragmentLeftList";
	private SlidingMenuSetupActivity activitymatch;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.left_side_list, container, false);
	}

	SimpleAdapter adapter;
	ImageButton profilePicture2;
	TextView profile_name;

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		activitymatch = (SlidingMenuSetupActivity) getActivity();
		activitymatch.getSlidingMenu().setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
				// setup my list
				setHeadImage();
			}
		});

		// set adapter
		adapter = new SimpleAdapter(getActivity());
		adapter.add("Smart Lid Connection Support");
		adapter.add("Shop Thermos Connected Products");
		adapter.add("About Thermos Connected Products");
		setListAdapter(adapter);

		// set profile picture
		profilePicture2 = (ImageButton) getActivity().findViewById(R.id.profilePicture_left);
		profilePicture2.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getActivity().getSupportFragmentManager().findFragmentByTag("Home") != null || getActivity().getSupportFragmentManager().findFragmentByTag("choose") != null) {
//					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentMyprofile(), "Myprofile").commit();
//					activitymatch.showContent();
					activitymatch.toggle();
				} else if (getActivity().getSupportFragmentManager().findFragmentByTag("Setting") != null) {
//					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentMyprofile(), "Myprofile").commit();
//					activitymatch.showContent();
					activitymatch.toggle();
				} else if (getActivity().getSupportFragmentManager().findFragmentByTag("Myprofile") != null) {
					activitymatch.toggle();
				} else {
					activitymatch.toggle();
				}

			}
		});
		// get profile picture
		// set profile name
	}
	SharedPreferences settings;
	public void setHeadImage() {
	}

	ActionMode mActionMode;

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.e(TAG, "clicking adapter.getItemId(position) " + adapter.getItemId(position)); // position,位置
		Log.e(TAG, "clicking adapter.getCount() " + adapter.getCount()); // 全部數目
		Log.e(TAG, "clicking adapter.getCount() " + adapter.getItem(position)); // 內容物
		switch ((int) adapter.getItemId(position)) {
		// home
		case 0:
			if (getActivity().getSupportFragmentManager().findFragmentByTag("Home") != null || getActivity().getSupportFragmentManager().findFragmentByTag("choose") != null) {
				activitymatch.toggle();
			} else if (getActivity().getSupportFragmentManager().findFragmentByTag("Setting") != null) {
//				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentHome(), "Home").commit();
//				activitymatch.showContent();
				activitymatch.toggle();
			} else if (getActivity().getSupportFragmentManager().findFragmentByTag("Myprofile") != null) {
//				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentHome(), "Home").commit();
//				activitymatch.showContent();
				activitymatch.toggle();
			} else {
				activitymatch.toggle();
			}
			break;
		// Messages
		case 1:
			activitymatch.toggle();
			break;
		// Newsfeed
		case 2:
			activitymatch.toggle();
			break;
		// rank campus
		case 3:
			activitymatch.toggle();
			break;
		// Setting
		case 4:
//			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentSetting(), "Setting").commit();
//			activitymatch.showContent();
			activitymatch.toggle();
			break;
		// Invite
		case 5:
			activitymatch.toggle();
			break;
		}
	}
}
