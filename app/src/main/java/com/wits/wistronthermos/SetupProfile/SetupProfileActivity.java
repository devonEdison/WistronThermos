package com.wits.wistronthermos.SetupProfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wits.wistronthermos.R;
import com.wits.wistronthermos.adapters.SimpleImageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import multipleimageselect.helpers.Constants;

public class SetupProfileActivity extends Activity {
    private static final String TAG = "SetupProfileActivity";
    SimpleImageAdapter adapter;
    ListView mListView;
    TextView cancel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mListView = (ListView)findViewById(R.id.mListView);
        //set adapter
        adapter = new SimpleImageAdapter(SetupProfileActivity.this);
//        adapter.add(new SampleItem(getResources().getString(R.string.lucida_string00000027), R.drawable.icon_menu_home));
        adapter.add(new SimpleImageAdapter.SampleItem("Use Fitbit Profile",R.mipmap.fitbitblack));
        adapter.add(new SimpleImageAdapter.SampleItem("Create My Own Prfile",0));

        //set mListView
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0 ){
                    startActivityForResult(new Intent(SetupProfileActivity.this, FitbitAuthenticationActivity.class), Constants.GET_FITBIT_REQUEST);
                } else {
                    Intent intent = new Intent(SetupProfileActivity.this, SetupProfileNameActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });

        //set cancel
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sharedPref = SetupProfileActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    SharedPreferences sharedPref;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case Constants.GET_FITBIT_REQUEST:
                Log.d(TAG,"check result Code = " + resultCode);
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    if(extras != null){
                        final String access_token = extras.getString("access_token");
                        final String user_id = extras.getString("user_id");

                        //setup
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.preference_user_fitbit_access_token), access_token);
                        editor.putString(getString(R.string.preference_user_fitbit_user_id), user_id);
                        editor.commit();

                        Log.d(TAG, "2devon check code = " + access_token);
                        RetrieveData(access_token, user_id);
                    }
                }else{
                }
        }
    }

    public void RetrieveData(String access_token, String user_id) {
        Log.d(TAG, "3devon check access_token = " + access_token + "  ,user_id = " + user_id);
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader("Authorization", "Bearer "+access_token);
        client.get(SetupProfileActivity.this, "https://api.fitbit.com/1/user/"+user_id+"/profile.json", new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.d(TAG, "4devon check responseBody = " + response);
                try {
                    JSONObject obj = new JSONObject(response);

                    //get name
                    JSONObject user = obj.getJSONObject("user");
                    String displayName = user.getString("displayName");
                    String avatar150 = user.getString("avatar150");
                    String waterUnit = user.getString("waterUnit");

                    //setup name
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preference_user_name), displayName);
                    editor.putString(getString(R.string.preference_user_photo_real_path), avatar150);
                    editor.putString(getString(R.string.preference_user_waterUnit), waterUnit);
                    editor.commit();

                    //進入下一個activity
                    Intent intent = new Intent(SetupProfileActivity.this, FitbitEnableActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
