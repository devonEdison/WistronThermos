package com.wits.wistronthermos.Hydration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.wits.wistronthermos.R;

public class HydrationHeightActivity extends Activity {
    private static final String TAG = "HydrationHeightActivity";
    TextView heightTextView, cancel, next,skip;
    HorizontalScrollView hsv;
    ViewTreeObserver observer;
    int my_height ;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydration_height);

        hsv = (HorizontalScrollView) findViewById(R.id.HRV);
        heightTextView = (TextView) findViewById(R.id.kg);
        heightTextView.setText(String.valueOf(130) + " cm");
        new Handler().postDelayed((new Runnable() {
            @Override
            public void run() {
                hsv.scrollTo(500, 0);
            }
        }), 50);

        observer = hsv.getViewTreeObserver();
        observer.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Log.d(TAG, "hsv getScrollX = " + hsv.getScrollX());
                int scrollX = (int) (hsv.getScrollX() / 5) + 10;
                heightTextView.setText(String.valueOf(scrollX) + " cm");
                my_height = scrollX;
            }
        });

        cancel = (TextView)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        next = (TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setup
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.preference_user_weight), String.valueOf(my_height));
                editor.commit();

                Intent intent = new Intent(HydrationHeightActivity.this, HydrationActivityActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        sharedPref = HydrationHeightActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        skip = (TextView)findViewById(R.id.skip);
        skip.setPaintFlags(skip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//add underline.= =
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HydrationHeightActivity.this, HydrationActivityActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
