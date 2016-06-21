package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.idevicesinc.sweetblue.utils.BluetoothEnabler;

import java.util.ArrayList;
import java.util.HashMap;

public class HelloActivity extends AppCompatActivity {

    private static final String TAG = "HelloActivity";
    SimpleAdapter adapter;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        mListView = (ListView)findViewById(R.id.listView);
        // set adapter
        adapter = new SimpleAdapter(HelloActivity.this);
        adapter.add("Shop Thermos Connected Products");
        adapter.add("About Thermos Connecting Products");
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0 ){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yahoo.com")));
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")));

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check permission
        BluetoothEnabler.start(this, new BluetoothEnabler.DefaultBluetoothEnablerFilter() {
            @Override
            public Please onEvent(BluetoothEnabler.BluetoothEnablerFilter.BluetoothEnablerEvent e) {
                return super.onEvent(e);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public class SimpleAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items = new ArrayList<String>();

        public SimpleAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View v = convertView;
            final Holder holder;
            if(v == null){
                v = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_row, null);
                holder = new Holder();
                holder.item_name = (TextView) v.findViewById(R.id.row_title);
                v.setTag(holder);
            }else{
                holder = (Holder) v.getTag();
            }

            holder.item_name.setText(getItem(position));

            return v;
        }

        /**
         * View holder for the views we need access to
         */
        private class Holder {
            public TextView item_name;
        }
    }
}
