package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SetUpProfileActivity extends Activity {
    SimpleImageAdapter adapter;
    ListView mListView;
    TextView cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        mListView = (ListView)findViewById(R.id.mListView);
        //set adapter
        adapter = new SimpleImageAdapter(SetUpProfileActivity.this);
//        adapter.add(new SampleItem(getResources().getString(R.string.lucida_string00000027), R.drawable.icon_menu_home));
        adapter.add(new SampleItem("Use Fitbit Profile",R.drawable.fitbitblack));
        adapter.add(new SampleItem("Create My Own Prfile",0));

        //set mListView
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

        //set cancel
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    private class SampleItem {
        public String text;
        public int iconRes;

        public SampleItem(String text, int iconRes) {
            this.text = text;
            this.iconRes = iconRes;
        }
    }

    public class SimpleImageAdapter extends ArrayAdapter<SampleItem> {
        public SimpleImageAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View v = convertView;
            final Holder holder;
            if(v == null){
                v = LayoutInflater.from(getContext()).inflate(R.layout.simple_image_list_row, null);
                holder = new Holder();
                holder.row_title = (TextView) v.findViewById(R.id.row_title);
                holder.row_icon = (ImageView)v.findViewById(R.id.row_icon);
                v.setTag(holder);
            }else{
                holder = (Holder) v.getTag();
            }

            holder.row_title.setText(getItem(position).text);
            if ( position ==1 ) {
                holder.row_icon.setVisibility(View.GONE);
            } else {
                holder.row_icon.setVisibility(View.VISIBLE);
                holder.row_icon.setImageResource(getItem(position).iconRes);
            }

            return v;
        }

        /**
         * View holder for the views we need access to
         */
        private class Holder {
            public TextView row_title;
            public ImageView row_icon;
        }
    }
}
