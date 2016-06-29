package com.wits.wistronthermos;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.wits.wistronthermos.adapters.SimpleAdapter;

import multipleimageselect.helpers.Constants;

public class HelloActivity extends Activity {

    private static final String TAG = "HelloActivity";
    SimpleAdapter adapter;
    ListView mListView;
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        mListView = (ListView)findViewById(R.id.listView);
        //set adapter
        adapter = new SimpleAdapter(HelloActivity.this);
        adapter.add("Shop Thermos Connected Products");
        adapter.add("About Thermos Connecting Products");
        //set mListView
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0 ){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/product_catalog.aspx?CatCode=CONN")));
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thermos.com/about.aspx")));

                }
            }
        });
        //button
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelloActivity.this, SetupProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 1 check locatoin permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HelloActivity.this, Manifest.permission.ACCESS_FINE_LOCATION )) {
                //第2次進入apk或是之後進入apk都會進來這
                ActivityCompat.requestPermissions(HelloActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION },
                        Constants.MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                //第一次進入apk會進來這
                ActivityCompat.requestPermissions(HelloActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION },
                        Constants.MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }else{
            //location permissions get do tasks you want.
        }

        // 2.check bluetooth
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(HelloActivity.this,"Device does not support Bluetooth",Toast.LENGTH_LONG).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, Constants.BLE_TURNON);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case Constants.BLE_TURNON:
                Log.d(TAG,"check result Code = " + resultCode);
                if(resultCode == -1){
                    //get permission
                }else{
                    Toast.makeText(HelloActivity.this,"拜託要給我藍芽權限",Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the task you need to do.
                } else {
                    Toast.makeText(HelloActivity.this,"拜託要給我定位權限",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



}
