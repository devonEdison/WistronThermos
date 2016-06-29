package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InitSetupActivity extends Activity {
    TextView cancel,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_setup);
        //set cancel
        cancel = (TextView) findViewById(R.id.cancel);
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
                Intent intent = new Intent(InitSetupActivity.this, InitSetupNoSmartLidFoundActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, 0);
    }
}
