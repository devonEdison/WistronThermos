package com.wits.wistronthermos.InitSetup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.idevicesinc.sweetblue.BleManager;
import com.idevicesinc.sweetblue.BleManagerConfig;
import com.idevicesinc.sweetblue.utils.BluetoothEnabler;
import com.wits.wistronthermos.R;

public class InitSetupConnectActivity extends Activity {
	private static final String TAG = "InitSetupConnectActivity";
	public BleManager m_bleManager;
	SharedPreferences sharedPref;
	String findBottleName = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_setup_connect);

		sharedPref = InitSetupConnectActivity.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
	}

	@Override
	protected void onStart() {
		findBottleName = "";
		// A ScanFilter decides whether a BleDevice instance will be created from a
		// BLE advertisement and passed to the DiscoveryListener implementation below.
		final BleManagerConfig.ScanFilter scanFilter = new BleManagerConfig.ScanFilter() {
			@Override
			public Please onEvent(BleManagerConfig.ScanFilter.ScanEvent e) {
				return BleManagerConfig.ScanFilter.Please.acknowledgeIf(e.name_normalized().contains("thermos"));
//                        .thenStopScan();
				//thermos_34C7
				//thermos_053B
			}
		};


		// New BleDevice instances are provided through this listener.
		// Nested listeners then listen for connection and read results.
		// Obviously you will want to structure your actual code a little better.
		// The deep nesting simply demonstrates the async-callback-based nature of the API.
		final BleManager.DiscoveryListener discoveryListener = new BleManager.DiscoveryListener() {
			@Override
			public void onEvent(DiscoveryEvent e) {
//                Log.i(TAG, "onEvent A =  " + e);
				Log.i(TAG, "onEvent Aname =  " + e.device().getName_debug());
				if("".equals(findBottleName)){
					findBottleName = e.device().getName_debug();
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString(getString(R.string.preference_user_bottle_name1), findBottleName);
					editor.commit();
				}
			}
		};

		// This class helps you navigate the treacherous waters of Android M Location requirements for scanning.
		// First it enables bluetooth itself, then location permissions, then location services. The latter two
		// are only needed in Android M.
		BluetoothEnabler.start(this, new BluetoothEnabler.DefaultBluetoothEnablerFilter() {
			@Override
			public Please onEvent(BluetoothEnabler.BluetoothEnablerFilter.BluetoothEnablerEvent e) {
				if (e.isDone()) {
					e.bleManager().startScan(scanFilter, discoveryListener);
					m_bleManager = e.bleManager();
				}
				return super.onEvent(e);
			}
		});

		//stop at 10 seconds
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				m_bleManager.stopScan();
				Log.d(TAG,"111111111111111111111111111111111");
				if("".equals(findBottleName)){
					Log.d(TAG,"22222222222222222222222222");
					Intent intent = new Intent(InitSetupConnectActivity.this, InitSetupNoSmartLidFoundActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent);
				}else{
					Log.d(TAG,"3333333333333333333333");
					Intent intent = new Intent(InitSetupConnectActivity.this, InitSetupPairCompleteActivity.class);
					startActivity(intent);
				}
			}
		}, 10000);
		super.onStart();
	}
}
